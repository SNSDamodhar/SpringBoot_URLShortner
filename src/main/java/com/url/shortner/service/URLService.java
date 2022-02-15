package com.url.shortner.service;

import com.url.shortner.VO.URLEntityVO;
import com.url.shortner.exception.URLValidationException;
import com.url.shortner.model.UrlShortenedEntity;

public interface URLService {
	
	UrlShortenedEntity createShortenLink(URLEntityVO urlEntityVO) throws URLValidationException;

}
