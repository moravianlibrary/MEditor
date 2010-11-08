/**
 * Metadata Editor
 * @author Jiri Kremser
 *  
 */
package cz.fi.muni.xkremser.editor.shared.event;

import com.gwtplatform.annotation.GenEvent;
import com.gwtplatform.annotation.Order;

// TODO: Auto-generated Javadoc
/**
 * The Class ConfigReceived.
 */
@GenEvent
public class DigitalObjectClosed {
	// @Order(1)
	// HasEventBus source;

	/** The status ok. */

	@Order(1)
	String uuid;
}