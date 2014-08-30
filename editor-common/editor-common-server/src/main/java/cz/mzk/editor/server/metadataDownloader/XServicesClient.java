package cz.mzk.editor.server.metadataDownloader;

import cz.mzk.editor.shared.rpc.MetadataBundle;

import java.util.ArrayList;

/**
 * Created by rumanekm on 27.8.14.
 */
public interface XServicesClient {
    ArrayList<MetadataBundle> search(String what);
}
