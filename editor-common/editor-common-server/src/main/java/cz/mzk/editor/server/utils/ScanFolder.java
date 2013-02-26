package cz.mzk.editor.server.utils;

import java.util.List;

/**
 * @author: Martin Rumanek
 * @version: 14.2.13
 */
public interface ScanFolder {
    List<String> getWrongNames();
    List<String> getFileNames();
    String getPath();
}
