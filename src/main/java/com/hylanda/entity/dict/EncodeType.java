package com.hylanda.entity.dict;


/** 
 * @author zhangy
 * @E-mail:blackoon88@gmail.com 
 * @qq:846579287
 * @version created at：2017年11月8日 上午11:15:03 
 * note
 */
public enum EncodeType {
	des(0,"des"){},
	jwt(1,"jwt"){};
	
	public int code;
	public String name;
	private EncodeType(int code, String name) {
		this.code = code;
		this.name = name;
	}

	public int getCode() {
		return code;
	}

	public String getName() {
		return name;
	}
	public static EncodeType[] getAllEncodeType(){
		return new EncodeType[]{des,jwt};
	}
}
