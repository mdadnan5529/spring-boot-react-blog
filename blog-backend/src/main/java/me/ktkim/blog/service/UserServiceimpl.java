package me.ktkim.blog.service;

import me.ktkim.blog.model.domain.User;
import me.ktkim.blog.model.dto.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author Kim Keumtae
 */

public interface UserServiceimpl {

//    public Logger log = LoggerFactory.getLogger(getClass());

//    @Autowired
//    public UserRepository userRepository;
//
//    @Autowired
//    public AuthorityRepository authorityRepository;
//
//    @Autowired
//    public PasswordEncoder passwordEncoder;

//    public User registerAccount(UserDto.Create userDto) {
//        userRepository.findByEmail(userDto.getEmail())
//                .ifPresent(user -> {
//                    throw new ApiException("Email Already exists.", HttpStatus.BAD_REQUEST);
//                });
//
//        User user = this.createUser(userDto.getEmail().toLowerCase(), userDto.getPassword(), userDto.getUserName());
//        return user;
//    }
    public User registerAccount(UserDto.Create userDto);

//    public User createUser(String email, String password, String userName) {
//        User newUser = new User();
//        Optional<Authority> authority = authorityRepository.findById(AuthoritiesConstants.USER);
//        Set<Authority> authorities = new HashSet<>();
//        newUser.setPassword( passwordEncoder.encode(password));
//        newUser.setEmail(email);
//        newUser.setUserName(userName);
//        newUser.setProvider(AuthProvider.local);
//        authority.ifPresent(auth -> authorities.add(auth));
//        newUser.setAuthorities(authorities);
//        userRepository.save(newUser);
//        log.debug("Created Information for User: {}", newUser);
//        return newUser;
//    }
    public User createUser(String email, String password, String userName);

//    public void updateUser(Long id, String email, String name, boolean activated) {
//        userRepository.findOneById(id).ifPresent(user -> {
//            user.setEmail(email);
//            log.debug("Changed Information for User: {}", user);
//        });
//    }
    public void updateUser(Long id, String email, String name, boolean activated);

//    public Page<User> findAllUser(Pageable pageable) {
//        Page<User> users = userRepository.findAll(pageable);
//        return users;
//    }
    public Page<User> findAllUser(Pageable pageable);

//    public List<String> getAuthorities() {
//        return authorityRepository.findAll().stream().map(Authority::getName).collect(Collectors.toList());
//    }
    public List<String> getAuthorities();

//    public void deleteUser(Long userId) {
//        userRepository.findOneById(userId).ifPresent(user -> {
//            userRepository.deleteById(userId);
//        });
//    }

    public void deleteUser(Long userId);
}