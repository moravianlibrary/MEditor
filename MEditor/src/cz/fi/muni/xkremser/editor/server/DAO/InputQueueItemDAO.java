package cz.fi.muni.xkremser.editor.server.DAO;

import java.util.ArrayList;
import java.util.List;

import cz.fi.muni.xkremser.editor.shared.rpc.InputQueueItem;

public interface InputQueueItemDAO {

	void updateItems(List<InputQueueItem> toUpdate);

	ArrayList<InputQueueItem> getItems(String prefix);

}
