package com.udacity.jwdnd.course1.cloudstorage.services.IService;

import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.viewModels.CredentialInfo;
import com.udacity.jwdnd.course1.cloudstorage.viewModels.NoteInfo;

import java.util.List;

public interface ICredentialService {
    public Integer createCredential(CredentialInfo credential);
    public Credential getCredential(String credentialId);
    public List<Credential> getCredentials(List<String> credentialIds);
    public List<CredentialInfo> getCredentialsByUser(String userId);
    public Integer updateCredential(CredentialInfo credential);
    public Integer deleteCredential(String credentialId);
}
