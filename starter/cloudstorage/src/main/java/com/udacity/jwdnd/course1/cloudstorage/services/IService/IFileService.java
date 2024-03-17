package com.udacity.jwdnd.course1.cloudstorage.services.IService;

import com.udacity.jwdnd.course1.cloudstorage.models.File;
import com.udacity.jwdnd.course1.cloudstorage.viewModels.FileInfo;

import java.util.List;

public interface IFileService {
    public Integer createFile(FileInfo fileInfo);
    public File getFile(String fileId);
    public List<File> getFiles(List<String> fileIds);
    public List<FileInfo> getFilesByUserId(String userId);
    public Integer updateFile(FileInfo user);
    public Integer deleteFile(String fileId);
    public String generateDownloadLink(byte[] dataFile, String contentType);
}
