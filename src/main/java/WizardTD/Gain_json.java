package WizardTD;
import java.util.*;
import processing.data.JSONObject;
import processing.data.JSONArray;
import processing.core.PApplet;

public class Gain_json{

    String path;
    JSONObject json_object;
    PApplet parent;
    JSONObject json;
    JSONObject jsonObject;
    JSONObject waveObject;
    JSONArray waveArray;
    JSONObject monsterObject;
    JSONArray monsterArray;

    Gain_json(PApplet parent, String path) {
        this.path = path;
        this.parent = parent;
    }

    // Return the String data from the json file
    Object gain_method(String json_key) {

        json = parent.loadJSONObject(path);
        Object data = json.get(json_key);

        return data;
    }

    ArrayList<Object> gain_waves_method(ArrayList<String> json_key) {

        // create the arraylist of data gained
        ArrayList<Object> gain_array = new ArrayList<Object>();

        // gain the json object from the main function
        jsonObject = parent.loadJSONObject(path);
        waveArray = jsonObject.getJSONArray(json_key.get(0));
        
        if (json_key.size() == 2) {
            for (int i = 0; i < waveArray.size(); i++) {
                waveObject = waveArray.getJSONObject(i);
                gain_array.add(waveObject.get(json_key.get(1)));
            }
        } else if (json_key.size() == 3) {
            for (int i = 0; i < waveArray.size(); i++) {
                waveObject = waveArray.getJSONObject(i);
                monsterArray = waveObject.getJSONArray(json_key.get(1));
                for (int j = 0; j < monsterArray.size(); j++) {
                    monsterObject = monsterArray.getJSONObject(j);
                    gain_array.add(monsterObject.get(json_key.get(2)));
                }
            }
        }
        return gain_array;
    }
}
