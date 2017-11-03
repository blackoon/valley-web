package com.hylanda.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the u_role database table.
 * 
 */
@Entity
@Table(name="u_role")
@NamedQuery(name="URole.findAll", query="SELECT u FROM URole u")
public class URole implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	private String name;

	private String type;

	public URole() {
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

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

}