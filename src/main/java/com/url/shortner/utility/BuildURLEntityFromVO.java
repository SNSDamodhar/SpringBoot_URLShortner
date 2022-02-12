package com.url.shortner.utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.url.shortner.VO.URLEntityVO;
import com.url.shortner.exception.URLValidationException;
import com.url.shortner.model.UrlEntity;

@Component
public class BuildURLEntityFromVO {
	
	@Autowired
	private DateUtilities dateConversionUtilities;

	public UrlEntity convert(URLEntityVO urlEntityVO, List<String> errors) {
		UrlEntity urlEntity = new UrlEntity();
		urlEntity.setOrginalUrl(urlEntityVO.getOrginalUrl().trim());
		
		if(urlEntityVO.getSecondsOfValdity() > 0) {
			urlEntity.setSecondsOfValdity(urlEntityVO.getSecondsOfValdity());
			return urlEntity;
		}
		
		if(urlEntityVO.getTimeZone() == null || urlEntityVO.getTimeZone().trim().length() == 0) {
			urlEntityVO.setTimeZone(URLConstants.DEFAULT_TIMEZONE_VALIDITY);
		}
		
		urlEntity.setTimeZone(urlEntityVO.getTimeZone());
		
		if(urlEntityVO.getDateOfValidity() != null) {
			try {
				SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
				Date parsedDate = formatter.parse(urlEntityVO.getDateOfValidity());
				urlEntity.setDateOfValidity(dateConversionUtilities.convertDateToUTCDate(parsedDate, urlEntityVO.getTimeZone()));
			} catch (URLValidationException e) {
				errors.add(e.getMessage());
			} catch (ParseException e) {
				errors.add("Invalid date format. Please specify date in dd-MM-yyyy");
			}
		}
		return urlEntity;
	}
	
	
}
