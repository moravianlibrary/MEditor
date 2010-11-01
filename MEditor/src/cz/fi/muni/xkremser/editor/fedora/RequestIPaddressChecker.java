/**
 * Metadata Editor
 * @author Jiri Kremser
 *  
 */
package cz.fi.muni.xkremser.editor.fedora;

import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import com.google.inject.Inject;
import com.google.inject.Provider;

import cz.fi.muni.xkremser.editor.server.config.EditorConfiguration;

// TODO: Auto-generated Javadoc
/**
 * The Class RequestIPaddressChecker.
 */
public class RequestIPaddressChecker implements IPaddressChecker {

	/** The logger. */
	private final Logger logger;
	
	/** The configuration. */
	@Inject
	// TODO: do setru s tim
	private EditorConfiguration configuration;
	
	/** The provider. */
	private final Provider<HttpServletRequest> provider;

	/**
	 * Instantiates a new request i paddress checker.
	 *
	 * @param provider the provider
	 * @param logger the logger
	 */
	@Inject
	public RequestIPaddressChecker(Provider<HttpServletRequest> provider, Logger logger) {
		super();
		this.provider = provider;
		this.logger = logger;
		this.logger.info("provider is '" + provider + "'");
	}

	/* (non-Javadoc)
	 * @see cz.fi.muni.xkremser.editor.fedora.IPaddressChecker#privateVisitor()
	 */
	@Override
	public boolean privateVisitor() {
		String[] patterns = configuration.getUserAccessPatterns();
		return checkPatterns(patterns);
	}

	/**
	 * Check patterns.
	 *
	 * @param patterns the patterns
	 * @return true, if successful
	 */
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

	/* (non-Javadoc)
	 * @see cz.fi.muni.xkremser.editor.fedora.IPaddressChecker#localHostVisitor()
	 */
	@Override
	public boolean localHostVisitor() {
		String[] patterns = configuration.getAdminAccessPatterns();
		return checkPatterns(patterns);
	}

}
