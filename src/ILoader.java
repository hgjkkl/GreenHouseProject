import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public interface ILoader {
    GreenHouseList loadGreenHouses() throws IOException;
    class Greenhouse
    {
        public Greenhouse(String ghId, String description, int temperature_min, int temperature_opt, int humidity_min, int volume) {
            this.ghId = ghId;
            this.description = description;
            this.temperature_min = temperature_min;
            this.temperature_opt = temperature_opt;
            this.humidity_min = humidity_min;
            this.volume = volume;
        }
        public String getGhId() {
            return ghId;
        }
        public void setGhId(String ghId) {
            this.ghId = ghId;
        }
        public String getDescription() {
            return description;
        }
        public void setDescription(String description) {
            this.description = description;
        }
        public int getTemperature_min() {
            return temperature_min;
        }
        public void setTemperature_min(int temperature_min) {
            this.temperature_min = temperature_min;
        }
        public int getTemperature_opt() {
            return temperature_opt;
        }
        public void setTemperature_opt(int temperature_opt) {
            this.temperature_opt = temperature_opt;
        }
        public int getHumidity_min() {
            return humidity_min;
        }
        public void setHumidity_min(int humidity_min) {
            this.humidity_min = humidity_min;
        }
        public int getVolume() {
            return volume;
        }
        public void setVolume(int volume) {
            this.volume = volume;
        }

        public String ghId;
        public String description;
        public int temperature_min;
        public int temperature_opt;
        public int humidity_min;
        public int volume;
    }

    class GreenHouseList
    {
       private ArrayList<Greenhouse> greenhouses;

        public ArrayList<Greenhouse> getGreenhouses() {
            return greenhouses;
        }

        public void setGreenhouses(ArrayList<Greenhouse> greenhouses) {
            this.greenhouses = greenhouses;
        }

        public Greenhouse find(String ghId){
            for (int i = 0; i < greenhouses.size(); i++) {
                if (Objects.equals(greenhouses.get(i).getGhId(), ghId)) {
                    return greenhouses.get(i);
                }
            }
            return null;
        }
    }
}
