package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileService {
    private FileMapper fileMapper;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    public Integer insertFile(File file) {
        return fileMapper.insertFile(file);
    }

    public List<File> getFilesByUserId(Integer userId){
        return fileMapper.getFilesByUserId(userId);
    }

    public File getFile(Integer fileId) {
        return fileMapper.getFile(fileId);
    }

    public boolean fileExists(String fileName) {
        return fileMapper.fileExists(fileName);
    }

    public boolean updateFile(File file) {
        return fileMapper.updateFile(file);
    }

    public boolean deleteFile(Integer fileId) {
        return fileMapper.deleteFile(fileId);
    }
}
