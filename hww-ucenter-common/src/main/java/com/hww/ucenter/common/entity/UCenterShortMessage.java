package com.hww.ucenter.common.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 存储发送短信相关的详情
 */
@Entity
@Table(name="ucenter_short_message")
public class UCenterShortMessage implements Serializable, Comparable<UCenterShortMessage> {

    private Long id;
    /**
     *手机号
     */
    private String phoneNumber;
    /**
     *验证码
     */
    private String code;
    /**
     *发送时间
     */
    private Date sendMessageTime;

    @Id
    @Column(name="short_message_id")
    @GeneratedValue(generator = "snowFlake")
    @GenericGenerator(name = "snowFlake", strategy = "com.hww.framework.common.idgen.SnowFlakeIdGenerator")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "phone_number")
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Column(name="send_message_time")
    public Date getSendMessageTime() {
        return sendMessageTime;
    }

    public void setSendMessageTime(Date sendMessageTime) {
        this.sendMessageTime = sendMessageTime;
    }

    @Override
    public String toString() {
        return "UcenterShortMessage{" +
                "id=" + id +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", code='" + code + '\'' +
                ", sendMessageTime=" + sendMessageTime +
                '}';
    }


    /**
     * 实现一个降序
     * @param o
     * @return
     */
    @Override
    public int compareTo(UCenterShortMessage o) {
        if(this.id<o.id)
            return 1;
        if(this.id>o.id)
            return -1;
        return 0;
    }
}
