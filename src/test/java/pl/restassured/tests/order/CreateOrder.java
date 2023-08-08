package pl.restassured.tests.order;

import org.aeonbits.owner.ConfigFactory;
import org.testng.annotations.Test;
import pl.main.builders.OrderBuilder;
import pl.main.cleaner.Cleaner;
import pl.main.pojo.Order;
import org.apache.http.HttpStatus;
import pl.main.properties.EnvironmentConfig;
import pl.main.readfile.ReadFile;
import pl.main.request.configuration.RequestConfigurationBuilder;
import pl.restassured.tests.SuitTestBase;

import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.given;

public class CreateOrder extends SuitTestBase {

    EnvironmentConfig environmentConfig = ConfigFactory.create(EnvironmentConfig.class);

    @Test
    public void shouldCreateOrderWithAllParameters() {
        Order order = new OrderBuilder().withAllData().build();
        given()
                .spec(RequestConfigurationBuilder.getDefaultRequestSpecification())
                .body(order).
        when()
                .post(environmentConfig.createOrderPath()).
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
    public void shouldCreateOrderWithoutId() {
        Order order = new OrderBuilder().withAllData().build();
        given()
                .spec(RequestConfigurationBuilder.getDefaultRequestSpecification())
                .body(order).
        when()
                .post(environmentConfig.createOrderPath()).
        then()
                .statusCode(HttpStatus.SC_OK)
                .assertThat().body("id", equalTo(order.getId()))
                .assertThat().body("petId", equalTo(order.getPetId()))
                .assertThat().body("quantity", equalTo(order.getQuantity()))
                .assertThat().body("status", equalToIgnoringCase(order.getStatus()))
                .assertThat().body("complete", equalTo(order.isComplete()));
        Cleaner.deleteOrder(order);
    }

    @Test
    public void shouldCreateOrderWithoutNotRequiredParameter() {
        Order order = new OrderBuilder().withAllData().build();
        given()
                .spec(RequestConfigurationBuilder.getDefaultRequestSpecification())
                .body(order).
        when()
                .post(environmentConfig.createOrderPath()).
        then()
                .statusCode(HttpStatus.SC_OK)
                .assertThat().body("petId", equalTo(order.getPetId()))
                .assertThat().body("quantity", equalTo(order.getQuantity()))
                .assertThat().body("status", equalTo(order.getStatus()))
                .assertThat().body("complete", equalTo(order.isComplete()));
        Cleaner.deleteOrder(order);
    }

    @Test
    public void shouldCreateOrderWithDataFromFile() {
        Order order = ReadFile.getOrderData();
        given()
                .spec(RequestConfigurationBuilder.getDefaultRequestSpecification())
                .body(order).
        when()
                .post(environmentConfig.createOrderPath()).
        then()
                .statusCode(HttpStatus.SC_OK)
                .assertThat().body("id", equalTo(order.getId()))
                .assertThat().body("petId", equalTo(order.getPetId()))
                .assertThat().body("quantity", equalTo(order.getQuantity()))
                .assertThat().body("status", equalTo(order.getStatus()))
                .assertThat().body("complete", equalTo(order.isComplete()));
        Cleaner.deleteOrder(order);
    }

}
