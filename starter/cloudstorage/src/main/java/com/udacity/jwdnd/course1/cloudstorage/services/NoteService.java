package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.mappers.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.IService.INoteService;
import com.udacity.jwdnd.course1.cloudstorage.viewModels.NoteInfo;
import com.udacity.jwdnd.course1.cloudstorage.viewModels.UserSignupInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class NoteService implements INoteService {
    @Autowired
    private NoteMapper noteMapper;

    @Override
    public Integer createNote(NoteInfo noteInfo) {
        Note note = Note.builder()
                .NoteTitle(noteInfo.getNoteTitle())
                .NoteDescription(noteInfo.getNoteDescription())
                .UserId(noteInfo.getUserId())
                .build();
        int result = noteMapper.insertNote(note);
        return result;
    }

    @Override
    public Note getNote(String noteId) {
        return null;
    }

    @Override
    public List<Note> getNotes(List<String> noteIds) {
        return null;
    }

    @Override
    public List<NoteInfo> getNotesByUser(String userId) {
        List<Note> notes = noteMapper.getNotesByUser(userId);
        var noteInforms = notes.stream().map(note -> {
                    NoteInfo noteInfo = new NoteInfo();
                    noteInfo.setNoteId(note.getNoteId());
                    noteInfo.setNoteTitle(note.getNoteTitle());
                    noteInfo.setNoteDescription(note.getNoteDescription());
                    noteInfo.setUserId(note.getUserId());
                    return noteInfo;
                }
        );
        return noteInforms.collect(Collectors.toList());
    }

    @Override
    public Integer updateNote(NoteInfo noteInfo) {
        Note note = Note.builder()
                .NoteId(Integer.valueOf(noteInfo.getNoteId()))
                .NoteTitle(noteInfo.getNoteTitle())
                .NoteDescription(noteInfo.getNoteDescription())
                .UserId(noteInfo.getUserId())
                .build();
        int result = noteMapper.updateNote(note);
        return result;
    }

    @Override
    public Integer deleteNote(String noteId) {
        int result = noteMapper.deleteNote(Integer.parseInt(noteId));
        return result;
    }
}
