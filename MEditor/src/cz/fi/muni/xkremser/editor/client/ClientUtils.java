/**
 * Metadata Editor
 * @author Jiri Kremser
 *  
 */
package cz.fi.muni.xkremser.editor.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.DOM;

import cz.fi.muni.xkremser.editor.client.mods.StringPlusAuthorityClient;
import cz.fi.muni.xkremser.editor.client.mods.StringPlusAuthorityPlusTypeClient;
import cz.fi.muni.xkremser.editor.client.view.RecentlyModifiedRecord;
import cz.fi.muni.xkremser.editor.shared.rpc.RecentlyModifiedItem;

// TODO: Auto-generated Javadoc
/**
 * The Class ServerUtils.
 */
public class ClientUtils {

	/**
	 * <p>
	 * Converts a String to a boolean (optimised for performance).
	 * </p>
	 * 
	 * <p>
	 * <code>'true'</code>, <code>'on'</code> or <code>'yes'</code> (case
	 * insensitive) will return <code>true</code>. Otherwise, <code>false</code>
	 * is returned.
	 * </p>
	 * 
	 * <p>
	 * This method performs 4 times faster (JDK1.4) than
	 * <code>Boolean.valueOf(String)</code>. However, this method accepts 'on' and
	 * 'yes' as true values.
	 * 
	 * <pre>
	 *   BooleanUtils.toBoolean(null)    = false
	 *   BooleanUtils.toBoolean("true")  = true
	 *   BooleanUtils.toBoolean("TRUE")  = true
	 *   BooleanUtils.toBoolean("tRUe")  = true
	 *   BooleanUtils.toBoolean("on")    = true
	 *   BooleanUtils.toBoolean("yes")   = true
	 *   BooleanUtils.toBoolean("false") = false
	 *   BooleanUtils.toBoolean("x gti") = false
	 * </pre>
	 * 
	 * @param str
	 *          the String to check
	 * @return the boolean value of the string, <code>false</code> if no match
	 */
	public static boolean toBoolean(String str) {
		// Previously used equalsIgnoreCase, which was fast for interned 'true'.
		// Non interned 'true' matched 15 times slower.
		//
		// Optimisation provides same performance as before for interned 'true'.
		// Similar performance for null, 'false', and other strings not length
		// 2/3/4.
		// 'true'/'TRUE' match 4 times slower, 'tRUE'/'True' 7 times slower.
		if (str == "true") {
			return true;
		}
		if (str == null) {
			return false;
		}
		switch (str.length()) {
			case 2: {
				char ch0 = str.charAt(0);
				char ch1 = str.charAt(1);
				return (ch0 == 'o' || ch0 == 'O') && (ch1 == 'n' || ch1 == 'N');
			}
			case 3: {
				char ch = str.charAt(0);
				if (ch == 'y') {
					return (str.charAt(1) == 'e' || str.charAt(1) == 'E') && (str.charAt(2) == 's' || str.charAt(2) == 'S');
				}
				if (ch == 'Y') {
					return (str.charAt(1) == 'E' || str.charAt(1) == 'e') && (str.charAt(2) == 'S' || str.charAt(2) == 's');
				}
				return false;
			}
			case 4: {
				char ch = str.charAt(0);
				if (ch == 't') {
					return (str.charAt(1) == 'r' || str.charAt(1) == 'R') && (str.charAt(2) == 'u' || str.charAt(2) == 'U')
							&& (str.charAt(3) == 'e' || str.charAt(3) == 'E');
				}
				if (ch == 'T') {
					return (str.charAt(1) == 'R' || str.charAt(1) == 'r') && (str.charAt(2) == 'U' || str.charAt(2) == 'u')
							&& (str.charAt(3) == 'E' || str.charAt(3) == 'e');
				}
			}
		}
		return false;
	}

	/**
	 * To record.
	 * 
	 * @param item
	 *          the item
	 * @return the recently modified record
	 */
	public static RecentlyModifiedRecord toRecord(RecentlyModifiedItem item) {
		return new RecentlyModifiedRecord(item.getUuid(), item.getName(), item.getDescription(), item.getModel());
	}

	public static String escapeHtml(String maybeHtml) {
		final com.google.gwt.user.client.Element div = DOM.createDiv();
		DOM.setInnerText(div, maybeHtml);
		return DOM.getInnerHTML(div);
	}

	public static List<List<String>> toListOfListOfStrings(List<StringPlusAuthorityClient> frequency) {
		if (frequency == null)
			return null;
		List<List<String>> outerList = new ArrayList<List<String>>(frequency.size());
		for (StringPlusAuthorityClient value : frequency) {
			if (value == null)
				continue;
			List<String> innerList = new ArrayList<String>(2);
			innerList.add(value.getValue());
			innerList.add(value.getAuthority());
			outerList.add(innerList);
		}
		return outerList;
	}

	public static List<List<String>> toListOfListOfStrings(List<StringPlusAuthorityPlusTypeClient> toConvert, boolean something) {
		if (toConvert == null)
			return null;
		List<List<String>> outerList = new ArrayList<List<String>>(toConvert.size());
		for (StringPlusAuthorityPlusTypeClient value : toConvert) {
			if (value == null)
				continue;
			List<String> innerList = new ArrayList<String>(2);
			innerList.add(value.getValue());
			innerList.add(value.getType());
			innerList.add(value.getAuthority());
			outerList.add(innerList);
		}
		return outerList;
	}

}
