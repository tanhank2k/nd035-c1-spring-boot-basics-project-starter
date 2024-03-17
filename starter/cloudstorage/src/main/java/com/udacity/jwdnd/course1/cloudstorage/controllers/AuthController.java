package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.services.IService.IUserService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import com.udacity.jwdnd.course1.cloudstorage.viewModels.UserSignupInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Objects;

@Controller
public class AuthController {

    @Autowired
    private IUserService userService;

    @GetMapping("/login")
    public String loginView(){
        return "login";
    }

    @GetMapping("/signup")
    public String signUpView(){
        return "signup";
    }

    @PostMapping("/signup")
    public String signUpUser(@ModelAttribute UserSignupInfo user, Model model, RedirectAttributes redirectAttributes){
        String signupError = null;

        if (!userService.isUsernameExists(user.getUsername())){
            signupError =  "The username already exists.";
        }

        if (Objects.isNull(signupError)){
            int numOfRowAdded = userService.createNewUser(user);
            if (numOfRowAdded < 1){
                signupError = "There was an error signing you up. Please try again.";
            }
        }

        if (signupError == null) {
            redirectAttributes.addFlashAttribute("signupSuccess", true);
            return "redirect:/login";
        } else {
            model.addAttribute("signupError", signupError);
        }

        return "signup";
    }

}
