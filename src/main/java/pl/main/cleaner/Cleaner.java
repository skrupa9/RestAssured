package pl.main.cleaner;

import org.aeonbits.owner.ConfigFactory;
import org.apache.http.HttpStatus;
import pl.main.builders.OrderBuilder;
import pl.main.builders.UserBuilder;
import pl.main.pojo.Order;
import pl.main.properties.EnvironmentConfig;
import pl.main.request.configuration.RequestConfigurationBuilder;
import pl.main.pojo.User;

import static io.restassured.RestAssured.given;

public class Cleaner {

    public static User createUser() {
        EnvironmentConfig environmentConfig = ConfigFactory.create(EnvironmentConfig.class);
        User user = new UserBuilder().withAllData().build();
        given()
                .spec(RequestConfigurationBuilder.getDefaultRequestSpecification())
                .body(user)
                .when()
                .post(environmentConfig.createUserPath())
                .then().statusCode(HttpStatus.SC_OK);
        return user;
    }

    public static void deleteUser(User user) {
        EnvironmentConfig environmentConfig = ConfigFactory.create(EnvironmentConfig.class);
        given()
                .spec(RequestConfigurationBuilder.getDefaultRequestSpecification())
                .pathParam("username", user.getUsername())
                .when()
                .delete(environmentConfig.deleteUserPath())
                .then().statusCode(HttpStatus.SC_OK);
    }

    public static Order createOrder() {
        EnvironmentConfig environmentConfig = ConfigFactory.create(EnvironmentConfig.class);
        Order order = new OrderBuilder().withAllData().build();
        given()
                .spec(RequestConfigurationBuilder.getDefaultRequestSpecification())
                .body(order)
                .when()
                .post(environmentConfig.createOrderPath())
                .then().statusCode(HttpStatus.SC_OK);

        return order;
    }

    public static void deleteOrder(Order order) {
        EnvironmentConfig environmentConfig = ConfigFactory.create(EnvironmentConfig.class);
        given()
                .spec(RequestConfigurationBuilder.getDefaultRequestSpecification())
                .pathParam("orderId", order.getId())
                .when()
                .delete(environmentConfig.deleteOrderPath())
                .then().statusCode(HttpStatus.SC_OK);
    }

}
