package cz.fi.muni.xkremser.editor.server.handler;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

import cz.fi.muni.xkremser.editor.client.Constants;
import cz.fi.muni.xkremser.editor.client.KrameriusModel;
import cz.fi.muni.xkremser.editor.fedora.FedoraAccess;
import cz.fi.muni.xkremser.editor.server.Z3950Client;
import cz.fi.muni.xkremser.editor.server.DAO.InputQueueItemDAO;
import cz.fi.muni.xkremser.editor.server.config.EditorConfiguration;
import cz.fi.muni.xkremser.editor.shared.rpc.InputQueueItem;
import cz.fi.muni.xkremser.editor.shared.rpc.action.ScanInputQueueAction;
import cz.fi.muni.xkremser.editor.shared.rpc.action.ScanInputQueueResult;

public class ScanInputQueueHandler implements ActionHandler<ScanInputQueueAction, ScanInputQueueResult> {
	private final Log logger;
	private final EditorConfiguration configuration;

	@Inject
	private Z3950Client client;

	@Inject
	@Named("securedFedoraAccess")
	private FedoraAccess fedoraAccess;

	@Inject
	private InputQueueItemDAO inputQueueDAO;

	@Inject
	public ScanInputQueueHandler(final Log logger, final EditorConfiguration configuration) {
		this.logger = logger;
		this.configuration = configuration;
	}

	@Override
	public ScanInputQueueResult execute(final ScanInputQueueAction action, final ExecutionContext context) throws ActionException {
		// parse input
		final String id = action.getId() == null ? "" : action.getId();
		final boolean refresh = action.isRefresh();
		final String base = configuration.getScanInputQueuePath();
		logger.debug("Processing input queue: " + base + id);
		ScanInputQueueResult result = null;

		if (!refresh) {
			if (base == null || "".equals(base)) {
				logger.error("Scanning input queue: Action failed because attribut " + EditorConfiguration.Constants.INPUT_QUEUE + " is not set.");
				throw new ActionException("Scanning input queue: Action failed because attribut " + EditorConfiguration.Constants.INPUT_QUEUE + " is not set.");
			}
			ArrayList<InputQueueItem> list; // due to gwt performance issues, more
																			// concrete interface is used
			if (id == null || "".equals(id)) { // top level
				if ((list = inputQueueDAO.getItems(id)).size() == 0) { // empty db
					result = new ScanInputQueueResult(updateDb(base));
				} else {
					result = new ScanInputQueueResult(list);
				}
			} else {
				result = new ScanInputQueueResult(inputQueueDAO.getItems(id));
			}
		}

		if (refresh) {
			result = new ScanInputQueueResult(updateDb(base));
		}
		return result;
	}

	private void checkDocumentTypes(String[] types) throws ActionException {
		for (String uuid : types) {
			if (!fedoraAccess.isDigitalObjectPresent(Constants.FEDORA_MODEL_PREFIX + KrameriusModel.toString(KrameriusModel.parseString(uuid)))) {
				logger.error("Model " + uuid + " is not present in repository.");
				throw new ActionException(Constants.FEDORA_MODEL_PREFIX + uuid);
			}
		}
	}

	private ArrayList<InputQueueItem> updateDb(String base) {
		String[] types = configuration.getDocumentTypes();
		if (types == null || types.length == 0)
			types = EditorConfiguration.Constants.DOCUMENT_DEFAULT_TYPES;
		try {
			checkDocumentTypes(types);
		} catch (ActionException e) {
			logger.warn("Unsupported fedora model, check your configuration.properties for documentTypes. They have to be the same as models in Fedora Commons repository.");
		}
		ArrayList<InputQueueItem> list = new ArrayList<InputQueueItem>();
		ArrayList<InputQueueItem> listTopLvl = new ArrayList<InputQueueItem>(types.length);
		for (int i = 0; i < types.length; i++) {
			File test = new File(base + File.separator + types[i]);
			if (!test.exists()) {
				test.mkdir(); // create if not exists
			}
			listTopLvl.add(new InputQueueItem(File.separator + types[i], types[i], ""));
			list.add(new InputQueueItem(File.separator + types[i], types[i], ""));
			list.addAll(scanDirectoryStructure(base, File.separator + types[i]));
		}
		inputQueueDAO.updateItems(list);
		return listTopLvl;

	}

	// private

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
	public Class<ScanInputQueueAction> getActionType() {
		return ScanInputQueueAction.class;
	}

	@Override
	public void undo(ScanInputQueueAction action, ScanInputQueueResult result, ExecutionContext context) throws ActionException {
		// TODO Auto-generated method stub

	}
}