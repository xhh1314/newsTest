package com.hww.sns.common.entity;

import com.hww.base.common.entity.AbsBaseEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 敏感词/过滤词
 * @author hewei
 *
 */
@Entity
@Table(name = "sns_senstive")
public class SnsSenstive
	extends
		AbsBaseEntity<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long senstiveId; //主键
	private Integer wordType;//类型0:敏感词1:过滤词
	private String word;
	private String creator;// 录入人
	

	@Id
    @GeneratedValue(generator = "snowFlake")
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
			@GenericGenerator(name="snowFlake", strategy="com.hww.framework.common.idgen.SnowFlakeIdGenerator")
	@Column(name = "senstive_id")
	public Long getSenstiveId() {
		return senstiveId;
	}


	public void setSenstiveId(Long senstiveId) {
		this.senstiveId = senstiveId;
	}

	@Column(name = "word_type")
	public Integer getWordType() {
		return wordType;
	}


	public void setWordType(Integer wordType) {
		this.wordType = wordType;
	}

	@Column(name = "word")
	public String getWord() {
		return word;
	}


	public void setWord(String word) {
		this.word = word;
	}

	@Column(name = "creator")
	public String getCreator() {
		return creator;
	}


	public void setCreator(String creator) {
		this.creator = creator;
	}
	
	@Override
	@Transient
	public Long getId() {
		// TODO Auto-generated method stub
		return senstiveId;
	}
}
