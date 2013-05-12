package ru.ifmo.ctddev.larionov.bach.checker.textchecker;

import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * User: Oleg Larionov
 * Date: 12.05.13
 * Time: 17:43
 */
public class ShinglesTextChecker implements ITextChecker {

    private static final int DEFAULT_PARTS_COUNT = 4;
    private int partsCount;

    public ShinglesTextChecker(int partsCount) {
        this.partsCount = partsCount;
    }

    public ShinglesTextChecker() {
        this(DEFAULT_PARTS_COUNT);
    }

    @Override
    public double checkText(String text1, String text2) {
        Set<String> text1Shingles = getShingles(text1);
        Set<String> text2Shingles = getShingles(text2);

        Set<String> intersection = new HashSet<>(text1Shingles);
        intersection.retainAll(text2Shingles);
        double intersectionSize = intersection.size();

        text1Shingles.addAll(text2Shingles);
        double unionSize = text1Shingles.size();

        return intersectionSize / unionSize;
    }

    private Set<String> getShingles(String text1) {
        Set<String> shingles = new HashSet<>();
        StringTokenizer tokenizer = new StringTokenizer(text1);
        RingBuffer buffer = new RingBuffer(partsCount);

        for (int i = 0; i < partsCount - 1 && tokenizer.hasMoreTokens(); i++) {
            buffer.append(tokenizer.nextToken());
        }

        while (tokenizer.hasMoreTokens()) {
            buffer.append(tokenizer.nextToken());
            shingles.add(buffer.toString());
        }

        return shingles;
    }

    private class RingBuffer {

        private String elements[];
        private int head;

        public RingBuffer(int count) {
            elements = new String[count];
            head = 0;
        }

        public void append(String s) {
            if (elements[head] == null) {
                elements[head] = s;
            } else {
                head = (head + 1) % elements.length;
                elements[head] = s;
            }
        }

        @Override
        public String toString() {
            StringBuffer sb = new StringBuffer();

            for (int i = head; i >= 0; i--) {
                if (sb.length() != 0) {
                    sb.append("_");
                }
                if (elements[i] != null) {
                    sb.append(elements[i]);
                }
            }

            for (int i = elements.length - 1; i > head; i--) {
                if (sb.length() != 0) {
                    sb.append("_");
                }
                if (elements[i] != null) {
                    sb.append(elements[i]);
                }
            }

            return sb.toString();
        }
    }
}
