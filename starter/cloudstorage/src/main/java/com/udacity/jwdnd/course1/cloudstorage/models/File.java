package com.udacity.jwdnd.course1.cloudstorage.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class File {
    private Integer FileId;
    private String FileName;
    private String ContentType;
    private String FileSize;
    private Integer UserId;
    private String FileData;
}
