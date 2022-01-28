package com.url.shortner.utility;

import java.net.URL;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.url.shortner.model.UrlEntity;
import com.url.shortner.service.URLServiceImpl;

@Component
public class GlobalUtilities {
	public static final Logger logger = LogManager.getLogger(GlobalUtilities.class);
	
	@Value("${app.properties.shorturl.length}")
	public int shortUrlLength;
	
	@Value("${app.properties.shorturl.characters}")
	public String shortUrlCharacters;
	
	public String getRandomCode() {
		String randomCode = "";
		
		String finalRandomCode = "";
		
		Random random = new Random();
		
		//Generate random string of given length
		for(int i = 0 ; i < shortUrlLength; i++) {
			int randomInt = random.nextInt(shortUrlCharacters.length());
	        char randomChar = shortUrlCharacters.charAt(randomInt);
	        randomCode += Character.toString(randomChar);
		}
		
		//Randomize the generated string from previous iteration
		for(int i = 0 ; i < randomCode.length(); i++) {
			int randomInt = random.nextInt(randomCode.length());
			char randomChar = randomCode.charAt(randomInt);
			finalRandomCode += Character.toString(randomChar);
		}
		
		return finalRandomCode;
	}
	
	public void validateUserInput(UrlEntity urlEntity, List<String> errors) {
		//Validate Orginal URL
		if(null == urlEntity.getOrginalUrl() || null == urlEntity.getOrginalUrl().trim() || urlEntity.getOrginalUrl().trim().length() == 0) {
			logger.error("Validation Error: " + URLConstants.NO_URL);
			errors.add(URLConstants.NO_URL);
		} else if(!(urlEntity.getOrginalUrl().startsWith("https://") || urlEntity.getOrginalUrl().startsWith("http://"))) {
			logger.error("Validation Error: " + URLConstants.INVALID_URL + ". Provide 'http' or 'https' for URL");
			errors.add(URLConstants.INVALID_URL + ". Provide 'http' or 'https' for URL");
		} else if(!isValidURL(urlEntity.getOrginalUrl())) {
			logger.error("Validation Error: " + URLConstants.INVALID_URL);
			errors.add(URLConstants.INVALID_URL);
		}
		
		//Validate validity of URL
		if(urlEntity.getSecondsOfValdity() <= 0 && null == urlEntity.getDateOfValidity()) {
			logger.error("Validation Error: " + URLConstants.INVALID_VALIDITY);
			errors.add(URLConstants.INVALID_VALIDITY);
		} else if(null != urlEntity.getDateOfValidity() && new Date().compareTo(urlEntity.getDateOfValidity()) >= 0) {
			logger.error("Validation Error: " + URLConstants.INVALID_DATE_VALIDITY);
			errors.add(URLConstants.INVALID_DATE_VALIDITY);
		}
	}
	
	
	public void sanitizeRequest(UrlEntity urlEntity) {
		urlEntity.setOrginalUrl(urlEntity.getOrginalUrl().trim());
	}
	
	protected Boolean isValidURL(String url) {
		try {
            new URL(url).toURI();
            return true;
        } catch (Exception e) {
            return false;
        }
	}

}
