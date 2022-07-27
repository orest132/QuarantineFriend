package me.kollcaku.QuarantineFriends.controller;

import me.kollcaku.QuarantineFriends.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static me.kollcaku.QuarantineFriends.utility.SecurityConstant.ANGULAR_CROSS_ORIGIN;
import static me.kollcaku.QuarantineFriends.utility.SecurityConstant.REQUEST_MAPPING;

@RestController
@CrossOrigin(ANGULAR_CROSS_ORIGIN)
@RequestMapping(REQUEST_MAPPING)
public class FileController {
    private final FileService fileService;
    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/{id}/upload-image")
    public ResponseEntity<String> uploadFile(@PathVariable Long id, @RequestParam("file") MultipartFile file) throws IOException {
        fileService.save(id, file);
        return ResponseEntity.status(HttpStatus.OK)
                .body("File uploaded successfully");
    }
}
