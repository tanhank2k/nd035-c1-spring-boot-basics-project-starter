package com.udacity.jwdnd.course1.cloudstorage.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Credential {
    private Integer CredentialId;
    private String Url;
    private String UserName;
    private String Key;
    private String Password;
    private Integer UserId;
}
