package com.hww.app.common.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class SearchHistoryDto implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long searchId;
    private String searchContent;
    private String imei;
    private Long memberId;
    private Short status;
    private Integer siteId;
    private Timestamp createTime;
    private Timestamp lastModifyTime;
    private Integer type; //1 综合 2 爆料 3 视频 4 新鲜事 5 用户 6 附近
    private Integer num;
    
    private Integer pageSize;
    private Integer pageNo;
    
    public SearchHistoryDto() {
		super();
	}
    public SearchHistoryDto(Long searchId, String searchContent, String imei, Long memberId, Short status,
			Integer siteId, Timestamp createTime, Timestamp lastModifyTime, Integer type, Integer num) {
		super();
		this.searchId = searchId;
		this.searchContent = searchContent;
		this.imei = imei;
		this.memberId = memberId;
		this.status = status;
		this.siteId = siteId;
		this.createTime = createTime;
		this.lastModifyTime = lastModifyTime;
		this.type = type;
		this.num = num;
	}

	public static SearchHistoryDto newForCreate(Long memberId,String imei, String searchContent,Integer type) {
		SearchHistoryDto dto=new SearchHistoryDto();
		dto.searchContent = searchContent;
		dto.imei = imei;
		dto.memberId = memberId;
		dto.status = Short.valueOf("1");
		dto.siteId = 1;
		dto.createTime = new Timestamp(System.currentTimeMillis()); 
		dto.lastModifyTime = new Timestamp(System.currentTimeMillis()); 
		dto.type = type;
		return dto;
    }
    
    public Long getSearchId() {
        return searchId;
    }

    public SearchHistoryDto setSearchId(Long searchId) {
        this.searchId = searchId;
		return this;
    }

    public String getSearchContent() {
        return searchContent;
    }

    public SearchHistoryDto setSearchContent(String searchContent) {
        this.searchContent = searchContent;
		return this;
    }

    public Long getMemberId() {
        return memberId;
    }

    public SearchHistoryDto setMemberId(Long memberId) {
        this.memberId = memberId;
		return this;
    }

    public String getImei() {
        return imei;
    }

    public SearchHistoryDto setImei(String imei) {
        this.imei = imei;
		return this;
    }


	public Short getStatus() {
		return status;
	}

	public SearchHistoryDto setStatus(Short status) {
		this.status = status;
		return this;
	}

	public Integer getSiteId() {
		return siteId;
	}

	public SearchHistoryDto setSiteId(Integer siteId) {
		this.siteId = siteId;
		return this;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public SearchHistoryDto setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
		return this;
	}

	public Timestamp getLastModifyTime() {
		return lastModifyTime;
	}

	public SearchHistoryDto setLastModifyTime(Timestamp lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
		return this;
	}

	public Integer getType() {
		return type;
	}

	public SearchHistoryDto setType(Integer type) {
		this.type = type;
		return this;
	}

	public Integer getNum() {
		return num;
	}

	public SearchHistoryDto setNum(Integer num) {
		this.num = num;
		return this;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public SearchHistoryDto setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
		return this;
	}
	public Integer getPageNo() {
		return pageNo;
	}
	public SearchHistoryDto setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
		return this;
	}
    
    
}
