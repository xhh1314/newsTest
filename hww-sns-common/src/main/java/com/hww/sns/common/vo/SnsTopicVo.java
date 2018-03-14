package com.hww.sns.common.vo;

import com.hww.base.common.vo.AbsBaseVo;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class SnsTopicVo extends AbsBaseVo {


    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private Long topicId;
    @NotNull(message = "用户不能为空")
    private Long memberId;//用户id
    private String memberName;// 登录账号
    private Long forumId;// 所属板块id
    private String forumName;//所属版块名称

    private String title;// 主题的标题

    private Long firstPostId;// 第一个帖子id
    private Long lastPostId;// 最后一个帖子id

    private Integer upNum;//点赞数
    private Integer commentNum;//评论数
    private Integer collectNum;//收藏数
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
//    private String umemberId;//用户id
    
    private String auhtorMemberId;//作者id
    
    private String sex;//性别
    private String avatar;//头像
    private String nickName;//昵称
    private String address;// 地址
    private List<SnsPostVo> sp;
    private Long relatednewsId;
    private String up;
    private String url;
    private String createTimeStr; //创建时间字符串
    private Long parentId; //父爆料id
    private List<SnsTopicVo> snsTopicVoList; //所有爆料的爆料列表
    private Integer topicType;
    private Integer concernType;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp createTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp lastModifyTime;    //最后修改时间(发布时间)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date beginTime;//开始时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;//结束时间
  
    private List<SnsPostVo> snsPostVos;    //主题跟帖
    private String imei;
    private Integer auditFlow;
    private Integer showStatus;
    
    private String keyWords;
    
    private Integer isAdminSetBl;//是否是管理员设为爆料：0 否 1 是
    
    private Map<String,Object> moreData;
    
    private Integer disabled;
    
    public Integer getDisabled() {
		return disabled;
	}

	public void setDisabled(Integer disabled) {
		this.disabled = disabled;
	}

	public Integer getAuditFlow() {
		return auditFlow;
	}

	public void setAuditFlow(Integer auditFlow) {
		this.auditFlow = auditFlow;
	}

	public Integer getShowStatus() {
		return showStatus;
	}

	public void setShowStatus(Integer showStatus) {
		this.showStatus = showStatus;
	}

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

    
    public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public Long getForumId() {
        return forumId;
    }

    public void setForumId(Long forumId) {
        this.forumId = forumId;
    }

    public String getForumName() {
		return forumName;
	}

	public void setForumName(String forumName) {
		this.forumName = forumName;
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
//
//    public String getUmemberId() {
//        return umemberId;
//    }
//
//    public void setUmemberId(String umemberId) {
//        this.umemberId = umemberId;
//    }

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

    public List<SnsTopicVo> getSnsTopicVoList() {
        return snsTopicVoList;
    }

    public void setSnsTopicVoList(List<SnsTopicVo> snsTopicVoList) {
        this.snsTopicVoList = snsTopicVoList;
    }

    public Integer getTopicType() {
        return topicType;
    }

    public void setTopicType(Integer topicType) {
        this.topicType = topicType;
    }

    public Integer getConcernType() {
        return concernType;
    }

    public void setConcernType(Integer concernType) {
        this.concernType = concernType;
    }

    public Timestamp getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(Timestamp lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public List<SnsPostVo> getSnsPostVos() {
        return snsPostVos;
    }

    public void setSnsPostVos(List<SnsPostVo> snsPostVos) {
        this.snsPostVos = snsPostVos;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

	public Integer getCollectNum() {
		return collectNum;
	}

	public void setCollectNum(Integer collectNum) {
		this.collectNum = collectNum;
	}

	public String getAuhtorMemberId() {
		return auhtorMemberId;
	}

	public void setAuhtorMemberId(String auhtorMemberId) {
		this.auhtorMemberId = auhtorMemberId;
	}

	public Integer getIsAdminSetBl() {
		return isAdminSetBl;
	}

	public void setIsAdminSetBl(Integer isAdminSetBl) {
		this.isAdminSetBl = isAdminSetBl;
	}

	public Map<String, Object> getMoreData() {
		return moreData;
	}

	public void setMoreData(Map<String, Object> moreData) {
		this.moreData = moreData;
	}

	public String getKeyWords() {
		return keyWords;
	}

	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}

    @Override
    public String toString() {
        return "SnsTopicVo{" +
                "topicId=" + topicId +
                ", forumName='" + forumName + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
