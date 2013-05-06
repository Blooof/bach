package ru.ifmo.ctddev.larionov.bach.classifier;

import org.junit.Before;
import org.junit.Test;
import ru.ifmo.ctddev.larionov.bach.comparator.IComparator;
import ru.ifmo.ctddev.larionov.bach.site.Site;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * User: Oleg Larionov
 * Date: 07.05.13
 * Time: 0:48
 */
public class ClassifierTest {

    private static final int DEFAULT_COMPARATORS_COUNT = 2;
    private static final double DEFAULT_COMPARATOR_VALUE = 1.0;
    private static final double EPS = 1e-6;
    private double[] multipliers;
    private List<IComparator> mockedComparators;
    private Classifier classifier;

    @Before
    public void setUp() throws Exception {
        mockedComparators = getComparators(DEFAULT_COMPARATORS_COUNT);
        multipliers = getMultipliers(DEFAULT_COMPARATORS_COUNT);

        classifier = new Classifier(mockedComparators, multipliers);
    }

    private double[] getMultipliers(int count) {
        double[] multipliers = new double[count];

        for (int i = 0; i < multipliers.length; i++) {
            multipliers[i] = DEFAULT_COMPARATOR_VALUE;
        }

        return multipliers;
    }

    private List<IComparator> getComparators(int count) {
        List<IComparator> comparators = new ArrayList<IComparator>();

        for (int i = 0; i < count; ++i) {
            IComparator comparator = mock(IComparator.class);
            when(comparator.compare(any(Site.class), any(Site.class))).thenReturn(DEFAULT_COMPARATOR_VALUE);
            comparators.add(comparator);
        }

        return comparators;
    }

    @Test
    public void testClassify() throws Exception {
        Site first = getSiteWithUrl("http://first.site.com");
        Site second = getSiteWithUrl("http://second.site.com");

        double actual = classifier.classify(first, second);

        double expected = 0;
        for (int i = 0; i < mockedComparators.size(); i++) {
            IComparator comparator = mockedComparators.get(i);
            expected += multipliers[i] * comparator.compare(first, second);
        }
        assertEquals(expected, actual, EPS);

    }

    private Site getSiteWithUrl(String url) {
        return new Site(url);
    }
}
