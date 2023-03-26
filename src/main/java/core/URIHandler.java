package core;

import io.github.cdimascio.dotenv.Dotenv;
import repository.UserRepository;

import java.util.HashMap;
import java.util.regex.Pattern;

public class URIHandler {
    private static URIHandler instance;
    private HashMap<String, String> uriParams;
    public static URIHandler getInstance(String uri, String template) {
        if (instance == null) {
            instance = new URIHandler(uri, template);
        }
        return instance;
    }

    public URIHandler(String uri, String template) {
        uriParams = parseURI(uri, template);
    }

    public HashMap<String, String> parseURI(String uri, String template) {
        String[] uriParts = uri.split("/");
        String[] templateParts = template.split("/");
        HashMap<String,String> params = new HashMap<String, String>();

        if(uriParts.length != templateParts.length) {
            throw  new IllegalArgumentException();
        }

        for (int i =0; i < uriParts.length; i++){
            if(templateParts[i].startsWith("{") && templateParts[i].endsWith("}")) {
                String key = templateParts[i].substring(1, templateParts[i].length() - 2);
                String value = uriParts[i];
                params.put(key, value);
            }
        }
        return params;
    }
    public String getParam(String key) {
        return uriParams.get(key);
    }


}
