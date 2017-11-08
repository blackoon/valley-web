package com.hylanda.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;


/**
 * The persistent class for the u_permission database table.
 * 
 */
@Entity
@Table(name="u_permission")
@NamedQuery(name="UPermission.findAll", query="SELECT u FROM UPermission u")
public class UPermission implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	private String url;

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name="create_time")
	private Date createTime;
	
	public UPermission() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}


}