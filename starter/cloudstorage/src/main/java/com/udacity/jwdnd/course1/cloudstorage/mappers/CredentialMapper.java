package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import com.udacity.jwdnd.course1.cloudstorage.models.File;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {
    @Select("SELECT * FROM CREDENTIALS WHERE credentialid = #{credentialId}")
    Credential getCredential(String credentialId);

    @Insert("INSERT INTO CREDENTIALS (url, username, password, key, userid) VALUES (#{Url}, #{UserName}, #{Password}, #{Key},#{UserId})")
//    @Options(useGeneratedKeys = true, keyProperty = "credentialid")
    int insertCredential(Credential credential);

    List<Credential> getCredentials(List<String> credentialIds);
    @Select("SELECT * FROM CREDENTIALS WHERE userid = #{userId}")
    List<Credential> getCredentialsByUser(String userId);

    @Insert("UPDATE CREDENTIALS SET url = #{Url}, username = #{UserName}, password = #{Password}, userid = #{UserId}")
    int updateCredential(Credential credentialModel);
    @Delete("DELETE FROM CREDENTIALS WHERE credentialid=#{credentialId}")
    int deleteCredential(String credentialId);
}
