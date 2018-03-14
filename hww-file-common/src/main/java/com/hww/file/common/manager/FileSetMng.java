package com.hww.file.common.manager;

import com.hww.base.common.manager.IBaseEntityMng;
import com.hww.file.common.dao.FileSetDao;
import com.hww.file.common.entity.FileSet;

public interface FileSetMng
	extends
		IBaseEntityMng<Long, FileSet, FileSetDao>
{

}
