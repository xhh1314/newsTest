package com.hww.sns.webservice.controller;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.hww.base.util.R;
import com.hww.sns.common.entity.SnsReport;
import com.hww.sns.webservice.service.SnsReportService;

@RestController
@RequestMapping("/sns/report")
public class SnsReportController {
	
	@Autowired
	private SnsReportService snsReportService;
	
	
	
	/**
	 * 舉報接口
	 * 
	 */
	
	

	@RequestMapping(value = "report.do", method = RequestMethod.POST)
	@ResponseBody
	public R reportTopics(@RequestParam("topicId") Long topicId,
			@RequestParam(value = "postId", required = false) Long postId,
			@RequestParam("reportContent") String reportContent, @RequestParam("memberId") Long memberId) {
		String result = "举报成功";
		if (reportContent != null && reportContent != "") {
			SnsReport report=new SnsReport();
			if(topicId!=null) {
				report.setSnsContentId(topicId);
				report.setPlateType(1);
				report.setSiteId(1);
				report.setStatus(Short.valueOf("0"));
				report.setCreateTime(new Timestamp(System.currentTimeMillis()));
				report.setAuditstatus(0);
				report.setReportContent(reportContent);
				snsReportService.saveReport(report);
			}else {
				report.setSnsContentId(postId);
				report.setPlateType(2);
				report.setSiteId(1);
				report.setStatus(Short.valueOf("0"));
				report.setCreateTime(new Timestamp(System.currentTimeMillis()));
				report.setAuditstatus(0);
				report.setReportContent(reportContent);
				snsReportService.saveReport(report);
			}
			result = "举报成功";
			return R.ok();
		} else {
			result = "举报失败";
			return R.error(1, result);
		}
	}

}
