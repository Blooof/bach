package ru.ifmo.ctddev.larionov.bach.common.site;

import org.codehaus.jackson.map.annotate.JsonDeserialize;

import java.net.URL;
import java.util.List;

/**
 * User: Oleg Larionov
 * Date: 07.05.13
 * Time: 22:40
 */
@JsonDeserialize(as = Site.class)
public interface ISite {
    public String getHost();

    public void setHost(String host);

    public List<URL> getLinks();

    public void setLinks(List<URL> links);
}
