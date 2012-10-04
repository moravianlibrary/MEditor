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

package cz.mzk.editor.server.newObject;

import java.io.ByteArrayOutputStream;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.QName;
import org.dom4j.XPath;

import cz.mzk.editor.client.mods.ModsTypeClient;
import cz.mzk.editor.client.mods.PlaceTypeClient;
import cz.mzk.editor.client.mods.StringPlusAuthorityPlusTypeClient;
import cz.mzk.editor.client.mods.TitleInfoTypeClient;
import cz.mzk.editor.client.util.ClientCreateUtils;
import cz.mzk.editor.client.util.Constants;
import cz.mzk.editor.client.util.Constants.DATASTREAM_CONTROLGROUP;
import cz.mzk.editor.client.util.Constants.DATASTREAM_ID;
import cz.mzk.editor.server.config.EditorConfiguration;
import cz.mzk.editor.server.fedora.utils.Dom4jUtils;
import cz.mzk.editor.server.fedora.utils.Dom4jUtils.PrintType;
import cz.mzk.editor.server.fedora.utils.FoxmlUtils;
import cz.mzk.editor.shared.domain.DigitalObjectModel;
import cz.mzk.editor.shared.domain.FedoraNamespaces;
import cz.mzk.editor.shared.rpc.MetadataBundle;
import cz.mzk.editor.shared.rpc.NewDigitalObject;

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
    private String name;
    private String type;
    private int pageIndex;
    private Policy policy = Policy.PRIVATE;
    private final List<RelsExtRelation> children;
    private MetadataBundle bundle;
    private Document document;
    protected Element rootElement;
    private Document dcXmlContent;
    private Document modsXmlContent;
    private Document relsExtXmlContent;
    private Element policyContentLocation;

    private String alephUrl;
    private String imageUrl;

    private String dateOrIntPartName;
    private String noteOrIntSubtitle;
    private String partNumber;
    private String aditionalInfo;

    private String base;

    @Inject
    private EditorConfiguration configuration;

    private static final Boolean VERSIONABLE = true;

    private final XPath shelfLocatorXpath = Dom4jUtils.createXPath("mods:shelfLocator");

    public FoxmlBuilder(NewDigitalObject object) {
        this(object, Policy.PRIVATE);
    }

    public FoxmlBuilder(NewDigitalObject object, Policy policy) {
        setLabel(object);
        this.children = new ArrayList<RelsExtRelation>();
        this.policy = policy;
    }

    private void setLabel(NewDigitalObject object) {
        String labelToAdd = "";
        if (object.getModel() == DigitalObjectModel.PERIODICALITEM && !isNotNullOrEmpty(object.getName())) {
            labelToAdd = object.getPartNumberOrAlto();
            this.name = "";
        } else {
            if (isNotNullOrEmpty(object.getName())) {
                labelToAdd = object.getName();
            } else {
                labelToAdd = "untitled";
            }
            this.name = labelToAdd;
        }
        if (!isNotNullOrEmpty(labelToAdd)) {
            labelToAdd = "untitled";
        }
        this.label = ClientCreateUtils.trimLabel(labelToAdd, Constants.MAX_LABEL_LENGTH);
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
        type.addText("model:" + getModel().getValue().substring(0, 1)
                + getModel().getValue().substring(1).toLowerCase());
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

    public String getName() {
        return name;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type
     *        the type to set
     */
    public void setType(String type) {
        this.type = type;
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
     * @return the pageIndex
     */

    public int getPageIndex() {
        return pageIndex;
    }

    /**
     * @return the dateOrIntPartName
     */
    public String getDateOrIntPartName() {
        return dateOrIntPartName;
    }

    /**
     * @param dateOrIntPartName
     *        the dateOrIntPartName to set
     */
    public void setDateOrIntPartName(String dateOrIntPartName) {
        this.dateOrIntPartName = dateOrIntPartName;
    }

    /**
     * @return the noteOrIntSubtitle
     */
    public String getNoteOrIntSubtitle() {
        return noteOrIntSubtitle;
    }

    /**
     * @param noteOrIntSubtitle
     *        the noteOrIntSubtitle to set
     */
    public void setNoteOrIntSubtitle(String noteOrIntSubtitle) {
        this.noteOrIntSubtitle = noteOrIntSubtitle;
    }

    /**
     * @return the partNumber
     */
    public String getPartNumber() {
        return partNumber;
    }

    /**
     * @param partNumber
     *        the partNumber to set
     */
    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }

    /**
     * @return the aditionalInfo
     */
    public String getAditionalInfo() {
        return aditionalInfo;
    }

    /**
     * @return the base
     */
    public String getBase() {
        return base;
    }

    /**
     * @param base
     *        the base to set
     */
    public void setBase(String base) {
        this.base = base;
    }

    /**
     * @param aditionalInfo
     *        the aditionalInfo to set
     */
    public void setAditionalInfo(String aditionalInfo) {
        this.aditionalInfo = aditionalInfo;
    }

    /**
     * @param pageIndex
     *        the pageIndex to set
     */

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
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

    protected void addRootLanguage(Element modsRootEl) {
        ModsTypeClient mods = getFirstMods();
        if (mods != null && mods.getLanguage() != null && mods.getLanguage().size() > 0
                && mods.getLanguage().get(0).getLanguageTerm() != null
                && mods.getLanguage().get(0).getLanguageTerm().size() > 0
                && mods.getLanguage().get(0).getLanguageTerm().get(0) != null
                && isNotNullOrEmpty(mods.getLanguage().get(0).getLanguageTerm().get(0).getValue())) {

            Element languageEl = modsRootEl.addElement(new QName("language", Namespaces.mods));
            Element languageTerm = languageEl.addElement(new QName("languageTerm", Namespaces.mods));
            languageTerm.addAttribute("type", "code");
            languageTerm.addAttribute("authority", "iso639-2b");
            languageTerm.addText(mods.getLanguage().get(0).getLanguageTerm().get(0).getValue());
        }
    }

    private TitleInfoTypeClient getFirstTitleInfo() {
        ModsTypeClient mods = getFirstMods();
        if (mods != null && mods.getTitleInfo() != null && mods.getTitleInfo().size() > 0
                && mods.getTitleInfo().get(0) != null) return mods.getTitleInfo().get(0);
        return null;
    }

    protected void addRootTitle(Element titleInfo) {
        TitleInfoTypeClient firstTitleInfo = getFirstTitleInfo();
        if (firstTitleInfo != null && firstTitleInfo.getTitle() != null
                && firstTitleInfo.getTitle().size() > 0 && isNotNullOrEmpty(firstTitleInfo.getTitle().get(0))) {
            titleInfo.addElement(new QName("title", Namespaces.mods)).addText(firstTitleInfo.getTitle()
                    .get(0));
        }
    }

    protected void addRootSubtitle(Element titleInfoEl) {
        TitleInfoTypeClient firstTitleInfo = getFirstTitleInfo();
        if (firstTitleInfo != null && firstTitleInfo.getSubTitle() != null
                && firstTitleInfo.getSubTitle().size() > 0
                && isNotNullOrEmpty(firstTitleInfo.getSubTitle().get(0))) {
            titleInfoEl.addElement(new QName("subtitle", Namespaces.mods)).addText(firstTitleInfo
                    .getSubTitle().get(0));
        }
    }

    protected boolean isNotNullOrEmpty(String string) {
        return string != null && !"".equals(string);
    }

    protected void addIdentifierUuid(Element modsRootElement, String uuid) {
        Element identifier = modsRootElement.addElement(new QName("identifier", Namespaces.mods));
        identifier.addAttribute("type", "uuid");
        identifier.addText("uuid:" + uuid);
    }

    protected void addRootPlace(Element originInfoEl) {
        ModsTypeClient mods = getFirstMods();
        PlaceTypeClient placeClient = null;
        if (mods != null && mods.getOriginInfo() != null && mods.getOriginInfo().size() > 0
                && mods.getOriginInfo().get(0).getPlace() != null
                && mods.getOriginInfo().get(0).getPlace().size() > 0)
            placeClient = mods.getOriginInfo().get(0).getPlace().get(0);

        if (placeClient != null && placeClient.getPlaceTerm() != null
                && placeClient.getPlaceTerm().size() > 0) {
            String authority = placeClient.getPlaceTerm().get(0).getAuthority().value();
            String type = placeClient.getPlaceTerm().get(0).getType().value();
            String place = placeClient.getPlaceTerm().get(0).getValue();
            if (authority != null && type != null && place != null) {
                Element placeEl = originInfoEl.addElement(new QName("place", Namespaces.mods));
                Element placeTermEl = placeEl.addElement(new QName("placeTerm", Namespaces.mods));
                placeTermEl.addAttribute("type", type);
                placeTermEl.addAttribute("authority", authority);
                placeTermEl.addText(place);
            }
        }
    }

    protected void addRootPublisher(Element originInfoEl) {
        if (getBundle().getMarc() != null) {
            String name = getBundle().getMarc().getPublisher();
            if (isNotNullOrEmpty(name)) {
                Element publisher = originInfoEl.addElement(new QName("publisher", Namespaces.mods));
                publisher.addText(name);
            }
        }
    }

    protected Element addRootPhysicalDescriptionForm(Element modsRootEl) {
        ModsTypeClient mods = getFirstMods();
        List<StringPlusAuthorityPlusTypeClient> physDescForms = null;
        Element physDescEl = modsRootEl.addElement(new QName("physicalDescription", Namespaces.mods));

        if (mods != null && mods.getPhysicalDescription().size() > 0
                && mods.getPhysicalDescription().get(0).getForm() != null
                && mods.getPhysicalDescription().get(0).getForm().size() > 0) {
            physDescForms = mods.getPhysicalDescription().get(0).getForm();

            for (StringPlusAuthorityPlusTypeClient physDescForm : physDescForms) {
                String authority = physDescForm.getAuthority();
                String form = physDescForm.getValue();
                if (authority != null && form != null) {
                    Element formEl = physDescEl.addElement(new QName("form", Namespaces.mods));
                    formEl.addAttribute("authority", authority);
                    formEl.addText(form);
                }
            }
        }
        return physDescEl;
    }

    protected void addLocation(Element locationEl) {
        Element url = locationEl.addElement(new QName("url", Namespaces.mods));
        url.addText(configuration.getKrameriusHost() + "/handle/uuid:" + uuid);
    }

    protected void addRootRecordInfo(Element recordInfoEl) {
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

        String nowStr = sdf.format(now);
        Element creationDate = recordInfoEl.addElement(new QName("recordCreationDate", Namespaces.mods));
        creationDate.addAttribute("encoding", "iso8601");
        creationDate.addText(nowStr);
        Element changeDate = recordInfoEl.addElement(new QName("recordChangeDate", Namespaces.mods));
        changeDate.addAttribute("encoding", "iso8601");
        changeDate.addText(nowStr);

    }

    protected void addRootTopic(Element modsRootEl) {
        if (getBundle().getMarc() != null) {
            String topic = getBundle().getMarc().getTopic();
            if (topic != null) {
                Element subjectEl = modsRootEl.addElement(new QName("subject", Namespaces.mods));
                Element topicEl = subjectEl.addElement(new QName("topic", Namespaces.mods));
                topicEl.addText(topic);
            }
        }
    }

    protected void addRootPhysicalLocation(Element locationEl, boolean doDetach) {
        if (getBundle().getMarc() != null) {
            String location = getBundle().getMarc().getLocation();

            if (location != null) {
                //            Element shelfLocatorEl = (Element) shelfLocatorXpath.selectSingleNode(locationEl);
                //            String shelfLocatorStr = "";
                //            if (shelfLocatorEl != null) {
                //                shelfLocatorStr = shelfLocatorEl.getTextTrim();
                //                if (doDetach) shelfLocatorEl.detach();
                //            }
                Element physicalLocationEl =
                        locationEl.addElement(new QName("physicalLocation", Namespaces.mods));
                physicalLocationEl.addText(location);
                //            shelfLocatorEl = locationEl.addElement(new QName("shelfLocator", Namespaces.mods));
                //            shelfLocatorEl.addText(shelfLocatorStr);
            }
        }
    }

    protected void addRootUdcOrDdc(Element modsRootEl) {
        if (getBundle().getMarc() != null) {
            if (getBundle().getMarc().getUdcs() == null) {
                return;
            }
            List<String> udcs = getBundle().getMarc().getUdcs();
            for (String udc : udcs) {
                Element classificationEl =
                        modsRootEl.addElement(new QName("classification", Namespaces.mods));
                classificationEl.addAttribute("authority", "udc");
                classificationEl.addText(udc);
            }
        }
    }
}
