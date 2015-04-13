package me.j360.base.service.impl;

import me.j360.base.bean.BaseEnum;
import me.j360.base.bean.Pager;
import me.j360.base.dao.BaseDao;
import me.j360.base.entity.Entity;
import me.j360.base.service.BaseService;
import org.hibernate.Query;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created with me.j360.base.service.impl.
 * User: min_xu
 * Date: 2014/9/10
 * Time: 16:22
 * 说明：
 */
@Service
public abstract class BaseServiceImpl<T extends Entity, PK extends Serializable> implements BaseService<T, PK> {
    private BaseDao<T, PK> baseDao;

    public abstract BaseDao<T, PK> getBaseDao();

    public void setBaseDao(BaseDao<T, PK> baseDao) {
        this.baseDao = baseDao;
    }

    public Object getObject(String className, PK id) {
        return getBaseDao().getObject(className, id);
    }

    public T get(PK id) {
        return getBaseDao().get(id);
    }

    public T load(PK id) {
        return getBaseDao().load(id);
    }

    public List<T> get(PK[] ids) {
        return getBaseDao().get(ids);
    }

    public T get(String propertyName, Object value) {
        return getBaseDao().get(propertyName, value);
    }

    public List<T> getList(String propertyName, Object value) {
        return getBaseDao().getList(propertyName, value);
    }

    public List<T> getAll() {
        return getBaseDao().getAll();
    }

    public Query getQuery(String hql){
        return getBaseDao().getQuery(hql);
    }
    public Object getUniqueResult(String hql){
        return getBaseDao().getUniqueResult(hql);
    }

    public Long getTotalCount() {
        return getBaseDao().getTotalCount();
    }

    public boolean isUnique(String propertyName, Object oldValue, Object newValue) {
        return getBaseDao().isUnique(propertyName, oldValue, newValue);
    }

    public boolean isExist(String propertyName, Object value) {
        return getBaseDao().isExist(propertyName, value);
    }

    public PK save(T entity) {
        return getBaseDao().save(entity);
    }

    public void update(T entity) {
        getBaseDao().update(entity);
    }

    public void delete(T entity) {
        getBaseDao().delete(entity);
    }

    public void delete(PK id) {
        getBaseDao().delete(id);
    }

    public void delete(PK[] ids) {
        getBaseDao().delete(ids);
    }

    public void flush() {
        getBaseDao().flush();
    }

    public void clear() {
        getBaseDao().clear();
    }

    public void evict(Object object) {
        getBaseDao().evict(object);
    }

    public Pager findByPager(Pager pager) {
        return getBaseDao().findByPager(pager);
    }

    @Override
    public Pager findByPager(Pager pager, DetachedCriteria detachedCriteria,
                             List<String> aliasList) {
        return getBaseDao().findByPager(pager, detachedCriteria, aliasList);
    }

    @Override
    public Pager findByPager(Pager pager, DetachedCriteria detachedCriteria,
                             List<String> aliasList, String searchDate, Date beginDate,
                             Date endDate, Map<String, Object> params) {
        return getBaseDao().findByPager(pager, detachedCriteria, aliasList, searchDate, beginDate, endDate, params);
    }

    @Override
    public Pager findByPager(Pager pager, String searchDate, Date beginDate,
                             Date endDate, Map<String, Object> params) {
        return findByPager(pager, null, null, searchDate, beginDate, endDate, params);
    }

    @Override
    public Pager findByPager(Pager pager, Map<String, Object> params) {
        return findByPager(pager, null, null, null, params);
    }

    @Override
    public List<T> getList(DetachedCriteria detachedCriteria,
                           List<String> aliasList, String searchDate, Date beginDate, Date endDate, Map<String, Object> params, String orderBy,
                           BaseEnum.OrderType orderType) {
        return getBaseDao().getList(detachedCriteria, aliasList, searchDate, beginDate, endDate, params, orderBy, orderType);
    }

    @Override
    public List<T> getList(String searchDate, Date beginDate, Date endDate, Map<String, Object> params, String orderBy,
                           BaseEnum.OrderType orderType) {
        return getList(null, null, searchDate, beginDate, endDate, params, orderBy, orderType);
    }

    @Override
    public List<T> getList(Map<String, Object> params, String orderBy,
                           BaseEnum.OrderType orderType) {
        return getList("", null, null, params, orderBy, orderType);
    }

    @Override
    public List<T> getList(Map<String, Object> params) {
        return getList(params, "createDate", BaseEnum.OrderType.asc);
    }

    @Override
    public List<T> getAll(String hql) {
        return getBaseDao().getAll(hql);
    }

    @Override
    public List<T> getList(String hql, Map<String, Object> params) {
        return getBaseDao().getList(hql, params);
    }

    @Override
    public Pager findByPager(Pager pager, DetachedCriteria detachedCriteria,
                             List<String> aliasList, String search, String filters, String sidx,
                             String sord) {
        return getBaseDao().findByPager(pager, detachedCriteria, aliasList, search, filters, sidx, sord);
    }

    @Override
    public Pager findByPager(Pager pager, String search, String filters,
                             String sidx, String sord) {
        return findByPager(pager, null, null, search, filters, sidx, sord);
    }

    @Override
    public Pager findByPager(Pager pager, DetachedCriteria detachedCriteria,
                             List<String> aliasList, String searchDate, Date beginDate,
                             Date endDate, Map<String, Object> params, String orderBy,
                             BaseEnum.OrderType orderType) {
        return getBaseDao().findByPager(pager, detachedCriteria, aliasList, searchDate, beginDate, endDate, params, orderBy, orderType);
    }

    @Override
    public Pager findByPager(Pager pager, Map<String, Object> params,
                             String orderBy, BaseEnum.OrderType orderType) {
        return findByPager(pager, null, null, null, params, orderBy, orderType);
    }

    @Override
    public Pager findByPager(Pager pager, String searchDate, Date beginDate,
                             Date endDate, Map<String, Object> params, String orderBy,
                             BaseEnum.OrderType orderType) {
        return getBaseDao().findByPager(pager, null, null, searchDate, beginDate, endDate, params, orderBy, orderType);
    }
}