/*
 * Metadata Editor
 * @author Jiri Kremser
 * 
 * 
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

package cz.fi.muni.xkremser.editor.server.fedora.utils;

import java.io.StringWriter;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.log4j.Logger;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import cz.fi.muni.xkremser.editor.client.domain.DigitalObjectModel;
import cz.fi.muni.xkremser.editor.client.mods.AbstractTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.AccessConditionTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.BaseDateTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.ClassificationTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.CodeOrTextClient;
import cz.fi.muni.xkremser.editor.client.mods.CopyInformationTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.DateOtherTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.DateTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.DetailTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.EnumerationAndChronologyTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.ExtensionTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.ExtentTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.GenreTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.HierarchicalGeographicTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.HoldingSimpleTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.IdentifierTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.LanguageTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.LanguageTypeClient.LanguageTermClient;
import cz.fi.muni.xkremser.editor.client.mods.LocationTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.ModsCollectionClient;
import cz.fi.muni.xkremser.editor.client.mods.ModsTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.NamePartTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.NameTypeAttributeClient;
import cz.fi.muni.xkremser.editor.client.mods.NameTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.NoteTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.OriginInfoTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.PartTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.PhysicalDescriptionTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.PhysicalLocationTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.PlaceAuthorityClient;
import cz.fi.muni.xkremser.editor.client.mods.PlaceTermTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.PlaceTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.RecordInfoTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.RecordInfoTypeClient.RecordIdentifierClient;
import cz.fi.muni.xkremser.editor.client.mods.RelatedItemTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.RoleTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.RoleTypeClient.RoleTermClient;
import cz.fi.muni.xkremser.editor.client.mods.StringPlusAuthorityClient;
import cz.fi.muni.xkremser.editor.client.mods.StringPlusAuthorityPlusLanguageClient;
import cz.fi.muni.xkremser.editor.client.mods.StringPlusAuthorityPlusTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.StringPlusAuthorityPlusTypePlusLanguageClient;
import cz.fi.muni.xkremser.editor.client.mods.StringPlusDisplayLabelClient;
import cz.fi.muni.xkremser.editor.client.mods.StringPlusDisplayLabelPlusTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.SubjectTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.SubjectTypeClient.CartographicsClient;
import cz.fi.muni.xkremser.editor.client.mods.SubjectTypeClient.GeographicCodeClient;
import cz.fi.muni.xkremser.editor.client.mods.TableOfContentsTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.TargetAudienceTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.TitleInfoTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.TypeOfResourceTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.UnstructuredTextClient;
import cz.fi.muni.xkremser.editor.client.mods.UrlTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.YesClient;

import cz.fi.muni.xkremser.editor.server.fedora.FedoraNamespaceContext;
import cz.fi.muni.xkremser.editor.server.mods.AbstractType;
import cz.fi.muni.xkremser.editor.server.mods.AccessConditionType;
import cz.fi.muni.xkremser.editor.server.mods.BaseDateType;
import cz.fi.muni.xkremser.editor.server.mods.ClassificationType;
import cz.fi.muni.xkremser.editor.server.mods.CodeOrText;
import cz.fi.muni.xkremser.editor.server.mods.CopyInformationType;
import cz.fi.muni.xkremser.editor.server.mods.DateOtherType;
import cz.fi.muni.xkremser.editor.server.mods.DateType;
import cz.fi.muni.xkremser.editor.server.mods.DetailType;
import cz.fi.muni.xkremser.editor.server.mods.EnumerationAndChronologyType;
import cz.fi.muni.xkremser.editor.server.mods.ExtensionType;
import cz.fi.muni.xkremser.editor.server.mods.ExtentType;
import cz.fi.muni.xkremser.editor.server.mods.GenreType;
import cz.fi.muni.xkremser.editor.server.mods.HierarchicalGeographicType;
import cz.fi.muni.xkremser.editor.server.mods.HoldingSimpleType;
import cz.fi.muni.xkremser.editor.server.mods.IdentifierType;
import cz.fi.muni.xkremser.editor.server.mods.LanguageType;
import cz.fi.muni.xkremser.editor.server.mods.LanguageType.LanguageTerm;
import cz.fi.muni.xkremser.editor.server.mods.LocationType;
import cz.fi.muni.xkremser.editor.server.mods.ModsCollection;
import cz.fi.muni.xkremser.editor.server.mods.ModsType;
import cz.fi.muni.xkremser.editor.server.mods.NamePartType;
import cz.fi.muni.xkremser.editor.server.mods.NameType;
import cz.fi.muni.xkremser.editor.server.mods.NameTypeAttribute;
import cz.fi.muni.xkremser.editor.server.mods.NoteType;
import cz.fi.muni.xkremser.editor.server.mods.ObjectFactory;
import cz.fi.muni.xkremser.editor.server.mods.OriginInfoType;
import cz.fi.muni.xkremser.editor.server.mods.PartType;
import cz.fi.muni.xkremser.editor.server.mods.PhysicalDescriptionType;
import cz.fi.muni.xkremser.editor.server.mods.PhysicalLocationType;
import cz.fi.muni.xkremser.editor.server.mods.PlaceAuthority;
import cz.fi.muni.xkremser.editor.server.mods.PlaceTermType;
import cz.fi.muni.xkremser.editor.server.mods.PlaceType;
import cz.fi.muni.xkremser.editor.server.mods.RecordInfoType;
import cz.fi.muni.xkremser.editor.server.mods.RecordInfoType.RecordIdentifier;
import cz.fi.muni.xkremser.editor.server.mods.RelatedItemType;
import cz.fi.muni.xkremser.editor.server.mods.RoleType;
import cz.fi.muni.xkremser.editor.server.mods.RoleType.RoleTerm;
import cz.fi.muni.xkremser.editor.server.mods.StringPlusAuthority;
import cz.fi.muni.xkremser.editor.server.mods.StringPlusAuthorityPlusLanguage;
import cz.fi.muni.xkremser.editor.server.mods.StringPlusAuthorityPlusType;
import cz.fi.muni.xkremser.editor.server.mods.StringPlusAuthorityPlusTypePlusLanguage;
import cz.fi.muni.xkremser.editor.server.mods.StringPlusDisplayLabel;
import cz.fi.muni.xkremser.editor.server.mods.StringPlusDisplayLabelPlusType;
import cz.fi.muni.xkremser.editor.server.mods.SubjectType;
import cz.fi.muni.xkremser.editor.server.mods.SubjectType.Cartographics;
import cz.fi.muni.xkremser.editor.server.mods.SubjectType.GeographicCode;
import cz.fi.muni.xkremser.editor.server.mods.TableOfContentsType;
import cz.fi.muni.xkremser.editor.server.mods.TargetAudienceType;
import cz.fi.muni.xkremser.editor.server.mods.TitleInfoType;
import cz.fi.muni.xkremser.editor.server.mods.TypeOfResourceType;
import cz.fi.muni.xkremser.editor.server.mods.UnstructuredText;
import cz.fi.muni.xkremser.editor.server.mods.UrlType;
import cz.fi.muni.xkremser.editor.server.mods.Yes;

// TODO: Auto-generated Javadoc
/**
 * The Class BiblioModsUtils.
 */
public final class BiblioModsUtils {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(BiblioModsUtils.class);

    /** The Constant factory. */
    private static final ObjectFactory factory = new ObjectFactory();

    /**
     * Gets the page number.
     * 
     * @param doc
     *        the doc
     * @return the page number
     */
    public static String getPageNumber(Document doc) {
        try {
            XPathFactory xpfactory = XPathFactory.newInstance();
            XPath xpath = xpfactory.newXPath();
            xpath.setNamespaceContext(new FedoraNamespaceContext());
            XPathExpression expr =
                    xpath.compile("//mods:mods/mods:part/mods:detail[@type='pageNumber']/mods:number/text()");
            Object pageNumber = expr.evaluate(doc, XPathConstants.STRING);
            return (String) pageNumber;
        } catch (XPathExpressionException e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        } catch (DOMException e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * Gets the title.
     * 
     * @param doc
     *        the doc
     * @param model
     *        the model
     * @return the title
     */
    public static String getTitle(Document doc, DigitalObjectModel model) {
        String title = titleFromBiblioMods(doc);
        if ((title == null) || (title.equals(""))) {
            switch (model) {
                case PERIODICALITEM:
                    return PeriodicalItemUtils.getItemNumber(doc) + " (" + PeriodicalItemUtils.getDate(doc)
                            + ")";
                case PERIODICALVOLUME:
                    return PeriodicalItemUtils.getItemNumber(doc) + " (" + PeriodicalItemUtils.getDate(doc)
                            + ")";
                default:
                    throw new UnsupportedOperationException("'" + model + "'");
            }
        } else
            return title;
    }

    /**
     * Title from biblio mods.
     * 
     * @param doc
     *        the doc
     * @return the string
     */
    public static String titleFromBiblioMods(Document doc) {
        try {
            XPathFactory xpfactory = XPathFactory.newInstance();
            XPath xpath = xpfactory.newXPath();
            xpath.setNamespaceContext(new FedoraNamespaceContext());
            XPathExpression expr = xpath.compile("//mods:titleInfo/mods:title");
            Node node = (Node) expr.evaluate(doc, XPathConstants.NODE);
            if (node != null) {
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element elm = (Element) node;
                    return elm.getTextContent();
                } else
                    return null;
            } else
                return null;
        } catch (XPathExpressionException e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        } catch (DOMException e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * Gets the mods.
     * 
     * @param doc
     *        the doc
     * @return the mods
     */
    public static ModsCollection getMods(org.w3c.dom.Document doc) {
        ModsCollection collection = null;
        try {
            JAXBContext jc = JAXBContext.newInstance("cz.fi.muni.xkremser.editor.server.mods");
            Unmarshaller unmarshaller = jc.createUnmarshaller();
            collection = (ModsCollection) unmarshaller.unmarshal(doc);
        } catch (JAXBException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return collection;
    }

    /**
     * To xml.
     * 
     * @param collection
     *        the collection
     * @return the string
     */
    public static String toXML(ModsCollection collection) {
        StringWriter sw = null;
        try {
            JAXBContext jc = JAXBContext.newInstance("cz.fi.muni.xkremser.editor.server.mods");
            sw = new StringWriter();
            Marshaller marshaller = jc.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            // marshaller.setProperty("com.sun.xml.bind.namespacePrefixMapper", new
            // MyNamespacePrefixMapper());
            marshaller.marshal(collection, sw);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return sw == null ? null : sw.toString();
    }

    /**
     * To mods client.
     * 
     * @param mods
     *        the mods
     * @return the mods collection client
     */
    public static ModsCollectionClient toModsClient(ModsCollection mods) {
        if (mods == null) throw new NullPointerException("mods");
        ModsCollectionClient modsClient = new ModsCollectionClient();
        List<ModsType> modss = mods.getMods();
        if (modss != null && modss.size() != 0) {
            List<ModsTypeClient> modsClientList = new ArrayList<ModsTypeClient>(modss.size());
            for (ModsType modsType : modss) {
                modsClientList.add(toModsClient(modsType));
            }
            modsClient.setMods(modsClientList);
        }
        return modsClient;
    }

    /**
     * To mods server.
     * 
     * @param modsClient
     *        the mods client
     * @return the mods collection
     */
    public static ModsCollection toModsServer(ModsCollectionClient modsClient) {
        return null;

    }

    /**
     * To mods client.
     * 
     * @param titleInfoType
     *        the title info type
     * @return the title info type client
     */
    private static TitleInfoTypeClient toModsClient(TitleInfoType titleInfoType) {
        if (titleInfoType == null) return null;
        TitleInfoTypeClient titleInfoTypeClient = new TitleInfoTypeClient();
        titleInfoTypeClient.setType(titleInfoType.getAtType());
        titleInfoTypeClient.setDisplayLabel(titleInfoType.getDisplayLabel());
        titleInfoTypeClient.setId(titleInfoType.getID());
        titleInfoTypeClient.setAuthority(titleInfoType.getAuthority());
        titleInfoTypeClient.setXlink(titleInfoType.getHref());
        titleInfoTypeClient.setXmlLang(titleInfoType.getXmlLang());
        titleInfoTypeClient.setLang(titleInfoType.getLang());
        titleInfoTypeClient.setScript(titleInfoType.getScript());
        titleInfoTypeClient.setTransliteration(titleInfoType.getTransliteration());
        List<JAXBElement<String>> subElements = titleInfoType.getTitleOrSubTitleOrPartNumber();
        for (JAXBElement<String> subElement : subElements) {
            if (subElement.getName().getLocalPart().equals("nonSort")) {
                if (titleInfoTypeClient.getNonSort() == null) {
                    titleInfoTypeClient.setNonSort(new ArrayList<String>());
                }
                titleInfoTypeClient.getNonSort().add(subElement.getValue());
            } else if (subElement.getName().getLocalPart().equals("partNumber")) {
                if (titleInfoTypeClient.getPartNumber() == null) {
                    titleInfoTypeClient.setPartNumber(new ArrayList<String>());
                }
                titleInfoTypeClient.getPartNumber().add(subElement.getValue());
            } else if (subElement.getName().getLocalPart().equals("subTitle")) {
                if (titleInfoTypeClient.getSubTitle() == null) {
                    titleInfoTypeClient.setSubTitle(new ArrayList<String>());
                }
                titleInfoTypeClient.getSubTitle().add(subElement.getValue());
            } else if (subElement.getName().getLocalPart().equals("partName")) {
                if (titleInfoTypeClient.getPartName() == null) {
                    titleInfoTypeClient.setPartName(new ArrayList<String>());
                }
                titleInfoTypeClient.getPartName().add(subElement.getValue());
            } else if (subElement.getName().getLocalPart().equals("title")) {
                if (titleInfoTypeClient.getTitle() == null) {
                    titleInfoTypeClient.setTitle(new ArrayList<String>());
                }
                titleInfoTypeClient.getTitle().add(subElement.getValue());
            }
        }
        return titleInfoTypeClient;
    }

    /**
     * To mods client.
     * 
     * @param nameType
     *        the name type
     * @return the name type client
     */
    private static NameTypeClient toModsClient(NameType nameType) {
        if (nameType == null) return null;
        NameTypeClient nameTypeClient = new NameTypeClient();
        nameTypeClient.setId(nameType.getID());
        nameTypeClient.setAuthority(nameType.getAuthority());
        nameTypeClient.setXlink(nameType.getHref());
        nameTypeClient.setXmlLang(nameType.getXmlLang());
        nameTypeClient.setLang(nameType.getLang());
        nameTypeClient.setScript(nameType.getScript());
        nameTypeClient.setTransliteration(nameType.getTransliteration());
        if (nameType.getAtType() != null)
            nameTypeClient.setType(NameTypeAttributeClient.fromValue(nameType.getAtType().value()));

        List<JAXBElement<?>> subElements = nameType.getNamePartOrDisplayFormOrAffiliation();
        for (JAXBElement<?> subElement : subElements) {
            if (subElement.getName().getLocalPart().equals("description")) {
                if (nameTypeClient.getDescription() == null) {
                    nameTypeClient.setDescription(new ArrayList<String>());
                }
                nameTypeClient.getDescription().add((String) subElement.getValue());
            } else if (subElement.getName().getLocalPart().equals("role")) {
                RoleType roleType = (RoleType) subElement.getValue();
                if (roleType != null) {
                    if (nameTypeClient.getRole() == null) {
                        nameTypeClient.setRole(new ArrayList<RoleTypeClient>());
                    }
                    RoleTypeClient roleClient = new RoleTypeClient();
                    List<RoleTermClient> roleTermClientList = null;
                    List<RoleTerm> roleTerms = roleType.getRoleTerm();
                    if (roleTerms != null && roleTerms.size() != 0) {
                        roleTermClientList = new ArrayList<RoleTermClient>(roleTerms.size());
                        for (RoleTerm roleTerm : roleTerms) {
                            RoleTermClient roleTermClient = new RoleTermClient();
                            roleTermClient.setAuthority(roleTerm.getAuthority());
                            roleTermClient.setValue(roleTerm.getValue());
                            if (roleTerm.getType() != null)
                                roleTermClient
                                        .setType(CodeOrTextClient.fromValue(roleTerm.getType().value()));
                            roleTermClientList.add(roleTermClient);
                        }
                    }
                    roleClient.setRoleTerm(roleTermClientList);
                    nameTypeClient.getRole().add(roleClient);
                }

            } else if (subElement.getName().getLocalPart().equals("displayForm")) {
                if (nameTypeClient.getDisplayForm() == null) {
                    nameTypeClient.setDisplayForm(new ArrayList<String>());
                }
                nameTypeClient.getDisplayForm().add((String) subElement.getValue());
            } else if (subElement.getName().getLocalPart().equals("namePart")) {
                if (nameTypeClient.getNamePart() == null) {
                    nameTypeClient.setNamePart(new ArrayList<NamePartTypeClient>());
                }
                NamePartType namePart = (NamePartType) subElement.getValue();
                NamePartTypeClient namePartClient = new NamePartTypeClient();
                namePartClient.setType(namePart.getType());
                namePartClient.setValue(namePart.getValue());
                nameTypeClient.getNamePart().add(namePartClient);
            } else if (subElement.getName().getLocalPart().equals("affiliation")) {
                if (nameTypeClient.getAffiliation() == null) {
                    nameTypeClient.setAffiliation(new ArrayList<String>());
                }
                nameTypeClient.getAffiliation().add((String) subElement.getValue());
            }
        }
        return nameTypeClient;
    }

    /**
     * To mods client.
     * 
     * @param typeOfResource
     *        the type of resource
     * @return the type of resource type client
     */
    private static TypeOfResourceTypeClient toModsClient(TypeOfResourceType typeOfResource) {
        if (typeOfResource == null) return null;
        TypeOfResourceTypeClient typeOfResourceClient = new TypeOfResourceTypeClient();
        typeOfResourceClient.setValue(typeOfResource.getValue());
        if (typeOfResource.getCollection() != null)
            typeOfResourceClient.setCollection(YesClient.fromValue(typeOfResource.getCollection().value()));
        if (typeOfResource.getManuscript() != null)
            typeOfResourceClient.setManuscript(YesClient.fromValue(typeOfResource.getManuscript().value()));
        return typeOfResourceClient;
    }

    /**
     * To mods client.
     * 
     * @param stringPlusDisplayLabel
     *        the string plus display label
     * @param clazz
     *        the clazz
     * @return the string plus display label client
     */
    private static StringPlusDisplayLabelClient toModsClient(StringPlusDisplayLabel stringPlusDisplayLabel,
                                                             Class<? extends StringPlusDisplayLabelClient> clazz) {
        if (stringPlusDisplayLabel == null) return null;
        StringPlusDisplayLabelClient stringPlusDisplayLabelClient = null;
        try {
            stringPlusDisplayLabelClient = clazz.newInstance();
        } catch (InstantiationException e) {
            return null;
        } catch (IllegalAccessException e) {
            return null;
        }
        stringPlusDisplayLabelClient.setValue(stringPlusDisplayLabel.getValue());
        stringPlusDisplayLabelClient.setDisplayLabel(stringPlusDisplayLabel.getDisplayLabel());

        if (stringPlusDisplayLabelClient instanceof StringPlusDisplayLabelPlusTypeClient) {
            decorate((StringPlusDisplayLabelPlusTypeClient) stringPlusDisplayLabelClient,
                     (StringPlusDisplayLabelPlusType) stringPlusDisplayLabel);
        }
        return stringPlusDisplayLabelClient;
    }

    /**
     * Decorate.
     * 
     * @param stringPlusDisplayLabelClient
     *        the string plus display label client
     * @param stringPlusDisplayLabel
     *        the string plus display label
     */
    private static void decorate(StringPlusDisplayLabelPlusTypeClient stringPlusDisplayLabelClient,
                                 StringPlusDisplayLabelPlusType stringPlusDisplayLabel) {
        if (stringPlusDisplayLabelClient == null || stringPlusDisplayLabel == null) return;
        stringPlusDisplayLabelClient.setAtType(stringPlusDisplayLabel.getAtType());
    }

    /**
     * To mods client.
     * 
     * @param stringPlusAuthority
     *        the string plus authority
     * @param clazz
     *        the clazz
     * @return the string plus authority client
     */
    private static StringPlusAuthorityClient toModsClient(StringPlusAuthority stringPlusAuthority,
                                                          Class<? extends StringPlusAuthorityClient> clazz) {
        if (stringPlusAuthority == null) return null;
        StringPlusAuthorityClient stringPlusAuthorityClient = null;
        try {
            stringPlusAuthorityClient = clazz.newInstance();
        } catch (InstantiationException e) {
            return null;
        } catch (IllegalAccessException e) {
            return null;
        }
        stringPlusAuthorityClient.setAuthority(stringPlusAuthority.getAuthority());
        stringPlusAuthorityClient.setValue(stringPlusAuthority.getValue());
        if (stringPlusAuthority instanceof StringPlusAuthorityPlusLanguage) {
            decorate((StringPlusAuthorityPlusLanguageClient) stringPlusAuthorityClient,
                     (StringPlusAuthorityPlusLanguage) stringPlusAuthority);
        }
        if (stringPlusAuthority instanceof StringPlusAuthorityPlusType) {
            decorate((StringPlusAuthorityPlusTypeClient) stringPlusAuthorityClient,
                     (StringPlusAuthorityPlusType) stringPlusAuthority);
        }
        if (stringPlusAuthority instanceof StringPlusAuthorityPlusTypePlusLanguage) {
            decorate((StringPlusAuthorityPlusTypePlusLanguageClient) stringPlusAuthorityClient,
                     (StringPlusAuthorityPlusTypePlusLanguage) stringPlusAuthority);
        }
        return stringPlusAuthorityClient;
    }

    /**
     * Decorate.
     * 
     * @param stringPlusAuthorityPlusLanguageClient
     *        the string plus authority plus language client
     * @param stringPlusAuthorityPlusLanguage
     *        the string plus authority plus language
     */
    private static void decorate(StringPlusAuthorityPlusLanguageClient stringPlusAuthorityPlusLanguageClient,
                                 StringPlusAuthorityPlusLanguage stringPlusAuthorityPlusLanguage) {
        if (stringPlusAuthorityPlusLanguage == null || stringPlusAuthorityPlusLanguageClient == null) return;
        stringPlusAuthorityPlusLanguageClient.setXmlLang(stringPlusAuthorityPlusLanguage.getXmlLang());
        stringPlusAuthorityPlusLanguageClient.setLang(stringPlusAuthorityPlusLanguage.getLang());
        stringPlusAuthorityPlusLanguageClient.setScript(stringPlusAuthorityPlusLanguage.getScript());
        stringPlusAuthorityPlusLanguageClient.setTransliteration(stringPlusAuthorityPlusLanguage
                .getTransliteration());
        stringPlusAuthorityPlusLanguageClient.setValue(stringPlusAuthorityPlusLanguage.getValue());
    }

    private static void decorate(StringPlusAuthorityPlusTypeClient stringPlusAuthorityPlusTypeClient,
                                 StringPlusAuthorityPlusType stringPlusAuthorityPlusType) {
        if (stringPlusAuthorityPlusType == null || stringPlusAuthorityPlusTypeClient == null) return;
        stringPlusAuthorityPlusTypeClient.setValue(stringPlusAuthorityPlusType.getValue());
        stringPlusAuthorityPlusTypeClient.setAuthority(stringPlusAuthorityPlusType.getAuthority());
        stringPlusAuthorityPlusTypeClient.setType(stringPlusAuthorityPlusType.getType());
    }

    /**
     * Decorate.
     * 
     * @param stringPlusAuthorityPlusTypePlusLanguageClient
     *        the string plus authority plus type plus language client
     * @param stringPlusAuthorityPlusTypePlusLanguage
     *        the string plus authority plus type plus language
     */
    private static void decorate(StringPlusAuthorityPlusTypePlusLanguageClient stringPlusAuthorityPlusTypePlusLanguageClient,
                                 StringPlusAuthorityPlusTypePlusLanguage stringPlusAuthorityPlusTypePlusLanguage) {
        if (stringPlusAuthorityPlusTypePlusLanguage == null
                || stringPlusAuthorityPlusTypePlusLanguageClient == null) return;
        stringPlusAuthorityPlusTypePlusLanguageClient.setType(stringPlusAuthorityPlusTypePlusLanguage
                .getAtType());
        stringPlusAuthorityPlusTypePlusLanguageClient.setValue(stringPlusAuthorityPlusTypePlusLanguage
                .getValue());
    }

    /**
     * To mods client.
     * 
     * @param unstructuredText
     *        the unstructured text
     * @param clazz
     *        the clazz
     * @return the unstructured text client
     */
    private static UnstructuredTextClient toModsClient(UnstructuredText unstructuredText,
                                                       Class<? extends UnstructuredTextClient> clazz) {
        if (unstructuredText == null) return null;
        UnstructuredTextClient unstructuredTextClient = null;
        try {
            unstructuredTextClient = clazz.newInstance();
        } catch (InstantiationException e) {
            return null;
        } catch (IllegalAccessException e) {
            return null;
        }
        unstructuredTextClient.setXmlLang(unstructuredText.getXmlLang());
        unstructuredTextClient.setLang(unstructuredText.getLang());
        unstructuredTextClient.setScript(unstructuredText.getScript());
        unstructuredTextClient.setTransliteration(unstructuredText.getTransliteration());
        unstructuredTextClient.setXlink(unstructuredText.getHref());
        unstructuredTextClient.setValue(unstructuredText.getValue());

        return unstructuredTextClient;
    }

    /**
     * To mods client.
     * 
     * @param dateType
     *        the date type
     * @return the date type client
     */
    private static DateTypeClient toModsClient(DateType dateType) {
        if (dateType == null) return null;
        DateTypeClient dateTypeClient = new DateTypeClient();
        dateTypeClient.setValue(dateType.getValue());
        dateTypeClient.setEncoding(dateType.getEncoding());
        dateTypeClient.setQualifier(dateType.getQualifier());
        dateTypeClient.setPoint(dateType.getPoint());
        if (dateType.getKeyDate() != null)
            dateTypeClient.setKeyDate(YesClient.fromValue(dateType.getKeyDate().value()));
        return dateTypeClient;
    }

    /**
     * To mods client.
     * 
     * @param dateOtherType
     *        the date other type
     * @return the date other type client
     */
    private static DateOtherTypeClient toModsClient(DateOtherType dateOtherType) {
        if (dateOtherType == null) return null;
        DateOtherTypeClient dateOtherTypeClient = new DateOtherTypeClient();
        dateOtherTypeClient.setValue(dateOtherType.getValue());
        dateOtherTypeClient.setEncoding(dateOtherType.getEncoding());
        dateOtherTypeClient.setQualifier(dateOtherType.getQualifier());
        dateOtherTypeClient.setPoint(dateOtherType.getPoint());
        dateOtherTypeClient.setType(dateOtherType.getType());
        if (dateOtherType.getKeyDate() != null)
            dateOtherTypeClient.setKeyDate(YesClient.fromValue(dateOtherType.getKeyDate().value()));
        return dateOtherTypeClient;
    }

    /**
     * To mods client.
     * 
     * @param originInfoType
     *        the origin info type
     * @return the origin info type client
     */
    private static OriginInfoTypeClient toModsClient(OriginInfoType originInfoType) {
        if (originInfoType == null) return null;
        OriginInfoTypeClient originInfoTypeClient = new OriginInfoTypeClient();
        originInfoTypeClient.setXmlLang(originInfoType.getXmlLang());
        originInfoTypeClient.setLang(originInfoType.getLang());
        originInfoTypeClient.setScript(originInfoType.getScript());
        originInfoTypeClient.setTransliteration(originInfoType.getTransliteration());

        List<JAXBElement<?>> subElements = originInfoType.getPlaceOrPublisherOrDateIssued();
        for (JAXBElement<?> subElement : subElements) {
            if (subElement.getName().getLocalPart().equals("issuance")) {
                if (originInfoTypeClient.getIssuance() == null) {
                    originInfoTypeClient.setIssuance(new ArrayList<String>());
                }
                originInfoTypeClient.getIssuance().add((String) subElement.getValue());
            } else if (subElement.getName().getLocalPart().equals("edition")) {
                if (originInfoTypeClient.getEdition() == null) {
                    originInfoTypeClient.setEdition(new ArrayList<String>());
                }
                originInfoTypeClient.getEdition().add((String) subElement.getValue());
            } else if (subElement.getName().getLocalPart().equals("publisher")) {
                if (originInfoTypeClient.getPublisher() == null) {
                    originInfoTypeClient.setPublisher(new ArrayList<String>());
                }
                originInfoTypeClient.getPublisher().add((String) subElement.getValue());
            } else if (subElement.getName().getLocalPart().equals("frequency")) {
                if (originInfoTypeClient.getFrequency() == null) {
                    originInfoTypeClient.setFrequency(new ArrayList<StringPlusAuthorityClient>());
                }
                originInfoTypeClient.getFrequency()
                        .add(toModsClient((StringPlusAuthority) subElement.getValue(),
                                          StringPlusAuthorityClient.class));
            } else if (subElement.getName().getLocalPart().equals("dateIssued")) {
                if (originInfoTypeClient.getDateIssued() == null) {
                    originInfoTypeClient.setDateIssued(new ArrayList<DateTypeClient>());
                }
                originInfoTypeClient.getDateIssued().add(toModsClient((DateType) subElement.getValue()));
            } else if (subElement.getName().getLocalPart().equals("dateCreated")) {
                if (originInfoTypeClient.getDateCreated() == null) {
                    originInfoTypeClient.setDateCreated(new ArrayList<DateTypeClient>());
                }
                originInfoTypeClient.getDateCreated().add(toModsClient((DateType) subElement.getValue()));
            } else if (subElement.getName().getLocalPart().equals("dateCaptured")) {
                if (originInfoTypeClient.getDateCaptured() == null) {
                    originInfoTypeClient.setDateCaptured(new ArrayList<DateTypeClient>());
                }
                originInfoTypeClient.getDateCaptured().add(toModsClient((DateType) subElement.getValue()));
            } else if (subElement.getName().getLocalPart().equals("dateValid")) {
                if (originInfoTypeClient.getDateValid() == null) {
                    originInfoTypeClient.setDateValid(new ArrayList<DateTypeClient>());
                }
                originInfoTypeClient.getDateValid().add(toModsClient((DateType) subElement.getValue()));
            } else if (subElement.getName().getLocalPart().equals("dateModified")) {
                if (originInfoTypeClient.getDateModified() == null) {
                    originInfoTypeClient.setDateModified(new ArrayList<DateTypeClient>());
                }
                originInfoTypeClient.getDateModified().add(toModsClient((DateType) subElement.getValue()));
            } else if (subElement.getName().getLocalPart().equals("copyrightDate")) {
                if (originInfoTypeClient.getCopyrightDate() == null) {
                    originInfoTypeClient.setCopyrightDate(new ArrayList<DateTypeClient>());
                }
                originInfoTypeClient.getCopyrightDate().add(toModsClient((DateType) subElement.getValue()));
            } else if (subElement.getName().getLocalPart().equals("dateOther")) {
                if (originInfoTypeClient.getDateOther() == null) {
                    originInfoTypeClient.setDateOther(new ArrayList<DateOtherTypeClient>());
                }
                originInfoTypeClient.getDateOther().add(toModsClient((DateOtherType) subElement.getValue()));
            } else if (subElement.getName().getLocalPart().equals("place")) {
                PlaceType placeType = (PlaceType) subElement.getValue();
                if (placeType != null && placeType.getPlaceTerm() != null
                        && placeType.getPlaceTerm().size() > 0) {
                    if (originInfoTypeClient.getPlace() == null) {
                        originInfoTypeClient.setPlace(new ArrayList<PlaceTypeClient>());
                    }
                    List<PlaceTermTypeClient> placeTermsTypeClient = new ArrayList<PlaceTermTypeClient>();
                    for (PlaceTermType placeTypeTerm : placeType.getPlaceTerm()) {
                        PlaceTermTypeClient placeTermTypeClient = new PlaceTermTypeClient();
                        placeTermTypeClient.setValue(placeTypeTerm.getValue());
                        if (placeTypeTerm.getAuthority() != null)
                            placeTermTypeClient.setAuthority(PlaceAuthorityClient.fromValue(placeTypeTerm
                                    .getAuthority().value()));
                        if (placeTypeTerm.getType() != null)
                            placeTermTypeClient.setType(CodeOrTextClient.fromValue(placeTypeTerm.getType()
                                    .value()));
                        placeTermsTypeClient.add(placeTermTypeClient);
                    }
                    PlaceTypeClient placeTypeClient = new PlaceTypeClient();
                    placeTypeClient.setPlaceTerm(placeTermsTypeClient);
                    originInfoTypeClient.getPlace().add(placeTypeClient);
                }
            }
        }
        return originInfoTypeClient;
    }

    /**
     * To mods client.
     * 
     * @param languageType
     *        the language type
     * @return the language type client
     */
    private static LanguageTypeClient toModsClient(LanguageType languageType) {
        if (languageType == null) return null;
        LanguageTypeClient languageTypeClient = new LanguageTypeClient();
        languageTypeClient.setObjectPart(languageType.getObjectPart());
        if (languageTypeClient.getLanguageTerm() == null) {
            languageTypeClient.setLanguageTerm(new ArrayList<LanguageTermClient>());
        }
        for (LanguageTerm languageTerm : languageType.getLanguageTerm()) {
            LanguageTermClient languageTermClient = new LanguageTermClient();
            languageTermClient.setValue(languageTerm.getValue());
            languageTermClient.setAuthority(languageTerm.getAuthority());
            if (languageTerm.getType() != null)
                languageTermClient.setType(CodeOrTextClient.fromValue(languageTerm.getType().value()));
            languageTypeClient.getLanguageTerm().add(languageTermClient);
        }
        return languageTypeClient;
    }

    /**
     * To mods client.
     * 
     * @param physicalDescriptionType
     *        the physical description type
     * @return the physical description type client
     */
    private static PhysicalDescriptionTypeClient toModsClient(PhysicalDescriptionType physicalDescriptionType) {
        if (physicalDescriptionType == null) return null;
        PhysicalDescriptionTypeClient physicalDescriptionTypeClient = new PhysicalDescriptionTypeClient();
        physicalDescriptionTypeClient.setXmlLang(physicalDescriptionType.getXmlLang());
        physicalDescriptionTypeClient.setLang(physicalDescriptionType.getLang());
        physicalDescriptionTypeClient.setScript(physicalDescriptionType.getScript());
        physicalDescriptionTypeClient.setTransliteration(physicalDescriptionType.getTransliteration());

        List<JAXBElement<?>> subElements =
                physicalDescriptionType.getFormOrReformattingQualityOrInternetMediaType();
        for (JAXBElement<?> subElement : subElements) {
            if (subElement.getName().getLocalPart().equals("reformattingQuality")) {
                if (physicalDescriptionTypeClient.getReformattingQuality() == null) {
                    physicalDescriptionTypeClient.setReformattingQuality(new ArrayList<String>());
                }
                physicalDescriptionTypeClient.getReformattingQuality().add((String) subElement.getValue());
            } else if (subElement.getName().getLocalPart().equals("internetMediaType")) {
                if (physicalDescriptionTypeClient.getInternetMediaType() == null) {
                    physicalDescriptionTypeClient.setInternetMediaType(new ArrayList<String>());
                }
                physicalDescriptionTypeClient.getInternetMediaType().add((String) subElement.getValue());
            } else if (subElement.getName().getLocalPart().equals("digitalOrigin")) {
                if (physicalDescriptionTypeClient.getDigitalOrigin() == null) {
                    physicalDescriptionTypeClient.setDigitalOrigin(new ArrayList<String>());
                }
                physicalDescriptionTypeClient.getDigitalOrigin().add((String) subElement.getValue());
            } else if (subElement.getName().getLocalPart().equals("extent")) {
                if (physicalDescriptionTypeClient.getExtent() == null) {
                    physicalDescriptionTypeClient.setExtent(new ArrayList<String>());
                }
                physicalDescriptionTypeClient.getExtent().add((String) subElement.getValue());
            } else if (subElement.getName().getLocalPart().equals("form")) {
                if (physicalDescriptionTypeClient.getForm() == null) {
                    physicalDescriptionTypeClient.setForm(new ArrayList<StringPlusAuthorityPlusTypeClient>());
                }
                physicalDescriptionTypeClient
                        .getForm()
                        .add((StringPlusAuthorityPlusTypeClient) toModsClient((StringPlusAuthorityPlusType) subElement
                                                                                      .getValue(),
                                                                              StringPlusAuthorityPlusTypeClient.class));
            } else if (subElement.getName().getLocalPart().equals("note")) {
                if (physicalDescriptionTypeClient.getNote() == null) {
                    physicalDescriptionTypeClient.setNote(new ArrayList<NoteTypeClient>());
                }
                physicalDescriptionTypeClient.getNote()
                        .add((NoteTypeClient) toModsClient((NoteType) subElement.getValue(),
                                                           NoteTypeClient.class));
            }
        }

        return physicalDescriptionTypeClient;
    }

    /**
     * To mods client.
     * 
     * @param subjectType
     *        the subject type
     * @return the subject type client
     */
    private static SubjectTypeClient toModsClient(SubjectType subjectType) {
        if (subjectType == null) return null;
        SubjectTypeClient subjectTypeClient = new SubjectTypeClient();
        subjectTypeClient.setXmlLang(subjectType.getXmlLang());
        subjectTypeClient.setLang(subjectType.getLang());
        subjectTypeClient.setScript(subjectType.getScript());
        subjectTypeClient.setTransliteration(subjectType.getTransliteration());
        subjectTypeClient.setXlink(subjectType.getHref());
        subjectTypeClient.setAuthority(subjectType.getAuthority());
        subjectTypeClient.setId(subjectType.getID());

        List<JAXBElement<?>> subElements = subjectType.getTopicOrGeographicOrTemporal();
        for (JAXBElement<?> subElement : subElements) {
            if (subElement.getName().getLocalPart().equals("topic")) {
                if (subjectTypeClient.getTopic() == null) {
                    subjectTypeClient.setTopic(new ArrayList<String>());
                }
                subjectTypeClient.getTopic().add((String) subElement.getValue());
            } else if (subElement.getName().getLocalPart().equals("geographic")) {
                if (subjectTypeClient.getGeographic() == null) {
                    subjectTypeClient.setGeographic(new ArrayList<String>());
                }
                subjectTypeClient.getGeographic().add((String) subElement.getValue());
            } else if (subElement.getName().getLocalPart().equals("occupation")) {
                if (subjectTypeClient.getOccupation() == null) {
                    subjectTypeClient.setOccupation(new ArrayList<String>());
                }
                subjectTypeClient.getOccupation().add((String) subElement.getValue());
            } else if (subElement.getName().getLocalPart().equals("genre")) {
                if (subjectTypeClient.getGenre() == null) {
                    subjectTypeClient.setGenre(new ArrayList<String>());
                }
                subjectTypeClient.getGenre().add((String) subElement.getValue());
            } else if (subElement.getName().getLocalPart().equals("temporal")) {
                if (subjectTypeClient.getTemporal() == null) {
                    subjectTypeClient.setTemporal(new ArrayList<DateTypeClient>());
                }
                subjectTypeClient.getTemporal().add(toModsClient((DateType) subElement.getValue()));
            } else if (subElement.getName().getLocalPart().equals("titleInfo")) {
                if (subjectTypeClient.getTitleInfo() == null) {
                    subjectTypeClient.setTitleInfo(new ArrayList<TitleInfoTypeClient>());
                }
                subjectTypeClient.getTitleInfo().add(toModsClient((TitleInfoType) subElement.getValue()));
            } else if (subElement.getName().getLocalPart().equals("name")) {
                if (subjectTypeClient.getName() == null) {
                    subjectTypeClient.setName(new ArrayList<NameTypeClient>());
                }
                subjectTypeClient.getName().add(toModsClient((NameType) subElement.getValue()));
            } else if (subElement.getName().getLocalPart().equals("geographicCode")) {
                GeographicCode geographicCode = (GeographicCode) subElement.getValue();
                if (geographicCode != null) {
                    GeographicCodeClient geographicCodeClient = new GeographicCodeClient();
                    if (subjectTypeClient.getGeographicCode() == null) {
                        subjectTypeClient.setGeographicCode(new ArrayList<GeographicCodeClient>());
                    }
                    geographicCodeClient.setValue(geographicCode.getValue());
                    if (geographicCode.getAuthority() != null)
                        geographicCodeClient.setAuthority(PlaceAuthorityClient.fromValue(geographicCode
                                .getAuthority().value()));
                    subjectTypeClient.getGeographicCode().add(geographicCodeClient);
                }
            } else if (subElement.getName().getLocalPart().equals("hierarchicalGeographic")) {
                HierarchicalGeographicType hierarchicalGeographicType =
                        (HierarchicalGeographicType) subElement.getValue();
                if (hierarchicalGeographicType != null) {
                    HierarchicalGeographicTypeClient hierarchicalGeographicTypeClient =
                            new HierarchicalGeographicTypeClient();
                    if (subjectTypeClient.getHierarchicalGeographic() == null) {
                        subjectTypeClient
                                .setHierarchicalGeographic(new ArrayList<HierarchicalGeographicTypeClient>());
                    }
                    for (JAXBElement<String> subSubElement : hierarchicalGeographicType
                            .getExtraterrestrialAreaOrContinentOrCountry()) {
                        if (subSubElement.getName().getLocalPart().equals("extraterrestrialArea")) {
                            if (hierarchicalGeographicTypeClient.getExtraterrestrialArea() == null) {
                                hierarchicalGeographicTypeClient
                                        .setExtraterrestrialArea(new ArrayList<String>());
                            }
                            hierarchicalGeographicTypeClient.getExtraterrestrialArea()
                                    .add(subSubElement.getValue());
                        } else if (subSubElement.getName().getLocalPart().equals("continent")) {
                            if (hierarchicalGeographicTypeClient.getContinent() == null) {
                                hierarchicalGeographicTypeClient.setContinent(new ArrayList<String>());
                            }
                            hierarchicalGeographicTypeClient.getContinent().add(subSubElement.getValue());
                        } else if (subSubElement.getName().getLocalPart().equals("country")) {
                            if (hierarchicalGeographicTypeClient.getCounty() == null) {
                                hierarchicalGeographicTypeClient.setCounty(new ArrayList<String>());
                            }
                            hierarchicalGeographicTypeClient.getCounty().add(subSubElement.getValue());
                        } else if (subSubElement.getName().getLocalPart().equals("province")) {
                            if (hierarchicalGeographicTypeClient.getProvince() == null) {
                                hierarchicalGeographicTypeClient.setProvince(new ArrayList<String>());
                            }
                            hierarchicalGeographicTypeClient.getProvince().add(subSubElement.getValue());
                        } else if (subSubElement.getName().getLocalPart().equals("region")) {
                            if (hierarchicalGeographicTypeClient.getRegion() == null) {
                                hierarchicalGeographicTypeClient.setRegion(new ArrayList<String>());
                            }
                            hierarchicalGeographicTypeClient.getRegion().add(subSubElement.getValue());
                        } else if (subSubElement.getName().getLocalPart().equals("state")) {
                            if (hierarchicalGeographicTypeClient.getState() == null) {
                                hierarchicalGeographicTypeClient.setState(new ArrayList<String>());
                            }
                            hierarchicalGeographicTypeClient.getState().add(subSubElement.getValue());
                        } else if (subSubElement.getName().getLocalPart().equals("territory")) {
                            if (hierarchicalGeographicTypeClient.getTerritory() == null) {
                                hierarchicalGeographicTypeClient.setTerritory(new ArrayList<String>());
                            }
                            hierarchicalGeographicTypeClient.getTerritory().add(subSubElement.getValue());
                        } else if (subSubElement.getName().getLocalPart().equals("county")) {
                            if (hierarchicalGeographicTypeClient.getCounty() == null) {
                                hierarchicalGeographicTypeClient.setCounty(new ArrayList<String>());
                            }
                            hierarchicalGeographicTypeClient.getCounty().add(subSubElement.getValue());
                        } else if (subSubElement.getName().getLocalPart().equals("city")) {
                            if (hierarchicalGeographicTypeClient.getCity() == null) {
                                hierarchicalGeographicTypeClient.setCity(new ArrayList<String>());
                            }
                            hierarchicalGeographicTypeClient.getCity().add(subSubElement.getValue());
                        } else if (subSubElement.getName().getLocalPart().equals("citySection")) {
                            if (hierarchicalGeographicTypeClient.getCitySection() == null) {
                                hierarchicalGeographicTypeClient.setCitySection(new ArrayList<String>());
                            }
                            hierarchicalGeographicTypeClient.getCitySection().add(subSubElement.getValue());
                        } else if (subSubElement.getName().getLocalPart().equals("island")) {
                            if (hierarchicalGeographicTypeClient.getIsland() == null) {
                                hierarchicalGeographicTypeClient.setIsland(new ArrayList<String>());
                            }
                            hierarchicalGeographicTypeClient.getIsland().add(subSubElement.getValue());
                        } else if (subSubElement.getName().getLocalPart().equals("area")) {
                            if (hierarchicalGeographicTypeClient.getArea() == null) {
                                hierarchicalGeographicTypeClient.setArea(new ArrayList<String>());
                            }
                            hierarchicalGeographicTypeClient.getArea().add(subSubElement.getValue());
                        }
                    }
                    subjectTypeClient.getHierarchicalGeographic().add(hierarchicalGeographicTypeClient);
                }
            } else if (subElement.getName().getLocalPart().equals("cartographics")) {
                if (subjectTypeClient.getCartographics() == null) {
                    subjectTypeClient.setCartographics(new ArrayList<CartographicsClient>());
                }
                CartographicsClient cartographicsClient = new CartographicsClient();
                Cartographics cartographics = (Cartographics) subElement.getValue();
                cartographicsClient.setProjection(cartographics.getProjection());
                cartographicsClient.setScale(cartographics.getScale());
                cartographicsClient.setCoordinates(cartographics.getCoordinates());
                subjectTypeClient.getCartographics().add(cartographicsClient);
            }
        }
        return subjectTypeClient;
    }

    /**
     * To mods client.
     * 
     * @param classificationType
     *        the classification type
     * @return the classification type client
     */
    private static ClassificationTypeClient toModsClient(ClassificationType classificationType) {
        if (classificationType == null) return null;
        ClassificationTypeClient classificationTypeClient = new ClassificationTypeClient();
        classificationTypeClient.setDisplayLabel(classificationType.getDisplayLabel());
        classificationTypeClient.setXmlLang(classificationType.getXmlLang());
        classificationTypeClient.setLang(classificationType.getLang());
        classificationTypeClient.setScript(classificationType.getScript());
        classificationTypeClient.setTransliteration(classificationType.getTransliteration());
        return classificationTypeClient;
    }

    /**
     * To mods client.
     * 
     * @param modsType
     *        the mods type
     * @return the mods type client
     */
    private static ModsTypeClient toModsClient(ModsType modsType) {
        ModsTypeClient modsTypeClient = new ModsTypeClient();
        modsTypeClient.setId(modsType.getID());
        modsTypeClient.setVersion(modsType.getVersion());

        List<Object> modsGroup = modsType.getModsGroup();
        if (modsGroup != null && modsGroup.size() != 0) {
            handleModsGroup(modsGroup, modsTypeClient);
        }
        return modsTypeClient;
    }

    /**
     * Handle mods group.
     * 
     * @param modsGroup
     *        the mods group
     * @param modsTypeClient
     *        the mods type client
     */
    private static void handleModsGroup(List<Object> modsGroup, ModsTypeClient modsTypeClient) {
        for (Object modsElement : modsGroup) {

            // TITLE INFO ELEMENT
            if (modsElement instanceof TitleInfoType) {
                if (modsTypeClient.getTitleInfo() == null) {
                    modsTypeClient.setTitleInfo(new ArrayList<TitleInfoTypeClient>());
                }
                modsTypeClient.getTitleInfo().add(toModsClient((TitleInfoType) modsElement));

                // NAME TYPE ELEMENT
            } else if (modsElement instanceof NameType) {
                if (modsTypeClient.getName() == null) {
                    modsTypeClient.setName(new ArrayList<NameTypeClient>());
                }
                modsTypeClient.getName().add(toModsClient((NameType) modsElement));

                // TYPE OF RESOURCE ELEMENT
            } else if (modsElement instanceof TypeOfResourceTypeClient) {
                if (modsTypeClient.getTypeOfResource() == null) {
                    modsTypeClient.setTypeOfResource(new ArrayList<TypeOfResourceTypeClient>());
                }
                modsTypeClient.getTypeOfResource().add(toModsClient((TypeOfResourceType) modsElement));

                // GENRE ELEMENT
            } else if (modsElement instanceof GenreType) {
                if (modsTypeClient.getGenre() == null) {
                    modsTypeClient.setGenre(new ArrayList<GenreTypeClient>());
                }
                modsTypeClient.getGenre().add((GenreTypeClient) toModsClient((GenreType) modsElement,
                                                                             GenreTypeClient.class));

                // ORIGIN INFO ELEMENT
            } else if (modsElement instanceof OriginInfoType) {
                if (modsTypeClient.getOriginInfo() == null) {
                    modsTypeClient.setOriginInfo(new ArrayList<OriginInfoTypeClient>());
                }
                OriginInfoTypeClient newOriginInfo = toModsClient((OriginInfoType) modsElement);

                if (modsTypeClient.getOriginInfo().size() > 0) {
                    int lastIndex = modsTypeClient.getOriginInfo().size() - 1;
                    if (isOnlyIssuance(newOriginInfo)
                            && !isOnlyIssuance(modsTypeClient.getOriginInfo().get(lastIndex))) {
                        modsTypeClient.getOriginInfo().get(lastIndex)
                                .setIssuance(newOriginInfo.getIssuance());

                    } else if (!isOnlyIssuance(newOriginInfo)
                            && isOnlyIssuance(modsTypeClient.getOriginInfo().get(lastIndex))) {
                        newOriginInfo
                                .setIssuance(modsTypeClient.getOriginInfo().get(lastIndex).getIssuance());
                        modsTypeClient.getOriginInfo().remove(lastIndex);
                        modsTypeClient.getOriginInfo().add(newOriginInfo);
                    }

                } else {
                    //                    System.err.println("new record: " + newOriginInfo.toString());
                    modsTypeClient.getOriginInfo().add(newOriginInfo);
                }

                // LANGUAGE ELEMENT
            } else if (modsElement instanceof LanguageType) {
                if (modsTypeClient.getLanguage() == null) {
                    modsTypeClient.setLanguage(new ArrayList<LanguageTypeClient>());
                }
                modsTypeClient.getLanguage().add(toModsClient((LanguageType) modsElement));

                // PHYSICAL DESCRIPTION ELEMENT
            } else if (modsElement instanceof PhysicalDescriptionType) {
                if (modsTypeClient.getPhysicalDescription() == null) {
                    modsTypeClient.setPhysicalDescription(new ArrayList<PhysicalDescriptionTypeClient>());
                }
                modsTypeClient.getPhysicalDescription()
                        .add(toModsClient((PhysicalDescriptionType) modsElement));

                // ABSTRACT ELEMENT
            } else if (modsElement instanceof AbstractType) {
                if (modsTypeClient.getAbstrac() == null) {
                    modsTypeClient.setAbstrac(new ArrayList<AbstractTypeClient>());
                }
                modsTypeClient.getAbstrac().add((AbstractTypeClient) toModsClient((AbstractType) modsElement,
                                                                                  AbstractTypeClient.class));

                // TOC ELEMENT
            } else if (modsElement instanceof TableOfContentsType) {
                if (modsTypeClient.getTableOfContents() == null) {
                    modsTypeClient.setTableOfContents(new ArrayList<TableOfContentsTypeClient>());
                }
                modsTypeClient.getTableOfContents()
                        .add((TableOfContentsTypeClient) toModsClient((TableOfContentsType) modsElement,
                                                                      TableOfContentsTypeClient.class));

                // TARGET AUDIENCE ELEMENT
            } else if (modsElement instanceof TargetAudienceType) {
                if (modsTypeClient.getTargetAudience() == null) {
                    modsTypeClient.setTargetAudience(new ArrayList<TargetAudienceTypeClient>());
                }
                modsTypeClient.getTargetAudience()
                        .add((TargetAudienceTypeClient) toModsClient((TargetAudienceType) modsElement,
                                                                     TargetAudienceTypeClient.class));

                // NOTE ELEMENT
            } else if (modsElement instanceof NoteType) {
                if (modsTypeClient.getNote() == null) {
                    modsTypeClient.setNote(new ArrayList<NoteTypeClient>());
                }
                NoteType noteType = (NoteType) modsElement;
                NoteTypeClient noteTypeClient = (NoteTypeClient) toModsClient(noteType, NoteTypeClient.class);
                noteTypeClient.setID(noteType.getID());
                modsTypeClient.getNote().add(noteTypeClient);

                // SUBJECT ELEMENT
            } else if (modsElement instanceof SubjectType) {
                if (modsTypeClient.getSubject() == null) {
                    modsTypeClient.setSubject(new ArrayList<SubjectTypeClient>());
                }
                modsTypeClient.getSubject().add(toModsClient((SubjectType) modsElement));

                // CLASSIFICATION ELEMENT
            } else if (modsElement instanceof ClassificationType) {
                if (modsTypeClient.getClassification() == null) {
                    modsTypeClient.setClassification(new ArrayList<ClassificationTypeClient>());
                }
                modsTypeClient.getClassification().add(toModsClient((ClassificationType) modsElement));

                // RELATED ITEM ELEMENT
            } else if (modsElement instanceof RelatedItemType) {
                if (modsTypeClient.getRelatedItem() == null) {
                    modsTypeClient.setRelatedItem(new ArrayList<RelatedItemTypeClient>());
                }
                modsTypeClient.getRelatedItem().add(toModsClient((RelatedItemType) modsElement));

                // IDENTIFIER ELEMENT
            } else if (modsElement instanceof IdentifierType) {
                if (modsTypeClient.getIdentifier() == null) {
                    modsTypeClient.setIdentifier(new ArrayList<IdentifierTypeClient>());
                }
                modsTypeClient.getIdentifier().add(toModsClient((IdentifierType) modsElement));

                // LOCATION ELEMENT
            } else if (modsElement instanceof LocationType) {
                if (modsTypeClient.getLocation() == null) {
                    modsTypeClient.setLocation(new ArrayList<LocationTypeClient>());
                }
                modsTypeClient.getLocation().add(toModsClient((LocationType) modsElement));

                // ACCESS CONDITION ELEMENT
            } else if (modsElement instanceof AccessConditionType) {
                if (modsTypeClient.getAccessCondition() == null) {
                    modsTypeClient.setAccessCondition(new ArrayList<AccessConditionTypeClient>());
                }
                modsTypeClient.getAccessCondition().add(toModsClient((AccessConditionType) modsElement));

                // PART ELEMENT
            } else if (modsElement instanceof PartType) {
                if (modsTypeClient.getPart() == null) {
                    modsTypeClient.setPart(new ArrayList<PartTypeClient>());
                }
                modsTypeClient.getPart().add(toModsClient((PartType) modsElement));

                // EXTENSION ELEMENT
            } else if (modsElement instanceof ExtensionType) {
                if (modsTypeClient.getExtension() == null) {
                    modsTypeClient.setExtension(new ArrayList<ExtensionTypeClient>());
                }
                modsTypeClient.getExtension().add(toModsClient((ExtensionType) modsElement));

                // RECORD INFO ELEMENT
            } else if (modsElement instanceof RecordInfoType) {
                if (modsTypeClient.getRecordInfo() == null) {
                    modsTypeClient.setRecordInfo(new ArrayList<RecordInfoTypeClient>());
                }
                modsTypeClient.getRecordInfo().add(toModsClient((RecordInfoType) modsElement));
            }
        }
    }

    private static boolean isOnlyIssuance(OriginInfoTypeClient originInfoToTest) {
        return (originInfoToTest.getIssuance() != null && originInfoToTest.getPlace() == null
                && originInfoToTest.getPublisher() == null && originInfoToTest.getDateIssued() == null
                && originInfoToTest.getDateCreated() == null && originInfoToTest.getDateCaptured() == null
                && originInfoToTest.getDateValid() == null && originInfoToTest.getDateModified() == null
                && originInfoToTest.getCopyrightDate() == null && originInfoToTest.getDateOther() == null
                && originInfoToTest.getEdition() == null && originInfoToTest.getFrequency() == null
                && originInfoToTest.getXmlLang() == null && originInfoToTest.getLang() == null
                && originInfoToTest.getScript() == null && originInfoToTest.getTransliteration() == null);
    }

    /**
     * To mods client.
     * 
     * @param accessConditionType
     *        the access condition type
     * @return the access condition type client
     */
    private static AccessConditionTypeClient toModsClient(AccessConditionType accessConditionType) {
        if (accessConditionType == null) return null;
        AccessConditionTypeClient accessConditionTypeClient = new AccessConditionTypeClient();
        accessConditionTypeClient.setDisplayLabel(accessConditionType.getDisplayLabel());
        accessConditionTypeClient.setType(accessConditionType.getAtType());
        accessConditionTypeClient.setXlink(accessConditionType.getHref());
        accessConditionTypeClient.setXmlLang(accessConditionType.getXmlLang());
        accessConditionTypeClient.setLang(accessConditionType.getLang());
        accessConditionTypeClient.setScript(accessConditionType.getScript());
        accessConditionTypeClient.setTransliteration(accessConditionType.getTransliteration());
        StringBuilder sb = new StringBuilder();
        for (Object content : accessConditionType.getContent()) {
            sb.append((String) content).append('\n');
        }
        if (!"".equals(sb.toString())) {
            accessConditionTypeClient.setContent(sb.toString());
        }

        return accessConditionTypeClient;
    }

    /**
     * To mods client.
     * 
     * @param locationType
     *        the location type
     * @return the location type client
     */
    private static LocationTypeClient toModsClient(LocationType locationType) {
        if (locationType == null) return null;
        LocationTypeClient locationTypeClient = new LocationTypeClient();
        if (locationTypeClient.getPhysicalLocation() == null) {
            locationTypeClient.setPhysicalLocation(new ArrayList<PhysicalLocationTypeClient>());
        }
        for (PhysicalLocationType physicalLocationType : locationType.getPhysicalLocation()) {
            locationTypeClient.getPhysicalLocation()
                    .add((PhysicalLocationTypeClient) toModsClient(physicalLocationType,
                                                                   PhysicalLocationTypeClient.class));
        }
        locationTypeClient.setShelfLocator(locationType.getShelfLocator());
        if (locationTypeClient.getUrl() == null) {
            locationTypeClient.setUrl(new ArrayList<UrlTypeClient>());
        }
        for (UrlType url : locationType.getUrl()) {
            UrlTypeClient urlTypeClient = new UrlTypeClient();
            urlTypeClient.setValue(url.getValue());
            urlTypeClient.setDateLastAccessed(url.getDateLastAccessed());
            urlTypeClient.setDisplayLabel(url.getDisplayLabel());
            urlTypeClient.setNote(url.getNote());
            urlTypeClient.setAccess(url.getAccess());
            urlTypeClient.setUsage(url.getUsage());
            locationTypeClient.getUrl().add(urlTypeClient);
        }
        if (locationType.getHoldingSimple() != null
                && locationType.getHoldingSimple().getCopyInformation().size() != 0) {
            HoldingSimpleTypeClient holdingSimpleTypeClient = new HoldingSimpleTypeClient();
            holdingSimpleTypeClient.setCopyInformation(new ArrayList<CopyInformationTypeClient>());
            for (CopyInformationType copyInformationType : locationType.getHoldingSimple()
                    .getCopyInformation()) {
                CopyInformationTypeClient copyInformationTypeClient = new CopyInformationTypeClient();
                copyInformationTypeClient.setForm(toModsClient(copyInformationType.getForm(),
                                                               StringPlusAuthorityClient.class));
                copyInformationTypeClient.setSubLocation(copyInformationType.getSubLocation());
                copyInformationTypeClient.setShelfLocator(copyInformationType.getShelfLocator());
                copyInformationTypeClient.setElectronicLocator(copyInformationType.getElectronicLocator());
                if (copyInformationType.getNote().size() != 0) {
                    copyInformationTypeClient.setNote(new ArrayList<StringPlusDisplayLabelPlusTypeClient>());
                    for (StringPlusDisplayLabelPlusType value : copyInformationType.getNote()) {
                        copyInformationTypeClient
                                .getNote()
                                .add((StringPlusDisplayLabelPlusTypeClient) toModsClient(value,
                                                                                         StringPlusDisplayLabelPlusTypeClient.class));
                    }
                }
                if (copyInformationType.getEnumerationAndChronology().size() != 0) {
                    copyInformationTypeClient
                            .setEnumerationAndChronology(new ArrayList<EnumerationAndChronologyTypeClient>());
                    for (final EnumerationAndChronologyType valuee : copyInformationType
                            .getEnumerationAndChronology()) {
                        copyInformationTypeClient.getEnumerationAndChronology()
                                .add(new EnumerationAndChronologyTypeClient() {

                                    {
                                        setValue(valuee.getValue());
                                        setUnitType(valuee.getUnitType());
                                    }
                                });
                    }
                }
                holdingSimpleTypeClient.getCopyInformation().add(copyInformationTypeClient);
            }
            locationTypeClient.setHoldingSimple(holdingSimpleTypeClient);
        }
        return locationTypeClient;
    }

    /**
     * To mods client.
     * 
     * @param identifierType
     *        the identifier type
     * @return the identifier type client
     */
    private static IdentifierTypeClient toModsClient(IdentifierType identifierType) {
        if (identifierType == null) return null;
        IdentifierTypeClient identifierTypeClient = new IdentifierTypeClient();
        identifierTypeClient.setValue(identifierType.getValue());
        identifierTypeClient.setType(identifierType.getType());
        identifierTypeClient.setDisplayLabel(identifierType.getDisplayLabel());
        identifierTypeClient.setXmlLang(identifierType.getXmlLang());
        identifierTypeClient.setLang(identifierType.getLang());
        identifierTypeClient.setScript(identifierType.getScript());
        identifierTypeClient.setTransliteration(identifierType.getTransliteration());
        if (identifierType.getInvalid() != null)
            identifierTypeClient.setInvalid(YesClient.fromValue(identifierType.getInvalid().value()));
        return identifierTypeClient;
    }

    /**
     * To mods client.
     * 
     * @param relatedItemType
     *        the related item type
     * @return the related item type client
     */
    private static RelatedItemTypeClient toModsClient(RelatedItemType relatedItemType) {
        if (relatedItemType == null) return null;
        RelatedItemTypeClient relatedItemTypeClient = new RelatedItemTypeClient();
        relatedItemTypeClient.setDisplayLabel(relatedItemType.getDisplayLabel());
        relatedItemTypeClient.setId(relatedItemType.getID());
        relatedItemTypeClient.setType(relatedItemType.getAtType());
        relatedItemTypeClient.setXlink(relatedItemType.getHref());
        List<Object> modsGroup = relatedItemType.getModsGroup();
        if (modsGroup != null && modsGroup.size() != 0) {
            ModsTypeClient modsTypeClient = new ModsTypeClient();
            handleModsGroup(modsGroup, modsTypeClient);
            relatedItemTypeClient.setMods(modsTypeClient);
        }
        return relatedItemTypeClient;
    }

    /**
     * To mods client.
     * 
     * @param recordInfoType
     *        the record info type
     * @return the record info type client
     */
    private static RecordInfoTypeClient toModsClient(RecordInfoType recordInfoType) {
        if (recordInfoType == null) return null;
        RecordInfoTypeClient recordInfoTypeClient = new RecordInfoTypeClient();
        recordInfoTypeClient.setXmlLang(recordInfoType.getXmlLang());
        recordInfoTypeClient.setLang(recordInfoType.getLang());
        recordInfoTypeClient.setScript(recordInfoType.getScript());
        recordInfoTypeClient.setTransliteration(recordInfoType.getTransliteration());

        List<JAXBElement<?>> subElements =
                recordInfoType.getRecordContentSourceOrRecordCreationDateOrRecordChangeDate();
        for (JAXBElement<?> subElement : subElements) {
            if (subElement.getName().getLocalPart().equals("recordChangeDate")) {
                if (recordInfoTypeClient.getRecordChangeDate() == null) {
                    recordInfoTypeClient.setRecordChangeDate(new ArrayList<DateTypeClient>());
                }
                recordInfoTypeClient.getRecordChangeDate()
                        .add(toModsClient((DateType) subElement.getValue()));
            } else if (subElement.getName().getLocalPart().equals("recordIdentifier")) {
                if (recordInfoTypeClient.getRecordIdentifier() == null) {
                    recordInfoTypeClient.setRecordIdentifier(new ArrayList<RecordIdentifierClient>());
                }
                RecordIdentifierClient recordIdentifierClient = new RecordIdentifierClient();
                RecordIdentifier recordIdentifier = (RecordIdentifier) subElement.getValue();
                recordIdentifierClient.setValue(recordIdentifier.getValue());
                recordIdentifierClient.setSource(recordIdentifier.getSource());
                recordInfoTypeClient.getRecordIdentifier().add(recordIdentifierClient);
            } else if (subElement.getName().getLocalPart().equals("recordOrigin")) {
                if (recordInfoTypeClient.getRecordOrigin() == null) {
                    recordInfoTypeClient.setRecordOrigin(new ArrayList<String>());
                }
                recordInfoTypeClient.getRecordOrigin().add((String) subElement.getValue());
            } else if (subElement.getName().getLocalPart().equals("recordCreationDate")) {
                if (recordInfoTypeClient.getRecordCreationDate() == null) {
                    recordInfoTypeClient.setRecordCreationDate(new ArrayList<DateTypeClient>());
                }
                recordInfoTypeClient.getRecordCreationDate()
                        .add(toModsClient((DateType) subElement.getValue()));
            } else if (subElement.getName().getLocalPart().equals("descriptionStandard")) {
                if (recordInfoTypeClient.getDescriptionStandard() == null) {
                    recordInfoTypeClient.setDescriptionStandard(new ArrayList<StringPlusAuthorityClient>());
                }
                recordInfoTypeClient.getDescriptionStandard()
                        .add(toModsClient((StringPlusAuthority) subElement.getValue(),
                                          StringPlusAuthorityClient.class));
            } else if (subElement.getName().getLocalPart().equals("languageOfCataloging")) {
                if (recordInfoTypeClient.getLanguageOfCataloging() == null) {
                    recordInfoTypeClient.setLanguageOfCataloging(new ArrayList<LanguageTypeClient>());
                }
                recordInfoTypeClient.getLanguageOfCataloging()
                        .add(toModsClient((LanguageType) subElement.getValue()));
            } else if (subElement.getName().getLocalPart().equals("recordContentSource")) {
                if (recordInfoTypeClient.getRecordContentSource() == null) {
                    recordInfoTypeClient
                            .setRecordContentSource(new ArrayList<StringPlusAuthorityPlusLanguageClient>());
                }
                recordInfoTypeClient
                        .getRecordContentSource()
                        .add((StringPlusAuthorityPlusLanguageClient) toModsClient((StringPlusAuthorityPlusLanguage) subElement
                                                                                          .getValue(),
                                                                                  StringPlusAuthorityPlusLanguageClient.class));
            }
        }
        return recordInfoTypeClient;
    }

    /**
     * To mods client.
     * 
     * @param extensionType
     *        the extension type
     * @return the extension type client
     */
    private static ExtensionTypeClient toModsClient(ExtensionType extensionType) {
        if (extensionType == null) return null;
        ExtensionTypeClient extensionTypeClient = new ExtensionTypeClient();
        StringBuilder sb = new StringBuilder();
        for (Object content : extensionType.getContent()) {
            sb.append((String) content).append('\n');
        }
        if (!"".equals(sb.toString())) {
            extensionTypeClient.setContent(sb.toString());
        }
        return extensionTypeClient;
    }

    /**
     * To mods client.
     * 
     * @param partType
     *        the part type
     * @return the part type client
     */
    private static PartTypeClient toModsClient(PartType partType) {
        if (partType == null) return null;
        PartTypeClient partTypeClient = new PartTypeClient();
        partTypeClient.setOrder(partType.getOrder());
        partTypeClient.setId(partType.getID());
        partTypeClient.setType(partType.getType());

        List<Object> subElements = partType.getDetailOrExtentOrDate();
        for (Object subElement : subElements) {
            if (subElement instanceof DetailType) {
                DetailType detailType = (DetailType) subElement;
                if (partTypeClient.getDetail() == null) {
                    partTypeClient.setDetail(new ArrayList<DetailTypeClient>());
                }
                DetailTypeClient detailTypeClient = new DetailTypeClient();
                detailTypeClient.setType(detailType.getType());
                detailTypeClient.setLevel(detailType.getLevel());
                if (detailType.getNumberOrCaptionOrTitle() != null
                        && detailType.getNumberOrCaptionOrTitle().size() > 0) {
                    List<JAXBElement<String>> subSubElements = detailType.getNumberOrCaptionOrTitle();
                    for (JAXBElement<String> subSubElement : subSubElements) {
                        if (subSubElement.getName().getLocalPart().equals("number")) {
                            if (detailTypeClient.getNumber() == null) {
                                detailTypeClient.setNumber(new ArrayList<String>());
                            }
                            detailTypeClient.getNumber().add(subSubElement.getValue());
                        } else if (subSubElement.getName().getLocalPart().equals("title")) {
                            if (detailTypeClient.getTitle() == null) {
                                detailTypeClient.setTitle(new ArrayList<String>());
                            }
                            detailTypeClient.getTitle().add(subSubElement.getValue());
                        } else if (subSubElement.getName().getLocalPart().equals("caption")) {
                            if (detailTypeClient.getCaption() == null) {
                                detailTypeClient.setCaption(new ArrayList<String>());
                            }
                            detailTypeClient.getCaption().add(subSubElement.getValue());
                        }
                    }
                }
                partTypeClient.getDetail().add(detailTypeClient);
            } else if (subElement instanceof BaseDateType) {
                if (partTypeClient.getDate() == null) {
                    partTypeClient.setDate(new ArrayList<BaseDateTypeClient>());
                }
                BaseDateTypeClient baseDateTypeClient = new BaseDateTypeClient();
                BaseDateType baseDateType = (BaseDateType) subElement;
                baseDateTypeClient.setValue(baseDateType.getValue());
                baseDateTypeClient.setEncoding(baseDateType.getEncoding());
                baseDateTypeClient.setQualifier(baseDateType.getQualifier());
                baseDateTypeClient.setPoint(baseDateType.getPoint());
                partTypeClient.getDate().add(baseDateTypeClient);
            } else if (subElement instanceof ExtentType) {
                if (partTypeClient.getExtent() == null) {
                    partTypeClient.setExtent(new ArrayList<ExtentTypeClient>());
                }
                ExtentTypeClient extentTypeClient = new ExtentTypeClient();
                ExtentType extentType = (ExtentType) subElement;
                extentTypeClient.setStart(extentType.getStart());
                extentTypeClient.setEnd(extentType.getEnd());
                extentTypeClient.setTotal(extentType.getTotal());
                extentTypeClient.setList(extentType.getList());
                extentTypeClient.setUnit(extentType.getUnit());
                partTypeClient.getExtent().add(extentTypeClient);
            } else if (subElement instanceof UnstructuredText) {
                if (partTypeClient.getText() == null) {
                    partTypeClient.setText(new ArrayList<String>());
                }
                partTypeClient.getText().add(((UnstructuredText) subElement).getValue());
            }
        }
        return partTypeClient;
    }

    /**
     * To mods.
     * 
     * @param modsClient
     *        the mods client
     * @return the mods collection
     */
    public static ModsCollection toMods(ModsCollectionClient modsClient) {
        if (modsClient == null) throw new NullPointerException("modsClient");
        ModsCollection mods = factory.createModsCollection();
        List<ModsTypeClient> modssClient = modsClient.getMods();
        if (modssClient != null && modssClient.size() != 0) {
            List<ModsType> modsList = mods.getMods();
            for (ModsTypeClient modsTypeClient : modssClient) {
                modsList.add(toMods(modsTypeClient));
            }
        }
        return mods;
    }

    /**
     * To mods.
     * 
     * @param modsTypeClient
     *        the mods type client
     * @return the mods type
     */
    private static ModsType toMods(ModsTypeClient modsTypeClient) {
        if (modsTypeClient != null) {
            ModsType modsType = factory.createModsType();
            modsType.setID(modsTypeClient.getId());
            modsType.setVersion(modsTypeClient.getVersion());
            handleModsGroupClient(modsTypeClient, modsType.getModsGroup());
            return modsType;
        } else
            return null;
    }

    /**
     * Handle mods group client.
     * 
     * @param modsTypeClient
     *        the mods type client
     * @param modsGroup
     *        the mods group
     */
    private static void handleModsGroupClient(ModsTypeClient modsTypeClient, List<Object> modsGroup) {
        if (modsTypeClient == null) return;
        handleTitleInfoClient(modsTypeClient.getTitleInfo(), modsGroup);
        handleNameClient(modsTypeClient.getName(), modsGroup);
        handleTypeOfResourceClient(modsTypeClient.getTypeOfResource(), modsGroup);
        handleGenreClient(modsTypeClient.getGenre(), modsGroup);
        handleOriginClient(modsTypeClient.getOriginInfo(), modsGroup);
        handleLanguageClient(modsTypeClient.getLanguage(), modsGroup);
        handlePhysicalDescription(modsTypeClient.getPhysicalDescription(), modsGroup);
        handleAbstractClient(modsTypeClient.getAbstrac(), modsGroup);
        handleTableOfContentsClient(modsTypeClient.getTableOfContents(), modsGroup);
        handleAudienceClient(modsTypeClient.getTargetAudience(), modsGroup);
        handleNoteClient(modsTypeClient.getNote(), modsGroup);
        handleSubjectClient(modsTypeClient.getSubject(), modsGroup);
        handleClassificationClient(modsTypeClient.getClassification(), modsGroup);
        handleRelatedItemClient(modsTypeClient.getRelatedItem(), modsGroup);
        handleIdentifierClient(modsTypeClient.getIdentifier(), modsGroup);
        handleLocationClient(modsTypeClient.getLocation(), modsGroup);
        handleAccessCondition(modsTypeClient.getAccessCondition(), modsGroup);
        handlePartClient(modsTypeClient.getPart(), modsGroup);
        handleExtensionClient(modsTypeClient.getExtension(), modsGroup);
        handleRecordInfoClient(modsTypeClient.getRecordInfo(), modsGroup);
    }

    /**
     * Handle title info client.
     * 
     * @param titleInfoClientList
     *        the title info client list
     * @param modsGroup
     *        the mods group
     */
    private static void handleTitleInfoClient(final List<TitleInfoTypeClient> titleInfoClientList,
                                              final List<Object> modsGroup) {
        if (titleInfoClientList != null && titleInfoClientList.size() > 0) {
            for (TitleInfoTypeClient valueClient : titleInfoClientList) {
                if (valueClient == null) continue;
                TitleInfoType value = factory.createTitleInfoType();
                value.setAtType(valueClient.getType());
                value.setID(valueClient.getId());
                value.setDisplayLabel(valueClient.getDisplayLabel());
                value.setAuthority(valueClient.getAuthority());
                value.setScript(valueClient.getScript());
                value.setHref(valueClient.getXlink());
                value.setLang(valueClient.getLang());
                value.setXmlLang(valueClient.getXmlLang());
                value.setTransliteration(valueClient.getTransliteration());
                if (valueClient.getTitle() != null) {
                    for (String strVal : valueClient.getTitle()) {
                        if (strVal != null)
                            value.getTitleOrSubTitleOrPartNumber()
                                    .add(factory.createBaseTitleInfoTypeTitle(strVal));
                    }
                }
                if (valueClient.getSubTitle() != null) {
                    for (String strVal : valueClient.getSubTitle()) {
                        if (strVal != null)
                            value.getTitleOrSubTitleOrPartNumber()
                                    .add(factory.createBaseTitleInfoTypeSubTitle(strVal));
                    }
                }
                if (valueClient.getPartName() != null) {
                    for (String strVal : valueClient.getPartName()) {
                        if (strVal != null)
                            value.getTitleOrSubTitleOrPartNumber()
                                    .add(factory.createBaseTitleInfoTypePartName(strVal));
                    }
                }
                if (valueClient.getPartNumber() != null) {
                    for (String strVal : valueClient.getPartNumber()) {
                        if (strVal != null)
                            value.getTitleOrSubTitleOrPartNumber()
                                    .add(factory.createBaseTitleInfoTypePartNumber(strVal));
                    }
                }
                if (valueClient.getNonSort() != null) {
                    for (String strVal : valueClient.getNonSort()) {
                        if (strVal != null)
                            value.getTitleOrSubTitleOrPartNumber()
                                    .add(factory.createBaseTitleInfoTypeNonSort(strVal));
                    }
                }
                modsGroup.add(value);
            }
        }
    }

    /**
     * Handle name client.
     * 
     * @param valueList
     *        the value list
     * @param modsGroup
     *        the mods group
     */
    private static void handleNameClient(final List<NameTypeClient> valueList, final List<Object> modsGroup) {
        if (valueList != null && valueList.size() > 0) {
            for (NameTypeClient valueClient : valueList) {
                if (valueClient == null) continue;
                NameType value = factory.createNameType();
                value.setAtType(valueClient.getType() == null || valueClient.getType().value() == null ? null
                        : NameTypeAttribute.fromValue(valueClient.getType().value()));
                value.setID(valueClient.getId());
                value.setAuthority(valueClient.getAuthority());
                value.setScript(valueClient.getScript());
                value.setHref(valueClient.getXlink());
                value.setLang(valueClient.getLang());
                value.setXmlLang(valueClient.getXmlLang());
                value.setTransliteration(valueClient.getTransliteration());
                if (valueClient.getNamePart() != null) {
                    for (NamePartTypeClient namePartClient : valueClient.getNamePart()) {
                        if (namePartClient != null) {
                            NamePartType namePart = factory.createNamePartType();
                            namePart.setType(namePartClient.getType());
                            namePart.setValue(namePartClient.getValue());
                            value.getNamePartOrDisplayFormOrAffiliation()
                                    .add(factory.createNameTypeNamePart(namePart));
                        }
                    }
                }
                if (valueClient.getDisplayForm() != null) {
                    for (String strVal : valueClient.getDisplayForm()) {
                        if (strVal != null)
                            value.getNamePartOrDisplayFormOrAffiliation()
                                    .add(factory.createNameTypeDisplayForm(strVal));
                    }
                }
                if (valueClient.getAffiliation() != null) {
                    for (String strVal : valueClient.getAffiliation()) {
                        if (strVal != null)
                            value.getNamePartOrDisplayFormOrAffiliation()
                                    .add(factory.createNameTypeAffiliation(strVal));
                    }
                }
                if (valueClient.getRole() != null) {
                    for (RoleTypeClient roleTypeClient : valueClient.getRole()) {
                        if (roleTypeClient != null) {
                            RoleType roleType = factory.createRoleType();
                            List<RoleTermClient> roleTermClientList = roleTypeClient.getRoleTerm();
                            if (roleTermClientList != null) {
                                for (RoleTermClient roleTermClient : roleTermClientList) {
                                    if (roleTermClient != null) {
                                        RoleTerm roleTerm = factory.createRoleTypeRoleTerm();
                                        roleTerm.setAuthority(roleTermClient.getAuthority());
                                        roleTerm.setType(roleTermClient.getType() == null ? null : CodeOrText
                                                .fromValue(roleTermClient.getType().value()));
                                        roleTerm.setValue(roleTermClient.getValue());
                                        roleType.getRoleTerm().add(roleTerm);
                                    }
                                }
                            }
                            value.getNamePartOrDisplayFormOrAffiliation()
                                    .add(factory.createNameTypeRole(roleType));
                        }
                    }
                }
                if (valueClient.getDescription() != null) {
                    for (String strVal : valueClient.getDescription()) {
                        if (strVal != null)
                            value.getNamePartOrDisplayFormOrAffiliation()
                                    .add(factory.createNameTypeDescription(strVal));
                    }
                }
                modsGroup.add(value);
            }
        }
    }

    /**
     * Handle type of resource client.
     * 
     * @param valueList
     *        the value list
     * @param modsGroup
     *        the mods group
     */
    private static void handleTypeOfResourceClient(final List<TypeOfResourceTypeClient> valueList,
                                                   final List<Object> modsGroup) {
        if (valueList != null && valueList.size() > 0) {
            for (TypeOfResourceTypeClient valueClient : valueList) {
                if (valueClient == null) continue;
                TypeOfResourceType value = factory.createTypeOfResourceType();
                value.setValue(valueClient.getValue());
                value.setManuscript(valueClient.getManuscript() == null
                        || valueClient.getManuscript().value() == null ? null : Yes.fromValue(valueClient
                        .getManuscript().value()));
                value.setManuscript(valueClient.getCollection() == null
                        || valueClient.getCollection().value() == null ? null : Yes.fromValue(valueClient
                        .getCollection().value()));
                modsGroup.add(value);
            }
        }
    }

    /**
     * Handle genre client.
     * 
     * @param valueList
     *        the value list
     * @param modsGroup
     *        the mods group
     */
    private static void handleGenreClient(final List<GenreTypeClient> valueList, final List<Object> modsGroup) {
        if (valueList != null && valueList.size() > 0) {
            for (GenreTypeClient valueClient : valueList) {
                if (valueClient == null) continue;
                GenreType value = factory.createGenreType();
                value.setAtType(valueClient.getType());
                value.setAuthority(valueClient.getAuthority());
                value.setScript(valueClient.getScript());
                value.setLang(valueClient.getLang());
                value.setXmlLang(valueClient.getXmlLang());
                value.setTransliteration(valueClient.getTransliteration());
                value.setValue(valueClient.getValue());
                modsGroup.add(value);
            }
        }
    }

    /**
     * Handle date client.
     * 
     * @param valueClient
     *        the value client
     * @param value
     *        the value
     */
    private static void handleDateClient(final DateTypeClient valueClient, final DateType value) {
        value.setValue(valueClient.getValue());
        value.setEncoding(valueClient.getEncoding());
        value.setKeyDate(valueClient.getKeyDate() == null || valueClient.getKeyDate().value() == null ? null
                : Yes.fromValue(valueClient.getKeyDate().value()));
        value.setPoint(valueClient.getPoint());
        value.setQualifier(valueClient.getQualifier());
    }

    /**
     * Handle origin client.
     * 
     * @param valueList
     *        the value list
     * @param modsGroup
     *        the mods group
     */
    private static void handleOriginClient(final List<OriginInfoTypeClient> valueList,
                                           final List<Object> modsGroup) {
        if (valueList != null && valueList.size() > 0) {
            for (OriginInfoTypeClient valueClient : valueList) {
                if (valueClient == null) continue;
                OriginInfoType value = factory.createOriginInfoType();
                value.setScript(valueClient.getScript());
                value.setLang(valueClient.getLang());
                value.setXmlLang(valueClient.getXmlLang());
                value.setTransliteration(valueClient.getTransliteration());
                if (valueClient.getPlace() != null) {
                    for (PlaceTypeClient placeTypeClient : valueClient.getPlace()) {
                        if (placeTypeClient != null) {
                            PlaceType placeType = factory.createPlaceType();
                            List<PlaceTermTypeClient> placeTermClientList = placeTypeClient.getPlaceTerm();
                            if (placeTermClientList != null) {
                                for (PlaceTermTypeClient placeTermClient : placeTermClientList) {
                                    if (placeTermClient != null) {
                                        PlaceTermType placeTerm = factory.createPlaceTermType();
                                        placeTerm.setAuthority(placeTermClient.getAuthority() == null ? null
                                                : PlaceAuthority.fromValue(placeTermClient.getAuthority()
                                                        .value()));
                                        placeTerm.setType(placeTermClient.getType() == null ? null
                                                : CodeOrText.fromValue(placeTermClient.getType().value()));
                                        placeTerm.setValue(placeTermClient.getValue());
                                        placeType.getPlaceTerm().add(placeTerm);
                                    }
                                }
                            }
                            value.getPlaceOrPublisherOrDateIssued()
                                    .add(factory.createOriginInfoTypePlace(placeType));
                        }
                    }
                }
                if (valueClient.getEdition() != null) {
                    for (String strVal : valueClient.getEdition()) {
                        if (strVal != null)
                            value.getPlaceOrPublisherOrDateIssued()
                                    .add(factory.createOriginInfoTypeEdition(strVal));
                    }
                }
                if (valueClient.getIssuance() != null) {
                    for (String strVal : valueClient.getIssuance()) {
                        if (strVal != null)
                            value.getPlaceOrPublisherOrDateIssued()
                                    .add(factory.createOriginInfoTypeIssuance(strVal));
                    }
                }
                if (valueClient.getPublisher() != null) {
                    for (String strVal : valueClient.getPublisher()) {
                        if (strVal != null)
                            value.getPlaceOrPublisherOrDateIssued()
                                    .add(factory.createOriginInfoTypePublisher(strVal));
                    }
                }
                if (valueClient.getDateIssued() != null) {
                    for (DateTypeClient dateTypeClient : valueClient.getDateIssued()) {
                        if (dateTypeClient != null) {
                            DateType dateType = factory.createDateType();
                            handleDateClient(dateTypeClient, dateType);
                            value.getPlaceOrPublisherOrDateIssued()
                                    .add(factory.createOriginInfoTypeDateIssued(dateType));
                        }
                    }
                }
                if (valueClient.getDateCreated() != null) {
                    for (DateTypeClient dateTypeClient : valueClient.getDateCreated()) {
                        if (dateTypeClient != null) {
                            DateType dateType = factory.createDateType();
                            handleDateClient(dateTypeClient, dateType);
                            value.getPlaceOrPublisherOrDateIssued()
                                    .add(factory.createOriginInfoTypeDateCreated(dateType));
                        }
                    }
                }
                if (valueClient.getDateCaptured() != null) {
                    for (DateTypeClient dateTypeClient : valueClient.getDateCaptured()) {
                        if (dateTypeClient != null) {
                            DateType dateType = factory.createDateType();
                            handleDateClient(dateTypeClient, dateType);
                            value.getPlaceOrPublisherOrDateIssued()
                                    .add(factory.createOriginInfoTypeDateCaptured(dateType));
                        }
                    }
                }
                if (valueClient.getDateValid() != null) {
                    for (DateTypeClient dateTypeClient : valueClient.getDateValid()) {
                        if (dateTypeClient != null) {
                            DateType dateType = factory.createDateType();
                            handleDateClient(dateTypeClient, dateType);
                            value.getPlaceOrPublisherOrDateIssued()
                                    .add(factory.createOriginInfoTypeDateValid(dateType));
                        }
                    }
                }
                if (valueClient.getDateModified() != null) {
                    for (DateTypeClient dateTypeClient : valueClient.getDateModified()) {
                        if (dateTypeClient != null) {
                            DateType dateType = factory.createDateType();
                            handleDateClient(dateTypeClient, dateType);
                            value.getPlaceOrPublisherOrDateIssued()
                                    .add(factory.createOriginInfoTypeDateModified(dateType));
                        }
                    }
                }
                if (valueClient.getCopyrightDate() != null) {
                    for (DateTypeClient dateTypeClient : valueClient.getCopyrightDate()) {
                        if (dateTypeClient != null) {
                            DateType dateType = factory.createDateType();
                            handleDateClient(dateTypeClient, dateType);
                            value.getPlaceOrPublisherOrDateIssued()
                                    .add(factory.createOriginInfoTypeCopyrightDate(dateType));
                        }
                    }
                }
                if (valueClient.getDateOther() != null) {
                    for (DateOtherTypeClient dateTypeClient : valueClient.getDateOther()) {
                        if (dateTypeClient != null) {
                            DateOtherType dateType = factory.createDateOtherType();
                            handleDateClient(dateTypeClient, dateType);
                            dateType.setType(dateTypeClient.getType());
                            value.getPlaceOrPublisherOrDateIssued()
                                    .add(factory.createOriginInfoTypeDateOther(dateType));
                        }
                    }
                }
                if (valueClient.getFrequency() != null) {
                    for (StringPlusAuthorityClient strVal : valueClient.getFrequency()) {
                        if (strVal != null) {
                            StringPlusAuthority frequency = factory.createStringPlusAuthority();
                            frequency.setAuthority(strVal.getAuthority());
                            frequency.setValue(strVal.getValue());
                            value.getPlaceOrPublisherOrDateIssued()
                                    .add(factory.createOriginInfoTypeFrequency(frequency));
                        }
                    }
                }
                modsGroup.add(value);
            }
        }
    }

    /**
     * Handle language client.
     * 
     * @param valueList
     *        the value list
     * @param modsGroup
     *        the mods group
     */
    private static void handleLanguageClient(List<LanguageTypeClient> valueList, List<Object> modsGroup) {
        if (valueList != null && valueList.size() > 0) {
            for (LanguageTypeClient valueClient : valueList) {
                if (valueClient == null) continue;
                LanguageType value = factory.createLanguageType();
                value.setObjectPart(valueClient.getObjectPart());
                if (valueClient.getLanguageTerm() != null) {
                    for (LanguageTermClient val : valueClient.getLanguageTerm()) {
                        if (val != null) {
                            LanguageTerm langTerm = factory.createLanguageTypeLanguageTerm();
                            langTerm.setAuthority(val.getAuthority());
                            langTerm.setValue(val.getValue());
                            langTerm.setType(val.getType() == null ? null : CodeOrText.fromValue(val
                                    .getType().value()));
                            value.getLanguageTerm().add(langTerm);
                        }
                    }
                }
                modsGroup.add(value);
            }
        }
    }

    /**
     * Handle physical description.
     * 
     * @param valueList
     *        the value list
     * @param modsGroup
     *        the mods group
     */
    private static void handlePhysicalDescription(List<PhysicalDescriptionTypeClient> valueList,
                                                  List<Object> modsGroup) {
        if (valueList != null && valueList.size() > 0) {
            for (PhysicalDescriptionTypeClient valueClient : valueList) {
                if (valueClient == null) continue;
                PhysicalDescriptionType value = factory.createPhysicalDescriptionType();
                value.setLang(valueClient.getLang());
                value.setXmlLang(valueClient.getXmlLang());
                value.setTransliteration(valueClient.getTransliteration());
                value.setScript(valueClient.getScript());

                if (valueClient.getForm() != null) {
                    for (StringPlusAuthorityPlusTypeClient val : valueClient.getForm()) {
                        if (val != null) {
                            StringPlusAuthorityPlusType form = new StringPlusAuthorityPlusType();
                            form.setAuthority(val.getAuthority());
                            form.setValue(val.getValue());
                            form.setType(val.getType());
                            value.getFormOrReformattingQualityOrInternetMediaType()
                                    .add(factory.createPhysicalDescriptionTypeForm(form));
                        }
                    }
                }
                if (valueClient.getReformattingQuality() != null) {
                    for (String val : valueClient.getReformattingQuality()) {
                        if (val != null) {
                            value.getFormOrReformattingQualityOrInternetMediaType()
                                    .add(factory.createPhysicalDescriptionTypeReformattingQuality(val));
                        }
                    }
                }
                if (valueClient.getInternetMediaType() != null) {
                    for (String val : valueClient.getInternetMediaType()) {
                        if (val != null) {
                            value.getFormOrReformattingQualityOrInternetMediaType()
                                    .add(factory.createPhysicalDescriptionTypeInternetMediaType(val));
                        }
                    }
                }
                if (valueClient.getExtent() != null) {
                    for (String val : valueClient.getExtent()) {
                        if (val != null) {
                            value.getFormOrReformattingQualityOrInternetMediaType()
                                    .add(factory.createPhysicalDescriptionTypeExtent(val));
                        }
                    }
                }
                if (valueClient.getDigitalOrigin() != null) {
                    for (String val : valueClient.getDigitalOrigin()) {
                        if (val != null) {
                            value.getFormOrReformattingQualityOrInternetMediaType()
                                    .add(factory.createPhysicalDescriptionTypeDigitalOrigin(val));
                        }
                    }
                }
                if (valueClient.getNote() != null) {
                    for (NoteTypeClient val : valueClient.getNote()) {
                        if (val != null) {
                            NoteType note = factory.createNoteType();
                            note.setDisplayLabel(val.getDisplayLabel());
                            note.setValue(val.getValue());
                            note.setHref(val.getXlink());
                            note.setXmlLang(val.getXmlLang());
                            note.setLang(val.getLang());
                            note.setScript(val.getScript());
                            note.setTransliteration(val.getTransliteration());
                            note.setAtType(val.getAtType());
                            value.getFormOrReformattingQualityOrInternetMediaType()
                                    .add(factory.createPhysicalDescriptionTypeNote(note));
                        }
                    }
                }
                modsGroup.add(value);
            }
        }
    }

    /**
     * Handle abstract client.
     * 
     * @param valueList
     *        the value list
     * @param modsGroup
     *        the mods group
     */
    private static void handleAbstractClient(List<AbstractTypeClient> valueList, List<Object> modsGroup) {
        if (valueList != null && valueList.size() > 0) {
            for (AbstractTypeClient valueClient : valueList) {
                if (valueClient == null) continue;
                AbstractType value = factory.createAbstractType();
                value.setValue(valueClient.getValue());
                value.setAtType(valueClient.getAtType());
                value.setDisplayLabel(valueClient.getDisplayLabel());
                value.setHref(valueClient.getXlink());
                value.setScript(valueClient.getScript());
                value.setLang(valueClient.getLang());
                value.setXmlLang(valueClient.getXmlLang());
                value.setTransliteration(valueClient.getTransliteration());
                modsGroup.add(value);
            }
        }
    }

    /**
     * Handle table of contents client.
     * 
     * @param valueList
     *        the value list
     * @param modsGroup
     *        the mods group
     */
    private static void handleTableOfContentsClient(List<TableOfContentsTypeClient> valueList,
                                                    List<Object> modsGroup) {
        if (valueList != null && valueList.size() > 0) {
            for (TableOfContentsTypeClient valueClient : valueList) {
                if (valueClient == null) continue;
                TableOfContentsType value = factory.createTableOfContentsType();
                value.setValue(valueClient.getValue());
                value.setDisplayLabel(valueClient.getDisplayLabel());
                value.setAtType(valueClient.getAtType());
                value.setHref(valueClient.getXlink());
                value.setScript(valueClient.getScript());
                value.setLang(valueClient.getLang());
                value.setXmlLang(valueClient.getXmlLang());
                value.setTransliteration(valueClient.getTransliteration());
                modsGroup.add(value);
            }
        }
    }

    /**
     * Handle audience client.
     * 
     * @param valueList
     *        the value list
     * @param modsGroup
     *        the mods group
     */
    private static void handleAudienceClient(List<TargetAudienceTypeClient> valueList, List<Object> modsGroup) {
        if (valueList != null && valueList.size() > 0) {
            for (TargetAudienceTypeClient valueClient : valueList) {
                if (valueClient == null) continue;
                TargetAudienceType value = factory.createTargetAudienceType();
                value.setValue(valueClient.getValue());
                value.setAuthority(valueClient.getAuthority());
                value.setScript(valueClient.getScript());
                value.setLang(valueClient.getLang());
                value.setXmlLang(valueClient.getXmlLang());
                value.setTransliteration(valueClient.getTransliteration());
                modsGroup.add(value);
            }
        }
    }

    /**
     * Handle note client.
     * 
     * @param valueList
     *        the value list
     * @param modsGroup
     *        the mods group
     */
    private static void handleNoteClient(List<NoteTypeClient> valueList, List<Object> modsGroup) {
        if (valueList != null && valueList.size() > 0) {
            for (NoteTypeClient valueClient : valueList) {
                if (valueClient == null) continue;
                NoteType value = factory.createNoteType();
                value.setValue(valueClient.getValue());
                value.setDisplayLabel(valueClient.getDisplayLabel());
                value.setID(valueClient.getID());
                value.setAtType(valueClient.getAtType());
                value.setHref(valueClient.getXlink());
                value.setScript(valueClient.getScript());
                value.setLang(valueClient.getLang());
                value.setXmlLang(valueClient.getXmlLang());
                value.setTransliteration(valueClient.getTransliteration());
                modsGroup.add(value);
            }
        }
    }

    /**
     * Handle subject client.
     * 
     * @param valueList
     *        the value list
     * @param modsGroup
     *        the mods group
     */
    private static void handleSubjectClient(List<SubjectTypeClient> valueList, List<Object> modsGroup) {
        if (valueList != null && valueList.size() > 0) {
            for (SubjectTypeClient valueClient : valueList) {
                if (valueClient == null) continue;
                SubjectType value = factory.createSubjectType();
                value.setID(valueClient.getId());
                value.setAuthority(valueClient.getAuthority());
                value.setHref(valueClient.getXlink());
                value.setScript(valueClient.getScript());
                value.setLang(valueClient.getLang());
                value.setXmlLang(valueClient.getXmlLang());
                value.setTransliteration(valueClient.getTransliteration());
                if (valueClient.getTopic() != null) {
                    for (String strVal : valueClient.getTopic()) {
                        if (strVal != null)
                            value.getTopicOrGeographicOrTemporal()
                                    .add(factory.createSubjectTypeTopic(strVal));
                    }
                }
                if (valueClient.getGeographic() != null) {
                    for (String strVal : valueClient.getGeographic()) {
                        if (strVal != null)
                            value.getTopicOrGeographicOrTemporal()
                                    .add(factory.createSubjectTypeGeographic(strVal));
                    }
                }
                if (valueClient.getGeographic() != null) {
                    for (String strVal : valueClient.getGeographic()) {
                        if (strVal != null)
                            value.getTopicOrGeographicOrTemporal()
                                    .add(factory.createSubjectTypeGeographic(strVal));
                    }
                }
                if (valueClient.getTemporal() != null) {
                    for (DateTypeClient dateTypeClient : valueClient.getTemporal()) {
                        if (dateTypeClient != null) {
                            DateType dateType = factory.createDateType();
                            handleDateClient(dateTypeClient, dateType);
                            value.getTopicOrGeographicOrTemporal()
                                    .add(factory.createSubjectTypeTemporal(dateType));
                        }
                    }
                }
                List<Object> titleInfos = new ArrayList<Object>();
                handleTitleInfoClient(valueClient.getTitleInfo(), titleInfos);
                for (Object o : titleInfos) {
                    if (o != null) {
                        value.getTopicOrGeographicOrTemporal()
                                .add(factory.createSubjectTypeTitleInfo((TitleInfoType) o));
                    }
                }
                List<Object> names = new ArrayList<Object>();
                handleNameClient(valueClient.getName(), names);
                for (Object o : names) {
                    if (o != null) {
                        value.getTopicOrGeographicOrTemporal()
                                .add(factory.createSubjectTypeName((NameType) o));
                    }
                }
                if (valueClient.getGenre() != null) {
                    for (String strVal : valueClient.getGenre()) {
                        if (strVal != null)
                            value.getTopicOrGeographicOrTemporal()
                                    .add(factory.createSubjectTypeGenre(strVal));
                    }
                }
                if (valueClient.getOccupation() != null) {
                    for (String strVal : valueClient.getOccupation()) {
                        if (strVal != null)
                            value.getTopicOrGeographicOrTemporal()
                                    .add(factory.createSubjectTypeOccupation(strVal));
                    }
                }
                if (valueClient.getGeographicCode() != null) {
                    for (GeographicCodeClient val : valueClient.getGeographicCode()) {
                        if (val != null) {
                            GeographicCode code = factory.createSubjectTypeGeographicCode();
                            code.setValue(val.getValue());
                            code.setAuthority(val.getAuthority() == null ? null : PlaceAuthority
                                    .fromValue(val.getAuthority().value()));
                            value.getTopicOrGeographicOrTemporal()
                                    .add(factory.createSubjectTypeGeographicCode(code));
                        }
                    }
                }

                if (valueClient.getHierarchicalGeographic() != null) {
                    for (HierarchicalGeographicTypeClient hierarchicalClient : valueClient
                            .getHierarchicalGeographic()) {
                        if (hierarchicalClient != null) {
                            HierarchicalGeographicType hierarchical =
                                    factory.createHierarchicalGeographicType();
                            if (hierarchicalClient.getContinent() != null) {
                                for (String strVal : hierarchicalClient.getContinent()) {
                                    if (strVal != null)
                                        hierarchical
                                                .getExtraterrestrialAreaOrContinentOrCountry()
                                                .add(factory.createHierarchicalGeographicTypeContinent(strVal));
                                }
                            }
                            if (hierarchicalClient.getCountry() != null) {
                                for (String strVal : hierarchicalClient.getCountry()) {
                                    if (strVal != null)
                                        hierarchical.getExtraterrestrialAreaOrContinentOrCountry()
                                                .add(factory.createHierarchicalGeographicTypeCountry(strVal));
                                }
                            }
                            if (hierarchicalClient.getProvince() != null) {
                                for (String strVal : hierarchicalClient.getProvince()) {
                                    if (strVal != null)
                                        hierarchical
                                                .getExtraterrestrialAreaOrContinentOrCountry()
                                                .add(factory.createHierarchicalGeographicTypeProvince(strVal));
                                }
                            }
                            if (hierarchicalClient.getRegion() != null) {
                                for (String strVal : hierarchicalClient.getRegion()) {
                                    if (strVal != null)
                                        hierarchical.getExtraterrestrialAreaOrContinentOrCountry()
                                                .add(factory.createHierarchicalGeographicTypeRegion(strVal));
                                }
                            }
                            if (hierarchicalClient.getState() != null) {
                                for (String strVal : hierarchicalClient.getState()) {
                                    if (strVal != null)
                                        hierarchical.getExtraterrestrialAreaOrContinentOrCountry()
                                                .add(factory.createHierarchicalGeographicTypeState(strVal));
                                }
                            }
                            if (hierarchicalClient.getTerritory() != null) {
                                for (String strVal : hierarchicalClient.getTerritory()) {
                                    if (strVal != null)
                                        hierarchical
                                                .getExtraterrestrialAreaOrContinentOrCountry()
                                                .add(factory.createHierarchicalGeographicTypeTerritory(strVal));
                                }
                            }
                            if (hierarchicalClient.getCounty() != null) {
                                for (String strVal : hierarchicalClient.getCounty()) {
                                    if (strVal != null)
                                        hierarchical.getExtraterrestrialAreaOrContinentOrCountry()
                                                .add(factory.createHierarchicalGeographicTypeCounty(strVal));
                                }
                            }
                            if (hierarchicalClient.getCity() != null) {
                                for (String strVal : hierarchicalClient.getCity()) {
                                    if (strVal != null)
                                        hierarchical.getExtraterrestrialAreaOrContinentOrCountry()
                                                .add(factory.createHierarchicalGeographicTypeCity(strVal));
                                }
                            }
                            if (hierarchicalClient.getCitySection() != null) {
                                for (String strVal : hierarchicalClient.getCitySection()) {
                                    if (strVal != null)
                                        hierarchical
                                                .getExtraterrestrialAreaOrContinentOrCountry()
                                                .add(factory.createHierarchicalGeographicTypeCitySection(strVal));
                                }
                            }
                            if (hierarchicalClient.getIsland() != null) {
                                for (String strVal : hierarchicalClient.getIsland()) {
                                    if (strVal != null)
                                        hierarchical.getExtraterrestrialAreaOrContinentOrCountry()
                                                .add(factory.createHierarchicalGeographicTypeIsland(strVal));
                                }
                            }
                            if (hierarchicalClient.getArea() != null) {
                                for (String strVal : hierarchicalClient.getArea()) {
                                    if (strVal != null)
                                        hierarchical.getExtraterrestrialAreaOrContinentOrCountry()
                                                .add(factory.createHierarchicalGeographicTypeArea(strVal));
                                }
                            }
                            if (hierarchicalClient.getExtraterrestrialArea() != null) {
                                for (String strVal : hierarchicalClient.getExtraterrestrialArea()) {
                                    if (strVal != null)
                                        hierarchical
                                                .getExtraterrestrialAreaOrContinentOrCountry()
                                                .add(factory.createHierarchicalGeographicTypeExtraterrestrialArea(strVal));
                                }
                            }
                            value.getTopicOrGeographicOrTemporal()
                                    .add(factory.createSubjectTypeHierarchicalGeographic(hierarchical));
                        }
                    }
                }
                if (valueClient.getCartographics() != null) {
                    for (CartographicsClient val : valueClient.getCartographics()) {
                        if (val != null) {
                            Cartographics cart = factory.createSubjectTypeCartographics();
                            cart.setProjection(val.getProjection());
                            cart.setScale(val.getScale());
                            if (val.getCoordinates() != null) {
                                for (String strVal : val.getCoordinates()) {
                                    if (strVal != null) cart.getCoordinates().add(strVal);
                                }
                            }
                            value.getTopicOrGeographicOrTemporal()
                                    .add(factory.createSubjectTypeCartographics(cart));
                        }
                    }
                }

                modsGroup.add(value);
            }
        }
    }

    /**
     * Handle classification client.
     * 
     * @param valueList
     *        the value list
     * @param modsGroup
     *        the mods group
     */
    private static void handleClassificationClient(List<ClassificationTypeClient> valueList,
                                                   List<Object> modsGroup) {
        if (valueList != null && valueList.size() > 0) {
            for (ClassificationTypeClient valueClient : valueList) {
                if (valueClient == null) continue;
                ClassificationType value = factory.createClassificationType();
                value.setAuthority(valueClient.getAuthority());
                value.setValue(valueClient.getValue());
                value.setDisplayLabel(valueClient.getDisplayLabel());
                value.setEdition(valueClient.getEdition());
                value.setScript(valueClient.getScript());
                value.setLang(valueClient.getLang());
                value.setXmlLang(valueClient.getXmlLang());
                value.setTransliteration(valueClient.getTransliteration());
                modsGroup.add(value);
            }
        }
    }

    /**
     * Handle related item client.
     * 
     * @param valueList
     *        the value list
     * @param modsGroup
     *        the mods group
     */
    private static void handleRelatedItemClient(List<RelatedItemTypeClient> valueList, List<Object> modsGroup) {
        if (valueList != null && valueList.size() > 0) {
            for (RelatedItemTypeClient valueClient : valueList) {
                if (valueClient == null) continue;
                RelatedItemType value = factory.createRelatedItemType();
                value.setAtType(valueClient.getType());
                value.setDisplayLabel(valueClient.getDisplayLabel());
                value.setHref(valueClient.getXlink());
                value.setID(valueClient.getId());
                handleModsGroupClient(valueClient.getMods(), value.getModsGroup());
                modsGroup.add(value);
            }
        }
    }

    /**
     * Handle identifier client.
     * 
     * @param valueList
     *        the value list
     * @param modsGroup
     *        the mods group
     */
    private static void handleIdentifierClient(List<IdentifierTypeClient> valueList, List<Object> modsGroup) {
        if (valueList != null && valueList.size() > 0) {
            for (IdentifierTypeClient valueClient : valueList) {
                if (valueClient == null) continue;
                IdentifierType value = factory.createIdentifierType();
                value.setType(valueClient.getType());
                value.setValue(valueClient.getValue());
                value.setDisplayLabel(valueClient.getDisplayLabel());
                value.setScript(valueClient.getScript());
                value.setLang(valueClient.getLang());
                value.setXmlLang(valueClient.getXmlLang());
                value.setTransliteration(valueClient.getTransliteration());
                value.setInvalid(valueClient.getInvalid() == null ? null : Yes.fromValue(valueClient
                        .getInvalid().value()));
                modsGroup.add(value);
            }
        }
    }

    /**
     * Handle location client.
     * 
     * @param valueList
     *        the value list
     * @param modsGroup
     *        the mods group
     */
    private static void handleLocationClient(List<LocationTypeClient> valueList, List<Object> modsGroup) {
        if (valueList != null && valueList.size() > 0) {
            for (LocationTypeClient valueClient : valueList) {
                if (valueClient == null) continue;
                LocationType value = factory.createLocationType();
                if (valueClient.getShelfLocator() != null) {
                    for (String strVal : valueClient.getShelfLocator()) {
                        if (strVal != null) value.getShelfLocator().add(strVal);
                    }
                }
                if (valueClient.getHoldingExternal() != null
                        && valueClient.getHoldingExternal().getContent() != null) {
                    ExtensionTypeClient val = valueClient.getHoldingExternal();
                    value.getHoldingExternal().getContent().add(val.getContent());
                }
                if (valueClient.getPhysicalLocation() != null) {
                    for (PhysicalLocationTypeClient val : valueClient.getPhysicalLocation()) {
                        if (val != null) {
                            PhysicalLocationType loc = factory.createPhysicalLocationType();
                            loc.setValue(val.getValue());
                            loc.setDisplayLabel(val.getDisplayLabel());
                            loc.setAtType(val.getType());
                            loc.setHref(val.getXlink());
                            loc.setAuthority(val.getAuthority());
                            loc.setScript(val.getScript());
                            loc.setLang(val.getLang());
                            loc.setXmlLang(val.getXmlLang());
                            loc.setTransliteration(val.getTransliteration());
                            value.getPhysicalLocation().add(loc);
                        }
                    }
                }

                if (valueClient.getUrl() != null) {
                    for (UrlTypeClient val : valueClient.getUrl()) {
                        if (val != null) {
                            UrlType url = factory.createUrlType();
                            url.setAccess(val.getAccess());
                            url.setValue(val.getValue());
                            url.setDisplayLabel(val.getDisplayLabel());
                            url.setNote(val.getNote());
                            url.setUsage(val.getUsage());
                            url.setDateLastAccessed(val.getDateLastAccessed());
                            value.getUrl().add(url);
                        }
                    }
                }

                // TODO: do not copy the empty structure
                if (valueClient.getHoldingSimple() != null
                        && valueClient.getHoldingSimple().getCopyInformation() != null
                        && valueClient.getHoldingSimple().getCopyInformation().size() > 0) {
                    HoldingSimpleTypeClient val = valueClient.getHoldingSimple();
                    HoldingSimpleType hold = factory.createHoldingSimpleType();
                    if (val.getCopyInformation() != null) {
                        for (CopyInformationTypeClient copyClient : val.getCopyInformation()) {
                            if (copyClient != null) {
                                CopyInformationType copy = factory.createCopyInformationType();
                                copy.getSubLocation().addAll(copyClient.getSubLocation());
                                copy.getShelfLocator().addAll(copyClient.getShelfLocator());
                                copy.getElectronicLocator().addAll(copyClient.getElectronicLocator());
                                if (copyClient.getForm() != null) {
                                    StringPlusAuthority str = factory.createStringPlusAuthority();
                                    str.setAuthority(copyClient.getForm().getAuthority());
                                    str.setValue(copyClient.getForm().getValue());
                                    copy.setForm(str);
                                }
                                if (copyClient.getNote() != null) {
                                    for (StringPlusDisplayLabelPlusTypeClient strVal : copyClient.getNote()) {
                                        if (strVal != null) {
                                            StringPlusDisplayLabelPlusType blibBlabBlob =
                                                    factory.createStringPlusDisplayLabelPlusType();
                                            blibBlabBlob.setValue(strVal.getValue());
                                            blibBlabBlob.setDisplayLabel(strVal.getDisplayLabel());
                                            blibBlabBlob.setAtType(strVal.getAtType());
                                            copy.getNote().add(blibBlabBlob);
                                        }
                                    }
                                }
                                if (copyClient.getEnumerationAndChronology() != null) {
                                    for (EnumerationAndChronologyTypeClient val1 : copyClient
                                            .getEnumerationAndChronology()) {
                                        if (val1 != null) {
                                            EnumerationAndChronologyType blibBlabBlob =
                                                    factory.createEnumerationAndChronologyType();
                                            blibBlabBlob.setValue(val1.getValue());
                                            blibBlabBlob.setUnitType(val1.getUnitType());
                                            copy.getEnumerationAndChronology().add(blibBlabBlob);
                                        }
                                    }
                                }
                                hold.getCopyInformation().add(copy);
                            }
                        }
                    }
                    value.setHoldingSimple(hold);
                }

                modsGroup.add(value);
            }
        }
    }

    /**
     * Handle access condition.
     * 
     * @param valueList
     *        the value list
     * @param modsGroup
     *        the mods group
     */
    private static void handleAccessCondition(List<AccessConditionTypeClient> valueList,
                                              List<Object> modsGroup) {
        if (valueList != null && valueList.size() > 0) {
            for (AccessConditionTypeClient valueClient : valueList) {
                if (valueClient == null) continue;
                AccessConditionType value = factory.createAccessConditionType();
                value.getContent().add(valueClient.getContent());
                value.setAtType(valueClient.getType());
                value.setDisplayLabel(valueClient.getDisplayLabel());
                value.setHref(valueClient.getXlink());
                value.setScript(valueClient.getScript());
                value.setLang(valueClient.getLang());
                value.setXmlLang(valueClient.getXmlLang());
                value.setTransliteration(valueClient.getTransliteration());
                modsGroup.add(value);
            }
        }
    }

    /**
     * Handle part client.
     * 
     * @param valueList
     *        the value list
     * @param modsGroup
     *        the mods group
     */
    private static void handlePartClient(List<PartTypeClient> valueList, List<Object> modsGroup) {
        if (valueList != null && valueList.size() > 0) {
            for (PartTypeClient valueClient : valueList) {
                if (valueClient == null) continue;
                PartType value = factory.createPartType();
                value.setID(valueClient.getId());
                value.setType(valueClient.getType());
                value.setOrder(valueClient.getOrder());
                if (valueClient.getText() != null && valueClient.getText().size() > 0) {
                    for (String strVal : valueClient.getText()) {
                        if (strVal != null) {
                            UnstructuredText text = factory.createUnstructuredText();
                            text.setValue(strVal);
                            value.getDetailOrExtentOrDate().add(text);
                        }

                    }
                }
                if (valueClient.getDate() != null) {
                    for (BaseDateTypeClient dateTypeClient : valueClient.getDate()) {
                        if (dateTypeClient != null) {
                            DateOtherType dateType = factory.createDateOtherType();
                            dateType.setValue(dateTypeClient.getValue());
                            dateType.setEncoding(dateTypeClient.getEncoding());
                            dateType.setPoint(dateTypeClient.getPoint());
                            dateType.setQualifier(dateTypeClient.getQualifier());
                            value.getDetailOrExtentOrDate().add(dateType);
                        }
                    }
                }
                if (valueClient.getText() != null && valueClient.getText().size() > 0) {
                    for (String strVal : valueClient.getText()) {
                        if (strVal != null) {
                            UnstructuredText text = factory.createUnstructuredText();
                            text.setValue(strVal);
                            value.getDetailOrExtentOrDate().add(text);
                        }

                    }
                }
                if (valueClient.getExtent() != null) {
                    for (ExtentTypeClient extentClient : valueClient.getExtent()) {
                        if (extentClient != null) {
                            ExtentType e = factory.createExtentType();
                            e.setUnit(extentClient.getUnit());
                            e.setStart(extentClient.getStart());
                            e.setEnd(extentClient.getEnd());
                            e.setTotal(extentClient.getTotal());
                            e.setList(extentClient.getList());
                            value.getDetailOrExtentOrDate().add(e);
                        }
                    }
                }
                if (valueClient.getDetail() != null) {
                    for (DetailTypeClient detailClient : valueClient.getDetail()) {
                        if (detailClient != null) {
                            DetailType detail = factory.createDetailType();
                            detail.setLevel(detailClient.getLevel());
                            detail.setType(detailClient.getType());
                            if (detailClient.getNumber() != null) {
                                for (String strVal : detailClient.getNumber()) {
                                    if (strVal != null)
                                        detail.getNumberOrCaptionOrTitle()
                                                .add(factory.createDetailTypeNumber(strVal));
                                }
                            }
                            if (detailClient.getCaption() != null) {
                                for (String strVal : detailClient.getCaption()) {
                                    if (strVal != null)
                                        detail.getNumberOrCaptionOrTitle()
                                                .add(factory.createDetailTypeCaption(strVal));
                                }
                            }
                            if (detailClient.getTitle() != null) {
                                for (String strVal : detailClient.getTitle()) {
                                    if (strVal != null)
                                        detail.getNumberOrCaptionOrTitle()
                                                .add(factory.createDetailTypeTitle(strVal));
                                }
                            }
                            value.getDetailOrExtentOrDate().add(detail);
                        }
                    }
                }

                modsGroup.add(value);
            }
        }
    }

    /**
     * Handle extension client.
     * 
     * @param valueList
     *        the value list
     * @param modsGroup
     *        the mods group
     */
    private static void handleExtensionClient(List<ExtensionTypeClient> valueList, List<Object> modsGroup) {
        if (valueList != null && valueList.size() > 0) {
            for (ExtensionTypeClient valueClient : valueList) {
                if (valueClient == null || valueClient.getContent() == null) continue;
                ExtensionType value = factory.createExtensionType();
                value.getContent().add(valueClient.getNameSpace() == null ? "" : ("namespace: "
                        + valueClient.getNameSpace() + '\n')
                        + valueClient.getContent());
                modsGroup.add(value);
            }
        }
    }

    /**
     * Handle record info client.
     * 
     * @param valueList
     *        the value list
     * @param modsGroup
     *        the mods group
     */
    private static void handleRecordInfoClient(List<RecordInfoTypeClient> valueList, List<Object> modsGroup) {
        if (valueList != null && valueList.size() > 0) {
            for (RecordInfoTypeClient valueClient : valueList) {
                if (valueClient == null) continue;
                RecordInfoType value = factory.createRecordInfoType();
                value.setScript(valueClient.getScript());
                value.setLang(valueClient.getLang());
                value.setXmlLang(valueClient.getXmlLang());
                value.setTransliteration(valueClient.getTransliteration());
                if (valueClient.getRecordOrigin() != null) {
                    for (String val : valueClient.getRecordOrigin()) {
                        if (val != null) {
                            value.getRecordContentSourceOrRecordCreationDateOrRecordChangeDate()
                                    .add(factory.createRecordInfoTypeRecordOrigin(val));
                        }
                    }
                }
                if (valueClient.getRecordContentSource() != null) {
                    for (StringPlusAuthorityPlusLanguageClient val : valueClient.getRecordContentSource()) {
                        if (val != null) {
                            StringPlusAuthorityPlusLanguage zblunk =
                                    factory.createStringPlusAuthorityPlusLanguage();
                            zblunk.setValue(val.getValue());
                            zblunk.setAuthority(val.getAuthority());
                            zblunk.setLang(val.getLang());
                            zblunk.setScript(val.getScript());
                            zblunk.setXmlLang(val.getXmlLang());
                            zblunk.setTransliteration(val.getTransliteration());
                            value.getRecordContentSourceOrRecordCreationDateOrRecordChangeDate()
                                    .add(factory.createRecordInfoTypeRecordContentSource(zblunk));
                        }
                    }
                }
                if (valueClient.getRecordCreationDate() != null) {
                    for (DateTypeClient dateTypeClient : valueClient.getRecordCreationDate()) {
                        if (dateTypeClient != null) {
                            DateType dateType = factory.createDateType();
                            handleDateClient(dateTypeClient, dateType);
                            value.getRecordContentSourceOrRecordCreationDateOrRecordChangeDate()
                                    .add(factory.createRecordInfoTypeRecordCreationDate(dateType));
                        }
                    }
                }
                if (valueClient.getRecordChangeDate() != null) {
                    for (DateTypeClient dateTypeClient : valueClient.getRecordChangeDate()) {
                        if (dateTypeClient != null) {
                            DateType dateType = factory.createDateType();
                            handleDateClient(dateTypeClient, dateType);
                            value.getRecordContentSourceOrRecordCreationDateOrRecordChangeDate()
                                    .add(factory.createRecordInfoTypeRecordChangeDate(dateType));
                        }
                    }
                }
                if (valueClient.getRecordIdentifier() != null) {
                    for (RecordIdentifierClient val : valueClient.getRecordIdentifier()) {
                        if (val != null) {
                            RecordIdentifier zblunk = factory.createRecordInfoTypeRecordIdentifier();
                            zblunk.setValue(val.getValue());
                            zblunk.setSource(val.getSource());
                            value.getRecordContentSourceOrRecordCreationDateOrRecordChangeDate()
                                    .add(factory.createRecordInfoTypeRecordIdentifier(zblunk));
                        }
                    }
                }
                if (valueClient.getDescriptionStandard() != null) {
                    for (StringPlusAuthorityClient val : valueClient.getDescriptionStandard()) {
                        if (val != null) {
                            StringPlusAuthority zblunk = factory.createStringPlusAuthority();
                            zblunk.setValue(val.getValue());
                            zblunk.setAuthority(val.getAuthority());
                            value.getRecordContentSourceOrRecordCreationDateOrRecordChangeDate()
                                    .add(factory.createRecordInfoTypeDescriptionStandard(zblunk));
                        }
                    }
                }
                List<Object> languages = new ArrayList<Object>();
                handleLanguageClient(valueClient.getLanguageOfCataloging(), languages);
                for (Object o : languages) {
                    if (o != null) {
                        value.getRecordContentSourceOrRecordCreationDateOrRecordChangeDate()
                                .add(factory.createRecordInfoTypeLanguageOfCataloging((LanguageType) o));
                    }
                }
                modsGroup.add(value);
            }
        }
    }

    //    public static void main(String... args) {
    //        C c1 = new C();
    //        c1.a = "a";
    //        B c2 = new B();
    //        c2.a = "a";
    //        c2.b = "b";
    //        A c3 = new A();
    //        c3.a = "a";
    //        C c4 = new C();
    //        B c5 = new B();
    //        c5.a = "";
    //        c5.b = "   ";
    //        A c6 = new A();
    //        c6.a = "  d  ";
    //
    //        System.out.println(hasOnlyNullFields(c1));
    //        System.out.println(hasOnlyNullFields(c2));
    //        System.out.println(hasOnlyNullFields(c3));
    //        System.out.println(hasOnlyNullFields(c4));
    //        System.out.println(hasOnlyNullFields(c5));
    //        System.out.println(hasOnlyNullFields(c6));
    //
    //    }
    //
    //    interface I {
    //
    //        public String i();
    //    }
    //
    //    static class A
    //            implements I {
    //
    //        public String a;
    //
    //        @Override
    //        public String i() {
    //            return null;
    //        }
    //    }
    //
    //    static class B
    //            extends A {
    //
    //        public String b;
    //    }
    //
    //    static class C
    //            extends B {
    //
    //        public String c;
    //    }

}
