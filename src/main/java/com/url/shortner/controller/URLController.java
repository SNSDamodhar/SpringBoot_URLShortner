package com.url.shortner.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.url.shortner.exception.URLValidationException;
import com.url.shortner.model.UrlEntity;
import com.url.shortner.service.URLService;

@RestController
@RequestMapping("/api/v1")
public class URLController {
	
	@Autowired
	private URLService urlService;
	
	@PostMapping("/urls/shorten")
	public ResponseEntity<String> createShortLink(@Valid @RequestBody UrlEntity urlEntity, HttpServletRequest request) throws URLValidationException {
		//To get the base url
		String baseUrl = ServletUriComponentsBuilder.fromRequestUri(request)
	            .replacePath(null)
	            .build()
	            .toUriString();
		
		String shortenLink = urlService.createShortenLink(urlEntity);
		shortenLink = baseUrl + "/" + shortenLink;
		
		return new ResponseEntity<String>(shortenLink, HttpStatus.CREATED);
	}

}
