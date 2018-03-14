package com.hww.sns.common.entity;

import javax.persistence.Column;

public class ThumbUpUionPKID implements java.io.Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column
	private Long upId;

    public Long getUpId() {
        return upId;
    }

    public void setUpId(Long upId) {
        this.upId = upId;
    }

    @Override
    public boolean equals(Object obj) { 
        if(obj instanceof ThumbUpUionPKID){
            ThumbUpUionPKID pk=(ThumbUpUionPKID)obj;
            if(this.upId.equals(pk.upId)){
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