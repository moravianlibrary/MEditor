/**
 * Metadata Editor
 * @author Jiri Kremser
 *  
 */
package cz.fi.muni.xkremser.editor.server.handler;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URLConnection;

import org.apache.commons.logging.Log;

import com.google.inject.Inject;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

import cz.fi.muni.xkremser.editor.server.config.EditorConfiguration;
import cz.fi.muni.xkremser.editor.server.fedora.utils.RESTHelper;
import cz.fi.muni.xkremser.editor.shared.rpc.action.CheckAvailability;
import cz.fi.muni.xkremser.editor.shared.rpc.action.CheckAvailabilityAction;
import cz.fi.muni.xkremser.editor.shared.rpc.action.CheckAvailabilityResult;

// TODO: Auto-generated Javadoc
/**
 * The Class PutRecentlyModifiedHandler.
 */
public class CheckAvailabilityHandler implements ActionHandler<CheckAvailabilityAction, CheckAvailabilityResult> {

	/** The logger. */
	private final Log logger;

	/** The configuration. */
	private final EditorConfiguration configuration;

	/**
	 * Instantiates a new put recently modified handler.
	 * 
	 * @param logger
	 *          the logger
	 * @param configuration
	 *          the configuration
	 */
	@Inject
	public CheckAvailabilityHandler(final Log logger, final EditorConfiguration configuration) {
		this.logger = logger;
		this.configuration = configuration;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gwtplatform.dispatch.server.actionhandler.ActionHandler#execute(com
	 * .gwtplatform.dispatch.shared.Action,
	 * com.gwtplatform.dispatch.server.ExecutionContext)
	 */
	@Override
	public CheckAvailabilityResult execute(final CheckAvailabilityAction action, final ExecutionContext context) throws ActionException {
		if (logger.isDebugEnabled()) {
			String serverName = null;
			if (action.getServerId() == CheckAvailability.FEDORA_ID) {
				serverName = "fedora";
			} else if (action.getServerId() == CheckAvailability.KRAMERIUS_ID) {
				serverName = "kramerius";
			}
			logger.debug("Processing action: CheckAvailability: " + serverName);
		}
		boolean status = true;
		String url = null;
		String usr = "";
		String pass = "";
		if (action.getServerId() == CheckAvailability.FEDORA_ID) {
			url = configuration.getFedoraHost();
			usr = configuration.getFedoraLogin();
			pass = configuration.getFedoraPassword();
		} else if (action.getServerId() == CheckAvailability.KRAMERIUS_ID) {
			url = configuration.getKrameriusHost();
		} else {
			throw new ActionException("Unknown server id");
		}
		try {
			URLConnection con = RESTHelper.openConnection(url, usr, pass);
			if (con instanceof HttpURLConnection) {
				HttpURLConnection httpConnection = (HttpURLConnection) con;
				if (httpConnection.getResponseCode() != HttpURLConnection.HTTP_OK) {
					status = false;
					logger.info("Server " + url + " answered with HTTP code " + httpConnection.getResponseCode());
				}
			} else {
				status = false;
			}
		} catch (MalformedURLException e) {
			status = false;
			e.printStackTrace();
		} catch (IOException e) {
			status = false;
			e.printStackTrace();
		}
		return new CheckAvailabilityResult(status, url);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gwtplatform.dispatch.server.actionhandler.ActionHandler#getActionType()
	 */
	@Override
	public Class<CheckAvailabilityAction> getActionType() {
		return CheckAvailabilityAction.class;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gwtplatform.dispatch.server.actionhandler.ActionHandler#undo(com.
	 * gwtplatform.dispatch.shared.Action, com.gwtplatform.dispatch.shared.Result,
	 * com.gwtplatform.dispatch.server.ExecutionContext)
	 */
	@Override
	public void undo(CheckAvailabilityAction action, CheckAvailabilityResult result, ExecutionContext context) throws ActionException {
		// TODO undo method

	}
}