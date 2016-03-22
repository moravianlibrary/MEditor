/*
 * Metadata Editor
 * @author Jiri Kremser
 * 
 * 
 * 
 * Metadata Editor - Rich internet application for editing metadata.
 * Copyright (C) 2011  Jiri Kremser (kremser@mzk.cz)
 * Moravian Library in Brno
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *
 * 
 */
// @version:    $Id: Z3950Client.java,v 1.39 2003/05/09 12:54:43 rob_tice Exp $
// Copyright:   Copyright (C) 1999,2000 Knowledge Integration Ltd.
// @author:     Ian Ibbotson (ibbo@k-int.com)
// Company:     KI
// Description: 
//

//
// This program is free software; you can redistribute it and/or
// modify it under the terms of the GNU General Public License
// as published by the Free Software Foundation; either version 2.1 of
// the license, or (at your option) any later version.
// 
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
// 
// You should have received a copy of the GNU general Public License
// along with this program; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place - Suite
// 330, Boston, MA  02111-1307, USA.
// 

package cz.mzk.editor.server.metadataDownloader;

<<<<<<< 1d29f455c561a0026a27a1df008d5cdc59fa0d2a
import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
=======
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
>>>>>>> issue #50 fixed, z39.50 works
import java.util.List;

import javax.inject.Inject;
import javax.xml.parsers.ParserConfigurationException;

<<<<<<< 1d29f455c561a0026a27a1df008d5cdc59fa0d2a
=======
import cz.mzk.editor.server.fedora.utils.BiblioModsUtils;
import cz.mzk.editor.server.fedora.utils.Dom4jUtils;
import cz.mzk.editor.server.util.XMLUtils;
>>>>>>> issue #50 fixed, z39.50 works
import org.apache.log4j.Logger;

import cz.mzk.editor.client.util.Constants;
import cz.mzk.editor.server.config.EditorConfiguration;
import cz.mzk.editor.server.config.EditorConfiguration.ServerConstants;
import cz.mzk.editor.server.fedora.utils.DCUtils;
import cz.mzk.editor.server.mods.ModsType;
import cz.mzk.editor.server.mods.ModsCollection;
import cz.mzk.editor.shared.rpc.DublinCore;
import cz.mzk.editor.shared.rpc.MarcSpecificMetadata;
import cz.mzk.editor.shared.rpc.MetadataBundle;
<<<<<<< 1d29f455c561a0026a27a1df008d5cdc59fa0d2a
=======
import org.dom4j.Document;
import org.dom4j.DocumentException;
>>>>>>> issue #50 fixed, z39.50 works
import org.marc4j.MarcReader;
import org.marc4j.MarcXmlReader;
import org.marc4j.marc.ControlField;
import org.marc4j.marc.DataField;
import org.marc4j.marc.Subfield;
import org.marc4j.marc.VariableField;
<<<<<<< 1d29f455c561a0026a27a1df008d5cdc59fa0d2a
=======
import org.xml.sax.SAXException;
>>>>>>> issue #50 fixed, z39.50 works
import org.yaz4j.Connection;
import org.yaz4j.PrefixQuery;
import org.yaz4j.Record;
import org.yaz4j.ResultSet;
import org.yaz4j.exception.ZoomException;

/**
 * @author Jiri Kremser
 */
@SuppressWarnings({"rawtypes", "unused", "unchecked"})
public class Z3950ClientImpl
        implements Z3950Client {

    /**
     * The logger.
     */
    private static final Logger LOGGER = Logger.getLogger(Z3950ClientImpl.class);

    /**
     * The configuration.
     */
    private final EditorConfiguration configuration;

    private String profile = null;
    private String host = null;
    private String port = null;
    private String base = null;
    private int barLength = 0;

    private Connection connection;

    /**
     * Instantiates a new z3950 client.
     *
<<<<<<< 1d29f455c561a0026a27a1df008d5cdc59fa0d2a
     * @param configuration the configuration
=======
     * @param configuration
     *        the configuration
>>>>>>> issue #50 fixed, z39.50 works
     */
    @Inject
    public Z3950ClientImpl(final EditorConfiguration configuration) {
        this.configuration = configuration;
    }

    /**
     * helper method for getting subfields of the marc record
     *
     * @param marcRecord      marc record
     * @param dataFieldString String 'number' of marc data field
     * @param subFieldString  Character of the marc data subfield
     * @return String of the marc subfield
     */
    private String getMarcSubFieldData(org.marc4j.marc.Record marcRecord, String dataFieldString, Character subFieldString) {
        DataField dataField = (DataField) marcRecord.getVariableField(dataFieldString);
        Subfield subfield;
        if (dataField != null) {
            subfield = dataField.getSubfield(subFieldString);
            return subfield.getData();
        } else {
            return null;
        }
    }

    /**
     * @param record marc record
     * @return MarcSpecificMetadata of the record
     */
    private MarcSpecificMetadata getMarcMetadata(org.marc4j.marc.Record record) {
        String data040a = getMarcSubFieldData(record, "040", 'a');
        String data650a = getMarcSubFieldData(record, "650", 'a');
        String data260a = getMarcSubFieldData(record, "260", 'a');
        String data260b = getMarcSubFieldData(record, "260", 'b');
        String data260c = getMarcSubFieldData(record, "260", 'c');
        String data910b = getMarcSubFieldData(record, "910", 'b');

        List<String> data080a = new ArrayList<>();
        List<VariableField> data080List = record.getVariableFields("080");
        for (VariableField field : data080List) {
            Subfield subfield = ((DataField) field).getSubfield('a');
            data080a.add(subfield.getData());
        }

        String sysno = ((ControlField) record.getVariableField("001")).getData();
        return new MarcSpecificMetadata(sysno, base, data040a, data080a, data650a, data260a, data260b, data260c, data910b);
    }

    /**
     * Search.
     *
<<<<<<< 1d29f455c561a0026a27a1df008d5cdc59fa0d2a
     * @param field the field
     * @param what  the what
     * @return the dublin core xml
=======
     * @param field
     *        the field
     * @param what
     *        the what
>>>>>>> issue #50 fixed, z39.50 works
     */
    @Override
    public ArrayList<MetadataBundle> search(Constants.SEARCH_FIELD field, String what) {
        boolean isOk = init();
        if (!isOk) {
            return null;
        }
<<<<<<< 1d29f455c561a0026a27a1df008d5cdc59fa0d2a
        Record record;
        List<String> dcStrings = new ArrayList<>();
        List<MarcSpecificMetadata> marcMetadataList = new ArrayList<>();
        ResultSet dcSet = search(field, what, false);
        try {
            for (int i = 0; i < dcSet.getHitCount(); i++) {
                record = dcSet.getRecord(i);
                if (record != null) {
                    String dcString = new String(record.getContent(), "UTF8");
=======
        Record z3950Record;
        List<String> dcStrings = new ArrayList<>();
        List<MarcSpecificMetadata> marcMetadataList = new ArrayList<>();
        ModsCollection modsCollection = new ModsCollection();

        try {
            ResultSet dcSet = search(field, what, false);
            for (int i = 0; i < dcSet.getHitCount(); i++) {
                z3950Record = dcSet.getRecord(i);
                if (z3950Record != null) {
                    String dcString = new String(z3950Record.getContent(), "UTF8");
>>>>>>> issue #50 fixed, z39.50 works
                    dcStrings.add(dcString);
                }
            }
            ResultSet marcSet = search(field, what, true);
            for (int i = 0; i < marcSet.getHitCount(); i++) {
<<<<<<< 1d29f455c561a0026a27a1df008d5cdc59fa0d2a
                record = marcSet.getRecord(i);
                byte[] b = record.get("xml; charset=windows-1250");
                ByteArrayInputStream in = new ByteArrayInputStream(b);
                MarcReader reader = new MarcXmlReader(in);
=======
                z3950Record = marcSet.getRecord(i);
                byte[] marcXmlByteArray = z3950Record.get("xml");

                Document modsStylesheet = Dom4jUtils.loadDocument(new File(configuration.getEditorHome() + MARC_TO_MODS_XSLT), true);
                Document marcDocument = Dom4jUtils.loadDocument(new ByteArrayInputStream(marcXmlByteArray), true);
                Document modsDocument = Dom4jUtils.transformDocument(marcDocument, modsStylesheet);
                ModsType mods = BiblioModsUtils.getMods(XMLUtils.parseDocument(modsDocument.asXML(), true));
                modsCollection.getMods().add(mods);

                MarcReader reader = new MarcXmlReader(new ByteArrayInputStream(marcXmlByteArray));
>>>>>>> issue #50 fixed, z39.50 works
                while (reader.hasNext()) {
                    org.marc4j.marc.Record marcRecord = reader.next();
                    marcMetadataList.add(getMarcMetadata(marcRecord));
                }
            }
<<<<<<< 1d29f455c561a0026a27a1df008d5cdc59fa0d2a
        } catch (ZoomException | UnsupportedEncodingException e) {
=======
        } catch (ZoomException | UnsupportedEncodingException | FileNotFoundException | DocumentException | SAXException | ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
>>>>>>> issue #50 fixed, z39.50 works
            e.printStackTrace();
        }

        ArrayList<MetadataBundle> retList = new ArrayList<>();
        for (int i = 0; i < dcStrings.size(); i++) {
            DublinCore dc = DCUtils.getDC(dcStrings.get(i));
            dc.removeTrailingSlash();
<<<<<<< 1d29f455c561a0026a27a1df008d5cdc59fa0d2a
            MetadataBundle bundle = new MetadataBundle(dc, null, marcMetadataList.get(i));
=======
            MetadataBundle bundle = new MetadataBundle(dc, BiblioModsUtils.toModsClient(modsCollection), marcMetadataList.get(i));
>>>>>>> issue #50 fixed, z39.50 works
            retList.add(bundle);
        }
        connection.close();
        return retList;
    }

    private ResultSet search(Constants.SEARCH_FIELD field, String what, boolean marc) {
        String query = "@attrset bib-1 ";
        if (field != null) {
            switch (field) {
                case BAR:
                    query += "@attr 1=1099 "; // not tested
                    break;
                case SYSNO:
                    query += "@attr 1=12 ";
                    break;
                case TITLE:
                    query += "@attr 1=4 ";
                    break;
            }
        } else {
            if (barLength == what.length()) {
                query += "@attr 1=1099 "; // barcode
            } else {
                query += "@attr 1=12 "; // sysno
            }
        }
        ResultSet resultSet = null;
        connection.setSyntax(marc ? MARC_RECORD_SYNTAX : DC_RECORD_SYNTAX);
        try {
            connection.connect();
            PrefixQuery prefixQuery = new PrefixQuery(query + "\"" + what + "\"");
            resultSet = connection.search(prefixQuery);
        } catch (ZoomException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    private boolean init() {
        int profileIndex = getProfileIndex();

        // host
        if ((host = configuration.getZ3950Host()) == null) {
            if (profileIndex == -1) {
                LOGGER.error("Neither " + EditorConfiguration.ServerConstants.Z3950_HOST + " nor "
                        + EditorConfiguration.ServerConstants.Z3950_PROFILE
                        + " is set in editor configuration!");
                return false;
            } else {
                host = EditorConfiguration.ServerConstants.Z3950_DEFAULT_HOSTS[profileIndex];
            }
        }
        // port
        if ((port = configuration.getZ3950Port()) == null) {
            if (profileIndex == -1) {
                LOGGER.error("Neither " + EditorConfiguration.ServerConstants.Z3950_PORT + " nor "
                        + EditorConfiguration.ServerConstants.Z3950_PROFILE
                        + " is set in editor configuration!");
                return false;
            } else {
                port = EditorConfiguration.ServerConstants.Z3950_DEFAULT_PORTS[profileIndex];
            }
        }
        // base
        if ((base = configuration.getZ3950Base()) == null) {
            if (profileIndex == -1) {
                LOGGER.error("Neither " + EditorConfiguration.ServerConstants.Z3950_BASE + " nor "
                        + EditorConfiguration.ServerConstants.Z3950_PROFILE
                        + " is set in editor configuration!");
                return false;
            } else {
                base = EditorConfiguration.ServerConstants.Z3950_DEFAULT_BASES[profileIndex];
            }
        }
        // barcode length
        if ((barLength = configuration.getZ3950BarLength().intValue()) == ServerConstants.UNDEF.intValue()) {
            if (profileIndex == -1) {
                LOGGER.error("Neither " + EditorConfiguration.ServerConstants.Z3950_BAR_LENGTH + " nor "
                        + EditorConfiguration.ServerConstants.Z3950_PROFILE
                        + " is set in editor configuration!");
                return false;
            } else {
                barLength = EditorConfiguration.ServerConstants.Z3950_DEFAULT_BAR_LENGTH[profileIndex];
            }
        }
        try {
            connection = new Connection(host, Integer.parseInt(port));
            connection.setDatabaseName(base);
        } catch (UnsatisfiedLinkError e) {
            LOGGER.error("You don't have yaz4j library on your class path. Please refer to installation at https://github.com/indexdata/yaz4j");
            return false;
        }
        return true;
    }

    @Override
    public int getProfileIndex() {
        int profileIndex = -1;
        if ((profile = configuration.getZ3950Profile()) != null) {
            if (MZK_PROFILE_ID.equals(profile)) {
                profileIndex = MZK_PROFILE_INDEX;
            } else if (MUNI_PROFILE_ID.equals(profile)) {
                profileIndex = MUNI_PROFILE_INDEX;
            } else if (NKP_SKC_PROFILE_ID.equals(profile)) {
                profileIndex = NKP_SKC_PROFILE_INDEX;
            } else if (NKP_NKC_PROFILE_ID.equals(profile)) {
                profileIndex = NKP_NKC_PROFILE_INDEX;
            } else {
                LOGGER.warn("Invalid value (" + profile + ") for key "
                        + EditorConfiguration.ServerConstants.Z3950_PROFILE + " in editor configuration.");
            }
        }
        return profileIndex;
    }
}