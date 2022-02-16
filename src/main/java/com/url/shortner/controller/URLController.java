package com.url.shortner.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.url.shortner.VO.URLEntityVO;
import com.url.shortner.exception.URLValidationException;
import com.url.shortner.model.UrlEntity;
import com.url.shortner.model.UrlShortenedEntity;
import com.url.shortner.service.URLService;

@RestController
@RequestMapping("/api/v1")
public class URLController {
	
	public static Logger logger = LogManager.getLogger(URLController.class);
	
	@Autowired
	private URLService urlService;
	
	@PostMapping("/urls/shorten")
	public ResponseEntity<Object> createShortLink(@Valid @RequestBody URLEntityVO urlEntityVO, HttpServletRequest request) throws Exception {
		logger.info("Controller Class :: Request Payload: " + urlEntityVO.toString());
		
		//To get the base url
		String baseUrl = ServletUriComponentsBuilder.fromRequestUri(request)
	            .replacePath(null)
	            .build()
	            .toUriString();
		
		logger.info("Controller Class :: calling createShortenLink service");
		UrlEntity entity = urlService.createShortenLink(urlEntityVO);
		entity.getUrlShortenedEntity().setShortenedURL(baseUrl + "/" + entity.getUrlShortenedEntity().getShortenedURL());
		
		return new ResponseEntity<>(entity.getUrlShortenedEntity(), HttpStatus.CREATED);
	}

}
