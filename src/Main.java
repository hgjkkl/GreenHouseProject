import org.json.JSONException;
import java.io.IOException;

import static java.lang.String.valueOf;

public class Main {
    public static void main(String[] args) throws IOException, JSONException {
        System.out.println("TEST");
        ILoader.GreenHouseList greenhouses = new Loader().loadGreenHouses();
        for (ILoader.Greenhouse i : greenhouses.getGreenhouses()) {
            IMonitor.SensorData currentSensorData = new Monitor().getSensorData(i.getGhId());
            int errorMsg = new Driver().sendCommand(i, currentSensorData.getToken(), currentSensorData.getTemperature_act(), currentSensorData.getHumidity_act());
            System.out.println(valueOf(errorMsg));
        }
    }
}
