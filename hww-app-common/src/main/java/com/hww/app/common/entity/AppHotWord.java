package com.hww.app.common.entity;

import com.hww.base.common.entity.AbsBaseEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * AbstractAppHoyWord entity provides the base persistence definition of the
 * AppHoyWord entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "app_hot_word")
public class AppHotWord extends AbsBaseEntity<Long> {

    private Long hotId;
    private String hotWord;
    private Integer type;
    private Short status;
    private Integer siteId;
    private Timestamp createTime;
    private Timestamp lastModifyTime;
    private Short auditStatus;

    @Id
    @Column(name = "hot_id", unique = true, nullable = false)
    @GeneratedValue(generator = "snowFlake")
    @GenericGenerator(name = "snowFlake", strategy = "com.hww.framework.common.idgen.SnowFlakeIdGenerator")
    public Long getHotId() {
        return this.hotId;
    }

    public void setHotId(Long hotId) {
        this.hotId = hotId;
    }

    @Column(name = "hot_word", nullable = false, length = 150)
    public String getHotWord() {
        return this.hotWord;
    }

    public void setHotWord(String hotWord) {
        this.hotWord = hotWord;
    }

    @Column(name = "type")
    public Integer getType() {
        return this.type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Column(name = "status")
    public Short getStatus() {
        return this.status;
    }

    public void setStatus(Short status) {
        this.status = status;
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

    @Column(name = "audit_status")
    public Short getAuditStatus() {
        return this.auditStatus;
    }

    public void setAuditStatus(Short auditStatus) {
        this.auditStatus = auditStatus;
    }

    @Override
    @Transient
    public Long getId() {
        // TODO Auto-generated method stub
        return hotId;
    }


}