package com.hww.cms.webadmin.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hww.base.common.page.Pagination;
import com.hww.base.common.util.Finder;
import com.hww.base.util.ArrayUtils;
import com.hww.base.util.BeanMapper;
import com.hww.base.util.R;
import com.hww.base.util.SnowFlake;
import com.hww.base.util.StringUtils;
import com.hww.cms.common.Constants;
import com.hww.cms.common.entity.CmsCategory;
import com.hww.cms.common.entity.CmsContent;
import com.hww.cms.common.entity.CmsContentAuditFlow;
import com.hww.cms.common.entity.CmsContentData;
import com.hww.cms.common.entity.CmsContentType;
import com.hww.cms.common.entity.CmsNewEsSyncFail;
import com.hww.cms.common.entity.CmsOrigin;
import com.hww.cms.common.entity.CmsSpecial;
import com.hww.cms.common.manager.CmsCategoryMng;
import com.hww.cms.common.manager.CmsContentDataMng;
import com.hww.cms.common.manager.CmsContentMng;
import com.hww.cms.common.manager.CmsContentTypeMng;
import com.hww.cms.common.manager.CmsOriginMng;
import com.hww.cms.common.util.EsContentCovertUtil;
import com.hww.cms.common.vo.CmsContentAuditFlowVo;
import com.hww.cms.common.vo.CmsContentDataVo;
import com.hww.cms.common.vo.CmsContentVo;
import com.hww.cms.webadmin.CmsAdminConstants;
import com.hww.cms.webadmin.service.CmsContentAuditFlowService;
import com.hww.cms.webadmin.service.CmsContentAuditService;
import com.hww.cms.webadmin.service.CmsContentService;
import com.hww.cms.webadmin.service.CmsNewEsSyncFailService;
import com.hww.cms.webadmin.service.CmsOriginService;
import com.hww.cms.webadmin.service.CmsSpecialService;
import com.hww.els.api.ElsFeignClient;
import com.hww.els.common.entity.ESContent;
import com.hww.file.api.FileFeignClient;
import com.hww.file.common.dto.FileInfoDto;
import com.hww.framework.common.exception.HServiceLogicException;
import com.hww.framework.web.mvc.RealPathResolver;
import com.hww.framework.web.mvc.controller.AbsBaseController;
import com.hww.framework.web.session.SessionProvider;
import com.hww.sys.common.dto.SysUserDto;
import com.hww.sys.common.util.SysUtils;
import freemarker.template.Configuration;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestOperations;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/content")
public class CmsContentController extends AbsBaseController {

	private static final Logger log = LoggerFactory.getLogger(CmsContentController.class);

	@Autowired
	CmsCategoryMng cmsCategoryMng;

	@Autowired
	CmsContentMng cmsContentMng;

	@Autowired
	CmsContentService cmsContentService;

	@Autowired
	CmsContentDataMng cmsContentDataMng;

	@Autowired
	ElsFeignClient elsFeignClient;

	@Autowired
	Configuration publishFreeMarkerConfiguration;

	@Autowired
	CmsContentTypeMng cmsContentTypeMng;

	@Autowired
	RealPathResolver realPathResolver;

	@Autowired
	FileFeignClient fileFeignClient;

	@Autowired
	private RestOperations restOperations;

	@Autowired
	private CmsNewEsSyncFailService cmsNewEsSyncFailService;

	@Autowired
	private CmsContentAuditFlowService cmsContentAuditFlowService;

	@Resource
	private SessionProvider session;

	@Resource
	private CmsOriginMng cmsOriginMng;
	@Autowired
	private CmsOriginService cmsOriginService;

	@Value("${file.viewUrl}")
	private String fileViewUrl;
	@Value("${nginx.viewPrefix}")
	private String nginxViewPrefix;
	@Value("${revertMapUrl}")
	private String revertMapUrl;
	@Value("${contentUrl}")
	private String contentUrl;

	@RequestMapping(value = "index.do")
	public String index(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		return "content/index";
	}

	@RequestMapping(value = "v_location_list.do")
	public String listByLocation(HttpServletRequest request, HttpServletResponse response, ModelMap model,
			CmsContentVo form, Integer flagLocation) {

		Finder typeSql = Finder.create("from ");
		typeSql.append(CmsContentType.class.getName());
		typeSql.append(" where 1=1");
		Pagination types = cmsContentTypeMng.find(typeSql, form.getPageNo(), 100);
		List<CmsOrigin> list = cmsOriginService.listOrigin();
		List<CmsContentAuditFlow> auditList = cmsContentAuditFlowService.getList(new CmsContentAuditFlowVo());

		Finder categorySql = Finder.create("from ");
		categorySql.append(CmsCategory.class.getName());
		categorySql.append(" where 1=1 and status>0");
		List<CmsCategory> listCategory = (List<CmsCategory>) cmsCategoryMng.find(categorySql);

		List<CmsContent> listContent = cmsContentService.getListByLocation(form);
		Long count = cmsContentService.countContent();

		Pagination p = new Pagination(form.getPageNo(), form.getPageSize(), count, listContent);

		model.addAttribute("page", p);
		model.addAttribute("form", form);
		model.addAttribute("typeList", types.getList());
		model.addAttribute("auditList", auditList);
		model.addAttribute("originList", list);
		model.addAttribute("categoryList", listCategory);
		model.addAttribute("flagLocation", flagLocation);
		return "content/list";
	}

	@RequestMapping(value = "v_list.do")
	public String list(HttpServletRequest request, HttpServletResponse response, ModelMap model, CmsContentVo form,
			Integer flagLocation) {
		if (form.getCategoryId() != null && form.getCategoryId() == -1) {
			form.setCategoryId(null);
		}
		Integer siteId = 1;
		Finder hql = Finder.create("from ");
		hql.append(CmsContent.class.getName());
		hql.append(" where 1=1");
		// hql.append(" and siteId=:siteIdP").setParam("siteIdP", siteId);
		if (form.getCategoryId() != null) {
			hql.append(" and id.categoryId=:categoryIdP").setParam("categoryIdP", form.getCategoryId());
		}

		if (form.getContenttypeId() != null && form.getContenttypeId() != 0) {
			hql.append(" and contenttypeId=:contentTypeIdP").setParam("contentTypeIdP", form.getContenttypeId());
		}

		if (StringUtils.isNotBlank(form.getTitle())) {
			hql.append(" and title like :titleP").setParam("titleP", "%" + form.getTitle() + "%");
		}
		if (StringUtils.isNotBlank(form.getSummary())) {
			hql.append(" and summary like :summaryP").setParam("summaryP", "%" + form.getSummary() + "%");
		}
		hql.append(" and status=:statusP").setParam("statusP", Short.valueOf("1"));
		if (form.getAuditStatus() != null) {
			hql.append(" and auditStatus=:auditStatusP").setParam("auditStatusP", form.getAuditStatus());
		}

		hql.append(" order by top desc, releaseTime desc");
		hql.append(" ,lastModifyTime desc");
		hql.append(" ,createTime desc");

		Finder typeSql = Finder.create("from ");
		typeSql.append(CmsContentType.class.getName());
		typeSql.append(" where 1=1");
		Pagination types = cmsContentTypeMng.find(typeSql, form.getPageNo(), 100);
		List<CmsOrigin> list = cmsOriginService.listOrigin();
		List<CmsContentAuditFlow> auditList = cmsContentAuditFlowService.getList(new CmsContentAuditFlowVo());

		Finder categorySql = Finder.create("from ");
		categorySql.append(CmsCategory.class.getName());
		categorySql.append(" where 1=1 and status>0");
		List<CmsCategory> listCategory = (List<CmsCategory>) cmsCategoryMng.find(categorySql);

		log.debug(hql.getOrigHql());
		Pagination p = cmsContentMng.find(hql, form.getPageNo(), 10);

		model.addAttribute("page", p);
		model.addAttribute("form", form);
		model.addAttribute("typeList", types.getList());
		model.addAttribute("auditList", auditList);
		model.addAttribute("originList", list);
		model.addAttribute("categoryList", listCategory);
		model.addAttribute("flagLocation", flagLocation);
		return "content/list";

	}

	/**
	 * 新闻内容审核列表页,不显示内容
	 * 
	 * @return
	 */
	@RequestMapping(value = "audit_list.do")
	public String auditList(HttpServletRequest request, HttpServletResponse response, ModelMap model,
			CmsContentVo form) {
		// 只显示未审核通过的
		// form.setAuditStatus(Constants.auditStatus.committed);
		Pagination contents = cmsContentMng.listCmsContent(form);
		model.addAttribute("page", contents);
		model.addAttribute("searchVo", form);

		return "audit/audit_list";

	}

	/**
	 * 新闻详情
	 * 
	 * @return
	 */
	@RequestMapping(value = "detail.do")
	public String newsDetail(HttpServletRequest request, HttpServletResponse response, ModelMap model,
			CmsContentVo vo) {
		CmsContent c = cmsContentMng.findOneByContentId(vo.getContentId());
		CmsContentType type = cmsContentTypeMng.find(c.getContenttypeId());
		c.setContentType(type);
		CmsContentData cData = cmsContentDataMng.find(vo.getContentId());
		model.addAttribute("news", c);
		model.addAttribute("dataContent", cData);
		// 图集
		if (c.getContentType().getContentTypeId() == 5 && c.getAttachmentIds() != null) {
			String picJson = fileFeignClient.getUrlsByIds(c.getAttachmentIds());
			if (picJson != null && picJson.length() > 0) {
				JSONArray array = JSONArray.parseArray(picJson);
				for (int i = 0; i < array.size(); i++) {
					JSONObject obj = (JSONObject) array.get(i);
					obj.put("fileId", obj.getLong("fileId") + "");
				}
				picJson = array.toJSONString();
			}

			model.addAttribute("picJson", picJson);
		}
		if (c.getThumbIds() != null) {
			String urls = fileFeignClient.getUrlByFileIdFeiApi(c.getThumbIds());
			c.setThumbUrl(urls);
		}
		if (c.getContentType().getContentTypeId() == 6 && c.getAttachmentIds() != null) {
			String urls = fileFeignClient.getUrlByVideoId(c.getAttachmentIds());
			model.addAttribute("vidUrls", urls);
		}
		String urls = fileFeignClient.getUrlByFileIdFeiApi(c.getThumbIds());
		model.addAttribute("thumbUrls", urls);
		return "content/detail";
	}

	@RequestMapping("v_add.do")
	public String add(HttpServletRequest request, ModelMap model, Integer categoryId) {
		List<CmsContentType> typeList = (List<CmsContentType>) cmsContentTypeMng.find(1, 100).getList();

		model.addAttribute("categoryId", categoryId);
		model.addAttribute("contentTypeList", typeList);

		return "content/add";
	}

	@RequestMapping("v_edit.do")
	public String edit(HttpServletRequest request, HttpServletResponse response, CmsContentVo form, ModelMap model) {

		CmsContentVo content = new CmsContentVo();
		if (form.getContentId() != null && form.getCategoryId() != null) {
			content = cmsContentService.findCmsContentById(form.getContentId(), form.getCategoryId());
		}

		CmsContentData contentData = cmsContentDataMng.find(form.getContentId());
		CmsContent csmcontent = cmsContentMng.findOneByContentId(form.getContentId());
		CmsContentVo cmsContentVo = BeanMapper.map(csmcontent, CmsContentVo.class);
		String jsonFile = "";
		List<FileInfoDto> fileList = new ArrayList<FileInfoDto>();
		try {
			if (cmsContentVo.getContenttypeId() == 5 && cmsContentVo.getAttachmentIds() != null) {
				String urls = fileFeignClient.getUrlByFileIdFeiApi(cmsContentVo.getAttachmentIds());
				jsonFile = fileFeignClient.getUrlsByIds(cmsContentVo.getAttachmentIds());
				if (jsonFile != null && jsonFile.length() > 0) {
					JSONArray array = JSONArray.parseArray(jsonFile);
					for (int i = 0; i < array.size(); i++) {
						JSONObject obj = (JSONObject) array.get(i);
						obj.put("fileId", obj.getLong("fileId") + "");
					}
					jsonFile = array.toJSONString();

				}

				String urlss = fileFeignClient.getUrlByFileIdFeiApi(cmsContentVo.getThumbIds());
				cmsContentVo.setThumbUrl(urlss);
			} else {
				String urls = fileFeignClient.getUrlByFileIdFeiApi(cmsContentVo.getThumbIds());
				cmsContentVo.setThumbUrl(urls);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<CmsContentType> typeList = (List<CmsContentType>) cmsContentTypeMng.find(1, 100).getList();// 防止类型太多出错！！！
		CmsContentAuditFlowVo cmsContentAuditFlowVo = new CmsContentAuditFlowVo();
		if (csmcontent.getAuditFlowId() != null) {
			cmsContentAuditFlowVo.setFlowId(csmcontent.getAuditFlowId());
		}
		// List<CmsContentAuditFlow> auditList =
		// cmsContentAuditFlowService.getList(cmsContentAuditFlowVo);
		try {
			if (cmsContentVo.getContenttypeId() == 6 && csmcontent.getAttachmentIds() != null) {
				String videoUrl = fileFeignClient.getUrlByVideoId(csmcontent.getAttachmentIds());
				cmsContentVo.setVedioUrl(videoUrl);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Finder f = Finder.create("from CmsContent where 1=1");
		f.append(" and contentId=:ContentIdP").setParam("ContentIdP", form.getContentId());
		f.append("and status>0");
		List<CmsContent> l = (List<CmsContent>) cmsContentMng.find(f);
		String categoryIds = "";
		for (CmsContent c : l) {
			categoryIds = categoryIds + c.getCategoryId() + ",";
		}
		categoryIds = categoryIds.substring(0, categoryIds.length() - 1);
		cmsContentVo.setCategoryIds(categoryIds);
		List<CmsOrigin> list = cmsOriginService.listOrigin();
		model.addAttribute("csmcontent", cmsContentVo);
		model.addAttribute("contentTypeList", typeList);
		model.addAttribute("contentData", contentData);
		model.addAttribute("picJson", jsonFile);
		model.addAttribute("r", content);
		model.addAttribute("form", form);
		model.addAttribute("originList",list);
		// model.addAttribute("audit", auditList.get(0));
		if (csmcontent.getContenttypeId() != null) {
			if (csmcontent.getContenttypeId() == Constants.contentType.picCollect) {
				// 如果是图集类型
				return "content/tuji_edit";
			} else if (csmcontent.getContenttypeId() == Constants.contentType.video) {
				// 如果是视频类型
				return "content/video_edit";
			}
		}
		return "content/tuwen_edit";
	}

	@RequestMapping("o_save.do")
	@ResponseBody
	public R save(HttpServletRequest request, @Valid @ModelAttribute("form") CmsContentVo form, BindingResult errors) {
		if (form.getContent() != null && form.getContent().startsWith(",")) {
			form.setContent(form.getContent().substring(1));
		}
		if (errors.hasErrors()) {
			log.debug(getErrorMessage(errors));
			return R.error(errors.getAllErrors().get(0).getDefaultMessage());
		}
		SysUserDto user = (SysUserDto) session.getAttribute(request, "login_user");
		form.setEditor(user.getUsername());
		form.setAuditHisRecord(1);
		// -----location的值，就用前台传送过来的，编辑可能会个性化更改,如果是空值，再逆向解码位置----
		// 调用百度地图接口获取location
		// 逆向地理解码API
		if (form.getLocation()==null && form.getLongitude() != null && form.getLatitude() != null) {
			reverseParseMapLocation(form);
		}

		if (form.getContenttypeId() != null) {
			CmsContentType type = new CmsContentType();
			type.setContentTypeId(form.getContenttypeId());
			form.setContentType(type);
		}

		String thumb = null;

		CmsContentData contentData = new CmsContentData();
		// 新闻内容
		String content = request.getParameter("content");
		contentData.setContent(content);
		try {
			BeanUtils.copyProperties(contentData, form);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		contentData.setCreateTime(new Timestamp(System.currentTimeMillis()));
		contentData.setStatus(Constants.CONTENT_STATUS_NEW);// 直接发布
		Integer siteId = 1;
		contentData.setSiteId(siteId);
		contentData.setSrcCategoryId(form.getSrcCategoryId());
		form.setCreateTime(new Timestamp(System.currentTimeMillis()));
		form.setAuditStatus(0);
		form.setLinkUrl(contentUrl);

		try {
			cmsContentDataMng.saveOrUpdateContentAndRelationshipWithCategory(contentData, form, true);
		} catch (Exception e) {
			e.printStackTrace();
			return R.error("保存错误！").put("result", 0);
		}
		try {
			ESContent eSContent = EsContentCovertUtil.toEsContent(form);
			R r = elsFeignClient.createContentFeginApi(eSContent);
			if (!r.isOk()) {

				// CmsNewEsSyncFail cmsNewEsSyncFail = new CmsNewEsSyncFail();
				// cmsNewEsSyncFail.setContentId(form.getContentId());
				// cmsNewEsSyncFail.setCreateTime(new Timestamp(System.currentTimeMillis()));
				// cmsNewEsSyncFail.setFailWhat(0);
				// cmsNewEsSyncFail.setIsDealSuccess(0);
				// cmsNewEsSyncFailService.save(cmsNewEsSyncFail);

				// throw new HServiceLogicException("同步失败：" + r.getMsg());
			}

		} catch (Exception e) {

			// e.printStackTrace();
			// CmsNewEsSyncFail cmsNewEsSyncFail = new CmsNewEsSyncFail();
			// cmsNewEsSyncFail.setContentId(form.getContentId());
			// cmsNewEsSyncFail.setCreateTime(new Timestamp(System.currentTimeMillis()));
			// cmsNewEsSyncFail.setFailWhat(0);
			// cmsNewEsSyncFail.setIsDealSuccess(0);
			// cmsNewEsSyncFailService.save(cmsNewEsSyncFail);
			// throw new HServiceLogicException("同步失败：" + e.getMessage());
		}

		return R.ok("操作成功").put("result", 1);
	}

	@RequestMapping("o_update.do")
	public R update(@RequestParam MultipartFile myfile, @Valid @ModelAttribute("form") CmsContentVo form,
			BindingResult errors) {
		if (errors.hasErrors()) {
			return R.error(errors.getAllErrors().get(0).getDefaultMessage());
		}
		String thumb = null;
		if (myfile.isEmpty()) {
			System.out.println("文件未上传");
		} else {
			// String realPath = request.getSession().getServletContext()
			// .getRealPath("/ueditor/jsp/upload/image/");
			String realPath = realPathResolver.get("/ueditor/jsp/upload/image/");
			String fileName = makeFileName(myfile.getOriginalFilename());

			try {
				FileUtils.copyInputStreamToFile(myfile.getInputStream(), new File(realPath, fileName));
				thumb = "/ueditor/jsp/upload/image/" + fileName;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// 更新的时候修改rcontentcategory 不会修改原来的稿件
		CmsContentData contentData = cmsContentDataMng.find(form.getContentId());
		// entity.setTitle(form.getTitle());
		// entity.setShortTitle(form.getShortTitle());
		// entity.setContent(form.getContent());
		contentData.setStatus(Constants.CONTENT_STATUS_FINAL);// 直接发布

		cmsContentDataMng.saveOrUpdateContentAndRelationshipWithCategory(contentData, form, null);

		return R.ok("操作成功");
	}

	private String makeFileName(String filename) {
		return UUID.randomUUID().toString() + "_" + filename;
	}

	@RequestMapping("o_delete.do")
	@ResponseBody
	public Boolean delete(HttpServletRequest request, HttpServletResponse response, CmsContentVo form) {
		List<Long> contentIds = null;
		if (form != null) {

			if (StringUtils.isNotBlank(form.getAllIDCheck())) {
				String[] ids = StringUtils.split(form.getAllIDCheck(), ",");
				if (ArrayUtils.isNotEmpty(ids)) {
					contentIds = new ArrayList<Long>(ids.length);
				}
				for (String str : ids) {
					if (StringUtils.isNumeric(str)) {
						contentIds.add(Long.parseLong((str)));
					}
				}
			}
			if (form.getContentId() != null) {// 单个删除
				contentIds = new ArrayList<Long>(1);
				contentIds.add(form.getContentId());
			}
		}
		if (contentIds != null) {
			List<CmsContent> list = cmsContentMng.updateRelationshipStatus(form.getCategoryId(), contentIds,
					Short.valueOf("0"));
			if (list != null) {
				for (CmsContent entity : list) {
					CmsContentVo cmsContentVo = BeanMapper.map(entity, CmsContentVo.class);
					try {
						ESContent eSContent = EsContentCovertUtil.toEsContent(cmsContentVo);
						R r = elsFeignClient.createContentFeginApi(eSContent);
						if (!r.isOk()) {
							/*
							 * CmsNewEsSyncFail cmsNewEsSyncFail = new CmsNewEsSyncFail();
							 * cmsNewEsSyncFail.setContentId(cmsContentVo.getContentId());
							 * cmsNewEsSyncFail.setCreateTime(new Timestamp(System.currentTimeMillis()));
							 * cmsNewEsSyncFail.setFailWhat(1); cmsNewEsSyncFail.setIsDealSuccess(0);
							 * cmsNewEsSyncFailService.save(cmsNewEsSyncFail);
							 */
							throw new HServiceLogicException("同步失败：" + r.getMsg());
						}

					} catch (Exception e) {
						/*
						 * CmsNewEsSyncFail cmsNewEsSyncFail = new CmsNewEsSyncFail();
						 * cmsNewEsSyncFail.setContentId(cmsContentVo.getContentId());
						 * cmsNewEsSyncFail.setCreateTime(new Timestamp(System.currentTimeMillis()));
						 * cmsNewEsSyncFail.setFailWhat(1); cmsNewEsSyncFail.setIsDealSuccess(0);
						 * cmsNewEsSyncFailService.save(cmsNewEsSyncFail);
						 */
						throw new HServiceLogicException("同步失败：" + e.getMessage());
					}
				}
			}
		}

		return true;
		// toJson(request, response, true);
	}

	/**
	 * tuji module begin 保存或者更新图集
	 * 
	 * @param faceImages
	 */
	@RequestMapping("saveOrUpdatePicCollect.do")
	@ResponseBody
	public String saveOrUpdatePicCollect(HttpServletRequest request, @ModelAttribute("tujiform") CmsContentVo form) {
		String result = "success";
		String categoryIds = form.getCategoryIds();
		if (StringUtils.isEmpty(categoryIds)) {
			result = "没有选择新闻分类!";
			log.debug(result);
			return result;
		}
		if (form.getLocation()==null && form.getLongitude() != null && form.getLatitude() != null) {
			reverseParseMapLocation(form);
		}
		String picIds = request.getParameter("picIds"); // 图集图片ids
		log.info("ids:" + picIds);
		try {
			form.setAttachmentIds(picIds);
			Integer siteId = SysUtils.getSiteId(request);
			form.setSiteId(siteId);
			form.setStatus(Constants.status.usable); // 可用
			form.setContenttypeId(Constants.contentType.picCollect);
			// 新闻默认先审后发
			form.setAuditStatus(Constants.auditStatus.committed);

			cmsContentMng.saveOrUpdateCmsContent(form);
			// 更新这些图片的setId
			fileFeignClient.picSet(picIds);
			log.info(picIds);

		} catch (Exception e) {
			e.printStackTrace();
			result = "error!";
		}
		return result;
	}

	/**
	 * video module begin 保存或者更新视频集
	 * 
	 * @param faceImages
	 */
	@RequestMapping("saveOrUpdateVideo.do")
	@ResponseBody
	public String saveOrUpdateVideo(HttpServletRequest request, @ModelAttribute("videoform") CmsContentVo form) {
		String result = "success";
		String categoryIds = form.getCategoryIds();
		if (StringUtils.isEmpty(categoryIds)) {
			result = "没有选择新闻分类!";
			log.debug(result);
			return result;
		}
		if (form.getLocation()==null && form.getLongitude() != null && form.getLatitude() != null) {
			reverseParseMapLocation(form);
		}
		// 视频urls,前台未转换，此处转换
		String videoUrls = request.getParameter("videoUrls");
		String content = request.getParameter("content");

		if (StringUtils.isNotEmpty(videoUrls)) {
			// 去除nginx访问前缀
			videoUrls = videoUrls.replace(nginxViewPrefix, "");
			videoUrls = fileViewUrl + videoUrls;
			log.debug(videoUrls);

			form.setAttachmentIds(videoUrls);// 附件id,视频为url
		}
		if (StringUtils.isNotEmpty(content)) {
			CmsContentDataVo cmsContentDataVo = new CmsContentDataVo();
			// 内容html
			cmsContentDataVo.setContent(content);
			form.setCmsContentDataVo(cmsContentDataVo);
		}
		Integer siteId = SysUtils.getSiteId(request);
		form.setSiteId(siteId);
		form.setStatus(Constants.status.usable); // 可用
		form.setContenttypeId(Constants.contentType.video);
		// 新闻默认先审后发
		form.setAuditStatus(Constants.auditStatus.committed);
		cmsContentMng.saveOrUpdateCmsContent(form);
		return result;
	}

	private CmsContentVo reverseParseMapLocation(CmsContentVo cmsContentVo){
		try {
			// 入参
			Map<String, Object> requestParam = CmsAdminConstants.getRevertParams();
			requestParam.put("location", cmsContentVo.getLongitude() + "," + cmsContentVo.getLatitude());
			String responseData = restOperations.getForObject(revertMapUrl, String.class, requestParam);
			log.debug(responseData);
			String resultJson = responseData.substring(responseData.indexOf("{"),
					responseData.lastIndexOf("}") + 1);
			JSONObject obj = JSONObject.parseObject(resultJson);
			String status = obj.getString("status");
			if (status.equals("0")) {
				JSONObject resultObj = obj.getJSONObject("result");
				String location = resultObj.getString("formatted_address");
				log.debug(location);
				if (StringUtils.isNotEmpty(location)) {
					cmsContentVo.setLocation(location);
				}
			}
			return cmsContentVo;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return  cmsContentVo;

	}

	/**
	 * 管理员：更新新闻内容
	 *
	 * @author XiaoBG
	 * @date 2018年1月11日 下午9:45:14 param CmsContentVo
	 * @return String
	 */
	@RequestMapping(value = "updateNewWithContent.do")
	@ResponseBody
	public R updateNewWithContent(CmsContentVo cmsContentVo) {
		if (cmsContentVo.getLocation()==null && cmsContentVo.getLongitude() != null && cmsContentVo.getLatitude() != null) {
		reverseParseMapLocation(cmsContentVo);
		}
		log.info("----------CMS系统修改频道信息开始----------");
		try {
			cmsContentService.updateNewWithContent(cmsContentVo);
		} catch (Exception e) {
			e.printStackTrace();
			return R.error("修改失败！").put("result", 0);
		}
		log.info("----------CMS系统修改频道信息结束----------");
		return R.ok("修改完成").put("result", 1);
	}

	@RequestMapping("updateTop.do")
	@ResponseBody
	public R updateTop(HttpServletRequest request, @RequestParam Long contentId, @RequestParam Long categoryId) {
		try {
			CmsContentVo cmsContentVo = cmsContentMng.updateTop(contentId, categoryId);
			try {
				ESContent eSContent = EsContentCovertUtil.toEsContent(cmsContentVo);
				R r = elsFeignClient.createContentFeginApi(eSContent);
				if (!r.isOk()) {
					CmsNewEsSyncFail cmsNewEsSyncFail = new CmsNewEsSyncFail();
					cmsNewEsSyncFail.setContentId(cmsContentVo.getContentId());
					cmsNewEsSyncFail.setCreateTime(new Timestamp(System.currentTimeMillis()));
					cmsNewEsSyncFail.setFailWhat(0);
					cmsNewEsSyncFail.setIsDealSuccess(0);
					cmsNewEsSyncFailService.save(cmsNewEsSyncFail);
					throw new HServiceLogicException("同步失败：" + r.getMsg());
				}
			} catch (Exception e) {
				/*
				 * e.printStackTrace(); CmsNewEsSyncFail cmsNewEsSyncFail = new
				 * CmsNewEsSyncFail();
				 * cmsNewEsSyncFail.setContentId(cmsContentVo.getContentId());
				 * cmsNewEsSyncFail.setCreateTime(new Timestamp(System.currentTimeMillis()));
				 * cmsNewEsSyncFail.setFailWhat(0); cmsNewEsSyncFail.setIsDealSuccess(0);
				 * cmsNewEsSyncFailService.save(cmsNewEsSyncFail);
				 */
				throw new HServiceLogicException("同步失败：" + e.getMessage());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return R.error("操做失败");
		}
		return R.ok("操作成功");
	}

	@RequestMapping("saveESContentByContentId.do")
	@ResponseBody
	public R saveESContentByContentId(HttpServletRequest request, @RequestParam Long contentId) {
		Finder hql = Finder.create("from ");
		hql.append(CmsContentData.class.getName());
		hql.append(" where 1=1");
		hql.append(" order by releaseTime desc");
		try {
			List<CmsContentData> list = (List<CmsContentData>) cmsContentMng.find(hql);
			for (CmsContentData enity : list) {
				cmsContentService.saveESContentByContentId(enity.getContentId());
			}
		} catch (Exception e) {
			e.printStackTrace();
			R.error("操作失败");
		}
		return R.ok("操作成功");
	}

	@RequestMapping("changeDesc.do")
	@ResponseBody
	public FileInfoDto changeDesc(HttpServletRequest request, Long fileId) {
		String jsonFile = fileFeignClient.getUrlsByIds(fileId + "");
		FileInfoDto dto = new FileInfoDto();
		if (jsonFile != null && jsonFile.length() > 0) {
			JSONArray array = JSONArray.parseArray(jsonFile);
			if (array != null && array.size() > 0) {
				JSONObject obj = (JSONObject) array.get(0);
				dto.setFileId(obj.getLong("fileId"));
				dto.setFileDesc(obj.getString("fileDesc"));

				return dto;
			}
		}
		return null;
	}

	@RequestMapping("updateDesc.do")
	@ResponseBody
	public R updateDesc(FileInfoDto dto) {
		try {
			FileInfoDto result = fileFeignClient.undateInfoFeginApi(dto);
			if (result == null) {
				return R.error("更新失败").put("result", 0);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return R.error("更新失败").put("result", 0);
		}
		return R.ok("更新成功").put("result", 1);
	}

	@RequestMapping("content_information.do")
	public String contentInformation(HttpServletRequest request, HttpServletResponse response, ModelMap model,
			CmsContentVo vo) {
		System.out.println(vo.getContentId());
		// cmsContentDataVo
		CmsContent c = cmsContentMng.findOneByContentId(vo.getContentId());
		CmsContentData cData = cmsContentDataMng.find(vo.getContentId());
		model.addAttribute("news", c);
		model.addAttribute("dataContent", cData);
		// 图集
		if (c.getContentType().getContentTypeId() == 5 && c.getAttachmentIds() != null) {
			String picJson = fileFeignClient.getUrlsByIds(c.getAttachmentIds());
			if (picJson != null && picJson.length() > 0) {
				JSONArray array = JSONArray.parseArray(picJson);
				for (int i = 0; i < array.size(); i++) {
					JSONObject obj = (JSONObject) array.get(i);
					obj.put("fileId", obj.getLong("fileId") + "");
				}
				picJson = array.toJSONString();
			}

			model.addAttribute("picJson", picJson);
		}
		if (c.getThumbIds() != null) {
			String urls = fileFeignClient.getUrlByFileIdFeiApi(c.getThumbIds());
			c.setThumbUrl(urls);
		}
		if (c.getContentType().getContentTypeId() == 6 && c.getAttachmentIds() != null) {
			String urls = fileFeignClient.getUrlByVideoId(c.getAttachmentIds());
			model.addAttribute("vidUrls", urls);
		}
		String urls = fileFeignClient.getUrlByFileIdFeiApi(c.getThumbIds());
		model.addAttribute("thumbUrls", urls);
		return "content/detail";
	}

	/**
	 * 地址
	 * @param longitude
	 * @param latitude
	 * @return
	 */
	@RequestMapping("apiAddress.do")
	@ResponseBody
	public String apiAddress(Double longitude, Double latitude) {
		String location = null;
		if (longitude != null && latitude != null) {
			try {
				// 入参
				Map<String, Object> requestParam = CmsAdminConstants.getRevertParams();
				requestParam.put("location", latitude + "," + longitude);
				String responseData = restOperations.getForObject(revertMapUrl, String.class, requestParam);
				log.debug(responseData);
				String resultJson = responseData.substring(responseData.indexOf("{"),
						responseData.lastIndexOf("}") + 1);
				JSONObject obj = JSONObject.parseObject(resultJson);
				String status = obj.getString("status");
				if (status.equals("0")) {
					JSONObject resultObj = obj.getJSONObject("result");
					location = resultObj.getString("formatted_address");
					log.debug(location);
					if (StringUtils.isNotEmpty(location)) {
						return location;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return location;
	}

	@RequestMapping("updateReleaseTime.do")
	@ResponseBody
	public String updateReleaseTime(Long contentId, String ReleaseTime) throws ParseException {
		String result = "success";
		if (contentId != null) {
			CmsContent cmsContent = cmsContentMng.findOneByContentId(contentId);
			java.util.Date date3 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(ReleaseTime);
			Timestamp date = new Timestamp(date3.getTime());
			cmsContent.setReleaseTime(date);
			CmsContentVo vos = BeanMapper.map(cmsContent, CmsContentVo.class);
			cmsContentService.updateContent(vos);
			result = "修改成功";
		} else {
			result = "修改失败";
		}
		return result;

	}

}
