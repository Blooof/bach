package ru.ifmo.ctddev.larionov.bach.classifier.endpoint;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ifmo.ctddev.larionov.bach.classifier.IClassifier;
import ru.ifmo.ctddev.larionov.bach.common.site.ISite;
import ru.ifmo.ctddev.larionov.bach.common.site.WeightedPair;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * User: Oleg Larionov
 * Date: 27.05.13
 * Time: 16:06
 */
@Service("classifierEndpoint")
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
    public Response classify(List<ISite> urls) {
        logger.info(String.format("New request"));

        Response response;
        try {
            List<WeightedPair> result = classifier.classify(urls);
            response = Response.ok(result).build();
        } catch (Exception e) {
            logger.warn("Cannot complete request", e);
            response = Response.status(Response.Status.BAD_REQUEST).entity("Cannot complete request. Reason: " + e).build();
        }

        return response;
    }
}
