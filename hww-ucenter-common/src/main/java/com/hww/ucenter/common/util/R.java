package com.hww.ucenter.common.util;

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

	public R() {
		put("status", 200);
		put("message", SUCCESS);
	}

    public static R ok() {
        return new R();
    }
    public static R ok(String message){
		return new R().put("message",message);
	}

	public static R error(int code, String message) {
		R r = new R();
		r.put("status", code);
		r.put("message", message);
		return r;
	}

	@Override
	public R put(String key, Object value) {
		super.put(key, value);
		return this;
	}
}
