package com.hww.app.webservice.service.impl;


import com.hww.app.common.entity.AppUninterestWord;
import com.hww.app.common.manager.AppUninterestWordMng;
import com.hww.app.webservice.service.AppUninterestWordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AppUninterestWordServiceImpl implements AppUninterestWordService {

    @Autowired
    private AppUninterestWordMng uninterestWordMng;

	@Override
	public List<AppUninterestWord> loadAll() {
		
		return uninterestWordMng.loadAllUninterestWord();
	}

}
