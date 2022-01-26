package com.url.shortner.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@MappedSuperclass
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Parent {
	
	@CreationTimestamp
	@Column(name = "created_date", nullable = false, updatable = false)
	private Date createdDate;
	
	@UpdateTimestamp
	@Column(name = "modified_date", nullable = false)
	private Date modifiedDate;
}
