package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.viewModels.UserSignupInfo;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Objects;

@Service
public class UserService {
    private final UserMapper userMapper;
    private final HashService hashService;

    public UserService(UserMapper userMapper, HashService hashService) {
        this.userMapper = userMapper;
        this.hashService = hashService;
    }

    public boolean isUsernameExists(String username){
        return Objects.isNull(userMapper.getUser(username));
    }

    public Integer createNewUser(UserSignupInfo user){
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        String encodedSalt = Base64.getEncoder().encodeToString(salt);
        String hashPassword = hashService.getHashedValue(user.getPassword(), encodedSalt);
        User newUser = new User();
        newUser.setUsername(user.getUsername());
        newUser.setSalt(encodedSalt);
        newUser.setPassword(hashPassword);
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        return userMapper.insertUser(newUser);
    }

    public User getUser(String username){
        return userMapper.getUser(username);
    }
}
