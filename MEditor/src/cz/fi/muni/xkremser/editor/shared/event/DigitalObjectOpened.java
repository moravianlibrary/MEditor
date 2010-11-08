/**
 * Metadata Editor
 * @author Jiri Kremser
 *  
 */
package cz.fi.muni.xkremser.editor.shared.event;

import java.util.List;

import com.gwtplatform.annotation.GenEvent;
import com.gwtplatform.annotation.Order;

import cz.fi.muni.xkremser.editor.shared.rpc.RecentlyModifiedItem;

// TODO: Auto-generated Javadoc
/**
 * The Class DigitalObjectOpened.
 */
@GenEvent
public class DigitalObjectOpened {

	/** The status ok. */
	@Order(1)
	boolean statusOK;

	/** The item. */
	@Order(2)
	RecentlyModifiedItem item;

	@Order(3)
	List<? extends List<String>> related;
}