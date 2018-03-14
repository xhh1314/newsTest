package com.hww.app.webservice;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HwwConsts {
	// 0 详情查看， 1点赞 ，2 收藏，3评论，4爆料，5 感兴趣，6内容太水，7看过了
	//value:1 点赞收藏等，0 取消点赞收藏等
	
	public static final class BehaviorValue{
		public static final Integer bev1=1; 
		public static final Integer bev0=0; 
	}
	
	public static final class Behavior{
		public static final int b0_xq=0; 
		
		public static final int b1_dz=1; 
		public static final int b2_sc=2; 
		public static final int b3_pl=3; 
		public static final int b4_bl=4; 
		
		public static final int b5_gxq=5; 
		public static final int b6_nrts=6; 
		public static final int b7_kgl=7; 
	}
	public static final List<Integer> userBeahavPlateList=new ArrayList<Integer>(){{
		add(new Integer( 0));//cms_content
		add(new Integer( 1));//sns_topic
		add(new Integer( 2));//sns_post
	}};
	public static final List<Integer> userBeahavList=new ArrayList<Integer>(){{
		add(0);
		add(1);
		add(2);
		add(3);
		add(4);
		add(5);
		add(6);
		add(7);
	}};
	public static final Map<String,Integer> userBeahavCount=new HashMap<String,Integer>(){{
		put("0", 0);
		put("1", 0);
		put("2", 0);
		put("3", 0);
		put("4", 0);
		put("5", 0);
		put("6", 0);
		put("7", 0);
	}};
}
