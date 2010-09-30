package cz.fi.muni.xkremser.editor.server.handler;

import java.io.File;
import java.io.FileFilter;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.ActionException;

import org.apache.commons.logging.Log;

import com.google.inject.Inject;
import com.google.inject.Provider;

import cz.fi.muni.xkremser.editor.client.Constants;
import cz.fi.muni.xkremser.editor.server.Z3950Client;
import cz.fi.muni.xkremser.editor.server.Z3950Client.SEARCH_FIELD;
import cz.fi.muni.xkremser.editor.server.config.EditorConfiguration;
import cz.fi.muni.xkremser.editor.shared.rpc.ScanInputQueue;
import cz.fi.muni.xkremser.editor.shared.rpc.ScanInputQueueResult;

public class ScanInputQueueHandler implements ActionHandler<ScanInputQueue, ScanInputQueueResult> {
	private final Log logger;
	private final Provider<EditorConfiguration> configuration;

	@Inject
	private Z3950Client client;

	@Inject
	public ScanInputQueueHandler(final Log logger, final Provider<EditorConfiguration> configuration) {
		this.logger = logger;
		this.configuration = configuration;
	}

	@Override
	public ScanInputQueueResult execute(final ScanInputQueue action, final ExecutionContext context) throws ActionException {
		final Constants.DOC_TYPE doctype = action.getDocumentType();

		try {
			File path = new File(".");
			FileFilter filter = new FileFilter() {
				@Override
				public boolean accept(File pathname) {
					return pathname.isFile();
				}

			};
			File[] dirs = path.listFiles(filter);
			// final String message = "Hello " + action.getName();
			logger.error("vypis");
			logger.error(configuration.get().getConfiguration().getProperty("key"));
			// for (File f : dirs) {
			// logger.info(f.getAbsolutePath());
			// }

			client.search(SEARCH_FIELD.SYSNO, "000266317");

			return null;
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