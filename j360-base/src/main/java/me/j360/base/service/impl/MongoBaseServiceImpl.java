package me.j360.base.service.impl;

import me.j360.base.bean.Pager;
import me.j360.base.dao.MongoBaseDao;
import me.j360.base.entity.MongoEntity;
import me.j360.base.service.MongoBaseService;
import me.j360.base.service.common.ResultService;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * Created with me.j360.base.service.impl.
 * User: min_xu
 * Date: 2014/9/28
 * Time: 16:35
 * 说明：
 */
@Service
public abstract class MongoBaseServiceImpl<T extends MongoEntity, PK extends Serializable> implements MongoBaseService<T,PK> {

    private MongoBaseDao<T, PK> mongoBaseDao;

    @Resource
    protected ResultService resultService;

    public abstract MongoBaseDao<T, PK> getMongoBaseDao();

    public void setMongoBaseDao(MongoBaseDao<T, PK> mongoBaseDao) {
        this.mongoBaseDao = mongoBaseDao;
    }

    @Override
    public List<T> find(Query query) {
        return getMongoBaseDao().find(query);
    }

    @Override
    public T findOne(Query query) {
        return getMongoBaseDao().findOne(query);
    }

    @Override
    public void update(Query query, Update update) {
        getMongoBaseDao().update(query,update);
    }

    @Override
    public T save(T entity) {
        return getMongoBaseDao().save(entity);
    }

    @Override
    public T findById(String id) {
        return getMongoBaseDao().findById(id);
    }

    @Override
    public T findById(String id, String collectionName) {
        return getMongoBaseDao().findById(id,collectionName);
    }

    @Override
    public Pager findPage(Pager pager, Query query) {
        return getMongoBaseDao().findPage(pager,query);
    }

    @Override
    public long count(Query query) {
        return getMongoBaseDao().count(query);
    }
}
