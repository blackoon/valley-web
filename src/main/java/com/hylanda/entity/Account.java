package com.hylanda.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonBackReference;

/**
 * @author zhangy
 * @E-mail:blackoon88@gmail.com
 * @qq:846579287
 * @version created at：2017年11月8日 上午9:56:01 note
 */
@Entity
@Table(name = "api_account")
public class Account implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;

	private String accessKey;

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "create_time")
	private Date createTime;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "use_start_time")
	private Date useStartTime;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "use_end_time")
	private Date useEndTime;
	
	private Integer status;
	
	private Integer encodeType;
	
	@ManyToOne
	@JoinColumn(name = "rid")
    @JsonBackReference
    private URole role;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAccessKey() {
		return accessKey;
	}

	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUseStartTime() {
		return useStartTime;
	}

	public void setUseStartTime(Date useStartTime) {
		this.useStartTime = useStartTime;
	}

	public Date getUseEndTime() {
		return useEndTime;
	}

	public void setUseEndTime(Date useEndTime) {
		this.useEndTime = useEndTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getEncodeType() {
		return encodeType;
	}

	public void setEncodeType(Integer encodeType) {
		this.encodeType = encodeType;
	}

	public URole getRole() {
		return role;
	}

	public void setRole(URole role) {
		this.role = role;
	}

}
