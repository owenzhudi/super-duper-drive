package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;

@Controller
@RequestMapping("/home")
public class HomeController {

    NoteService noteService;
    CredentialService credentialService;
    FileService fileService;
    UserService userService;
    EncryptionService encryptionService;

    public HomeController(NoteService noteService, CredentialService credentialService,
                          FileService fileService, UserService userService, EncryptionService encryptionService) {
        this.noteService = noteService;
        this.credentialService = credentialService;
        this.fileService = fileService;
        this.userService = userService;
        this.encryptionService = encryptionService;
    }

    @GetMapping()
    public String getHomePage(Model model, Authentication authentication) {
        Integer userId = userService.getUser(authentication.getName()).getUserId();

        model.addAttribute("noteList",	noteService.getNotes(userId));
        model.addAttribute("credentialList", credentialService.getCredentials(userId));
        model.addAttribute("fileList", fileService.getFilesByUserId(userId));
        model.addAttribute("encryptionService", encryptionService);

        return "home";
    }
}
