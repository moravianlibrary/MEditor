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

import cz.fi.muni.xkremser.editor.shared.rpc.action.ScanInputQueueResult;
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

	/** The detail. */
	@Out(1)
	private AbstractDigitalObjectDetail detail;

}