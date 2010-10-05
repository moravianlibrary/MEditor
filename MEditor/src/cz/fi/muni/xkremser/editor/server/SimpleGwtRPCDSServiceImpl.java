package cz.fi.muni.xkremser.editor.server;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import cz.fi.muni.xkremser.editor.client.gwtrpcds.SimpleGwtRPCDSService;
import cz.fi.muni.xkremser.editor.shared.rpc.InputQueueItem;

/**
 * 
 * @author Aleksandras Novikovas
 * @author System Tier
 * @version 1.0
 */
public class SimpleGwtRPCDSServiceImpl extends RemoteServiceServlet implements SimpleGwtRPCDSService {

	private static final long serialVersionUID = 1L;

	static List<InputQueueItem> list;

	static int id;
	static {
		id = 1;
		list = new ArrayList<InputQueueItem>();
		InputQueueItem record = null;
		// record = new InputQueueItem();
		// record.setId("1");
		// record.setName("First");
		// list.add(record);
		// record = new InputQueueItem();
		// record.setId("2");
		// record.setName("Second");
		// list.add(record);
		// record = new InputQueueItem();
		// record.setId("3");
		// record.setName("Third");
		list.add(record);
	}

	@Override
	public List<InputQueueItem> fetch() {
		return list;
	}

	@Override
	public InputQueueItem add(InputQueueItem record) {
		// record.setId("4"); // new id
		list.add(record);
		return record;
	}

	@Override
	public InputQueueItem update(InputQueueItem record) {
		String recordId = record.getPath();
		if (recordId != null) {
			int index = -1;
			for (int i = 0; i < list.size(); i++) {
				if (recordId.equals(list.get(i).getPath())) {
					index = i;
					break;
				}
			}
			if (index >= 0) {
				list.set(index, record);
				return record;
			}
		}
		return null;
	}

	@Override
	public void remove(InputQueueItem record) {
		String recordId = record.getPath();
		if (recordId != null) {
			int index = -1;
			for (int i = 0; i < list.size(); i++) {
				if (recordId.equals(list.get(i).getPath())) {
					index = i;
					break;
				}
			}
			if (index >= 0) {
				list.remove(index);
			}
		}
	}

}
