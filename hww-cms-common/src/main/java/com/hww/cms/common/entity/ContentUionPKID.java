package com.hww.cms.common.entity;

import javax.persistence.Column;

public class ContentUionPKID implements java.io.Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column
	private Long contentId;

	@Column
    private Long categoryId;


    public Long getContentId() {
		return contentId;
	}

	public void setContentId(Long contentId) {
		this.contentId = contentId;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	@Override 
    public boolean equals(Object obj) { 
        if(obj instanceof ContentUionPKID){ 
        	ContentUionPKID pk=(ContentUionPKID)obj; 
            if(this.contentId.equals(pk.contentId)&&this.categoryId.equals(pk.categoryId)){ 
                return true; 
            } 
        } 
        return false; 
    }

    @Override 
    public int hashCode() { 
        return super.hashCode(); 
    }

}