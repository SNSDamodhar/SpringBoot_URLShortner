package com.url.shortner.model;


import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "url")
public class UrlEntity extends Parent {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "url_id")
	private Long id;
	
	@Column(name = "orginal_url", nullable = false)
	private String orginalUrl;
	
	@Column(name = "valid_seconds")
	private int secondsOfValdity;
	
	@Column(name = "valid_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateOfValidity;
	
	@Column(name = "time_zone")
	private String timeZone;
	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "urlEntity")
	@JsonIgnore
	private UrlShortenedEntity urlShortenedEntity;
}
