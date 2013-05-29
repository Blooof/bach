package ru.ifmo.ctddev.larionov.bach.endpoint.provider;

import ru.ifmo.ctddev.larionov.bach.common.site.FileList;
import ru.ifmo.ctddev.larionov.bach.common.site.ISite;

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
public class ListReader implements MessageBodyReader<Iterable<ISite>> {
    @Override
    public boolean isReadable(Class<?> aClass, Type type, Annotation[] annotations, MediaType mediaType) {
        return mediaType.equals(MediaType.TEXT_PLAIN_TYPE) && aClass.equals(Iterable.class);
    }

    @Override
    public Iterable<ISite> readFrom(Class<Iterable<ISite>> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, String> httpHeaders, InputStream entityStream) throws IOException, WebApplicationException {
        List<String> list = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(entityStream))) {
            String current;
            while ((current = reader.readLine()) != null) {
                list.add(current);
            }
        }

        return new FileList(list);
    }
}
