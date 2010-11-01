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

import cz.fi.muni.xkremser.editor.shared.rpc.InputQueueItem;

// TODO: Auto-generated Javadoc
/**
 * The Class ScanInputQueue.
 */
@GenDispatch(isSecure = false)
public class ScanInputQueue extends UnsecuredActionImpl<ScanInputQueueResult> {
	
	/** The id. */
	@In(1)
	private String id;

	/** The refresh. */
	@In(2)
	private boolean refresh;

	/** The items. */
	@Out(1)
	private ArrayList<InputQueueItem> items;
}
