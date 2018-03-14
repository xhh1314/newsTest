package com.hww.app.common.entity;

import com.hww.base.common.entity.IBaseEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 存放各个国家电话号码代码，比如中国 86
 */
@Entity
@Table(name = "app_national_telephone_code")
public class AppNationalTelCode implements IBaseEntity<Long> {
	private Long id;
	/**
	*国家代号 例如 en ch us
	*/
	private String nationCode;
    /**
     *电话代码 例如 +86
     */
	private String phoneCode;
    /**
     *国家中文名 例如:美国
     */
	private String chineseName;
    /**
     *国家英文名 例如:America
     */
	private String englishName;
	
	/**
	 * 国家繁体名
	 */
	private String nationNameTw ;

	@Id
	@Column(name = "national_telephone_id")
    @GeneratedValue(generator = "snowFlake")
    @GenericGenerator(name = "snowFlake", strategy = "com.hww.framework.common.idgen.SnowFlakeIdGenerator")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "nation_code")
	public String getNationCode() {
		return nationCode;
	}

	public void setNationCode(String nationCode) {
		this.nationCode = nationCode;
	}

	@Column(name = "phone_code")
	public String getPhoneCode() {
		return phoneCode;
	}

	public void setPhoneCode(String phoneCode) {
		this.phoneCode = phoneCode;
	}

	@Column(name = "chinese_name")
	public String getChineseName() {
		return chineseName;
	}

	public void setChineseName(String chineseName) {
		this.chineseName = chineseName;
	}

	@Column(name = "english_name")
	public String getEnglishName() {
		return englishName;
	}

	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}
@Column(name="nation_name_tw")
	public String getNationNameTw() {
		return nationNameTw;
	}

	public void setNationNameTw(String nationNameTw) {
		this.nationNameTw = nationNameTw;
	}
	
	
}
