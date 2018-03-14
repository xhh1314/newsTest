var childPageCommon = {
	init:function(){
		this.setParentIframeHeight();
		this.eventBind();
	},
	//父级iframe高度随内容高度
	setParentIframeHeight:function(){
		if(window.parent===window.self)return;
		var height = document.body.clientHeight;
		window.parent.pageMethods.iframeSetHeight(height);
	},
	eventBind:function(){
		//窗口调整父级iframe自适应
		if(window.parent===window.self)return;
		window.onresize = _(function() {  
			var height = document.body.clientHeight;
	        window.parent.pageMethods.iframeSetHeight(height);
	    }).debounce(2000);
	}
}

//对bootbox插件进行封装
!(function(win,$){
	var op = {
		alert:{ 
		  	size:'small',
		  	title:'信息提示',
		  	message:'', 
		  	buttons:{
			  	ok:{
			  		label:'确定',
			  		className: 'btn-info btn-sm'
			  	}
			}
		},
		confirm:{ 
		  	size:'',
		  	title:'信息确认',
		  	message:'', 
		  	buttons:{
			  	cancel:{
		            label:'取消',
		            className:'btn-info btn-sm'
		        },
		        confirm:{
		            label:'确认',
		            className:'btn-info btn-sm'
		        }
			}
		}
	};

	win.hkbootbox = {
		alert:function(options){
			options.message = '<h3><span class="glyphicon glyphicon-info-sign text-info align-middle"></span> <small class="align-middle">'+options.message+'</small></h3> ';
			var options = $.extend(op.alert,options);
			bootbox.alert(options);
		},
		confirm:function(options){
			options.message = '<h3><span class="glyphicon glyphicon-info-sign text-danger align-middle"></span> <small class="align-middle">'+options.message+'</small></h3> ';
			var options = $.extend(op.confirm,options);
			bootbox.confirm(options);
		}
	}
})(window,jQuery);