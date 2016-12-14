package zhiyuan;

import org.junit.Test;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.Assert.*;

/**
 * Created by markma on 16/12/10.
 */
public class SuperStrTest {
    @Test
    public void slice() throws Exception {
        assertEquals(SuperStr.slice("mazhiyuan", 0, 1), "azhiyuan");
        assertEquals(SuperStr.slice("mazhiyuan", 0, 10), "");
        assertEquals(SuperStr.slice("mazhiyuan", 0, 0), "mazhiyuan");
        assertEquals(SuperStr.slice("mazhiyuan", 3, 2), "mazyuan");
        assertEquals(SuperStr.slice("mazhiyuan", 4, 3), "mazhan");
    }

    @Test
    public void slice_withInsert() throws Exception {
        assertEquals(SuperStr.slice("mazhiyuan", 4, 3, "b"), "mazhban");
        assertEquals(SuperStr.slice("mazhiyuan", 4, 3, "b", "d", "w"), "mazhbdwan");
        assertEquals(SuperStr.slice("mazhiyuan", 4, 0, "b", "d"), "mazhbdiyuan");
    }

    @Test
    public void append() throws Exception {
        assertEquals(SuperStr.append("m", "a", "r", "k"), "mark");
        assertEquals(SuperStr.append("", "m", "a", "r", "k"), "mark");
    }

    @Test
    public void appendArray() throws Exception {
        assertEquals(SuperStr.appendArray("", new String[]{"ma", "rk"}), "mark");
        assertEquals(SuperStr.appendArray("mark", new String[]{}), "mark");
        assertEquals(SuperStr.appendArray("ma", new String[]{"rk"}), "mark");
    }

    @Test
    public void at() throws Exception {
        assertTrue(SuperStr.at("mark", 2).get().equals('r'));
        assertSame(SuperStr.at("mark", 5), Optional.empty());
        assertSame(SuperStr.at("mark", -3).get(), 'a');
    }

    @Test
    public void between() throws Exception {
        assertArrayEquals(SuperStr.between("[abc][cde]", "[", "]"), new String[]{"abc", "cde"});
        assertArrayEquals(SuperStr.between("[abc]f[cde]", "[", "]"), new String[]{"abc", "cde"});
    }

    @Test
    public void chars() throws Exception {
        assertArrayEquals(SuperStr.chars("mark"), new String[]{"m", "a", "r", "k"});
    }

    @Test
    public void collapseSpace() throws Exception {
        assertEquals(SuperStr.collapseSpace("mark     ma"), "mark ma");
        assertEquals(SuperStr.collapseSpace("    mark     ma   zhiyuan"), " mark ma zhiyuan");
    }

    @Test
    public void contains() throws Exception {
        assertTrue(SuperStr.contains("mark", "m"));
        assertFalse(SuperStr.contains("mark", "M"));
    }

    @Test
    public void contains_notCaseSensitive() throws Exception {
        assertTrue(SuperStr.contains("mark", "M", false));
    }

    @Test
    public void containsAll() throws Exception {
        assertTrue(SuperStr.containsAll("mark zhiyuan", new String[]{"m", "z", "i"}));
        assertFalse(SuperStr.containsAll("mark zhiyuan", new String[]{"m", "Z", "i"}));
    }

    @Test
    public void containsAll_notCaseSensitive() throws Exception {
        assertTrue(SuperStr.containsAll("mark zhiyuan", new String[]{"m", "Z", "i"}, false));
    }

    @Test
    public void containsAny() throws Exception {
        assertTrue(SuperStr.containsAny("mark zhiyuan", new String[]{"m", "Z", "i"}));
        assertFalse(SuperStr.containsAny("mark zhiyuan", new String[]{"M", "Z"}));
    }

    @Test
    public void containsAny_notCaseSensitive() throws Exception {
        assertTrue(SuperStr.containsAny("mark zhiyuan", new String[]{"M", "Z"}, false));
    }

    @Test
    public void countStr() throws Exception {
        assertSame(SuperStr.countStr("maaaaark", "a"), 5);
        assertSame(SuperStr.countStr("maaaaark", "aa"), 2);
        assertSame(SuperStr.countStr("maaaaark", "aaa"), 1);
    }

    @Test
    public void countStr_notCaseSensitive_allowOverlapping() throws Exception {
        assertSame(SuperStr.countStr("maaaaaaaaaark", "Aa", false, false), 5);
        assertSame(SuperStr.countStr("maaaaaaaaaark", "Aa", false, true), 9);
    }

    @Test
    public void isValid() throws Exception {
        assertTrue(SuperStr.isValid(" "));
        assertFalse(SuperStr.isValid(""));
        assertFalse(SuperStr.isValid(null));
    }

    @Test
    public void isBlank() throws Exception {
        assertFalse(SuperStr.isBlank(" a "));
        assertTrue(SuperStr.isBlank(" "));
        assertFalse(SuperStr.isBlank(""));
        assertFalse(SuperStr.isBlank(null));
    }

    @Test
    public void isNotBlank() throws Exception {
        assertTrue(SuperStr.isNotBlank("  a"));
        assertFalse(SuperStr.isNotBlank("  "));
        assertFalse(SuperStr.isNotBlank(null));
        assertFalse(SuperStr.isNotBlank(""));
    }

    @Test
    public void base64Decode() throws Exception {
        assertEquals(SuperStr.base64Decode("bWFyaw=="), "mark");
    }

    @Test
    public void base64Encode() throws Exception {
        assertEquals(SuperStr.base64Encode("mark"), "bWFyaw==");
    }

    @Test
    public void insert() throws Exception {
        assertEquals(SuperStr.insert("mk ma", "ar", 1), "mark ma");
        assertEquals(SuperStr.insert("mk ma", "ar", 6), "mk ma");
    }

    @Test
    public void leftTrim() throws Exception {
        assertEquals(SuperStr.leftTrim("      mark"), "mark");
        assertEquals(SuperStr.leftTrim("      mark    "), "mark    ");
        assertEquals(SuperStr.leftTrim("      mar k"), "mar k");
    }

    @Test
    public void rightTrim() throws Exception {
        assertEquals(SuperStr.rightTrim("mark    "), "mark");
        assertEquals(SuperStr.rightTrim("      mark    "), "      mark");
        assertEquals(SuperStr.rightTrim("mar k      "), "mar k");
    }

    @Test
    public void midTrim() throws Exception {
        assertEquals(SuperStr.midTrim("mark    "), "mark    ");
        assertEquals(SuperStr.midTrim("mar k      "), "mark      ");
    }

    @Test
    public void trims() throws Exception {
        assertArrayEquals(SuperStr.trims(new String[]{"ma", "rk", null, "", "  "}), new String[]{"ma", "rk"});
    }

    @Test
    public void removeNonWords() throws Exception {
        assertEquals(SuperStr.removeNonWords("ma^%$rk"), "mark");
    }

    @Test
    public void repeat() throws Exception {
        assertEquals(SuperStr.repeat("a", 5), "aaaaa");
        assertEquals(SuperStr.repeat("a", 1), "a");
        assertEquals(SuperStr.repeat("a", 0), "");
    }

    @Test
    public void shuffle() throws Exception {
        assertNotSame(SuperStr.shuffle("mark"), "mark");
        assertEquals(SuperStr.shuffle(""), "");
        assertEquals(SuperStr.shuffle("m"), "m");
    }

    @Test
    public void reverse() throws Exception {
        assertEquals(SuperStr.reverse("mark"), "kram");
    }

}