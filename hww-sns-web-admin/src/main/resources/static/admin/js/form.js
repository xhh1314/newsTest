
	/** 模糊查询 **/
	function search(){
		$("#submitForm").submit();
		
	}

	/** 新增   **/
	function save(){
		$("#submitForm").attr("action", "o_save.do").submit();	
	}
	
	/** 刷新页面 **/
	function refresh(){
		alert("aa");
		window.location.reload();//刷新当前页面.
	}
	
	/*
	 * 取消
	 */
	
	/*$("#cancelbutton").click(function() {
		*//**  关闭弹出iframe  **//*
		window.parent.$.fancybox.close();
	});*/
	function cancel(){
		
		
	}
	
	/** 删除 **/
	function del(action){
		if(confirm("您确定要删除吗？")){
			$("#submitForm").attr("action", action).submit();			
		}
	}
	
	function batchDel(action){
		batch(action,"您确定要批量删除这些记录吗？！");
	}
	
	/** 批量删除 **/
	function batch(action,msg){
		if($("input[name='IDCheck']:checked").size()<=0){
			//art.dialog({icon:'error', title:'友情提示', drag:false, resize:false, content:'至少选择一条', ok:true,});
			alert("至少选择一条");
			return;
		}
		// 1）取出用户选中的checkbox放入字符串传给后台,form提交
		var allIDCheck = "";
		$("input[name='IDCheck']:checked").each(function(index, domEle){
			
				allIDCheck += $(domEle).val() + ",";
			
		});
		// 截掉最后一个","
		if(allIDCheck.length>0) {
			allIDCheck = allIDCheck.substring(0, allIDCheck.length-1);
			// 赋给隐藏域
			$("#allIDCheck").val(allIDCheck);
			if(confirm(msg)){
				// 提交form
				$("#submitForm").attr("action", action).submit();
			}
		}
	}

	
	
	/** 输入页跳转 **/
	function jumpInputPage(totalPage){
		// 如果“跳转页数”不为空
		if($("#jumpNumTxt").val() != ''){
			var pageNum = parseInt($("#jumpNumTxt").val());
			// 如果跳转页数在不合理范围内，则置为1
			if(pageNum<1 | pageNum>totalPage){
				art.dialog({icon:'error', title:'友情提示', drag:false, resize:false, content:'请输入合适的页数，\n自动为您跳到首页', ok:true,});
				pageNum = 1;
				
			}
			$("#pageNo").val(pageNum);
			$("#submitForm").attr("action", "v_list.do").submit();
		}else{
			// “跳转页数”为空
			art.dialog({icon:'error', title:'友情提示', drag:false, resize:false, content:'请输入合适的页数，\n自动为您跳到首页', ok:true,});
			return ;
			$("#pageNo").val(1);
			$("#submitForm").attr("action", "v_list.do").submit();
		}
	}