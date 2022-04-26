package com.stakhiyevich.openadboard.service.impl;

import com.stakhiyevich.openadboard.service.ItemService;
import com.stakhiyevich.openadboard.service.UploadService;
import jakarta.servlet.http.Part;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.Random;

public class UploadServiceImpl implements UploadService {

    private static final Logger logger = LogManager.getLogger();
    private static final Properties properties = new Properties();
    private static final String UPLOAD_PROPERTIES_FILE = "config/upload.properties";
    private static final String UPLOAD_DIRECTORY = "upload.directory";
    private static UploadService instance;

    static {
        try (InputStream inputStream = UploadServiceImpl.class.getClassLoader().getResourceAsStream(UPLOAD_PROPERTIES_FILE)) {
            properties.load(inputStream);
        } catch (IOException e) {
            logger.error("failed to load a properties file", e);
        }
    }

    public static UploadService getInstance() {
        if (instance == null) {
            instance = new UploadServiceImpl();
        }
        return instance;
    }

    @Override
    public Optional<String> uploadFile(List<Part> parts, String originalPictureName) {
        String uploadFolderPath = properties.getProperty(UPLOAD_DIRECTORY);
        String fileName = getRandomAlphanumericString() + originalPictureName;
        String path = uploadFolderPath + fileName;
        try (FileOutputStream fileOutputStream = new FileOutputStream(path)) {
            for (Part part : parts) {
                part.getInputStream().transferTo(fileOutputStream);
            }
        } catch (IOException e) {
            logger.error("failed to upload a picture", e);
        }
        return Optional.of(fileName);
    }

    @Override
    public boolean deleteFile(String name) {
        String path = properties.getProperty(UPLOAD_DIRECTORY) + name;
        try {
            return Files.deleteIfExists(Paths.get(path));
        } catch (IOException e) {
            logger.error("failed to delete a file {}", name, e);
            return false;
        }
    }

    @Override
    public boolean deleteFile(long itemId) {
        ItemService itemService = ItemServiceImpl.getInstance();
        String path = properties.getProperty(UPLOAD_DIRECTORY) + itemService.findItemById(itemId).get().getPicture();
        try {
            return Files.deleteIfExists(Paths.get(path));
        } catch (IOException e) {
            logger.error("failed to delete a file");
        }
        return false;
    }

    private String getRandomAlphanumericString() {
        int leftLimit = 48;
        int rightLimit = 122;
        int targetStringLength = 10;
        Random random = new Random();
        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
