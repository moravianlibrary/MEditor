package cz.mzk.editor.server.metadataDownloader;

import com.google.inject.Inject;
import cz.mzk.editor.server.fedora.utils.BiblioModsUtils;
import cz.mzk.editor.server.fedora.utils.Dom4jUtils;
import cz.mzk.editor.server.newObject.MarcDocument;
import cz.mzk.editor.server.util.RESTHelper;
import cz.mzk.editor.shared.rpc.DublinCore;
import cz.mzk.editor.shared.rpc.MetadataBundle;
import org.dom4j.*;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by rumanekm on 27.8.14.
 */
public class XServicesClientImpl implements XServicesClient {


    private Document marc2mods;

    private Document marc2dc;

    MarcConvertor marcConvertor = new MarcConvertor();

    @Override
    public ArrayList<MetadataBundle> search(String sysno) {
        ArrayList<MetadataBundle> retList = new ArrayList<MetadataBundle>();

        Client client = ClientBuilder.newClient();
        String marc = client.target("http://aleph.nkp.cz/X?op=ill_get_doc&doc_number=" + sysno + "&library=nkc01").request().get(String.class);


        Document documentMarc = null;
        try {
            documentMarc = DocumentHelper.parseText(marc);
            Namespace oldNs = Namespace.get("http://www.loc.gov/MARC21/slim/");
            Namespace newNs = Namespace.get("marc21", "http://www.loc.gov/MARC21/slim");
            Visitor visitor = new NamespaceChangingVisitor(oldNs, newNs);
            documentMarc.accept(visitor);
            MarcDocument marcDoc = new MarcDocument(documentMarc, "/ill-get-doc/");
            DublinCore dc = marcConvertor.marc2dublincore(marcDoc);

            MetadataBundle bundle = new MetadataBundle(dc, null, null);
            retList.add(bundle);

        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return retList;
    }
}
