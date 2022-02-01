package by.morunov.service.impl;

import by.morunov.exception.UserRegistrationException;
import by.morunov.domain.entity.Role;
import by.morunov.domain.entity.User;
import by.morunov.repository.UserRepo;
import by.morunov.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;

/**
 * @author Alex Morunov
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {


    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    public UserServiceImpl(UserRepo userRepo, @Lazy PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public boolean saveUser(User user) {
        User userFromDb = userRepo.findByEmail(user.getEmail());

        if (userFromDb != null) {
            throw new UserRegistrationException("This email is busy");

        }

        user.setRole(Collections.singleton(Role.PERSON));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);
        LOGGER.info("The user has registered in the system");
        return true;

    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        LOGGER.info("The user is logged in");
        return user;
    }
}
