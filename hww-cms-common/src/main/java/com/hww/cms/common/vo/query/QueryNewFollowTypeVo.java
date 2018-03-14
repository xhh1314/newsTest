package com.hww.cms.common.vo.query;

import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by kongkenan on 2017/11/24.
 */
public class QueryNewFollowTypeVo {

    @NotBlank(message = "新闻id不能为空")
    private Long newId; //新闻id
    @NotBlank(message = "用户id不能为空")
    private Long userId; //用户id
    @NotBlank(message = "设备imei号不能为空")
    private String imei; //imei号
    @NotBlank(message = "新闻评论id不能为空")
    private Long newsPostId; //新闻评论id
    @NotBlank(message = "新闻爆料id不能为空")
    private Long topicId; //新闻爆料id

    public Long getNewId() {
        return newId;
    }

    public void setNewId(Long newId) {
        this.newId = newId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public Long getNewsPostId() {
        return newsPostId;
    }

    public void setNewsPostId(Long newsPostId) {
        this.newsPostId = newsPostId;
    }

    public Long getTopicId() {
        return topicId;
    }

    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }
}
