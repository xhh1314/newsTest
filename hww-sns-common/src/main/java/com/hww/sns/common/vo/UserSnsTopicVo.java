package com.hww.sns.common.vo;

import java.sql.Timestamp;
import java.util.List;
import javax.validation.constraints.NotNull;
import com.hww.base.common.vo.AbsBaseVo;

public class UserSnsTopicVo extends AbsBaseVo{

    
    private Long topicId;
    @NotNull(message = "用户不能为空")
    private Long memberId;//用户id
    private Integer forumId;// 所属板块id

    private String title;// 主题的标题

    private Long firstPostId;// 第一个帖子id
    private Long lastPostId;// 最后一个帖子id
    
    private Integer upNum;//点赞数
    private Integer commentNum;//评论数
    private Integer auditStatus;//审核状态
    private String content;// 主题内容（可空）
    private String outLink;// 来源链接
    @NotNull(message = "经度不能为空")
    private Double longitude;//经度
    @NotNull(message = "纬度不能为空")
    private Double latitude;//纬度
    private Integer anonymous;//是否匿名
    private Integer publi;//是否公开
    private String fileId;//图片
    private Double distance;//纬度
    private Long umemberId;//用户id
    private String sex;//性别
    private String avatar;//头像
    private String nickName;//昵称
    private Timestamp createTime;// 创建时间
    private String address;// 地址
    private List<SnsPostVo> sp;
    private Long relatednewsId;
    private String up;
    private String url;
    private String createTimeStr; //创建时间字符串
    private Long parentId; //父爆料id
    private boolean followFlag; //点赞状态 true为已点赞
    private List<UserSnsTopicVo> snsTopicVoList; //所有爆料的爆料列表
    
    public Long getTopicId() {
        return topicId;
    }
    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }
    public Long getMemberId() {
        return memberId;
    }
    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }
    public Integer getForumId() {
        return forumId;
    }
    public void setForumId(Integer forumId) {
        this.forumId = forumId;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public Long getFirstPostId() {
        return firstPostId;
    }
    public void setFirstPostId(Long firstPostId) {
        this.firstPostId = firstPostId;
    }
    public Long getLastPostId() {
        return lastPostId;
    }
    public void setLastPostId(Long lastPostId) {
        this.lastPostId = lastPostId;
    }
    public Integer getUpNum() {
        return upNum;
    }
    public void setUpNum(Integer upNum) {
        this.upNum = upNum;
    }
    public Integer getCommentNum() {
        return commentNum;
    }
    public void setCommentNum(Integer commentNum) {
        this.commentNum = commentNum;
    }
    public Integer getAuditStatus() {
        return auditStatus;
    }
    public void setAuditStatus(Integer auditStatus) {
        this.auditStatus = auditStatus;
    }
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
    public Double getDistance() {
        return distance;
    }
    public void setDistance(Double distance) {
        this.distance = distance;
    }
    public Long getUmemberId() {
        return umemberId;
    }
    public void setUmemberId(Long umemberId) {
        this.umemberId = umemberId;
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
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public List<SnsPostVo> getSp() {
        return sp;
    }
    public void setSp(List<SnsPostVo> sp) {
        this.sp = sp;
    }

    public Long getRelatednewsId() {
        return relatednewsId;
    }

    public void setRelatednewsId(Long relatednewsId) {
        this.relatednewsId = relatednewsId;
    }

    public String getUp() {
        return up;
    }

    public void setUp(String up) {
        this.up = up;
    }
    public String getUrl() {
      return url;
    }
    public void setUrl(String url) {
      this.url = url;
    }

    public String getCreateTimeStr() {
        return createTimeStr;
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public boolean isFollowFlag() {
        return followFlag;
    }

    public void setFollowFlag(boolean followFlag) {
        this.followFlag = followFlag;
    }

    public List<UserSnsTopicVo> getSnsTopicVoList() {
        return snsTopicVoList;
    }

    public void setSnsTopicVoList(List<UserSnsTopicVo> snsTopicVoList) {
        this.snsTopicVoList = snsTopicVoList;
    }
}
