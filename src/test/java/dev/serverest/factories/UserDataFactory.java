package dev.serverest.factories;

import com.github.javafaker.Faker;
import dev.serverest.models.User;

import java.util.UUID;

public class UserDataFactory {

    private final Faker faker = new Faker();

    public User validUser() {
        User user = new User();
        user.setNome(faker.name().fullName());
        user.setEmail(uniqueEmail());
        user.setPassword(faker.internet().password(8, 12));
        user.setAdministrador("false");
        return user;
    }

    public User adminUser() {
        User user = new User();
        user.setNome(faker.name().fullName());
        user.setEmail(uniqueEmail());
        user.setPassword(faker.internet().password(8, 12));
        user.setAdministrador("true");
        return user;
    }

    public User userWithEmail(String email) {
        User user = new User();
        user.setNome(faker.name().fullName());
        user.setEmail(email);
        user.setPassword(faker.internet().password(8, 12));
        user.setAdministrador("false");
        return user;
    }

    private String uniqueEmail() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 12) + "@test.com";
    }
}
