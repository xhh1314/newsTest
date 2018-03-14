package com.hww.base.util;

/**
 * 自定义异常
 * @author xlf
 * @email xlfbe696@gmail.com
 * @date 2017年4月19日 上午11:27:44
 */
public class RRException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	private String msg;
	private int errCode = 500;

	public RRException(String msg) {
		super(msg);
		this.msg = msg;
	}

	public RRException(String msg, Throwable e) {
		super(msg, e);
		this.msg = msg;
	}

	public RRException(String msg, int code) {
		super(msg);
		this.msg = msg;
		this.errCode = code;
	}

	public RRException(String msg, int code, Throwable e) {
		super(msg, e);
		this.msg = msg;
		this.errCode = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getCode() {
		return errCode;
	}

	public void setCode(int code) {
		this.errCode = code;
	}

}
