package core;

import io.github.cdimascio.dotenv.Dotenv;
import repository.UserRepository;

import java.util.HashMap;
import java.util.regex.Pattern;

public class URIHandler {
    private HashMap<String, String> uriParams;

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

                if(templateParts[i].length() < 3){
                    throw  new IllegalArgumentException();
                }

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