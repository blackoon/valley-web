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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonBackReference;


/**
 * The persistent class for the u_user database table.
 * 
 */
@Entity
@Table(name="u_user")
@NamedQuery(name="UUser.findAll", query="SELECT u FROM UUser u")
public class UUser implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

//	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name="create_time")
	private Date createTime;

	private String email;

//	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name="last_login_time")
	private Date lastLoginTime;

	private String nickname;

	private String pswd;

	private Integer status;
	private String sex;
	@ManyToOne
    @JoinColumn(name = "did")
    @JsonBackReference
    private Department department;
	

    @ManyToMany
    @JoinTable(name = "u_user_role",
            joinColumns = {@JoinColumn(name = "uid")},
            inverseJoinColumns = {@JoinColumn(name = "rid")})
    //@JsonIgnore
    private List<URole> roles;

	public UUser() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getLastLoginTime() {
		return this.lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public String getNickname() {
		return this.nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getPswd() {
		return this.pswd;
	}

	public void setPswd(String pswd) {
		this.pswd = pswd;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public List<URole> getRoles() {
		return roles;
	}

	public void setRoles(List<URole> roles) {
		this.roles = roles;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

}