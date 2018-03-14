package com.hww.sys.common.dto;

import java.sql.Timestamp;

import com.hww.base.common.dto.AbsBaseDto;



public class SysLogDto  extends AbsBaseDto<Integer> {


    // Fields    

     private Integer logId;
     private Integer category;
     private Timestamp logTime;
     private String ip;
     private String url;
     private String title;
     private String content;
     private Integer userId;

    // Property accessors

    public Integer getLogId() {
        return this.logId;
    }
    
    public void setLogId(Integer logId) {
        this.logId = logId;
    }

    public Integer getCategory() {
        return this.category;
    }
    
    public void setCategory(Integer category) {
        this.category = category;
    }

    public Timestamp getLogTime() {
        return this.logTime;
    }
    
    public void setLogTime(Timestamp logTime) {
        this.logTime = logTime;
    }

    public String getIp() {
        return this.ip;
    }
    
    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUrl() {
        return this.url;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return this.title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return this.content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }

    public Integer getUserId() {
        return this.userId;
    }
    
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

	@Override
	public Integer getId() {
		// TODO Auto-generated method stub
		return logId;
	}

}