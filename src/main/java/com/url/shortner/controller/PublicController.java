package com.url.shortner.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.url.shortner.exception.URLRedirectionException;
import com.url.shortner.service.URLShortenService;


@Controller
public class PublicController {
	
	@Autowired
	private URLShortenService urlShortenService;

	@GetMapping(value = "/{shortID}")
	 public ResponseEntity<Void> redirect(@PathVariable("shortID") String shortID) throws URLRedirectionException {
		String orginalURL = "";
		orginalURL = urlShortenService.getOrginalUrl(shortID);
		return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(orginalURL)).build();
	 }
}
