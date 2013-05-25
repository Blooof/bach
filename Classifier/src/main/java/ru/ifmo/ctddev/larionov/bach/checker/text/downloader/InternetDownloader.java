package ru.ifmo.ctddev.larionov.bach.checker.text.downloader;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.net.URL;

/**
 * User: Oleg Larionov
 * Date: 25.05.13
 * Time: 13:51
 */
public class InternetDownloader implements IDownloader {

    private static final Logger logger = Logger.getLogger(InternetDownloader.class);
    private static final int TIMEOUT = 3000;

    @Override
    public String download(URL url) {
        try {
            return Jsoup.parse(url, TIMEOUT).body().text();
        } catch (IOException e) {
            logger.warn("Cannot get page", e);
            return null;
        }
    }
}
