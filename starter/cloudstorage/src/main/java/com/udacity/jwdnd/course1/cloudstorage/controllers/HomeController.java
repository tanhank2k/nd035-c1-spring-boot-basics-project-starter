package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.IService.ICredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.IService.IFileService;
import com.udacity.jwdnd.course1.cloudstorage.services.IService.INoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.IService.IUserService;
import com.udacity.jwdnd.course1.cloudstorage.viewModels.ResultModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Objects;

@Controller
public class HomeController {
    @Autowired
    private IFileService fileService;
    @Autowired
    private ICredentialService credentialService;
    @Autowired
    private INoteService noteService;

    @RequestMapping(value = {"/", "/home"})
    @GetMapping()
    public String getHomeView(Authentication authentication, Model model) {
        User user = (User) authentication.getPrincipal();
        var fileInforms = fileService.getFilesByUserId(String.valueOf(user.getUserId()));
        var noteInforms = noteService.getNotesByUser(String.valueOf(user.getUserId()));
        var credentialInforms = credentialService.getCredentialsByUser(String.valueOf(user.getUserId()));
        model.addAttribute("fileInforms", fileInforms);
        model.addAttribute("noteInforms", noteInforms);
        model.addAttribute("credentialInforms", credentialInforms);
        return "home";
    }

    @RequestMapping("/result")
    @GetMapping()
    public String getResultView(@RequestParam("isSuccess") boolean isSuccess,
                                @RequestParam(value ="errorMessage", required = false) String errorMessage,
                                Model model) {
        model.addAttribute("isSuccess", isSuccess);
        model.addAttribute("errorMessage", errorMessage);
        return "result";
    }
//    @RequestMapping("/home")
//    @GetMapping()
//    public String getHomeView(){
//        return "home";
//    }
}
