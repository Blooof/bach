package ru.ifmo.ctddev.larionov.bach.checker.text;

import org.junit.Before;
import org.junit.Test;
import ru.ifmo.ctddev.larionov.bach.checker.text.checker.ShinglesTextChecker;

import static org.junit.Assert.assertEquals;

/**
 * User: Oleg Larionov
 * Date: 12.05.13
 * Time: 17:57
 */
public class ShinglesTextCheckerTest {

    private static final double EPS = 1e-6;
    private final String text = "Lorem ipsum dolor sit amet consectetur adipisicing elit sed do eiusmod tempor incididunt";
    private ShinglesTextChecker textChecker;

    @Before
    public void setUp() throws Exception {
        textChecker = new ShinglesTextChecker();
    }

    @Test
    public void testCheckEqualTexts() throws Exception {
        double actual = textChecker.checkText(text, text);

        assertEquals(1d, actual, EPS);
    }

    @Test
    public void testCheckDifferentTexts() throws Exception {
        double actual = textChecker.checkText(text, "Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi");

        assertEquals(0d, actual, EPS);
    }

    @Test
    public void testCheck50Percent() throws Exception {
        double actual = textChecker.checkText(text, "Lorem ipsum dolor sit amet consectetur adipisicing elit");

        assertEquals(0.5, actual, EPS);
    }
}
