package me.j360.base.dao.impl;

import me.j360.base.bean.BaseEnum;
import me.j360.base.bean.Pager;
import me.j360.base.dao.MongoBaseDao;
import me.j360.base.entity.MongoEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created with us-parent -> me.j360.base.dao.impl.
 * User: min_xu
 * Date: 2014/9/15
 * Time: 14:39
 * 说明：
 */
@Repository
public abstract class MongoBaseDaoImpl<T extends MongoEntity, PK extends Serializable> implements MongoBaseDao<T, PK> {

    private static final int DEFAULT_SKIP = 0;
    private static final int DEFAULT_LIMIT = 200;

    private Class<T> entityClass;

    /**
     * spring mongodb　集成操作类　
     */
    @Resource
    protected MongoTemplate mongoTemplate;

    @SuppressWarnings("unchecked")
    public MongoBaseDaoImpl() {
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

    @Override
    public List<T> find(Query query) {
        return mongoTemplate.find(query, entityClass);
    }

    @Override
    public T findOne(Query query) {
        return mongoTemplate.findOne(query, entityClass);
    }

    @Override
    public void update(Query query, Update update) {
        mongoTemplate.findAndModify(query, update, entityClass);
    }

    @Override
    public T save(T entity) {
        mongoTemplate.insert(entity);
        return entity;
    }

    @Override
    public T findById(String id) {
        System.out.println(entityClass);
        return mongoTemplate.findById(id, entityClass);
    }

    @Override
    public T findById(String id, String collectionName) {
        return mongoTemplate.findById(id, entityClass, collectionName);
    }
    //查询主要用到Query和Criteria两个对象
    //Query query = new Query();
    //Criteria criteria = where("targetId").is(targetId);
    // criteria.and("name").is("cuichongfei");等于
    // List<String> interests = new ArrayList<String>();
    // interests.add("study");
    // interests.add("linux");
    // criteria.and("interest").in(interests);   in查询
    // criteria.and("home.address").is("henan"); 内嵌文档查询
    // criteria.and("").exists(false);           列存在
    // criteria.and("").lte();                   小于等于
    // criteria.and("").regex("");               正则表达式
    // criteria.and("").ne("");                  不等于
    @Override
    public Pager findPage(Pager pager, Query query) {
        long count = this.count(query);
        //LogUtil.info(count);
        int i = (int)count;
        pager.setTotalCount(i);
        int pageNumber = pager.getPageNumber();
        int pageSize = pager.getPageSize();
        if(StringUtils.isNotEmpty(pager.getOrderBy())){
            query.with(new Sort(BaseEnum.OrderType.asc.equals(pager.getOrderBy())?Sort.Direction.ASC:Sort.Direction.DESC, pager.getOrderBy()));
        }
        query.skip((pageNumber - 1) * pageSize).limit(pageSize);
        List<T> rows = this.find(query);
        pager.setList(rows);
        //LogUtil.info("pager:"+pager.getTotalCount() + " " + pager.getPageCount());
        return pager;
    }

    @Override
    public long count(Query query) {
        return mongoTemplate.count(query, entityClass);
    }
}
