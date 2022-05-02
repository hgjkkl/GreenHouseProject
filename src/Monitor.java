import org.json.JSONObject;

import java.io.IOException;

public class Monitor implements  IMonitor{
    public SensorData getSensorData(String extension) throws IOException {
        JSONObject json = JSONParser.readJsonFromUrl("http://193.6.19.58:8181/greenhouse/" + extension);
            String ghId = json.getString("ghId");
            String token = json.getString("token");
            double temperature_act = json.getInt("temperature_act");
            double humidity_act = json.getInt("humidity_act");
            boolean boiler_on = json.getBoolean("boiler_on");
            boolean sprinkler_on = json.getBoolean("sprinkler_on");
            System.out.println(ghId + " " + token + " "  + temperature_act + " "  + humidity_act + " "  + boiler_on + " "  + sprinkler_on);
            SensorData current = new SensorData(ghId,token,temperature_act,humidity_act,boiler_on,sprinkler_on);
        return current;
    }
}
