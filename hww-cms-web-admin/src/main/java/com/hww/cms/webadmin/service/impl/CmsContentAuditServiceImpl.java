package com.hww.cms.webadmin.service.impl;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import com.hww.base.common.page.Pagination;
import com.hww.base.common.util.Finder;
import com.hww.base.util.BeanMapper;
import com.hww.base.util.R;
import com.hww.base.util.StringUtils;
import com.hww.cms.common.entity.CmsCategory;
import com.hww.cms.common.entity.CmsContent;
import com.hww.cms.common.entity.CmsContentAudit;
import com.hww.cms.common.entity.CmsContentAuditFlowSteps;
import com.hww.cms.common.entity.CmsContentData;
import com.hww.cms.common.entity.CmsContentType;
import com.hww.cms.common.entity.CmsNewEsSyncFail;
import com.hww.cms.common.manager.CmsCategoryMng;
import com.hww.cms.common.manager.CmsContentAuditFlowStepsMng;
import com.hww.cms.common.manager.CmsContentAuditMng;
import com.hww.cms.common.manager.CmsContentDataMng;
import com.hww.cms.common.manager.CmsContentMng;
import com.hww.cms.common.manager.CmsContentTypeMng;
import com.hww.cms.common.util.EsContentCovertUtil;
import com.hww.cms.common.vo.CmsContentAuditVo;
import com.hww.cms.common.vo.CmsContentVo;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.hww.cms.webadmin.service.CmsCategoryService;
import com.hww.cms.webadmin.service.CmsContentAuditService;
import com.hww.cms.webadmin.service.CmsNewEsSyncFailService;
import com.hww.els.api.ElsFeignClient;
import com.hww.els.common.entity.ESContent;
import com.hww.file.api.FileFeignClient;
import com.hww.framework.common.exception.HServiceLogicException;
import com.hww.sys.api.SysFeignClient;
import com.hww.sys.common.dto.SysRoleDto;
import com.hww.sys.common.dto.SysUserDto;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service("cmsContentAuditService")
@Transactional
public class CmsContentAuditServiceImpl implements CmsContentAuditService {

	@Autowired
	CmsContentAuditMng cmsContentAuditMng;

	@Autowired
	CmsContentMng cmsContentMng;

	@Autowired
	CmsContentAuditFlowStepsMng cmsContentAuditFlowStepsMng;

	@Autowired
	CmsContentDataMng cmsContentDataMng;

	@Autowired
	CmsCategoryMng cmsCategoryMng;
	@Autowired
	private ElsFeignClient elsFeignClient;

	@Autowired
	private CmsContentTypeMng cmsContentTypeMng;

	@Autowired
	FileFeignClient fileFeignClient;

	@Value("${contentXmlFilePath}")
	private String xmlPath;

	@Autowired
	private CmsNewEsSyncFailService cmsNewEsSyncFailService;
	@Resource
	private SysFeignClient sysFeignClient;

	@Override
	public void save(CmsContentAuditVo vo) {
		vo.setCreateTime(new Timestamp(System.currentTimeMillis()));
		vo.setLastModifyTime(new Timestamp(System.currentTimeMillis()));
		vo.setStatus(Short.valueOf("1"));
		CmsContentAudit entity = BeanMapper.map(vo, CmsContentAudit.class);
		cmsContentAuditMng.save(entity);

	}

	@Override
	public CmsContentAuditVo findByContentIdAndCategoryId(Long ContentId, Long CategortId) {
		Finder finder = Finder.create("from CmsContentAudit where 1=1");
		finder.append(" and ContentId=:ContentId").setParam("ContentId", ContentId);
		finder.append(" and CategortId=:CategortId").setParam("CategortId", CategortId);
		CmsContentAudit entity = (CmsContentAudit) cmsContentAuditMng.find(finder);
		CmsContentAuditVo vo = BeanMapper.map(entity, CmsContentAuditVo.class);
		return vo;
	}

	@Override
	public void ContentAuditupdate(CmsContentAuditVo vo) {
		CmsContentAudit entity = BeanMapper.map(vo, CmsContentAudit.class);
		cmsContentAuditMng.update(entity);
	}

	@Override
	public Pagination getMyAuditContent(CmsContentVo vo,SysUserDto user) {

		// Finder hqlFlow = Finder.create("from CmsContentAuditFlowSteps");
		// hqlFlow.append("where 1=1 and status>0");
		// hqlFlow.append("and roleId=:RoleIdP").setParam("RoleIdP", roleId);
		// hqlFlow.append("group by flow.flowId");
		// List<CmsContentAuditFlowSteps> listSteps = (List<CmsContentAuditFlowSteps>)
		// cmsContentAuditMng.find(hqlFlow);
		//
		// List<Long> listFlows = new ArrayList<Long>();
		// for (CmsContentAuditFlowSteps c : listSteps) {
		// listFlows.add(c.getFlow().getFlowId());
		// }
		// Pagination p=null;
		// Finder hql = Finder.create("from CmsContent");
		// hql.append(" where (auditstatus=0 or auditstatus=null)");
		// hql.append(" and status>0");
		// hql.append(" and auditFlowId in:FlowList").setParam("FlowList", listFlows);
		// hql.append(" order by contentId asc");
		// p = cmsContentAuditMng.find(hql, vo.getPageNo(), vo.getPageSize());
		//
		// return p;
		List<SysRoleDto> roleDto= sysFeignClient.findRoleByUserId(user.getUserId());
		Long count =0L;
		for(SysRoleDto s:roleDto) {
			if(s.getRoleId()!=null) {
		Long i= cmsContentAuditMng.getMyAuditContentCount(vo, s.getRoleId());
		count+=i;
			}
		}
		List<CmsContent> list =new ArrayList<CmsContent>();
		for(SysRoleDto s:roleDto) {
			if(s.getRoleId()!=null) {
		List<CmsContent> cmslist = cmsContentAuditMng.getMyAuditContent(vo, s.getRoleId());
		for (int i = 0; i < cmslist.size(); i++) {
			CmsContent c = cmslist.get(i);
			if (c.getContentType() == null) {
				if (c.getContenttypeId() != null) {
					CmsContentType type = cmsContentTypeMng.find(c.getContenttypeId());
					c.setContentType(type);
					//list.remove(i);
					list.add(i, c);
				}
			}
		}
			}
		}
		/*for (int i = 0; i < list.size(); i++) {
			CmsContent c = list.get(i);
			if (c.getContentType() == null) {
				if (c.getContenttypeId() != null) {
					CmsContentType type = cmsContentTypeMng.find(c.getContenttypeId());
					c.setContentType(type);
					list.remove(i);
					list.add(i, c);
				}
			}
		}*/

		Pagination p2 = new Pagination(vo.getPageNo(), 10, count, list);
		return p2;
	}

	@Override
	public boolean auditResultUpdate(CmsContentVo vo, Long roleId, SysUserDto user) {
		// TODO Auto-generated method stub
		Finder hqlContent = Finder.create("from CmsContent where 1=1");
		hqlContent.append(" and contentId=:ContentIdP").setParam("ContentIdP", vo.getContentId());
		List<CmsContent> entitys = (List<CmsContent>) cmsContentAuditMng.find(hqlContent);

		CmsContentData data = cmsContentDataMng.find(entitys.get(0).getContentId());
		CmsCategory category = cmsCategoryMng.find(data.getSrcCategoryId());

		boolean flag = false;
		String entityId = "";
		CmsContent oneEntity = null;

		for (CmsContent entity : entitys) {

			Long flowId = category.getFlowId();
			Integer flowStep = (entity.getAuditStep() == null ? 0 : entity.getAuditStep()) + 1;
			Finder flowSteps = Finder.create("from CmsContentAuditFlowSteps");
			flowSteps.append("where 1=1");
			flowSteps.append(" and flow.flowId=:FlowIdP").setParam("FlowIdP", flowId);
			flowSteps.append(" and roleId=:RoleIdP").setParam("RoleIdP", roleId);

			List<CmsContentAuditFlowSteps> cmsContentAuditFlowStepss = (List<CmsContentAuditFlowSteps>) cmsContentAuditFlowStepsMng
					.find(flowSteps);
			if (cmsContentAuditFlowStepss == null || cmsContentAuditFlowStepss.size() < 1) {
				return false;
			}
			int i = 0;
			for (i = 0; i < cmsContentAuditFlowStepss.size(); i++) {
				if (cmsContentAuditFlowStepss.get(i).getStepIndex() == flowStep) {
					break;
				}
			}
			if (i == cmsContentAuditFlowStepss.size()) {
				flowStep = cmsContentAuditFlowStepss.get(0).getStepIndex();
			}

			Finder flowStepsCount = Finder.create("select count(*) from CmsContentAuditFlowSteps");
			flowStepsCount.append("where 1=1");
			flowStepsCount.append(" and flow.flowId=:FlowIdP").setParam("FlowIdP", flowId);
			flowStepsCount.append(" and status>0");

			Integer countSteps = Integer
					.parseInt(((((List<Long>) cmsContentAuditFlowStepsMng.find(flowStepsCount)).get(0)) + ""));

			if (entity.getAuditHisRecord() == null) {
				entity.setAuditHisRecord(1);
			}
			if (entity.getAuditHisRecord() == 0) {
				entity.setAuditHisRecord(1);
			}
			if (vo.getAuditStepResult() == 1 && countSteps == flowStep) {
				entity.setAuditStep(flowStep);
				entity.setAuditStepResult(1);
				entity.setAuditStatus(1);
				entity.setReleaseTime(new Timestamp(System.currentTimeMillis()));
				flag = true;
				entityId = entity.getContentId() + "";
				oneEntity = entity;
			} else if (vo.getAuditStepResult() == 1 && countSteps != flowStep) {
				entity.setAuditStep(flowStep);
				entity.setAuditStepResult(1);
				entity.setAuditStatus(0);
				if (countSteps < flowStep) {

				}
			} else {
				entity.setAuditStep(flowStep);
				entity.setAuditStepResult(0);
				entity.setAuditStatus(2);
			}
			entity.setLastModifyTime(new Timestamp(System.currentTimeMillis()));
			CmsContentData cmsContentData = cmsContentDataMng.find(entity.getContentId());
			CmsContentVo cmsContentVo = BeanMapper.map(entity, CmsContentVo.class);
			if (cmsContentData.getContent() != null) {
				cmsContentVo.setContent(cmsContentData.getContent());
			}

			try {
				//EsContentCovertUtil eSContentUtil = new EsContentCovertUtil();
				ESContent eSContent =EsContentCovertUtil.toEsContent(cmsContentVo);
				R r = elsFeignClient.createContentFeginApi(eSContent);
				if (r.getStatusCode() == 500) {
					throw new HServiceLogicException("同步失败：" + r.getMsg());
				}
			} catch (Exception e) {
				throw new HServiceLogicException("同步失败：" + e.getMessage());
			}

			cmsContentMng.update(entity);

			CmsContentAudit auditEntity = new CmsContentAudit();
			auditEntity.setAuditFlowId(entity.getAuditFlowId());
			auditEntity.setAuditHisRecord(entity.getAuditHisRecord());
			auditEntity.setAuditRole(roleId);
			auditEntity.setAuditStep(flowStep);
			auditEntity.setAuditUser(user.getUserId());
			auditEntity.setAuditUserStr(user.getUsername());
			auditEntity.setAutitStepResult(vo.getAuditStepResult());
			auditEntity.setContentId(entity.getContentId());
			auditEntity.setCreateTime(new Timestamp(System.currentTimeMillis()));
			auditEntity.setLastModifyTime(new Timestamp(System.currentTimeMillis()));
			auditEntity.setSiteId(1);
			auditEntity.setStatus(Short.parseShort("1"));
			try {
				cmsContentAuditMng.save(auditEntity);
			} catch (Exception e) {
				entity.setAuditStep(flowStep - 1);
				entity.setAuditStepResult(1);
				entity.setAuditStatus(0);
				cmsContentMng.update(entity);
			}
		}

		// 保存文件
		if (flag) {
			String path = xmlPath;
			File file = new File(path);
			if (!file.exists()) {
				file.mkdirs();
			}
			file = new File(file, entityId + ".xml");
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder;
			try {
				builder = factory.newDocumentBuilder();
				Document document = builder.newDocument();

				Element news = document.createElement("news");

				Element op = setChild("op", "操作类型", "add", document);
				news.appendChild(op);

				Element id = setChild("id", "文章id", oneEntity.getContentId() + "", document);
				news.appendChild(id);

				Element roleid = setChild("roleid", "角色ID", roleId + "", document);
				news.appendChild(roleid);

				Element userid = setChild("userid", "发布人", user.getUserId() + "", document);
				news.appendChild(userid);

				Element releasid = setChild("releasid", "审核人ID", oneEntity.getContentId() + "", document);
				news.appendChild(releasid);

				Element catid = setChild("catid", "栏目ID", data.getSrcCategoryId() + "", document);
				news.appendChild(catid);

				Element title = setChild("title", "标题", oneEntity.getTitle() + "", document);
				news.appendChild(title);

				Element pretitle = setChild("pretitle", "肩标题", "", document);
				news.appendChild(pretitle);

				Element subtitle = setChild("subtitle", "副标题", "", document);
				news.appendChild(subtitle);

				Element shorttitle = setChild("shorttitle", "短标题", data.getShortTitle(), document);
				news.appendChild(shorttitle);

				Element keywords = setChild("keywords", "关键字", data.getKeywordIds(), document);
				news.appendChild(keywords);

				Element author = setChild("author", "作者", data.getAuthor(), document);
				news.appendChild(author);

				Element copyfrom = setChild("copyfrom", "来源", data.getOrigin().getOriginName() + "", document);
				news.appendChild(copyfrom);

				Element weight = setChild("weight", "权重", oneEntity.getPriority() + "", document);
				news.appendChild(weight);

				/*
				 * Element thumb = setChild("thumb", "缩略图", "" , document);
				 * news.appendChild(thumb);
				 */

				Element thumb = document.createElement("thumb");
				Element name = document.createElement("name");
				name.setTextContent("缩略图");
				thumb.appendChild(name);
				String[] thumbs = oneEntity.getThumbUrl().split(",");
				for (String url : thumbs) {
					Element value = document.createElement("value");
					value.setTextContent(url);
					thumb.appendChild(value);
				}
				news.appendChild(thumb);

				Element relation = setChild("relation", "相关文章", "", document);
				news.appendChild(relation);

				Element inputtime = setChild("inputtime", "发布时间", oneEntity.getReleaseTime() + "", document);
				news.appendChild(inputtime);

				Element islink = setChild("islink", "跳转链接标记", "1", document);
				news.appendChild(islink);

				Element linkurl = setChild("linkurl", "跳转链接", oneEntity.getLinkUrl() + "", document);
				news.appendChild(linkurl);

				Element template = setChild("template", "模板", "", document);
				news.appendChild(template);

				Element allow_comment = setChild("allow_comment", "允许评论", "", document);
				news.appendChild(allow_comment);

				Element posids = setChild("posids", "推荐位ID", oneEntity.getManuallySortNum() + "", document);
				news.appendChild(posids);

				Element description = setChild("description", "摘要", oneEntity.getSummary() + "", document);
				news.appendChild(description);

				Element content = setChild("content", "正文", data.getContent() + "", document);
				news.appendChild(content);

				Element pictureurls = document.createElement("pictureurls");
				name = document.createElement("name");
				name.setTextContent("附件图片");
				pictureurls.appendChild(name);

				if (oneEntity.getContenttypeId() == 5) {
					String picUrl = fileFeignClient.getUrlByFileIdFeiApi(oneEntity.getAttachmentIds());
					String[] picUrls = picUrl.split(",");
					for (String url : picUrls) {
						Element value = document.createElement("value");
						value.setTextContent(url);
						pictureurls.appendChild(value);

					}

				} else {
					Element value = document.createElement("value");
					value.setTextContent("");
					pictureurls.appendChild(value);
				}
				news.appendChild(pictureurls);

				Element videourl = document.createElement("videourl");
				name = document.createElement("name");
				name.setTextContent("视频地址");
				videourl.appendChild(name);

				if (oneEntity.getContenttypeId() == 6) {
					String picUrl = fileFeignClient.getUrlByVideoId(oneEntity.getAttachmentIds());
					String[] picUrls = picUrl.split(",");
					for (String url : picUrls) {
						Element value = document.createElement("value");
						value.setTextContent(url);
						videourl.appendChild(value);

					}

				} else {
					Element value = document.createElement("value");
					value.setTextContent("");
					videourl.appendChild(value);
				}
				news.appendChild(videourl);

				Element extended = setChild("extended", "扩展字段", "", document);
				news.appendChild(extended);

				Element paginationtype = setChild("paginationtype", "内容分页", "", document);
				news.appendChild(paginationtype);

				document.appendChild(news);

				// 创建TransformerFactory对象
				TransformerFactory tff = TransformerFactory.newInstance();

				// 创建Transformer对象
				Transformer tf = tff.newTransformer();

				// 设置输出数据时换行
				tf.setOutputProperty(OutputKeys.INDENT, "yes");

				// 使用Transformer的transform()方法将DOM树转换成XML
				tf.transform(new DOMSource(document), new StreamResult(file));

			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (TransformerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return true;
	}

	public Element setChild(String elementName, String nameStr, String valueStr, Document document) {
		Element elment = document.createElement(elementName);

		Element name = document.createElement("name");
		if (elementName.equals("op")) {
			name = document.createElement("ename");
		}
		Element value = document.createElement("value");
		name.setTextContent(nameStr);
		value.setTextContent(valueStr);
		elment.appendChild(name);
		elment.appendChild(value);
		return elment;

	}

	@Override
	public CmsContent getOneContent(CmsContentVo vo) {
		Finder finder = Finder.create("from CmsContent");
		finder.append("where 1=1");
		finder.append(" and contentId=:ContentIdP").setParam("ContentIdP", vo.getContentId());
		List<CmsContent> list = (List<CmsContent>) cmsContentMng.find(finder);
		if (list != null && list.size() > 0) {
			CmsContent c = list.get(0);
			c.setContentType(cmsContentTypeMng.find(c.getContenttypeId()));
			return c;
		}
		return new CmsContent();
	}

	@Override
	public CmsContentData getContentDate(CmsContentVo vo) {
		// TODO Auto-generated method stub
		return cmsContentDataMng.find(vo.getContentId());
	}

	@Override
	public boolean auditAllContent(String contentIds, Integer result, Long roleId, SysUserDto user) {
		// TODO Auto-generated method stub
		if (contentIds == null || contentIds.length() < 1) {
			return false;
		}
		String[] idsStr = contentIds.split(",");
		try {
			for (int i = 0; i < idsStr.length; i++) {
				CmsContentVo vo = new CmsContentVo();
				vo.setContentId(Long.parseLong(idsStr[i]));
				vo.setAuditStepResult(result);

				auditResultUpdate(vo, roleId, user);
			}

		} catch (Exception e) {
			return false;
		}

		// auditResultUpdate
		return true;
	}

}
