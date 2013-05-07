package ru.ifmo.ctddev.larionov.bach.site;

import java.util.List;

/**
 * User: Oleg Larionov
 * Date: 07.05.13
 * Time: 22:40
 */
public interface ISite {
    public String getHost();

    public List<ISite> getChildren();

    public List<ISite> getAllLinks();
}
