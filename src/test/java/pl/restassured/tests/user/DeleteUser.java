package pl.restassured.tests.user;

import org.aeonbits.owner.ConfigFactory;
import org.apache.http.HttpStatus;
import org.testng.annotations.Test;
import pl.main.cleaner.Cleaner;
import pl.main.pojo.User;
import pl.main.properties.EnvironmentConfig;
import pl.main.request.configuration.RequestConfigurationBuilder;
import pl.restassured.tests.SuitTestBase;

import static org.hamcrest.Matchers.equalTo;
import static io.restassured.RestAssured.given;

public class DeleteUser extends SuitTestBase {

    EnvironmentConfig environmentConfig = ConfigFactory.create(EnvironmentConfig.class);

    @Test
    public void shouldDeleteUserWithCorrectUserName() {
        User user = Cleaner.createUser();
        given()
                .spec(RequestConfigurationBuilder.getDefaultRequestSpecification())
                .pathParam("username", user.getUsername()).
        when()
                .delete(environmentConfig.deleteUserPath()).
        then()
                .statusCode(HttpStatus.SC_OK)
                .assertThat().body("code", equalTo(200))
                .assertThat().body("message", equalTo(user.getUsername()));
    }

    @Test
    public void shouldNotDeleteUserWithIncorrectUserName() {
        User user = Cleaner.createUser();
        given()
                .spec(RequestConfigurationBuilder.getDefaultRequestSpecification())
                .pathParam("username", "incorrectValue").
        when()
                .delete(environmentConfig.deleteUserPath()).
        then()
                .statusCode(HttpStatus.SC_NOT_FOUND);
        Cleaner.deleteUser(user);
    }

    @Test
    public void shouldNotDeleteAlreadyDeletedUser() {
        User user = Cleaner.createUser();
        Cleaner.deleteUser(user);
        given()
                .spec(RequestConfigurationBuilder.getDefaultRequestSpecification())
                .pathParam("username", user.getUsername()).
        when()
                .delete(environmentConfig.deleteUserPath()).
        then()
                .statusCode(HttpStatus.SC_NOT_FOUND);
    }

}
