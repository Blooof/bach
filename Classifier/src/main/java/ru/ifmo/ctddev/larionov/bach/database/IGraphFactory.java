package ru.ifmo.ctddev.larionov.bach.database;

import com.tinkerpop.blueprints.impls.orient.OrientGraph;

/**
 * User: Oleg Larionov
 * Date: 29.05.13
 * Time: 16:40
 */
public interface IGraphFactory {
    public OrientGraph getGraph();
}
