package com.hww.sns.common.dto;

import com.hww.base.common.dto.AbsBaseDto;

//帖子
public class SnsPostDto extends AbsBaseDto<Long>{
	private Long postId;
	private Integer topicId;//所属主题 topic
	private Integer floor;//楼层
	
	private Boolean anonymous;//是否匿名
	
	//分表
	private String title;//帖子标题
	private String content;//帖子内容
}
