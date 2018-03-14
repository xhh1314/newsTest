package com.hww.cms.common.dto;

import com.hww.base.common.dto.AbsBaseDto;

import java.sql.Timestamp;

public class CmsHotWordDto extends AbsBaseDto<Long> {

    private Long hotId; //热词id
    private String hotWord; //热词内容
    private Integer type; //1 综合 2 爆料 3 视频 4 新鲜事 5 用户 6 附近
    private Integer auditStatus; //审核状态 0 未审核 1 已审核
    private Short status;
    private Integer siteId;
    private Timestamp createTime;
    private Timestamp lastModifyTime;
    private Integer pageNo;
    private Integer pageSize;
    private String allIDCheck;

    public Long getHotId() {
        return hotId;
    }

    public void setHotId(Long hotId) {
        this.hotId = hotId;
    }

    public String getHotWord() {
        return hotWord;
    }

    public void setHotWord(String hotWord) {
        this.hotWord = hotWord;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(Integer auditStatus) {
        this.auditStatus = auditStatus;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public Integer getSiteId() {
        return siteId;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(Timestamp lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getAllIDCheck() {
        return allIDCheck;
    }

    public void setAllIDCheck(String allIDCheck) {
        this.allIDCheck = allIDCheck;
    }
}
