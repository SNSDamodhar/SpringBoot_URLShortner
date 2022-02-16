package com.url.shortner.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.url.shortner.VO.URLEntityVO;
import com.url.shortner.exception.URLValidationException;
import com.url.shortner.model.UrlEntity;
import com.url.shortner.model.UrlShortenedEntity;
import com.url.shortner.repository.URLRepository;
import com.url.shortner.repository.UrlShortenedRepository;
import com.url.shortner.utility.BuildURLEntityFromVO;
import com.url.shortner.utility.GlobalUtilities;
import com.url.shortner.utility.URLConstants;

@Service
@Transactional(readOnly = true)
public class URLServiceImpl implements URLService {
	public static final Logger logger = LogManager.getLogger(URLServiceImpl.class);
	
	@Autowired
	private UrlShortenedRepository urlShortenedRepository;
	
	@Autowired
	private URLRepository urlRepository;
	
	@Autowired
	private GlobalUtilities globalUtilities;
	
	@Autowired
	private BuildURLEntityFromVO buildURLEntityFromVO;

	@Override
	@Transactional
	public UrlEntity createShortenLink(URLEntityVO urlEntityVO) throws Exception {
		List<String> errors = new ArrayList<String>();
		
		//Convert VO object to Entity object
		UrlEntity urlEntity = buildURLEntityFromVO.convert(urlEntityVO, errors); 
		if(errors.size() != 0) {
			logger.error("Throwing Validation Error");
			throw new URLValidationException("Validation Error", errors);
		}
		
		globalUtilities.validateUserInput(urlEntity, errors);
		if(errors.size() != 0) {
			logger.error("Throwing Validation Error");
			throw new URLValidationException("Validation Error", errors);
		}
		errors.clear();
		
		Boolean isProceedToSave = isProceedToSave(urlEntity, errors);
		if(isProceedToSave) {
			try {
				urlEntity = urlRepository.saveAndFlush(urlEntity);
			} catch(Exception e) {
				logger.error("UrlEntity saving error ::", e);
				throw new Exception(URLConstants.GENERAL_EXCEPTION_MESSAGE);
			}
		} else {
			throw new URLValidationException("Validation Error", errors);
		}
		
		return urlEntity;
	}
	
	private Boolean isProceedToSave(UrlEntity entity, List<String> errors) {
		if(null != entity.getUrlShortenedEntity().getShortenedURL()) {
			Boolean isUnique = isUniqueCode(entity.getUrlShortenedEntity().getShortenedURL());
			if(!isUnique) {
				errors.add(URLConstants.DUPLICATE_REQUESTED_URL);
				return false;
			}
			return true;
		}
		String randomString = globalUtilities.getRandomCodeNew();
		while(true) {
			if(isUniqueCode(randomString)) {
				break;
			} else
				randomString = globalUtilities.getRandomCodeNew();
		}
		entity.getUrlShortenedEntity().setShortenedURL(randomString);
		return true;
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
