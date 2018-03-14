package com.hww.app.common.dao;

import java.util.HashMap;
import java.util.List;
import com.hww.base.common.dao.IBaseEntityDao;
import com.hww.app.common.entity.AppCategory;
import com.hww.app.common.vo.AppCategoryVo;

public interface AppCategoryDao extends IBaseEntityDao<Long, AppCategory> {

	/**
	 * @author XiaoBG
	 * 管理员功能
	 * 多条件查询：根据实体类参数查询频道信息
	 */
	public List<AppCategory> selectAppCategoryInfo(AppCategory appCategory);

	/**
	 * @author XiaoBG
	 * @param:用户删除自频道信息,如果已登录则以userId为先排序,如果未登录则通过imei号加载列表
	 */
	public AppCategory deleteUserCategory(AppCategory appCategory, Long userId, String imei);
	
	/**
	 * @author XiaoBG
	 * @param:userId, imei
	 * @Des:查询用户订阅的频道列表,有用户id的情况下优先按照用户id查询,否则按照imei查询,不包含推荐频道信息
	 * 返回appCategory会出现实体类属性数值绑定错误,在services层统一进行转换绑定
	 * @return:appCategory
	 */
	public List<HashMap<String, Object>> userSubscribedColumn(Long userId, String imei);

	/**
	 * @author XiaoBG
	 * @param:userId, imei
	 * @Des:查询用户未订阅的频道列表的中间节点,
	 * @return:appCategory
	 */
	public List<AppCategory> selectMidelNodeColumn(Long columnId);
	
	/**
	 * @author XiaoBG
	 * @param:userId, imei
	 * @Des:查询用户未订阅的频道列表的叶子节点,
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
	public List<AppCategory> selectDefaultColumn(Long userId, String imei);
	
	/**
	 * @author XiaoBG
	 * @param:
	 * @Des:查询读报频道
	 * @return:appCategory
	 */
	public List<AppCategory> selectReaderPaperColumn();
	
	/**
	 * @author XiaoBG
	 * @param:
	 * @Des:查询推荐频道
	 * @return:appCategory
	 */
	public List<AppCategory> selectRecommendColumn();

	/**
	 * @author XiaoBG
	 * @param:
	 * @Des:用于确认用户与频道关系表中是否存在读报频道和推荐频道
	 * @return: user_id, imei, coulmn_id, column_name, sort
	 */
	List<HashMap<String,Object>> confirmCategoryOfReadPaper(Long userId, String imei);

	/**
	 * @author XiaoBG
	 * 管理员：加载频道列表树
	 * @return List<AppCategoryVo>
	 */
	public List<AppCategory> getRetrievingFullTree();
	
	/**
	 * 用户：根据频道接口查询新闻Id列表
	 *
	 * @author XiaoBG
	 * @date 2018年1月11日 下午9:45:14
	 * param Long
	 * @return List<Long>
	 * @version v0.1
	 */
	public List<Long> selectCateIdsByColumnId(Long columnId);
	
	/**
	 * 管理员：分页加载频道列表
	 *
	 * @author XiaoBG
	 * @date 2018年1月11日 下午9:45:14
	 * param appCategoryVo
	 * @return 
	 * @version v0.1
	 */
	public HashMap<String, Object> selectAppCategoryListByPage(AppCategoryVo vo);
	
	/**
	 * @author XiaoBG
	 * @param:userId, imei
	 * @Des:查询用户订阅的所有频道列表,有用户id的情况下优先按照用户id查询,否则按照imei查询,按照顺序排序
	 * @return:List<AppCategory>
	 */
	public List<HashMap<String, Object>> userSubscribedColumnAll(Long userId, String imei);

	/**
	 * @author XiaoBG
	 * @param:userId, imei
	 * @Des:查询叶子节点，不包含默认频道和固定频道
	 * @return:List<AppCategory>
	 */
	public List<HashMap<String, Object>> selectLeafNodeColumnByParentIdNotContain(long midleNodeId);
	

}