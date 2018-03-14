package com.hww.sns.common.dto;

import com.hww.base.common.dto.AbsBaseDto;

//分区  等同于内容里的分类
public class SnsCategoryDto extends AbsBaseDto<Long>{
	private Long categoryId;
	private Integer forumCols;//板块列数
	private String name;
	
}
