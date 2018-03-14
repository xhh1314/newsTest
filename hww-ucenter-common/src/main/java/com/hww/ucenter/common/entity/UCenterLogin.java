package com.hww.ucenter.common.entity;

import com.hww.base.common.entity.AbsBaseEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;


@Entity
@Table(name = "ucenter_login")
public class UCenterLogin extends AbsBaseEntity<Long> {

    private static final long serialVersionUID = 6320126208542022941L;
    private Long loginId;
    private Long memberId;
    private String lastLoginIp;
    private Timestamp lastLoginTime;
    private Integer loginCount;
  
    private Long countryId;
    private String countryName;
    private String lon;
    private String lat;
    private String countryCode;
	public UCenterLogin() {
		super();
		
	}
	public UCenterLogin(Long memberId, String lastLoginIp, String lon, String lat, String countryCode) {
		super();
		this.memberId = memberId;
		this.lastLoginIp = lastLoginIp;
		this.lon = lon;
		this.lat = lat;
		this.countryCode = countryCode;
	}
    
	public UCenterLogin(Long loginId, Long memberId, String lastLoginIp, Timestamp lastLoginTime, Integer loginCount,
			Long countryId, String countryName, String lon, String lat, String countryCode) {
		super();
		this.loginId = loginId;
		this.memberId = memberId;
		this.lastLoginIp = lastLoginIp;
		this.lastLoginTime = lastLoginTime;
		this.loginCount = loginCount;
		this.countryId = countryId;
		this.countryName = countryName;
		this.lon = lon;
		this.lat = lat;
		this.countryCode = countryCode;
	}

	@Id
    @Column(name = "login_id")
    @GeneratedValue(generator = "snowFlake")
    @GenericGenerator(name = "snowFlake", strategy = "com.hww.framework.common.idgen.SnowFlakeIdGenerator")
    public Long getLoginId() {
		return loginId;
	}

	public void setLoginId(Long loginId) {
		this.loginId = loginId;
	}
	@Column(name = "country_code")
	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	@Column(name = "member_id")
    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }


    @Column(name = "last_login_ip")
    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    @Column(name = "last_login_time")
    public Timestamp getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Timestamp lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    @Column(name = "login_count")
    public Integer getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(Integer loginCount) {
        this.loginCount = loginCount;
    }

   

    @Column(name = "country_id")
    public Long getCountryId() {
        return countryId;
    }

    public void setCountryId(Long countryId) {
        this.countryId = countryId;
    }



    @Column(name = "country_name")
    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }


    @Column(name = "lon")
    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    @Column(name = "lat")
    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }



    @Column(name = "status")
    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }


    @Override
    @Transient
    public Long getId() {
        return loginId;
    }

    
    
    
}
