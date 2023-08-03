package pl.main.properties;

import org.aeonbits.owner.Config;

@Config.Sources("classpath:EnvironmentConfig.properties")
public interface EnvironmentConfig extends Config{

    @Key("BASE_URI")
    String baseUri();

    @Key("BASE_PATH")
    String basePath();

    @Key("CREATE_USER_PATH")
    String createUserPath();

    @Key("UPDATE_USER_PATH")
    String updateUserPath();

    @Key("DELETE_USER_PATH")
    String deleteUserPath();

    @Key("CREATE_ORDER_PATH")
    String createOrderPath();

    @Key("GET_ORDER_PATH")
    String getOrderPath();

    @Key("DELETE_ORDER_PATH")
    String deleteOrderPath();
}
