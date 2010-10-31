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
import cz.fi.muni.xkremser.editor.fedora.utils.IOUtils;
import cz.fi.muni.xkremser.editor.fedora.utils.RESTHelper;
import cz.fi.muni.xkremser.editor.server.config.EditorConfiguration;

public class FullImgServiceImpl extends HttpServlet {

	@Inject
	private EditorConfiguration config;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// super.doGet(req, resp);
		// TODO: sekuryta :]

		String uuid = req.getRequestURI().substring(req.getRequestURI().indexOf(Constants.SERVLET_FULL_PREFIX) + Constants.SERVLET_FULL_PREFIX.length() + 1);

		if (uuid != null && !"".equals(uuid)) {
			resp.setContentType("image/jpeg");

			// TODO: handle djvu mime type
			StringBuffer sb = new StringBuffer();
			sb.append(config.getFedoraHost()).append("/objects/").append(Constants.FEDORA_UUID_PREFIX).append(uuid).append("/datastreams/IMG_FULL/content");
			InputStream is = RESTHelper.inputStream(sb.toString(), config.getFedoraLogin(), config.getFedoraPassword());
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

	public EditorConfiguration getConfig() {
		return config;
	}

	public void setConfig(EditorConfiguration config) {
		this.config = config;
	}

	@Override
	public void init() throws ServletException {
		super.init();
		Injector injector = getInjector();
		injector.injectMembers(this);
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		Injector injector = getInjector();
		injector.injectMembers(this);
	}

	protected Injector getInjector() {
		return (Injector) getServletContext().getAttribute(Injector.class.getName());
	}

}
