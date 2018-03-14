package com.hww.cms.common;

/**
 * CMS常量
 * 
 */
public class Constants {

	public static final Short CONTENT_STATUS_NEW = 1;// 稿件新建状态
	public static final Short CONTENT_STATUS_FINAL = 90;// 终审状态
	public static final Short CONTENT_STATUS_PUBLISHED = 99;// 稿件发布后的状态
	public static final Short CONTENT_STATUS_DELETING = -2;// 稿件准备删除状态
	public static final Short CONTENT_STATUS_DELETED = -1;// 稿件删除完成状态

	public static final Short SITE_STATUS_ENABLE = 1;// 站点可用状态
	public static final Short SITE_STATUS_DELETED = -1;// 站点删除状态

	public static final Short MENU_STATUS_ENABLE = 1;// 菜单可用状态
	public static final Short MENU_STATUS_DELETED = -1;// 菜单删除状态

	public static final Short ROLE_STATUS_ENABLE = 1;// 角色可用状态
	public static final Short ROLE_STATUS_DELETED = -1;// 角色删除状态

	public static final Short USER_STATUS_ENABLE = 1;// 用户可用状态
	public static final Short USER_STATUS_DELETED = -1;// 用户删除状态

	public static final Short GROUP_STATUS_ENABLE = 1;// 组可用状态
	public static final Short GROUP_STATUS_DELETED = -1;// 组删除状态

	public static final Short CATEGORY_STATUS_ENABLE = 1;// 分类可用状态
	public static final Short CATEGORY_STATUS_DELETED = 0;// 分类删除状态

	public static final Short CATEGORY_TPL_STATUS_ENABLE = 1;// 稿件发布后的状态
	public static final Short CATEGORY_TPL_STATUS_DELETED = -1;// 稿件删除状态

	public static final Short CONTENT_TPL_STATUS_ENABLE = 1;// 稿件发布后的状态
	public static final Short CONTENT_TPL_STATUS_DELETED = -1;// 稿件删除状态

	public static final Short QUEUE_OPTION_PUBLISHING = 1;// 正在处理

	/**
	 * UTF-8编码
	 */
	public static final String UTF8 = "UTF-8";

	// tag参数名称
	/**
	 * 当前栏目
	 */
	public static final String OUT_CURRENT_CATEGORY = "currentCategory";
	/**
	 * 
	 */
	public static final String PARAM_CONTENT_TPL_ID = "contentTplId";
	public static final String PARAM_CATEGORY_TPL_ID = "categoryTplId";
	/**
	 * 稿件url规则
	 */
	public static final String PARAM_CONTENT_INDEX_URL_RULE = "contentIndexUrlRule";
	public static final String PARAM_CATEGORY_INDEX_URL_RULE = "categoryIndexUrlRule";
	public static final String PARAM_CONTENT_PAGE_URL_RULE = "contentPageUrlRule";
	public static final String PARAM_CATEGORY_PAGE_URL_RULE = "categoryPageUrlRule";
	/**
	 * 导航
	 */
	public static final String OUT_CATEGORY_NAV = "tag_categoryNav";
	/**
	 * 内容id
	 */
	public static final String PARAM_CONTNT_ID = "contentId";
	/**
	 * 栏目id
	 */
	public static final String PARAM_CATEGORY_ID = "categoryId";
	/**
	 * 站点id
	 */
	public static final String PARAM_SITE_ID = "siteId";

	/**
	 * 参数：分页号
	 */
	public static final String PARAM_PAGE_NO = "pageNo";
	/**
	 * 参数：每页大小
	 */
	public static final String PARAM_PAGE_SIZE = "pageSize";

	/**
	 * 输入参数，栏目类型。
	 */
	public static final String PARAM_TPL_TYPE_ID = "tplTypeId";
	/**
	 * 输入参数。
	 */
	public static final String PARAM_TPL_STYLE_ID = "tplStyleId";
	/**
	 * 参数：是否有全路径导航
	 */
	public static final String PARAM_HAS_NAV = "hasNav";

	/**
	 * 模板类型 公共碎片部分common-0,聚合单页channel-1,分页列表page-2,详情content-3
	 */
	public static final Integer TPL_TYPE_COMMON = 0;
	public static final Integer TPL_TYPE_CHANNEL = 1;
	public static final Integer TPL_TYPE_PAGE = 2;
	public static final Integer TPL_TYPE_CONTENT = 2;
	
	public static class status{
		public static final short disabled = 0; //禁用
		public static final short usable = 1; //可用
	}
	/**
	 * 分类
	 */
	public static class categoryType{
		public static final short singlePage = 0;//单页
		public static final short outerLink = 1;//外部链接
		public static final short listview = 2; //列表
		public static final short special = 3; //专题
	}
	/**
	 * 内容分类
	 *
	 */
	public static class contentType{
		public static final int simple = 1;//普通
		//public static final short point = 3; //焦点,本期没有
		public static final int picword = 2;//图文
		public static final int topline = 4; //头条
		public static final Long picCollect =Long.valueOf("5"); //图集
		public static final Long video = Long.valueOf("6"); //视频
	}
	
	public static class auditType{
		public static final int contentType = 0; //新闻审核
		public static final int specialType = 1; //专题审核
	}
	
	/**
	 * 一审拒绝二审不显示该条,以此类推
	 * 参考hww-sns-web-admin模块Constants.auditStatus
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
	
	/**
	 * 用户行为type
	 * 用户行为类别 1 点赞 2 收藏 3 不感兴趣 4 内容太水 5 看过了',
	 */
	public static class behaviorType{
		public static final int upNum = 1; //点赞
		public static final int collect = 2;//收藏
		public static final int notinterested = 3;//不感兴趣
		public static final int toolow = 4; //内容太水
		public static final int view=5;//查看
		//public static final int comment=3;//评论数
		//public static final int shareNum=4;//分享数
	}
	
}
