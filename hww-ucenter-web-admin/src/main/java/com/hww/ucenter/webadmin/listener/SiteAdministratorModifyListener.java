//package com.hww.ucenter.webadmin.listener;
//
//import java.io.File;
//import java.util.Map;
//
//import org.springframework.beans.factory.annotation.Autowired;
//
//import com.hww.base.common.listener.ModifyListenerAdapter;
//import com.hww.framework.web.mvc.RealPathResolver;
//import com.hww.sys.common.entity.SysSite;
//
//public class SiteAdministratorModifyListener extends ModifyListenerAdapter<SysSite> {
//
//	@Autowired
//	RealPathResolver realPathResolver;
//
//	@Override
//	public void afterSave(SysSite entity) {
//		// TODO Auto-generated method stub
//		super.afterSave(entity);
//		String realPath = realPathResolver.get("/WEB-INF/view/access/1/");
//		java.io.File accessFile = new java.io.File(realPath);
//		if (accessFile.exists() && accessFile.isDirectory()) {
//			File myAccessFile = new File(realPath + "/" + entity.getSiteId());
//			boolean ret = myAccessFile.mkdir();
//			System.out.println("创建目录" + ret);
//
//		}
//
//	}
//
//	@Override
//	public void afterChange(SysSite entity, Map<String, Object> map) {
//		// TODO Auto-generated method stub
//		super.afterChange(entity, map);
//		String realPath = realPathResolver.get("/WEB-INF/view/access/1/" + entity.getSiteId());
//		java.io.File myAccessFile = new java.io.File(realPath);
//		if (!myAccessFile.exists()) {
//			boolean ret = myAccessFile.mkdir();
//			System.out.println("创建目录" + ret);
//
//		}
//	}
//
//}
