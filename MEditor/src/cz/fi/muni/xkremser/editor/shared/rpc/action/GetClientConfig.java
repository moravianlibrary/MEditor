/**
 * Metadata Editor
 * @author Jiri Kremser
 *  
 */
package cz.fi.muni.xkremser.editor.shared.rpc.action;

import java.util.HashMap;

import com.gwtplatform.annotation.GenDispatch;
import com.gwtplatform.annotation.Out;

// TODO: Auto-generated Javadoc
/**
 * The Class GetClientConfig.
 */
@GenDispatch(isSecure = false)
public class GetClientConfig {

	/** The config. */
	@Out(1)
	HashMap<String, Object> config;
}
