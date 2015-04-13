package me.j360.base.dao;

import me.j360.base.bean.Pager;
import me.j360.base.entity.MongoEntity;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.io.Serializable;
import java.util.List;

/**
 * Created with us-parent -> com.fz.us.base.dao.
 * User: min_xu
 * Date: 2014/9/15
 * Time: 14:37
 * 说明：
 */
public interface  MongoBaseDao<T extends MongoEntity, PK extends Serializable> {
    /**
     * 通过条件查询实体(集合)
     *
     * @param query
     */
    public List<T> find(Query query) ;

    /**
     * 通过一定的条件查询一个实体
     *
     * @param query
     * @return
     */
    public T findOne(Query query) ;

    /**
     * 通过条件查询更新数据
     *
     * @param query
     * @param update
     * @return
     */
    public void update(Query query, Update update) ;

    /**
     * 保存一个对象到mongodb
     *
     * @param entity
     * @return
     */
    public T save(T entity) ;

    /**
     * 通过ID获取记录
     *
     * @param id
     * @return
     */
    public T findById(String id) ;

    /**
     * 通过ID获取记录,并且指定了集合名(表的意思)
     *
     * @param id
     * @param collectionName
     *            集合名
     * @return
     */
    public T findById(String id, String collectionName) ;

    /**
     * 分页查询
     * @param pager
     * @param query
     * @return
     */
    public Pager findPage(Pager pager, Query query);

    /**
     * 求数据总和
     * @param query
     * @return
     */
    public long count(Query query);

}
