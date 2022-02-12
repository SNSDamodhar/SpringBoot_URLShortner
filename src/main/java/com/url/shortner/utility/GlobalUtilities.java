package com.url.shortner.utility;

import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.url.shortner.model.UrlEntity;

@Component
public class GlobalUtilities {
	public static final Logger logger = LogManager.getLogger(GlobalUtilities.class);
	
	@Value("${app.properties.shorturl.length}")
	public int shortUrlLength;
	
	@Value("${app.properties.shorturl.characters}")
	public String shortUrlCharacters;
	
	@Autowired
	private DateUtilities dateUtilities;
	
	@Deprecated
	public String getRandomCode() {
		String randomCode = "";
		
		Random random = new Random();
		
		//Generate random string of given length
		for(int i = 0 ; i < shortUrlLength; i++) {
			int randomInt = random.nextInt(shortUrlCharacters.length());
	        char randomChar = shortUrlCharacters.charAt(randomInt);
	        randomCode += Character.toString(randomChar);
		}
		
		randomCode = randomizeString(randomCode);
		
		return randomCode;
	}
	
	public String getRandomCodeNew() {
		int leftLimit = 48; // numeral '0'
	    int rightLimit = 122; // letter 'z'
	    Random random = new Random();

	    String generatedString = random.ints(leftLimit, rightLimit + 1)
	      .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
	      .limit(shortUrlLength)
	      .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
	      .toString();
	    
	    generatedString = new String(randomizeString(generatedString));
	    
	    return generatedString;
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
		} else if(null != urlEntity.getDateOfValidity() && dateUtilities.getUTCTimeNow().compareTo(urlEntity.getDateOfValidity()) >= 0) {
			logger.error("Validation Error: " + URLConstants.INVALID_DATE_VALIDITY);
			errors.add(URLConstants.INVALID_DATE_VALIDITY);
		}
	}
	
	
	public void sanitizeRequest(UrlEntity urlEntity) {
		urlEntity.setOrginalUrl(urlEntity.getOrginalUrl().trim());
	}
	
	private Boolean isValidURL(String url) {
		try {
            new URL(url).toURI();
            return true;
        } catch (Exception e) {
            return false;
        }
	}
	
	private String randomizeString(String string) {
		List<String> letters = Arrays.asList(string.split(""));
		Collections.shuffle(letters);
		Collections.shuffle(letters);
		return letters.stream().collect(Collectors.joining());
	}

}
