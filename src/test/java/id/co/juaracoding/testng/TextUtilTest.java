package id.co.juaracoding.testng;

import id.co.juaracoding.util.TextUtil;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class TextUtilTest {

    @BeforeClass
    public void setup_class() {
        System.out.println("Starting TextUtil tests...");
    }

    @Test
    public void test_is_palindrome_positive_scenario() {
        Assert.assertTrue(TextUtil.isPalindrome("Katak"), "Word 'Katak' should be a palindrome");
        Assert.assertTrue(TextUtil.isPalindrome("Kasur ini rusak"), "Phrase 'Kasur ini rusak' should be a palindrome");
    }

    @Test
    public void test_is_palindrome_edge_case() {
        Assert.assertFalse(TextUtil.isPalindrome(null), "Null should not be a palindrome");
        Assert.assertTrue(TextUtil.isPalindrome(""), "Empty string should be treated as palindrome");
        Assert.assertFalse(TextUtil.isPalindrome("Hello"), "'Hello' is not a palindrome");
    }

    @Test
    public void test_count_vowels_positive_scenario() {
        Assert.assertEquals(TextUtil.countVowels("Hello World"), 3, "There should be 3 vowels in 'Hello World'");
        Assert.assertEquals(TextUtil.countVowels("JuaraCoding"), 5, "There should be 5 vowels in 'JuaraCoding'");
    }

    @Test
    public void test_count_vowels_edge_case() {
        Assert.assertEquals(TextUtil.countVowels(null), 0, "Null should return 0 vowels");
        Assert.assertEquals(TextUtil.countVowels(""), 0, "Empty string should return 0 vowels");
        Assert.assertEquals(TextUtil.countVowels("bcdfg"), 0, "String without vowels should return 0");
    }
}
