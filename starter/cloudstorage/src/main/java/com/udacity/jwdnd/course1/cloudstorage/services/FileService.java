package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.mappers.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.File;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.IService.IFileService;
import com.udacity.jwdnd.course1.cloudstorage.viewModels.FileInfo;
import com.udacity.jwdnd.course1.cloudstorage.viewModels.UserSignupInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class FileService implements IFileService {
    @Autowired
    private FileMapper fileMapper;

    @Override
    public Integer createFile(FileInfo fileInfo) {
        File file = File.builder()
                .FileName(fileInfo.getFileName())
                .FileSize(fileInfo.getFileSize())
                .FileData(fileInfo.getFileData())
                .ContentType(fileInfo.getContentType())
                .UserId(fileInfo.getUserId())
                .build();
        return fileMapper.insertFile(file);
    }

    @Override
    public File getFile(String fileId) {
        return null;
    }

    /**
     * @param fileName
     * @return
     */
    @Override
    public boolean checkFileExists(String fileName) {
        File file = fileMapper.getFileByName(fileName);
        return Objects.nonNull(file);
    }

    @Override
    public List<File> getFiles(List<String> fileIds) {
        return null;
    }

    @Override
    public List<FileInfo> getFilesByUserId(String userId) {
        var files = fileMapper.getFilesByUser(userId);
        var fileInforms = files.stream().map((File item) ->
                FileInfo.builder()
                        .FileId(item.getFileId())
                        .FileSize(item.getFileSize())
                        .FileName(item.getFileName())
                        .FileData(item.getFileData())
                        .ContentType(item.getContentType())
                        .UserId(item.getUserId())
                        .DownloadLink(generateDownloadLink(item.getFileData(), item.getContentType()))
                        .build()
        ).collect(Collectors.toList());
        return fileInforms;
    }

    @Override
    public Integer updateFile(FileInfo fileInfo) {
        return null;
    }

    @Override
    public Integer deleteFile(String fileId) {
        var result = fileMapper.deleteFile(Integer.parseInt(fileId));
        return result;
    }

    /**
     * @param dataFile
     * @param contentType
     * @return
     */
    @Override
    public String generateDownloadLink(byte[] dataFile, String contentType) {
        String base64 = Base64.getEncoder().encodeToString(dataFile);
        String downloadLink = "data:" + contentType + ";base64," + base64;
        return downloadLink;
    }
}
