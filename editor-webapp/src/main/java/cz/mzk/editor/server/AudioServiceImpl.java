package cz.mzk.editor.server;


import com.google.inject.Injector;
import cz.mzk.editor.client.util.Constants;
import cz.mzk.editor.server.config.EditorConfiguration;
import cz.mzk.editor.server.util.IOUtils;
import org.apache.log4j.Logger;

import javax.inject.Inject;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;

public class AudioServiceImpl extends HttpServlet {

    private static final long serialVersionUID = 4395794239268092097L;

    /**
     * The logger.
     */
    private static final Logger LOGGER = Logger.getLogger(PdfServiceImpl.class.getPackage().toString());

    /**
     * The configuration.
     */
    @Inject
    private EditorConfiguration config;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
            IOException {
        String audioPath = req.getPathInfo();

        if (audioPath == null || "".equals(audioPath)) {
            resp.setStatus(HttpURLConnection.HTTP_NOT_FOUND);
            LOGGER.error("Problem with downloading audio file - the path is empty.");
        }

        resp.setContentType("audio/ogg");
        InputStream in = new FileInputStream(config.getImagesPath() + File.separator + audioPath);
        ServletOutputStream os = resp.getOutputStream();

        byte[] bytes = new byte[2048];
        int bytesRead;
        //TODO-MR setcontenttype, catch
        try {
            while ((bytesRead = in.read(bytes)) != -1) {
                os.write(bytes, 0, bytesRead);
            }
        } finally {
            in.close();
            os.close();
        }
    }

    /*
    * (non-Javadoc)
    * @see javax.servlet.GenericServlet#init()
    */
    @Override
    public void init() throws ServletException {
        super.init();
        Injector injector = getInjector();
        injector.injectMembers(this);
        //        base = config.getScanInputQueuePath();
        //        baseOk = base != null && !"".equals(base);
    }

    /*
     * (non-Javadoc)
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