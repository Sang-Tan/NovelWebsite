package core.metadata;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public interface JSONSerializable extends Serializable {
    JSONObject toJSON() throws JSONException;
}
