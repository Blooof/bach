package client;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.log4j.Logger;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import ru.ifmo.ctddev.larionov.bach.common.site.ISite;
import ru.ifmo.ctddev.larionov.bach.common.site.WeightedPair;
import ru.ifmo.ctddev.larionov.bach.exception.ClassifierRuntimeException;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import java.util.Arrays;
import java.util.List;

/**
 * User: Oleg Larionov
 * Date: 03.06.13
 * Time: 14:39
 */
public class ComparatorClient implements IComparatorClient {

    private static final Logger logger = Logger.getLogger(ComparatorClient.class);
    private String base;
    private JacksonJsonProvider provider;

    @Value("${comparator.base}")
    public void setBase(String base) {
        this.base = base;
    }

    @Autowired
    public void setProvider(JacksonJsonProvider provider) {
        this.provider = provider;
    }

    @Override
    public List<WeightedPair> compare(List<ISite> sites) {
        WebClient client = getClient()
                .path("compare")
                .type(MediaType.APPLICATION_JSON_TYPE)
                .accept(MediaType.APPLICATION_JSON_TYPE);
        try {
            WeightedPair[] result = client.post(sites, WeightedPair[].class);
            return Arrays.asList(result);
        } catch (WebApplicationException e) {
            logger.warn("Cannot compare sites", e);
            throw new ClassifierRuntimeException("Cannot compare", e);
        }
    }

    private WebClient getClient() {
        String path = String.format("http://%s", base);
        return WebClient.create(path, Arrays.asList(provider));
    }
}
