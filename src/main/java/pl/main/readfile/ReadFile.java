package pl.main.readfile;

import com.google.gson.Gson;
import org.aeonbits.owner.ConfigFactory;
import pl.main.pojo.*;
import pl.main.properties.EnvironmentConfig;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ReadFile {

    public static Order getOrderData() {
        Order order;
        try {
            Gson gson = new Gson();
            EnvironmentConfig environmentConfig = ConfigFactory.create(EnvironmentConfig.class);
            Reader reader = Files.newBufferedReader(Paths.get(environmentConfig.getOrderDataPath()));
            order = gson.fromJson(reader, Order.class);
        } catch (Exception e) {
            e.printStackTrace();
            order = null;
        }
        return order;
    }

    public static User getUserData() {
        User user;
        try {
            Gson gson = new Gson();
            EnvironmentConfig environmentConfig = ConfigFactory.create(EnvironmentConfig.class);
            Reader reader = Files.newBufferedReader(Paths.get(environmentConfig.getUserDataPath()));
            user = gson.fromJson(reader, User.class);
        } catch (Exception e) {
            e.printStackTrace();
            user = null;
        }
        return user;
    }

}
