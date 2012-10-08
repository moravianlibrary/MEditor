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

package cz.mzk.editor.client.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.regexp.shared.RegExp;
import com.google.gwt.regexp.shared.SplitResult;
import com.google.gwt.user.client.DOM;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.tree.Tree;
import com.smartgwt.client.widgets.tree.TreeNode;

import cz.mzk.editor.client.CreateObjectException;
import cz.mzk.editor.client.LangConstants;
import cz.mzk.editor.client.mods.StringPlusAuthorityClient;
import cz.mzk.editor.client.mods.StringPlusAuthorityPlusTypeClient;
import cz.mzk.editor.client.view.other.RecentlyModifiedRecord;
import cz.mzk.editor.client.view.other.ScanRecord;
import cz.mzk.editor.client.view.other.SubstructureTreeNode;
import cz.mzk.editor.shared.domain.DigitalObjectModel;
import cz.mzk.editor.shared.rpc.DublinCore;
import cz.mzk.editor.shared.rpc.MetadataBundle;
import cz.mzk.editor.shared.rpc.NewDigitalObject;
import cz.mzk.editor.shared.rpc.RecentlyModifiedItem;
import cz.mzk.editor.shared.rpc.TreeStructureBundle.TreeStructureNode;

/**
 * @author Jiri Kremser
 */
public class ClientCreateUtils {

    /**
     * To record.
     * 
     * @param item
     *        the item
     * @return the recently modified record
     */
    public static RecentlyModifiedRecord toRecord(RecentlyModifiedItem item) {
        return new RecentlyModifiedRecord(item.getUuid(),
                                          item.getName(),
                                          item.getDescription(),
                                          item.getModel());
    }

    /**
     * Escape html.
     * 
     * @param maybeHtml
     *        the maybe html
     * @return the string
     */
    public static String escapeHtml(String maybeHtml) {
        final com.google.gwt.user.client.Element div = DOM.createDiv();
        DOM.setInnerText(div, maybeHtml);
        return DOM.getInnerHTML(div);
    }

    /**
     * To list of list of strings.
     * 
     * @param frequency
     *        the frequency
     * @return the list
     */
    public static List<List<String>> toListOfListOfStrings(List<StringPlusAuthorityClient> frequency) {
        if (frequency == null) return null;
        List<List<String>> outerList = new ArrayList<List<String>>(frequency.size());
        for (StringPlusAuthorityClient value : frequency) {
            if (value == null) continue;
            List<String> innerList = new ArrayList<String>(2);
            innerList.add(value.getValue());
            innerList.add(value.getAuthority());
            outerList.add(innerList);
        }
        return outerList;
    }

    /**
     * To list of list of strings.
     * 
     * @param toConvert
     *        the to convert
     * @param something
     *        the something
     * @return the list
     */
    public static List<List<String>> toListOfListOfStrings(List<StringPlusAuthorityPlusTypeClient> toConvert,
                                                           boolean something) {
        if (toConvert == null) return null;
        List<List<String>> outerList = new ArrayList<List<String>>(toConvert.size());
        for (StringPlusAuthorityPlusTypeClient value : toConvert) {
            if (value == null) continue;
            List<String> innerList = new ArrayList<String>(2);
            innerList.add(value.getValue());
            innerList.add(value.getType());
            innerList.add(value.getAuthority());
            outerList.add(innerList);
        }
        return outerList;
    }

    /**
     * Subtract.
     * 
     * @param whole
     *        the whole
     * @param part
     *        the part
     * @return the list grid record[]
     */
    public static ListGridRecord[] subtract(ListGridRecord[] whole, ListGridRecord[] part) {
        if (whole == null || whole.length == 0) return null;
        if (part == null || part.length == 0) return whole;
        List<ListGridRecord> list = new ArrayList<ListGridRecord>();
        for (ListGridRecord record : whole) {
            boolean add = true;
            for (ListGridRecord counterpart : part) {
                if (record == counterpart) {
                    add = false;
                }
            }
            if (add) list.add(record);
        }
        return list.toArray(new ListGridRecord[] {});
    }

    public static NewDigitalObject createTheStructure(MetadataBundle bundle,
                                                      Tree tree,
                                                      boolean visible,
                                                      boolean isPdf) throws CreateObjectException {

        Map<String, NewDigitalObject> processedPages = new HashMap<String, NewDigitalObject>();

        TreeNode root = tree.findById(SubstructureTreeNode.ROOT_OBJECT_ID);
        if (root == null || (tree.getChildren(root).length == 0 && !isPdf)) {
            return null;
        }

        String name = root.getAttribute(Constants.ATTR_NAME);
        if (name == null || "".equals(name)) {
            throw new CreateObjectException("unknown name");
        }

        String modelString = root.getAttribute(Constants.ATTR_MODEL_ID);
        if (modelString == null || "".equals(modelString)) {
            throw new CreateObjectException("unknown type");
        }
        DigitalObjectModel model = null;
        try {
            model = DigitalObjectModel.parseString(modelString);
        } catch (RuntimeException ex) {
            throw new CreateObjectException("unknown type");
        }
        boolean exists = root.getAttributeAsBoolean(Constants.ATTR_EXIST);
        NewDigitalObject newObj = new NewDigitalObject(0, name, model, bundle, exists ? name : null, exists);
        newObj.setVisible(visible);
        if (tree.getChildren(root).length > 0)
            createChildrenStructure(tree, root, bundle, visible, processedPages, newObj);
        return newObj;
    }

    private static void createChildrenStructure(Tree tree,
                                                TreeNode node,
                                                MetadataBundle bundle,
                                                boolean visible,
                                                Map<String, NewDigitalObject> processedPages,
                                                NewDigitalObject newObj) throws CreateObjectException {
        TreeNode[] children = tree.getChildren(node);

        int childrenLastPageIndex = -1;
        List<TreeNode> folders = new ArrayList<TreeNode>();
        for (TreeNode child : children) {
            if (child.getAttribute(tree.getIsFolderProperty()) == null) {
                NewDigitalObject newChild =
                        createTheStructure(bundle,
                                           tree,
                                           child,
                                           visible,
                                           childrenLastPageIndex,
                                           processedPages);
                newObj.getChildren().add(newChild);
                childrenLastPageIndex = newChild.getPageIndex();
            } else {
                folders.add(child);
            }
        }
        for (TreeNode folder : folders) {
            NewDigitalObject newChild =
                    createTheStructure(bundle, tree, folder, visible, childrenLastPageIndex, processedPages);
            newObj.getChildren().add(newChild);
            childrenLastPageIndex = newChild.getPageIndex();
        }
    }

    private static NewDigitalObject createTheStructure(MetadataBundle bundle,
                                                       Tree tree,
                                                       TreeNode node,
                                                       boolean visible,
                                                       int lastPageIndex,
                                                       Map<String, NewDigitalObject> processedPages)
            throws CreateObjectException {

        String modelString = node.getAttribute(Constants.ATTR_MODEL_ID);
        if (modelString == null || "".equals(modelString)) {
            throw new CreateObjectException("unknown type");
        }
        DigitalObjectModel model = null;
        try {
            model = DigitalObjectModel.parseString(modelString);
        } catch (RuntimeException ex) {
            throw new CreateObjectException("unknown type");
        }
        String imgUuid = node.getAttribute(Constants.ATTR_PICTURE_OR_UUID);

        if (!processedPages.containsKey(imgUuid)) {

            int pageIndex = -1;
            if (model == DigitalObjectModel.PAGE) {
                if (imgUuid == null || "".equals(imgUuid)) {
                    throw new CreateObjectException("unknown uuid");
                }
                if (lastPageIndex < 0) {
                    lastPageIndex = 0;
                }
                pageIndex = ++lastPageIndex;
            }

            String name = node.getAttribute(Constants.ATTR_NAME);
            if (name == null || "".equals(name) && model != DigitalObjectModel.PERIODICALITEM) {
                if (model != DigitalObjectModel.PAGE) {
                    name = node.getAttribute(Constants.ATTR_PART_NUMBER_OR_ALTO);
                }
                if (name == null || "".equals(name)) {
                    throw new CreateObjectException("unknown name");
                }
            }

            Boolean exists = node.getAttributeAsBoolean(Constants.ATTR_EXIST);

            NewDigitalObject newObj = new NewDigitalObject(pageIndex, name, model, bundle, null, exists);

            newObj.setVisible(visible);
            String dateOrIntPartName = node.getAttribute(Constants.ATTR_DATE_OR_INT_PART_NAME);
            if (dateOrIntPartName != null && !"".equals(dateOrIntPartName)) {
                newObj.setDateOrIntPartName(dateOrIntPartName);
            }

            String noteOrIntSubtitle = node.getAttribute(Constants.ATTR_NOTE_OR_INT_SUBTITLE);
            if (noteOrIntSubtitle != null && !"".equals(noteOrIntSubtitle)) {
                newObj.setNoteOrIntSubtitle(noteOrIntSubtitle);
            }

            String type = node.getAttribute(Constants.ATTR_TYPE);
            if (type != null && !"".equals(type)) {
                if (model == DigitalObjectModel.INTERNALPART)
                    newObj.setType(Constants.PERIODICAL_ITEM_GENRE_TYPES.MAP.get(type));

                newObj.setType(node.getAttribute(Constants.ATTR_TYPE));
            }

            String partNumberOrAlto = node.getAttribute(Constants.ATTR_PART_NUMBER_OR_ALTO);
            if (partNumberOrAlto != null && !"".equals(partNumberOrAlto)) {
                newObj.setPartNumberOrAlto(partNumberOrAlto);
            }

            if (exists) {
                if (imgUuid != null && !"".equals(imgUuid)) {
                    newObj.setUuid(imgUuid.startsWith("uuid:") ? imgUuid.substring("uuid:".length())
                            : imgUuid);
                } else {
                    throw new CreateObjectException("unknown uuid of an existing object");
                }
            }

            String aditionalInfoOrOcr = node.getAttribute(Constants.ATTR_ADITIONAL_INFO_OR_OCR);
            if (aditionalInfoOrOcr != null && !"".equals(aditionalInfoOrOcr)) {
                newObj.setAditionalInfoOrOcr(aditionalInfoOrOcr);
            }

            newObj.setPath(imgUuid);

            createChildrenStructure(tree, node, bundle, visible, processedPages, newObj);
            if (model == DigitalObjectModel.PAGE) {
                processedPages.put(imgUuid, newObj);
            }
            return newObj;
        } else {
            return new NewDigitalObject(processedPages.get(imgUuid));
        }
    }

    public static String toStringTree(NewDigitalObject node) {
        final StringBuilder buffer = new StringBuilder();
        return toStringTreeHelper(node, buffer, new LinkedList<Iterator<NewDigitalObject>>()).toString();
    }

    private static String toStringTreeDrawLines(List<Iterator<NewDigitalObject>> parentIterators,
                                                boolean amLast) {
        StringBuilder result = new StringBuilder();
        Iterator<Iterator<NewDigitalObject>> it = parentIterators.iterator();
        while (it.hasNext()) {
            Iterator<NewDigitalObject> anIt = it.next();
            if (anIt.hasNext() || (!it.hasNext() && amLast)) {
                result.append("   |");
            } else {
                result.append("    ");
            }
        }
        return result.toString();
    }

    private static StringBuilder toStringTreeHelper(NewDigitalObject node,
                                                    StringBuilder buffer,
                                                    List<Iterator<NewDigitalObject>> parentIterators) {
        if (!parentIterators.isEmpty()) {
            boolean amLast = !parentIterators.get(parentIterators.size() - 1).hasNext();
            buffer.append("\n");
            String lines = toStringTreeDrawLines(parentIterators, amLast);
            buffer.append(lines);
            buffer.append("\n");
            buffer.append(lines);
            buffer.append("- ");
        }
        buffer.append(node.toString());
        if (node.getChildren() != null && node.getChildren().size() > 0) {
            Iterator<NewDigitalObject> it = node.getChildren().iterator();
            parentIterators.add(it);
            while (it.hasNext()) {
                NewDigitalObject child = it.next();
                toStringTreeHelper(child, buffer, parentIterators);
            }
            parentIterators.remove(it);
        }
        return buffer;
    }

    private static final String[] RCODE_1 = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V",
            "IV", "I"};

    private static final String[] RCODE_2 = {"M", "DCCCC", "D", "CCCC", "C", "LXXXX", "L", "XXXX", "X",
            "VIIII", "V", "IIII", "I"};
    private static final int[] BVAL = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};

    public static String decimalToRoman(int binary, boolean oldFashion) {
        if (binary <= 0 || binary >= 4000) {
            throw new NumberFormatException("Value outside roman numeral range.");
        }
        String roman = "";

        // Loop from biggest value to smallest, successively subtracting,
        // from the binary value while adding to the roman representation.
        for (int i = 0; i < RCODE_1.length; i++) {
            while (binary >= BVAL[i]) {
                binary -= BVAL[i];
                roman += oldFashion ? RCODE_2[i] : RCODE_1[i];
            }
        }
        return roman;
    }

    public static int romanToDecimal(String roman) {
        int number = 0;
        String romanSubstring = roman;
        for (int i = 0; i < RCODE_1.length && !"".equals(romanSubstring); i++) {
            if (romanSubstring.startsWith(RCODE_1[i])) {
                number += BVAL[i];
                romanSubstring = romanSubstring.substring(RCODE_1[i].length());
                i--;
            }
        }
        for (int i = 0; i < RCODE_2.length && !"".equals(romanSubstring); i++) {
            if (romanSubstring.startsWith(RCODE_2[i])) {
                number += BVAL[i];
                romanSubstring = romanSubstring.substring(RCODE_2[i].length());
                i--;
            }
        }
        if ("".equals(romanSubstring)) return number;
        return Integer.MIN_VALUE;
    }

    /**
     * String.format is not accessible on the gwt client-side
     * 
     * @param format
     * @param args
     * @return formatted string
     */
    public static String format(final String format, final char escapeCharacter, final Object... args) {
        final RegExp regex = RegExp.compile("%" + escapeCharacter);
        final SplitResult split = regex.split(format);
        final StringBuffer msg = new StringBuffer();
        for (int pos = 0; pos < split.length() - 1; pos += 1) {
            msg.append(split.get(pos));
            msg.append(args[pos].toString());
        }
        msg.append(split.get(split.length() - 1));
        return msg.toString();
    }

    public static class SimpleDateFormat {

        private DateTimeFormat format;

        public SimpleDateFormat() {
            super();
        }

        protected SimpleDateFormat(DateTimeFormat format) {
            this.format = format;
        }

        public SimpleDateFormat(String pattern) {
            applyPattern(pattern);
        }

        public void applyPattern(String pattern) {
            this.format = DateTimeFormat.getFormat(pattern);
        }

        public String format(Date date) {
            return format.format(date);
        }

        /**
         * Parses text and returns the corresponding date object.
         */
        public Date parse(String source) {
            return format.parse(source);
        }

    }

    public static List<TreeStructureNode> toNodes(Record[] records) {
        List<TreeStructureNode> retList = new ArrayList<TreeStructureNode>();
        for (int i = 0; i < records.length; i++) {
            TreeStructureNode node = toNode(records[i]);
            if (node != null) {
                retList.add(node);
            } else {
                return null;
            }
        }
        return retList;
    }

    private static TreeStructureNode toNode(Record treeNode) {
        String modelId = treeNode.getAttribute(Constants.ATTR_MODEL_ID);
        String attrId = modelId == null ? null : treeNode.getAttribute(Constants.ATTR_ID);

        return new TreeStructureNode(attrId,
                                     treeNode.getAttribute(Constants.ATTR_PARENT),
                                     trimLabel(treeNode.getAttribute(Constants.ATTR_NAME), 255),
                                     treeNode.getAttribute(Constants.ATTR_PICTURE_OR_UUID),
                                     modelId == null ? treeNode.getAttribute(Constants.ATTR_MODEL) : modelId,
                                     treeNode.getAttribute(Constants.ATTR_TYPE),
                                     modelId == null ? treeNode.getAttribute(Constants.ATTR_ID) : treeNode
                                             .getAttribute(Constants.ATTR_DATE_OR_INT_PART_NAME),
                                     treeNode.getAttribute(Constants.ATTR_NOTE_OR_INT_SUBTITLE),
                                     treeNode.getAttribute(Constants.ATTR_PART_NUMBER_OR_ALTO),
                                     treeNode.getAttribute(Constants.ATTR_ADITIONAL_INFO_OR_OCR),
                                     treeNode.getAttributeAsBoolean(Constants.ATTR_EXIST));
    }

    public static ScanRecord toScanRecord(TreeStructureNode node) {
        ScanRecord rec =
                new ScanRecord(node.getPropName(),
                               node.getPropModelId(),
                               node.getPropPictureOrUuid(),
                               node.getPropType());
        rec.setAttribute(Constants.ATTR_ADITIONAL_INFO_OR_OCR, node.getPropAditionalInfoOrOcr());
        return rec;
    }

    public static SubstructureTreeNode toTreeNode(TreeStructureNode node) {
        SubstructureTreeNode subNode =
                new SubstructureTreeNode(node.getPropId(),
                                         node.getPropParent(),
                                         node.getPropName(),
                                         node.getPropPictureOrUuid(),
                                         node.getPropModelId(),
                                         node.getPropType(),
                                         node.getPropDateOrIntPartName(),
                                         node.getPropNoteOrIntSubtitle(),
                                         node.getPropPartNumberOrAlto(),
                                         node.getPropAditionalInfoOrOcr(),
                                         true,
                                         ClientUtils.toBoolean(node.getPropName()));
        return subNode;
    }

    public static String recordsToString(Record[] records) {
        if (records == null || records.length == 0) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < records.length; i++) {
            sb.append(" ");
            String fyzNum = String.valueOf(i + 1);
            for (int j = fyzNum.length(); j < 3; j++) {
                sb.append(' ');
            }
            sb.append(fyzNum);
            sb.append(records[i].getAttribute(Constants.ATTR_TYPE));
            sb.append("  --  ");
            sb.append(records[i].getAttribute(Constants.ATTR_NAME));
            sb.append("\n");
        }
        return sb.toString();
    }

    public static String trimLabel(String originalString, int maxLength) {
        if (originalString == null) return "";
        if (originalString.length() <= maxLength) {
            return originalString;
        } else {
            return originalString.substring(0, maxLength - Constants.OVER_MAX_LENGTH_SUFFIX.length())
                    + Constants.OVER_MAX_LENGTH_SUFFIX;
        }
    }

    public static String checkDC(DublinCore dc, LangConstants lang) {
        StringBuffer errorMessage = new StringBuffer("");
        errorMessage.append(checkValues(dc.getIdentifier(), lang.dcIdentifier(), lang));
        errorMessage.append(checkValues(dc.getContributor(), lang.dcContributor(), lang));
        errorMessage.append(checkValues(dc.getCoverage(), lang.dcCoverage(), lang));
        errorMessage.append(checkValues(dc.getCreator(), lang.dcCreator(), lang));
        errorMessage.append(checkValues(dc.getDate(), lang.dcDate(), lang));
        errorMessage.append(checkValues(dc.getDescription(), lang.dcDescription(), lang));
        errorMessage.append(checkValues(dc.getFormat(), lang.dcFormat(), lang));
        errorMessage.append(checkValues(dc.getIdentifier(), lang.dcIdentifier(), lang));
        errorMessage.append(checkValues(dc.getLanguage(), lang.dcLanguage(), lang));
        errorMessage.append(checkValues(dc.getPublisher(), lang.dcPublisher(), lang));
        errorMessage.append(checkValues(dc.getRelation(), lang.dcRelation(), lang));
        errorMessage.append(checkValues(dc.getRights(), lang.dcRights(), lang));
        errorMessage.append(checkValues(dc.getSource(), lang.dcSource(), lang));
        errorMessage.append(checkValues(dc.getSubject(), lang.dcSubject(), lang));
        errorMessage.append(checkValues(dc.getTitle(), lang.dcTitle(), lang));
        errorMessage.append(checkValues(dc.getType(), lang.dcType(), lang));
        return errorMessage.toString();
    }

    private static String checkValues(List<String> values, String elementName, LangConstants lang) {
        StringBuffer errorMessage = new StringBuffer("");
        if (values != null) {
            for (String value : values) {
                if (value.contains("<"))
                    errorMessage.append(lang.inDCSection() + ": " + elementName + " "
                            + lang.isForbiddenChar() + ": &lt.<br>");
                if (value.contains(">"))
                    errorMessage.append(lang.inDCSection() + ": " + elementName + " "
                            + lang.isForbiddenChar() + ": &gt.<br>");
            }
        }
        return errorMessage.toString();
    }
}
