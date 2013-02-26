package cz.mzk.editor.server.quartz.jobs;

import com.abby.recognitionserver3.FileContainer;
import com.abby.recognitionserver3.OutputDocument;
import com.abby.recognitionserver3.RSSoapService;
import com.abby.recognitionserver3.RSSoapServiceSoap;
import com.abby.recognitionserver3.XmlResult;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.name.Names;
import cz.mzk.editor.server.DAO.DatabaseException;
import cz.mzk.editor.server.DAO.OcrDao;
import cz.mzk.editor.server.config.EditorConfiguration;
import cz.mzk.editor.server.fedora.FedoraAccess;
import cz.mzk.editor.server.fedora.ImageMimeType;
import cz.mzk.editor.server.fedora.KrameriusImageSupport;
import cz.mzk.editor.server.fedora.utils.FedoraUtils;
import cz.mzk.editor.server.util.RESTHelper;
import cz.mzk.editor.shared.domain.DigitalObjectModel;
import org.apache.commons.io.IOUtils;
import org.quartz.InterruptableJob;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.UnableToInterruptJobException;

import javax.xml.xpath.XPathExpressionException;
import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: Martin Rumanek
 * @version: 19.2.13
 */
public class AddOcrToObjects implements InterruptableJob {

    private Injector guice = null;
    private FedoraAccess fedoraAccess;
    private EditorConfiguration configuration;
    private OcrDao ocrDao;

    @Override
    public void interrupt() throws UnableToInterruptJobException {

    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap dataMap = context.getJobDetail().getJobDataMap();
        guice = (Injector) dataMap.get("Injector");
        fedoraAccess = guice.getInstance(Key.get(FedoraAccess.class, Names.named("securedFedoraAccess")));
        configuration = guice.getInstance(EditorConfiguration.class);
        ocrDao = guice.getInstance(OcrDao.class);

        String uuid = dataMap.getString("uuid");
        List<String> uuidList = new ArrayList<String>();
        try {
            getChildren(uuid, uuidList);

            for (String uuidImage : uuidList) {
                byte[] image = getImage(uuidImage);

                String[] ocr = getOcr(image);
                try {
                    ocrDao.insertOcr(uuidImage, ocr[0], ocr[1]);
                } catch (DatabaseException e) {
                    e.printStackTrace();
                }
            }

        } catch (IOException e) {
        }


    }

    private List<String> getChildren(String uuid, List<String> uuidList) throws IOException {
        if (DigitalObjectModel.PAGE.equals(FedoraUtils.getModel(uuid))) {
            uuidList.add(uuid);
        }
        DigitalObjectModel parentModel = null;
        ArrayList<ArrayList<String>> children = FedoraUtils.getAllChildren(uuid);

        if (children != null) {
            for (ArrayList<String> child : children) {
                getChildren(child.get(0), uuidList);
            }
        }

        return uuidList;
    }

    private byte[] getImage(String uuid) throws IOException {
        String mimetype = fedoraAccess.getMimeTypeForStream(uuid, FedoraUtils.IMG_FULL_STREAM);
        if (mimetype == null) {
            mimetype = "image/jpeg";
        }
        ImageMimeType loadFromMimeType = ImageMimeType.loadFromMimeType(mimetype);
        if (loadFromMimeType == ImageMimeType.JPEG || loadFromMimeType == ImageMimeType.PNG) {
            StringBuffer sb = new StringBuffer();
            sb.append(configuration.getFedoraHost()).append("/objects/").append(uuid)
                    .append("/datastreams/IMG_FULL/content");
            InputStream is =
                    RESTHelper.get(sb.toString(),
                            configuration.getFedoraLogin(),
                            configuration.getFedoraPassword(),
                            false);
            ;
            return IOUtils.toByteArray(is);
        } else {
            Image rawImg = null;
            try {
                rawImg = KrameriusImageSupport.readImage(uuid,
                        FedoraUtils.IMG_FULL_STREAM,
                        this.fedoraAccess,
                        0,
                        loadFromMimeType);
            } catch (XPathExpressionException e) {
                throw new IOException(e);
            }

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            KrameriusImageSupport.writeFullImageToStream(rawImg, "JPG", bos);
            return bos.toByteArray();
        }
    }

    private String[] getOcr(byte[] file) {
        String ocrAlto = null;
        String ocrTxt = null;
        RSSoapService service = new RSSoapService();
        RSSoapServiceSoap soap = service.getRSSoapServiceSoap();
        FileContainer data = new FileContainer();
        data.setFileContents(file);
        XmlResult result = soap.processFile("localhost", "meditor", data);

        if (result.isIsFailed()) {
            //TODO
        }

        for (OutputDocument document :result.getInputFiles().getInputFile().get(0).getOutputDocuments().getOutputDocument()) {
            if ("OFF_ALTO".equals(document.getFormatSettings().getFileFormat().toString())) {
                try {
                    ocrAlto = new String(document.getFiles().getFileContainer().get(0).getFileContents(), "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            if ("OFF_TEXT".equals(document.getFormatSettings().getFileFormat().toString())) {
                try {
                    ocrTxt = new String(document.getFiles().getFileContainer().get(0).getFileContents(), "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }

        return new String[] {ocrTxt, ocrAlto};
    }

}


