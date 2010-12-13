/**
 * Metadata Editor
 * @author Jiri Kremser
 *  
 */
package cz.fi.muni.xkremser.editor.shared.rpc.action;

import com.gwtplatform.annotation.GenDispatch;
import com.gwtplatform.annotation.In;
import com.gwtplatform.annotation.Out;
import com.gwtplatform.dispatch.shared.UnsecuredActionImpl;

import cz.fi.muni.xkremser.editor.shared.rpc.UserInfoItem;

// TODO: Auto-generated Javadoc
/**
 * The Class GetRecentlyModified.
 */
@GenDispatch(isSecure = false)
public class PutUserInfo extends UnsecuredActionImpl<ScanInputQueueResult> {

	@In(1)
	private UserInfoItem user;

	@Out(1)
	private boolean found;
}
