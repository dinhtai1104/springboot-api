package com.springboot.api.services;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public class ImageStorageServices implements IStorageService{
    private final Path storageFolder = Paths.get("upload");
    public ImageStorageServices() {
        try {
            Files.createDirectories(storageFolder);
        }
        catch (Exception e) {

        }
    }

    private boolean isImageFile(MultipartFile file) {
        String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
        return Arrays.asList(new String[] {"png", "jpg", "jpeg", "bmp"}).contains(fileExtension.toLowerCase().trim());
    }

    @Override
    public String storeFile(MultipartFile file) {
        try {
            System.out.println("haha");
            if (file.isEmpty()) {
                throw new RuntimeException("Failed to store empty file.");
            }
            if (!isImageFile(file)) {
                throw new RuntimeException("Failed to store not image file.");
            }
            var fileSizeInMegabytes = file.getSize()/1_000_000;
            if (fileSizeInMegabytes >= 5.0f) {
                throw new RuntimeException("Failed to store 5MB image file.");
            }
            // change file name
            String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
            String generatedFilename = UUID.randomUUID().toString().replace("-","");
            generatedFilename = generatedFilename + "." + fileExtension;

            Path destinationFilePath = this.storageFolder.resolve(
                    Paths.get(generatedFilename)
            ).normalize().toAbsolutePath();

            if (!destinationFilePath.getParent().equals(this.storageFolder.toAbsolutePath()))
            {
                throw new RuntimeException("Cannot store file outside current directory.");
            }
            try(InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFilePath, StandardCopyOption.REPLACE_EXISTING);
            }
            return generatedFilename;
        }
        catch (Exception e) {
            throw new RuntimeException("Failed to store file.");
        }
    }

    @Override
    public Stream<Path> loadAll() {
        return null;
    }

    @Override
    public byte[] readFileContent(String fileName) {
        return new byte[0];
    }

    @Override
    public void DeleteAllFiles() {

    }

    @Override
    public void DeleteFile(String fileName) {

    }
}
