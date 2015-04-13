package me.j360.base.dao.impl;
import me.j360.base.bean.BaseEnum;
import me.j360.base.bean.Pager;
import me.j360.base.dao.BaseDao;
import me.j360.base.entity.Entity;
import me.j360.base.util.CommonUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.*;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;


/**
 * Dao实现类 - Dao实现类基类
 * ============================================================================
 * ============================================================================
 */

@Repository
public abstract class BaseDaoImpl<T extends Entity, PK extends Serializable> implements BaseDao<T, PK> {
	
	private Class<T> entityClass;
	
	@Resource
	protected SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	public BaseDaoImpl() {
		this.entityClass = null;
		Class<?> c = getClass();
		Type type = c.getGenericSuperclass();//返回本类的父类,包含泛型参数信息
		if (type instanceof ParameterizedType) {
			Type[] parameterizedType = ((ParameterizedType) type)
					.getActualTypeArguments();//泛型参数的实际类型
			this.entityClass = (Class<T>) parameterizedType[0];//第一个泛型
		}
	}

	protected Class<T> getEntityClass() {
		return entityClass;
	}

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public Object getObject(String className, PK id) {
		Assert.notNull(className, "className is required");
		Assert.notNull(id, "id is required");
		return getSession().get(className, id);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public T get(PK id) {
		Assert.notNull(id, "id is required");
		return (T) getSession().get(entityClass, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public T load(PK id) {
		Assert.notNull(id, "id is required");
		return (T) getSession().load(entityClass, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> get(PK[] ids) {
		Assert.notEmpty(ids, "ids must not be empty");
		String hql = "from " + entityClass.getName()
				+ " model where model.id in(:ids)";
		return getSession().createQuery(hql).setParameterList("ids", ids)
				.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public T get(String propertyName, Object value) {
		Assert.hasText(propertyName, "propertyName must not be empty");
		Assert.notNull(value, "value is required");
		String hql = "from " + entityClass.getName() + " model where model."
				+ propertyName + " = ?";
		return (T) getSession().createQuery(hql).setParameter(0, value)
				.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> getList(String propertyName, Object value) {
		Assert.hasText(propertyName, "propertyName must not be empty");
		// Assert.notNull(value, "value is required");
		String hql;
		if (value == null) {
			hql = "from " + entityClass.getName() + " model where model."
					+ propertyName + " is null";
			return getSession().createQuery(hql).list();
		}
		hql = "from " + entityClass.getName() + " model where model."
				+ propertyName + " = ?";
		return getSession().createQuery(hql).setParameter(0, value).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> getAll() {
		String hql = "from " + entityClass.getName();
		return getSession().createQuery(hql).list();
	}

	@Override
	public Long getTotalCount() {
		String hql = "select count(*) from " + entityClass.getName();
		return (Long) getSession().createQuery(hql).uniqueResult();
	}
	

	@Override
	public boolean isUnique(String propertyName, Object oldValue,
			Object newValue) {
		Assert.hasText(propertyName, "propertyName must not be empty");
		Assert.notNull(newValue, "newValue is required");
		if (newValue == oldValue || newValue.equals(oldValue)) {
			return true;
		}
		if (newValue instanceof String) {
			if (oldValue != null
					&& StringUtils.equalsIgnoreCase((String) oldValue,
                    (String) newValue)) {
				return true;
			}
		}
		T object = get(propertyName, newValue);
		return (object == null);
	}

	@Override
	public boolean isExist(String propertyName, Object value) {
		Assert.hasText(propertyName, "propertyName must not be empty");
		Assert.notNull(value, "value is required");
		T object = get(propertyName, value);
		return (object != null);
	}

	@SuppressWarnings("unchecked")
	@Override
	public PK save(T entity) {
		Assert.notNull(entity, "entity is required");
		return (PK) getSession().save(entity);
	}

	@Override
	public void update(T entity) {
		Assert.notNull(entity, "entity is required");
		getSession().update(entity);
	}

	@Override
	public void delete(T entity) {
		Assert.notNull(entity, "entity is required");
		getSession().delete(entity);
	}

	@Override
	public void delete(PK id) {
		Assert.notNull(id, "id is required");
		T entity = load(id);
		getSession().delete(entity);
	}

    @Override
    public void delete(PK[] ids) {

    }

    @Override
    public void flush() {

    }

    @Override
    public void clear() {

    }

    @Override
    public void evict(Object object) {

    }

    @Override
    public Pager findByPager(Pager pager) {
        return null;
    }


    @Override
	public Pager findByPager(Pager pager, DetachedCriteria detachedCriteria, List<String> aliasList) {
		if (pager == null) {
			pager = new Pager();
		}
		if(detachedCriteria == null){
			detachedCriteria = DetachedCriteria.forClass(entityClass);
		}
		//createAlias Key集合
		if(aliasList == null){
			aliasList = new ArrayList<String>();
		}
		Integer pageNumber = pager.getPageNumber();
		Integer pageSize = pager.getPageSize();
		String property = pager.getProperty();
		String keyword = pager.getKeyword();
		String orderBy = pager.getOrderBy();
		BaseEnum.OrderType orderType = pager.getOrderType();
		
		Criteria criteria = detachedCriteria
				.getExecutableCriteria(getSession());
		if (StringUtils.isNotEmpty(property) && StringUtils.isNotEmpty(keyword)) {
			if (StringUtils.contains(property, ".")) {
				String[] fields = StringUtils.split(property, ".");
				for (int j = 0; j < fields.length - 1; j++) {
					if (!aliasList.contains(CommonUtil.getAlias(fields, j))) {
						criteria.createAlias(CommonUtil.getPath(fields, j), CommonUtil.getAlias(fields, j));
						aliasList.add(CommonUtil.getAlias(fields, j));
					}
				}
				criteria.add(Restrictions.like(CommonUtil.getPath(fields, fields.length - 1), "%" + keyword + "%"));
			} else {
				criteria.add(Restrictions.like(property, "%" + keyword + "%"));
			}
		}
		
		Integer totalCount = (Integer.parseInt(criteria
				.setProjection(Projections.rowCount()).uniqueResult()
				.toString()));
		
		criteria.setProjection(null);
		criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		if (pageNumber > 0) {
			criteria.setFirstResult((pageNumber - 1) * pageSize);
			criteria.setMaxResults(pageSize);
		}
		
		if(StringUtils.isNotEmpty(orderBy)&& orderType!=null){
			if(StringUtils.contains(orderBy, ".")){
				String[] fields = StringUtils.split(orderBy, ".");
				for(int j=0;j<fields.length-1;j++){
					if(!aliasList.contains(CommonUtil.getAlias(fields, j))){
						criteria.createAlias(CommonUtil.getPath(fields, j), CommonUtil.getAlias(fields, j));
						aliasList.add(CommonUtil.getAlias(fields, j));
					}
				}
				if(BaseEnum.OrderType.asc.equals(orderType)){
					criteria.addOrder(Order.asc(CommonUtil.getPath(fields, fields.length - 1)));
				}else{
					criteria.addOrder(Order.desc(CommonUtil.getPath(fields, fields.length - 1)));
				}
			}else{
				if(BaseEnum.OrderType.asc.equals(orderType)){
					criteria.addOrder(Order.asc(orderBy));
				}else{
					criteria.addOrder(Order.desc(orderBy));
				}
			}
		}
		
		pager.setTotalCount(totalCount);
		pager.setList(criteria.list());
		return pager;
	}
	
	@Override
	public Pager findByPager(Pager pager, DetachedCriteria detachedCriteria, List<String> aliasList, String searchDate, Date beginDate, Date endDate, Map<String, Object> params) {
		if(detachedCriteria == null){
			detachedCriteria = DetachedCriteria.forClass(entityClass);
		}
		//createAlias Key集合
		if(aliasList == null){
			aliasList = new ArrayList<String>();
		}
		//筛选时间
		if(StringUtils.isNotEmpty(searchDate)){
			if(beginDate!=null||endDate!=null){
				if(StringUtils.contains(searchDate, ".")){
					String[] fields = StringUtils.split(searchDate, ".");
					for(int j=0;j<fields.length-1;j++){
						if(!aliasList.contains(CommonUtil.getAlias(fields, j))){
							detachedCriteria.createAlias(CommonUtil.getPath(fields, j), CommonUtil.getAlias(fields, j));
							aliasList.add(CommonUtil.getAlias(fields, j));
						}
					}
					if(beginDate!=null){
						detachedCriteria.add(Restrictions.ge(CommonUtil.getPath(fields, fields.length - 1), beginDate));
					}
					if(endDate!=null){
						detachedCriteria.add(Restrictions.le(CommonUtil.getPath(fields, fields.length - 1), endDate));
					}
				}else{
					if(beginDate!=null){
						detachedCriteria.add(Restrictions.ge(searchDate, beginDate));
					}
					if(endDate!=null){
						detachedCriteria.add(Restrictions.le(searchDate, endDate));
					}
				}
			}
		}
		//查询条件
		if(params!=null&&params.size()>0){
			//遍历条件
			for(String field : params.keySet()){
				if(params.get(field)!=null){
					Object value = params.get(field);
					if(StringUtils.contains(field, ".")){
						String[] fields = StringUtils.split(field, ".");
						for(int j=0;j<fields.length-1;j++){
							if(!aliasList.contains(CommonUtil.getAlias(fields, j))){
								detachedCriteria.createAlias(CommonUtil.getPath(fields, j), CommonUtil.getAlias(fields, j));
								aliasList.add(CommonUtil.getAlias(fields, j));
							}
						}
						if(value instanceof String){
							detachedCriteria.add(Restrictions.like(CommonUtil.getPath(fields, fields.length - 1), "%" + value + "%"));
						}else if(value instanceof Object[]){
							Object[] values = (Object[]) value;
							if(values!=null&&values.length>0){
								Criterion[] criterions = new Criterion[values.length];
								for(int i=0;i<values.length;i++){
									Object o = values[i];
									if(o instanceof Entity){
										criterions[i] = Restrictions.or(Restrictions.eq(CommonUtil.getPath(fields, fields.length - 1), o), Restrictions.eq(CommonUtil.getPath(fields, fields.length - 1) + ".id", ((Entity) o).getId()));
									}else{
										criterions[i]= Restrictions.eq(CommonUtil.getPath(fields, fields.length - 1), o);
									}
								}
								detachedCriteria.add(Restrictions.or(criterions));
							}
						}else if(value instanceof Entity){
							detachedCriteria.add(Restrictions.or(Restrictions.eq(CommonUtil.getPath(fields, fields.length - 1), value), Restrictions.eq(CommonUtil.getPath(fields, fields.length - 1) + ".id", ((Entity) value).getId())));
						}else{
							detachedCriteria.add(Restrictions.eq(CommonUtil.getPath(fields, fields.length - 1), value));
						}
					}else{
						if(value instanceof String){
							detachedCriteria.add(Restrictions.like(field, "%" + value + "%"));
						}else if(value instanceof Object[]){
							Object[] values = (Object[]) value;
							if(values!=null&&values.length>0){
								Criterion[] criterions = new Criterion[values.length];
								for(int i=0;i<values.length;i++){
									Object o = values[i];
									if(o instanceof Entity){
										criterions[i] = Restrictions.or(Restrictions.eq(field, o), Restrictions.eq(field + ".id", ((Entity) o).getId()));
									}else{
										criterions[i]= Restrictions.eq(field, o);
									}
								}
								detachedCriteria.add(Restrictions.or(criterions));
							}
						}else if(value instanceof Entity){
							detachedCriteria.add(Restrictions.or(Restrictions.eq(field, value), Restrictions.eq(field + ".id", ((Entity) value).getId())));
						}else{
							detachedCriteria.add(Restrictions.eq(field, value));
						}
					}
				}
			}
		}
		return findByPager(pager, detachedCriteria, aliasList);
	}

    @Override
    public Pager findByPager(Pager pager, DetachedCriteria detachedCriteria, List<String> aliasList, String searchDate, Date beginDate, Date endDate, Map<String, Object> params, String orderBy, BaseEnum.OrderType orderType) {
        if(detachedCriteria == null){
            detachedCriteria = DetachedCriteria.forClass(entityClass);
        }
        //createAlias Key集合
        if(aliasList == null){
            aliasList = new ArrayList<String>();
        }
        if(StringUtils.isNotEmpty(searchDate)){
            if(beginDate!=null||endDate!=null){
                if(StringUtils.contains(searchDate, ".")){
                    String[] fields = StringUtils.split(searchDate, ".");
                    for(int j=0;j<fields.length-1;j++){
                        if(!aliasList.contains(CommonUtil.getAlias(fields, j))){
                            detachedCriteria.createAlias(CommonUtil.getPath(fields, j), CommonUtil.getAlias(fields, j));
                            aliasList.add(CommonUtil.getAlias(fields, j));
                        }
                    }
                    if(beginDate!=null){
                        detachedCriteria.add(Restrictions.ge(CommonUtil.getPath(fields, fields.length - 1), beginDate));
                    }
                    if(endDate!=null){
                        detachedCriteria.add(Restrictions.le(CommonUtil.getPath(fields, fields.length - 1), endDate));
                    }
                }else{
                    if(beginDate!=null){
                        detachedCriteria.add(Restrictions.ge(searchDate, beginDate));
                    }
                    if(endDate!=null){
                        detachedCriteria.add(Restrictions.le(searchDate, endDate));
                    }
                }
            }
        }
        if(params!=null&&params.size()>0){
            //遍历条件
            for(String field : params.keySet()){
                if(params.get(field)!=null){
                    Object value = params.get(field);
                    if(StringUtils.contains(field, ".")){
                        String[] fields = StringUtils.split(field, ".");
                        for(int j=0;j<fields.length-1;j++){
                            if(!aliasList.contains(CommonUtil.getAlias(fields, j))){
                                detachedCriteria.createAlias(CommonUtil.getPath(fields, j), CommonUtil.getAlias(fields, j));
                                aliasList.add(CommonUtil.getAlias(fields, j));
                            }
                        }
                        if(value instanceof String){
                            detachedCriteria.add(Restrictions.like(CommonUtil.getPath(fields, fields.length - 1), "%"+value+"%"));
                        }else if(value instanceof Object[]){
                            Object[] values = (Object[]) value;
                            if(values!=null&&values.length>0){
                                Criterion[] criterions = new Criterion[values.length];
                                for(int i=0;i<values.length;i++){
                                    Object o = values[i];
                                    if(o instanceof Entity){
                                        criterions[i] = Restrictions.or(Restrictions.eq(CommonUtil.getPath(fields, fields.length - 1), o), Restrictions.eq(CommonUtil.getPath(fields, fields.length - 1)+".id", ((Entity) o).getId()));
                                    }else{
                                        criterions[i]= Restrictions.eq(CommonUtil.getPath(fields, fields.length - 1), o);
                                    }
                                }
                                detachedCriteria.add(Restrictions.or(criterions));
                            }
                        }else if(value instanceof Entity){
                            detachedCriteria.add(Restrictions.or(Restrictions.eq(CommonUtil.getPath(fields, fields.length - 1), value), Restrictions.eq(CommonUtil.getPath(fields, fields.length - 1)+".id", ((Entity) value).getId())));
                        }else{
                            detachedCriteria.add(Restrictions.eq(CommonUtil.getPath(fields, fields.length - 1), value));
                        }
                    }else{
                        if(value instanceof String){
                            detachedCriteria.add(Restrictions.like(field, "%"+value+"%"));
                        }else if(value instanceof Object[]){
                            Object[] values = (Object[]) value;
                            if(values!=null&&values.length>0){
                                Criterion[] criterions = new Criterion[values.length];
                                for(int i=0;i<values.length;i++){
                                    Object o = values[i];
                                    if(o instanceof Entity){
                                        criterions[i] = Restrictions.or(Restrictions.eq(field, o), Restrictions.eq(field+".id", ((Entity) o).getId()));
                                    }else{
                                        criterions[i]= Restrictions.eq(field, o);
                                    }
                                }
                                detachedCriteria.add(Restrictions.or(criterions));
                            }
                        }else if(value instanceof Entity){
                            detachedCriteria.add(Restrictions.or(Restrictions.eq(field, value), Restrictions.eq(field+".id", ((Entity) value).getId())));
                        }else{
                            detachedCriteria.add(Restrictions.eq(field, value));
                        }
                    }
                }
            }
        }
        if(StringUtils.isNotEmpty(orderBy)&&orderType!=null){
            pager.setOrderBy(orderBy);
            pager.setOrderType(orderType);
        }
        return findByPager(pager, detachedCriteria, aliasList);
    }

    @SuppressWarnings("unchecked")
	@Override
	public List<T> getList(DetachedCriteria detachedCriteria,
			List<String> aliasList, String searchDate, Date beginDate, Date endDate, Map<String, Object> params, String orderBy,
			BaseEnum.OrderType orderType) {
		if(detachedCriteria == null){
			detachedCriteria = DetachedCriteria.forClass(entityClass);
		}
		//createAlias Key集合
		if(aliasList == null){
			aliasList = new ArrayList<String>();
		}
		Criteria criteria = detachedCriteria.getExecutableCriteria(getSession());
		//筛选时间
		if(StringUtils.isNotEmpty(searchDate)){
			if(beginDate!=null||endDate!=null){
				if(StringUtils.contains(searchDate, ".")){
					String[] fields = StringUtils.split(searchDate, ".");
					for(int j=0;j<fields.length-1;j++){
						if(!aliasList.contains(CommonUtil.getAlias(fields, j))){
							detachedCriteria.createAlias(CommonUtil.getPath(fields, j), CommonUtil.getAlias(fields, j));
							aliasList.add(CommonUtil.getAlias(fields, j));
						}
					}
					if(beginDate!=null){
						detachedCriteria.add(Restrictions.ge(CommonUtil.getPath(fields, fields.length - 1), beginDate));
					}
					if(endDate!=null){
						detachedCriteria.add(Restrictions.le(CommonUtil.getPath(fields, fields.length - 1), endDate));
					}
				}else{
					if(beginDate!=null){
						detachedCriteria.add(Restrictions.ge(searchDate, beginDate));
					}
					if(endDate!=null){
						detachedCriteria.add(Restrictions.le(searchDate, endDate));
					}
				}
			}
		}
		//查询条件
		if(params!=null&&params.size()>0){
			//遍历条件
			for(String field : params.keySet()){
				if(params.get(field)!=null){
					Object value = params.get(field);
					if(StringUtils.contains(field, ".")){
						String[] fields = StringUtils.split(field, ".");
						for(int j=0;j<fields.length-1;j++){
							if(!aliasList.contains(CommonUtil.getAlias(fields, j))){
								criteria.createAlias(CommonUtil.getPath(fields, j), CommonUtil.getAlias(fields, j));
								aliasList.add(CommonUtil.getAlias(fields, j));
							}
						}
						if(value instanceof String){
							criteria.add(Restrictions.like(CommonUtil.getPath(fields, fields.length - 1), "%" + value + "%"));
						}else if(value instanceof Object[]){
							Object[] values = (Object[]) value;
							if(values!=null&&values.length>0){
								Criterion[] criterions = new Criterion[values.length];
								for(int i=0;i<values.length;i++){
									Object o = values[i];
									if(o instanceof Entity){
										criterions[i] = Restrictions.or(Restrictions.eq(CommonUtil.getPath(fields, fields.length - 1), o), Restrictions.eq(CommonUtil.getPath(fields, fields.length - 1) + ".id", ((Entity) o).getId()));
									}else{
										criterions[i]= Restrictions.eq(CommonUtil.getPath(fields, fields.length - 1), o);
									}
								}
								criteria.add(Restrictions.or(criterions));
							}
						}else if(value instanceof Entity){
							criteria.add(Restrictions.or(Restrictions.eq(CommonUtil.getPath(fields, fields.length - 1), value), Restrictions.eq(CommonUtil.getPath(fields, fields.length - 1) + ".id", ((Entity) value).getId())));
						}else{
							criteria.add(Restrictions.eq(CommonUtil.getPath(fields, fields.length - 1), value));
						}
					}else{
						if(value instanceof String){
							criteria.add(Restrictions.like(field, "%" + value + "%"));
						}else if(value instanceof Object[]){
							Object[] values = (Object[]) value;
							if(values!=null&&values.length>0){
								Criterion[] criterions = new Criterion[values.length];
								for(int i=0;i<values.length;i++){
									Object o = values[i];
									if(o instanceof Entity){
										criterions[i] = Restrictions.or(Restrictions.eq(field, o), Restrictions.eq(field + ".id", ((Entity) o).getId()));
									}else{
										criterions[i]= Restrictions.eq(field, o);
									}
								}
								criteria.add(Restrictions.or(criterions));
							}
						}else if(value instanceof Entity){
							criteria.add(Restrictions.or(Restrictions.eq(field, value), Restrictions.eq(field + ".id", ((Entity) value).getId())));
						}else{
							criteria.add(Restrictions.eq(field, value));
						}
					}
				}
			}
		}
		if(StringUtils.isNotEmpty(orderBy)&& orderType!=null){
			if(StringUtils.contains(orderBy, ".")){
				String[] fields = StringUtils.split(orderBy, ".");
				for(int j=0;j<fields.length-1;j++){
					if(!aliasList.contains(CommonUtil.getAlias(fields, j))){
						criteria.createAlias(CommonUtil.getPath(fields, j), CommonUtil.getAlias(fields, j));
						aliasList.add(CommonUtil.getAlias(fields, j));
					}
				}
				if(BaseEnum.OrderType.asc.equals(orderType)){
					criteria.addOrder(Order.asc(CommonUtil.getPath(fields, fields.length - 1)));
				}else{
					criteria.addOrder(Order.desc(CommonUtil.getPath(fields, fields.length - 1)));
				}
			}else{
				if(BaseEnum.OrderType.asc.equals(orderType)){
					criteria.addOrder(Order.asc(orderBy));
				}else{
					criteria.addOrder(Order.desc(orderBy));
				}
			}
		}
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> getAll(String hql) {
		return sessionFactory.getCurrentSession().createQuery(hql).list();
	}

	public Query getQuery(String hql){
		return sessionFactory.getCurrentSession().createQuery(hql);
	}
	public Object getUniqueResult(String hql){
		return sessionFactory.getCurrentSession().createQuery(hql).uniqueResult();
	}

	public Object getUniqueResult(String hql, Map<String, Object> params) {
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		if(params!=null&&params.size()>0){
			for(String key : params.keySet()){
				Object value = params.get(key);
				if(value instanceof Collection){
					query.setParameterList(key, (Collection<?>) value);
				}else if(value instanceof Object[]){
					query.setParameterList(key, (Object[]) value);
				}else{
					query.setParameter(key, value);
				}
			}
		}
		return query.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> getList(String hql, Map<String, Object> params) {
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		if(params!=null&&params.size()>0){
			for(String key : params.keySet()){
				Object value = params.get(key);
				if(value instanceof Collection){
					query.setParameterList(key, (Collection<?>) value);
				}else if(value instanceof Object[]){
					query.setParameterList(key, (Object[]) value);
				}else{
					query.setParameter(key, value);
				}
			}
		}
		return query.list();
	}

	@Override
	public List<Map<String, Object>> getMapedList(String hql, Map<String, Object> params) {
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		if(params!=null&&params.size()>0){
			for(String key : params.keySet()){
				Object value = params.get(key);
				if(value instanceof Collection){
					query.setParameterList(key, (Collection<?>) value);
				}else if(value instanceof Object[]){
					query.setParameterList(key, (Object[]) value);
				}else{
					query.setParameter(key, value);
				}
			}
		}
		return query.list();
	}

	public Pager findByPager(Pager pager, DetachedCriteria detachedCriteria, List<String> aliasList, String search, String filters, String sidx, String sord) {
		if (pager == null) {
			pager = new Pager();
		}
		if(detachedCriteria == null){
			detachedCriteria = DetachedCriteria.forClass(entityClass);
		}
		//createAlias Key集合
		if(aliasList == null){
			aliasList = new ArrayList<String>();
		}
		if(StringUtils.isNotEmpty(search)){
			boolean _search = Boolean.parseBoolean(search);
			if(_search&& StringUtils.isNotEmpty(filters)){
				JSONObject filtersJson = JSONObject.fromObject(filters);
				if(StringUtils.equals(filtersJson.getString("groupOp"), "AND")){
					//查询条件
					JSONArray rulesJsons = filtersJson.getJSONArray("rules");
					//遍历条件
					for(int i=0;i<rulesJsons.size();i++){
						JSONObject rule = rulesJsons.getJSONObject(i);
						if(rule.has("field")&& StringUtils.isNotEmpty(rule.getString("field"))&&rule.has("data")&& StringUtils.isNotEmpty(rule.getString("data"))){
							String field = rule.getString("field");
							if(field.contains("_")){
								String[] fields = field.split("_");
								for(int j=0;j<fields.length-1;j++){
									if(!aliasList.contains(fields[j])){
										detachedCriteria.createAlias(CommonUtil.getPath(fields, j), CommonUtil.getAlias(fields, j));
										aliasList.add(CommonUtil.getAlias(fields, j));
									}
								}
								detachedCriteria.add(Restrictions.like(CommonUtil.getPath(fields, fields.length - 1), CommonUtil.getData(field, rule.getString("data"))));
							}else{
								detachedCriteria.add(Restrictions.like(field, CommonUtil.getData(field, rule.getString("data"))));
							}
						}
					}
				}
			}
		}
		if(StringUtils.isNotEmpty(sidx)&& StringUtils.isNotEmpty(sord)){
			if(sidx.contains("_")){
				String[] fields = sidx.split("_");
				for(int j=0;j<fields.length-1;j++){
					if(!aliasList.contains(fields[j])){
						detachedCriteria.createAlias(CommonUtil.getPath(fields, j), CommonUtil.getAlias(fields, j));
						aliasList.add(CommonUtil.getAlias(fields, j));
					}
				}
				if(StringUtils.equals("asc", sord)){
					detachedCriteria.addOrder(Order.asc(CommonUtil.getPath(fields, fields.length - 1)));
				}else{
					detachedCriteria.addOrder(Order.desc(CommonUtil.getPath(fields, fields.length - 1)));
				}
			}else{
				if(StringUtils.equals("asc", sord)){
					detachedCriteria.addOrder(Order.asc(sidx));
				}else{
					detachedCriteria.addOrder(Order.desc(sidx));
				}
			}
		}
		return findByPager(pager, detachedCriteria, aliasList);
	}

    // 传入动态查询的条件，和页数，进行动态查询和分页
    public Pager findByPager(Pager pager, DetachedCriteria detachedCriteria) {
        if (pager == null) {
            pager = new Pager();
        }
        Integer pageNumber = pager.getPageNumber();
        Integer pageSize = pager.getPageSize();
        String property = pager.getProperty();
        String keyword = pager.getKeyword();
        String orderBy = pager.getOrderBy();
        BaseEnum.OrderType orderType = pager.getOrderType();

        Criteria criteria = detachedCriteria
                .getExecutableCriteria(getSession());
        if (StringUtils.isNotEmpty(property) && StringUtils.isNotEmpty(keyword)) {

			/*
			 * String propertyString = ""; if (property.contains(".")) { String
			 * propertyPrefix = StringUtils.substringBefore(property, ".");
			 * String propertySuffix = StringUtils.substringAfter(property,
			 * "."); //重新定义一个别名，把propertyPrefix替换为model
			 * criteria.createAlias(propertyPrefix, "model"); propertyString =
			 * "model." + propertySuffix; } else { propertyString = property; }
			 * criteria.add(Restrictions.like(propertyString, "%" + keyword +
			 * "%"));
			 */
            // createAlias Key集合
            List<String> alias = new ArrayList<String>();
            if (property.contains(".")) {
                String[] fields = StringUtils.split(property, ".");
                for (int j = 0; j < fields.length - 1; j++) {
                    if (!alias.contains(fields[j])) {
                        criteria.createAlias(CommonUtil.getAliasPath(fields, j),
                                fields[j]);
                        alias.add(fields[j]);
                    }
                }
                // System.out.println(property + " - " +
                // CommonUtil.getPath(fields, fields.length-1, fields.length-2)
                // + " - " + keyword);
                criteria.add(Restrictions.like(CommonUtil.getAliasPath(fields,
                        fields.length - 1, fields.length - 2), "%" + keyword
                        + "%"));
            } else {
                criteria.add(Restrictions.like(property, "%" + keyword + "%"));
            }
        }

        Integer totalCount = (Integer.parseInt(criteria
                .setProjection(Projections.rowCount()).uniqueResult()
                .toString()));

        criteria.setProjection(null);
        criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
        if (pageNumber > 0) {
            criteria.setFirstResult((pageNumber - 1) * pageSize);
            criteria.setMaxResults(pageSize);
        }
        if (StringUtils.isNotEmpty(orderBy) && orderType != null) {
            if (orderType == BaseEnum.OrderType.asc) {
                criteria.addOrder(Order.asc(orderBy));
            } else {
                criteria.addOrder(Order.desc(orderBy));
            }
        }
        pager.setTotalCount(totalCount);
        pager.setList(criteria.list());
        return pager;
    }

}