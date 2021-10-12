package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.SecureRandom;
import java.util.Base64;

@Controller
@RequestMapping("/home/credentials")
public class CredentialController {
    private CredentialService credentialService;
    private UserService userService;
    private EncryptionService encryptionService;

    public CredentialController(CredentialService credentialService, UserService userService, EncryptionService encryptionService) {
        this.credentialService = credentialService;
        this.userService = userService;
        this.encryptionService = encryptionService;
    }

    @PostMapping("/update")
    public String updateCredentials(@ModelAttribute Credential credential, Authentication authentication, RedirectAttributes redirectAttributes) {
        boolean dbUpdated;
        Integer userId = userService.getUser(authentication.getName()).getUserId();
        SecureRandom sRandom = new SecureRandom();
        byte[] key = new byte[16];
        String encodedKey;
        String encryptedPassword;

        sRandom.nextBytes(key);
        encodedKey = Base64.getEncoder().encodeToString(key);
        encryptedPassword = encryptionService.encryptValue(credential.getPassword(), encodedKey);

        credential.setKey(encodedKey);
        credential.setPassword(encryptedPassword);
        credential.setUserId(userId);

        try {
            if (credential.getCredentialId() == null) {
                credentialService.insertCredential(credential);
            } else {
                credentialService.updateCredential(credential);
            }

            redirectAttributes.addFlashAttribute("success", true);
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("success", false);
        }


        return "redirect:/result";
    }

    @GetMapping("/delete/{credentialId}")
    public String deleteCredential(@PathVariable Integer credentialId, RedirectAttributes redirectAttributes) {
        try {
            credentialService.deleteCredential(credentialId);
            redirectAttributes.addFlashAttribute("success", true);
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("success", false);
        }

        return "redirect:/result";
    }
}
