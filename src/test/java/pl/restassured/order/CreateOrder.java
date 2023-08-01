package pl.restassured.order;


import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pl.main.pojo.Order;
import org.apache.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;


public class CreateOrder {


    @BeforeClass
    public void setupConfiguration() {
        RestAssured.baseURI = "https://petstore.swagger.io";
        RestAssured.basePath = "v2/store";
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    }


    @Test
    public void shouldCreateOrderWithAllParameters() {

        Order order = new Order();
        order.setId(5);
        order.setPetId(12);
        order.setQuantity(2);
        order.setStatus("placed");
        order.setShipDate("2023-08-01T16:14:41.499Z");
        order.setComplete(true);


        given()
                .body(order)
                .contentType("application/json").
        when()
                .post("order").
        then()
                .statusCode(HttpStatus.SC_OK)
                .assertThat().body("id", equalTo(order.getId()))
                .assertThat().body("petId", equalTo(order.getPetId()))
                .assertThat().body("quantity", equalTo(order.getQuantity()))
                .assertThat().body("shipDate", containsString(order.getShipDate().split("Z")[0]))
                .assertThat().body("status", equalTo(order.getStatus()))
                .assertThat().body("complete", equalTo(order.isComplete()));
    }


    @Test
    public void shouldCreateOrderWithoutId() {

        Order order = new Order();
        order.setId(4);
        order.setPetId(8);
        order.setQuantity(12);
        order.setStatus("placed");
        order.setShipDate("2023-08-01T16:14:41.499Z");
        order.setComplete(true);


        given()
                .body(order)
                .contentType("application/json").
        when()
                .post("order").
        then()
                .statusCode(HttpStatus.SC_OK)
                .assertThat().body("id", equalTo(order.getId()))
                .assertThat().body("petId", equalTo(order.getPetId()))
                .assertThat().body("quantity", equalTo(order.getQuantity()))
                .assertThat().body("shipDate", containsString(order.getShipDate().split("Z")[0]))
                .assertThat().body("status", equalToIgnoringCase(order.getStatus()))
                .assertThat().body("complete", equalTo(order.isComplete()));
    }


    @Test
    public void shouldCreateOrderWithoutNotRequiredParameter() {

        Order order = new Order();
        order.setId(6);
        order.setPetId(12);
        order.setQuantity(18);
        order.setStatus("placed");
        order.setShipDate("2023-08-01T16:14:41.499Z");


        given()
                .body(order)
                .contentType("application/json").
        when()
                .post("order").
        then()
                .statusCode(HttpStatus.SC_OK)
                .assertThat().body("petId", equalTo(order.getPetId()))
                .assertThat().body("quantity", equalTo(order.getQuantity()))
                .assertThat().body("shipDate", containsString(order.getShipDate().split("Z")[0]))
                .assertThat().body("status", equalTo(order.getStatus()))
                .assertThat().body("complete", equalTo(order.isComplete()));
    }


}
