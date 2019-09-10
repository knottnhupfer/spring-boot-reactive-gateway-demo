package com.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.*;

import java.io.InputStream;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/internal/api")
public class DemoController {

	@GetMapping("/{value}/**")
	public String retrieveArtifacts(ServerHttpRequest request, @PathVariable("value") String value) {
		String path = request.getPath().toString();
		return "Calling GET successful, " + value + ", request is: " + path;
	}

	@PutMapping(value = "/{value}/**", consumes = "application/octet-stream")
	public String putArtifact(ServerHttpRequest request, ServerHttpResponse response, @PathVariable("value") String value) {
		System.out.println("Upload path is: " + request.getPath());

		request.getBody().flatMap(requestData -> {
			// TODO: read data here?
			response.setStatusCode(HttpStatus.ACCEPTED);
							InputStream inputStream = requestData.asInputStream();
			return null;
		});
		return "Called service";
	}

//	@PutMapping(value = "/{value}/**", consumes = "application/octet-stream")
//	public String putArtifact(@RequestPart FilePart file, @PathVariable("value") String value) {
//		return "Called service";
//	}

//	@PutMapping(value = "/{value}/**", consumes = "application/octet-stream")
//	public String putArtifact(@@RequestBody Mono<MultiValueMap<String, Part>> parts, @PathVariable("value") String value) {
//		Mono<Object> objectMono = parts.flatMap(this::readBuffer);
//		return "Called service";
//	}
}
