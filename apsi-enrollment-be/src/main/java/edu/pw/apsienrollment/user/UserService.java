package edu.pw.apsienrollment.user;

import edu.pw.apsienrollment.user.db.User;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.Optional;

public interface UserService {
    User getUser(Integer id);

    User getUserByUsername(String username);

    Optional<User> getUserIfAvailable(Integer userId, LocalDateTime availableFrom, LocalDateTime availableTo);

    Page<User> findUsers(String query, Integer page, Integer size);

    Page<User> findUsers(Integer page, Integer size);
}
