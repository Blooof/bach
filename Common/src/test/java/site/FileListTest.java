package site;

import org.junit.Before;
import org.junit.Test;
import ru.ifmo.ctddev.larionov.bach.common.site.FileList;
import ru.ifmo.ctddev.larionov.bach.common.site.ISite;

import java.io.File;
import java.net.URL;
import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * User: Oleg Larionov
 * Date: 10.05.13
 * Time: 22:55
 */
public class FileListTest {

    private final String FILENAME = "src/test/resources/testFileIterator.txt";
    private Iterator<ISite> iterator;

    @Before
    public void setUp() throws Exception {
        File file = new File(FILENAME);
        FileList list = new FileList(file);
        iterator = list.iterator();
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
        File testFile = new File(FILENAME);
        Set<String> hosts = new HashSet<>();
        try (Scanner scanner = new Scanner(testFile)) {
            while (scanner.hasNextLine()) {
                String url = scanner.nextLine().trim();
                URL page = new URL(url);
                hosts.add(page.getHost());
            }
        }

        int count = 0;
        while (iterator.hasNext()) {
            assertTrue(hosts.contains(iterator.next().getHost()));
            ++count;
        }

        assertEquals(hosts.size(), count);
    }

    @Test(expected = NoSuchElementException.class)
    public void testNextEndOfFile() {
        moveToEnd();
        iterator.next();
    }

    private void moveToEnd() {
        iterator.next();
        iterator.next();
    }
}
