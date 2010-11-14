/**
 * Metadata Editor
 * @author Jiri Kremser
 *  
 */
package cz.fi.muni.xkremser.editor.server.fedora.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
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
import cz.fi.muni.xkremser.editor.client.mods.ClassificationTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.CodeOrTextClient;
import cz.fi.muni.xkremser.editor.client.mods.ExtensionTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.GenreTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.IdentifierTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.LanguageTypeClient;
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
import cz.fi.muni.xkremser.editor.client.mods.RecordInfoTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.RelatedItemTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.RoleTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.RoleTypeClient.RoleTermClient;
import cz.fi.muni.xkremser.editor.client.mods.SubjectTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.TableOfContentsTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.TargetAudienceTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.TitleInfoTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.TypeOfResourceTypeClient;
import cz.fi.muni.xkremser.editor.server.fedora.FedoraNamespaceContext;
import cz.fi.muni.xkremser.editor.server.mods.ModsCollection;
import cz.fi.muni.xkremser.editor.server.mods.ModsType;
import cz.fi.muni.xkremser.editor.server.mods.NamePartType;
import cz.fi.muni.xkremser.editor.server.mods.NameType;
import cz.fi.muni.xkremser.editor.server.mods.RoleType;
import cz.fi.muni.xkremser.editor.server.mods.RoleType.RoleTerm;
import cz.fi.muni.xkremser.editor.server.mods.TitleInfoType;

// TODO: Auto-generated Javadoc
/**
 * The Class BiblioModsUtils.
 */
public final class BiblioModsUtils {

	/** The Constant LOGGER. */
	public static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(BiblioModsUtils.class.getName());

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

	public static ModsCollectionClient toModsClient(ModsCollection mods) {
		if (mods == null)
			throw new NullPointerException("mods");
		ModsCollectionClient modsClient = new ModsCollectionClient();
		List<ModsType> modss = mods.getMods();
		if (modss != null && modss.size() != 0) {
			List<ModsTypeClient> modsClientList = new ArrayList<ModsTypeClient>(modss.size());
			for (ModsType modsType : modss) {
				ModsTypeClient modsTypeClient = new ModsTypeClient();
				modsTypeClient.setId(modsType.getID());
				modsTypeClient.setVersion(modsType.getVersion());

				List<Object> modsGroup = modsType.getModsGroup();
				if (modsGroup != null && modsGroup.size() != 0) {
					for (Object modsElement : modsGroup) {
						if (modsElement instanceof TitleInfoType) {
							if (modsTypeClient.getTitleInfo() == null) {
								modsTypeClient.setTitleInfo(new ArrayList<TitleInfoTypeClient>());
							}
							modsTypeClient.getTitleInfo().add(toModsClient((TitleInfoType) modsElement));
						} else if (modsElement instanceof NameTypeClient) {
							if (modsTypeClient.getName() == null) {
								modsTypeClient.setName(new ArrayList<NameTypeClient>());
							}
							modsTypeClient.getName().add(toModsClient((NameType) modsElement));
						} else if (modsElement instanceof TypeOfResourceTypeClient) {
						} else if (modsElement instanceof GenreTypeClient) {
						} else if (modsElement instanceof OriginInfoTypeClient) {
						} else if (modsElement instanceof LanguageTypeClient) {
						} else if (modsElement instanceof PhysicalDescriptionTypeClient) {
						} else if (modsElement instanceof AbstractTypeClient) {
						} else if (modsElement instanceof TableOfContentsTypeClient) {
						} else if (modsElement instanceof TargetAudienceTypeClient) {
						} else if (modsElement instanceof NoteTypeClient) {
						} else if (modsElement instanceof SubjectTypeClient) {
						} else if (modsElement instanceof ClassificationTypeClient) {
						} else if (modsElement instanceof RelatedItemTypeClient) {
						} else if (modsElement instanceof IdentifierTypeClient) {
						} else if (modsElement instanceof LocationTypeClient) {
						} else if (modsElement instanceof AccessConditionTypeClient) {
						} else if (modsElement instanceof PartTypeClient) {
						} else if (modsElement instanceof ExtensionTypeClient) {
						} else if (modsElement instanceof RecordInfoTypeClient) {
						}

					}
				}

				modsClientList.add(modsTypeClient);
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

}
