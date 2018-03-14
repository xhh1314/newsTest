package com.hww.app.common.vo;


import com.hww.base.common.vo.AbsBaseVo;

public class AppTelCodeVo extends AbsBaseVo {
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
	 * @return
	 */
	private String nationNameTw;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNationCode() {
		return nationCode;
	}
	public void setNationCode(String nationCode) {
		this.nationCode = nationCode;
	}
	public String getPhoneCode() {
		return phoneCode;
	}
	public void setPhoneCode(String phoneCode) {
		this.phoneCode = phoneCode;
	}
	public String getChineseName() {
		return chineseName;
	}
	public void setChineseName(String chineseName) {
		this.chineseName = chineseName;
	}
	public String getEnglishName() {
		return englishName;
	}
	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}
	public String getNationNameTw() {
		return nationNameTw;
	}
	public void setNationNameTw(String nationNameTw) {
		this.nationNameTw = nationNameTw;
	}
	

	
}
