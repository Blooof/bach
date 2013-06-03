package ru.ifmo.ctddev.larionov.bach.comparator.endpoint;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ifmo.ctddev.larionov.bach.common.site.ISite;
import ru.ifmo.ctddev.larionov.bach.common.site.WeightedPair;
import ru.ifmo.ctddev.larionov.bach.comparator.IComparatorService;

import javax.ws.rs.core.Response;
import java.util.List;

/**
 * User: Oleg Larionov
 * Date: 03.06.13
 * Time: 14:17
 */
@Service("comparatorEndpoint")
public class ComparatorEndpoint implements IComparatorEndpoint {

    private static final Logger logger = Logger.getLogger(ComparatorEndpoint.class);
    private IComparatorService comparatorService;

    @Autowired
    public void setComparatorService(IComparatorService comparatorService) {
        this.comparatorService = comparatorService;
    }

    @Override
    public Response compare(List<ISite> sites) {
        logger.debug("Endpoint compare request. Sites count: " + sites.size());
        Response response;

        if (validateSites(sites)) {
            try {
                List<WeightedPair> result = comparatorService.compare(sites);
                response = Response.ok(result).build();
            } catch (Exception e) {
                response = Response.status(Response.Status.BAD_REQUEST)
                        .entity("Cannot complete request: " + e.toString()).build();
            }
        } else {
            response = Response.status(Response.Status.BAD_REQUEST)
                    .entity("Input entity is not valid").build();
        }

        return response;
    }

    private boolean validateSites(List<ISite> sites) {
        if (sites == null) {
            return false;
        }

        for (ISite site : sites) {
            if (site.getHost() == null || site.getLinks() == null) {
                return false;
            }
        }

        return true;
    }


}
