package pl.javastart.restassured.test.http.methods;


import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import pl.javastart.main.pojo.*;
import java.util.Collections;

public class BasicHttpMethodsTests {

    @Test
    public void givenPetWhenPostPetThenPetIsCreatedTest() {
        Category category = new Category();
        category.setId(1);
        category.setName("dogs");

        Tag tag = new Tag();
        tag.setId(1);
        tag.setName("dogs-category");

        Pet pet = new Pet();
        pet.setId(123);
        pet.setCategory(category);
        pet.setName("doggie");
        pet.setPhotoUrls(Collections.singletonList("http://photos.com/dog1.jpg"));
        pet.setTags(Collections.singletonList(tag));
        pet.setStatus("available");

        given().log().all().body(pet).contentType("application/json")
                .when().post("https://swaggerpetstore.przyklady.javastart.pl/v2/pet")
                .then().log().all().statusCode(200);
    }


    @Test
    public void givenExistingPetIdWhenGetPetThenReturnPetTest() {
        given().log().uri().log().method()
                .pathParam("petId", 1)
                .when().get("https://swaggerpetstore.przyklady.javastart.pl/v2/pet/{petId}")
                .then().log().all().statusCode(200);
    }


    @Test
    public void givenExistingPetWhenUpdatePetNameThenPetIsChangedTest() {

        Category category = new Category();
        category.setId(1);
        category.setName("dogs");

        Pet pet = new Pet();
        pet.setId(123);
        pet.setName("Burek");
        pet.setPhotoUrls(Collections.singletonList("http://photos.com/dog1.jpg"));
        pet.setStatus("available");

        Tag tag = new Tag();
        tag.setId(1);
        tag.setName("dogs-category");

        given().log().body().body(pet).contentType("application/json")
                .when().post("https://swaggerpetstore.przyklady.javastart.pl/v2/pet")
                .then().statusCode(200);

        pet.setName("Reksio");

        given().body(pet).contentType("application/json")
                .when().put("https://swaggerpetstore.przyklady.javastart.pl/v2/pet")
                .then().log().body().statusCode(200);
    }


    @Test
    public void givenExistingPetIdWhenDeletingPetThenIsDeletedTest() {

        Pet pet = new Pet();
        pet.setId(455);
        pet.setName("Burek");
        pet.setPhotoUrls(Collections.singletonList("http://photos.com/dog1.jpg"));
        pet.setStatus("available");

        Category category = new Category();
        category.setId(1);
        category.setName("dogs");

        Tag tag = new Tag();
        tag.setId(1);
        tag.setName("dogs-category");


        given().body(pet).contentType("application/json")
                .when().post("https://swaggerpetstore.przyklady.javastart.pl/v2/pet")
                .then().statusCode(200);

        given().pathParam("petId", pet.getId()).contentType("application/json")
                .when().delete("https://swaggerpetstore.przyklady.javastart.pl/v2/pet/{petId}")
                .then().statusCode(200);
    }
}
