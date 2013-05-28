package ru.ifmo.ctddev.larionov.bach.endpoint.provider;

import javax.ws.rs.Consumes;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.Provider;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * User: Oleg Larionov
 * Date: 28.05.13
 * Time: 18:30
 */
@Consumes(MediaType.TEXT_PLAIN)
@Provider
public class ListReader implements MessageBodyReader<List> {
    @Override
    public boolean isReadable(Class<?> aClass, Type type, Annotation[] annotations, MediaType mediaType) {
        return mediaType.equals(MediaType.TEXT_PLAIN_TYPE) && aClass.equals(List.class);
    }

    @Override
    public List readFrom(Class<List> listClass, Type type, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, String> stringStringMultivaluedMap, InputStream inputStream) throws IOException, WebApplicationException {
        List<String> list = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String current;
            while ((current = reader.readLine()) != null) {
                list.add(current);
            }
        }

        return list;
    }
}
