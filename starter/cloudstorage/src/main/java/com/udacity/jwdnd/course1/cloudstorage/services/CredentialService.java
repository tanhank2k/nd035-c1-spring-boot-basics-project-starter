package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import com.udacity.jwdnd.course1.cloudstorage.services.IService.ICredentialService;
import com.udacity.jwdnd.course1.cloudstorage.viewModels.CredentialInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CredentialService implements ICredentialService {

    @Autowired
    private CredentialMapper  credentialMapper;
    @Autowired
    private EncryptionService encryptionService;

    private void encryptPassword(Credential credential) {
        String encodedKey = credential.getKey();
        if (Objects.isNull(encodedKey) || encodedKey.isEmpty()){
            SecureRandom random = new SecureRandom();
            byte[] key = new byte[16];
            random.nextBytes(key);
            encodedKey = Base64.getEncoder().encodeToString(key);
            credential.setKey(encodedKey);
        }

        String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), encodedKey);
        credential.setPassword(encryptedPassword);
    }

    private void decryptPassword(Credential credential) {
        String decryptedPassword = encryptionService.decryptValue(credential.getPassword(), credential.getKey());
        credential.setPassword(decryptedPassword);
    }

    /**
     * @param credentialInfo
     * @return
     */
    @Override
    public Integer createCredential(CredentialInfo credentialInfo) {
        Credential credential = Credential.builder()
                .Url(credentialInfo.getUrl())
                .UserId(credentialInfo.getUserId())
                .Password(credentialInfo.getPassword())
                .UserName(credentialInfo.getUsername())
                .build();
        encryptPassword(credential);
        var result = credentialMapper.insertCredential(credential);
        return result;
    }

    /**
     * @param credentialId
     * @return
     */
    @Override
    public Credential getCredential(String credentialId) {
        return null;
    }

    /**
     * @param credentialIds
     * @return
     */
    @Override
    public List<Credential> getCredentials(List<String> credentialIds) {
        return null;
    }

    /**
     * @param userId
     * @return
     */
    @Override
    public List<CredentialInfo> getCredentialsByUser(String userId) {
        var credentials = credentialMapper.getCredentialsByUser(userId);
        var result = credentials.stream().map(item ->{
            CredentialInfo credentialInfo = new CredentialInfo();
            credentialInfo.setCredentialId(item.getCredentialId());
            credentialInfo.setKey(item.getKey());
            credentialInfo.setUserId(item.getUserId());
            credentialInfo.setUrl(item.getUrl());
            credentialInfo.setUsername(item.getUserName());
            credentialInfo.setEncryptedPassword(item.getPassword());
            decryptPassword(item);
            credentialInfo.setPassword(item.getPassword());
            return credentialInfo;
        });
        return result.collect(Collectors.toList());
    }

    /**
     * @param credential
     * @return
     */
    @Override
    public Integer updateCredential(CredentialInfo credentialInfo) {
        var credentialOld = credentialMapper.getCredential(String.valueOf(credentialInfo.getCredentialId()));
        Credential credentialNew = Credential.builder()
                .CredentialId(credentialOld.getCredentialId())
                .Url(credentialInfo.getUrl())
                .UserId(credentialInfo.getUserId())
                .Password(credentialInfo.getPassword())
                .UserName(credentialInfo.getUsername())
                .Key(credentialOld.getKey())
                .build();
        encryptPassword(credentialNew);
        var result = credentialMapper.updateCredential(credentialNew);
        return result;
    }

    /**
     * @param credentialId
     * @return
     */
    @Override
    public Integer deleteCredential(String credentialId) {
        var result = credentialMapper.deleteCredential(credentialId);
        return result;
    }
}
