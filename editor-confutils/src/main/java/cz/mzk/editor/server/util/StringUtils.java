package cz.mzk.editor.server.util;


import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by rumanekm on 2.2.15.
 */
public class StringUtils {
    public static String doTheSubstitution(String input, Map<String, String> properties) {
        Pattern foo = Pattern.compile("(?i)\\$\\{([a-zA-Z]+):?(-?[0-9])?:?(-?[0-9])?\\}");
        Matcher matcher = foo.matcher(input);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "");
            String beforeSubstringModification = properties.get(matcher.group(1));
            int start = -1;
            int end = -1;
            try {
                start = matcher.group(2) == null ? 0 : Math.min(Integer.parseInt(matcher.group(2)),
                        beforeSubstringModification.length() - 1);
                end = matcher.group(3) == null ? beforeSubstringModification.length() : Math.min(
                        Integer.parseInt(matcher.group(3)), beforeSubstringModification.length());
            } catch (NumberFormatException nfe) {
                start = 0;
                end = beforeSubstringModification.length();
            }
            if (start < 0) {
                sb.append(beforeSubstringModification.substring(beforeSubstringModification.length() + start));
            } else {
                sb.append(beforeSubstringModification.substring(start > 0 ? start : 0,
                        end < 0 ? beforeSubstringModification.length() + end : end));
            }
        }
        matcher.appendTail(sb);
        return sb.toString();
    }
}
