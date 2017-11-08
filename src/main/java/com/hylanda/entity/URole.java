package com.hylanda.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;


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
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	private String type;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name="create_time")
	private Date createTime;
	
	 @ManyToMany
	    @JoinTable(name = "u_role_permission",
	            joinColumns = {@JoinColumn(name = "rid")},
	            inverseJoinColumns = {@JoinColumn(name = "pid")})
	    //@JsonIgnore
	    private List<UPermission> permissions;
	public URole() {
	}


	public List<UPermission> getPermissions() {
		return permissions;
	}



	public void setPermissions(List<UPermission> permissions) {
		this.permissions = permissions;
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

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}


	public Date getCreateTime() {
		return createTime;
	}


	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}