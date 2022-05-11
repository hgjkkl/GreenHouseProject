import java.io.*;
import java.net.*;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static java.lang.Integer.parseInt;

public class Driver implements  IDriver {
    public int sendCommand(ILoader.Greenhouse gh, String token, double boilerValue, double sprinklerValue) throws IOException {

        URL url;
        String controlToken;
        url = new URL("http://193.6.19.58:8181/greenhouse/" + token);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "text/plain");
        con.setRequestProperty("charset", "utf-8");
        con.setDoOutput(true);
        IMonitor.SensorData currentSensorData = new Monitor().getSensorData(gh.getGhId());
        Controller currentCommand = new Controller();
        String bC="";
        try {
            bC = currentCommand.kazan(currentSensorData.getTemperature_act(), currentSensorData.getHumidity_act(), gh.getTemperature_min(), gh.getHumidity_min(), gh.getTemperature_opt(), currentSensorData.isBoiler_on(), currentSensorData.isSprinkler_on());
            System.out.println(bC);

        }
        catch (Controller.TooHighHumidityDiff e){
            FileWriter myWriter = new FileWriter("error_log.txt");
            myWriter.write(String.valueOf(LocalDateTime.now()) + " " + String.valueOf(LocalTime.now()) + " Too high difference between optimal and actual temperature:" + String.valueOf(e.difference));
            myWriter.close();
        }
        catch (Controller.TooHighTempDiff e){
            FileWriter myWriter = new FileWriter("error_log.txt");
            myWriter.write(String.valueOf(LocalDateTime.now()) + " " + String.valueOf(LocalTime.now()) + " Too high difference between optimal and actual humidity:" + String.valueOf(e.difference));
            myWriter.close();
        }
        String sC = currentCommand.locsolo(Double.valueOf(gh.getVolume()),currentSensorData.getTemperature_act(),currentSensorData.getHumidity_act(),gh.getHumidity_min(),gh.getTemperature_opt());
        System.out.println(sC);
        String jsonInputString = "{\"ghId\":\""+gh.getGhId()+"\",\"boilerCommand\":\""+bC+"\",\"sprinklerCommand\":\""+sC+"\"}";
        System.out.println(jsonInputString);

        try(OutputStream os = con.getOutputStream()) {
            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        try(BufferedReader br = new BufferedReader(
                new InputStreamReader(con.getInputStream(), "utf-8"))) {
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            return parseInt(response.toString());
        }
    }
}

