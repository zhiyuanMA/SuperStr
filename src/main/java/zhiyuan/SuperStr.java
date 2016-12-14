package zhiyuan;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by mazhiyuan on 2016/12/1.
 */
public final class SuperStr {
    private static final Predicate<Object> NULL_PREDICATE = str -> str == null;

    private SuperStr() {
    }

    /**
     * delete or replace characters in the value
     *
     * @param value
     * @param index
     * @param op
     * @param strs
     * @return
     */
    public static String slice(final String value, final int index, final int op, final String... strs) {
        validate(value);
        if (value.length() < op) {
            return "";
        }

        if (strs == null) {
            return value.substring(0, index) + value.substring(index + op);
        }

        StringJoiner sj = new StringJoiner("", value.substring(0, index), value.substring(index + op));
//        for (String str : strs) {
//            sj.add(str);
//        }
        Arrays.stream(strs).forEach(sj::add);
        return sj.toString();
    }

    /**
     * delete characters in the value
     *
     * @param value
     * @param index
     * @param op
     * @return
     */
    public static String slice(final String value, final int index, final int op) {
        return slice(value, index, op, null);
    }


    /**
     * Append Strings to the value
     *
     * @param str  the value
     * @param strs Strings to append
     * @return full String
     */
    public static String append(final String str, final String... strs) {
        return appendArray(str, strs);
    }

    /**
     * Append an array of String to the value
     *
     * @param str  the value
     * @param strs the array of String to append
     * @return full String
     */
    public static String appendArray(final String str, final String[] strs) {
        validate(str);
        if (strs == null || strs.length == 0) {
            return str;
        }
        StringJoiner sj = new StringJoiner("", str, "");
        Arrays.stream(strs).forEach(sj::add);
        return sj.toString();
    }

    /**
     * Get the character at index. This method will take care of negative indexes.
     * The valid value of index is between -(length-1) to (length-1).
     * For values which don't fall under this range Optional.empty will be returned.
     *
     * @param str
     * @param index
     * @return the character at index
     */
    public static Optional<Character> at(final String str, int index) {
        if (!isValid(str)) {
            return Optional.empty();
        }

        int at = index > 0 ? index : str.length() + index;

        return at < str.length() && at > 0 ? Optional.of(str.charAt(at)) : Optional.empty();
    }

    /**
     * Get the sub Strings between start and end String
     *
     * @param str
     * @param start
     * @param end
     * @return the array of sub String
     */
    public static String[] between(final String str, final String start, final String end) {
        validate(str);
        validate(start, "start");
        validate(end, "end");

        String[] strs = str.split(end);

        return Arrays.stream(strs).map(sub -> sub.substring(sub.indexOf(start) + start.length())).toArray(String[]::new);
    }

    /**
     * get the characters in a String Array
     *
     * @param str
     * @return
     */
    public static String[] chars(final String str) {
        validate(str);
        return str.split("");
    }

    /**
     * Replace consecutive whitespace characters with a single space.
     *
     * @param str
     * @return
     */
    public static String collapseSpace(final String str) {
        validate(str, null);
        return str.replaceAll("\\s\\s+", " ");
    }

    /**
     * Verifies that the pattern is contained in the str. The search is case insensitive
     *
     * @param str
     * @param pattern
     * @return
     */
    public static boolean contains(final String str, final String pattern) {
        return contains(str, pattern, true);
    }

    /**
     * Verifies that the pattern is contained in the str.
     *
     * @param str           the value
     * @param pattern       the pattern
     * @param caseSensitive
     * @return
     */
    public static boolean contains(final String str, final String pattern, boolean caseSensitive) {
        validate(str);
        validate(pattern);

        if (caseSensitive) {
            return str.contains(pattern);
        }

        return str.toLowerCase().contains(pattern.toLowerCase());
    }

    /**
     * Verifies that all the patterns are contained in the str. default in case sensitive
     *
     * @param str
     * @param patterns
     * @return
     */
    public static boolean containsAll(final String str, final String[] patterns) {
        return containsAll(str, patterns, true);
    }

    /**
     * Verifies that all the patterns are contained in the str.
     *
     * @param str
     * @param patterns
     * @param caseSensitive
     * @return
     */
    public static boolean containsAll(final String str, final String[] patterns, boolean caseSensitive) {
        validate(str);
        validate(patterns, "patterns");

        return Arrays.stream(patterns).allMatch(s -> contains(str, s, caseSensitive));
    }

    /**
     * Verifies that any the patterns are contained in the str. default in case sensitive
     *
     * @param str
     * @param patterns
     * @return
     */
    public static boolean containsAny(final String str, final String[] patterns) {
        return containsAny(str, patterns, true);
    }

    /**
     * Verifies that any the patterns are contained in the str.
     *
     * @param str
     * @param patterns
     * @param caseSensitive
     * @return
     */
    public static boolean containsAny(final String str, final String[] patterns, boolean caseSensitive) {
        validate(str);
        validate(patterns, "patterns");

        return Arrays.stream(patterns).anyMatch(s -> contains(str, s, caseSensitive));
    }

    /**
     * count the number of pattern in the value. default in case sensitive and not overlapping
     *
     * @param str
     * @param pattern
     * @return
     */
    public static int countStr(final String str, final String pattern) {
        return countStr(str, pattern, true, false);
    }

    /**
     * count the number of pattern in the value.
     *
     * @param str
     * @param pattern
     * @param caseSensitive
     * @param overlapping
     * @return
     */
    public static int countStr(final String str, final String pattern, boolean caseSensitive, boolean overlapping) {
        return countStr(caseSensitive ? str : str.toLowerCase(), caseSensitive ? pattern : pattern.toLowerCase(), overlapping, 0);
    }

    /**
     * check the str is NULL or length is 0
     *
     * @param str
     * @return
     */
    public static boolean isValid(final String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        return true;
    }

    /**
     * check if the str contains space only
     *
     * @param str
     * @return
     */
    public static boolean isBlank(final String str) {
        return isValid(str) && (!isValid(str.trim()) && str.trim().length() == 0);
    }

    /**
     * check the string is not blank
     *
     * @param str
     * @return
     */
    public static boolean isNotBlank(final String str) {
        return isValid(str) && isValid(str.trim());
    }

    public static String base64Decode(final String str) {
        validate(str);
        return new String(Base64.getDecoder().decode(str));
    }

    public static String base64Encode(final String str) {
        validate(str);
        return Base64.getEncoder().encodeToString(str.getBytes());
    }

    /**
     * insert pattern in the string
     *
     * @param str
     * @param pattern
     * @param index
     * @return
     */
    public static String insert(final String str, final String pattern, final int index) {
        validate(str);
        validate(pattern);

        if (index > str.length()) {
            return str;
        }
        return append(str.substring(0, index), pattern, str.substring(index));
    }

    /**
     * remove the spaces in the left
     *
     * @param value
     * @return
     */
    public static String leftTrim(final String value) {
        validate(value);
        return value.replaceAll("^\\s+", "");
    }

    /**
     * remove the spaces in the right
     *
     * @param value
     * @return
     */
    public static String rightTrim(final String value) {
        validate(value);
        return value.replaceAll("\\s+$", "");
    }

    /**
     * remove the spaces in the middle
     *
     * @param value
     * @return
     */
    public static String midTrim(final String value) {
        validate(value);
        return value.replaceAll("\\b\\s*\\b", "");
    }

    /**
     * remove all the invalid strings(NULL,empty String,blank String) in String Array
     *
     * @param strs
     * @return
     */
    public static String[] trims(String[] strs) {
        validate(strs, "Input array");
        return Arrays.stream(strs).filter(SuperStr::isNotBlank).toArray(String[]::new);
    }

    /**
     * remove all the Non-word in a String
     *
     * @param value
     * @return
     */
    public static String removeNonWords(final String value) {
        validate(value);
        return value.replaceAll("[^\\w]+", "");
    }

    /**
     * repeat str in multi times
     *
     * @param str
     * @param multi
     * @return
     */
    public static String repeat(final String str, final int multi) {
        validate(str);
        return Stream.generate(() -> str).limit(multi).collect(Collectors.joining());
    }

    public static String shuffle(final String str) {
        String crs[] = chars(str);
        List<String> ls = Arrays.asList(crs);
        Collections.shuffle(ls);
        StringJoiner sj = new StringJoiner("");
        ls.stream().forEach(sj::add);
        return sj.toString();
    }

    public static String reverse(final String str) {
        validate(str);
        return new StringBuilder(str).reverse().toString();
    }

    private static int countStr(final String str, final String pattern, boolean overlapping, int count) {
        int position = str.indexOf(pattern);
        if (position == -1) {
            return count;
        }
        int offset;
        if (!overlapping) {
            offset = position + pattern.length();
        } else {
            offset = position + 1;
        }
        return countStr(str.substring(offset), pattern, overlapping, ++count);
    }

    private static void validate(final String str) {
        validate(str, null);
    }

    private static void validate(Object value, String msg) {
        if (NULL_PREDICATE.test(value)) {
            throw new IllegalArgumentException((isBlank(msg) ? "'value'" : "'" + msg) + "'" + " should not be NULL");
        }
    }
}
