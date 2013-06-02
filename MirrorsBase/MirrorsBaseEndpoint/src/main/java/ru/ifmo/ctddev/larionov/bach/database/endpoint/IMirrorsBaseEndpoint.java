package ru.ifmo.ctddev.larionov.bach.database.endpoint;

import ru.ifmo.ctddev.larionov.bach.common.Pair;
import ru.ifmo.ctddev.larionov.bach.common.site.WeightedPair;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * User: Oleg Larionov
 * Date: 02.06.13
 * Time: 14:15
 */
public interface IMirrorsBaseEndpoint {

    @PUT
    @Path("/pair")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response setMirrors(WeightedPair pair);

    @POST
    @Path("/pair")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response checkMirrors(Pair<String, String> hosts);

    @POST
    @Path("/list")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response checkMirrors(List<String> sites);
}
