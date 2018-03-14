package com.hww.sns.webservice.consts;

import java.util.ArrayList;
import java.util.List;

public class SnsConsts {
	//0 新鲜事1 爆料2新闻
	public static final List<Integer> topicTypeList=new ArrayList<Integer>(2) {{add(0);add(1);add(2);}};
	//评论类型(0:新鲜事评论1:爆料评论2:新闻评论)
	public static final List<Integer> postTypeList=new ArrayList<Integer>(2) {{add(0);add(1);add(2);}};

}
