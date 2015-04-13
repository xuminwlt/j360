package me.j360.core.dao;

import me.j360.base.bean.BaseEnum;
import me.j360.base.bean.Pager;
import me.j360.base.dao.BaseDao;
import me.j360.core.entity.biz.Client;
import me.j360.core.entity.common.Company;
import me.j360.core.entity.common.Users;
import me.j360.core.entity.parent.BaseEntity;
import org.hibernate.criterion.DetachedCriteria;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Dao接口 - Dao实体基类接口
 * ============================================================================
 * 版权所有 2014。
 * 
 * @author fallenpanda
 * @version 0.1 2014-07-26
 * ============================================================================
 */

public interface BaseEntityDao<T extends BaseEntity, PK extends Serializable> extends BaseDao<T, PK> {

    /**
     * 根据Pager对象进行查询(提供分页、查找、排序功能).
     *
     * @param pager
     *            Pager对象
     * @param states
     *            states数组
     * @return Pager对象
     */
    public Pager findByPagerAndStates(Pager pager, BaseEnum.StateEnum[] states);
    /**
     * 根据Pager对象进行查询(提供分页、查找、排序功能).
     *
     * @param pager
     *            Pager对象
     * @param states
     *            states数组
     * @return Pager对象
     */
    public Pager findByPagerAndCompany(Pager pager, Users users, Company company, BaseEnum.StateEnum[] states);
    public Pager findByPagerAndCompany(Pager pager, Users users,
                                       Company company, Date beginDate, Date endDate, BaseEnum.StateEnum[] states);
    /**
     * 根据Pager对象进行查询(所有有效的对象).
     *
     * @param pager
     *            Pager对象
     * @return Pager对象
     */

    public Pager queryValidByPager(Pager pager);

    /**
     * 根据Pager5层计算规则对象进行查询(提供分页、查找、排序功能).
     *
     * @param pager
     *            Pager对象
     * @param states
     *            states数组
     * @return Pager对象
     */
    @Deprecated
    public Pager findByPagerDateSearch(Pager pager,
                                       Users users, Company company, Date beginDate, Date endDate, BaseEnum.StateEnum... states);

    /**
     * 根据Pager5层计算规则对象进行查询(提供分页、查找、排序功能).
     * 一下3个方法为适应不同的调用多态方法
     *
     * @param pager
     *            Pager对象
     * @param formState
     *            states数组
     * @return Pager对象
     */
    public Pager findByPagerDateSearchFormState(Pager pager, DetachedCriteria detachedCriteria,
                                                Users users, Company company, Client client, Object... formState);

    public Pager findByPagerDateSearchFormState(Pager pager,
                                                Users users, Company company, Client client, Date beginDate, Date endDate, Object... formState);

    public Pager findByPagerDateSearchFormState(Pager pager,
                                                Users users, Company company, Client client, String searchDate, Date beginDate, Date endDate, Object... formState);

    public Pager findByPagerDateSearchFormState(Pager pager, Map<String, Object> params,
                                                Users users, Company company, Client client, String searchDate, Date beginDate, Date endDate, Object... formState);

    public Pager findByPagerDateSearchFormState(Pager pager, DetachedCriteria detachedCriteria, List<String> aliasList, Map<String, Object> params,
                                                Users users, Company company, Client client, String searchDate, Date beginDate, Date endDate, Object... formState);
}