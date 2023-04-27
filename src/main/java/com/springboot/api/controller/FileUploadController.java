package com.springboot.api.controller;

import com.springboot.api.model.ResponseObject;
import com.springboot.api.services.IStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping(path = "/api/v1/FileUpload")
public class FileUploadController {
    //Inject Store
    @Autowired
    private IStorageService storeService;
    @PostMapping("")
    public ResponseEntity<ResponseObject> uploadFile(@RequestParam("file")MultipartFile file) {
        try {
            // save files
            String generatedFileName = storeService.storeFile(file);

            return ResponseEntity.status(HttpStatus.OK).body(
              new ResponseObject("ok","upload file successfully", generatedFileName)
            );
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject("fail",e.getMessage(), "")
            );
        }
    }
}
