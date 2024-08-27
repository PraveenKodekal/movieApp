package com.movieapi.controller;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.movieapi.service.impl.FileServiceImpl;

import jakarta.servlet.http.HttpServletResponse;

@RestController("file")
public class FileController {
	@Autowired
	private final FileServiceImpl fileService;

	public FileController(FileServiceImpl fileService) {
		this.fileService = fileService;
	}

	@Value("${project.poster}")
	private String path;

	@PostMapping("/upload")
	public ResponseEntity<String> uploadFile(@RequestPart MultipartFile file) throws IOException {
		String uploadedFile = fileService.uploadFile(path, file);
		return ResponseEntity.ok("fileUploaded : " + uploadedFile);

	}

	@GetMapping("/{fileName}")
	public void serveFileHandler(@PathVariable String fileName, HttpServletResponse response) throws IOException {
		InputStream resourceFile = fileService.getResourceFile(path, fileName);
		response.setContentType(MediaType.IMAGE_PNG_VALUE);
		StreamUtils.copy(resourceFile, response.getOutputStream());
	}

}
