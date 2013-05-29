package ru.ifmo.ctddev.larionov.bach.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ifmo.ctddev.larionov.bach.common.Pair;
import ru.ifmo.ctddev.larionov.bach.common.site.ISite;
import ru.ifmo.ctddev.larionov.bach.common.site.Site;
import ru.ifmo.ctddev.larionov.bach.common.site.WeightedPair;
import ru.ifmo.ctddev.larionov.bach.database.IMirrorsBase;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * User: Oleg Larionov
 * Date: 30.05.13
 * Time: 0:11
 */
@Service("mirrorsBaseEndpoint")
@Path("/mirrorsBase")
public class MirrorsBaseEndpoint {

    private IMirrorsBase mirrorsBase;

    @Autowired
    public void setMirrorsBase(IMirrorsBase mirrorsBase) {
        this.mirrorsBase = mirrorsBase;
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response setMirrors(WeightedPair pair) {
        Response response;
        if (validateWeightedPair(pair)) {
            mirrorsBase.addMirrors(pair);
            response = Response.ok().build();
        } else {
            response = Response.status(Response.Status.BAD_REQUEST).build();
        }
        return response;
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response checkMirrors(Pair<String, String> hosts) {
        Response response;
        if (validatePair(hosts)) {
            double weight = mirrorsBase.checkMirrors(hosts.getFirst(), hosts.getSecond());
            ISite first = new Site(hosts.getFirst(), null);
            ISite second = new Site(hosts.getSecond(), null);
            WeightedPair pair = new WeightedPair(first, second, weight);
            response = Response.ok(pair).build();
        } else {
            response = Response.status(Response.Status.BAD_REQUEST).build();
        }
        return response;
    }

    private boolean validatePair(Pair<String, String> hosts) {
        if (hosts == null) {
            return false;
        }

        if (hosts.getFirst() == null || hosts.getSecond() == null) {
            return false;
        }

        return true;
    }

    private boolean validateWeightedPair(WeightedPair pair) {
        if (pair == null) {
            return false;
        }

        if (pair.getFirstHost() == null || pair.getSecondHost() == null) {
            return false;
        }

        if (pair.getFirstHost().getHost() == null || pair.getSecondHost().getHost() == null) {
            return false;
        }

        if (pair.getWeight() <= 0 || pair.getWeight() > 1) {
            return false;
        }

        return true;
    }
}
