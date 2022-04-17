package com.stakhiyevich.openadboard.service;

import jakarta.servlet.http.Part;

import java.util.List;
import java.util.Optional;

public interface UploadService {
    Optional<String> uploadFile(List<Part> parts, String fileName);

    boolean deleteFile(String name);

    boolean deleteFile(long id);
}
