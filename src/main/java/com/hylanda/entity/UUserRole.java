package com.hylanda.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigInteger;


/**
 * The persistent class for the u_user_role database table.
 * 
 */
@Entity
@Table(name="u_user_role")
@NamedQuery(name="UUserRole.findAll", query="SELECT u FROM UUserRole u")
public class UUserRole implements Serializable {
	private static final long serialVersionUID = 1L;

	private BigInteger rid;

	private BigInteger uid;

	public UUserRole() {
	}

	public BigInteger getRid() {
		return this.rid;
	}

	public void setRid(BigInteger rid) {
		this.rid = rid;
	}

	public BigInteger getUid() {
		return this.uid;
	}

	public void setUid(BigInteger uid) {
		this.uid = uid;
	}

}