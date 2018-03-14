package com.hww.sns.common.dto;

import com.alibaba.fastjson.JSON;
import com.hww.base.common.dto.AbsBaseDto;

import java.util.HashMap;
import java.util.Map;

//主题  在板块列表中可见的，具体帖子是post
public class SnsTopicDto extends AbsBaseDto<Long>{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private Long topicId;
	private Long memberId;//用户id
	private Long forumId;// 所属板块id 0新鲜事 1 爆料 固定板块不允许修改

	private Long relatednewsId; //关联新闻id
//    private Long contentId;

	private String title;// 主题的标题


	private Long firstPostId;// 第一个帖子id
	private Long lastPostId;// 最后一个帖子id

	private Integer upNum;//点赞数
	private Integer commentNum;//评论数
	private Integer auditStatus;//审核状态
	private Integer topicType;  //类型 主题类型0:新鲜事1:爆料
	private Integer anonymous;//是否匿名
	private Integer publi;//是否公开
	private String fileId;//图片
	private String address;// 主题的标题
	private Double longitude;//经度
	private Double latitude;//纬度
	private String content;// 主题内容（可空）
	private String outLink;// 来源链接

	private Long parentId; //父爆料id
	private String imei;
	private Integer auditFlow;
	private Integer showStatus;
	private String url;

	private Integer isAdminSetBl;//是否是管理员设为爆料：0 否 1 是

	public static long getSerialVersionUID() {
		return serialVersionUID;
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

	public Long getForumId() {
		return forumId;
	}

	public void setForumId(Long forumId) {
		this.forumId = forumId;
	}

	public Long getRelatednewsId() {
		return relatednewsId;
	}

	public void setRelatednewsId(Long relatednewsId) {
		this.relatednewsId = relatednewsId;
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

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getIsAdminSetBl() {
		return isAdminSetBl;
	}

	public void setIsAdminSetBl(Integer isAdminSetBl) {
		this.isAdminSetBl = isAdminSetBl;
	}

	public Map<String,String> transferToMap(){
		Map<String,String> map=new HashMap<>(32);
		Map<String,Object> temp= JSON.parseObject(JSON.toJSONString(this),Map.class);
		temp.forEach((s, o) -> {
			if(o instanceof  String){
				map.put(s,(String)o);
			}else {
				map.put(s,o.toString());
			}
		});
		return map;
	}
}
