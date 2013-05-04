package ru.ifmo.ctddev.larionov.bach.site;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import ru.ifmo.ctddev.larionov.bach.exception.ClassifierRuntimeException;

import java.io.IOException;

/**
 * User: Oleg Larionov
 * Date: 04.05.13
 * Time: 23:23
 */
public class Site {
    private String url;
    private Document doc;

    public Site(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public Document getDocument() {
        if (doc == null) {
            try {
                doc = Jsoup.connect(url).get();
            } catch (IOException e) {
                throw new ClassifierRuntimeException("Cannot get document", e);
            }
        }

        return doc;
    }
}
