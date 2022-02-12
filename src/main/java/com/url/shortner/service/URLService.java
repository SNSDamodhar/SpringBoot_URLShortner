package com.url.shortner.service;

import com.url.shortner.VO.URLEntityVO;
import com.url.shortner.exception.URLValidationException;

public interface URLService {
	
	String createShortenLink(URLEntityVO urlEntityVO) throws URLValidationException;

}
