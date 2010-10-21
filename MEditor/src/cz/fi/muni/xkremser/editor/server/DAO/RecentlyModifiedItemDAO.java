package cz.fi.muni.xkremser.editor.server.DAO;

import java.util.ArrayList;

import cz.fi.muni.xkremser.editor.shared.rpc.RecentlyModifiedItem;

public interface RecentlyModifiedItemDAO {

	boolean put(RecentlyModifiedItem toPut);

	ArrayList<RecentlyModifiedItem> getItems(int nLatest, boolean isForAll);
}
