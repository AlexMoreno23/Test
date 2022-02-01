package by.morunov.repository;

import by.morunov.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Alex Morunov
 */
@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    User findByEmail(String email);

}
