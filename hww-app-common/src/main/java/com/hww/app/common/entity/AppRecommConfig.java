package com.hww.app.common.entity;

import com.hww.base.common.entity.AbsBaseEntity;
import com.hww.base.common.entity.IBaseEntity;

import java.sql.Timestamp;

import javax.persistence.*;

/**
 * 首页栏目推荐新闻数量
 */
@Entity
@Table(name = "app_recomm_config")
public class AppRecommConfig  implements IBaseEntity<Long>  {

    private Long columnId; //栏目id
    private String columnName; //栏目名称
    private Integer recommNum; //推荐数量
    private Integer type; //1 栏目 2 专题 3 新鲜事
    private Short status;
    private Integer siteId;
    private Timestamp createTime;
    private Timestamp lastModifyTime;

    @Override
    @Transient
    public Long getId() {
        return columnId;
    }

    @Id
    @Column(name = "column_id")
    public Long getColumnId() {
        return columnId;
    }

    public void setColumnId(Long columnId) {
        this.columnId = columnId;
    }

    @Column(name = "recomm_num")
    public Integer getRecommNum() {
        return recommNum;
    }

    public void setRecommNum(Integer recommNum) {
        this.recommNum = recommNum;
    }

    @Column(name = "type")
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Column(name = "column_name")
    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }
    @Column(name = "status")
	public Short getStatus() {
		return status;
	}
	
	public void setStatus(Short status) {
		this.status = status;
	}
	@Column(name = "site_id")
	public Integer getSiteId() {
		return siteId;
	}
	 
	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}
	 @Column(name = "create_time")
	public Timestamp getCreateTime() {
		return createTime;
	}
	
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	 @Column(name = "last_modify_time")
	public Timestamp getLastModifyTime() {
		return lastModifyTime;
	}

	public void setLastModifyTime(Timestamp lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}
}