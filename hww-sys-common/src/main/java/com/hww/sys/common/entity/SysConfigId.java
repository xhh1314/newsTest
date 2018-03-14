package com.hww.sys.common.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
@Embeddable
public class SysConfigId implements java.io.Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer siteId;
	
    private Long moduleId;

    @Column(name="site_id")
    public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}
	
	@Column(name="module_id")
	public Long getModuleId() {
		return moduleId;
	}

	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}
	
	public SysConfigId() {
		// TODO Auto-generated constructor stub
	}
	
	public SysConfigId(Integer siteId, Long moduleId) {
		// TODO Auto-generated constructor stub
		this.siteId = siteId;
		this.moduleId = moduleId;
	}

	@Override 
    public boolean equals(Object other) { 
		if ((this == other))
            return true;
        if ((other == null))
            return false;
        if (!(other instanceof SysConfigId))
            return false;
        SysConfigId castOther = (SysConfigId) other;

        return ((this.getSiteId() == castOther.getSiteId()) || (this.getSiteId() != null
                && castOther.getSiteId() != null && this.getSiteId().equals(
                castOther.getSiteId())))
                && ((this.getModuleId() == castOther.getModuleId()) || (this
                        .getModuleId() != null && castOther.getModuleId() != null && this
                        .getModuleId().equals(castOther.getModuleId())));
    }

    @Override 
    public int hashCode() {
    	int result = 17;
    	result = 37 * result + (getSiteId() == null ? 0 : this.getSiteId().hashCode());
        result = 37 * result
                + (getModuleId() == null ? 0 : this.getModuleId().hashCode());
        return result; 
    }

}