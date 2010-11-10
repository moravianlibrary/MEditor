/**
 * Metadata Editor
 * @author Jiri Kremser
 *  
 */
package cz.fi.muni.xkremser.editor.server.fedora;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Set;

import javax.xml.xpath.XPathExpressionException;

import org.fedora.api.FedoraAPIA;
import org.fedora.api.FedoraAPIM;
import org.fedora.api.ObjectFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import cz.fi.muni.xkremser.editor.client.KrameriusModel;

// TODO: Auto-generated Javadoc
/**
 * This is main point to access to fedora through REST-API.
 * 
 * @see FedoraAccessImpl
 * @see SecuredFedoraAccessImpl
 * @author pavels
 */
public interface FedoraAccess {

	/**
	 * Returns parsed rels-ext.
	 * 
	 * @param uuid
	 *          Object uuid
	 * @return the rels ext
	 * @throws IOException
	 *           Signals that an I/O exception has occurred.
	 */
	public Document getRelsExt(String uuid) throws IOException;

	/**
	 * Returns KrameriusModel parsed from given document.
	 * 
	 * @param relsExt
	 *          RELS-EXT document
	 * @return the kramerius model
	 * @see KrameriusModel
	 */
	public KrameriusModel getKrameriusModel(Document relsExt);

	/**
	 * Returns KrameriusModel of given object.
	 * 
	 * @param uuid
	 *          uuid of object
	 * @return the kramerius model
	 * @throws IOException
	 *           Signals that an I/O exception has occurred.
	 */
	public KrameriusModel getKrameriusModel(String uuid) throws IOException;

	/**
	 * Recursive processing fedora objects.
	 * 
	 * @param uuid
	 *          UUID of top level object
	 * @param handler
	 *          handler fo handling events
	 * @throws IOException
	 *           Signals that an I/O exception has occurred.
	 */
	public void processRelsExt(String uuid, RelsExtHandler handler) throws IOException;

	/**
	 * Recursive processing fedora objects.
	 * 
	 * @param relsExtDocument
	 *          Document of top level object
	 * @param handler
	 *          handler fo handling events
	 * @throws IOException
	 *           Signals that an I/O exception has occurred.
	 */
	public void processRelsExt(Document relsExtDocument, RelsExtHandler handler) throws IOException;

	/**
	 * Return parsed biblio mods stream.
	 * 
	 * @param uuid
	 *          the uuid
	 * @return the biblio mods
	 * @throws IOException
	 *           Signals that an I/O exception has occurred.
	 */
	public Document getBiblioMods(String uuid) throws IOException;

	/**
	 * Returns DC stream.
	 * 
	 * @param uuid
	 *          the uuid
	 * @return the dC
	 * @throws IOException
	 *           Signals that an I/O exception has occurred.
	 */
	public Document getDC(String uuid) throws IOException;

	/**
	 * Parse, find and returns all pages.
	 * 
	 * @param uuid
	 *          UUID of object
	 * @param deep
	 *          the deep
	 * @return the pages
	 * @throws IOException
	 *           Signals that an I/O exception has occurred.
	 */
	public List<Element> getPages(String uuid, boolean deep) throws IOException;

	/**
	 * Find and returns all pages.
	 * 
	 * @param uuid
	 *          UUID of object
	 * @param rootElementOfRelsExt
	 *          Root element of RelsExt
	 * @return the pages
	 * @throws IOException
	 *           Signals that an I/O exception has occurred.
	 */
	public List<Element> getPages(String uuid, Element rootElementOfRelsExt) throws IOException;

	/**
	 * Returns input stream of thumbnail.
	 * 
	 * @param uuid
	 *          the uuid
	 * @return the thumbnail
	 * @throws IOException
	 *           Signals that an I/O exception has occurred.
	 */
	public InputStream getThumbnail(String uuid) throws IOException;

	/**
	 * Gets the thumbnail profile.
	 * 
	 * @param uuid
	 *          the uuid
	 * @return the thumbnail profile
	 * @throws IOException
	 *           Signals that an I/O exception has occurred.
	 */
	Document getThumbnailProfile(String uuid) throws IOException;

	/**
	 * Gets the thumbnail mime type.
	 * 
	 * @param uuid
	 *          the uuid
	 * @return the thumbnail mime type
	 * @throws IOException
	 *           Signals that an I/O exception has occurred.
	 * @throws XPathExpressionException
	 *           the x path expression exception
	 */
	public String getThumbnailMimeType(String uuid) throws IOException, XPathExpressionException;

	/**
	 * Returns djvu image of the object.
	 * 
	 * @param uuid
	 *          the uuid
	 * @return the image full
	 * @throws IOException
	 *           Signals that an I/O exception has occurred.
	 */
	public InputStream getImageFULL(String uuid) throws IOException;

	/**
	 * REturns profile of full image stream.
	 * 
	 * @param uuid
	 *          the uuid
	 * @return the image full profile
	 * @throws IOException
	 *           Signals that an I/O exception has occurred.
	 */
	public Document getImageFULLProfile(String uuid) throws IOException;

	/**
	 * Returns full image mime type.
	 * 
	 * @param uuid
	 *          the uuid
	 * @return the image full mime type
	 * @throws IOException
	 *           Signals that an I/O exception has occurred.
	 * @throws XPathExpressionException
	 *           the x path expression exception
	 */
	public String getImageFULLMimeType(String uuid) throws IOException, XPathExpressionException;

	/**
	 * Check whether full image is available, is present and accessible.
	 * 
	 * @param uuid
	 *          the uuid
	 * @return true, if is image full available
	 * @throws IOException
	 *           Signals that an I/O exception has occurred.
	 */
	public boolean isImageFULLAvailable(String uuid) throws IOException;

	/**
	 * Checks whetere content is acessiable.
	 * 
	 * @param uuid
	 *          uuid of object which can be protected
	 * @return true, if is content accessible
	 * @throws IOException
	 *           Signals that an I/O exception has occurred.
	 */
	public boolean isContentAccessible(String uuid) throws IOException;

	/**
	 * Checks whetere content is present.
	 * 
	 * @param uuid
	 *          uuid of object which can be protected
	 * @return true, if is digital object present
	 */
	public boolean isDigitalObjectPresent(String uuid);

	/**
	 * Gets the aPIA.
	 * 
	 * @return the aPIA
	 */
	public FedoraAPIA getAPIA();

	/**
	 * Gets the aPIM.
	 * 
	 * @return the aPIM
	 */
	public FedoraAPIM getAPIM();

	/**
	 * Gets the object factory.
	 * 
	 * @return the object factory
	 */
	public ObjectFactory getObjectFactory();

	/**
	 * Process subtree.
	 * 
	 * @param pid
	 *          the pid
	 * @param processor
	 *          the processor
	 */
	public void processSubtree(String pid, TreeNodeProcessor processor);

	/**
	 * Gets the pids.
	 * 
	 * @param pid
	 *          the pid
	 * @return the pids
	 */
	public Set<String> getPids(String pid);

	/**
	 * Gets the data stream.
	 * 
	 * @param pid
	 *          the pid
	 * @param datastreamName
	 *          the datastream name
	 * @return the data stream
	 * @throws IOException
	 *           Signals that an I/O exception has occurred.
	 */
	public InputStream getDataStream(String pid, String datastreamName) throws IOException;

	/**
	 * Gets the mime type for stream.
	 * 
	 * @param pid
	 *          the pid
	 * @param datastreamName
	 *          the datastream name
	 * @return the mime type for stream
	 * @throws IOException
	 *           Signals that an I/O exception has occurred.
	 */
	public String getMimeTypeForStream(String pid, String datastreamName) throws IOException;

	/**
	 * Gets the checks if is on pages uuid.
	 * 
	 * @param uuid
	 *          the uuid
	 * @return the checks if is on pages uuid
	 * @throws IOException
	 *           Signals that an I/O exception has occurred.
	 */
	public List<String> getIsOnPagesUuid(String uuid) throws IOException;

	/**
	 * Gets the pages uuid.
	 * 
	 * @param uuid
	 *          the uuid
	 * @return the pages uuid
	 * @throws IOException
	 *           Signals that an I/O exception has occurred.
	 */
	public List<String> getPagesUuid(String uuid) throws IOException;

	public List<String> getIntCompPartUuid(String uuid) throws IOException;

}
