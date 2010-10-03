package cz.fi.muni.xkremser.editor.client.gwtrpcds;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * 
 * @author Aleksandras Novikovas
 * @author System Tier
 * @version 1.0
 */
@RemoteServiceRelativePath("/SimpleGwtRPCDSService")
public interface SimpleGwtRPCDSService extends RemoteService {

	List<SimpleGwtRPCDSRecord> fetch();

	SimpleGwtRPCDSRecord add(SimpleGwtRPCDSRecord record);

	SimpleGwtRPCDSRecord update(SimpleGwtRPCDSRecord record);

	void remove(SimpleGwtRPCDSRecord record);

	public static class Util {
		private static SimpleGwtRPCDSServiceAsync instance;

		public static SimpleGwtRPCDSServiceAsync getInstance() {
			return (instance == null) ? (instance = GWT.create(SimpleGwtRPCDSService.class)) : instance;
		}
	}

}
