package ru.ifmo.ctddev.larionov.bach.comparator;

import org.junit.Test;
import ru.ifmo.ctddev.larionov.bach.site.FileList;
import ru.ifmo.ctddev.larionov.bach.site.ISite;
import ru.ifmo.ctddev.larionov.bach.site.WeightedPair;

import java.io.File;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * User: Oleg Larionov
 * Date: 11.05.13
 * Time: 0:56
 */
public class IPAddressComparatorTest {

    @Test
    public void testCreatePairs() throws Exception {
        IComparator comparator = new IPAddressComparator();

        Iterable<ISite> sites = new FileList(new File("src/test/resources/testFileIterator.txt"));
        List<WeightedPair> pairs = comparator.createPairs(sites);

        assertEquals(pairs.size(), 0);
    }
}
