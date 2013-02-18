package cz.mzk.editor.server;

import com.google.inject.assistedinject.Assisted;
import com.gwtplatform.dispatch.shared.ActionException;
import cz.mzk.editor.server.config.EditorConfiguration;
import cz.mzk.editor.server.util.IOUtils;
import org.apache.log4j.Logger;

import javax.inject.Inject;
import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;


/**
 * @author: Martin Rumanek
 * @version: 14.2.13
 */
public class ScanFolderImpl implements ScanFolder {

    /** The logger. */
    private static final Logger LOGGER = Logger.getLogger(ScanFolder.class.getPackage().toString());

    public interface ScanFolderFactory {
        ScanFolder create(@Assisted("model") String model, @Assisted("code") String code);
    }

    private final String path;
    public List<String> wrongNames = new ArrayList<String>();
    public List<String> fileNames;

        EditorConfiguration configuration;

    /**
     * Scan directory structure, (lazy)
     * @param model
     * @param code
     * @throws ActionException
     */
    @Inject
    public ScanFolderImpl(@Assisted("model") String model, @Assisted("code") String code, EditorConfiguration configuration) throws ActionException {
        this.configuration = configuration;
        final String base = configuration.getScanInputQueuePath();

        LOGGER.debug("Scanning folder: (model = " + model + ", code = " + code + ")");

        if (base == null || "".equals(base)) {
            LOGGER.error("Scanning folder: Action failed because attribut "
                    + EditorConfiguration.ServerConstants.INPUT_QUEUE + " is not set.");
            throw new ActionException("Scanning input queue: Action failed because attribut "
                    + EditorConfiguration.ServerConstants.INPUT_QUEUE + " is not set.");
        } else {
            this.path = base + File.separator + model + File.separator + code + File.separator;
        }
    }

    @Override
    public List<String> getWrongNames() {
        if (this.wrongNames == null || this.wrongNames.isEmpty()) {
            this.fileNames = scanDirectoryStructure(path, this.wrongNames);
        }
        return this.wrongNames;
    }

    @Override
    public List<String> getFileNames() {
        if (this.fileNames == null || this.fileNames.isEmpty()) {
            this.fileNames = scanDirectoryStructure(path, this.wrongNames);
        }
        return this.fileNames;
    }


    public String getPath() {
        return path;
    }


    /**
     * Scan directory structure
     * @param path path to directory
     * @param wrongNames return wrong names from folder
     * @return list with filenames
     */
    private List<String> scanDirectoryStructure(String path, List<String> wrongNames) {
        final String[] imageTypes = configuration.getImageExtensions();
        File dir = new File(path);
        FileFilter filter = new FileFilter() {

            @Override
            public boolean accept(File pathname) {
                for (String suffix : imageTypes) {
                    if (pathname.getName().toLowerCase().endsWith("." + suffix.toLowerCase())) {
                        return true;
                    }
                }
                return false;
            }

        };
        File[] imgs = dir.listFiles(filter);
        if (imgs == null || imgs.length == 0) {
            return null;
        }
        ArrayList<String> list = new ArrayList<String>(imgs != null ? imgs.length : 0);
        for (int i = 0; i < imgs.length; i++) {

            String name = imgs[i].getName();
            if (!IOUtils.containsIllegalCharacter(name)) {
                list.add(path + name);
            } else {
                wrongNames.add(name);
                LOGGER.error("This image contains some illegal character(s): " + path + name);
            }
        }
        return list;
    }


}
