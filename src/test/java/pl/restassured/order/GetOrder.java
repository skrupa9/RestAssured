package pl.restassured.order;


import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.apache.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class GetOrder {


    @BeforeClass
    public void setupConfiguration() {
        RestAssured.baseURI = "https://petstore.swagger.io";
        RestAssured.basePath = "v2/store";
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    }


    @Test
    public void shouldGetExistedOrder() {

        given()
                .pathParam("orderId", 5).
        when()
                .get("/order/{orderId}").
        then()
                .statusCode(HttpStatus.SC_OK);
    }


    @Test
    public void shouldNotGetNotExistedOrder() {

        given()
                .pathParam("orderId", 4).
        when()
                .get("order/{orderId}").
        then()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .assertThat().body("code", equalTo(1))
                .assertThat().body("type", containsString("error"))
                .assertThat().body("message", containsString("Order not found"));

    }


    @Test
    public void shouldNotGetOrderWithoutIdPathParam() {

        given()
                .queryParam("orderId", 11).
        when()
                .get("order/{orderId}").
        then()
                .statusCode(HttpStatus.SC_BAD_REQUEST);

    }


}
