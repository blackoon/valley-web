package com.hylanda.entity.dict;

/**
 * @author zhangy
 * @E-mail:blackoon88@gmail.com
 * @qq:846579287
 * @version created at：2017年11月8日 上午11:08:33 note
 */
public enum StatusType {
	enable(1, "可用") {
	},
	disable(0, "不可用") {
	};
	public int code;
	public String name;

	private StatusType(int code, String name) {
		this.code = code;
		this.name = name;
	}

	public int getCode() {
		return code;
	}

	public String getName() {
		return name;
	}
	public static StatusType[] getAllEncodeType(){
		return new StatusType[]{enable,disable};
	}
}
