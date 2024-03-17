package com.udacity.jwdnd.course1.cloudstorage.viewModels;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoteInfo {
    private Integer noteId;
    private String noteTitle;
    private String noteDescription;
    private Integer userId;
}
