package edu.pw.apsienrollment.user;

import edu.pw.apsienrollment.common.db.SearchQueryParser;
import edu.pw.apsienrollment.user.db.User;
import edu.pw.apsienrollment.user.db.UserRepository;
import edu.pw.apsienrollment.user.db.UserSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    public Optional<User> getUserIfAvailable(Integer id, LocalDateTime availableFrom, LocalDateTime availableTo) {
        return userRepository.findByIdAndAvailability(id, availableFrom, availableTo);
    }

    @Override
    public Page<User> findUsers(String query, Integer page, Integer size) {
        return userRepository.findAll(
                new UserSpecification(SearchQueryParser.parse(query)), PageRequest.of(page, size));
    }

    @Override
    public Page<User> findUsers(Integer page, Integer size) {
        return userRepository.findAll(PageRequest.of(page, size));
    }

    @Override
    public User getUser(Integer id) {
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }
}
