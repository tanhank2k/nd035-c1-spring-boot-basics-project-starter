package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.IService.INoteService;
import com.udacity.jwdnd.course1.cloudstorage.viewModels.NoteInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Objects;

@Controller
public class NoteController {
    @Autowired
    private INoteService noteService;

    @PostMapping("/notes")
    public String insertNote(@ModelAttribute NoteInfo noteInfo, Authentication authentication){
        try {
            User user = (User) authentication.getPrincipal();
            noteInfo.setUserId(user.getUserId());
            var result = 0;
            if (Objects.isNull(noteInfo.getNoteId())){
                result = noteService.createNote(noteInfo);
            }else{
                result = noteService.updateNote(noteInfo);
            }
            return"redirect:/result?isSuccess=" +  (result > 0 ? "true" : "false");
        }catch (Exception e){
            e.printStackTrace();
            return "redirect:/result?isSuccess=false&errorMessage=" + e.getMessage();
        }
    }

    @GetMapping("/notes/delete")
    public String deleteFile(@RequestParam("id") Integer noteId) {
        try {
            var result = 0;
            if (!Objects.isNull(noteId)) {
                result = noteService.deleteNote(String.valueOf(noteId));
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
