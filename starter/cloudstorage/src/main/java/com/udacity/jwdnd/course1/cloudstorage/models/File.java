package com.udacity.jwdnd.course1.cloudstorage.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class File {
    private Integer FileId;
    private String FileName;
    private String ContentType;
    private String FileSize;
    private Integer UserId;
    private byte[] FileData;
}
