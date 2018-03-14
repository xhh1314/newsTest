package com.hww.app.common.entity;

import com.hww.base.common.entity.AbsBaseEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * AbstractAppSearchHistory entity provides the base persistence definition of
 * the AppSearchHistory entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "app_search_history")
public class AppSearchHistory extends AbsBaseEntity<Long> {


    private Long searchId;
    private String searchContent;
    private Short auditStatus;
    private Short status;
    private String imei;
    private Long memberId;
    private Integer siteId;
    private Timestamp createTime;
    private Timestamp lastModifyTime;
    private Integer type;

    // Constructors

    /**
     * default constructor
     */
    public AppSearchHistory() {
    }

    /**
     * minimal constructor
     */
    public AppSearchHistory(Long searchId, String searchContent) {
        this.searchId = searchId;
        this.searchContent = searchContent;
    }

    /**
     * full constructor
     */
    public AppSearchHistory(Long searchId, String searchContent,
                            Short auditStatus, Short status, String imei, Long memberId,
                            Integer siteId, Timestamp createTime, Timestamp lastModifyTime,
                            Integer type) {
        this.searchId = searchId;
        this.searchContent = searchContent;
        this.auditStatus = auditStatus;
        this.status = status;
        this.imei = imei;
        this.memberId = memberId;
        this.siteId = siteId;
        this.createTime = createTime;
        this.lastModifyTime = lastModifyTime;
        this.type = type;
    }

    // Property accessors
    @Id
    @Column(name = "search_id", unique = true, nullable = false)
    @GeneratedValue(generator = "snowFlake")
    @GenericGenerator(name = "snowFlake", strategy = "com.hww.framework.common.idgen.SnowFlakeIdGenerator")
    public Long getSearchId() {
        return this.searchId;
    }

    public void setSearchId(Long searchId) {
        this.searchId = searchId;
    }

    @Column(name = "search_content", nullable = false, length = 100)
    public String getSearchContent() {
        return this.searchContent;
    }

    public void setSearchContent(String searchContent) {
        this.searchContent = searchContent;
    }

    @Column(name = "audit_status")
    public Short getAuditStatus() {
        return this.auditStatus;
    }

    public void setAuditStatus(Short auditStatus) {
        this.auditStatus = auditStatus;
    }

    @Column(name = "status")
    public Short getStatus() {
        return this.status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    @Column(name = "imei", length = 64)
    public String getImei() {
        return this.imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    @Column(name = "member_id")
    public Long getMemberId() {
        return this.memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    @Column(name = "site_id")
    public Integer getSiteId() {
        return this.siteId;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }

    @Column(name = "create_time", length = 19)
    public Timestamp getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Column(name = "last_modify_time", length = 19)
    public Timestamp getLastModifyTime() {
        return this.lastModifyTime;
    }

    public void setLastModifyTime(Timestamp lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

    @Column(name = "type")
    public Integer getType() {
        return this.type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    @Transient
    public Long getId() {
        // TODO Auto-generated method stub
        return searchId;
    }

}