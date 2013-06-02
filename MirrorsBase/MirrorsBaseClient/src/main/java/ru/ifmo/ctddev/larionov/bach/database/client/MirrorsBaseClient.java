package ru.ifmo.ctddev.larionov.bach.database.client;

import org.apache.cxf.jaxrs.client.WebClient;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import ru.ifmo.ctddev.larionov.bach.common.Pair;
import ru.ifmo.ctddev.larionov.bach.common.site.ISite;
import ru.ifmo.ctddev.larionov.bach.common.site.WeightedPair;
import ru.ifmo.ctddev.larionov.bach.exception.ClassifierRuntimeException;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * User: Oleg Larionov
 * Date: 02.06.13
 * Time: 14:15
 */
public class MirrorsBaseClient implements IMirrorsBaseClient {

    private String base;
    private JacksonJsonProvider provider;

    @Value("${mirrors.base}")
    public void setBase(String base) {
        this.base = base;
    }

    @Autowired
    public void setProvider(JacksonJsonProvider provider) {
        this.provider = provider;
    }

    @Override
    public boolean addMirrors(WeightedPair pair) {
        WebClient client = getClient()
                .path("pair")
                .type(MediaType.APPLICATION_JSON_TYPE)
                .accept(MediaType.WILDCARD_TYPE);

        try {
            Response r = client.put(pair);
            return Response.Status.OK.getStatusCode() == r.getStatus();
        } catch (WebApplicationException e) {
            return false;
        }
    }

    @Override
    public double checkMirrors(Pair<String, String> hosts) {
        WebClient client = getClient()
                .path("pair")
                .type(MediaType.APPLICATION_JSON_TYPE)
                .accept(MediaType.APPLICATION_JSON_TYPE);
        try {
            WeightedPair result = client.post(hosts, WeightedPair.class);
            return result.getWeight();
        } catch (WebApplicationException e) {
            throw new ClassifierRuntimeException("Cannot check mirrors", e);
        }
    }

    @Override
    public List<WeightedPair> checkMirrors(List<ISite> sites) {
        List<String> hosts = new ArrayList<>();
        for (ISite site : sites) {
            hosts.add(site.getHost());
        }

        WebClient client = getClient()
                .path("list")
                .type(MediaType.APPLICATION_JSON_TYPE)
                .accept(MediaType.APPLICATION_JSON_TYPE);
        try {
            WeightedPair[] result = client.post(hosts, WeightedPair[].class);
            return Arrays.asList(result);
        } catch (WebApplicationException e) {
            throw new ClassifierRuntimeException("Cannot check mirrors", e);
        }
    }

    private WebClient getClient() {
        String path = String.format("http://%s", base);
        return WebClient.create(path, Arrays.asList(provider));
    }
}
