package cz.mzk.editor.server.metadataDownloader;

import cz.mzk.editor.server.fedora.utils.BiblioModsUtils;
import cz.mzk.editor.server.mods.ModsCollection;
import cz.mzk.editor.server.mods.ModsType;
import cz.mzk.editor.server.newObject.MarcDocument;
import cz.mzk.editor.shared.rpc.DublinCore;
import cz.mzk.editor.shared.rpc.MarcSpecificMetadata;
import cz.mzk.editor.shared.rpc.MetadataBundle;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Namespace;
import org.dom4j.Visitor;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import javax.ws.rs.client.Client;
import javax.xml.parsers.ParserConfigurationException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;


/**
 * Created by rumanekm on 27.8.14.
 */
@Service
public class XServicesClientImpl implements XServicesClient {



    MarcConvertor marcConvertor = new MarcConvertor();

    @Override
    public ArrayList<MetadataBundle> search(String sysno, String base) {
        ArrayList<MetadataBundle> retList = new ArrayList<MetadataBundle>();

        Client client = new ResteasyClientBuilder()
                .establishConnectionTimeout(100, TimeUnit.SECONDS)
                .socketTimeout(2, TimeUnit.SECONDS)
                .build();
        String marc = client.target("http://aleph.nkp.cz/X?op=ill_get_doc&doc_number=" + sysno + "&library=" + base).request().get(String.class);


        Document documentMarc = null;
        try {
            documentMarc = DocumentHelper.parseText(marc);
            Namespace oldNs = Namespace.get("http://www.loc.gov/MARC21/slim/");
            Namespace newNs = Namespace.get("marc21", "http://www.loc.gov/MARC21/slim");
            Visitor visitor = new NamespaceChangingVisitor(oldNs, newNs);
            documentMarc.accept(visitor);
            MarcDocument marcDoc = new MarcDocument(documentMarc, "/ill-get-doc/");

            DublinCore dc = marcConvertor.marc2dublincore(marcDoc);

            ModsType modsType = marcConvertor.marc2mods(marcDoc);
            final ModsCollection modsCollection = new ModsCollection();
            modsCollection.getMods().addAll(Arrays.<ModsType> asList(modsType));

            MarcSpecificMetadata marcSpecific =
                    new MarcSpecificMetadata(marcDoc.findSysno(),
                            base,
                            marcDoc.find040a(),
                            marcDoc.find080a(),
                            marcDoc.find650a(),
                            marcDoc.find260aCorrected(),
                            marcDoc.find260bCorrected(),
                            marcDoc.find260c(),
                            marcDoc.find910b());

            MetadataBundle bundle = new MetadataBundle(dc, BiblioModsUtils.toModsClient(modsCollection), marcSpecific);
            retList.add(bundle);

        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return retList;
    }
}
