package com.hww.ucenter.common.entity;

/*import com.hww.base.common.entity.AbsBaseEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;

*//**
 * 管理账号实体类
 *
 *//*
@Entity
@Table(name = "ucenter_user")
public class UCenterUser extends AbsBaseEntity<Long> {
	private static final long serialVersionUID = 6615517529375476353L;

	private Long memberId;
	*//**
	 * 真名
	 *//*
	private String realName;
	*//**
	 * 笔名
	 *//*
	private String pseudonym;
	*//**
	 * 审核状态(锁定状态)
	 *//*
	private Short status;


	@Id
	@Column(name = "member_id")
	@GeneratedValue(generator = "snowFlake")
	@GenericGenerator(name = "snowFlake", strategy = "com.hww.framework.common.idgen.SnowFlakeIdGenerator")
	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	@Column(name = "real_name")
	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	@Column(name = "pseudonym")
	public String getPseudonym() {
		return pseudonym;
	}

	public void setPseudonym(String pseudonym) {
		this.pseudonym = pseudonym;
	}

	@Override
	@Transient
	public Long getId() {
		// TODO Auto-generated method stub
		return memberId;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

}
*/