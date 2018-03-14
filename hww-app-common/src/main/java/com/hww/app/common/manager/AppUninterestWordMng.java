package com.hww.app.common.manager;


import java.util.List;

import com.hww.app.common.dto.AppHotWordDto;
import com.hww.app.common.entity.AppHotWord;
import com.hww.app.common.entity.AppUninterestWord;
import com.hww.app.common.vo.AppHotWordVo;
import com.hww.base.common.page.Pagination;


public interface AppUninterestWordMng {

	List<AppUninterestWord> loadAllUninterestWord();

}
