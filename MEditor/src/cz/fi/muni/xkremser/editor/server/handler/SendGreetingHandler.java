package cz.fi.muni.xkremser.editor.server.handler;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.ActionException;

import org.apache.commons.logging.Log;

import com.google.inject.Inject;
import com.google.inject.Provider;

import cz.fi.muni.xkremser.editor.server.config.EditorConfiguration;
import cz.fi.muni.xkremser.editor.shared.rpc.action.SendGreeting;
import cz.fi.muni.xkremser.editor.shared.rpc.result.SendGreetingResult;

public class SendGreetingHandler implements ActionHandler<SendGreeting, SendGreetingResult> {
	private final Log logger;
	private final Provider<ServletContext> servletContext;
	private final Provider<HttpServletRequest> servletRequest;
	@Inject
	private EditorConfiguration conf;

	@Inject
	public SendGreetingHandler(final Log logger, final Provider<ServletContext> servletContext, final Provider<HttpServletRequest> servletRequest) {
		this.logger = logger;
		this.servletContext = servletContext;
		this.servletRequest = servletRequest;
	}

	@Override
	public SendGreetingResult execute(final SendGreeting action, final ExecutionContext context) throws ActionException {
		final String name = action.getName();

		try {
			String serverInfo = servletContext.get().getServerInfo();

			String userAgent = servletRequest.get().getHeader("User-Agent");

			final String message = "Hello, " + name + "!<br><br>I am running " + serverInfo + ".<br><br>It looks like you are using:<br>" + userAgent;

			// final String message = "Hello " + action.getName();
			System.out.println("valim");
			System.out.println(conf.getConfiguration().getProperty("key"));
			logger.error("slapu");
			logger.error(conf.getConfiguration().getProperty("key"));

			return new SendGreetingResult(name, message);
		} catch (Exception cause) {
			logger.error("Unable to send message", cause);

			throw new ActionException(cause);
		}
	}

	@Override
	public void rollback(final SendGreeting action, final SendGreetingResult result, final ExecutionContext context) throws ActionException {
		// Nothing to do here
	}

	@Override
	public Class<SendGreeting> getActionType() {
		return SendGreeting.class;
	}
}