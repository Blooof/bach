package ru.ifmo.ctddev.larionov.bach.common.site;


import java.net.URL;
import java.util.List;
import java.util.Objects;

/**
 * User: Oleg Larionov
 * Date: 04.05.13
 * Time: 23:23
 */
public class Site implements ISite {

    private String host;
    private List<URL> links;

    public Site() {
    }

    public Site(String host, List<URL> links) {
        this.host = host;
        this.links = links;
    }

    public Site(String host) {
        this(host, null);
    }

    @Override
    public String getHost() {
        return host;
    }

    @Override
    public void setHost(String host) {
        this.host = host;
    }

    @Override
    public List<URL> getLinks() {
        return links;
    }

    @Override
    public void setLinks(List<URL> links) {
        this.links = links;
    }

    @Override
    public String toString() {
        return "Site{" +
                "host='" + host + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(host);
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
        return Objects.equals(this.host, other.host);
    }
}
