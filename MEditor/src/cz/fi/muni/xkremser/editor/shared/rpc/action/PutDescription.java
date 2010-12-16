/**
 * Metadata Editor
 * @author Jiri Kremser
 *  
 */
package cz.fi.muni.xkremser.editor.shared.rpc.action;

import com.gwtplatform.annotation.GenDispatch;
import com.gwtplatform.annotation.In;
import com.gwtplatform.dispatch.shared.UnsecuredActionImpl;

// TODO: Auto-generated Javadoc
/**
 * The Class GetClientConfig.
 */
@GenDispatch(isSecure = false)
public class PutDescription extends UnsecuredActionImpl<PutDescriptionResult> {

	/** The config. */
	@In(1)
	String uuid;

	@In(2)
	String description;

	@In(3)
	boolean common;

}
