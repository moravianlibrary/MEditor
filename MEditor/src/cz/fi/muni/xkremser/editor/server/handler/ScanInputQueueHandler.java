package cz.fi.muni.xkremser.editor.server.handler;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.ActionException;

import org.apache.commons.logging.Log;

import com.google.inject.Inject;

import cz.fi.muni.xkremser.editor.server.Z3950Client;
import cz.fi.muni.xkremser.editor.server.config.EditorConfiguration;
import cz.fi.muni.xkremser.editor.shared.rpc.InputQueueItemDetail;
import cz.fi.muni.xkremser.editor.shared.rpc.ScanInputQueue;
import cz.fi.muni.xkremser.editor.shared.rpc.ScanInputQueueResult;

public class ScanInputQueueHandler implements ActionHandler<ScanInputQueue, ScanInputQueueResult> {
	private final Log logger;
	private final EditorConfiguration configuration;

	@Inject
	private Z3950Client client;

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
		final String id = action.getId() == null ? "/" : action.getId();
		logger.info("Scanning input queue: " + base + id);
		ScanInputQueueResult result;
		ArrayList<InputQueueItemDetail> list; // due to gwt performance issues, more
																					// concrete interface is used
		if ("/".equals(id)) { // top level
			String[] types = configuration.getDocumentTypes();
			if (types == null || types.length == 0)
				types = EditorConfiguration.Constants.DOCUMENT_DEFAULT_TYPES;
			list = new ArrayList<InputQueueItemDetail>(types.length);
			for (int i = 0; i < types.length; i++) {
				File test = new File(base + types[i]);
				if (!test.exists()) {
					test.mkdir(); // create if not exists
				}
				list.add(new InputQueueItemDetail(File.separator + types[i], File.separator + types[i]));
			}
			result = new ScanInputQueueResult(id, list);
			return result;
		}

		try {
			File path = new File(base + id);
			FileFilter filter = new FileFilter() {
				@Override
				public boolean accept(File pathname) {
					return !pathname.isFile();
				}

			};
			File[] dirs = path.listFiles(filter);

			list = new ArrayList<InputQueueItemDetail>(dirs.length);
			for (int i = 0; i < dirs.length; i++) {
				list.add(new InputQueueItemDetail(id + File.separator + dirs[i].getName(), dirs[i].getName()));
			}
			result = new ScanInputQueueResult(id, list);
			return result;
		} catch (Exception cause) {
			logger.error("Unable to send message", cause);
			throw new ActionException(cause);
		}
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