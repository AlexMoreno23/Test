package by.morunov.service;

import by.morunov.domain.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author Alex Morunov
 */
public interface UserService extends UserDetailsService {

    boolean saveUser(User user);



}
