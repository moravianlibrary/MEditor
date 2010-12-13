/**
 * Metadata Editor
 * @author Jiri Kremser
 *  
 */
package cz.fi.muni.xkremser.editor.shared.rpc.action;

import java.util.ArrayList;

import com.gwtplatform.annotation.GenDispatch;
import com.gwtplatform.annotation.In;
import com.gwtplatform.annotation.Out;
import com.gwtplatform.dispatch.shared.UnsecuredActionImpl;

import cz.fi.muni.xkremser.editor.shared.rpc.OpenIDItem;
import cz.fi.muni.xkremser.editor.shared.rpc.RoleItem;

// TODO: Auto-generated Javadoc
/**
 * The Class GetRecentlyModified.
 */
@GenDispatch(isSecure = false)
public class GetUserRolesAndIdentities extends UnsecuredActionImpl<ScanInputQueueResult> {

	@In(1)
	private String id;

	@Out(1)
	private ArrayList<RoleItem> roles;

	@Out(2)
	private ArrayList<OpenIDItem> identities;
}
