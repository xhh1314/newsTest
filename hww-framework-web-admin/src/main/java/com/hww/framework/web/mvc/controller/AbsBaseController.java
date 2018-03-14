package com.hww.framework.web.mvc.controller;

import java.util.List;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

public abstract class AbsBaseController {

	public String getErrorMessage(BindingResult errors) {
		List<ObjectError> ls=errors.getAllErrors(); 
		StringBuffer sb  = new StringBuffer();
        for (int i = 0; i < ls.size(); i++) {  
        	ObjectError error = ls.get(i);
        	sb.append(error.getDefaultMessage());
        }
        String result = sb.toString();
        return result;
	}
}
