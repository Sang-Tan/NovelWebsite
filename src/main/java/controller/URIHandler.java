package controller;

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

    /**
     *
     * @param pathComponent path component of the URI exp : 1-ten-truyen
     * @return id of the path component, -1 if no id is found
     */
    public static int getIdFromPathComponent(String pathComponent)
    {
        int numStartIndex = -1;
        int numEndIndex = -1;
        for (int i = 0; i < pathComponent.length(); i++) {
            if (Character.isDigit(pathComponent.charAt(i))) {
                if (numStartIndex == -1) {
                    numStartIndex = i;
                }
                numEndIndex = i;
            } else if (numStartIndex != -1) {
                break;
            }
        }
        try {
            int id = Integer.parseInt(pathComponent.substring(numStartIndex, numEndIndex + 1));
            return id;
        }
        catch (NumberFormatException e) {
            return -1;
        }
    }


}
