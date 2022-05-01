import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class Loader implements  ILoader{

    private GreenHouseList currentGreenHouses;

    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }

    @Override
    public GreenHouseList loadGreenHouses() throws IOException {
        JSONObject json = readJsonFromUrl("http://193.6.19.58:8181/greenhouse");
        JSONArray ja = json.getJSONArray("greenhouseList");
        ArrayList<Greenhouse> toParse = new ArrayList<Greenhouse>();
        GreenHouseList toReturn = new GreenHouseList();

        for (int i = 0; i < ja.length(); i++) {
            JSONObject jsonobject = ja.getJSONObject(i);

            String ghId = jsonobject.getString("ghId");
            String description = jsonobject.getString("description");
            int temperature_min = jsonobject.getInt("temperature_min");
            int temperature_opt = jsonobject.getInt("temperature_opt");
            int humidity_min = jsonobject.getInt("humidity_min");
            int volume = jsonobject.getInt("volume");

            System.out.println(ghId + " " + description + " "  + temperature_min + " "  + temperature_opt + " "  + humidity_min + " "  + volume);
            Greenhouse current = new Greenhouse(ghId,description,temperature_min,temperature_opt,humidity_min,volume);
            toParse.add(current);
        }
        toReturn.setGreenhouses(toParse);
        return toReturn;
    }
    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }


}
