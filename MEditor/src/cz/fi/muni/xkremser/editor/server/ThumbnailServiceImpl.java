/**
 * Metadata Editor
 * @author Jiri Kremser
 *  
 */
package cz.fi.muni.xkremser.editor.server;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.inject.Inject;
import com.google.inject.Injector;

import cz.fi.muni.xkremser.editor.client.Constants;
import cz.fi.muni.xkremser.editor.server.config.EditorConfiguration;
import cz.fi.muni.xkremser.editor.server.fedora.utils.IOUtils;
import cz.fi.muni.xkremser.editor.server.fedora.utils.RESTHelper;

// TODO: Auto-generated Javadoc
/**
 * The Class ThumbnailServiceImpl.
 */
public class ThumbnailServiceImpl extends HttpServlet {

	/** The config. */
	@Inject
	private EditorConfiguration config;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// super.doGet(req, resp);
		// TODO: sekuryta :]

		// char '/' is twice present in "/thumbnail/XYZ"
		// /meditor ==> nefunguje
		String uuid = req.getRequestURI().substring(
				req.getRequestURI().indexOf(Constants.SERVLET_THUMBNAIL_PREFIX) + Constants.SERVLET_THUMBNAIL_PREFIX.length() + 1);

		if (uuid != null && !"".equals(uuid)) {
			resp.setContentType("image/jpeg");
			StringBuffer sb = new StringBuffer();
			sb.append(config.getFedoraHost()).append("/objects/").append(Constants.FEDORA_UUID_PREFIX).append(uuid).append("/datastreams/IMG_THUMB/content");
			InputStream is = RESTHelper.inputStream(sb.toString(), config.getFedoraLogin(), config.getFedoraPassword());
			if (is == null) {
				return;
			}
			ServletOutputStream os = resp.getOutputStream();

			try {
				IOUtils.copyStreams(is, os);

			} catch (IOException e) {
				// TODO: zalogovat
			} finally {
				os.flush();
				if (is != null) {
					try {
						is.close();
					} catch (IOException e) {
						// TODO: zalogovat
					} finally {
						is = null;
					}
				}
			}
			resp.setStatus(200);
		} else {
			resp.setStatus(404);
		}
	}

	/**
	 * Gets the config.
	 * 
	 * @return the config
	 */
	public EditorConfiguration getConfig() {
		return config;
	}

	/**
	 * Sets the config.
	 * 
	 * @param config
	 *          the new config
	 */
	public void setConfig(EditorConfiguration config) {
		this.config = config;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.GenericServlet#init()
	 */
	@Override
	public void init() throws ServletException {
		super.init();
		Injector injector = getInjector();
		injector.injectMembers(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.GenericServlet#init(javax.servlet.ServletConfig)
	 */
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		Injector injector = getInjector();
		injector.injectMembers(this);
	}

	/**
	 * Gets the injector.
	 * 
	 * @return the injector
	 */
	protected Injector getInjector() {
		return (Injector) getServletContext().getAttribute(Injector.class.getName());
	}

}
