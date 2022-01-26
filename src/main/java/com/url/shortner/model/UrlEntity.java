package com.url.shortner.model;

import java.time.LocalDate;
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
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
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
	@NotBlank(message = "URL should not be empty")
	private String orginalUrl;
	
	@Column(name = "valid_seconds")
	private int secondsOfValdity;
	
	@Column(name = "valid_date")
	@Temporal(TemporalType.DATE)
	@JsonFormat(shape = Shape.STRING ,pattern = "dd-MM-yyyy")
	private Date dateOfValidity;
	
	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "urlEntity")
	@JsonIgnore
	private UrlShortenedEntity urlShortenedEntity;
}
