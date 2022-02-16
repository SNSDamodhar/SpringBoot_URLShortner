package com.url.shortner.utility;

public class URLConstants {
	public static final String NO_URL = "URL is empty";
	
	public static final String INVALID_URL = "Invalid URL provided";
	
	public static final String INVALID_VALIDITY = "Either Seconds should be greater than 0 nor Date should be mentioned";
	
	public static final String INVALID_DATE_VALIDITY = "Validity Date should be future date";
	
	public static final String INVALID_VALIDITY_DATE_FORMAT = "Invalid date format. Please specify date in dd-MM-yyyy";
	
	public static final String INVALID_CUSTOM_SHORT_URL = "Requested short url should be minimum 3 characters length with alphanumeric characters";
	
	public static final String DEFAULT_TIMEZONE_VALIDITY = "UTC";
	
	public static final String PROPERTY_SECONDS = "valid_upto_seconds";
	
	public static final String PROPERTY_DATE = "valid_till_date";
	
	public static final String PROPERTY_URL = "url";
	
	public static final String PROPERTY_TIME_ZONE = "time_zone";
	
	public static final int CUSTOM_URL_MIN_LENGTH = 3;
	
	public static final String DUPLICATE_REQUESTED_URL = "Requested short url is already taken. Please request another";
	
	public static final String GENERAL_EXCEPTION_MESSAGE = "Something went wrong from our side. Please try after sometime";
}
