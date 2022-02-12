package com.url.shortner.VO;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class URLEntityVO {
	
	@NotBlank(message = "URL should not be empty")
	private String orginalUrl;
	
	private int secondsOfValdity;
	private String dateOfValidity;
	private String timeZone;
}
