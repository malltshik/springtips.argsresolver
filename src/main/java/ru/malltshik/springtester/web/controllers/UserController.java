package ru.malltshik.springtester.web.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.malltshik.springtester.configurations.annotations.InPath;
import ru.malltshik.springtester.persistense.entities.User;
import ru.malltshik.springtester.persistense.repositories.UserRepository;

@RestController
@RequestMapping("/restapi/users")
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class UserController {

    private final UserRepository userRepository;

    @GetMapping
    public ResponseEntity getUsers() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    @GetMapping("/{userId}")
    public ResponseEntity getUser(@InPath("userId") User user) {
        return ResponseEntity.ok(user);
    }

}
