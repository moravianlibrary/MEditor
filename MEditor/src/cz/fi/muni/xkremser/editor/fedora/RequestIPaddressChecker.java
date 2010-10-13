package cz.fi.muni.xkremser.editor.fedora;

import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import com.google.inject.Inject;
import com.google.inject.Provider;

import cz.fi.muni.xkremser.editor.fedora.utils.KConfiguration;

public class RequestIPaddressChecker implements IPaddressChecker {

	private final Logger logger;
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
		KConfiguration kConfiguration = KConfiguration.getInstance();
		List<String> patterns = kConfiguration.getPatterns();
		return checkPatterns(patterns);
	}

	private boolean checkPatterns(List<String> patterns) {
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
	// TODO: Controlled by property, it is not only localhost
	public boolean localHostVisitor() {
		List<String> lrControllingAddrs = KConfiguration.getInstance().getLRControllingAddresses();
		return checkPatterns(lrControllingAddrs);
	}

}
