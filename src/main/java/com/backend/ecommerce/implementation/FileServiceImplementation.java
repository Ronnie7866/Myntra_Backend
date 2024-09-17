package com.backend.ecommerce.implementation;

import com.backend.ecommerce.service.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileServiceImplementation implements FileService {

    @Override
    public String uploadFile(String path, MultipartFile file) throws IOException {
        // get name of the file
        String originalFilename = file.getOriginalFilename();

        // generating random name pic
        String randomID = UUID.randomUUID().toString();
        String fileName1 = randomID.concat(originalFilename.substring(originalFilename.lastIndexOf(".")));
        System.out.println("----------------------------------------------------------------------------------------------------------------------");
        System.out.println(fileName1);

        // get the file Path
        String filePath = path + File.separator + fileName1;

        // crete file object
        File f = new File(path);
        if (!f.exists()) {
            f.mkdir();
        }

        // copy the file or upload the file to the path
        Files.copy(file.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
        return fileName1;
    }

    @Override
    public InputStream getResourceFile(String path, String fileName) throws FileNotFoundException {
        String filePath = path + File.separator + fileName;
        return new FileInputStream(filePath);
    }
}
