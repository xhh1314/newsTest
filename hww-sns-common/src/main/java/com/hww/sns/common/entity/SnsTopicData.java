package com.hww.sns.common.entity;

import com.hww.base.common.entity.AbsBaseEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

//JPA基类标识
@MappedSuperclass
public class SnsTopicData
    extends
        AbsBaseEntity<Long> {

    private Long topicId;
    
    

    
    
    @Id
    @GeneratedValue(generator = "snowFlake")
  //@GeneratedValue(strategy = GenerationType.IDENTITY)
	@GenericGenerator(name="snowFlake", strategy="com.hww.framework.common.idgen.SnowFlakeIdGenerator")
    @Column(name = "topic_id")
    public Long getTopicId() {
        return topicId;
    }

    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }

    


    @Override
    @Transient
    public Long getId() {
        // TODO Auto-generated method stub
        return topicId;
    }

    

    
}
