package com.hww.app.common.manager;

import com.hww.app.common.dao.AppCategoryDao;
import com.hww.app.common.entity.AppCategory;
import com.hww.app.common.vo.AppCategoryVo;
import com.hww.base.common.manager.IBaseEntityMng;

import java.util.HashMap;
import java.util.List;


public interface AppCategoryMng extends IBaseEntityMng<Long, AppCategory, AppCategoryDao> {
	
	List<AppCategory> loadAppCategoryByIds(List<Long> cateIds);
	List<AppCategory> loadDefaultColumn();
	
	List<AppCategory> loadAllColumns();
	
	/**
	 * @author XiaoBG
	 * 管理员功能
	 * 多条件查询：根据实体类参数查询频道信息
	 */
	public List<AppCategory> selectAppCategoryInfo(AppCategory appCategory);
	/**
	 * @author XiaoBG
	 * 用户删除信息
	 * @param:column_name, column_desc, userId
	 */
	AppCategory deleteUserCategory(AppCategory appCategory, Long userId, String imei);
	/**
	 * @author XiaoBG
	 * @param:userId, imei
	 * @Des:查询用户订阅的频道列表,有用户id的情况下优先按照用户id查询,否则按照imei查询,不包含推荐频道信息
	 * @return:appCategory
	 */
	List<HashMap<String, Object>> userSubscribedColumn(Long userId, String imei);
	
	/**
	 * @author XiaoBG
	 * @param:userId, imei
	 * @Des:查询频道列表的中间节点
	 * @return:appCategory
	 */

	List<AppCategory> selectMidelNodeColumn(Long columnId);
	
	/**
	 * @author XiaoBG
	 * @param:userId, imei
	 * @Des:查询叶子节点
	 * @return:appCategory
	 */
	List<HashMap<String, Object>> selectLeafNodeColumnByParentId(Long parentId);
	
	/**
	 * @author XiaoBG
	 * @param:columnId
	 * @Des:查询频道列表中的根节点信息
	 * @return:appCategory
	 */
	AppCategory selectRootNode();
	/**
	 * @author XiaoBG
	 * @param:
	 * @Des:查询默认频道
	 * @return:appCategory
	 */
	List<AppCategory> selectDefaultColumn(Long userId, String imei);
	
	/**
	 * @author XiaoBG
	 * @param:
	 * @Des:查询读报频道
	 * @return:appCategory
	 */
	List<AppCategory> selectReaderPaperColumn();
	
	/**
	 * @author XiaoBG
	 * @param:
	 * @Des:查询推荐频道
	 * @return:appCategory
	 */
	List<AppCategory> selectRecommendColumn();

	/**
	 * @author XiaoBG
	 * @param:
	 * @Des:用于确认用户与频道关系表中是否存在读报频道和推荐频道
	 * @return: Integer
	 */
	List<HashMap<String, Object>> confirmCategoryOfReadPaper(Long userId, String imei);

	/**
	 * @author XiaoBG
	 * 管理员：加载频道列表树
	 * 全部加载
	 * @return List<AppCategoryVo>
	 */
	List<AppCategory> getRetrievingFullTree();
	
	/**
	 * 用户：根据频道接口查询新闻Id列表
	 *
	 * @author XiaoBG
	 * @date 2018年1月11日 下午9:45:14
	 * param Long
	 * @return List<Long>
	 * @version v0.1
	 */
	List<Long> selectCateIdsByColumnId(Long columnId);
	
	/**
	 * 管理员：分页加载频道列表
	 *
	 * @author XiaoBG
	 * @date 2018年1月11日 下午9:45:14
	 * param appCategoryVo
	 * @return 
	 * @version v0.1
	 */
	HashMap<String, Object> selectAppCategoryListByPage(AppCategoryVo vo);
	
	
	List<AppCategory> selectCustomCategoryList();
	
	/**
	 * @author XiaoBG
	 * @param:userId, imei
	 * @Des:查询用户订阅的所有频道列表,有用户id的情况下优先按照用户id查询,否则按照imei查询,按照顺序排序
	 * @return:List<AppCategory>
	 */
	List<HashMap<String, Object>> userSubscribedColumnAll(Long userId, String imei);
	
	/**
	 * @author XiaoBG
	 * @param:midleNodeId
	 * @Des:查询中间节点包含的叶子节点信息，不包含默认频道和固定频道
	 * @return:List<HashMap<String, Object>>
	 */	
	List<HashMap<String, Object>> selectLeafNodeColumnByParentIdNotContain(long midleNodeId);
	
	List<Integer> sort(Integer sort);
}
