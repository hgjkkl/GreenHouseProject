import java.io.IOException;
import java.net.MalformedURLException;

public interface IDriver {
    int sendCommand(ILoader.Greenhouse gh, String token, double boilerValue, double sprinklerValue) throws IOException;

    class Command
    {
        public String ghId;
        public String boilerCommand;
        public String sprinklerCommand;
    }
}
