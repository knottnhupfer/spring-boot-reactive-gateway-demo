package com.demo.controller;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/internal/api")
public class DemoController {

	@GetMapping("/{value}/**")
	public String retrieveArtifacts(ServerHttpRequest request, @PathVariable("value") String value) {
		String path = request.getPath().toString();
		return "Calling GET successful, " + value + ", request is: " + path;
	}

	@PutMapping(value = "/{value}/**", consumes = "application/octet-stream")
	public  Mono<ResponseEntity>  putArtifact(ServerHttpRequest request, @RequestBody ByteArrayResource resource, @PathVariable("value") String value) {
		System.out.println("Upload path is (#2): " + request.getPath());
		File file = new File(request.getPath().toString());
		System.out.println("Uploaded file is (#1): " + file.getName());

		writeFileToFileSystem(resource, file);
		return Mono.just(ResponseEntity.ok().build());
	}

	private void writeFileToFileSystem(@RequestBody ByteArrayResource resource, File file) {
		try {
			String outFileName = "storage/" + file.getName();
			InputStream is = resource.getInputStream();
			FileOutputStream out = new FileOutputStream(outFileName);
			StreamUtils.copy(is, out);
			is.close();
			out.close();
			System.out.println(String.format("File '%s' successfully written.", outFileName));
		} catch (IOException e) {
			throw new IllegalStateException("Error while reading and writing file.");
		}
	}
}
