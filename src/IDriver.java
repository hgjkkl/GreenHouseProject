import java.io.IOException;
import java.net.MalformedURLException;

public interface IDriver {
    int sendCommand(ILoader.Greenhouse gh, String token, double boilerValue, double sprinklerValue) throws IOException;

}
