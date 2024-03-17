package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.IService.IUserService;
import com.udacity.jwdnd.course1.cloudstorage.viewModels.UserSignupInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Objects;

@Service
public class UserService implements IUserService, UserDetailsService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private HashService hashService;

    @Override
    public boolean isUsernameExists(String username){
        return Objects.isNull(userMapper.getUser(username));
    }

    @Override
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

    @Override
    public User getUser(String username){
        return userMapper.getUser(username);
    }

    /**
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.getUser(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        // Construct UserDetails object with user details
        return user;
    }
}
