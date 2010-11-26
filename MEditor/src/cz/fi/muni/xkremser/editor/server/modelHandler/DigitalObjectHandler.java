/**
 * Metadata Editor
 * @author Jiri Kremser
 *  
 */
package cz.fi.muni.xkremser.editor.server.modelHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.fedora.api.RelationshipTuple;
import org.w3c.dom.Document;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.name.Named;
import com.uwyn.jhighlight.renderer.XhtmlRendererFactory;

import cz.fi.muni.xkremser.editor.client.Constants;
import cz.fi.muni.xkremser.editor.client.KrameriusModel;
import cz.fi.muni.xkremser.editor.client.mods.ModsCollectionClient;
import cz.fi.muni.xkremser.editor.server.config.KrameriusModelMapping;
import cz.fi.muni.xkremser.editor.server.fedora.FedoraAccess;
import cz.fi.muni.xkremser.editor.server.fedora.utils.BiblioModsUtils;
import cz.fi.muni.xkremser.editor.server.fedora.utils.DCUtils;
import cz.fi.muni.xkremser.editor.server.fedora.utils.FedoraUtils;
import cz.fi.muni.xkremser.editor.server.mods.ModsCollection;
import cz.fi.muni.xkremser.editor.shared.valueobj.AbstractDigitalObjectDetail;
import cz.fi.muni.xkremser.editor.shared.valueobj.metadata.DublinCore;

// TODO: Auto-generated Javadoc
/**
 * The Class DigitalObjectHandler.
 */
public class DigitalObjectHandler implements CanGetObject {

	/** The fedora access. */
	private final FedoraAccess fedoraAccess;

	/** The logger. */
	private final Log logger;

	/** The injector. */
	@Inject
	private Injector injector;

	/**
	 * Instantiates a new digital object handler.
	 * 
	 * @param logger
	 *          the logger
	 * @param fedoraAccess
	 *          the fedora access
	 */
	@Inject
	public DigitalObjectHandler(final Log logger, @Named("securedFedoraAccess") FedoraAccess fedoraAccess) {
		this.logger = logger;
		this.fedoraAccess = fedoraAccess;
	}

	protected ArrayList<ArrayList<String>> getRelated(final String uuid) {
		List<RelationshipTuple> triplets = FedoraUtils.getSubjectPids(Constants.FEDORA_UUID_PREFIX + uuid);
		ArrayList<ArrayList<String>> returnList = new ArrayList<ArrayList<String>>(triplets.size());
		for (RelationshipTuple triplet : triplets) {
			ArrayList<String> relatedRecord = new ArrayList<String>(2);
			String subject = triplet.getSubject().substring((Constants.FEDORA_INFO_PREFIX + Constants.FEDORA_UUID_PREFIX).length());
			String predicate = triplet.getPredicate().substring(Constants.FEDORA_INFO_PREFIX.length());
			relatedRecord.add(subject);
			relatedRecord.add(predicate);
			returnList.add(relatedRecord);
		}
		return returnList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cz.fi.muni.xkremser.editor.server.modelHandler.CanGetObject#getDigitalObject
	 * (java.lang.String)
	 */
	@Override
	public AbstractDigitalObjectDetail getDigitalObject(String uuid, final boolean findRelated) throws IOException {
		KrameriusModel model = null;
		try {
			model = fedoraAccess.getKrameriusModel(uuid);
		} catch (IOException e) {
			logger.warn("Could not get model of object " + uuid + ". Using generic model handler.", e);
			throw e;
		}
		CanGetObject handler = null;
		if (model != null) {
			handler = injector.getInstance(KrameriusModelMapping.TYPES.get(model));
		} else {
			handler = injector.getInstance(GenericHandler.class);
		}
		AbstractDigitalObjectDetail detail = handler.getDigitalObject(uuid, findRelated);
		detail.setMods(handleMods(uuid));
		detail.setFoxml(handleFOXML(uuid));
		return detail;
	}

	/**
	 * Gets the fedora access.
	 * 
	 * @return the fedora access
	 */
	public FedoraAccess getFedoraAccess() {
		return fedoraAccess;
	}

	protected DublinCore handleDc(String uuid, boolean onlyTitleAndUuid) {
		DublinCore dc = null;
		Document dcDocument = null;
		try {
			dcDocument = getFedoraAccess().getDC(uuid);
			if (onlyTitleAndUuid) {
				dc = DCUtils.getDC(dcDocument);
			} else {
				dc = new DublinCore();
				dc.addTitle(DCUtils.titleFromDC(dcDocument));
				dc.addIdentifier(uuid);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return dc;
	}

	protected String handleFOXML(String uuid) {
		String returnString = null;
		returnString = getFedoraAccess().getFOXML(uuid);
		String highlighted = null;
		try {
			highlighted = XhtmlRendererFactory.getRenderer("xml").highlight("foxml", returnString, "UTF-8", true);
		} catch (IOException e) {
			e.printStackTrace();
			return returnString;
		}
		return highlighted.substring(highlighted.indexOf('\n'));
	}

	protected ModsCollectionClient handleMods(String uuid) {
		ModsCollectionClient modsClient = null;
		ModsCollection mods = null;
		Document modsDocument = null;
		try {
			modsDocument = getFedoraAccess().getBiblioMods(uuid);
			// File file = new
			// File("/home/kremser/workspace/MEditor/test/cz/fi/muni/xkremser/editor/client/Website_mods.xml");
			// DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			// dbf.setNamespaceAware(true);
			// DocumentBuilder db = dbf.newDocumentBuilder();
			// Document mockModsDocument = db.parse(file);
			// long start = System.currentTimeMillis();
			// mods = BiblioModsUtils.getMods(mockModsDocument);
			mods = BiblioModsUtils.getMods(modsDocument);
			modsClient = BiblioModsUtils.toModsClient(mods);
			// System.out.println("duration: " + (System.currentTimeMillis() - start)
			// + " ms.");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) { // TODO: remove
			e.printStackTrace();
		}
		return modsClient;
	}

}
