/**
 * Metadata Editor
 * @author Jiri Kremser
 *  
 */
package cz.fi.muni.xkremser.editor.shared.rpc.action;

import java.util.HashMap;

import com.gwtplatform.annotation.GenDispatch;
import com.gwtplatform.annotation.Out;
import com.gwtplatform.dispatch.shared.UnsecuredActionImpl;

// TODO: Auto-generated Javadoc
/**
 * The Class GetClientConfig.
 */
@GenDispatch(isSecure = false)
public class GetClientConfig extends UnsecuredActionImpl<GetClientConfigResult> {

	/** The config. */
	@Out(1)
	HashMap<String, Object> config;
}
