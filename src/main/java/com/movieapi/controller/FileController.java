 package com.movieapi.controller;

import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.movieapi.service.impl.FileServiceImpl;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/file/")
public class FileController {
	
	private final FileServiceImpl fileService;
	private Logger log= LoggerFactory.getLogger(FileController.class);

	public FileController(FileServiceImpl fileService) {
		this.fileService = fileService;
	}

	@Value("${project.poster}")
	private String path;

	@PostMapping("/upload")
	public ResponseEntity<String> uploadFile(@RequestPart MultipartFile file) throws IOException {
		log.info("upload File Handler Function()", FileController.class);
		String uploadedFile = fileService.uploadFile(path, file);
		return ResponseEntity.ok("fileUploaded : " + uploadedFile);

	}

	@GetMapping("/{fileName}")
	public void serveFileHandler(@PathVariable String fileName, HttpServletResponse response) throws IOException {
		log.info("serve File Handler function()", FileController.class);
		InputStream resourceFile = fileService.getResourceFile(path, fileName);
		response.setContentType(MediaType.IMAGE_PNG_VALUE);
		StreamUtils.copy(resourceFile, response.getOutputStream());
	}

}
