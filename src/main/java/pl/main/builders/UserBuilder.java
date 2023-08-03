package pl.main.builders;

import com.github.javafaker.Faker;
import pl.main.pojo.User;

public class UserBuilder {

    private User user = new User();
    private Faker faker = new Faker();

    public User build() {
        User builder = user;
        user = new User();
        return builder;
    }

    public UserBuilder withAllData() {
        withId();
        withUserName();
        withSomeData();
        return this;
    }

    public UserBuilder withoutId() {
        withFirstName();
        withSomeData();
        return this;
    }

    public UserBuilder withoutFirstName() {
        withId();
        withSomeData();
        return this;
    }

    private UserBuilder withSomeData() {
        withUserName();
        withLastName();
        withEmail();
        withPassword();
        withPhone();
        withUserStatus();
        return this;
    }

    public UserBuilder withId() {
        user.setId(4);
        return this;
    }

    public UserBuilder withId(Integer id) {
        user.setId(id);
        return this;
    }

    public UserBuilder withUserName() {
        user.setUsername(faker.name().firstName().toLowerCase());
        return this;
    }

    public UserBuilder withUserName(String userName) {
        user.setUsername(userName);
        return this;
    }

    public UserBuilder withFirstName() {
        user.setFirstName(faker.name().firstName());
        return this;
    }

    public UserBuilder withFirstName(String firstName) {
        user.setFirstName(firstName);
        return this;
    }

    public UserBuilder withLastName() {
        user.setLastName(faker.name().lastName());
        return this;
    }

    public UserBuilder withLastName(String lastName) {
        user.setLastName(lastName);
        return this;
    }

    public UserBuilder withEmail() {
        user.setEmail(faker.internet().emailAddress());
        return this;
    }

    public UserBuilder withEmail(String email) {
        user.setEmail(email);
        return this;
    }

    public UserBuilder withPassword() {
        user.setPassword(faker.internet().password());
        return this;
    }

    public UserBuilder withPassword(String password) {
        user.setPassword(password);
        return this;
    }

    public UserBuilder withPhone() {
        user.setPhone(faker.phoneNumber().phoneNumber());
        return this;
    }

    public UserBuilder withPhone(String phone) {
        user.setPhone(phone);
        return this;
    }

    public UserBuilder withUserStatus() {
        user.setUserStatus(0);
        return this;
    }

    public UserBuilder withUserStatus(Integer userStatus) {
        user.setUserStatus(userStatus);
        return this;
    }

}
