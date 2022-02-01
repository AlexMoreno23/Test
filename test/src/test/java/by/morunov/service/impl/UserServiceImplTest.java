package by.morunov.service.impl;

import by.morunov.domain.entity.Role;
import by.morunov.domain.entity.User;
import by.morunov.exception.UserRegistrationException;
import by.morunov.repository.UserRepo;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Alex Morunov
 */
@RunWith(SpringRunner.class)
@SpringBootTest
class UserServiceImplTest {

    @Autowired
    private UserServiceImpl userService;

    @MockBean
    private UserRepo userRepo;
    @MockBean
    private PasswordEncoder passwordEncoder;

    @Test
    void saveUser() {
        User user = new User();
        boolean isUserCreated = userService.saveUser(user);
        assertTrue(isUserCreated);
        assertTrue(CoreMatchers.is(user.getRole()).matches(Collections.singleton(Role.PERSON)));

        Mockito.verify(userRepo, Mockito.times(1)).save(user);
    }

    @Test ()
    void saveUserFailTest(){
        User user = new User();
        user.setEmail("asdasd@mail.com");

        Mockito.doReturn(new User())
                .when(userRepo)
                .findByEmail("asdasd@mail.com");

        Throwable thrown= assertThrows(UserRegistrationException.class, () ->
        {
            userService.saveUser(user);
        });
        assertNotNull(thrown.getMessage());

    }

    @Test
    void loadUserByUsername() {
        String email = "asdasd@mail.com";

        Mockito.doReturn(new User())
                .when(userRepo)
                .findByEmail(email);

        UserDetails user = userService.loadUserByUsername(email);

        assertNotNull(user);
    }

    @Test
    void loadUserByUsernameFailTest(){
        String email = "asdasd@mail.com";

        Throwable thrown = assertThrows(UsernameNotFoundException.class, ()->
        {
            userService.loadUserByUsername(email);
        } );
        assertNotNull(thrown.getMessage());
    }
}