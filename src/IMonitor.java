import java.io.IOException;

public interface IMonitor {
    SensorData getSensorData(String ghId) throws IOException;

    class SensorData
    {
        public String ghId;
        public String token;
        public double temperature_act;
        public double humidity_act;
        public boolean boiler_on;
        public boolean sprinkler_on;

        public String getGhId() {
            return ghId;
        }

        public void setGhId(String ghId) {
            this.ghId = ghId;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public double getTemperature_act() {
            return temperature_act;
        }

        public void setTemperature_act(double temperature_act) {
            this.temperature_act = temperature_act;
        }

        public double getHumidity_act() {
            return humidity_act;
        }

        public void setHumidity_act(double humidity_act) {
            this.humidity_act = humidity_act;
        }

        public boolean isBoiler_on() {
            return boiler_on;
        }

        public void setBoiler_on(boolean boiler_on) {
            this.boiler_on = boiler_on;
        }

        public boolean isSprinkler_on() {
            return sprinkler_on;
        }

        public void setSprinkler_on(boolean sprinkler_on) {
            this.sprinkler_on = sprinkler_on;
        }

        public SensorData(String ghId, String token, double temperature_act, double humidity_act, boolean boiler_on, boolean sprinkler_on) {
            this.ghId = ghId;
            this.token = token;
            this.temperature_act = temperature_act;
            this.humidity_act = humidity_act;
            this.boiler_on = boiler_on;
            this.sprinkler_on = sprinkler_on;
        }
    }
}
