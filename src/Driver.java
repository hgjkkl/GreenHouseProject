import java.io.*;
import java.net.*;

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

        String jsonInputString = "{\"ghId\":\"KFI3EW45RD\",\"boilerCommand\":\"bup5c\",\"sprinklerCommand\":\"son224l\"}";


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

