package ru.ifmo.ctddev.larionov.bach.site;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * User: Oleg Larionov
 * Date: 10.05.13
 * Time: 22:49
 */
public class FileIterator implements Iterator<ISite> {

    private final Logger logger = Logger.getLogger(FileIterator.class);
    private Scanner scanner;

    public FileIterator(File file) throws FileNotFoundException {
        scanner = new Scanner(file);
    }

    @Override
    public boolean hasNext() {
        return scanner.hasNextLine();
    }

    @Override
    public ISite next() {
        while (scanner.hasNextLine()) {
            try {
                ISite site = new Site(scanner.next());
                return site;
            } catch (MalformedURLException e) {
                logger.warn("Cannot parse url", e);
            }
        }

        throw new NoSuchElementException();
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("Cannot delete link from file");
    }
}
