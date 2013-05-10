package ru.ifmo.ctddev.larionov.bach.site;

import ru.ifmo.ctddev.larionov.bach.exception.ClassifierRuntimeException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;

/**
 * User: Oleg Larionov
 * Date: 10.05.13
 * Time: 22:48
 */
public class FileList implements Iterable<ISite> {

    private File file;

    public FileList(File file) {
        this.file = file;
    }

    @Override
    public Iterator<ISite> iterator() {
        try {
            return new FileIterator(file);
        } catch (FileNotFoundException e) {
            throw new ClassifierRuntimeException("Cannot find file " + file);
        }
    }
}
