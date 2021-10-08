package com.udacity.jwdnd.course1.cloudstorage.controller;

import java.security.SecureRandom;
import java.util.Base64;

import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
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
    public String getHomePage(Model model, Authentication auth) {
        Integer userId = userService.getUser(auth.getName()).getUserId();

        model.addAttribute("noteList",	noteService.getNotes(userId));
        model.addAttribute("credentialList", credentialService.getCredentials(userId));
        model.addAttribute("fileList", fileService.getFiles(userId));
        model.addAttribute("encryptionService", encryptionService);

        return "home";
    }

    @GetMapping("/files/view/{fileId}")
    public ResponseEntity<Resource> getFile(@PathVariable Integer fileId){
        File file = fileService.getFile(fileId);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename='" + file.getFileName() + "'")
                .body(new ByteArrayResource(file.getFileData()));

    }

    @PostMapping("/files/upload")
    public String uploadFiles(@RequestParam("fileUpload") MultipartFile file, Authentication auth) {
        boolean dbUpdated;
        Integer userId = userService.getUser(auth.getName()).getUserId();
        String fileName = file.getOriginalFilename();

        try {
            if (!fileName.isEmpty() && !fileService.fileExists(fileName)) {
                fileService.insertFile(new File(0, fileName, file.getContentType(), file.getSize(), userId, file.getBytes()));
                dbUpdated = true;
            }
            else {
                dbUpdated = false;
            }

        } catch (Exception e) {
            dbUpdated = false;
        }

        return "redirect:/home/result?success=" + dbUpdated;
    }

    @GetMapping("/files/delete/{fileId}")
    public String deleteFile(@PathVariable int fileId)
    {
        boolean dbUpdated;

        try {
            fileService.deleteFile(fileId);
            dbUpdated = true;
        } catch (Exception e) {
            dbUpdated = false;
        }

        return "redirect:/home/result?success=" + dbUpdated;
    }

    @PostMapping("/notes/update")
    public String updateNotes(@ModelAttribute Note note, Authentication auth) {
        boolean dbUpdated;
        Integer userId = userService.getUser(auth.getName()).getUserId();

        note.setUserId(userId);

        try {
            if (note.getNoteId() == null) {
                noteService.insertNote(note);
            } else {
                noteService.updateNote(note);
            }

            dbUpdated = true;

        } catch (Exception e) {
            e.printStackTrace();
            dbUpdated = false;
        }

        return "redirect:/home/result?success=" + dbUpdated;
    }

    @GetMapping("/notes/delete/{noteId}")
    public String deleteNote(@PathVariable Integer noteId) {
        boolean dbUpdated;

        try {
            noteService.deleteNote(noteId);
            dbUpdated = true;
        } catch (Exception e) {
            e.printStackTrace();
            dbUpdated = false;
        }

        return "redirect:/home/result?success=" + dbUpdated;
    }

    @PostMapping("/credentials/update")
    public String updateCredentials(@ModelAttribute Credential credential, Authentication auth) {
        boolean dbUpdated;
        Integer userId = userService.getUser(auth.getName()).getUserId();
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
            if(credential.getCredentialId() == null) {
                credentialService.insertCredential(credential);
            }
            else {
                credentialService.updateCredential(credential);
            }

            dbUpdated = true;
        } catch (Exception e) {
            e.printStackTrace();
            dbUpdated = false;
        }


        return "redirect:/home/result?success=" + dbUpdated;
    }

    @GetMapping("/credentials/delete/{credentialId}")
    public String deleteCredential(@PathVariable Integer credentialId) {
        boolean dbUpdated;

        try {
            credentialService.deleteCredential(credentialId);
            dbUpdated = true;
        } catch (Exception e) {
            e.printStackTrace();
            dbUpdated = false;
        }

        return "redirect:/home/result?success=" + dbUpdated;
    }

}
