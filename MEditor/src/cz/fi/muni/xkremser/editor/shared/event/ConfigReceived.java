package cz.fi.muni.xkremser.editor.shared.event;

import com.gwtplatform.annotation.GenEvent;
import com.gwtplatform.annotation.Order;

@GenEvent
public class ConfigReceived {
	// @Order(1)
	// HasEventBus source;

	@Order(1)
	boolean statusOK;
}