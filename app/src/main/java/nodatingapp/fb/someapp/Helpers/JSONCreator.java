package nodatingapp.fb.someapp.Helpers;

import org.json.JSONException;
import org.json.JSONObject;

public class JSONCreator {

    private JSONObject jsonObject;

    public JSONCreator()
    {
        this.jsonObject = new JSONObject();
    }

    public void addField(String key, Object value) {
        try {
            this.jsonObject.put(key, value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public JSONObject getFinalObject() { return this.jsonObject; }
}
