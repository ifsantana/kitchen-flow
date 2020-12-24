package br.com.italo.santana.challenge.prompt.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.Reader;
import java.lang.reflect.Type;

/**
 * Only to encapsulate the default Json parser.
 *
 */
public class JsonParserUtil {

    /**
     * Default parser
     */
    private static final Gson gson = new Gson();

    /**
     * Converts and {@link Reader} in a {@link java.util.List} of objects according the defined type {@link Type}.
     * @param reader
     * @param typeToken
     * @param <T>
     * @return List<Object>
     */
    public static <T> T fromReaderToEntityList(Reader reader, Type typeToken) {
        return new Gson().fromJson(reader, typeToken);
    }

    public static String toJson(Object obj) {
        return gson.toJson(obj);
    }

    public static  String prettyJson(String uglyJSONString) {
        Gson gson = new GsonBuilder().create();
        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(uglyJSONString);
        return gson.toJson(je);
    }
}
