package cz.mzk.editor.server.handler;

import cz.mzk.editor.server.metadataDownloader.XServicesClient;
import cz.mzk.editor.server.metadataDownloader.XServicesClientImpl;
import cz.mzk.editor.shared.rpc.MetadataBundle;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by rumanekm on 29.8.14.
 */

public class XServicesClientTest {

    @Test
    @Ignore
    public void testDcFindBySysno() {
        XServicesClient xServicesClient = new XServicesClientImpl();
        List<MetadataBundle> metadataBundles = xServicesClient.search("001810391", "nkc01");
        String author = metadataBundles.get(0).getDc().getCreator().get(0);
        assertEquals(author, "Hejný, Milan");
    }
}
