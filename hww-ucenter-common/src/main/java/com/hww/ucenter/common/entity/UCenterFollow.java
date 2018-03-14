package com.hww.ucenter.common.entity;

import com.hww.base.common.entity.AbsBaseEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
/**
 * 
 * @author lh
 * @date 2017年12月27日
 * @version 
 */
/**
 * 关注表，存放谁关注谁
 * @author lh
 * @date 2017年12月27日
 * @version 
 */
@Entity
@Table(name = "ucenter_follow")
public class UCenterFollow extends AbsBaseEntity<Long> {

    private Long followId;
    private Long memberId;
    private Long tomemberId;
    /**
     * 关注类型，1关注对方 2互相关注
     */
    private Integer concernType; 
    @Id
    @Column(name = "follow_id")
    @GeneratedValue(generator = "snowFlake")
    @GenericGenerator(name = "snowFlake", strategy = "com.hww.framework.common.idgen.SnowFlakeIdGenerator")
    public Long getFollowId() {
        return followId;
    }

    public void setFollowId(Long followId) {
        this.followId = followId;
    }

    @Column(name = "member_id")
    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    @Override
    @Transient
    public Long getId() { 
        return followId;
    }

    @Column(name = "concern_type")
    public Integer getConcernType() {
        return concernType;
    }

    public void setConcernType(Integer concernType) {
        this.concernType = concernType;
    }

    @Column(name = "tomember_id")
    public Long getTomemberId() {
        return tomemberId;
    }

    public void setTomemberId(Long tomemberId) {
        this.tomemberId = tomemberId;
    }
    
    
}
