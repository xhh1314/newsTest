package com.hww.app.common.entity;

import com.hww.base.common.entity.IBaseEntity;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;

@Entity
@Table(name = "app_unintreset_word")
public class AppUninterestWord implements  IBaseEntity<Long> {

    private Long id;
    private String wordText;
    private Integer status;
    private Integer siteId;

    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(generator = "snowFlake")
    @GenericGenerator(name = "snowFlake", strategy = "com.hww.framework.common.idgen.SnowFlakeIdGenerator")
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

   
    @Column(name = "word_text", nullable = false, length = 30)
    public String getWordText() {
		return wordText;
	}

	public void setWordText(String wordText) {
		this.wordText = wordText;
	}

	@Column(name = "status")
    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Column(name = "site_id")
    public Integer getSiteId() {
        return this.siteId;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }

}