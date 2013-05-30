package ru.ifmo.ctddev.larionov.bach.database;

import com.orientechnologies.orient.core.record.impl.ODocument;
import com.orientechnologies.orient.core.sql.query.OSQLSynchQuery;
import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ifmo.ctddev.larionov.bach.common.site.WeightedPair;
import ru.ifmo.ctddev.larionov.bach.exception.ClassifierRuntimeException;

import java.util.Iterator;
import java.util.List;

/**
 * User: Oleg Larionov
 * Date: 29.05.13
 * Time: 1:40
 */
@Service("mirrorsBase")
public class MirrorsBase implements IMirrorsBase {

    private static final Logger logger = Logger.getLogger(MirrorsBase.class);
    private static final String HOST = "host";
    private static final String MIRROR = "mirror";
    private static final String WEIGHT = "weight";
    private static final String SHORTEST_PATH = "shortestPath";
    private IGraphFactory graphFactory;

    @Autowired
    public void setGraphFactory(IGraphFactory graphFactory) {
        this.graphFactory = graphFactory;
    }

    @Override
    public void addMirrors(WeightedPair pair) {
        logger.debug("Adding new pair to mirrors " + pair);
        OrientGraph graph = graphFactory.getGraph();
        try {
            Vertex first = getVertex(graph, pair.getFirstHost().getHost());
            Vertex second = getVertex(graph, pair.getSecondHost().getHost());
            logger.debug(String.format("New vertices: %s, %s", first, second));
            if (first.equals(second)) {
                return;
            }

            if (!existsEdge(first, second)) {
                Edge e = graph.addEdge(null, first, second, MIRROR);
                e.setProperty(WEIGHT, pair.getWeight());
            }
        } catch (Exception e) {
            graph.rollback();
            throw new ClassifierRuntimeException("Fail to add mirrors to database", e);
        } finally {
            logger.debug("Transaction completed");
            graph.shutdown();
        }
    }

    private boolean existsEdge(Vertex first, Vertex second) {
        for (Vertex neighbour : first.getVertices(Direction.BOTH)) {
            if (second.equals(neighbour)) {
                return true;
            }
        }

        return false;
    }

    private Vertex getVertex(OrientGraph graph, String host) {
        Iterator<Vertex> iterator = getVertexIterator(graph, host);
        Vertex vertex;
        if (!iterator.hasNext()) {
            vertex = graph.addVertex(null);
            vertex.setProperty(HOST, host);
        } else {
            vertex = iterator.next();
        }
        return vertex;
    }

    private Vertex findVertex(OrientGraph graph, String host) {
        Iterator<Vertex> iterator = getVertexIterator(graph, host);
        Vertex vertex = null;
        if (iterator.hasNext()) {
            vertex = iterator.next();
        }
        return vertex;
    }

    private Iterator<Vertex> getVertexIterator(OrientGraph graph, String host) {
        Iterable<Vertex> vertices = graph.getVertices(HOST, host);
        return vertices.iterator();
    }

    @Override
    public double checkMirrors(String firstHost, String secondHost) {
        logger.debug(String.format("Check mirrors request: %s, %s", firstHost, secondHost));
        OrientGraph graph = graphFactory.getGraph();
        try {
            Vertex first = findVertex(graph, firstHost);
            Vertex second = findVertex(graph, secondHost);
            logger.debug("Found vertices: " + first + ", " + second);
            if (first == null || second == null) {
                return 0;
            }
            if (first.equals(second)) {
                return 1;
            }

            String query = String.format("select shortestPath(%s, %s, 'BOTH')", first.getId(), second.getId());
            List<ODocument> documents = graph.getRawGraph().query(new OSQLSynchQuery(query));
            List<ODocument> path = documents.get(0).field(SHORTEST_PATH);

            double weight;
            if (path.size() > 1) {
                weight = 1.0;
            } else {
                weight = 0.0;
            }

            return weight;
        } catch (Exception e) {
            graph.rollback();
            throw new ClassifierRuntimeException("Cannot check mirrors", e);
        } finally {
            logger.debug("Transaction completed");
            graph.shutdown();
        }
    }
}
