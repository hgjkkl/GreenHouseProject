import org.json.JSONException;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, JSONException {
        Loader init = new Loader();
        ILoader.GreenHouseList greenhouses = init.loadGreenHouses();
    }
}
