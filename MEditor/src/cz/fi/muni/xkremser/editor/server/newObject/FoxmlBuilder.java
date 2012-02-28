/*
 * Metadata Editor
 * 
 * Metadata Editor - Rich internet application for editing metadata.
 * Copyright (C) 2011  Jiri Kremser (kremser@mzk.cz)
 * Moravian Library in Brno
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *
 * 
 */

package cz.fi.muni.xkremser.editor.server.newObject;

import java.io.ByteArrayOutputStream;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.QName;

import cz.fi.muni.xkremser.editor.client.mods.ModsTypeClient;
import cz.fi.muni.xkremser.editor.client.util.ClientUtils;
import cz.fi.muni.xkremser.editor.client.util.Constants;
import cz.fi.muni.xkremser.editor.client.util.Constants.DATASTREAM_CONTROLGROUP;
import cz.fi.muni.xkremser.editor.client.util.Constants.DATASTREAM_ID;

import cz.fi.muni.xkremser.editor.server.fedora.utils.Dom4jUtils;
import cz.fi.muni.xkremser.editor.server.fedora.utils.Dom4jUtils.PrintType;
import cz.fi.muni.xkremser.editor.server.fedora.utils.FoxmlUtils;

import cz.fi.muni.xkremser.editor.shared.domain.DigitalObjectModel;
import cz.fi.muni.xkremser.editor.shared.domain.FedoraNamespaces;
import cz.fi.muni.xkremser.editor.shared.rpc.MetadataBundle;
import cz.fi.muni.xkremser.editor.shared.rpc.NewDigitalObject;

/**
 * @author Jiri Kremser
 * @version 31.10.2011
 */
@SuppressWarnings("unused")
public abstract class FoxmlBuilder {

    private static final String PID_PREFIX = Constants.FEDORA_UUID_PREFIX;
    private String uuid;
    private String pid;
    private String signature;
    private String sysno;
    private String label;
    private String pageType;
    private int pageScanIndex;
    private Policy policy = Policy.PRIVATE;
    private final List<RelsExtRelation> children;
    private MetadataBundle bundle;
    private Document document;
    protected Element rootElement;
    private Document dcXmlContent;
    private Document modsXmlContent;
    private Document relsExtXmlContent;
    private Element policyContentLocation;

    private String krameriusUrl;
    private String alephUrl;
    private String imageUrl;
    private String dateIssued;

    private static final Boolean VERSIONABLE = true;

    public FoxmlBuilder(NewDigitalObject object) {
        this(object, Policy.PRIVATE);
    }

    public FoxmlBuilder(NewDigitalObject object, Policy policy) {
        this.label = ClientUtils.trimLabel(object.getName(), Constants.MAX_LABEL_LENGTH);
        this.children = new ArrayList<RelsExtRelation>();
        this.policy = policy;
    }

    public void createDocument() {
        createDocumentAndRootElement();
        decotateProperties();
        decorateDCStream();
        decorateMODSStream();
        decorateRelsExtStream();
        createOtherStreams();
        //        addPolicyDatastream(policy);
        //dcXmlContent = createDcXmlContent();
        //        modsXmlContent = createModsXmlContent();
        //        relsExtXmlContent = createRelsExtXmlContent();
        //        policyContentLocation = createPolicyContentLocation();
    }

    public String getDocument(boolean toScreen) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Dom4jUtils.writeDocument(rootElement.getDocument(), baos, PrintType.PRETTY);
        if (toScreen) {
            System.out.println(baos.toString());
        }
        return baos.toString();
    }

    private void createDocumentAndRootElement() {
        rootElement = DocumentHelper.createElement(new QName("digitalObject", Namespaces.foxml));
        document = DocumentHelper.createDocument(rootElement);
        document.getRootElement().add(Namespaces.foxml);
        document.getRootElement().add(Namespaces.xsi);
        document.getRootElement()
                .addAttribute(new QName("schemaLocation", Namespaces.xsi),
                              "info:fedora/fedora-system:def/foxml# http://www.fedora.info/definitions/1/0/foxml1-1.xsd");
        rootElement.addAttribute("VERSION", "1.1");
        rootElement.addAttribute("PID", pid);
    }

    private void addPolicyDatastream(Policy policy) {
        policyContentLocation.addAttribute("TYPE", "URL");
        switch (policy) {
            case PRIVATE:
                policyContentLocation
                        .addAttribute("REF", "http://local.fedora.server/fedora/get/policy:private/POLICYDEF");
            case PUBLIC:
                policyContentLocation
                        .addAttribute("REF", "http://local.fedora.server/fedora/get/policy:public/POLICYDEF");
        }
    }

    private Element createDatastreamElement(Element rootElement,
                                            String state,
                                            DATASTREAM_CONTROLGROUP controlGroup,
                                            DATASTREAM_ID dsId) {
        Element dataStream = rootElement.addElement(new QName("datastream", Namespaces.foxml));
        dataStream.addAttribute("VERSIONABLE", VERSIONABLE.toString());
        dataStream.addAttribute("STATE", state);
        dataStream.addAttribute("CONTROL_GROUP", controlGroup.name());
        dataStream.addAttribute("ID", dsId.getValue());
        return dataStream;
    }

    private void decotateProperties() {
        Element properties = rootElement.addElement(new QName("objectProperties", Namespaces.foxml));
        addProperty(properties, "info:fedora/fedora-system:def/model#label", label);
        addProperty(properties, "info:fedora/fedora-system:def/model#state", "A");
        //TODO: change to the responsible person later
        addProperty(properties, "info:fedora/fedora-system:def/model#ownerId", "fedoraAdmin");
    }

    private void addProperty(Element properties, String name, String value) {
        Element property = properties.addElement(new QName("property", Namespaces.foxml));
        property.addAttribute("NAME", name);
        property.addAttribute("VALUE", value);
    }

    protected void decorateDCStream() {
        Element rootElement = DocumentHelper.createElement(new QName("dc", Namespaces.oai_dc));
        rootElement.add(Namespaces.dc);
        rootElement.add(Namespaces.xsi);
        Element title = rootElement.addElement(new QName("title", Namespaces.dc));
        title.addText(getLabel());
        Element identifier = rootElement.addElement(new QName("identifier", Namespaces.dc));
        identifier.setText(getPid());
        Element type = rootElement.addElement(new QName("type", Namespaces.dc));
        type.addText("model:" + getModel().getValue());
        Element rights = rootElement.addElement(new QName("rights", Namespaces.dc));
        rights.addText("policy:" + getPolicy().toString().toLowerCase());
        appendDatastream(DATASTREAM_CONTROLGROUP.X, DATASTREAM_ID.DC, rootElement, null, null);
        dcXmlContent = rootElement.getDocument();
    }

    protected void appendDatastream(DATASTREAM_CONTROLGROUP dsCGroup,
                                    DATASTREAM_ID dsId,
                                    Element content,
                                    String name,
                                    String reference) {
        Element datastreamEl = createDatastreamElement(rootElement, "A", dsCGroup, dsId);
        String versionId = dsId.toString() + ".0";
        Element dataStreamVersion = null;
        switch (dsId) {
            case DC:
                dataStreamVersion =
                        createDatastreamVersionElement(datastreamEl,
                                                       FedoraNamespaces.OAI_DC_NAMESPACE_URI,
                                                       "text/xml",
                                                       "Dublin Core Record for this object",
                                                       versionId);
                break;
            case BIBLIO_MODS:
                dataStreamVersion =
                        createDatastreamVersionElement(datastreamEl,
                                                       FedoraNamespaces.BIBILO_MODS_URI,
                                                       "text/xml",
                                                       "BIBLIO_MODS description of current object",
                                                       versionId);
                break;
            case RELS_EXT:
                dataStreamVersion =
                        createDatastreamVersionElement(datastreamEl,
                                                       FedoraNamespaces.RELS_EXT_NAMESPACE_URI,
                                                       "application/rdf+xml",
                                                       "RDF Statements about this object",
                                                       versionId);
                break;
            case IMG_FULL:
                dataStreamVersion =
                        createDatastreamVersionElement(datastreamEl, null, "image/jpeg", "", versionId);
                break;
            case IMG_THUMB:
                dataStreamVersion =
                        createDatastreamVersionElement(datastreamEl, null, "image/jpeg", "", versionId);
        }
        switch (dsCGroup) {
            case X:
                addXmlContent(content, dataStreamVersion);
                break;
            case E:
            case R:
                addContentLocation(name, reference, dataStreamVersion);
                break;
            default:
                throw new IllegalArgumentException("Unable to add Fedora managed content");

        }
    }

    protected Element createDatastreamVersionElement(Element rootEl,
                                                     String formatUri,
                                                     String mimetype,
                                                     String label,
                                                     String id) {
        Element dataStreamVersion = rootEl.addElement(new QName("datastreamVersion", Namespaces.foxml));
        if (formatUri != null) {
            dataStreamVersion.addAttribute("FORMAT_URI", formatUri);
        }
        dataStreamVersion.addAttribute("MIMETYPE", mimetype);
        //probably will be generated
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        dataStreamVersion.addAttribute("CREATED", sdf.format(now));
        if (label != null) {
            dataStreamVersion.addAttribute("LABEL", label);
        }
        dataStreamVersion.addAttribute("ID", id);
        return dataStreamVersion;
    }

    private void addXmlContent(Element content, Element dataStreamVersion) {
        Element xmlContent = dataStreamVersion.addElement(new QName("xmlContent", Namespaces.foxml));
        xmlContent.add(content);
    }

    private void addContentLocation(String name, String reference, Element dataStreamVersion) {
        Element contentLocation =
                dataStreamVersion.addElement(new QName("contentLocation", Namespaces.foxml));
        contentLocation.addAttribute("TYPE", name);
        contentLocation.addAttribute("REF", reference);
    }

    protected void decorateRelsExtStream() {
        Document relsExt = FoxmlUtils.createRelsExtSkeleton(uuid, getModel().getValue(), policy);
        for (RelsExtRelation child : children) {
            FoxmlUtils.addRelationshipToRelsExt(relsExt, child);
        }
        appendDatastream(DATASTREAM_CONTROLGROUP.X,
                         DATASTREAM_ID.RELS_EXT,
                         relsExt.getRootElement(),
                         null,
                         null);
        relsExtXmlContent = relsExt;
    }

    protected abstract void decorateMODSStream();

    protected abstract void createOtherStreams();

    protected abstract DigitalObjectModel getModel();

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getPageType() {
        return pageType;
    }

    public void setPageType(String pageType) {
        this.pageType = pageType;
    }

    public String getPid() {
        return pid;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
        this.pid = PID_PREFIX + uuid;
    }

    public Policy getPolicy() {
        return policy;
    }

    public void setPolicy(Policy policy) {
        this.policy = policy;
    }

    public List<RelsExtRelation> getChildren() {
        return children;
    }

    public Document getDcXmlContent() {
        return dcXmlContent;
    }

    public void setDcXmlContent(Document dcXmlContent) {
        this.dcXmlContent = dcXmlContent;
    }

    public Document getModsXmlContent() {
        return modsXmlContent;
    }

    public void setModsXmlContent(Document modsXmlContent) {
        this.modsXmlContent = modsXmlContent;
    }

    public Document getRelsExtXmlContent() {
        return relsExtXmlContent;
    }

    public void setRelsExtXmlContent(Document relsExtXmlContent) {
        this.relsExtXmlContent = relsExtXmlContent;
    }

    public String getKrameriusUrl() {
        return krameriusUrl;
    }

    public void setKrameriusUrl(String krameriusUrl) {
        this.krameriusUrl = krameriusUrl;
    }

    public String getAlephUrl() {
        return alephUrl;
    }

    public void setAlephUrl(String alephUrl) {
        this.alephUrl = alephUrl;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getSysno() {
        return sysno;
    }

    public void setSysno(String sysno) {
        this.sysno = sysno;
    }

    public MetadataBundle getBundle() {
        return bundle;
    }

    /**
     * @return the dateIssued
     */

    public String getDateIssued() {
        return dateIssued;
    }

    /**
     * @param dateIssued
     *        the dateIssued to set
     */

    public void setDateIssued(String dateIssued) {
        this.dateIssued = dateIssued;
    }

    /**
     * @return the pageScanIndex
     */

    public int getPageScanIndex() {
        return pageScanIndex;
    }

    /**
     * @param pageScanIndex
     *        the pageScanIndex to set
     */

    public void setPageScanIndex(int pageScanIndex) {
        this.pageScanIndex = pageScanIndex;
    }

    public void setBundle(MetadataBundle bundle) {
        this.bundle = bundle;
        if (getBundle() == null || getBundle().getMarc() == null) {
            setSysno("-1");
        } else {
            setSysno(getBundle().getMarc().getSysno());
        };
    }

    protected String transformLanguage(String originalLang) {
        if ("d".equals(originalLang)) {
            return "ger";
        }
        return originalLang;
    }

    private ModsTypeClient getFirstMods() {
        if (getBundle() != null && getBundle().getMods() != null && getBundle().getMods().getMods() != null
                && getBundle().getMods().getMods().size() > 0) {
            return getBundle().getMods().getMods().get(0);
        }
        return null;
    }

    protected String getTypeOfResource() {
        String typeOfResourceValue = null;

        ModsTypeClient mods = getFirstMods();
        if (mods != null && mods.getTypeOfResource() != null && mods.getTypeOfResource().size() > 0
                && mods.getTypeOfResource().get(0).getValue() != null) {
            typeOfResourceValue = mods.getTypeOfResource().get(0).getValue();
        }

        return typeOfResourceValue;
    }

    protected String getLanguage() {
        String language = null;

        ModsTypeClient mods = getFirstMods();
        if (mods != null && mods.getLanguage() != null && mods.getLanguage().size() > 0
                && mods.getLanguage().get(0).getLanguageTerm() != null
                && mods.getLanguage().get(0).getLanguageTerm().size() > 0) {
            language = mods.getLanguage().get(0).getLanguageTerm().get(0).getValue();
        }

        return language;
    }
}
