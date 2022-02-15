package com.url.shortner.VO;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.url.shortner.utility.URLConstants;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class URLEntityVO {
	
	@NotBlank(message = "URL should not be empty")
	@JsonProperty(URLConstants.PROPERTY_URL)
	private String orginalUrl;
	
	@JsonProperty(URLConstants.PROPERTY_SECONDS)
	private int secondsOfValdity;
	
	@JsonProperty(URLConstants.PROPERTY_DATE)
	private String dateOfValidity;
	
	@JsonProperty(URLConstants.PROPERTY_TIME_ZONE)
	private String timeZone;
}
