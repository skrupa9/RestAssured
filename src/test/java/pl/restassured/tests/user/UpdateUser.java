package pl.restassured.tests.user;

import org.aeonbits.owner.ConfigFactory;
import org.apache.http.HttpStatus;
import org.testng.annotations.Test;
import pl.main.builders.UserBuilder;
import pl.main.cleaner.Cleaner;
import pl.main.properties.EnvironmentConfig;
import pl.main.request.configuration.RequestConfigurationBuilder;
import pl.restassured.tests.SuitTestBase;
import pl.main.pojo.User;

import static org.hamcrest.Matchers.equalTo;
import static io.restassured.RestAssured.given;

public class UpdateUser extends SuitTestBase {

    EnvironmentConfig environmentConfig = ConfigFactory.create(EnvironmentConfig.class);

    @Test
    public void shouldUpdateUserWithCorrectUserName() {

        User createdUser = Cleaner.createUser();
        User updatedUser = new UserBuilder().withAllData().withUserName(createdUser.getUsername()).build();

        given()
                .spec(RequestConfigurationBuilder.getDefaultRequestSpecification())
                .body(updatedUser)
                .pathParam("username", updatedUser.getUsername())
                .when()
                .put(environmentConfig.updateUserPath())
                .then().statusCode(HttpStatus.SC_OK)
                    .assertThat().body("code", equalTo(200))
                    .assertThat().body("type", equalTo("uknown"));

        Cleaner.deleteUser(createdUser);
    }

    @Test
    public void shouldNotUpdateUserWithIncorrectUserName() {

        User createdUser = Cleaner.createUser();
        User updatedUser = new UserBuilder().withAllData().withUserName(createdUser.getUsername()).build();

        given()
                .spec(RequestConfigurationBuilder.getDefaultRequestSpecification())
                .body(updatedUser)
                .pathParam("username", "incorrectValue")
                .when()
                .put(environmentConfig.updateUserPath())
                .then().statusCode(HttpStatus.SC_NOT_FOUND); // Błąd API - powinno zwracać 404, ale zwraca 200

        Cleaner.deleteUser(createdUser);
    }

    @Test
    public void shouldNotUpdateUserWithoutBody() {

        User createdUser = Cleaner.createUser();
        User emptyBody = new User();
        emptyBody.setUsername(createdUser.getUsername());

        given()
                .spec(RequestConfigurationBuilder.getDefaultRequestSpecification())
                .body(emptyBody)
                .pathParam("username", emptyBody.getUsername())
                .when()
                .put(environmentConfig.updateUserPath())
                .then().statusCode(HttpStatus.SC_NOT_FOUND); // Błąd API - powinno zwracać 404, ale zwraca 200

        Cleaner.deleteUser(createdUser);
    }

}
