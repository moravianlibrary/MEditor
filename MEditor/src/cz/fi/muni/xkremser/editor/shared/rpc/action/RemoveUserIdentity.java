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
 * The Class GetRecentlyModified.
 */
@GenDispatch(isSecure = false)
public class RemoveUserIdentity extends UnsecuredActionImpl<ScanInputQueueResult> {

	@In(1)
	private String id;

}
