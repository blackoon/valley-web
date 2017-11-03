package com.hylanda.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigInteger;


/**
 * The persistent class for the u_role_permission database table.
 * 
 */
@Entity
@Table(name="u_role_permission")
@NamedQuery(name="URolePermission.findAll", query="SELECT u FROM URolePermission u")
public class URolePermission implements Serializable {
	private static final long serialVersionUID = 1L;

	private BigInteger pid;

	private BigInteger rid;

	public URolePermission() {
	}

	public BigInteger getPid() {
		return this.pid;
	}

	public void setPid(BigInteger pid) {
		this.pid = pid;
	}

	public BigInteger getRid() {
		return this.rid;
	}

	public void setRid(BigInteger rid) {
		this.rid = rid;
	}

}