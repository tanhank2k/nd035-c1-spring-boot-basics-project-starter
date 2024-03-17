package com.udacity.jwdnd.course1.cloudstorage.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Note {
    private Integer NoteId;
    private String NoteTitle;
    private String NoteDescription;
    private Integer UserId;
}
