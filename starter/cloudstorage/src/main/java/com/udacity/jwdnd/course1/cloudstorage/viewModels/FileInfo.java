package com.udacity.jwdnd.course1.cloudstorage.viewModels;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FileInfo {
    private Integer FileId;
    private String FileName;
    private String ContentType;
    private String FileSize;
    private Integer UserId;
    private byte[] FileData;
    private String DownloadLink;
}
