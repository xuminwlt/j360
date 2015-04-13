package me.j360.base.service;

import me.j360.base.bean.BaseEnum;
import me.j360.base.bean.Pager;
import me.j360.base.entity.Entity;
import org.hibernate.Query;
import org.hibernate.criterion.DetachedCriteria;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * Service接口 - Service接口基类
 * ============================================================================
 * ============================================================================
 */

public interface BaseService<T extends Entity, PK extends Serializable>{


	/**
	 * 根据ID获取实体对象.
	 * 
	 * @param className
	 *            记录Class
	 * @param id
	 *            记录ID
	 *            
	 * @return 实体对象
	 */
	public Object getObject(String className, PK id);
	
	/**
	 * 根据ID获取实体对象.
	 * 
	 * @param id
	 *            记录ID
	 *            
	 * @return 实体对象
	 */
	public T get(PK id);

	/**
	 * 根据ID获取实体对象.
	 * 
	 * @param id
	 *            记录ID
	 *            
	 * @return 实体对象
	 */
	public T load(PK id);
	
	/**
	 * 根据ID数组获取实体对象集合.
	 * 
	 * @param ids
	 *            ID对象数组
	 * 
	 * @return 实体对象集合
	 */
	public List<T> get(PK[] ids);
	
	/**
	 * 根据属性名和属性值获取实体对象.
	 * 
	 * @param propertyName
	 *            属性名称
	 * @param value
	 *            属性值
	 *            
	 * @return 实体对象
	 */
	public T get(String propertyName, Object value);
	
	/**
	 * 根据属性名和属性值获取实体对象集合.
	 * 
	 * @param propertyName
	 *            属性名称
	 * @param value
	 *            属性值
	 *            
	 * @return 实体对象集合
	 */
	public List<T> getList(String propertyName, Object value);

	/**
	 * 获取所有实体对象集合.
	 * 
	 * @return 实体对象集合
	 */
	public List<T> getAll();
	public Query getQuery(String hql);
	public Object getUniqueResult(String hql);
	/**
	 * 获取所有实体对象总数.
	 * 
	 * @return 实体对象总数
	 */
	public Long getTotalCount();
	
	/**
	 * 根据属性名、修改前后属性值判断在数据库中是否唯一(若新修改的值与原来值相等则直接返回true).
	 * 
	 * @param propertyName
	 *            属性名称
	 * @param oldValue
	 *            修改前的属性值
	 * @param oldValue
	 *            修改后的属性值
	 *            
	 * @return boolean
	 */
	public boolean isUnique(String propertyName, Object oldValue, Object newValue);
	
	/**
	 * 根据属性名判断数据是否已存在.
	 * 
	 * @param propertyName
	 *            属性名称
	 * @param value
	 *            值
	 *            
	 * @return boolean
	 */
	public boolean isExist(String propertyName, Object value);

	/**
	 * 保存实体对象.
	 * 
	 * @param entity
	 *            对象
	 *            
	 * @return ID
	 */
	public PK save(T entity);

	/**
	 * 更新实体对象.
	 * 
	 * @param entity
	 *            对象
	 */
	public void update(T entity);
	
	/**
	 * 删除实体对象.
	 * 
	 * @param entity
	 *            对象
	 */
	public void delete(T entity);

	/**
	 * 根据ID删除实体对象.
	 * 
	 * @param id
	 *            记录ID
	 */
	public void delete(PK id);

	/**
	 * 根据ID数组删除实体对象.
	 * 
	 * @param ids
	 *            ID数组
	 */
	public void delete(PK[] ids);

	/**
	 * 刷新session.
	 * 
	 */
	public void flush();

	/**
	 * 清除Session.
	 * 
	 */
	public void clear();
	
	/**
	 * 清除某一对象.
	 * 
	 * @param object
	 *            需要清除的对象
	 */
	public void evict(Object object);

	/**
	 * 根据Pager对象进行查询(提供分页、查找、排序功能).
	 * 
	 * @param pager
	 *            Pager对象
	 *            
	 * @return Pager对象
	 */
	public Pager findByPager(Pager pager);
	
	/**
	 * 根据Pager和DetachedCriteria对象进行查询(提供分页、查找、排序功能).
	 * 
	 * @param pager
	 *            Pager对象
	 * @param detachedCriteria
	 *            detachedCriteria对象
	 * @param aliasList
	 *            aliasList别名集合
	 *               
	 * @return Pager对象
	 */
	public Pager findByPager(Pager pager, DetachedCriteria detachedCriteria, List<String> aliasList);
	
	/**
	 * 筛选查询（字段名分隔符为 . ）
	 * @param pager
	 *            	Pager对象
	 * @param detachedCriteria
	 *            detachedCriteria对象          
	 * @param aliasList
	 * 				createAlias的Alias集合（重复会报错）
	 * @param searchDate
	 * 				筛选时间字段名
	 * @param beginDate
	 * 				筛选时间开始时间
	 * @param endDate
	 * 				筛选时间结束时间
	 * @param params
	 * 				筛选条件
	 * 
	 * @return Pager对象
	 */
	public Pager findByPager(Pager pager, DetachedCriteria detachedCriteria, List<String> aliasList, String searchDate, Date beginDate, Date endDate, Map<String, Object> params);
	/**
	 * 筛选查询（字段名分隔符为 . ）
	 * @param pager
	 *            	Pager对象
	 * @param searchDate
	 * 				筛选时间字段名
	 * @param beginDate
	 * 				筛选时间开始时间
	 * @param endDate
	 * 				筛选时间结束时间
	 * @param params
	 * 				筛选条件
	 * 
	 * @return Pager对象
	 */
	public Pager findByPager(Pager pager, String searchDate, Date beginDate, Date endDate, Map<String, Object> params);
	/**
	 * 筛选查询（字段名分隔符为 . ）
	 * @param pager
	 *            	Pager对象
	 * @param params
	 * 				筛选条件
	 * 
	 * @return Pager对象
	 */
	public Pager findByPager(Pager pager, Map<String, Object> params);
	
	
	/**
	 * 筛选查询（字段名分隔符为 . ）
	 * @param detachedCriteria
	 * 				detachedCriteria对象
	 * @param aliasList
	 * 				createAlias的Alias集合（重复会报错）
	 * @param searchDate
	 * 				筛选时间字段名
	 * @param beginDate
	 * 				筛选时间开始时间
	 * @param endDate
	 * 				筛选时间结束时间
	 * @param params
	 * 				筛选条件
	 * @param orderBy
	 * 				排序字段名
	 * @param orderType
	 * 				排序顺序
	 * 
	 * @return Pager对象
	 */
	public List<T> getList(DetachedCriteria detachedCriteria, List<String> aliasList, String searchDate, Date beginDate, Date endDate, Map<String, Object> params, String orderBy, BaseEnum.OrderType orderType);
	/**
	 * 筛选查询（字段名分隔符为 . ）
	 * @param searchDate
	 * 				筛选时间字段名
	 * @param beginDate
	 * 				筛选时间开始时间
	 * @param endDate
	 * 				筛选时间结束时间
	 * @param params
	 * 				筛选条件
	 * @param orderBy
	 * 				排序字段名
	 * @param orderType
	 * 				排序顺序
	 * 
	 * @return Pager对象
	 */
	public List<T> getList(String searchDate, Date beginDate, Date endDate, Map<String, Object> params, String orderBy, BaseEnum.OrderType orderType);
	/**
	 * 筛选查询（字段名分隔符为 . ）
	 * @param params
	 * 				筛选条件
	 * @param orderBy
	 * 				排序字段名
	 * @param orderType
	 * 				排序顺序
	 * 
	 * @return Pager对象
	 */
	public List<T> getList(Map<String, Object> params, String orderBy, BaseEnum.OrderType orderType);
	/**
	 * 筛选查询（字段名分隔符为 . ）
	 * @param params
	 * 				筛选条件
	 * 
	 * @return Pager对象
	 */
	public List<T> getList(Map<String, Object> params);
	
	/**
	 * 得到所有清单
	 * 
	 * @param hql
	 *            hql
	 *            
	 * @return List集合
	 */
	public List<T> getAll(String hql);
	
	/**
	 * 得到所有清单
	 * @param hql
	 *            hql
	 * @param params
	 * 			   参数
	 * 
	 * @return List集合
	 */
	public List<T> getList(String hql, Map<String, Object> params);
	
	/**
	 * 整合JqGrid插件（对象属性采用下划线“_”分隔）
	 * @param pager
	 *            	Pager对象
	 * @param detachedCriteria
	 *            detachedCriteria对象          
	 * @param aliasList
	 * 				createAlias的Alias集合（重复会报错）
	 * @param search 筛选条件
	 * @param filters
	 * @param sidx
	 * @param sord
	 * 
	 * @return Pager
	 */
	public Pager findByPager(Pager pager, DetachedCriteria detachedCriteria, List<String> aliasList, String search, String filters, String sidx, String sord);
	/**
	 * 整合JqGrid插件（对象属性采用下划线“_”分隔）
	 * @param pager
	 *            	Pager对象
	 * @param search 筛选条件
	 * @param filters
	 * @param sidx
	 * @param sord
	 * 
	 * @return Pager
	 */
	public Pager findByPager(Pager pager, String search, String filters, String sidx, String sord);


    /**
     * 筛选查询（字段名分隔符为 . ）
     * @param pager
     *            	Pager对象
     * @param aliasList
     * 				createAlias的Alias集合（重复会报错）
     * @param searchDate
     * 				筛选时间字段名
     * @param beginDate
     * 				筛选时间开始时间
     * @param endDate
     * 				筛选时间结束时间
     * @param params
     * 				筛选条件
     * @param orderBy
     * 				排序字段名
     * @param orderType
     * 				排序顺序
     * @return
     */
    public Pager findByPager(Pager pager, DetachedCriteria detachedCriteria, List<String> aliasList, String searchDate, Date beginDate, Date endDate, Map<String, Object> params, String orderBy, BaseEnum.OrderType orderType);


    /**
     * 筛选查询（字段名分隔符为 . ）
     * @param pager
     *            	Pager对象
     * @param params
     * 				筛选条件
     * @param orderBy
     * 				排序字段名
     * @param orderType
     * 				排序顺序
     * @return
     */
    public Pager findByPager(Pager pager, Map<String, Object> params, String orderBy, BaseEnum.OrderType orderType);

    public Pager findByPager(Pager pager, String searchDate, Date beginDate,
							 Date endDate, Map<String, Object> params, String orderBy,
							 BaseEnum.OrderType orderType);
}
