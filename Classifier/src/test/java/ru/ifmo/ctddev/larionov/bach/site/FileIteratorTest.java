package ru.ifmo.ctddev.larionov.bach.site;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.net.URL;
import java.util.NoSuchElementException;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * User: Oleg Larionov
 * Date: 10.05.13
 * Time: 22:55
 */
public class FileIteratorTest {

    private final String FILENAME = "Classifier/src/test/resources/testFileIterator.txt";
    private FileIterator iterator;

    @Before
    public void setUp() throws Exception {
        File file = new File(FILENAME);
        iterator = new FileIterator(file);
    }

    @Test
    public void testHasNext() {
        assertTrue(iterator.hasNext());
        iterator.next();
        assertTrue(iterator.hasNext());
    }

    @Test
    public void testHasNextEndOfFile() {
        moveToEnd();
        assertFalse(iterator.hasNext());
    }

    @Test
    public void testNext() throws Exception {
        try (Scanner scanner = new Scanner(new File(FILENAME))) {
            while (scanner.hasNextLine()) {
                String nextLine = scanner.nextLine();
                URL expected = new URL(nextLine);
                assertEquals(expected, iterator.next().getUrl());
            }
        }
    }

    @Test(expected = NoSuchElementException.class)
    public void testNextEndOfFile() {
        moveToEnd();
        iterator.next();
    }

    private void moveToEnd() {
        iterator.next();
        iterator.next();
        iterator.next();
        iterator.next();
        iterator.next();
    }
}
