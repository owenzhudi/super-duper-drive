package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/home/files")
public class FileController {
    private FileService fileService;
    private UserService userService;

    public FileController(FileService fileService, UserService userService) {
        this.fileService = fileService;
        this.userService = userService;
    }

    @GetMapping("/{fileId}")
    // get the helper code for downloading files
    // from: https://github.com/rrbiz662/super-duper-drive/blob/c4cbf33d6e79503e53337578ff9ee10799a6aa11/starter/cloudstorage/src/main/java/com/udacity/jwdnd/course1/cloudstorage/controller/HomeController.java#L64
    public ResponseEntity<Resource> downloadFile(@PathVariable Integer fileId){
        File file = fileService.getFile(fileId);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename='" + file.getFileName() + "'")
                .body(new ByteArrayResource(file.getFileData()));

    }

    @PostMapping()
    public String uploadFiles(@RequestParam("fileUpload") MultipartFile file, Authentication auth, RedirectAttributes redirectAttributes) {
        Integer userId = userService.getUser(auth.getName()).getUserId();
        String fileName = file.getOriginalFilename();

        if (file.isEmpty() || fileName.isEmpty()) {
            redirectAttributes.addFlashAttribute("success", false);
            redirectAttributes.addFlashAttribute("errorMessage", "File or file name is empty!");
            return "redirect:/result";
        }

        if (fileService.fileExists(fileName)) {
            redirectAttributes.addFlashAttribute("success", false);
            redirectAttributes.addFlashAttribute("errorMessage", "File already exists!");
            return "redirect:/result";
        }

        try {
            fileService.insertFile(new File(0, fileName, file.getContentType(), file.getSize(), userId, file.getBytes()));
            redirectAttributes.addFlashAttribute("success", true);

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("success", false);
            redirectAttributes.addFlashAttribute("errorMessage", "Error uploading file!");
        }

        return "redirect:/result";
    }

    @GetMapping("/delete/{fileId}")
    public String deleteFile(@PathVariable Integer fileId, RedirectAttributes redirectAttributes)
    {
        try {
            fileService.deleteFile(fileId);
            redirectAttributes.addFlashAttribute("success", true);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("success", false);
            redirectAttributes.addFlashAttribute("errorMessage", "Error deleting file!");
        }

        return "redirect:/result";
    }
}
