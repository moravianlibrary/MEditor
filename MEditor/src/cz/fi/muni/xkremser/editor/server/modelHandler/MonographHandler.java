/**
 * Metadata Editor
 * @author Jiri Kremser
 *  
 */
package cz.fi.muni.xkremser.editor.server.modelHandler;

import org.apache.commons.logging.Log;

import com.google.inject.Inject;
import com.google.inject.name.Named;

import cz.fi.muni.xkremser.editor.server.fedora.FedoraAccess;
import cz.fi.muni.xkremser.editor.shared.valueobj.AbstractDigitalObjectDetail;

// TODO: Auto-generated Javadoc
/**
 * The Class MonographHandler.
 */
public class MonographHandler extends DigitalObjectHandler implements CanGetObject {

	/**
	 * Instantiates a new monograph handler.
	 *
	 * @param logger the logger
	 * @param fedoraAccess the fedora access
	 */
	@Inject
	public MonographHandler(Log logger, @Named("securedFedoraAccess") FedoraAccess fedoraAccess) {
		super(logger, fedoraAccess);
	}

	/* (non-Javadoc)
	 * @see cz.fi.muni.xkremser.editor.server.modelHandler.DigitalObjectHandler#getDigitalObject(java.lang.String)
	 */
	@Override
	public AbstractDigitalObjectDetail getDigitalObject(String uuid) {
		System.out.println("ahoj");
		return null;
	}

}
