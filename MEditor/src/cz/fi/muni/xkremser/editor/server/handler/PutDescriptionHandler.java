/**
 * Metadata Editor
 * @author Jiri Kremser
 *  
 */
package cz.fi.muni.xkremser.editor.server.handler;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

import cz.fi.muni.xkremser.editor.server.HttpCookies;
import cz.fi.muni.xkremser.editor.server.DAO.RecentlyModifiedItemDAO;
import cz.fi.muni.xkremser.editor.server.config.EditorConfiguration;
import cz.fi.muni.xkremser.editor.shared.rpc.action.PutDescriptionAction;
import cz.fi.muni.xkremser.editor.shared.rpc.action.PutDescriptionResult;

// TODO: Auto-generated Javadoc
/**
 * The Class PutRecentlyModifiedHandler.
 */
public class PutDescriptionHandler implements ActionHandler<PutDescriptionAction, PutDescriptionResult> {

	/** The logger. */
	private final Log logger;

	/** The configuration. */
	private final EditorConfiguration configuration;

	/** The recently modified dao. */
	@Inject
	private RecentlyModifiedItemDAO recentlyModifiedDAO;

	@Inject
	private Provider<HttpSession> httpSessionProvider;

	/**
	 * Instantiates a new put recently modified handler.
	 * 
	 * @param logger
	 *          the logger
	 * @param configuration
	 *          the configuration
	 */
	@Inject
	public PutDescriptionHandler(final Log logger, final EditorConfiguration configuration) {
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
	public PutDescriptionResult execute(final PutDescriptionAction action, final ExecutionContext context) throws ActionException {
		if (action.getUuid() == null || "".equals(action.getUuid()))
			throw new NullPointerException("getUuid()");
		if (action.getDescription() == null)
			throw new NullPointerException("getDescription()");
		logger.debug("Processing action: PutDescription: " + action.getUuid());
		HttpSession session = httpSessionProvider.get();
		String openID = (String) session.getAttribute(HttpCookies.SESSION_ID_KEY);
		if (action.isCommon()) {
			recentlyModifiedDAO.putDescription(action.getUuid(), action.getDescription());
		} else {
			recentlyModifiedDAO.putUserDescription(openID, action.getUuid(), action.getDescription());
		}
		return new PutDescriptionResult();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gwtplatform.dispatch.server.actionhandler.ActionHandler#getActionType()
	 */
	@Override
	public Class<PutDescriptionAction> getActionType() {
		return PutDescriptionAction.class;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gwtplatform.dispatch.server.actionhandler.ActionHandler#undo(com.
	 * gwtplatform.dispatch.shared.Action, com.gwtplatform.dispatch.shared.Result,
	 * com.gwtplatform.dispatch.server.ExecutionContext)
	 */
	@Override
	public void undo(PutDescriptionAction action, PutDescriptionResult result, ExecutionContext context) throws ActionException {
		// TODO undo method

	}
}