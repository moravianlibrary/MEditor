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

import cz.fi.muni.xkremser.editor.shared.rpc.RecentlyModifiedItem;

// TODO: Auto-generated Javadoc
/**
 * The Class PutRecentlyModified.
 */
@GenDispatch(isSecure = false)
public class PutRecentlyModified extends UnsecuredActionImpl<PutRecentlyModifiedResult> {

	/** The item. */
	@In(1)
	private RecentlyModifiedItem item;

	/** The found. */
	@Out(1)
	private boolean found;

}
