package com.udacity.jwdnd.course1.cloudstorage.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private Integer UserId;
    private String Username;
    private String Salt;
    private String Password;
    private String FirstName;
    private String LastName;

}
