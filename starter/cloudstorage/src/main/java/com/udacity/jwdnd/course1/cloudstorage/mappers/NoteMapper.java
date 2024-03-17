package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {
    @Select("SELECT * FROM NOTES WHERE noteid = #{nodeId}")
    Note getNote(String nodeId);

    @Select("SELECT * FROM NOTES WHERE userid = #{userId}")
    List<Note> getNotesByUser(String userId);

    @Insert("INSERT INTO NOTES (notetitle, notedescription, userid) VALUES ( #{NoteTitle}, #{NoteDescription},#{UserId})")
//    @Options(useGeneratedKeys = true, keyProperty = "noteid")
    int insertNote(Note noteModel);

    @Update("UPDATE NOTES SET notetitle = #{NoteTitle}, notedescription = #{NoteDescription} WHERE noteid = #{NoteId} ")
    int updateNote(Note noteModel);

    @Delete("DELETE FROM NOTES WHERE noteid = #{noteId} ")
    int deleteNote(int noteId);

}
