import org.json.JSONException;
import java.io.IOException;

import static java.lang.String.valueOf;

public class Main {
    public static void main(String[] args) throws IOException, JSONException {
        System.out.println("TEST");
        ILoader.GreenHouseList greenhouses = new Loader().loadGreenHouses();
        IMonitor.SensorData currentSensorData = new Monitor().getSensorData("KFI3EW45RD");
        int errorMsg = new Driver().sendCommand(greenhouses.getGreenhouses().get(0), currentSensorData.getToken(), currentSensorData.getTemperature_act(), currentSensorData.getHumidity_act());
        System.out.println(valueOf(errorMsg));
    }
}
