package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.IService.ICredentialService;
import com.udacity.jwdnd.course1.cloudstorage.viewModels.CredentialInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Objects;

@Controller
public class CredentialController {
    @Autowired
    private ICredentialService credentialService;

    @PostMapping("/credentials")
    public String insertNote(@ModelAttribute CredentialInfo credentialInfo, Authentication authentication){
        try {
            User user = (User) authentication.getPrincipal();
            credentialInfo.setUserId(user.getUserId());
            var result = 0;
            if (Objects.isNull(credentialInfo.getCredentialId())){
                result = credentialService.createCredential(credentialInfo);
            }else{
                result = credentialService.updateCredential(credentialInfo);
            }
            return "redirect:/result?isSuccess=true";
        }catch (Exception e){
            e.printStackTrace();
            return "redirect:/result?isSuccess=false&errorMessage=" + e.getMessage();
        }
    }

    @GetMapping("/credentials/delete")
    public String deleteFile(@RequestParam("id") Integer credentialId) {
        try {
            var result = 0;
            if (!Objects.isNull(credentialId)) {
                result = credentialService.deleteCredential(String.valueOf(credentialId));
            }
            if (result > 0) {
                return "redirect:/result?isSuccess=true";
            }
            return "redirect:/result?isSuccess=false&errorMessage=Changes Not Saved";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/result?isSuccess=false&errorMessage=" + e.getLocalizedMessage();
        }
    }
}
