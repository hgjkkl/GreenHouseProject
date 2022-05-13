import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

import static java.lang.Integer.parseInt;

public class Driver implements  IDriver {

    public Driver() throws IOException {
    }

    public int sendCommand(ILoader.Greenhouse gh, String token, double boilerValue, double sprinklerValue) throws IOException {

        URL url;
        url = new URL("http://193.6.19.58:8181/greenhouse/" + token);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "text/plain");
        con.setRequestProperty("charset", "utf-8");
        con.setDoOutput(true);

        Controller currentCommand = new Controller();
        String bC = "";
        String sC = "";

            bC = currentCommand.kazan(boilerValue, sprinklerValue, gh.getTemperature_min(), gh.getHumidity_min(), gh.getTemperature_opt());
            sC = currentCommand.locsolo(Double.valueOf(gh.getVolume()), boilerValue, sprinklerValue, gh.getHumidity_min(), gh.getTemperature_opt(), gh.getTemperature_min());
            System.out.println(bC);
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
            return parseInt(response.toString()); //hibakod
        }
    }
//teszeléshez sajátkezűleg editelni
    public int sendEmptyCommand(ILoader.Greenhouse gh,String token) throws IOException {
        URL url;
        url = new URL("http://193.6.19.58:8181/greenhouse/" + token);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "text/plain");
        con.setRequestProperty("charset", "utf-8");
        con.setDoOutput(true);
        String jsonInputString = "{\"ghId\":\""+gh.getGhId()+"\",\"boilerCommand\":\""+""+"\",\"sprinklerCommand\":\""+""+"\"}";

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

