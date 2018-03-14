package com.hww.sns.common.vo;

import java.sql.Timestamp;
import java.util.List;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotBlank;
import com.hww.base.common.vo.AbsBaseVo;

public class TopPostListVo extends AbsBaseVo{

    private Long postId;
    @NotNull(message="新鲜事不能为空")
    private Long topicId;// 所属主题 topic
    private Integer floor;// 楼层，最大为总个数
    private Integer anonymous;// 是否匿名
    private Long upNum;//点赞数
    private Long commentNum;//评论数
    private String content;// 帖子内容
    private Double longitude;//经度
    private Double latitude;//纬度
    private String address;//地址
    private Long memberId;//用户
    private Integer type;// 类型
    private Long parentId;
    private String sex;//性别
    private String avatar;//头像
    private String nickName;//昵称
    private Timestamp createTime;// 创建时间
    private Double distance;//距离
    private String up;
    private Long umemberId;
    public Long getPostId() {
        return postId;
    }
    public void setPostId(Long postId) {
        this.postId = postId;
    }
    public Long getTopicId() {
        return topicId;
    }
    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }
    public Integer getFloor() {
        return floor;
    }
    public void setFloor(Integer floor) {
        this.floor = floor;
    }
    public Integer getAnonymous() {
        return anonymous;
    }
    public void setAnonymous(Integer anonymous) {
        this.anonymous = anonymous;
    }
    public Long getUpNum() {
        return upNum;
    }
    public void setUpNum(Long upNum) {
        this.upNum = upNum;
    }
    public Long getCommentNum() {
        return commentNum;
    }
    public void setCommentNum(Long commentNum) {
        this.commentNum = commentNum;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
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
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public Long getMemberId() {
        return memberId;
    }
    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }
    public Integer getType() {
        return type;
    }
    public void setType(Integer type) {
        this.type = type;
    }
    public Long getParentId() {
        return parentId;
    }
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
    public String getSex() {
        return sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
    public String getAvatar() {
        return avatar;
    }
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
    public String getNickName() {
        return nickName;
    }
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
    public Timestamp getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }
    public Double getDistance() {
        return distance;
    }
    public void setDistance(Double distance) {
        this.distance = distance;
    }
    public String getUp() {
        return up;
    }
    public void setUp(String up) {
        this.up = up;
    }
    public Long getUmemberId() {
      return umemberId;
    }
    public void setUmemberId(Long umemberId) {
      this.umemberId = umemberId;
    }
    
    
}
