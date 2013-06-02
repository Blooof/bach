package ru.ifmo.ctddev.larionov.bach.classifier.checker.text.downloader;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;

/**
 * User: Oleg Larionov
 * Date: 25.05.13
 * Time: 13:51
 */
@Service("inetDownloader")
public class InternetDownloader implements IDownloader {

    private static final Logger logger = Logger.getLogger(InternetDownloader.class);
    private static final int TIMEOUT = 3000;
    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:5.0) Gecko/20100101 Firefox/5.0";

    @Override
    public String download(URL url) {
        String text = null;
        try {
            Document doc = Jsoup.connect(url.toString())
                    .userAgent(USER_AGENT).timeout(TIMEOUT)
                    .get();

            text = doc.body().text();
        } catch (IOException e) {
            logger.warn("Cannot get page", e);
        }
        return text;
    }
}
