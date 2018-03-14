package com.hww.app.common.vo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;


public class ZAppHomeCategoryVo implements Serializable{
	
	    /**
	 * 
	 */
	private static final long serialVersionUID = -206862104592567414L;

		private List<HashMap<String, Object>> unSubscribeColumns;

	    private List<ZAppCategoryVo> indexColumns;

		public List<HashMap<String, Object>> getUnSubscribeColumns() {
			return unSubscribeColumns;
		}

		public void setUnSubscribeColumns(List<HashMap<String, Object>> unSubscribeColumns) {
			this.unSubscribeColumns = unSubscribeColumns;
		}

		public List<ZAppCategoryVo> getIndexColumns() {
			return indexColumns;
		}

		public void setIndexColumns(List<ZAppCategoryVo> indexColumns) {
			this.indexColumns = indexColumns;
		}
	    
	    
	
}
