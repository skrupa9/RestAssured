package pl.restassured.order;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.apache.http.HttpStatus;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class DeleteOrder {


    @BeforeClass
    public void setupConfiguration() {
        RestAssured.baseURI = "https://petstore.swagger.io";
        RestAssured.basePath = "v2/store";
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    }


    @Test
    public void shouldDeleteExistedOrder() {

        given().
                pathParam("orderId", 5).
        when()
                .delete("order/{orderId}").
        then()
                .statusCode(HttpStatus.SC_OK)
                .assertThat().body("code", equalTo(200))
                .assertThat().body("type", equalToIgnoringCase("unknown"))
                .assertThat().body("message", equalTo(5));

    }


    @Test
    public void shouldNotDeleteAlreadyDeletedOrder() {

        given().
                pathParam("orderId", 5).
        when()
                .delete("order/{orderId}").
        then()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .assertThat().body("code", equalTo(404))
                .assertThat().body("type", equalToIgnoringCase("unknown"))
                .assertThat().body("message", equalToIgnoringCase("Order Not Found"));

    }


    @Test
    public void shouldNotDeleteNotExistedOrder() {

        given()
                .pathParam("orderId", 1).
        when()
                .delete("order/{orderId}").
        then()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .assertThat().body("code", equalTo(404))
                .assertThat().body("type", equalToIgnoringCase("unknown"))
                .assertThat().body("message", equalToIgnoringCase("Order Not Found"));
    }


}
