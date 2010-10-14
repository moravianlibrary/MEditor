package cz.fi.muni.xkremser.editor.fedora;

import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import com.google.inject.Inject;
import com.google.inject.Provider;

import cz.fi.muni.xkremser.editor.server.config.EditorConfiguration;

public class RequestIPaddressChecker implements IPaddressChecker {

	private final Logger logger;
	@Inject
	// TODO: do setru s tim
	private EditorConfiguration configuration;
	private final Provider<HttpServletRequest> provider;

	@Inject
	public RequestIPaddressChecker(Provider<HttpServletRequest> provider, Logger logger) {
		super();
		this.provider = provider;
		this.logger = logger;
		this.logger.info("provider is '" + provider + "'");
	}

	@Override
	public boolean privateVisitor() {
		String[] patterns = configuration.getUserAccessPatterns();
		return checkPatterns(patterns);
	}

	private boolean checkPatterns(String[] patterns) {
		HttpServletRequest httpServletRequest = this.provider.get();
		String remoteAddr = httpServletRequest.getRemoteAddr();
		if (patterns != null) {
			for (String regex : patterns) {
				if (remoteAddr.matches(regex))
					return true;
			}
		}
		logger.info("Remote address is == " + remoteAddr);
		return false;
	}

	@Override
	public boolean localHostVisitor() {
		String[] patterns = configuration.getAdminAccessPatterns();
		return checkPatterns(patterns);
	}

}
