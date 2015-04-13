package me.j360.base.test;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSFile;
import me.j360.base.test.entity.Users;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.transaction.TransactionConfiguration;

import java.io.*;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created with j360 -> me.j360.web.test.base.
 * User: min_xu
 * Date: 2015-03-12
 * Time: 11:30
 * 说明：使用spring-data-mongo调用mongodb，需要安装mongodb服务器
 * 见：http://my.oschina.net/smartsales/blog
 */
@TransactionConfiguration(defaultRollback=true)
@ContextConfiguration(locations = {"/applicationContext-base.xml" })
public class MongoDBTest extends SpringTransactionalTestCase {

    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private GridFsTemplate gridOperations;

    @Test
    public void mongoSave(){
        DBObject metaData = new BasicDBObject();
        metaData.put("extra1", "anything 1");
        metaData.put("extra2", "anything 2");

        mongoTemplate.save(metaData);
    }
    //----------------------------------------------------------
    /**
     * 将附件存储到FS
     * @throws java.io.FileNotFoundException
     * */
    @Test
    public void gridFSSave() throws FileNotFoundException {
        String filename = "";
        File file = new File("");
        InputStream inputStream = new FileInputStream(file);
        save(inputStream,"",filename);

    }


    @Test
    public void gridGetInputStream(){
        List<GridFSDBFile> result = gridOperations.find(new Query()
                .addCriteria(Criteria.where("filename").is("testing.jpg")));

        for (GridFSDBFile file : result) {
            System.out.println(file.getFilename());
            System.out.println(file.getContentType());
        }

    }

    @Test
    public void gridFSStore() {
        DBObject metaData = new BasicDBObject();
        metaData.put("extra1", "anything 1");
        metaData.put("extra2", "anything 2");

        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(
                    "D://testing.jpg");
            GridFSFile file = gridOperations.store(inputStream, "testing1.jpg", "image/jpg",
                    metaData);
            System.out.println("Done"+file.getId());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        System.out.println("Done");
    }

    @Test
    public void GridFsAppRead() {
        List<GridFSDBFile> result = gridOperations.find(new Query()
                .addCriteria(Criteria.where("filename").is("testing.jpg")));
        for (GridFSDBFile file : result) {
            System.out.println(file.getFilename());
            System.out.println(file.getContentType());

            System.out.println(file.getId());
            // save as another image
            //file.writeTo("D://new-testing.jpg");

        }

        System.out.println("Done");
    }

    public String save(InputStream inputStream, String contentType,
                       String filename) {

        DBObject metaData = new BasicDBObject();
        metaData.put("filename", filename);
        metaData.put("contentType", contentType);
        GridFSFile file = gridOperations.store(inputStream, filename, metaData);
        return file.getId().toString();
    }

    public GridFSDBFile get(String id) {
        System.out.println("Finding by ID: " + id);
        return gridOperations.findOne(new Query(Criteria.where("_id").is(
                new ObjectId(id))));
    }

    public List listFiles() {
        return gridOperations.find(null);
    }

    public GridFSDBFile getByFilename(String filename) {
        return gridOperations.findOne(new Query(Criteria.where("filename").is(
                filename)));
    }





    @Test
    public void testUpdate()
    {
        mongoTemplate.updateFirst(new Query(Criteria.where("username").is("John0")), Update.update("password", "newpassword"), "userinfo");
    }

    @Test
    public void testDelete()
    {
        WriteResult remove = mongoTemplate.remove(new Query(Criteria.where("id").is("4ffe3486b41f8ed41269a729")), Users.class);

    }

    @Test
    public void testQuery()
    {
        //输出所有表名
        Set<String> set = mongoTemplate.getCollectionNames();
        Iterator<String> it =  set.iterator();
        while(it.hasNext())
        {
            System.out.println(it.next());
        }
    }

}
