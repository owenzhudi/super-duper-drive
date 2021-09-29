package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CredentialService {
    private CredentialMapper credentialMapper;

    public CredentialService(CredentialMapper credentialMapper) {
        this.credentialMapper = credentialMapper;
    }

    public Integer insertCredential(Credential credential) {
        return credentialMapper.insertCredential(credential);
    }

    public List<Credential> getCredentials(Integer userId) {
        return credentialMapper.getCredentials(userId);
    }

    public boolean updateCredential(Credential credential) {
        return credentialMapper.updateCredential(credential);
    }

    public boolean deleteCredential(Integer credentialId) {
        return credentialMapper.deleteCredential(credentialId);
    }
}
