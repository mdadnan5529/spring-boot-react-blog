package me.ktkim.blog.service.impl;

import me.ktkim.blog.common.Exception.ApiException;
import me.ktkim.blog.common.util.AuthProvider;
import me.ktkim.blog.common.util.AuthoritiesConstants;
import me.ktkim.blog.model.domain.Authority;
import me.ktkim.blog.model.domain.User;
import me.ktkim.blog.model.dto.UserDto;
import me.ktkim.blog.repository.AuthorityRepository;
import me.ktkim.blog.repository.UserRepository;
import me.ktkim.blog.service.UserServiceimpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceimplImpl implements UserServiceimpl {

    public Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    public UserRepository userRepository;

    @Autowired
    public AuthorityRepository authorityRepository;

    @Autowired
    public PasswordEncoder passwordEncoder;



    @Override
    public User registerAccount(UserDto.Create userDto) {
        userRepository.findByEmail(userDto.getEmail())
                .ifPresent(user -> {
                    throw new ApiException("Email Already exists.", HttpStatus.BAD_REQUEST);
                });

        User user = this.createUser(userDto.getEmail().toLowerCase(), userDto.getPassword(), userDto.getUserName());
        return user;
    }

    @Override
    public User createUser(String email, String password, String userName) {
        User newUser = new User();
        Optional<Authority> authority = authorityRepository.findById(AuthoritiesConstants.USER);
        Set<Authority> authorities = new HashSet<>();
        newUser.setPassword( passwordEncoder.encode(password));
        newUser.setEmail(email);
        newUser.setUserName(userName);
        newUser.setProvider(AuthProvider.local);
        authority.ifPresent(auth -> authorities.add(auth));
        newUser.setAuthorities(authorities);
        userRepository.save(newUser);
        log.debug("Created Information for User: {}", newUser);
        return newUser;
    }

    @Override
    public void updateUser(Long id, String email, String name, boolean activated) {
        userRepository.findOneById(id).ifPresent(user -> {
            user.setEmail(email);
            log.debug("Changed Information for User: {}", user);
        });
    }

    @Override
    public Page<User> findAllUser(Pageable pageable) {
        Page<User> users = userRepository.findAll(pageable);
        return users;
    }

    @Override
    public List<String> getAuthorities() {
        return authorityRepository.findAll().stream().map(Authority::getName).collect(Collectors.toList());
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.findOneById(userId).ifPresent(user -> {
            userRepository.deleteById(userId);
        });
    }


}
