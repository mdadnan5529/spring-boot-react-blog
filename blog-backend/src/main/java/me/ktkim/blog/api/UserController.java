package me.ktkim.blog.api;

import me.ktkim.blog.model.dto.UserDto;
import me.ktkim.blog.repository.UserRepository;
import me.ktkim.blog.service.UserServiceimpl;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author Kim Keumtae
 */
@RestController
@CrossOrigin
@RequestMapping("/api")
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserServiceimpl userServiceimpl;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    public UserController(UserServiceimpl userServiceimpl , UserRepository userRepository ,ModelMapper modelMapper ){
        this.userServiceimpl = userServiceimpl;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    private static final String CHECK_ERROR_MESSAGE = "Incorrect password";

    @PostMapping(path = "/register")
    public ResponseEntity registerAccount(@Valid @RequestBody UserDto.Create userDto) {

        HttpHeaders textPlainHeaders = new HttpHeaders();
        textPlainHeaders.setContentType(MediaType.TEXT_PLAIN);
        if (StringUtils.isEmpty(userDto.getPassword()) &&
                (userDto.getPassword().length() <= 4 && userDto.getPassword().length() > 10)) {
            return new ResponseEntity<>(CHECK_ERROR_MESSAGE, HttpStatus.BAD_REQUEST);
        }
        userServiceimpl.registerAccount(userDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/users/{email}")
    public ResponseEntity<UserDto.Response> getUser(@PathVariable String email) {
        return userRepository.findByEmail(email)
                .map(user -> modelMapper.map(user, UserDto.Response.class))
                .map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
