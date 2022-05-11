import static java.lang.String.valueOf;
import static java.lang.Double.valueOf;
import java.util.HashMap;

public class Controller {
    boolean kell=false;
    HashMap<Double, Double> tem_hum = new HashMap<Double, Double>();

    public class TooHighTempDiff extends Exception {
        public double difference;
        public TooHighTempDiff(double difference) {
            super("Too high difference between optimal and actual temperature: "+String.valueOf(difference));
            this.difference=difference;
        }
    }
    public class TooHighHumidityDiff extends Exception {
        public double difference;
        public TooHighHumidityDiff(double difference) {
            super("Too high difference between optimal and actual Humidity: "+String.valueOf(difference));
            this.difference=difference;
        }
    }

    String kazan(double temperature_act, double humidity_act, double temperature_min, double humidity_min, double temperature_opt, boolean boiler_on, boolean sprinkler_on) throws TooHighTempDiff, TooHighHumidityDiff{
        if((temperature_opt-temperature_act)>=5)
            throw new TooHighTempDiff((temperature_opt-temperature_act));
        if((humidity_min-humidity_act)>=20)
            throw new TooHighHumidityDiff((humidity_min-humidity_act));
        tem_hum.put(Double.valueOf(20),17.3);
        tem_hum.put(Double.valueOf(21),18.5);
        tem_hum.put(Double.valueOf(22),19.7);
        tem_hum.put(Double.valueOf(23),20.9);
        tem_hum.put(Double.valueOf(24),22.1);
        tem_hum.put(Double.valueOf(25),23.3);
        tem_hum.put(Double.valueOf(26),24.7);
        tem_hum.put(Double.valueOf(27),26.1);
        tem_hum.put(Double.valueOf(28),27.5);
        tem_hum.put(Double.valueOf(29),28.9);
        tem_hum.put(Double.valueOf(30),30.3);
        tem_hum.put(Double.valueOf(31),31.9);
        tem_hum.put(Double.valueOf(32),33.5);
        tem_hum.put(Double.valueOf(33),35.1);
        tem_hum.put(Double.valueOf(34),36.7);
        tem_hum.put(Double.valueOf(35),38.3);
        if(!boiler_on && !sprinkler_on){
            if(temperature_act>temperature_min && humidity_act>humidity_min){
                return "";
            }else{
                kell=true;
                return "bup"+String.valueOf((int)(temperature_opt-temperature_act))+"c";
            }
        }else{
            return "";
        }
    }
    String locsolo(double greenhouse_size, double temperature_act, double humidity_act, double humidity_min, double temperature_opt){
        if(kell){
            double plus_water_necessary=0;
            double gm3_act = 0; //25 esetén 23,3 |25.0
            double gm3_opt = 0; //30 esetén 30,3 |30
            for (Double i : tem_hum.keySet()) {
                if(i==temperature_act)
                    gm3_act=tem_hum.get(i);
            }
            for (Double i : tem_hum.keySet()) {
                if(i==temperature_opt)
                    gm3_opt=tem_hum.get(i);
            }
            System.out.println(gm3_act);
            System.out.println(gm3_opt);
            double gm3_hum_act = gm3_act * (humidity_act/100);//23,3*0,7 = 16,31 |13.98
            System.out.println(gm3_hum_act);
            double fut_gm3_hum = gm3_hum_act / gm3_opt; //16,31/30,3 |0.46138613861
            System.out.println(fut_gm3_hum);
            if(humidity_min>fut_gm3_hum) //60>53,82
            {
                double fut_hum_min = gm3_opt * (humidity_min/100); // 18,18
                System.out.println(fut_hum_min);
                double plus_water = fut_hum_min - gm3_hum_act; // 1,87 |4.2
                if(plus_water < 0)
                    plus_water*=-1;
                System.out.println(plus_water);
                plus_water /= 0.01; //1%os párologtatás
                System.out.println(plus_water);
                plus_water *= greenhouse_size;
                plus_water /=1000;
                plus_water_necessary = plus_water;
            }
            return "son"+String.valueOf((int)plus_water_necessary)+"l";
        }else{
            return "";
        }
    }
}
