/**
 * Metadata Editor
 * @author Jiri Kremser
 *  
 */
package cz.fi.muni.xkremser.editor.server;

import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.xpath.XPathExpressionException;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.name.Named;

import cz.fi.muni.xkremser.editor.client.Constants;
import cz.fi.muni.xkremser.editor.server.config.EditorConfiguration;
import cz.fi.muni.xkremser.editor.server.fedora.FedoraAccess;
import cz.fi.muni.xkremser.editor.server.fedora.ImageMimeType;
import cz.fi.muni.xkremser.editor.server.fedora.KrameriusImageSupport;
import cz.fi.muni.xkremser.editor.server.fedora.utils.FedoraUtils;
import cz.fi.muni.xkremser.editor.server.fedora.utils.IOUtils;
import cz.fi.muni.xkremser.editor.server.fedora.utils.RESTHelper;

// TODO: Auto-generated Javadoc
/**
 * The Class FullImgServiceImpl.
 */
public class FullImgServiceImpl extends HttpServlet {

	@Inject
	private EditorConfiguration config;

	@Inject
	@Named("securedFedoraAccess")
	private FedoraAccess fedoraAccess;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO: sekuryta :]
		// resp.setDateHeader("Last Modified", lastModifiedDate.getTime());
		// resp.setDateHeader("Last Fetched", System.currentTimeMillis());
		// resp.setDateHeader("Expires", instance.getTime().getTime());

		String uuid = req.getRequestURI().substring(req.getRequestURI().indexOf(Constants.SERVLET_FULL_PREFIX) + Constants.SERVLET_FULL_PREFIX.length() + 1);
		ServletOutputStream os = resp.getOutputStream();
		if (uuid != null && !"".equals(uuid)) {

			try {
				String mimetype = fedoraAccess.getMimeTypeForStream("uuid:" + uuid, FedoraUtils.IMG_FULL_STREAM);
				ImageMimeType loadFromMimeType = ImageMimeType.loadFromMimeType(mimetype);
				if (loadFromMimeType == ImageMimeType.JPEG || loadFromMimeType == ImageMimeType.PNG) {
					StringBuffer sb = new StringBuffer();
					sb.append(config.getFedoraHost()).append("/objects/").append(Constants.FEDORA_UUID_PREFIX).append(uuid).append("/datastreams/IMG_FULL/content");
					InputStream is = RESTHelper.inputStream(sb.toString(), config.getFedoraLogin(), config.getFedoraPassword());
					try {
						IOUtils.copyStreams(is, os);
					} catch (IOException e) {
						resp.setStatus(HttpURLConnection.HTTP_NOT_FOUND);
						// TODO: zalogovat
					} finally {
						os.flush();
						if (is != null) {
							try {
								is.close();
							} catch (IOException e) {
								resp.setStatus(HttpURLConnection.HTTP_NOT_FOUND);
								// TODO: zalogovat
							} finally {
								is = null;
							}
						}
					}
				} else {
					Image rawImg = KrameriusImageSupport.readImage(uuid, FedoraUtils.IMG_FULL_STREAM, this.fedoraAccess, 0, loadFromMimeType);
					Image scaled = KrameriusImageSupport.getSmallerImage(rawImg);
					KrameriusImageSupport.writeImageToStream(scaled, "JPG", os);
					resp.setContentType(ImageMimeType.JPEG.getValue());
					resp.setStatus(HttpURLConnection.HTTP_OK);
				}
			} catch (XPathExpressionException e1) {
				resp.setStatus(HttpURLConnection.HTTP_NOT_FOUND);
				e1.printStackTrace();
				// TODO: log exception
			} finally {
				os.flush();
			}
		}
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
