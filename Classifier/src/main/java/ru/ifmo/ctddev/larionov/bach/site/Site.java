package ru.ifmo.ctddev.larionov.bach.site;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.ifmo.ctddev.larionov.bach.exception.ClassifierRuntimeException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * User: Oleg Larionov
 * Date: 04.05.13
 * Time: 23:23
 */
public class Site implements ISite {

    private static final Logger logger = Logger.getLogger(Site.class);
    private static final int TIMEOUT = 3000;
    private URL url;
    private Document doc;

    public Site(String url) throws MalformedURLException {
        this.url = new URL(url);
    }

    @Override
    public URL getUrl() {
        return url;
    }

    @Override
    public List<ISite> getChildren() {
        Document document = getDocument();
        List<ISite> children = new ArrayList<>();
        Elements links = document.select("a[href]");
        for (Element link : links) {
            String currentUrl = link.attr("href");
            if (currentUrl.startsWith("/")) {
                currentUrl = String.format("%s://%s%s", url.getProtocol(), url.getHost(), currentUrl);
            }
            try {
                children.add(new Site(currentUrl));
            } catch (MalformedURLException e) {
                logger.warn(String.format("Wrong url on site %s: %s", url, currentUrl));
            }
        }

        logger.debug(String.format("Links on %s: %s", getUrl(), children));
        return children;
    }

    @Override
    public String getText() {
        Document document = getDocument();
        return document.text();
    }

    private Document getDocument() {
        if (doc == null) {
            try {
                doc = Jsoup.parse(url, TIMEOUT);
                logger.debug("Doc downloaded: " + doc);
            } catch (IOException e) {
                throw new ClassifierRuntimeException("Cannot get document", e);
            }
        }

        return doc;
    }

    @Override
    public String toString() {
        return "Site{" +
                "url='" + url + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(url);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final Site other = (Site) obj;
        return Objects.equals(this.url, other.url);
    }
}
