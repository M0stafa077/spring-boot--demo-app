package com.springbootrestdemo.users;

import com.springbootrestdemo.global.exceptions.AccessDeniedException;
import com.springbootrestdemo.users.exceptions.EmailAlreadyExistsException;
import com.springbootrestdemo.users.exceptions.UserNotFoundException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder pwdEncoder;

    public Iterable<User> findAll(User currentUser) {
        assertAdminOnlyAccess(currentUser);
        return this.userRepository.findAll();
    }

    public User findById(Long userId, User currentUser) {
        assertOwnerOrAdminAccess(currentUser, userId);

        Optional<User> user = this.userRepository.findWithOrdersById(userId);
        if (user.isEmpty()) {
            throw new UserNotFoundException(userId);
        }

        return user.get();
    }

    public User findByEmail(String email) {
        Optional<User> user = this.userRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new UserNotFoundException();
        }

        return user.get();
    }

    @Transactional
    public User create(User user) {
        if (this.userRepository.existsByEmail(user.getEmail())) {
            throw new EmailAlreadyExistsException("A user with this email already exists");
        }

        user.setId(null);
        user.setPassword(this.pwdEncoder.encode(user.getPassword()));
        return this.userRepository.save(user);
    }

    @Transactional
    public User update(Long userId, User changes, User currentUser) {
        User userEntity = this.findById(userId, currentUser);
        userEntity.setName(changes.getName());
        userEntity.setEmail(changes.getEmail());
        return this.userRepository.save(userEntity);
    }

    @Transactional
    public void remove(Long userId, User currentUser) {
        this.findById(userId, currentUser);

        this.userRepository.deleteById(userId);
    }

    @Override
    @NonNull
    public UserDetails loadUserByUsername(@NonNull String email)
            throws UsernameNotFoundException {
        var user = this.userRepository.findByEmail(email)
                .orElseThrow(
                        () -> new UsernameNotFoundException(("User not found"))
                );

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .authorities(new ArrayList<>()).build();
    }

    private void assertOwnerOrAdminAccess(User user, Long id) {
        if (!user.getId().equals(id) && !user.isAdmin()) {
            throw new AccessDeniedException("You cannot access this resource");
        }
    }

    private void assertAdminOnlyAccess(User user) {
        if (!user.isAdmin()) {
            throw new AccessDeniedException("You cannot access this resource");
        }
    }
}
