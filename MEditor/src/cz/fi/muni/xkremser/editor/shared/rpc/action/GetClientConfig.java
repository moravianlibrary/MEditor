package cz.fi.muni.xkremser.editor.shared.rpc.action;

import java.util.HashMap;

import com.gwtplatform.annotation.GenDispatch;
import com.gwtplatform.annotation.Out;

@GenDispatch(isSecure = false)
public class GetClientConfig {

	@Out(1)
	HashMap<String, Object> config;
}
