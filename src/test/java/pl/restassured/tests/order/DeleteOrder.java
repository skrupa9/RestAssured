package pl.restassured.tests.order;

import org.aeonbits.owner.ConfigFactory;
import org.apache.http.HttpStatus;
import org.testng.annotations.Test;
import pl.main.cleaner.Cleaner;
import pl.main.pojo.Order;
import pl.main.properties.EnvironmentConfig;
import pl.main.request.configuration.RequestConfigurationBuilder;
import pl.restassured.tests.SuitTestBase;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class DeleteOrder extends SuitTestBase {

    EnvironmentConfig environmentConfig = ConfigFactory.create(EnvironmentConfig.class);

    @Test
    public void shouldDeleteExistedOrder() {
        Order order = Cleaner.createOrder();
        given()
                .spec(RequestConfigurationBuilder.getDefaultRequestSpecification())
                .pathParam("orderId", order.getId())
                .when()
                .delete(environmentConfig.deleteOrderPath())
                .then().statusCode(HttpStatus.SC_OK)
                    .assertThat().body("code", equalTo(200))
                    .assertThat().body("message", equalTo(order.getId().toString()));
    }

    @Test
    public void shouldNotDeleteAlreadyDeletedOrder() {
        Order order = Cleaner.createOrder();
        Cleaner.deleteOrder(order);
        given()
                .spec(RequestConfigurationBuilder.getDefaultRequestSpecification())
                .pathParam("orderId", order.getId())
                .when()
                .delete(environmentConfig.deleteOrderPath())
                .then().statusCode(HttpStatus.SC_NOT_FOUND)
                    .assertThat().body("code", equalTo(404))
                    .assertThat().body("message", equalToIgnoringCase("Order Not Found"));
    }

    @Test
    public void shouldNotDeleteNotExistedOrder() {
        given()
                .spec(RequestConfigurationBuilder.getDefaultRequestSpecification())
                .pathParam("orderId", 1)
                .when()
                .delete(environmentConfig.deleteOrderPath())
                .then().statusCode(HttpStatus.SC_NOT_FOUND)
                    .assertThat().body("code", equalTo(404))
                    .assertThat().body("type", equalToIgnoringCase("unknown"))
                    .assertThat().body("message", equalToIgnoringCase("Order Not Found"));
    }

}
