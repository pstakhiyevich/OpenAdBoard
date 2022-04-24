package com.stakhiyevich.openadboard.service;

import jakarta.servlet.http.Part;

import java.util.List;
import java.util.Optional;

/**
 * The Upload service interface.
 */
public interface UploadService {
    /**
     * Uploads a file with specified file parts and a name.
     *
     * @param parts a list of parts that was received within a multipart/from-data POST request
     * @param fileName a file's name
     * @return an optional object of an uploaded file name
     */
    Optional<String> uploadFile(List<Part> parts, String fileName);

    /**
     * Deletes a file with specified name.
     *
     * @param name a file's name
     * @return whether a file was successfully deleted
     */
    boolean deleteFile(String name);

    /**
     * Deletes a file with specified entity id that file belongs to
     *
     * @param id entity's id that file belongs to
     * @return whether a file was successfully deleted
     */
    boolean deleteFile(long id);
}
