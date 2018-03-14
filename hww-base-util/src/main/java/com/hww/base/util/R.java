package com.hww.base.util;

import java.util.LinkedHashMap;

/**
 * 返回数据
 *
 * @author xlf
 * @email xlfbe696@gmail.com
 * @date 2017年4月19日 上午11:58:56
 */
public class R extends LinkedHashMap<String, Object> {
	private static final long serialVersionUID = 1L;

	public static final String SUCCESS = "success";

	private int statusCode;
	
	public R() {
		this.statusCode=200;
		put("status", 200);
		put("message", SUCCESS);
	}

    public static R ok() {
    	R r = new R();
    	r.setStatusCode(200);
        return new R();
    }
    public  boolean isOk() {
    	boolean isOK=("200".equals(""+this.statusCode));
        return isOK;
    }
    public  String  getMsg() {
        return  (String) this.get("message");
    }
    public static R ok(String message){
    	R r = new R();
    	r.setStatusCode(200);
		return r.put("message",message);
	}

	public static R error(int code, String message) {
		R r = new R();
		r.setStatusCode(500);
		r.put("status", code);
		r.put("message", message);
		return r;
	}
	
	public static R error() {
		return error(500, "异常");
	}

	public static R error(String msg) {
		return error(500, msg);
	}

	@Override
	public R put(String key, Object value) {
		super.put(key, value);
		return this;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	
	
	
}
