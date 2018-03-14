package com.hww.base.common.dto;

import java.io.Serializable;

//专门用来远程调用和服务的传输 只包含基础数据类型
public interface IBaseDto<ID extends Serializable> extends Serializable{
	
}
