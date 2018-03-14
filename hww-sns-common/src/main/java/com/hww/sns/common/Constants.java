package com.hww.sns.common;

/**
 * sns常量
 * @author hewei
 *
 */
public class Constants {

	public static class topicType{
		public static final int newsfreshType = 0; //新鲜事类型
		public static final int newsbrokeType = 1; //爆料类型
		public static final int newsType = 2; //评论类型
	}
	
	/**
	 * 审核类型
	 *
	 */
	public static class auditType{
		public static final int topicType = 0; //主题审核
		public static final int postType = 1; //评论审核
	}
	/**
	 * 一审拒绝二审不显示该条,以此类推
	 *
	 */
	public static class auditStatus{
		public static final int committed = 0;	//已提交未审核
		public static final int autitpassed = 1;	//审核通过
		
		public static final int oneauditDeny =10; // 一审拒绝
		public static final int oneauditPassed = 11; //一审通过
		
		public static final int twoauditDeny = 20; //二审拒绝
		public static final int twoauditPassed = 21; //二审通过
		
		public static final int threeauditDeny = 20; //三审拒绝
		public static final int threeauditPassed = 21; //三审通过

		public static final int fourauditDeny = 40; //四审拒绝
		public static final int fourauditPassed = 41; //四审通过
	}
	
	public static class wordType{
		public static final int sensitivewordType=0; //敏感词类型
		public static final int filterwordType=0; //过滤词类型
	}
	
	public static class newsType{
		public static final int china = 0;  //国内
		public static final int foreign =1; //国外
		public static final int economics =2;//经济
		public static final int politics = 3;//政治
	}
	
	public static class followType{
		public static final int viewNum=0;//查看数
		public static final int upNum=1; //点赞数
		public static final int commentNum=2;//评论数
		public static final int collectNum=3;//收藏数
		public static final int shareNum=4;//分享数
		public static final int notinterestedNum=5;//不感兴趣数
	}
}
