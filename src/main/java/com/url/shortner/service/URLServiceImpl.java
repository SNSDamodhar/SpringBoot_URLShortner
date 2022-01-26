package com.url.shortner.service;

import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.url.shortner.exception.URLValidationException;
import com.url.shortner.model.UrlEntity;
import com.url.shortner.model.UrlShortenedEntity;
import com.url.shortner.repository.UrlShortenedRepository;
import com.url.shortner.utility.GlobalUtilities;

@Service
@Transactional(readOnly = true)
public class URLServiceImpl implements URLService {
	
	@Autowired
	private UrlShortenedRepository urlShortenedRepository;
	
	@Autowired
	private GlobalUtilities globalUtilities;

	@Override
	@Transactional
	public String createShortenLink(UrlEntity urlEntity) throws URLValidationException {
		
		globalUtilities.sanitizeRequest(urlEntity);
		
		List<String> errors = new ArrayList<String>();
		
		
		globalUtilities.validateUserInput(urlEntity, errors);
		
		if(errors.size() != 0) {
			throw new URLValidationException("Validation Error", errors);
		}
		
		String randomString = globalUtilities.getRandomCode();
		
		UrlShortenedEntity urlShortenedEntity = new UrlShortenedEntity();
		urlShortenedEntity.setShortenedURL(randomString);
		
		urlShortenedEntity.setUrlEntity(urlEntity);
		
		urlShortenedRepository.saveAndFlush(urlShortenedEntity);
		
		return randomString;
	}

}
