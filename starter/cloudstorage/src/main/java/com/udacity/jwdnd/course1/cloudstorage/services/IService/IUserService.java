package com.udacity.jwdnd.course1.cloudstorage.services.IService;

import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.viewModels.UserSignupInfo;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Objects;

public interface IUserService {

    public boolean isUsernameExists(String username);

    public Integer createNewUser(UserSignupInfo user);

    public User getUser(String username);
}
