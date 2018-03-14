package com.hww.base.common.entity;

/**
 * 基础接口
 * 实现父子结构tree
 *
 * @param <T>
 */
public interface IBaseTree<T extends Number> extends IBaseEntity<T>
{
    /**
     * 默认树左边属性名称
     */
    public static final String DEF_LEFT_NAME = "lft";
    /**
     * 默认树右边属性名称
     */
    public static final String DEF_RIGHT_NAME = "rgt";
    /**
     * 默认父节点属性名称
     */
    public static final String DEF_PARENT_NAME = "parent";
    /**
     * 实体类别名
     */
    public static final String ENTITY_ALIAS = "bean";

    /**
     * 获得树左边属性名称
     * 
     * @return
     */
    public abstract String getLftName();

    /**
     * 获得树右边属性名称
     * 
     * @return
     */
    public abstract String getRgtName();

    /**
     * 获得父节点属性名称
     * 
     * @return
     */
    public abstract String getParentName();

    /**
     * 获得树左边值
     * 
     * @return
     */
    public abstract T getLft();

    /**
     * 设置树左边值
     * 
     * @param lft
     */
    public abstract void setLft(T lft);

    /**
     * 获得树右边值
     * 
     * @return
     */
    public abstract T getRgt();

    /**
     * 设置数右边值
     * 
     * @param rgt
     */
    public abstract void setRgt(T rgt);

    /**
     * 获得父节点ID
     * 
     * @return 如果没有父节点，则返回null。
     */
    public abstract T getParentId();

    /**
     * 获得树ID
     * 
     * @return
     */
    public abstract T getTreeId();

    /**
     * 获得附加条件
     * 
     * 通过附加条件可以维护多棵树相互独立的树，附加条件使用hql语句，实体别名为bean。例如：bean.website.id=5
     * 
     * @return 为null则不添加任何附加条件
     * @see HibernateTree#ENTITY_ALIAS
     */
    public abstract String getTreeCondition();
}
