import static java.lang.String.valueOf;
import static java.lang.Double.valueOf;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;

public class Controller {
    HashMap<Double, Double> tem_hum = new HashMap<Double, Double>();

    String kazan(double temperature_act, double humidity_act, double temperature_min, double humidity_min, double temperature_opt, boolean boiler_on, boolean sprinkler_on) {
        if ((temperature_opt - temperature_act) >= 5) {
            FileWriter myWriter = null;
            try {
                myWriter = new FileWriter("error_log.txt", true);
                BufferedWriter bw = new BufferedWriter(myWriter);
                bw.write(String.valueOf(LocalDateTime.now()) + " " + String.valueOf(LocalTime.now()) + " Too high difference between optimal and actual temperature: " + String.valueOf((temperature_opt - temperature_act)));
                bw.newLine();
                bw.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        if ((humidity_min - humidity_act) >= 20) {
            FileWriter myWriter = null;
            try {
                myWriter = new FileWriter("error_log.txt", true);
                BufferedWriter bw = new BufferedWriter(myWriter);
                bw.write(String.valueOf(LocalDateTime.now()) + " " + String.valueOf(LocalTime.now()) + " Too high difference between optimal and actual humidity: " + String.valueOf((humidity_min - humidity_act)));
                bw.newLine();
                bw.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        tem_hum.put(Double.valueOf(20), 17.3);
        tem_hum.put(Double.valueOf(21), 18.5);
        tem_hum.put(Double.valueOf(22), 19.7);
        tem_hum.put(Double.valueOf(23), 20.9);
        tem_hum.put(Double.valueOf(24), 22.1);
        tem_hum.put(Double.valueOf(25), 23.3);
        tem_hum.put(Double.valueOf(26), 24.7);
        tem_hum.put(Double.valueOf(27), 26.1);
        tem_hum.put(Double.valueOf(28), 27.5);
        tem_hum.put(Double.valueOf(29), 28.9);
        tem_hum.put(Double.valueOf(30), 30.3);
        tem_hum.put(Double.valueOf(31), 31.9);
        tem_hum.put(Double.valueOf(32), 33.5);
        tem_hum.put(Double.valueOf(33), 35.1);
        tem_hum.put(Double.valueOf(34), 36.7);
        tem_hum.put(Double.valueOf(35), 38.3);
        System.out.println(temperature_act + "act?" + temperature_min +"min:" + temperature_opt + "opt," + humidity_act + "act?" + humidity_min+"min");
        if (temperature_act >= temperature_min) {
            return "bup" + String.valueOf(0) + "c";
        } else {
            return "bup" + String.valueOf((int) (temperature_opt - temperature_act)) + "c";
        }
    }

    String locsolo(double greenhouse_size, double temperature_act, double humidity_act, double humidity_min, double temperature_opt, double temperature_min) {
        double act_water = 0;
        double needed_water = 0;
        double diff;
        double fut_hum;
        double fut_needed_water = 0;
        if (temperature_act < temperature_min)//temperature_act<temperature_min
        {
            for (Double i : tem_hum.keySet()) {
                if (i == temperature_act) {
                    act_water = tem_hum.get(i);
                    act_water *= (humidity_act / 100);
                    System.out.println("act_water:"+act_water);
                    break;
                }
            }
            for (Double i : tem_hum.keySet()) {
                if (i == temperature_opt) {
                    needed_water = tem_hum.get(i);
                    System.out.println("needed_water:"+needed_water);
                    break;
                }
            }
            fut_hum = act_water / needed_water;
            System.out.println("fut_hum"+fut_hum);
            if( (fut_hum*100) < humidity_min)
            {
                fut_needed_water = ( ( ( ( (needed_water*(humidity_min/100)/*18,18*/ )-act_water/*1,87*/) /0.01/*187*/) )*greenhouse_size/*224400*/)/1000;//224,4
            }
            return "son" + String.valueOf((int)fut_needed_water) + "l";
        } else {
            if (humidity_act >= humidity_min) //humidity_act>=humidity_min
            {
                return "son" + String.valueOf(0) + "l";
            }
            else {
                for (Double i : tem_hum.keySet()) {
                    if (i == temperature_act) {
                        needed_water = tem_hum.get(i);
                        needed_water *= (humidity_min / 100);
                        break;
                    }
                }
                for (Double i : tem_hum.keySet()) {
                    if (i == temperature_act) {
                        act_water = tem_hum.get(i);
                        act_water *= (humidity_act / 100);
                        break;
                    }
                }
                diff = (((needed_water - act_water) / 0.01) * greenhouse_size) / 1000;
                return "son" + String.valueOf((int) diff) + "l";
            }//inner else
        }//outer else
    }
}//class
