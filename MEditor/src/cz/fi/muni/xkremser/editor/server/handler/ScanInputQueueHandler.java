package cz.fi.muni.xkremser.editor.server.handler;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.ActionException;

import org.apache.commons.logging.Log;

import com.google.inject.Inject;

import cz.fi.muni.xkremser.editor.client.Constants;
import cz.fi.muni.xkremser.editor.server.Z3950Client;
import cz.fi.muni.xkremser.editor.server.DAO.InputQueueItemDAO;
import cz.fi.muni.xkremser.editor.server.config.EditorConfiguration;
import cz.fi.muni.xkremser.editor.shared.rpc.InputQueueItem;
import cz.fi.muni.xkremser.editor.shared.rpc.ScanInputQueue;
import cz.fi.muni.xkremser.editor.shared.rpc.ScanInputQueueResult;

public class ScanInputQueueHandler implements ActionHandler<ScanInputQueue, ScanInputQueueResult> {
	private final Log logger;
	private final EditorConfiguration configuration;

	@Inject
	private Z3950Client client;

	@Inject
	private InputQueueItemDAO inputQueueDAO;

	@Inject
	public ScanInputQueueHandler(final Log logger, final EditorConfiguration configuration) {
		this.logger = logger;
		this.configuration = configuration;
	}

	@Override
	public ScanInputQueueResult execute(final ScanInputQueue action, final ExecutionContext context) throws ActionException {

		final String base = configuration.getScanInputQueuePath();
		if (base == null || "".equals(base)) {
			logger.error("Scanning input queue: Action failed because attribut " + EditorConfiguration.Constants.INPUT_QUEUE + " is not set.");
			return null;
		}
		// parse input
		final String id = action.getId() == null ? "" : action.getId();
		logger.info("Scanning input queue: " + base + id);
		ScanInputQueueResult result;
		ArrayList<InputQueueItem> list; // due to gwt performance issues, more
																		// concrete interface is used
		if (id == null || "".equals(id)) { // top level
			list = inputQueueDAO.getItems(id);
			if (list.size() == 0) {

				String[] types = configuration.getDocumentTypes();
				if (types == null || types.length == 0)
					types = EditorConfiguration.Constants.DOCUMENT_DEFAULT_TYPES;
				list = new ArrayList<InputQueueItem>(types.length);
				for (int i = 0; i < types.length; i++) {
					File test = new File(base + types[i]);
					if (!test.exists()) {
						test.mkdir(); // create if not exists
					}
					list.add(new InputQueueItem(File.separator + types[i], types[i], ""));
				}
				inputQueueDAO.updateItems(scanDirectoryStructure(base, ""));
				result = new ScanInputQueueResult(id, list);
			} else {
				result = new ScanInputQueueResult(id, inputQueueDAO.getItems(id));
			}
		} else {
			result = new ScanInputQueueResult(id, inputQueueDAO.getItems(id));
		}
		return result;
	}

	private List<InputQueueItem> scanDirectoryStructure(String pathPrefix, String relativePath) {
		return scanDirectoryStructure(pathPrefix, relativePath, new ArrayList<InputQueueItem>(), Constants.DIR_MAX_DEPTH);
	}

	private List<InputQueueItem> scanDirectoryStructure(String pathPrefix, String relativePath, List<InputQueueItem> list, int level) {
		if (level == 0)
			return list;
		File path = new File(pathPrefix + relativePath);
		FileFilter filter = new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				return !pathname.isFile();
			}

		};
		File[] dirs = path.listFiles(filter);
		for (int i = 0; i < dirs.length; i++) {
			String rltvpth = relativePath + File.separator + dirs[i].getName();
			list.add(new InputQueueItem(rltvpth, dirs[i].getName(), ""));
			scanDirectoryStructure(pathPrefix, rltvpth, list, level - 1);
		}
		return list;
	}

	@Override
	public void rollback(final ScanInputQueue action, final ScanInputQueueResult result, final ExecutionContext context) throws ActionException {
		// Nothing to do here
	}

	@Override
	public Class<ScanInputQueue> getActionType() {
		return ScanInputQueue.class;
	}
}