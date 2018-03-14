package com.hww.cms.common.dto;

import com.hww.base.common.dto.AbsBaseDto;

/**
 * 内容来源vo
 */
public class CmsOriginDto extends AbsBaseDto<Long> {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private Long originId; // 来源id
    private String originName; // 来源名称
    private String originUrl; // 来源地址
    private String icon;
    private String word;
    private String link;
    private Integer pageNo;
    private Integer pageSize;

    public Long getOriginId() {
        return originId;
    }

    public void setOriginId(Long originId) {
        this.originId = originId;
    }

    public String getOriginName() {
        return originName;
    }

    public void setOriginName(String originName) {
        this.originName = originName;
    }

    public String getOriginUrl() {
        return originUrl;
    }

    public void setOriginUrl(String originUrl) {
        this.originUrl = originUrl;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
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
}