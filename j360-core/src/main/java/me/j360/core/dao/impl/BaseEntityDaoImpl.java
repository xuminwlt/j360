package me.j360.core.dao.impl;

import me.j360.base.bean.BaseEnum;
import me.j360.base.bean.Pager;
import me.j360.base.dao.impl.BaseDaoImpl;
import me.j360.base.util.CommonUtil;
import me.j360.core.dao.BaseEntityDao;
import me.j360.core.entity.biz.Client;
import me.j360.core.entity.common.Company;
import me.j360.core.entity.common.Department;
import me.j360.core.entity.common.Users;
import me.j360.core.entity.parent.BaseEntity;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.*;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;


/**
 * Dao实现类 - Dao实现类实体基类
 * ============================================================================
 * 版权所有 2013。
 * 
 * @author fallenpanda
 * @version 0.1 2013-01-06
 * ============================================================================
 */

@Repository
public abstract class BaseEntityDaoImpl<T extends BaseEntity, PK extends Serializable> extends BaseDaoImpl<T, PK> implements BaseEntityDao<T, PK> {

    private Class<T> entityClass;

    @SuppressWarnings("unchecked")
    public BaseEntityDaoImpl() {
        this.entityClass = null;
        Class<?> c = getClass();
        Type type = c.getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            Type[] parameterizedType = ((ParameterizedType) type)
                    .getActualTypeArguments();
            this.entityClass = (Class<T>) parameterizedType[0];
        }
    }

    public Pager findByPagerAndStates(Pager pager, BaseEnum.StateEnum[] states) {
        if (pager == null) {
            pager = new Pager();
        }
        DetachedCriteria detachedCriteria = DetachedCriteria
                .forClass(entityClass);

        if (states != null && states.length > 0) {
            Criterion[] criterions = new Criterion[states.length];
            for (int i = 0; i < states.length; i++) {
                criterions[i] = Restrictions.eq("state", states[i]);
            }
            detachedCriteria.add(Restrictions.or(criterions));
        }
        // detachedCriteria.addOrder(Order.desc("createDate"));
        return findByPager(pager, detachedCriteria);
    }

    public Pager findByPagerAndCompany(Pager pager, Users users,
                                       Company company, BaseEnum.StateEnum[] states) {
        if (pager == null) {
            pager = new Pager();
        }
        DetachedCriteria detachedCriteria = DetachedCriteria
                .forClass(entityClass);
        if (states != null && states.length > 0) {
            Criterion[] criterions = new Criterion[states.length];
            for (int i = 0; i < states.length; i++) {
                criterions[i] = Restrictions.eq("state", states[i]);
            }
            detachedCriteria.add(Restrictions.or(criterions));
        }
        if (users != null) {
            detachedCriteria.createAlias("creater", "creater");
            detachedCriteria.add(Restrictions.or(
                    Restrictions.eq("creater", users),
                    Restrictions.eq("creater.id", users.getId())));
        }
        if (company != null) {
            detachedCriteria.createAlias("company", "company");
            detachedCriteria.add(Restrictions.or(
                    Restrictions.eq("company", company),
                    Restrictions.eq("company.id", company.getId())));
        }
        // detachedCriteria.addOrder(Order.desc("createDate"));
        return findByPager(pager, detachedCriteria);
    }

    public Pager findByPagerAndCompany(Pager pager, Users users,
                                       Company company, Date beginDate, Date endDate,BaseEnum.StateEnum[] states) {
        if (pager == null) {
            pager = new Pager();
        }
        String order = "createDate";
        if(entityClass.getName().contains("Negotiation")){
            order = "startTime";
        }else if(entityClass.getName().contains("Reservation")){
            order = "perTime";
        }else if(entityClass.getName().contains("Announcement")){
            order = "perTime";
        }else if(entityClass.getName().contains("Memorandum")){
            order = "perTime";
        }

        DetachedCriteria detachedCriteria = DetachedCriteria
                .forClass(entityClass);
        if (states != null && states.length > 0) {
            Criterion[] criterions = new Criterion[states.length];
            for (int i = 0; i < states.length; i++) {
                criterions[i] = Restrictions.eq("state", states[i]);
            }
            detachedCriteria.add(Restrictions.or(criterions));
        }
        if (users != null) {
            detachedCriteria.createAlias("creater", "creater");
            detachedCriteria.add(Restrictions.or(
                    Restrictions.eq("creater", users),
                    Restrictions.eq("creater.id", users.getId())));
        }
        if (company != null) {
            detachedCriteria.createAlias("company", "company");
            detachedCriteria.add(Restrictions.or(
                    Restrictions.eq("company", company),
                    Restrictions.eq("company.id", company.getId())));
        }
        if(beginDate != null){
            detachedCriteria.add(Restrictions.ge(order, beginDate));
        }
        if(endDate != null){
            detachedCriteria.add(Restrictions.lt(order, endDate));
        }
        detachedCriteria.addOrder(Order.desc(order));
        return findByPager(pager, detachedCriteria);
    }

    @Override
    public Pager queryValidByPager(Pager pager) {
        if (pager == null) {
            pager = new Pager();
        }
        DetachedCriteria detachedCriteria = DetachedCriteria
                .forClass(entityClass);
        detachedCriteria
                .add(Restrictions.ne("state", BaseEnum.StateEnum.delete.value()));
        // detachedCriteria.addOrder(Order.desc("createDate"));
        return findByPager(pager, detachedCriteria);
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


    /**
     * #1所有当前人所在的部门(负责人身份/开启组内共享) + 下级所有部门(负责人身份) -> 得出所有能够看到的创建人的集合
     -- SELECT users_id FROM ss_duty as duty WHERE duty.department_id in (select department.id FROM ss_department as department where department.id IN (SELECT duty.department_id from ss_duty  as duty where duty.users_id='3426359a4487bd4c014487bef5fd0005' and (duty.dutyState = 'Principal' or '1'='1')) or department.parent_id in (SELECT duty.department_id  from ss_duty  as duty where duty.users_id='3426359a4487bd4c014487bef5fd0005' and duty.dutyState = 'Principal'))
     #2所有文档里面包含他本人的文档 (如果有产品经理的话+ or re.productManager_id = '3426359a4487bd4c014487bef5fd0005' )

     SELECT DISTINCT(re.id) from ss_reservation as re left join ss_reservation_ss_users as visit on re.id = visit.ss_Reservation_id WHERE
     (visit.visitMan_id = '3426359a4487bd4c014487bef5fd0005'
     OR creater_id IN (SELECT users_id FROM ss_duty as duty WHERE duty.department_id in (select department.id FROM ss_department as department where department.id IN (SELECT duty.department_id from ss_duty  as duty where duty.users_id='3426359a4487bd4c014487bef5fd0005' and (duty.dutyState = 'Principal' or '1'='1')) or department.parent_id in (SELECT duty.department_id  from ss_duty  as duty where duty.users_id='3426359a4487bd4c014487bef5fd0005' and duty.dutyState = 'Principal'))
     ));

     * 五级权限计算测试代码
     * 客户、联系人 -》 负责人+组共享(不含visitMan)
     * 预约、洽谈、成交 -》 负责人+组共享+参与人
     * 畅谈室、业务 -》 负责人+组共享+参与人+产品经理
     *
     * */
     @Deprecated
     public Pager findByPagerDateSearch(Pager pager,
                                       Users users, Company company, Date beginDate, Date endDate, BaseEnum.StateEnum... states) {
        if(pager == null){
            pager = new Pager();
        }

        String order = "createDate";															//排序的顺序
        String joinTable = "ss_" + entityClass.getSimpleName().toLowerCase()  + "_ss_users"; 	//ss_reservation_ss_users
        String thisTableId = "ss_" + entityClass.getSimpleName()  + "_id";						//ss_Reservation_id

        if(entityClass.getName().contains("Negotiation")){
            order = "startTime";
        }else if(entityClass.getName().contains("Reservation")){
            order = "perTime";
        }
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(entityClass);
        if (company != null) {
            detachedCriteria.createAlias("company", "company");
            detachedCriteria.add(Restrictions.or(
                    Restrictions.eq("company", company),
                    Restrictions.eq("company.id", company.getId())));
        } else {
            return pager;
        }

        if(users != null){

            //时间筛选
            //预约 - 时间段查询
            if (entityClass.getName().contains("Reservation")||entityClass.getName().contains("Negotiation")){
                if(beginDate!=null&&endDate!=null){
                    Criterion[] criterions = new Criterion[2];
                    //结束时间不为空
                    criterions[0] = Restrictions.and(Restrictions.isNotNull("endTime"), Restrictions.and(Restrictions.lt(order, endDate), Restrictions.ge("endTime", beginDate)));
                    //结束时间为空
                    criterions[1] = Restrictions.and(Restrictions.isNull("endTime"), Restrictions.and(Restrictions.ge(order, beginDate), Restrictions.lt(order, endDate)));
                    //开始时间不为空
                    detachedCriteria.add(Restrictions.and(Restrictions.isNotNull(order), Restrictions.or(criterions)));
                }else{
                    if(beginDate != null){
                        detachedCriteria.add(Restrictions.ge(order, beginDate));
                    }
                    if(endDate != null){
                        detachedCriteria.add(Restrictions.lt(order, endDate));
                    }
                }
            } else {
                if(beginDate != null){
                    detachedCriteria.add(Restrictions.ge(order, beginDate));
                }
                if(endDate != null){
                    detachedCriteria.add(Restrictions.lt(order, endDate));
                }
            }

            //通用处理单元
            if(states!=null&&states.length>0){
                Criterion[] criterions = new Criterion[states.length];
                for(int i=0;i<states.length;i++){
                    criterions[i]= Restrictions.eq("state", states[i]);
                }
                detachedCriteria.add(Restrictions.or(criterions));
            }
            //默认时间降序
            detachedCriteria.addOrder(Order.desc(order));
            return findByPager(pager, detachedCriteria);
        }
        return pager;
    }

    /**
     * 多态权限方法
     * */
    public Pager findByPagerDateSearchFormState(Pager pager,DetachedCriteria detachedCriteria,
                                                Users users, Company company, Client client, Object... formState){
        return findByPagerDateSearchFormState(pager,detachedCriteria, null, null,
                users,  company,  client, null, null,  null, formState);
    }

    public Pager findByPagerDateSearchFormState(Pager pager,
                                                Users users, Company company, Client client,Date beginDate, Date endDate, Object... formState){
        return findByPagerDateSearchFormState(pager,null, null, null,
                 users,  company,  client, null, beginDate,  endDate, formState);
    }

    public Pager findByPagerDateSearchFormState(Pager pager,
                                                Users users, Company company, Client client,String searchDate,Date beginDate, Date endDate, Object... formState){
        return findByPagerDateSearchFormState(pager,null, null, null,
                users,  company,  client, searchDate, beginDate,  endDate, formState);
    }

    public Pager findByPagerDateSearchFormState(Pager pager,Map<String, Object> params,
                                                Users users, Company company, Client client,String searchDate,Date beginDate, Date endDate, Object... formState){
        return findByPagerDateSearchFormState(pager,null, null, params,
                users,  company,  client, searchDate, beginDate,  endDate, formState);
    }

    /**
     * 通用的计算五级权限的唯一计算方法。
     * */
    public Pager findByPagerDateSearchFormState(Pager pager,DetachedCriteria detachedCriteria, List<String> aliasList,  Map<String, Object> params,
                                       Users users, Company company, Client client,String searchDate,Date beginDate, Date endDate, Object... formState) {
        Assert.notNull(company,"company is null");
        if(pager == null){
            pager = new Pager();
        }
        String orderBy = pager.getOrderBy();
        BaseEnum.OrderType orderType = pager.getOrderType();
        if(StringUtils.isEmpty(orderBy)){
            if(StringUtils.containsIgnoreCase(entityClass.getName(), "Client")){
                orderBy = "createDate";
                orderType = BaseEnum.OrderType.desc;
            }else if(StringUtils.containsIgnoreCase(entityClass.getName(), "Linkman")){
                orderBy = "createDate";
                orderType = BaseEnum.OrderType.desc;
            }else if(StringUtils.containsIgnoreCase(entityClass.getName(),"Reservation")){
                orderBy = "perTime";
                orderType = BaseEnum.OrderType.desc;
            }else if(StringUtils.containsIgnoreCase(entityClass.getName(), "Negotiation")){
                orderBy = "startTime";
                orderType = BaseEnum.OrderType.desc;
            }else if(StringUtils.containsIgnoreCase(entityClass.getName(), "Business")){
                orderBy = "preDate";
                orderType = BaseEnum.OrderType.desc;
            }else if(StringUtils.containsIgnoreCase(entityClass.getName(), "Contract")){
                orderBy = "signTime";
                orderType = BaseEnum.OrderType.desc;
            }else if(StringUtils.containsIgnoreCase(entityClass.getName(), "ProceedsPlan")){
                orderBy = "preDate";
                orderType = BaseEnum.OrderType.desc;
            }else{
                orderBy = "createDate";
                orderType = BaseEnum.OrderType.desc;
            }
        }
        if(StringUtils.isEmpty(searchDate)){
            //LogUtil.info(entityClass.getName() + " " + StringUtils.containsIgnoreCase(entityClass.getName(), "Negotiation"));
            if(StringUtils.containsIgnoreCase(entityClass.getName(), "Negotiation")){
                searchDate = "startTime";
            }else if(StringUtils.containsIgnoreCase(entityClass.getName(),"Reservation")){
                searchDate = "perTime";
            }else{
                searchDate = "createDate";
            }
        }
        //LogUtil.info(searchDate);
		//排序的顺序
        String joinTable = "ss_" + entityClass.getSimpleName().toLowerCase()  + "_ss_users"; 	//ss_reservation_ss_users
        String thisTableId = "ss_" + entityClass.getSimpleName()  + "_id";						//ss_Reservation_id
        if(detachedCriteria == null){
            detachedCriteria = DetachedCriteria.forClass(entityClass);
        }
        if (company != null) {
            detachedCriteria.createAlias("company", "company");
            detachedCriteria.add(Restrictions.or(
                    Restrictions.eq("company", company),
                    Restrictions.eq("company.id", company.getId())));
        }
        if(users != null) {
            //组织架构处理单元

        }
        //时间筛选
        //预约 - 时间段查询
        if (entityClass.getName().contains("Reservation") || entityClass.getName().contains("Negotiation")) {
            if (beginDate != null && endDate != null) {
                Criterion[] criterions = new Criterion[2];
                //结束时间不为空
                criterions[0] = Restrictions.and(Restrictions.isNotNull("endTime"), Restrictions.and(Restrictions.lt(searchDate, endDate), Restrictions.ge("endTime", beginDate)));
                //结束时间为空
                criterions[1] = Restrictions.and(Restrictions.isNull("endTime"), Restrictions.and(Restrictions.ge(searchDate, beginDate), Restrictions.lt(searchDate, endDate)));
                //开始时间不为空
                detachedCriteria.add(Restrictions.and(Restrictions.isNotNull(searchDate), Restrictions.or(criterions)));


            } else {
                if (beginDate != null) {
                    detachedCriteria.add(Restrictions.ge(searchDate, beginDate));
                }
                if (endDate != null) {
                    detachedCriteria.add(Restrictions.lt(searchDate, endDate));
                }
            }
        } else {
            if (beginDate != null) {
                detachedCriteria.add(Restrictions.ge(searchDate, beginDate));
            }
            if (endDate != null) {
                detachedCriteria.add(Restrictions.lt(searchDate, endDate));
            }
        }
        if (client != null) {
            detachedCriteria.createAlias("client", "client");
            detachedCriteria.add(Restrictions.or(
                    Restrictions.eq("client", client),
                    Restrictions.eq("client.id", client.getId())));
        }
        //通用处理单元
        if (formState != null && formState.length > 0) {
            Criterion[] criterions = new Criterion[formState.length];
            for (int i = 0; i < formState.length; i++) {
                criterions[i] = Restrictions.eq("formState", formState[i]);
            }
            detachedCriteria.add(Restrictions.or(criterions));
        }
        //启用和归档状态
        Criterion[] criterions = new Criterion[2];
        criterions[0] = Restrictions.eq("state", BaseEnum.StateEnum.enable);
        criterions[1] = Restrictions.eq("state", BaseEnum.StateEnum.history);
        detachedCriteria.add(Restrictions.or(criterions));
        return findByPager(pager, detachedCriteria,aliasList, null, null, null, params, orderBy,  orderType);
    }

    public Map<String,Object> getBasicParams(Map<String,Object> params,Users users,Department department,Company company){
        if(params==null){
            params = new HashMap<String,Object>();
        }
        params.put("state", new BaseEnum.StateEnum[]{BaseEnum.StateEnum.enable,BaseEnum.StateEnum.history});
        if(users!=null){
            params.put("saler.id", users.getId());
        }
        if(company!=null){
            params.put("company.id", company.getId());
        }
        /*if(department!=null){
            List<Users> creaters = dutyService.getPersons(department);
            params.put("saler", creaters);
        }*/
        return params;
    }
}
