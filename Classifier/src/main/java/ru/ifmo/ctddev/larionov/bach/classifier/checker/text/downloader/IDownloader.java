package ru.ifmo.ctddev.larionov.bach.classifier.checker.text.downloader;

import java.net.URL;

/**
 * User: Oleg Larionov
 * Date: 25.05.13
 * Time: 13:52
 */
public interface IDownloader {
    public String download(URL url);
}
