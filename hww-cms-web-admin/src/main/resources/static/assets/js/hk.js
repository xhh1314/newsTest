//注：此文件依赖于jquery 请确保jquery先引入
//此文件存储一些海客站点公共方法及页面一些初始化方法



var hk = {
	//初始化
	init:function(){
		this.scrollbarInit();
//		this.sliderMenuInit();
	},
	//滚动条相关入口
	scrollbarInit:function(){
		//如果想让某个元素使用自定义滚动条 给该元素class hk-scrollbar
		//注：子元素高度或宽度 > 该元素高度或宽度 出现滚动条
		$('.hk-scrollbar').mCustomScrollbar({
		    theme:"minimal-dark",
		    mouseWheelPixels:200,
		    scrollInertia:200,
		    scrollEasing:"linear",
		    axis:"yx"
		});
	},
	//侧边栏状态 根据实际开发选择用与不用（如果页面不刷新或局部刷新即侧边菜单不刷新 可以调用）
//	sliderMenuInit:function(){
//		$(document).on('click','#sidebar .nav-list a',function(event) {
//			var $this = $(this);
//			var $allLi = $('#sidebar .nav-list li');
//			//激活状态更新
//			$allLi.removeClass('active');
//			$this.parentsUntil('.nav-list','li').addClass('active');
//		});
//	},
	//公共方法
	methods:{
		//元素高度计算
		//针对需求：元素要求在内容区顶上顶下超出可滚动
		//原理：页面加载完毕或者窗口大小调整对齐进行高度计算 
		//屏幕高度（不固定） - 其他固定元素所占屏幕高度（一般固定）- 其他如margin，padding高度 = 元素高度
		//参数解释
		//elId（string）：元素id
		//subtractHeight（number）：其他固定元素所占屏幕高度
		elHeightComputed:function(elId,subtractHeight){
			var $el = $(elId);
			var winHeight = $(window).height();
			$el.height(winHeight-subtractHeight);
			$(window).resize(function(){
				winHeight = $(window).height();
				$el.height(winHeight-subtractHeight);
			});
		}
	}
};

$(function(){
	hk.init();
});