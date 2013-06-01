package ru.ifmo.ctddev.larionov.bach.common.site;

import org.apache.log4j.Logger;
import ru.ifmo.ctddev.larionov.bach.exception.ClassifierRuntimeException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * User: Oleg Larionov
 * Date: 10.05.13
 * Time: 22:48
 */
public class FileList implements Iterable<ISite> {

    private static final int MIN_PAGES_COUNT = 50;
    private static final Logger logger = Logger.getLogger(FileList.class);
    private Map<String, ISite> sites = new HashMap<>();

    public FileList(Iterable<String> list) {
        for (String url : list) {
            parseUrl(url);
        }

        logger.debug("Initial FileList size: " + sites.size());

        removeSmallSites(MIN_PAGES_COUNT);
        logger.debug("FileList created with sites: " + sites.size());
    }

    public FileList(File file) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String url;
            while ((url = br.readLine()) != null) {
                parseUrl(url);
            }
            removeSmallSites(MIN_PAGES_COUNT);
        } catch (IOException e) {
            throw new ClassifierRuntimeException("Cannot open file " + file.getAbsolutePath(), e);
        }
    }

    private void removeSmallSites(int minSize) {
        Iterator<Map.Entry<String, ISite>> iterator = sites.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, ISite> site = iterator.next();
            if (site.getValue().getLinks().size() < minSize) {
                iterator.remove();
            }
        }
    }

    private void parseUrl(String url) {
        url = url.trim();
        try {
            URL page = new URL(url);
            String host = page.getHost();

            if (!sites.containsKey(host)) {
                sites.put(host, new Site(host, new ArrayList<URL>()));
            }

            sites.get(host).getLinks().add(page);
        } catch (MalformedURLException e) {
            logger.warn("Cannot parse url", e);
        }
    }

    @Override
    public Iterator<ISite> iterator() {
        return sites.values().iterator();
    }
}
