package ru.ifmo.ctddev.larionov.bach.json;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

/**
 * User: Oleg Larionov
 * Date: 23.05.13
 * Time: 18:23
 */
public class JsonExtractor {
    public static void extract(File from, File to) {
        Set<String> urls = new HashSet<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(from));
             PrintWriter writer = new PrintWriter(to)) {
            ObjectMapper mapper = new ObjectMapper();
            String json;
            while ((json = reader.readLine()) != null) {
                JsonNode root = mapper.readValue(json, JsonNode.class);
                String url = root.get("url").getTextValue();

                if (!urls.contains(url)) {
                    urls.add(url);
                    writer.println(url);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Cannot open file", e);
        }
    }
}
