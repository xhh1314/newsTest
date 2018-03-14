package com.hww.cms.common.vo;

import java.sql.Timestamp;

/**
 * 用户新闻行为VO类
 */
public class CmsMemberBehaviorVo {

    private Long behaviorId; //id
    private Long memberId;//会员id作为主键
    private String uuid;//imei或者UDID
    private Integer categoryId;//分类id
    private Long contentId;//内容id作为主键
    protected Timestamp createTime;// 推荐这条内容的时间，不可修改
    private Timestamp readedTime;//是否查看过详情，第一次阅读过的时间
    private Short praised;//点赞过0   1
    private Integer commented;//评论过,可以多次
    private Timestamp favoritedTime;//收藏过,按照收藏时间的实际时间记录
    private Integer type; //用户行为类别 1 点赞 2 收藏 3 不感兴趣 4 内容太水 5 看过了
    private String ids; //类别ids
    private String keywords; //不感兴趣新闻关键词

    public Long getBehaviorId() {
        return behaviorId;
    }

    public void setBehaviorId(Long behaviorId) {
        this.behaviorId = behaviorId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Long getContentId() {
        return contentId;
    }

    public void setContentId(Long contentId) {
        this.contentId = contentId;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getReadedTime() {
        return readedTime;
    }

    public void setReadedTime(Timestamp readedTime) {
        this.readedTime = readedTime;
    }

    public Short getPraised() {
        return praised;
    }

    public void setPraised(Short praised) {
        this.praised = praised;
    }

    public Integer getCommented() {
        return commented;
    }

    public void setCommented(Integer commented) {
        this.commented = commented;
    }

    public Timestamp getFavoritedTime() {
        return favoritedTime;
    }

    public void setFavoritedTime(Timestamp favoritedTime) {
        this.favoritedTime = favoritedTime;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }
}
