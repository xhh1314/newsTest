package com.hww.cms.webservice.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.hww.cms.CmsWebServiceApplication;
import com.hww.cms.webservice.controller.CmsColumnController;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CmsWebServiceApplication.class)
@WebAppConfiguration
public class CmsCategoryQueryTest {

	@Autowired
	private CmsColumnController cmsColumnController;

	@Test
	public void test() {
//		cmsColumnController.query();
		cmsColumnController.editColumn(9, 1);
		//cmsColumnController.myColumn(9);
	}

}
