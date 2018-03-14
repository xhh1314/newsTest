package com.hww.sns.common.vo;

//import java.util.Date;
//
//import javax.validation.constraints.NotNull;
//
//import org.springframework.format.annotation.DateTimeFormat;
//
//import com.hww.base.common.vo.AbsBaseVo;
//
///**
// * 新闻审核表vo
// * @author hewei
// *
// */
//public class SnsNewsAuditVo extends AbsBaseVo{
//	/**
//	 * 
//	 */
//	private static final long serialVersionUID = 1L;
//	private Long auditId;
//	private Long topicId;//新闻id
//	private Long postId; //新闻评论id
//	private Integer topicType;//类型.默认为空表示新闻类型
//	private Integer curentstatus;//当前状态(0:审核不通过1:审核通过)
//	private String auditUser;// 审核人
//
//	private String auditOpinion;//审核意见
//
//	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//	private Date createTime;
//
//	public Long getAuditId() {
//		return auditId;
//	}
//
//	public void setAuditId(Long auditId) {
//		this.auditId = auditId;
//	}
//	
//	public Long getTopicId() {
//		return topicId;
//	}
//
//	public void setTopicId(Long topicId) {
//		this.topicId = topicId;
//	}
//
//	
//	public Long getPostId() {
//		return postId;
//	}
//
//	public void setPostId(Long postId) {
//		this.postId = postId;
//	}
//
//	@NotNull(message = "审核类型不能为空")
//	public Integer getTopicType() {
//		return topicType;
//	}
//
//	public void setTopicType(Integer topicType) {
//		this.topicType = topicType;
//	}
//
//	@NotNull(message = "审核状态不能为空")
//	public Integer getCurentstatus() {
//		return curentstatus;
//	}
//
//	public void setCurentstatus(Integer curentstatus) {
//		this.curentstatus = curentstatus;
//	}
//
//	public String getAuditUser() {
//		return auditUser;
//	}
//
//	public void setAuditUser(String auditUser) {
//		this.auditUser = auditUser;
//	}
//
//	public String getAuditOpinion() {
//		return auditOpinion;
//	}
//
//	public void setAuditOpinion(String auditOpinion) {
//		this.auditOpinion = auditOpinion;
//	}
//
//	public Date getCreateTime() {
//		return createTime;
//	}
//
//	public void setCreateTime(Date createTime) {
//		this.createTime = createTime;
//	}
//	
//	
//	
//	
//}
