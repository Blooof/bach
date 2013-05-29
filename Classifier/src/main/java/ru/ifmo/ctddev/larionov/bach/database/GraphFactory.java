package ru.ifmo.ctddev.larionov.bach.database;

import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * User: Oleg Larionov
 * Date: 29.05.13
 * Time: 16:41
 */
@Service("graphFactory")
public class GraphFactory implements IGraphFactory {

    private String databasePath;
    private String login;
    private String password;

    @Value("${database.path}")
    public void setDatabasePath(String databasePath) {
        this.databasePath = databasePath;
    }

    @Value("${database.login}")
    public void setLogin(String login) {
        this.login = login;
    }

    @Value("${database.password}")
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public OrientGraph getGraph() {
        return new OrientGraph(databasePath, login, password);
    }
}
