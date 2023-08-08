package pl.restassured.tests.order;

import org.aeonbits.owner.ConfigFactory;
import org.testng.annotations.Test;
import org.apache.http.HttpStatus;
import pl.main.cleaner.Cleaner;
import pl.main.pojo.Order;
import pl.main.properties.EnvironmentConfig;
import pl.main.request.configuration.RequestConfigurationBuilder;
import pl.restassured.tests.SuitTestBase;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class GetOrder extends SuitTestBase {

    EnvironmentConfig environmentConfig = ConfigFactory.create(EnvironmentConfig.class);

    @Test
    public void shouldGetExistedOrder() {
        Order order = Cleaner.createOrder();
        given()
                .spec(RequestConfigurationBuilder.getDefaultRequestSpecification())
                .pathParam("orderId", order.getId()).
        when()
                .get(environmentConfig.getOrderPath()).
        then()
                .statusCode(HttpStatus.SC_OK)
                .assertThat().body("id", equalTo(order.getId()))
                .assertThat().body("petId", equalTo(order.getPetId()))
                .assertThat().body("quantity", equalTo(order.getQuantity()))
                .assertThat().body("status", equalTo(order.getStatus()))
                .assertThat().body("complete", equalTo(order.isComplete()));
        Cleaner.deleteOrder(order);
    }

    @Test
    public void shouldNotGetNotExistedOrder() {
        given()
                .spec(RequestConfigurationBuilder.getDefaultRequestSpecification())
                .pathParam("orderId", 1001).
        when()
                .get(environmentConfig.getOrderPath()).
        then()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .assertThat().body("code", equalTo(1))
                .assertThat().body("type", containsString("error"))
                .assertThat().body("message", containsString("Order not found"));
    }

}
