package ru.ifmo.ctddev.larionov.bach.endpoint;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ifmo.ctddev.larionov.bach.classifier.IClassifier;
import ru.ifmo.ctddev.larionov.bach.common.site.FileList;
import ru.ifmo.ctddev.larionov.bach.common.site.ISite;
import ru.ifmo.ctddev.larionov.bach.common.site.WeightedPair;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * User: Oleg Larionov
 * Date: 27.05.13
 * Time: 16:06
 */
@Service("classifierEndpoint")
@Path("/classifier")
public class ClassifierEndpoint {

    private static final Logger logger = Logger.getLogger(ClassifierEndpoint.class);
    private IClassifier classifier;

    @Autowired
    public void setClassifier(IClassifier classifier) {
        this.classifier = classifier;
    }

    @POST
    @Path("classify")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    public List<WeightedPair> classify(List<String> urls) {
        logger.info(String.format("New request: urls count: %d", urls.size()));
        Iterable<ISite> sites = new FileList(urls);
        return classifier.classify(sites);
    }
}
