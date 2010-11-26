/**
 * Metadata Editor
 * @author Jiri Kremser
 *  
 */
package cz.fi.muni.xkremser.editor.server.modelHandler;

import java.io.IOException;

import cz.fi.muni.xkremser.editor.shared.valueobj.AbstractDigitalObjectDetail;

// TODO: Auto-generated Javadoc
/**
 * The Interface CanGetObject.
 */
public interface CanGetObject {

	/**
	 * Gets the digital object.
	 * 
	 * @param uuid
	 *          the uuid
	 * @return the digital object
	 * @throws IOException
	 */
	AbstractDigitalObjectDetail getDigitalObject(String uuid, final boolean findRelated) throws IOException;
}
