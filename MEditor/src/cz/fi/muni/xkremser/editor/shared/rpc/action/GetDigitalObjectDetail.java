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

import cz.fi.muni.xkremser.editor.shared.valueobj.AbstractDigitalObjectDetail;

// TODO: Auto-generated Javadoc
/**
 * The Class GetDigitalObjectDetail.
 */
@GenDispatch(isSecure = false)
public class GetDigitalObjectDetail extends UnsecuredActionImpl<ScanInputQueueResult> {

	/** The uuid. */
	@In(1)
	private String uuid;

	@In(2)
	private boolean refreshIn;

	/** The detail. */
	@Out(1)
	private AbstractDigitalObjectDetail detail;

	@Out(2)
	private boolean refresh;

}
