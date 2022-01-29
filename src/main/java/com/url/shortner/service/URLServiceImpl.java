package com.url.shortner.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.url.shortner.exception.URLValidationException;
import com.url.shortner.model.UrlEntity;
import com.url.shortner.model.UrlShortenedEntity;
import com.url.shortner.repository.UrlShortenedRepository;
import com.url.shortner.utility.GlobalUtilities;

@Service
@Transactional(readOnly = true)
public class URLServiceImpl implements URLService {
	public static final Logger logger = LogManager.getLogger(URLServiceImpl.class);
	
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
			logger.error("Throwing Validation Error");
			throw new URLValidationException("Validation Error", errors);
		}
		
		String randomString = globalUtilities.getRandomCodeNew();
		while(true) {
			if(isUniqueCode(randomString))
				break;
			else
				randomString = globalUtilities.getRandomCodeNew();
		}
		logger.info("Generated Random String: " + randomString);
		
		UrlShortenedEntity urlShortenedEntity = new UrlShortenedEntity();
		urlShortenedEntity.setShortenedURL(randomString);
		urlShortenedEntity.setUrlEntity(urlEntity);
		
		logger.info("Saving URLShortenedEntity: " + urlShortenedEntity.toString());
		urlShortenedRepository.saveAndFlush(urlShortenedEntity);
		
		return randomString;
	}
	
	private Boolean isUniqueCode(String code) {
		UrlShortenedEntity temp = urlShortenedRepository.findByShortenedURL(code);
		if(null != temp) {
			System.out.println("if condition");
			return false;
		}
		return true;
	}

}
