package com.url.shortner.service;

import com.url.shortner.exception.URLValidationException;
import com.url.shortner.model.UrlEntity;

public interface URLService {
	
	String createShortenLink(UrlEntity urlEntity) throws URLValidationException;

}
