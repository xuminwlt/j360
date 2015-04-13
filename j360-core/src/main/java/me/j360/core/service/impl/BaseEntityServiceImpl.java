package me.j360.core.service.impl;

import me.j360.base.bean.BaseEnum;
import me.j360.base.bean.Pager;
import me.j360.base.bean.Result;
import me.j360.base.dao.BaseDao;
import me.j360.base.service.common.ResultService;
import me.j360.base.service.impl.BaseServiceImpl;
import me.j360.base.util.PinyinUtil;
import me.j360.core.dao.BaseEntityDao;
import me.j360.core.entity.common.Company;
import me.j360.core.entity.common.Users;
import me.j360.core.entity.parent.BaseEntity;
import me.j360.core.entity.parent.NameEntity;
import me.j360.core.service.BaseEntityService;
import me.j360.core.service.common.ResumeBeanService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service实现类 - Service实现类实体基类
 * ============================================================================
 * 版权所有 2014。
 * 
 * @author fallenpanda
 * @version 0.1 2014-07-26
 * ============================================================================
 */

@Service
public abstract class BaseEntityServiceImpl<T extends BaseEntity, PK extends Serializable> extends BaseServiceImpl<T, PK> implements BaseEntityService<T, PK> {
	
	public abstract BaseEntityDao<T, PK> getBaseEntityDao();
	
	@Override
	public BaseDao<T, PK> getBaseDao() {
		return getBaseEntityDao();
	}
    @Resource
    protected ResultService resultService;
    @Resource
    protected ResumeBeanService resumeBeanService;

	@Override
	public PK save(T entity, BaseEnum.StateEnum state) {
		entity.setState(state);
		return save(entity);
	}

    @Override
    public PK saveAndEnable(T entity) {
        entity.setState(BaseEnum.StateEnum.enable);
        return save(entity);
    }

    @Override
    public T updateAndEnable(T entity) {
        entity.setState(BaseEnum.StateEnum.enable);
        if(StringUtils.isEmpty(entity.getId())){
            save(entity);
        }else{
            update(entity);
        }
        return entity;
    }

    @Override
    public T updateNameAndEnable(T entity) {
        if(entity instanceof NameEntity){
            if(StringUtils.isNoneEmpty(((NameEntity)entity).getName())){
                ((NameEntity)entity).setPinYin(PinyinUtil.getPingYin(((NameEntity) entity).getName()));
                ((NameEntity)entity).setPinYinHead(PinyinUtil.getPinYinHeadChar(((NameEntity)entity).getName()));
            }
        }
        return updateAndEnable(entity);
    }
    @Override
    public void enable(T entity){
        entity.setState(BaseEnum.StateEnum.enable);
        if(StringUtils.isEmpty(entity.getId())){
            save(entity);
        }else{
            update(entity);
        }
    }
    @Override
    public void disable(T entity){
        entity.setState(BaseEnum.StateEnum.disabled);
        if(StringUtils.isEmpty(entity.getId())){
            save(entity);
        }else{
            update(entity);
        }
    }

	@Override
	public Pager findByPagerBy(Pager pager, Users creater, BaseEnum.StateEnum... states) {
		Map< String, Object> params = new HashMap<String, Object>();
		if(creater!=null){
			params.put("creater", creater);
		}
		if(states!=null&&states.length>0){
			params.put("state", states);
		}
		return findByPager(pager, params);
	}

	@Override
	public List<T> getListBy(Users creater, BaseEnum.StateEnum... states) {
		Map< String, Object> params = new HashMap<String, Object>();
		if(creater!=null){
			params.put("creater", creater);
		}
		if(states!=null&&states.length>0){
			params.put("state", states);
		}
		return getList(params);
	}

    public Pager findByPagerAndCompany(Pager pager,Users users, Company company, BaseEnum.StateEnum[] states) {
        return getBaseEntityDao().findByPagerAndCompany(pager, users, company, states);
    }
    public Pager findByPagerAndCompany(Pager pager, Users users,
                                       Company company, Date beginDate, Date endDate,BaseEnum.StateEnum[] states){
        return getBaseEntityDao().findByPagerAndCompany(pager, users, company, beginDate, endDate, states);
    }
    public Pager queryEnableByPager(Pager pager) {
        return getBaseEntityDao().findByPagerAndStates(pager, new BaseEnum.StateEnum[]{BaseEnum.StateEnum.enable});
    }

    public Pager queryDeletedByPager(Pager pager) {
        return getBaseEntityDao().findByPagerAndStates(pager, new BaseEnum.StateEnum[]{BaseEnum.StateEnum.delete});
    }

    public Pager queryValidByPager(Pager pager) {
        return getBaseEntityDao().queryValidByPager(pager);
    }

    public Pager findByPagerAndStates(Pager pager, BaseEnum.StateEnum[] states) {
        return getBaseEntityDao().findByPagerAndStates(pager, states);
    }

    @Override
    public Result checkReadAccess(T entity,Users users){
        return resultService.success();
        //resultService.build();
    }

    @Override
    public Result checkEditAccess(T entity,Users users){
        return resultService.success();
    }


    @Deprecated
    @Override
    public Pager findByPagerDateSearch(Pager pager,
                                       Users users, Company company, Date beginDate, Date endDate, BaseEnum.StateEnum... states){
        return getBaseEntityDao().findByPagerDateSearch(pager, users, company, beginDate, endDate, states);
    }
}
