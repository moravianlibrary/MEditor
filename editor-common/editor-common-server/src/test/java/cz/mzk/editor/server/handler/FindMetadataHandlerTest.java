package cz.mzk.editor.server.handler;

import cz.mzk.editor.server.config.EditorConfigurationImpl;
import cz.mzk.editor.server.metadataDownloader.*;
import cz.mzk.editor.shared.rpc.MetadataBundle;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static cz.mzk.editor.client.util.Constants.*;
import static org.junit.Assert.assertEquals;

/**
 * Created by kreplj on 29.8.14.
 */

public class FindMetadataHandlerTest {
    public static final String MZK01 = "MZK01";
    public static final String MZK03 = "MZK03";
    public List<TestData> testDataList;

    @Before
    public void setUp() {
        testDataList = new ArrayList<>();
        testDataList.add(new TestData("001365394", MZK01, "Čapek, Karel"));
        testDataList.add(new TestData("001084045", MZK03, "Marot, Jean"));
        testDataList.add(new TestData("001091278", MZK03, "Brémond, Antonin"));
    }

    private class TestData {
        private String systemNumber;
        private String base;
        private String author;

        private TestData(String systemNumber, String base, String author) {
            this.systemNumber = systemNumber;
            this.base = base;
            this.author = author;
        }

        public String getSystemNumber() {
            return systemNumber;
        }

        public String getBase() {
            return base;
        }

        public String getAuthor() {
            return author;
        }
    }

    @Test
    public void testFindByOai() {
        OAIPMHClient oaiClient = new OAIPMHClientImpl(new EditorConfigurationImpl());
        for (TestData testData : testDataList) {
            String url = "http://aleph.mzk.cz/OAI?verb=GetRecord&identifier=oai:aleph.mzk.cz:" + testData.getBase() + "-" + testData.getSystemNumber() + "&metadataPrefix=";
            List<MetadataBundle> metadataBundle = oaiClient.search(url, testData.getBase());
            String author = metadataBundle.get(0).getDc().getCreator().get(0);
            assertEquals(author, testData.getAuthor());
        }
    }

    @Test
    @Ignore
    public void testFindByZ3950() {
        Z3950Client z3950Client = new Z3950ClientImpl(new EditorConfigurationImpl());
        for (TestData testData : testDataList) {
            // z3950 is hard wired to base MZK03
            if (testData.getBase().equals(MZK03)) {
                List<MetadataBundle> metadataBundle = z3950Client.search(SEARCH_FIELD.SYSNO, testData.getSystemNumber());
                // Dc string returned from z3950 by yaz4j does not have author!!!
                String author = metadataBundle.get(0).getDc().getCreator().get(0);
                assertEquals(author, testData.getAuthor());
            }
        }
    }
}
