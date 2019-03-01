package ru.malltshik.springtester;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import ru.malltshik.springtester.persistense.entities.User;
import ru.malltshik.springtester.persistense.repositories.UserRepository;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class SpringtesterApplication extends SpringBootServletInitializer {

    private final UserRepository userRepository;

    @Autowired
    public SpringtesterApplication(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostConstruct
    public void init() {
        User admin = User.builder().username("admin").build();
        admin.setAccountId(42L);
        List<User> users = Arrays.asList(
                admin,
                User.builder().username("username1").build(),
                User.builder().username("username2").build(),
                User.builder().username("username3").build(),
                User.builder().username("username4").build()
        );
        userRepository.saveAll(users);
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringtesterApplication.class, args);
    }
}
