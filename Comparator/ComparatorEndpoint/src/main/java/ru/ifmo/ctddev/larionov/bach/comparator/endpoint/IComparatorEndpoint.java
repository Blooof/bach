package ru.ifmo.ctddev.larionov.bach.comparator.endpoint;

import ru.ifmo.ctddev.larionov.bach.common.site.ISite;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * User: Oleg Larionov
 * Date: 03.06.13
 * Time: 14:12
 */
public interface IComparatorEndpoint {

    @POST
    @Path("/compare")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response compare(List<ISite> sites);
}
