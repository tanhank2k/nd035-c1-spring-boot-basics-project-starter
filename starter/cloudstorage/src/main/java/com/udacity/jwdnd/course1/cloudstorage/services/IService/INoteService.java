package com.udacity.jwdnd.course1.cloudstorage.services.IService;

import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.viewModels.NoteInfo;
import com.udacity.jwdnd.course1.cloudstorage.viewModels.UserSignupInfo;
import org.springframework.stereotype.Service;

import java.util.List;

public interface INoteService {
//    public boolean isNoteExists(String username);
    public Integer createNote(NoteInfo note);
    public Note getNote(String noteId);
    public List<Note> getNotes(List<String> noteIds);
    public List<NoteInfo> getNotesByUser(String userId);
    public Integer updateNote(NoteInfo noteInfo);
    public Integer deleteNote(String noteId);
}
