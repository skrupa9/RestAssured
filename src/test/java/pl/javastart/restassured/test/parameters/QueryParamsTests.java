package pl.javastart.restassured.test.parameters;

import org.testng.annotations.Test;
import pl.javastart.main.pojo.Pet;
import pl.javastart.main.pojo.Tag;
import pl.javastart.main.pojo.Category;

import java.util.Arrays;
import java.util.Collections;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertTrue;

public class QueryParamsTests {

    @Test
    public void givenExistingPetWithStatusSoldWhenGetPetWithSoldStatusThenPetWithStatusIsReturnedTest() {
        Pet pet = new Pet();
        pet.setId(777);
        pet.setName("Reksio");
        pet.setPhotoUrls(Collections.singletonList("http://photos.com/dog1.jpg"));
        pet.setStatus("sold");

        Category category = new Category();
        category.setId(1);
        category.setName("dog-category");

        Tag tag = new Tag();
        tag.setId(0);
        tag.setName("tag-name");

        given().body(pet).contentType("application/json")
                .when().post("https://swaggerpetstore.przyklady.javastart.pl/v2/pet")
                .then().statusCode(200);

        Pet[] petsList = given().queryParam("status", "sold")
                .when().get("https://swaggerpetstore.przyklady.javastart.pl/v2/pet/findByStatus")
                .then().statusCode(200)
                .extract().as(Pet[].class);

        assertTrue(Arrays.asList(petsList).size() > 0, "List of pets");
    }
}