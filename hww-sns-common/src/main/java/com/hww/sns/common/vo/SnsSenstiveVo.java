package com.hww.sns.common.vo;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.hww.base.common.vo.AbsBaseVo;

/**
 * 敏感词/过滤词
 * @author hewei
 *
 */
public class SnsSenstiveVo extends AbsBaseVo{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long senstiveId; //主键
	private Integer wordType;//类型0:敏感词1:过滤词
	private String word;
	private String creator;// 录入人
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;//录入时间
	
	public Long getSenstiveId() {
		return senstiveId;
	}
	public void setSenstiveId(Long senstiveId) {
		this.senstiveId = senstiveId;
	}
	public Integer getWordType() {
		return wordType;
	}
	public void setWordType(Integer wordType) {
		this.wordType = wordType;
	}
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	
	
	
}
