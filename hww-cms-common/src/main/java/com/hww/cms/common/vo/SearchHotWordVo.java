package com.hww.cms.common.vo;

import com.hww.base.common.vo.AbsBaseVo;

import java.sql.Timestamp;

public class SearchHotWordVo extends AbsBaseVo {

    private Long hotId;
    private String hotWord;
    private Integer type; //1 综合 2 爆料 3 视频 4 新鲜事 5 用户 6 附近
    private Short status;
    private Integer siteId;
    private Timestamp createTime;
    private Timestamp lastModifyTime;
    private Short auditStatus;

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

    public Short getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(Short auditStatus) {
        this.auditStatus = auditStatus;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
