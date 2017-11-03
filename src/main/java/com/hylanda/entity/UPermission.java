package com.hylanda.entity;

import java.io.Serializable;
import javax.persistence.*;


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
	private String id;

	private String name;

	private String url;

	public UPermission() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
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

}