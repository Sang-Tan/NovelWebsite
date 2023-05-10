
package core;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

public class JSON {
    /**
     * Get response json
     *
     * @param status status want to response
     * @param errors errors want to response
     * @return
     * @throws JSONException if the value cannot be converted to JSON
     */
    public static JSONObject getResponseJson(String status, HashMap<String, String> errors) throws JSONException {
        JSONObject responseJson = new JSONObject();
        if (!errors.isEmpty()) {
            responseJson.put("status", status);
            responseJson.put("errors", errors);
            return responseJson;
        } else {
            responseJson.put("status", status);
            return responseJson;
        }
    }


    public static JSONObject getResponseJson(String status) throws IOException, JSONException {
        JSONObject responseJson = new JSONObject();
        responseJson.put("status", status);
        return responseJson;

    }
}