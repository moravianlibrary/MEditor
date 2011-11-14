/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.fi.muni.xkremser.editor.server.newObject;

import java.io.File;
import java.io.FileNotFoundException;

import java.util.List;

import org.dom4j.DocumentException;

/**
 * @author Martin Rehanek (rehan@mzk.cz)
 */
public class MarcDocument
        extends MetadataDocument {

    public MarcDocument(File file)
            throws FileNotFoundException, DocumentException {
        super(file);
    }

    public String findSysno() {
        String xpath = "/marc21:record/marc21:controlfield[@tag='001']";
        return findValue(xpath);
    }

    public String findBase() {
        return "mzk03";//TODO: opravit
    }

    public String find040a() {
        return findDatafieldAndSubfield("040", 'a');
    }

    private String findDatafieldAndSubfield(String datafield, char subfield) {
        String xpath =
                "/marc21:record/marc21:datafield[@tag='" + datafield + "']/marc21:subfield[@code='"
                        + subfield + "']";
        return findValue(xpath);
    }

    public List<String> find080a() {
        String xpath = "/marc21:record/marc21:datafield[@tag='080']/marc21:subfield[@code='a']";
        return findValues(xpath);
    }

    public String find650a() {
        return findDatafieldAndSubfield("650", 'a');
    }

    public String find260aCorrected() {
        String content = findDatafieldAndSubfield("260", 'a');
        if (content != null) {
            content = removePrefix(content, "[");
            content = removeSuffix(content, " :");
            content = removeSuffix(content, ":");
        }
        return content;
    }

    public String find260bCorrected() {
        String content = findDatafieldAndSubfield("260", 'b');
        if (content != null) {
            content = removeSuffix(content, ",");
            content = removeSuffix(content, "]");
        }
        return content;
    }

    private String removePrefix(String string, String prefix) {
        if (string.startsWith(prefix)) {
            return string.substring(prefix.length());
        } else {
            return string;
        }
    }

    private String removeSuffix(String string, String suffix) {
        if (string.endsWith(suffix)) {
            int length = string.length() - suffix.length();
            return string.substring(0, length);
        } else {
            return string;
        }
    }

    public String find260c() {
        return findDatafieldAndSubfield("260", 'c');
    }
}
