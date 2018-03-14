package com.hww.file.webservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hww.base.util.BeanMapper;
import com.hww.base.util.SnowFlake;
import com.hww.file.common.entity.FileSet;
import com.hww.file.common.manager.FileSetMng;
import com.hww.file.common.vo.FileSetVo;
import com.hww.file.webservice.service.FileSetService;

/**
 * 图集，音频集，视频集(电视剧)
 * @author hewei
 *
 */
@Service("fileSetService")
public class FileSetServiceImpl implements FileSetService {

	@Autowired
	FileSetMng fileSetMng;
	
	@Override
	public void saveOrUpdateFileSet(FileSetVo fileSetVo) {
		Long setId = fileSetVo.getSetId();
		if(setId!=null) {
			FileSet dbEntity = fileSetMng.find(setId);
			if(dbEntity!=null) {
				Integer total = dbEntity.getTotal()+fileSetVo.getTotal();
				dbEntity.setTotal(total);
				fileSetMng.update(dbEntity);
			}else {
				dbEntity = BeanMapper.map(fileSetVo, FileSet.class);
				fileSetMng.save(dbEntity);
			}
		}else {
			FileSet entity = BeanMapper.map(fileSetVo, FileSet.class);
			//SnowFlake flake = new SnowFlake(1, 1);
			//setId = flake.nextId();
			//entity.setSetId(setId);
			fileSetMng.save(entity);
		}
	}

}
