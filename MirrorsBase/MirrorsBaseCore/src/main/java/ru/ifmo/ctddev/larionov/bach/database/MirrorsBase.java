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
import ru.ifmo.ctddev.larionov.bach.common.site.ISite;
import ru.ifmo.ctddev.larionov.bach.common.site.WeightedPair;
import ru.ifmo.ctddev.larionov.bach.exception.ClassifierRuntimeException;

import java.util.ArrayList;
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
    private static final double ZERO_THRESHOLD = 0.01;
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
    public List<WeightedPair> checkMirrors(List<ISite> sites) {
        logger.debug("Check mirrors request: sites size: " + sites.size());

        OrientGraph graph = graphFactory.getGraph();
        try {
            List<ISite> existsSites = checkExistingInBase(graph, sites);
            List<WeightedPair> pairs = new ArrayList<>();

            int length = existsSites.size();
            logger.debug("Existing sites count: " + length);

            for (int i = 0; i < length - 1; i++) {
                for (int j = i + 1; j < length; j++) {
                    WeightedPair pair = new WeightedPair(existsSites.get(i), existsSites.get(j));
                    logger.debug("Check pair: " + pair);

                    Vertex first = findVertex(graph, pair.getFirstHost().getHost());
                    Vertex second = findVertex(graph, pair.getSecondHost().getHost());
                    logger.debug("Found vertices: " + first + ", " + second);

                    if (first.equals(second)) {
                        logger.debug("Vertices are equal");
                        pair.setWeight(0);
                        continue;
                    }

                    double weight = findPath(graph, first, second);

                    logger.debug("Weight of pair " + pair + " is " + weight);
                    if (weight > ZERO_THRESHOLD) {
                        pair.setWeight(weight);
                        pairs.add(pair);
                    }
                }
            }

            return pairs;
        } catch (Exception e) {
            graph.rollback();
            throw new ClassifierRuntimeException("Cannot check mirrors", e);
        } finally {
            logger.debug("Transaction completed");
            graph.shutdown();
        }
    }

    private List<ISite> checkExistingInBase(OrientGraph graph, List<ISite> sites) {
        List<ISite> existsSites = new ArrayList<>();

        for (ISite site : sites) {
            Vertex v = findVertex(graph, site.getHost());
            if (v != null) {
                existsSites.add(site);
            }
        }

        return existsSites;
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
                return -1;
            }
            if (first.equals(second)) {
                return 1;
            }

            return findPath(graph, first, second);
        } catch (Exception e) {
            graph.rollback();
            throw new ClassifierRuntimeException("Cannot check mirrors", e);
        } finally {
            logger.debug("Transaction completed");
            graph.shutdown();
        }
    }

    private double findPath(OrientGraph graph, Vertex first, Vertex second) {
        String query = String.format("select shortestPath(%s, %s, 'BOTH')", first.getId(), second.getId());
        List<ODocument> documents = graph.getRawGraph().query(new OSQLSynchQuery(query));
        List<ODocument> path = documents.get(0).field(SHORTEST_PATH);

        return checkPath(first, second, path);
    }

    private double checkPath(Vertex first, Vertex second, List<ODocument> path) {
        double weight;
        int last = path.size() - 1;
        if ((path.get(last).equals(first) && path.get(0).equals(second)) ||
                (path.get(0).equals(first) && path.get(last).equals(second))) {
            weight = 1.0;
        } else {
            weight = 0.0;
        }
        return weight;
    }
}
