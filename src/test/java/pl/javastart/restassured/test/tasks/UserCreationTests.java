package pl.javastart.restassured.test.tasks;

import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import pl.javastart.main.pojo.user.User;
public class UserCreationTests {

    @Test
    public void givenCorrectUserDataWhenCreateUserThenUserIsCreatedTest() {
        User user = new User();
        user.setId(445);
        user.setUsername("firstuser");
        user.setFirstName("Krzysztof");
        user.setLastName("Kowalski");
        user.setEmail("Krzysztof@test.com");
        user.setPassword("password");
        user.setPhone("+123 456 789");
        user.setUserStatus(1);

        given().body(user).contentType("application/json").log().all()
                .when().post("https://swaggerpetstore.przyklady.javastart.pl/v2/user")
                .then().statusCode(200);

        given().pathParam("username", user.getUsername())
                .when().get("https://swaggerpetstore.przyklady.javastart.pl/v2/user/{username}")
                .then().log().all().statusCode(200);
    }
}
