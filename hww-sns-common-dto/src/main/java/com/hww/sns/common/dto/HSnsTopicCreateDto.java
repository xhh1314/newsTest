package com.hww.sns.common.dto;


import java.io.Serializable;
public class HSnsTopicCreateDto  implements Serializable 

{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private Long memberId;//用户id
   
    private Long relatednewsId; //关联新闻id
    private String title;// 主题的标题
    private Integer topicType;  //类型(新鲜事:0/爆料:1),默认为0
    private Integer anonymous;//是否匿名
    private Integer publi;//是否公开
    private String fileId;//图片
    private String address;// 
    private Double longitude;//经度
    private Double latitude;//纬度
    private String content;// 主题内容（可空）
    private String outLink;// 来源链接
    private String imei;


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getOutLink() {
        return outLink;
    }

    public void setOutLink(String outLink) {
        this.outLink = outLink;
    }
    
    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    

    public Integer getTopicType() {
        return topicType;
    }

    public void setTopicType(Integer topicType) {
        this.topicType = topicType;
    }


    public Integer getAnonymous() {
        return anonymous;
    }


    public void setAnonymous(Integer anonymous) {
        this.anonymous = anonymous;
    }


    public Integer getPubli() {
        return publi;
    }


    public void setPubli(Integer publi) {
        this.publi = publi;
    }


    public String getFileId() {
        return fileId;
    }


    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getAddress() {
        return address;
    }


    public void setAddress(String address) {
        this.address = address;
    }
    
    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Long getRelatednewsId() {
        return relatednewsId;
    }

    public void setRelatednewsId(Long relatednewsId) {
        this.relatednewsId = relatednewsId;
    }

    

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }
	
}
