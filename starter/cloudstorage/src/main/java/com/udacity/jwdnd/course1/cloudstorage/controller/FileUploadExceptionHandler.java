package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

// Get idea from: https://knowledge.udacity.com/questions/778627
// https://github.com/alimuhammadca/cloudstorage/blob/main/src/main/java/com/udacity/jwdnd/course1/cloudstorage/controller/FileUploadExceptionController.java
@ControllerAdvice
public class FileUploadExceptionHandler {
    @ExceptionHandler(value = MaxUploadSizeExceededException.class)
    public String handleFileSizeLimitExceedException(RedirectAttributes redirectAttributes) {
        System.out.println("File exceed size");
        redirectAttributes.addFlashAttribute("success", false);
        redirectAttributes.addFlashAttribute("errorMessage", "File size exceed limit!");
        return "redirect:/result";
    }
}
