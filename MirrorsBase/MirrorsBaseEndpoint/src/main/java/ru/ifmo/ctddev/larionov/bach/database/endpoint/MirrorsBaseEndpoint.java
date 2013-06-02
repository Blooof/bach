package ru.ifmo.ctddev.larionov.bach.database.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ifmo.ctddev.larionov.bach.common.Pair;
import ru.ifmo.ctddev.larionov.bach.common.site.ISite;
import ru.ifmo.ctddev.larionov.bach.common.site.Site;
import ru.ifmo.ctddev.larionov.bach.common.site.WeightedPair;
import ru.ifmo.ctddev.larionov.bach.database.IMirrorsBase;
import ru.ifmo.ctddev.larionov.bach.exception.ClassifierRuntimeException;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * User: Oleg Larionov
 * Date: 30.05.13
 * Time: 0:11
 */
@Service("mirrorsBaseEndpoint")
public class MirrorsBaseEndpoint implements IMirrorsBaseEndpoint {

    private IMirrorsBase mirrorsBase;

    @Autowired
    public void setMirrorsBase(IMirrorsBase mirrorsBase) {
        this.mirrorsBase = mirrorsBase;
    }

    @Override
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

    @Override
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

    @Override
    public Response checkMirrors(List<String> hosts) {
        Response response;

        if (validateList(hosts)) {
            Set<ISite> sites = new HashSet<>();
            for (String host : hosts) {
                sites.add(new Site(host));
            }
            List<ISite> list = new ArrayList<>(sites);

            try {
                List<WeightedPair> pairs = mirrorsBase.checkMirrors(list);
                response = Response.ok(pairs).build();
            } catch (ClassifierRuntimeException e) {
                response = Response.status(Response.Status.BAD_REQUEST).entity(e.toString()).build();
            }
        } else {
            response = Response.status(Response.Status.BAD_REQUEST).entity("List is not valid").build();
        }

        return response;
    }

    private boolean validateList(List<String> sites) {
        if (sites == null) {
            return false;
        }

        return true;
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
