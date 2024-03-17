package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.IService.IFileService;
import com.udacity.jwdnd.course1.cloudstorage.viewModels.FileInfo;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class FileController {
    @Autowired
    private IFileService fileService;

    @PostMapping("/files")
    public String uploadFile(@ModelAttribute MultipartFile fileUpload, Model model, Authentication authentication) {
        if (!fileUpload.isEmpty()) {
            try {
                byte[] bytes = fileUpload.getBytes();
                long fileSizeInBytes = bytes.length;
                // Convert bytes to KB or MB if needed
                String contentType = fileUpload.getContentType();
                User userDetails = (User) authentication.getPrincipal();
                FileInfo fileInfo = FileInfo.builder()
                        .FileSize(String.valueOf(fileSizeInBytes))
                        .FileName(fileUpload.getOriginalFilename())
                        .FileData(bytes)
                        .ContentType(contentType)
                        .UserId(userDetails.getUserId())
                        .build();
                int insertRecord = fileService.createFile(fileInfo);
                if (insertRecord == 0) {
                    return "redirect:/result?isSuccess=false&errorMessage=Changes Not Saved";
                }
                return "redirect:/result?isSuccess=true";
            } catch (IOException e) {
                e.printStackTrace();
                return "redirect:/result?isSuccess=false&errorMessage=Changes Not Saved";
            }
        } else {
            return "redirect:/result?isSuccess=false&errorMessage=Changes Not Saved";
        }
    }

    @GetMapping("/files/delete")
    public String deleteFile(@RequestParam("id") String fileId) {
        try {
            var result = 0;
            if (!fileId.isEmpty()) {
                result = fileService.deleteFile(fileId);
            }
            if (result > 0) {
                return "redirect:/result?isSuccess=true";
            }
            return "redirect:/result?isSuccess=false&errorMessage=Changes Not Saved";
        } catch (Exception e) {
            return "redirect:/result?isSuccess=false&errorMessage=" + e.getMessage();
        }
    }
}
