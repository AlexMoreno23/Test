package by.morunov.controller;

import by.morunov.domain.entity.Role;
import by.morunov.domain.entity.User;
import by.morunov.service.impl.UserServiceImpl;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

/**
 * @author Alex Morunov
 */
@RestController
@RequestMapping(name = "/")
public class RegistrationController {

    private final UserServiceImpl userService;
    private final PasswordEncoder passwordEncoder;

    public RegistrationController(UserServiceImpl userService,@Lazy PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(User user, Map<String, Object> model) {


        if (!userService.saveUser(user)) {
            model.put("message", "User exists!");
            return "registration";
        }

        user.setRole(Collections.singleton(Role.PERSON));
        if(user.getPassword().equals(passwordEncoder.encode("admin"))){
            user.setRole(Collections.singleton(Role.ADMIN));
        }
        userService.saveUser(user);

        return "redirect:/login";
    }
}
