package com.hww.sns.common.dto;

import com.hww.base.common.dto.AbsBaseDto;

//板块
public class SnsForumDto extends AbsBaseDto<Long>{
	private Long forumId;
	private Long categoryId;//所属分区id
	
	private String title;
	private String description;
	
	private Integer topicTotal;//主题总数
	private Integer postTotal;//帖子总数
	
}
