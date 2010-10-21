package cz.fi.muni.xkremser.editor.shared.event;

import com.gwtplatform.annotation.GenEvent;
import com.gwtplatform.annotation.Order;

import cz.fi.muni.xkremser.editor.shared.rpc.RecentlyModifiedItem;

@GenEvent
public class DigitalObjectOpened {

	@Order(1)
	boolean statusOK;

	@Order(2)
	RecentlyModifiedItem item;
}