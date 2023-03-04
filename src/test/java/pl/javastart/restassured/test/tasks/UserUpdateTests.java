package pl.javastart.restassured.test.tasks;

import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import pl.javastart.main.pojo.user.User;

public class UserUpdateTests {

    @Test
    public void givenCorrectUserDataWhenFirstNameLastNameAreUpdatedThenUserDataIsUpdatedTest() {
        User user = new User();
        user.setId(1);
        user.setUsername("firstUser");
        user.setFirstName("Krzysztof");
        user.setLastName("Kowalski");
        user.setEmail("kowal@test.com");
        user.setPassword("password");
        user.setPhone("123 325 152");
        user.setUserStatus(1);

        given().body(user).contentType("application/json")
                .when().post("https://swaggerpetstore.przyklady.javastart.pl/v2/user")
                .then().statusCode(200);

        user.setFirstName("Adam");
        user.setLastName("Nowak");

        given().body(user).contentType("application/json")
                .pathParam("username", user.getUsername())
                .when().put("https://swaggerpetstore.przyklady.javastart.pl/v2/user/{username}")
                .then().statusCode(200);

        given().pathParam("username", user.getUsername())
                .when().get("https://swaggerpetstore.przyklady.javastart.pl/v2/user/{username}")
                .then().log().all().statusCode(200);
    }
}
