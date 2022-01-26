package com.url.shortner.service;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.url.shortner.exception.URLRedirectionException;
import com.url.shortner.model.UrlShortenedEntity;
import com.url.shortner.repository.UrlShortenedRepository;
import com.url.shortner.utility.URLShortenConstants;

@Service
public class URLShortenServiceImpl implements URLShortenService {
	
	@Autowired
	private UrlShortenedRepository urlShortenedRepository;

	@Override
	public String getOrginalUrl(String shortID) throws URLRedirectionException {
		UrlShortenedEntity urlShorten = urlShortenedRepository.findByShortenedURL(shortID.trim());
		if(null == urlShorten) {
			throw new URLRedirectionException(URLShortenConstants.INVALID_SHORT_URL);
		}
		
		if(null != urlShorten.getUrlEntity().getDateOfValidity() && !(urlShorten.getUrlEntity().getDateOfValidity().compareTo(new Date()) >= 0)) {
			throw new URLRedirectionException(URLShortenConstants.SHORT_URL_EXPIRED);
		}
		
		if(urlShorten.getUrlEntity().getSecondsOfValdity() > 0) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(urlShorten.getUrlEntity().getCreatedDate());
			cal.add(Calendar.SECOND, urlShorten.getUrlEntity().getSecondsOfValdity());
			Date newDate = cal.getTime();
			
			System.out.println(urlShorten.getUrlEntity().getCreatedDate());
			System.out.println(newDate);
			
			if(!(newDate.compareTo(new Date()) >= 0)) {
				throw new URLRedirectionException(URLShortenConstants.SHORT_URL_EXPIRED);
			}
		}
		
		String orginalURL = urlShorten.getUrlEntity().getOrginalUrl();
		return orginalURL;
	}

}
