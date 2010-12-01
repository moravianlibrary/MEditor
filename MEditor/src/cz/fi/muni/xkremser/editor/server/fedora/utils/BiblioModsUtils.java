/**
 * Metadata Editor
 * @author Jiri Kremser
 *  
 */
package cz.fi.muni.xkremser.editor.server.fedora.utils;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

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

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import cz.fi.muni.xkremser.editor.client.KrameriusModel;
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
import cz.fi.muni.xkremser.editor.server.mods.CopyInformationType;
import cz.fi.muni.xkremser.editor.server.mods.DateOtherType;
import cz.fi.muni.xkremser.editor.server.mods.DateType;
import cz.fi.muni.xkremser.editor.server.mods.DetailType;
import cz.fi.muni.xkremser.editor.server.mods.EnumerationAndChronologyType;
import cz.fi.muni.xkremser.editor.server.mods.ExtensionType;
import cz.fi.muni.xkremser.editor.server.mods.ExtentType;
import cz.fi.muni.xkremser.editor.server.mods.GenreType;
import cz.fi.muni.xkremser.editor.server.mods.HierarchicalGeographicType;
import cz.fi.muni.xkremser.editor.server.mods.IdentifierType;
import cz.fi.muni.xkremser.editor.server.mods.LanguageType;
import cz.fi.muni.xkremser.editor.server.mods.LanguageType.LanguageTerm;
import cz.fi.muni.xkremser.editor.server.mods.LocationType;
import cz.fi.muni.xkremser.editor.server.mods.ModsCollection;
import cz.fi.muni.xkremser.editor.server.mods.ModsType;
import cz.fi.muni.xkremser.editor.server.mods.NamePartType;
import cz.fi.muni.xkremser.editor.server.mods.NameType;
import cz.fi.muni.xkremser.editor.server.mods.NoteType;
import cz.fi.muni.xkremser.editor.server.mods.ObjectFactory;
import cz.fi.muni.xkremser.editor.server.mods.OriginInfoType;
import cz.fi.muni.xkremser.editor.server.mods.PartType;
import cz.fi.muni.xkremser.editor.server.mods.PhysicalDescriptionType;
import cz.fi.muni.xkremser.editor.server.mods.PhysicalLocationType;
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

// TODO: Auto-generated Javadoc
/**
 * The Class BiblioModsUtils.
 */
public final class BiblioModsUtils {

	/** The Constant LOGGER. */
	public static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(BiblioModsUtils.class.getName());
	private static final ObjectFactory factory = new ObjectFactory();

	/**
	 * Gets the page number.
	 * 
	 * @param doc
	 *          the doc
	 * @return the page number
	 */
	public static String getPageNumber(Document doc) {
		try {
			XPathFactory xpfactory = XPathFactory.newInstance();
			XPath xpath = xpfactory.newXPath();
			xpath.setNamespaceContext(new FedoraNamespaceContext());
			XPathExpression expr = xpath.compile("//mods:mods/mods:part/mods:detail[@type='pageNumber']/mods:number/text()");
			Object pageNumber = expr.evaluate(doc, XPathConstants.STRING);
			return (String) pageNumber;
		} catch (XPathExpressionException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			return null;
		} catch (DOMException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			return null;
		}
	}

	/**
	 * Gets the title.
	 * 
	 * @param doc
	 *          the doc
	 * @param model
	 *          the model
	 * @return the title
	 */
	public static String getTitle(Document doc, KrameriusModel model) {
		String title = titleFromBiblioMods(doc);
		if ((title == null) || (title.equals(""))) {
			switch (model) {
				case PERIODICALITEM:
					return PeriodicalItemUtils.getItemNumber(doc) + " (" + PeriodicalItemUtils.getDate(doc) + ")";
				case PERIODICALVOLUME:
					return PeriodicalItemUtils.getItemNumber(doc) + " (" + PeriodicalItemUtils.getDate(doc) + ")";
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
	 *          the doc
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
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			return null;
		} catch (DOMException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			return null;
		}
	}

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

	public static String toXML(ModsCollection collection) {
		try {
			JAXBContext jc = JAXBContext.newInstance("cz.fi.muni.xkremser.editor.server.mods");

			Marshaller marshaller = jc.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			StringWriter sw = new StringWriter();

			marshaller.marshal(collection, sw);
			System.out.println(sw.toString());
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static ModsCollectionClient toModsClient(ModsCollection mods) {
		if (mods == null)
			throw new NullPointerException("mods");
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

	public static ModsCollection toModsServer(ModsCollectionClient modsClient) {
		return null;

	}

	private static TitleInfoTypeClient toModsClient(TitleInfoType titleInfoType) {
		if (titleInfoType == null)
			return null;
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

	private static NameTypeClient toModsClient(NameType nameType) {
		if (nameType == null)
			return null;
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
								roleTermClient.setType(CodeOrTextClient.fromValue(roleTerm.getType().value()));
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

	private static TypeOfResourceTypeClient toModsClient(TypeOfResourceType typeOfResource) {
		if (typeOfResource == null)
			return null;
		TypeOfResourceTypeClient typeOfResourceClient = new TypeOfResourceTypeClient();
		typeOfResourceClient.setValue(typeOfResource.getValue());
		if (typeOfResource.getCollection() != null)
			typeOfResourceClient.setCollection(YesClient.fromValue(typeOfResource.getCollection().value()));
		if (typeOfResource.getManuscript() != null)
			typeOfResourceClient.setManuscript(YesClient.fromValue(typeOfResource.getManuscript().value()));
		return typeOfResourceClient;
	}

	private static StringPlusDisplayLabelClient toModsClient(StringPlusDisplayLabel stringPlusDisplayLabel, Class<? extends StringPlusDisplayLabelClient> clazz) {
		if (stringPlusDisplayLabel == null)
			return null;
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
			decorate((StringPlusDisplayLabelPlusTypeClient) stringPlusDisplayLabelClient, (StringPlusDisplayLabelPlusType) stringPlusDisplayLabel);
		}
		return stringPlusDisplayLabelClient;
	}

	private static void decorate(StringPlusDisplayLabelPlusTypeClient stringPlusDisplayLabelClient, StringPlusDisplayLabelPlusType stringPlusDisplayLabel) {
		if (stringPlusDisplayLabelClient == null || stringPlusDisplayLabel == null)
			return;
		stringPlusDisplayLabelClient.setAtType(stringPlusDisplayLabel.getAtType());
	}

	private static StringPlusAuthorityClient toModsClient(StringPlusAuthority stringPlusAuthority, Class<? extends StringPlusAuthorityClient> clazz) {
		if (stringPlusAuthority == null)
			return null;
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
			decorate((StringPlusAuthorityPlusLanguageClient) stringPlusAuthorityClient, (StringPlusAuthorityPlusLanguage) stringPlusAuthority);
		}
		if (stringPlusAuthority instanceof StringPlusAuthorityPlusTypePlusLanguage) {
			decorate((StringPlusAuthorityPlusTypePlusLanguageClient) stringPlusAuthorityClient, (StringPlusAuthorityPlusTypePlusLanguage) stringPlusAuthority);
		}
		return stringPlusAuthorityClient;
	}

	private static void decorate(StringPlusAuthorityPlusLanguageClient stringPlusAuthorityPlusLanguageClient,
			StringPlusAuthorityPlusLanguage stringPlusAuthorityPlusLanguage) {
		if (stringPlusAuthorityPlusLanguage == null || stringPlusAuthorityPlusLanguageClient == null)
			return;
		stringPlusAuthorityPlusLanguageClient.setXmlLang(stringPlusAuthorityPlusLanguage.getXmlLang());
		stringPlusAuthorityPlusLanguageClient.setLang(stringPlusAuthorityPlusLanguage.getLang());
		stringPlusAuthorityPlusLanguageClient.setScript(stringPlusAuthorityPlusLanguage.getScript());
		stringPlusAuthorityPlusLanguageClient.setTransliteration(stringPlusAuthorityPlusLanguage.getTransliteration());
	}

	private static void decorate(StringPlusAuthorityPlusTypePlusLanguageClient stringPlusAuthorityPlusTypePlusLanguageClient,
			StringPlusAuthorityPlusTypePlusLanguage stringPlusAuthorityPlusTypePlusLanguage) {
		if (stringPlusAuthorityPlusTypePlusLanguage == null || stringPlusAuthorityPlusTypePlusLanguageClient == null)
			return;
		stringPlusAuthorityPlusTypePlusLanguageClient.setType(stringPlusAuthorityPlusTypePlusLanguage.getAtType());
	}

	private static UnstructuredTextClient toModsClient(UnstructuredText unstructuredText, Class<? extends UnstructuredTextClient> clazz) {
		if (unstructuredText == null)
			return null;
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

		return unstructuredTextClient;
	}

	private static DateTypeClient toModsClient(DateType dateType) {
		if (dateType == null)
			return null;
		DateTypeClient dateTypeClient = new DateTypeClient();
		dateTypeClient.setValue(dateType.getValue());
		dateTypeClient.setEncoding(dateType.getEncoding());
		dateTypeClient.setQualifier(dateType.getQualifier());
		dateTypeClient.setPoint(dateType.getPoint());
		if (dateType.getKeyDate() != null)
			dateTypeClient.setKeyDate(YesClient.fromValue(dateType.getKeyDate().value()));
		return dateTypeClient;
	}

	private static DateOtherTypeClient toModsClient(DateOtherType dateOtherType) {
		if (dateOtherType == null)
			return null;
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

	private static OriginInfoTypeClient toModsClient(OriginInfoType originInfoType) {
		if (originInfoType == null)
			return null;
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
				originInfoTypeClient.getFrequency().add(toModsClient((StringPlusAuthority) subElement.getValue(), StringPlusAuthorityClient.class));
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
				if (placeType != null && placeType.getPlaceTerm() != null && placeType.getPlaceTerm().size() > 0) {
					if (originInfoTypeClient.getPlace() == null) {
						originInfoTypeClient.setPlace(new ArrayList<PlaceTypeClient>());
					}
					List<PlaceTermTypeClient> placeTermsTypeClient = new ArrayList<PlaceTermTypeClient>();
					for (PlaceTermType placeTypeTerm : placeType.getPlaceTerm()) {
						PlaceTermTypeClient placeTermTypeClient = new PlaceTermTypeClient();
						placeTermTypeClient.setValue(placeTypeTerm.getValue());
						if (placeTypeTerm.getAuthority() != null)
							placeTermTypeClient.setAuthority(PlaceAuthorityClient.fromValue(placeTypeTerm.getAuthority().value()));
						if (placeTypeTerm.getType() != null)
							placeTermTypeClient.setType(CodeOrTextClient.fromValue(placeTypeTerm.getType().value()));
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

	private static LanguageTypeClient toModsClient(LanguageType languageType) {
		if (languageType == null)
			return null;
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

	private static PhysicalDescriptionTypeClient toModsClient(PhysicalDescriptionType physicalDescriptionType) {
		if (physicalDescriptionType == null)
			return null;
		PhysicalDescriptionTypeClient physicalDescriptionTypeClient = new PhysicalDescriptionTypeClient();
		physicalDescriptionTypeClient.setXmlLang(physicalDescriptionType.getXmlLang());
		physicalDescriptionTypeClient.setLang(physicalDescriptionType.getLang());
		physicalDescriptionTypeClient.setScript(physicalDescriptionType.getScript());
		physicalDescriptionTypeClient.setTransliteration(physicalDescriptionType.getTransliteration());

		List<JAXBElement<?>> subElements = physicalDescriptionType.getFormOrReformattingQualityOrInternetMediaType();
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
				physicalDescriptionTypeClient.getForm().add(
						(StringPlusAuthorityPlusTypeClient) toModsClient((StringPlusAuthorityPlusType) subElement.getValue(), StringPlusAuthorityPlusTypeClient.class));
			} else if (subElement.getName().getLocalPart().equals("note")) {
				if (physicalDescriptionTypeClient.getNote() == null) {
					physicalDescriptionTypeClient.setNote(new ArrayList<NoteTypeClient>());
				}
				physicalDescriptionTypeClient.getNote().add((NoteTypeClient) toModsClient((NoteType) subElement.getValue(), NoteTypeClient.class));
			}
		}

		return physicalDescriptionTypeClient;
	}

	private static SubjectTypeClient toModsClient(SubjectType subjectType) {
		if (subjectType == null)
			return null;
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
						geographicCodeClient.setAuthority(PlaceAuthorityClient.fromValue(geographicCode.getAuthority().value()));
					subjectTypeClient.getGeographicCode().add(geographicCodeClient);
				}
			} else if (subElement.getName().getLocalPart().equals("hierarchicalGeographic")) {
				HierarchicalGeographicType hierarchicalGeographicType = (HierarchicalGeographicType) subElement.getValue();
				if (hierarchicalGeographicType != null) {
					HierarchicalGeographicTypeClient hierarchicalGeographicTypeClient = new HierarchicalGeographicTypeClient();
					if (subjectTypeClient.getHierarchicalGeographic() == null) {
						subjectTypeClient.setHierarchicalGeographic(new ArrayList<HierarchicalGeographicTypeClient>());
					}
					for (JAXBElement<String> subSubElement : hierarchicalGeographicType.getExtraterrestrialAreaOrContinentOrCountry()) {
						if (subSubElement.getName().getLocalPart().equals("extraterrestrialArea")) {
							if (hierarchicalGeographicTypeClient.getExtraterrestrialArea() == null) {
								hierarchicalGeographicTypeClient.setExtraterrestrialArea(new ArrayList<String>());
							}
							hierarchicalGeographicTypeClient.getExtraterrestrialArea().add(subSubElement.getValue());
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

	private static ClassificationTypeClient toModsClient(ClassificationType classificationType) {
		if (classificationType == null)
			return null;
		ClassificationTypeClient classificationTypeClient = new ClassificationTypeClient();
		classificationTypeClient.setDisplayLabel(classificationType.getDisplayLabel());
		classificationTypeClient.setXmlLang(classificationType.getXmlLang());
		classificationTypeClient.setLang(classificationType.getLang());
		classificationTypeClient.setScript(classificationType.getScript());
		classificationTypeClient.setTransliteration(classificationType.getTransliteration());
		return classificationTypeClient;
	}

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
				modsTypeClient.getGenre().add((GenreTypeClient) toModsClient((GenreType) modsElement, GenreTypeClient.class));

				// ORIGIN INFO ELEMENT
			} else if (modsElement instanceof OriginInfoType) {
				if (modsTypeClient.getOriginInfo() == null) {
					modsTypeClient.setOriginInfo(new ArrayList<OriginInfoTypeClient>());
				}
				modsTypeClient.getOriginInfo().add(toModsClient((OriginInfoType) modsElement));

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
				modsTypeClient.getPhysicalDescription().add(toModsClient((PhysicalDescriptionType) modsElement));

				// ABSTRACT ELEMENT
			} else if (modsElement instanceof AbstractType) {
				if (modsTypeClient.getAbstrac() == null) {
					modsTypeClient.setAbstrac(new ArrayList<AbstractTypeClient>());
				}
				modsTypeClient.getAbstrac().add((AbstractTypeClient) toModsClient((AbstractType) modsElement, AbstractTypeClient.class));

				// TOC ELEMENT
			} else if (modsElement instanceof TableOfContentsType) {
				if (modsTypeClient.getTableOfContents() == null) {
					modsTypeClient.setTableOfContents(new ArrayList<TableOfContentsTypeClient>());
				}
				modsTypeClient.getTableOfContents().add((TableOfContentsTypeClient) toModsClient((TableOfContentsType) modsElement, TableOfContentsTypeClient.class));

				// TARGET AUDIENCE ELEMENT
			} else if (modsElement instanceof TargetAudienceType) {
				if (modsTypeClient.getTargetAudience() == null) {
					modsTypeClient.setTargetAudience(new ArrayList<TargetAudienceTypeClient>());
				}
				modsTypeClient.getTargetAudience().add((TargetAudienceTypeClient) toModsClient((TargetAudienceType) modsElement, TargetAudienceTypeClient.class));

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

	private static AccessConditionTypeClient toModsClient(AccessConditionType accessConditionType) {
		if (accessConditionType == null)
			return null;
		AccessConditionTypeClient accessConditionTypeClient = new AccessConditionTypeClient();
		accessConditionTypeClient.setDisplayLabel(accessConditionType.getDisplayLabel());
		accessConditionTypeClient.setType(accessConditionType.getAtType());
		accessConditionTypeClient.setXlink(accessConditionType.getHref());
		accessConditionTypeClient.setXmlLang(accessConditionType.getXmlLang());
		accessConditionTypeClient.setLang(accessConditionType.getLang());
		accessConditionTypeClient.setScript(accessConditionType.getScript());
		accessConditionTypeClient.setTransliteration(accessConditionType.getTransliteration());
		if (accessConditionType.getContent() == null && accessConditionType.getContent().size() > 0) {
			accessConditionTypeClient.setContent(new ArrayList<String>());
		}
		for (Object content : accessConditionType.getContent()) {
			accessConditionTypeClient.getContent().add((String) content);
		}
		return accessConditionTypeClient;
	}

	private static LocationTypeClient toModsClient(LocationType locationType) {
		if (locationType == null)
			return null;
		LocationTypeClient locationTypeClient = new LocationTypeClient();
		if (locationTypeClient.getPhysicalLocation() == null) {
			locationTypeClient.setPhysicalLocation(new ArrayList<PhysicalLocationTypeClient>());
		}
		for (PhysicalLocationType physicalLocationType : locationType.getPhysicalLocation()) {
			locationTypeClient.getPhysicalLocation().add((PhysicalLocationTypeClient) toModsClient(physicalLocationType, PhysicalLocationTypeClient.class));
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
		if (locationType.getHoldingSimple() != null && locationType.getHoldingSimple().getCopyInformation().size() != 0) {
			HoldingSimpleTypeClient holdingSimpleTypeClient = new HoldingSimpleTypeClient();
			holdingSimpleTypeClient.setCopyInformation(new ArrayList<CopyInformationTypeClient>());
			for (CopyInformationType copyInformationType : locationType.getHoldingSimple().getCopyInformation()) {
				CopyInformationTypeClient copyInformationTypeClient = new CopyInformationTypeClient();
				copyInformationTypeClient.setForm(toModsClient(copyInformationType.getForm(), StringPlusAuthorityClient.class));
				copyInformationTypeClient.setSubLocation(copyInformationType.getSubLocation());
				copyInformationTypeClient.setShelfLocator(copyInformationType.getShelfLocator());
				copyInformationTypeClient.setElectronicLocator(copyInformationType.getElectronicLocator());
				if (copyInformationType.getNote().size() != 0) {
					copyInformationTypeClient.setNote(new ArrayList<StringPlusDisplayLabelPlusTypeClient>());
					for (StringPlusDisplayLabelPlusType value : copyInformationType.getNote()) {
						copyInformationTypeClient.getNote().add((StringPlusDisplayLabelPlusTypeClient) toModsClient(value, StringPlusDisplayLabelPlusTypeClient.class));
					}
				}
				if (copyInformationType.getEnumerationAndChronology().size() != 0) {
					copyInformationTypeClient.setEnumerationAndChronology(new ArrayList<EnumerationAndChronologyTypeClient>());
					for (final EnumerationAndChronologyType valuee : copyInformationType.getEnumerationAndChronology()) {
						copyInformationTypeClient.getEnumerationAndChronology().add(new EnumerationAndChronologyTypeClient() {
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

	private static IdentifierTypeClient toModsClient(IdentifierType identifierType) {
		if (identifierType == null)
			return null;
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

	private static RelatedItemTypeClient toModsClient(RelatedItemType relatedItemType) {
		if (relatedItemType == null)
			return null;
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

	private static RecordInfoTypeClient toModsClient(RecordInfoType recordInfoType) {
		if (recordInfoType == null)
			return null;
		RecordInfoTypeClient recordInfoTypeClient = new RecordInfoTypeClient();
		recordInfoTypeClient.setXmlLang(recordInfoType.getXmlLang());
		recordInfoTypeClient.setLang(recordInfoType.getLang());
		recordInfoTypeClient.setScript(recordInfoType.getScript());
		recordInfoTypeClient.setTransliteration(recordInfoType.getTransliteration());

		List<JAXBElement<?>> subElements = recordInfoType.getRecordContentSourceOrRecordCreationDateOrRecordChangeDate();
		for (JAXBElement<?> subElement : subElements) {
			if (subElement.getName().getLocalPart().equals("recordChangeDate")) {
				if (recordInfoTypeClient.getRecordChangeDate() == null) {
					recordInfoTypeClient.setRecordChangeDate(new ArrayList<DateTypeClient>());
				}
				recordInfoTypeClient.getRecordChangeDate().add(toModsClient((DateType) subElement.getValue()));
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
				recordInfoTypeClient.getRecordCreationDate().add(toModsClient((DateType) subElement.getValue()));
			} else if (subElement.getName().getLocalPart().equals("descriptionStandard")) {
				if (recordInfoTypeClient.getDescriptionStandard() == null) {
					recordInfoTypeClient.setDescriptionStandard(new ArrayList<StringPlusAuthorityClient>());
				}
				recordInfoTypeClient.getDescriptionStandard().add(toModsClient((StringPlusAuthority) subElement.getValue(), StringPlusAuthorityClient.class));
			} else if (subElement.getName().getLocalPart().equals("languageOfCataloging")) {
				if (recordInfoTypeClient.getLanguageOfCataloging() == null) {
					recordInfoTypeClient.setLanguageOfCataloging(new ArrayList<LanguageTypeClient>());
				}
				recordInfoTypeClient.getLanguageOfCataloging().add(toModsClient((LanguageType) subElement.getValue()));
			} else if (subElement.getName().getLocalPart().equals("recordContentSource")) {
				if (recordInfoTypeClient.getRecordContentSource() == null) {
					recordInfoTypeClient.setRecordContentSource(new ArrayList<StringPlusAuthorityPlusLanguageClient>());
				}
				recordInfoTypeClient.getRecordContentSource().add(
						(StringPlusAuthorityPlusLanguageClient) toModsClient((StringPlusAuthorityPlusLanguage) subElement.getValue(),
								StringPlusAuthorityPlusLanguageClient.class));
			}
		}
		return recordInfoTypeClient;
	}

	private static ExtensionTypeClient toModsClient(ExtensionType extensionType) {
		if (extensionType == null)
			return null;
		ExtensionTypeClient extensionTypeClient = new ExtensionTypeClient();
		if (extensionType.getContent() == null && extensionType.getContent().size() > 0) {
			extensionTypeClient.setContent(new ArrayList<String>());
		}
		for (Object content : extensionType.getContent()) {
			extensionTypeClient.getContent().add((String) content);
		}
		return extensionTypeClient;
	}

	private static PartTypeClient toModsClient(PartType partType) {
		if (partType == null)
			return null;
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
				if (detailType.getNumberOrCaptionOrTitle() != null && detailType.getNumberOrCaptionOrTitle().size() > 0) {
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

	public static ModsCollection toMods(ModsCollectionClient modsClient) {
		if (modsClient == null)
			throw new NullPointerException("modsClient");
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

	private static ModsType toMods(ModsTypeClient modsTypeClient) {
		if (modsTypeClient != null) {
			ModsType modsType = factory.createModsType();
			modsType.setID(modsTypeClient.getId());
			modsType.setVersion(modsTypeClient.getVersion());
			handleModsGroupClient(modsTypeClient, modsType);
			return modsType;
		} else
			return null;
	}

	private static void handleModsGroupClient(ModsTypeClient modsTypeClient, ModsType modsType) {
		List<Object> modsGroup = modsType.getModsGroup();
		handleTitleInfo(modsTypeClient.getTitleInfo(), modsGroup);
	}

	private static void handleTitleInfo(final List<TitleInfoTypeClient> titleInfoClientList, final List<Object> modsGroup) {
		if (titleInfoClientList != null && titleInfoClientList.size() > 0) {
			for (TitleInfoTypeClient valueClient : titleInfoClientList) {
				TitleInfoType value = new TitleInfoType();
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
							value.getTitleOrSubTitleOrPartNumber().add(factory.createBaseTitleInfoTypeTitle(strVal));
					}
				}
				if (valueClient.getSubTitle() != null) {
					for (String strVal : valueClient.getSubTitle()) {
						if (strVal != null)
							value.getTitleOrSubTitleOrPartNumber().add(factory.createBaseTitleInfoTypeSubTitle(strVal));
					}
				}
				if (valueClient.getPartName() != null) {
					for (String strVal : valueClient.getPartName()) {
						if (strVal != null)
							value.getTitleOrSubTitleOrPartNumber().add(factory.createBaseTitleInfoTypePartName(strVal));
					}
				}
				if (valueClient.getPartNumber() != null) {
					for (String strVal : valueClient.getPartNumber()) {
						if (strVal != null)
							value.getTitleOrSubTitleOrPartNumber().add(factory.createBaseTitleInfoTypePartNumber(strVal));
					}
				}
				if (valueClient.getNonSort() != null) {
					for (String strVal : valueClient.getNonSort()) {
						if (strVal != null)
							value.getTitleOrSubTitleOrPartNumber().add(factory.createBaseTitleInfoTypeNonSort(strVal));
					}
				}
				modsGroup.add(value);
			}
		}
	}

}
