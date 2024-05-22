package com.utcn.StackOverflow.request;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public class UploadImageRequest {
    private MultipartFile file;

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
