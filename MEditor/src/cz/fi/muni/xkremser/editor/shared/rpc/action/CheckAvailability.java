/**
 * Metadata Editor
 * @author Jiri Kremser
 *  
 */
package cz.fi.muni.xkremser.editor.shared.rpc.action;

import com.gwtplatform.annotation.GenDispatch;
import com.gwtplatform.annotation.In;
import com.gwtplatform.annotation.Out;

// TODO: Auto-generated Javadoc
/**
 * The Class GetClientConfig.
 */
@GenDispatch(isSecure = false)
public class CheckAvailability {
	public static final int FEDORA_ID = 0;
	public static final int KRAMERIUS_ID = 1;

	/** The config. */
	@In(1)
	int serverId;

	@Out(1)
	boolean availability;

	@Out(2)
	String url;
}
