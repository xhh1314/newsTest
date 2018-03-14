package com.hww.base.common.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TemporalType;

/**
 * HQL语句分页查询
 */
public class Finder {
	public static final String ROW_COUNT = "select count(*) ";
	public static final String FROM = "from";
	public static final String DISTINCT = "distinct";
	public static final String HQL_FETCH = "fetch";
	public static final String ORDER_BY = "order";
	public static final String GROUP_BY = "group";

	private StringBuilder hqlBuilder;

	private List<String> params;
	private List<Object> values;
	private List<TemporalType> types;

	private List<String> paramsList;
	private List<Collection<?>> valuesList;
	private List<TemporalType> typesList;

	private List<String> paramsArray;
	private List<Object[]> valuesArray;
	private List<TemporalType> typesArray;

	private int firstResult = 0;

	private int maxResults = 0;

	private boolean cacheable = false;

	protected Finder() {
		hqlBuilder = new StringBuilder();
	}

	protected Finder(String hql) {
		hqlBuilder = new StringBuilder(hql);
	}

	public static Finder create() {
		return new Finder();
	}

	public static Finder create(String hql) {
		return new Finder(hql);
	}

	public Finder append(String hql) {
		hqlBuilder.append(" "+hql+" ");
		return this;
	}

	/**
	 * 获得原始hql语句
	 * 
	 * @return
	 */
	public String getOrigHql() {
		return hqlBuilder.toString();
	}

	/**
	 * 获得查询数据库记录数的hql语句。
	 * 
	 * @return
	 */
	public String getRowCountHql() {
		return getRowCountBaseHql(ORDER_BY);
	}

	public String getRowCountTotalHql(String selectSql) {
		return getRowCountTotalBaseHql(ORDER_BY, selectSql);
	}

	public String getRowCountHqlByGroup() {
		return getRowCountBaseHql(GROUP_BY);
	}
	
	public static String getRowCountSql(String selectSql) {
		String resultSql = "select count(*) from ("+selectSql+") as c";
		return resultSql;
	}

	public int getFirstResult() {
		return firstResult;
	}

	public void setFirstResult(int firstResult) {
		this.firstResult = firstResult;
	}

	public int getMaxResults() {
		return maxResults;
	}

	public void setMaxResults(int maxResults) {
		this.maxResults = maxResults;
	}

	/**
	 * 是否使用查询缓存
	 * 
	 * @return
	 */
	public boolean isCacheable() {
		return cacheable;
	}

	/**
	 * 设置是否使用查询缓存
	 * 
	 * @param cacheable
	 * @see Query#setCacheable(boolean)
	 */
	public void setCacheable(boolean cacheable) {
		this.cacheable = cacheable;
	}

	/**
	 * 设置参数
	 * 
	 * @param param
	 * @param value
	 * @return
	 * @see Query#setParameter(String, Object)
	 */
	public Finder setParam(String param, Object value) {
		getParams().add(param);
		getValues().add(value);
		return this;
	}

	/**
	 * 设置参数。与hibernate的Query接口一致。
	 * 
	 * @param paramMap
	 * @return
	 * @see Query#setProperties(Map)
	 */
	public Finder setParams(Map<String, Object> paramMap) {
		for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
			setParam(entry.getKey(), entry.getValue());
		}
		return this;
	}

	/**
	 * 设置参数。与hibernate的Query接口一致。
	 * 
	 * @param name
	 * @param vals
	 * @return
	 */
	public Finder setParamList(String name, Collection<?> vals) {
		getParamsList().add(name);
		getValuesList().add(vals);
		return this;
	}

	/**
	 * 设置参数。与hibernate的Query接口一致。
	 * 
	 * @param name
	 * @param vals
	 * @return
	 * @see Query#setParameterList(String, Object[])
	 */
	public Finder setParamList(String name, Object[] vals) {
		getParamsArray().add(name);
		getValuesArray().add(vals);
		return this;
	}

	/**
	 * 将finder中的参数设置到query中。
	 * 
	 * @param query
	 */
	public Query setParamsToQuery(Query query) {
		if (params != null) {
			for (int i = 0; i < params.size(); i++) {
				if (values.get(i) != null) {
					query.setParameter(params.get(i), values.get(i));
				} else {
					System.out.println("values为空" + i);
				}
			}
		} else {
			System.out.println("params为空");
		}
		if (paramsList != null) {
			for (int i = 0; i < paramsList.size(); i++) {
				if (valuesList.get(i) != null) {
					query.setParameter(paramsList.get(i), valuesList.get(i));
				}
			}
		}
		if (paramsArray != null) {
			for (int i = 0; i < paramsArray.size(); i++) {
				if (valuesArray.get(i) == null) {
					query.setParameter(paramsArray.get(i), valuesArray.get(i));
				}
			}
		}
		 
		return query;
	}

	public Query createQuery(EntityManager s) {
		Query query = setParamsToQuery(s.createQuery(getOrigHql()));
		if (getFirstResult() > 0) {
			query.setFirstResult(getFirstResult());
		}
		if (getMaxResults() > 0) {
			query.setMaxResults(getMaxResults());
		}
		return query;
	}
	
	public Query createNativeQuery(EntityManager s) {
        Query query = setParamsToQuery(s.createNativeQuery(getOrigHql()));
        if (getFirstResult() > 0) {
            query.setFirstResult(getFirstResult());
        }
        if (getMaxResults() > 0) {
            query.setMaxResults(getMaxResults());
        }
        return query;
    }


	private String getRowCountBaseHql(String indexKey) {
		String hql = hqlBuilder.toString();

		int fromIndex = hql.toLowerCase().indexOf(FROM);
		String projectionHql = hql.substring(0, fromIndex);

		hql = hql.substring(fromIndex);
		String rowCountHql = hql.replace(HQL_FETCH, "");

		int index = rowCountHql.indexOf(indexKey);
		if (index > 0) {
			rowCountHql = rowCountHql.substring(0, index);
		}
		return wrapProjection(projectionHql) + rowCountHql;
	}

	private String getRowCountTotalBaseHql(String indexKey, String selectSql) {
		String hql = hqlBuilder.toString();

		int fromIndex = hql.toLowerCase().indexOf(FROM);
		String projectionHql = hql.substring(0, fromIndex);

		hql = hql.substring(fromIndex);
		String rowCountHql = hql.replace(HQL_FETCH, "");

		int index = rowCountHql.indexOf(indexKey);
		if (index > 0) {
			rowCountHql = rowCountHql.substring(0, index);
		}
		// return
		// selectSql+"( "+wrapProjectionBeanId(projectionHql)+rowCountHql+")";
		return "select count() from " + "( " + projectionHql + rowCountHql + ") as a";
	}

	private String wrapProjection(String projection) {
		if (projection.indexOf("select") == -1) {
			return ROW_COUNT;
		} else {
			return projection.replace("select", "select count(") + ") ";
		}
	}

	@SuppressWarnings("unused")
	private String wrapProjectionBeanId(String projection) {
		if (projection.indexOf("select") == -1) {
			return "select bean.id ";
		} else {
			return projection.replace("select bean", "select bean.id") + " ";
		}
	}

	public List<String> getParams() {
		if (params == null) {
			params = new ArrayList<String>();
		}
		return params;
	}

	public void setParams(List<String> params,List<Object> values) {
		this.params = params;
		this.values = values;
	}

	public List<Object> getValues() {
		if (values == null) {
			values = new ArrayList<Object>();
		}
		return values;
	}

	private List<String> getParamsList() {
		if (paramsList == null) {
			paramsList = new ArrayList<String>();
		}
		return paramsList;
	}

	private List<Collection<?>> getValuesList() {
		if (valuesList == null) {
			valuesList = new ArrayList<Collection<?>>();
		}
		return valuesList;
	}

	private List<String> getParamsArray() {
		if (paramsArray == null) {
			paramsArray = new ArrayList<String>();
		}
		return paramsArray;
	}

	private List<Object[]> getValuesArray() {
		if (valuesArray == null) {
			valuesArray = new ArrayList<Object[]>();
		}
		return valuesArray;
	}

	public static void main(String[] args) {
		Finder find = Finder.create("select distinct p FROM BookType join fetch p");
		System.out.println(find.getRowCountHql());
		System.out.println(find.getRowCountTotalHql(find.getOrigHql()));
		System.out.println(find.getRowCountHqlByGroup());
		System.out.println(find.getRowCountHqlByGroup());
		System.out.println(find.getRowCountSql(find.getOrigHql()));
		
		System.out.println(find.getOrigHql());
	}

}