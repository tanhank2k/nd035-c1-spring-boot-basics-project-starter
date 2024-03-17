package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.File;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {
    @Select("SELECT * FROM FILES WHERE fileId = #{fileId}")
    File getFile(String fileId);
    @Select("SELECT * FROM FILES WHERE userid = #{userId}")
    List<File> getFilesByUser(String userId);
    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata) VALUES ( #{FileName}, #{ContentType}, #{FileSize}, #{UserId}, #{FileData})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int insertFile(File fileModel);

    @Delete("DELETE FROM FILES WHERE fileId=#{fileId}")
    int deleteFile(int fileId);
}
