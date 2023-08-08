package pl.restassured.tests.user;

import org.aeonbits.owner.ConfigFactory;
import org.apache.http.HttpStatus;
import org.testng.annotations.Test;
import pl.main.builders.UserBuilder;
import pl.main.pojo.User;
import pl.main.cleaner.Cleaner;
import pl.main.properties.EnvironmentConfig;
import pl.main.readfile.ReadFile;
import pl.main.request.configuration.RequestConfigurationBuilder;
import pl.restassured.tests.SuitTestBase;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class CreateUser extends SuitTestBase {

    EnvironmentConfig environmentConfig = ConfigFactory.create(EnvironmentConfig.class);

    @Test
    public void shouldCreateUserWithAllParameters() {
        User user = new UserBuilder().withAllData().build();
        given()
                .spec(RequestConfigurationBuilder.getDefaultRequestSpecification())
                .body(user).
        when()
                .post(environmentConfig.createUserPath()).
        then()
                .statusCode(HttpStatus.SC_OK)
                .assertThat().body("code", equalTo(200))
                .assertThat().body("message", equalTo(user.getId().toString()));
        Cleaner.deleteUser(user);
    }

    @Test
    public void shouldCreateUserWithoutId() {
        User user = new UserBuilder().withoutId().build();
        given()
                .spec(RequestConfigurationBuilder.getDefaultRequestSpecification())
                .body(user).
        when()
                .post(environmentConfig.createUserPath()).
        then()
                .statusCode(HttpStatus.SC_OK)
                .assertThat().body("code", equalTo(200));
        Cleaner.deleteUser(user);
    }

    @Test
    public void shouldCreateUserWithoutUserName() {
        User user = new UserBuilder().withoutFirstName().build();
        given()
                .spec(RequestConfigurationBuilder.getDefaultRequestSpecification())
                .body(user).
        when()
                .post(environmentConfig.createUserPath()).
        then()
                .statusCode(HttpStatus.SC_OK)
                .assertThat().body("code", equalTo(200))
                .assertThat().body("message", equalTo(user.getId().toString()));
        Cleaner.deleteUser(user);
    }

    @Test
    public void shouldCreateUserWithUserNameOnly() {
        User user = new UserBuilder().withUserName().build();
        given()
                .spec(RequestConfigurationBuilder.getDefaultRequestSpecification())
                .body(user).
        when()
                .post(environmentConfig.createUserPath()).
        then()
                .statusCode(HttpStatus.SC_OK)
                .assertThat().body("code", equalTo(200));
        Cleaner.deleteUser(user);
    }

    @Test
    public void shouldCreateUserWithDataFromFile() {
        User user = ReadFile.getUserData();
        given()
                .spec(RequestConfigurationBuilder.getDefaultRequestSpecification())
                .body(user).
        when()
                .post(environmentConfig.createUserPath()).
        then()
                .statusCode(HttpStatus.SC_OK)
                .assertThat().body("code", equalTo(200))
                .assertThat().body("message", equalTo(user.getId().toString()));
        Cleaner.deleteUser(user);
    }

}
