/**
 * Metadata Editor
 * @author Jiri Kremser
 *  
 */
package cz.fi.muni.xkremser.editor.server.DAO;

import java.util.ArrayList;
import java.util.List;

import cz.fi.muni.xkremser.editor.shared.rpc.InputQueueItem;

// TODO: Auto-generated Javadoc
/**
 * The Interface InputQueueItemDAO.
 */
public interface InputQueueItemDAO {

	/**
	 * Update items.
	 *
	 * @param toUpdate the to update
	 */
	void updateItems(List<InputQueueItem> toUpdate);

	/**
	 * Gets the items.
	 *
	 * @param prefix the prefix
	 * @return the items
	 */
	ArrayList<InputQueueItem> getItems(String prefix);

}
