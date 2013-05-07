package ru.ifmo.ctddev.larionov.bach.site;

import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.ifmo.ctddev.larionov.bach.exception.ClassifierRuntimeException;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.jsoup.Jsoup.connect;

/**
 * User: Oleg Larionov
 * Date: 04.05.13
 * Time: 23:23
 */
public class Site implements ISite {

    private static Logger logger = Logger.getLogger(Site.class);
    private String url;
    private Document doc;

    public Site(String url) {
        this.url = url;
    }

    @Override
    public String getHost() {
        return url;
    }

    @Override
    public List<ISite> getChildren() {
        Document document = getDocument();
        List<ISite> children = new ArrayList<>();
        Elements links = document.getElementsByTag("a");
        for (Element link : links) {
            children.add(new Site(link.text()));
        }

        logger.debug(String.format("Links on %s: %s", getHost(), children));
        return children;
    }

    @Override
    public List<ISite> getAllLinks() {
        // TODO implement
        throw new NotImplementedException();
    }

    private Document getDocument() {
        if (doc == null) {
            try {
                doc = connect(url).get();
                logger.debug("Doc downloaded: " + doc);
            } catch (IOException e) {
                throw new ClassifierRuntimeException("Cannot get document", e);
            }
        }

        return doc;
    }
}
