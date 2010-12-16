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

import cz.fi.muni.xkremser.editor.shared.rpc.OpenIDItem;

// TODO: Auto-generated Javadoc
/**
 * The Class GetRecentlyModified.
 */
@GenDispatch(isSecure = false)
public class PutUserIdentity extends UnsecuredActionImpl<PutUserIdentityResult> {

	@In(1)
	private OpenIDItem identity;

	@In(2)
	private String userId;

	@Out(1)
	private String id;

	@Out(2)
	private boolean found;
}
