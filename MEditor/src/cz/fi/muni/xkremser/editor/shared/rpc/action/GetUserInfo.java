/**
 * Metadata Editor
 * @author Jiri Kremser
 *  
 */
package cz.fi.muni.xkremser.editor.shared.rpc.action;

import java.util.ArrayList;

import com.gwtplatform.annotation.GenDispatch;
import com.gwtplatform.annotation.Out;
import com.gwtplatform.dispatch.shared.UnsecuredActionImpl;

import cz.fi.muni.xkremser.editor.shared.rpc.UserInfoItem;

// TODO: Auto-generated Javadoc
/**
 * The Class GetRecentlyModified.
 */
@GenDispatch(isSecure = false)
public class GetUserInfo extends UnsecuredActionImpl<GetUserInfoResult> {

	/** The items. */
	@Out(1)
	private ArrayList<UserInfoItem> items;
}
