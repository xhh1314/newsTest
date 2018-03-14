function loadMenu(resourceType, treeObjId,setting) {
	//$.fn.zTree.init($("#" + treeObjId), setting, null);
	$.ajax({
		type : "POST",
		url : resourceType,
		dataType : "json",
		success : function(data) {
			// 如果返回数据不为空，加载"业务模块"目录
			if (data != null) {
				// 将返回的数据赋给zTree
				$.fn.zTree.init($("#" + treeObjId), setting, data);
				//              alert(treeObj);
				zTree = $.fn.zTree.getZTreeObj(treeObjId);
				if (zTree) {
					// 默认展开所有节点
					zTree.expandAll(true);
				}
			}
		}
	});
}

function loadMenuByJsonStr(jsonStr, treeObjId,setting){
	if (jsonStr != '') {
		// 将返回的数据赋给zTree
		$.fn.zTree.init($("#" + treeObjId), setting, jsonStr);
		zTree = $.fn.zTree.getZTreeObj(treeObjId);
		if (zTree) {
			// 默认展开所有节点
			zTree.expandAll(true);
		}
	}
}

/**
 * 选中节点
 * @param treeDemoId   
 * @returns
 */
function selectNodeById(treeDemoId,id){
	var selectTreeObj = $.fn.zTree.getZTreeObj(treeDemoId);
	if(selectTreeObj!=null){
		var node = selectTreeObj.getNodeByParam("id", id, null);
		if(node!=null){
			selectTreeObj.selectNode(node);
		}
	}
}