package com.udacity.jwdnd.course1.cloudstorage.viewModels;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CredentialInfo {
    private Integer credentialId;
    private String url;
    private String username;
    private String key;
    private String encryptedPassword;
    private String password;
    private Integer userId;
}
