package ru.ifmo.ctddev.larionov.bach;

import ru.ifmo.ctddev.larionov.bach.json.JsonExtractor;

import java.io.File;

/**
 * User: Oleg Larionov
 * Date: 23.05.13
 * Time: 18:36
 */
public class ExtractMain {
    public static final String FILENAME = "blogspot.com";

    public static void main(String[] args) {
        File from = new File("run/" + FILENAME + ".json");
        File to = new File("run/" + FILENAME + ".txt");
        JsonExtractor.extract(from, to);
    }
}
