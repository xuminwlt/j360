package me.j360.core.service.common;

import com.google.gson.reflect.TypeToken;
import me.j360.base.bean.Pager;
import me.j360.base.bean.Result;
import me.j360.base.service.common.ResultService;
import me.j360.base.util.DateUtil;
import me.j360.base.util.LogUtil;
import me.j360.base.util.mapper.JsonMapper;
import me.j360.core.bean.event.ResumeEvent;
import me.j360.core.bean.json.FormEntityGsonBuilder;
import me.j360.core.entity.biz.Client;
import me.j360.core.entity.biz.Linkman;
import me.j360.core.entity.common.Users;
import me.j360.core.entity.parent.FormEntity;
import me.j360.ext.bean.EventBean;
import me.j360.ext.entity.Resume;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.Type;
import java.util.*;

/**
 * Created with us2 -> com.fz.us.core.service.common.
 * User: min_xu
 * Date: 2014-11-25
 * Time: 15:57
 * 说明：
 */
@Service
public class ResumeBeanService {
    //@Resource
    //private ResumeService resumeService;
    @Resource
    private ResultService resultService;
    //@Resource
    //private UsersService usersService;
    @Resource
    private ApplicationContext applicationContext;
    //@Resource
    //private ClientService clientService;

    protected static JsonMapper binder = JsonMapper.nonEmptyMapper();

    //textObject的分隔符
    private static final String SIGN = "; ";

    public Result info(EventBean.EventEnum event,FormEntity formEntity,FormEntity preFormEntity,Users users,String text){
        return resume(EventBean.EventLevel.info,event, formEntity, preFormEntity, users, text);
    }
    public Result warn(EventBean.EventEnum event,FormEntity formEntity,FormEntity preFormEntity,Users users,String text){
        return resume(EventBean.EventLevel.warn,event, formEntity, preFormEntity, users, text);
    }
    /**
     * 带有履历等级的记录
     * */
    public Result resume(EventBean.EventLevel level,EventBean.EventEnum event,FormEntity formEntity,FormEntity preFormEntity,Users users,String text){
        try {
            Resume resume = formResume(event,formEntity,users,text);
            //resume.setLevel(level);
            //formEntity to json to Object
            formEntityObject(event,resume,formEntity,preFormEntity);

            //Event 异步存储，不使用jms
            //resumeService.createMongoEntity(resume);
            buildTranslateEvent(resume);
        }catch (Exception e){
            LogUtil.error(e.getMessage(), e);
        }
        return resultService.success();
    }

    /**
     * 记录字段内容到json
     * */
    public void formEntityObject(EventBean.EventEnum event,Resume resume,FormEntity formEntity,FormEntity preFormEntity){

        //clientId 初始化
        if(formEntity instanceof Client){
            resume.setClientId(formEntity.getId());
        }else if(formEntity instanceof Linkman){
            if(((Linkman) formEntity).getClient()!=null)
                resume.setClientId(((Linkman) formEntity).getClient().getId());
        }
        resume.setJsonObject(toJson(formEntity));
        //if(EventBean.EventEnum.create.equals(event)){}
        if(EventBean.EventEnum.update.equals(event)){
            if(null != preFormEntity){
                resume.setPreJsonObject(toJson(preFormEntity));

            }
        }
    }

    /**
     * 初始化并记录关键内容到resume
     * 操作stack往前计算4个就是计算的方法
     * 3:method
     * 2:resume
     * 1:warn
     * 0:this
     * */
    public Resume formResume(EventBean.EventEnum event,FormEntity formEntity,Users users,String text){
        StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
        Resume resume = new Resume(event,text,formEntity.getId(),formEntity.getClass().getName());
        resume.setCreateDate(new Date());
        resume.setBizerId(null == users ? "" : users.getId());
        //resume.setService(EventBean.ServiceEnum.update);
        resume.setEventClass(stacks[4].getClassName());
        resume.setEventMethod(stacks[4].getMethodName());
        resume.setEventDate(DateUtil.DateTimeToString(new Date()));
        return resume;
    }

    /**
     * 发起resumeEvent方法
     * */
    public void buildTranslateEvent(Resume resume){
        applicationContext.publishEvent(new ResumeEvent(resume));
    }

    /**
     * 执行event方法
     * */
    public Result translate(Resume resume){
        //比较前后的结果
        String json = resume.getJsonObject();
        String preJson = resume.getPreJsonObject();
        //转化为map进行比较
        Map<String,String> map = null;
        Map<String,String> preMap = null;

        if(EventBean.EventEnum.update.equals(resume.getEvent())){
            if(StringUtils.isNotEmpty(json)){
                try {
                    map = binder.fromJson(json,Map.class);
                    //设置clientId，客户菜单下面的所有动态通过 clientId = id || id = id
                    if(map.containsKey("clientId")){
                        resume.setClientId((String) map.get("clientId"));
                    }
                    //解释preJson
                    if(StringUtils.isNotEmpty(preJson)){
                        preMap = binder.fromJson(preJson,Map.class);
                        resume.setTextObject(getDiffText(resume,map,preMap));
                    }
                }catch (Exception e){
                    LogUtil.error(e.getMessage(),e);
                }
            }
        }
        //resumeService.createMongoEntity(resume);
        return resultService.success();
    }

    /**
     * 得到两个对象前后的差异说明
     * */
    private String getDiffText(Resume resume,Map<String,String> map,Map<String,String> preMap){
        StringBuffer sb = new StringBuffer();
        Set<String> keySet = map.keySet();
        for (String key : keySet) {
            if (preMap.containsKey(key)) {
                //对比两者的值是否相等
                if(!StringUtils.equalsIgnoreCase(map.get(key),preMap.get(key))){
                    String label = "";//getLabelByAddress(resume.getTargetClass(),key);
                    if(StringUtils.isNotEmpty(label)){
                        sb.append(getDiffFormatKey(label,map.get(key),preMap.get(key))).append(SIGN);
                    }
                }
            }
        }
        if(StringUtils.isEmpty(sb.toString())){
            return "无内容修改";
        }
        return sb.toString();
    }

    //格式化对比的数据
    private String getDiffFormatKey(String label,String value,String preValue){
        return "["+label + "：" + preValue + "->" + value + "]";
    }



    /*public Pager getTargetPager(Pager pager, String targetId){
        return resumeService.getTargetResume(pager, targetId);
    }

    public Resume getLastTarget(String targetId){
        return resumeService.getLastTargetResume(targetId);
    }



    public Pager getPager(Pager pager,String usersId, String companyId, String targetId,Map<String,Object> params){
        return resumeService.getPager(pager,usersId,companyId,targetId,params);
    }

    public Pager getClientPager(Pager pager, String companyId ,String clientId,Map<String,Object> params){
        return resumeService.getClientPager(pager,companyId,clientId,params);
    }

    public Map<String, Object> getListItemMap(Resume resume) {
        Map<String, Object> map = resumeService.getListItemMap(resume);
        if(StringUtils.isNotEmpty(resume.getBizerId())){
            Users bizer = usersService.get(resume.getBizerId());
            if(null != bizer){
                map.put("bizerName", bizer.getName());
                map.put("bizerHeadImageId", (bizer.getHeadImage()!=null&&StringUtils.isNotEmpty(bizer.getHeadImage().getGridId()))?bizer.getHeadImage().getGridId():"");
            }
        }
        if(StringUtils.endsWithIgnoreCase(resume.getTargetClass(), "client")){
            Client item = clientService.get(resume.getTargetId());
            if(item!=null){
                map.put("targetName", item.getName());
            }else{
                map.put("targetName", "");
            }
        }else if(StringUtils.endsWithIgnoreCase(resume.getTargetClass(), "linkman")){
            Linkman item = linkmanService.get(resume.getTargetId());
            if(item!=null){
                map.put("targetName", item.getName());
            }else{
                map.put("targetName", "");
            }
        }else if(StringUtils.endsWithIgnoreCase(resume.getTargetClass(), "reservation")){
            Reservation item = reservationService.get(resume.getTargetId());
            if(item!=null){
                map.put("targetName", item.getName());
            }else{
                map.put("targetName", "");
            }
        }else if(StringUtils.endsWithIgnoreCase(resume.getTargetClass(), "negotiation")){
            Negotiation item = negotiationService.get(resume.getTargetId());
            if(item!=null){
                map.put("targetName", item.getName());
            }else{
                map.put("targetName", "");
            }
        }else if(StringUtils.endsWithIgnoreCase(resume.getTargetClass(), "business")){
            Business item = businessService.get(resume.getTargetId());
            if(item!=null){
                map.put("targetName", item.getName());
            }else{
                map.put("targetName", "");
            }
        }else if(StringUtils.endsWithIgnoreCase(resume.getTargetClass(), "contract")){
            Contract item = contractService.get(resume.getTargetId());
            if(item!=null){
                map.put("targetName", item.getName());
            }else{
                map.put("targetName", "");
            }
        }else if(StringUtils.endsWithIgnoreCase(resume.getTargetClass(), "proceedsplan")){
            ProceedsPlan item = proceedsPlanService.get(resume.getTargetId());
            if(item!=null){
                map.put("targetName", item.getName());
            }else{
                map.put("targetName", "");
            }
        }else{
            map.put("targetName", "");
        }
        return map;
    }*/

    /**
     * 转化成jqGrid的对象
     *
    public Map<String,Object> jqGridDetailMap(Pager pager,String usersId, String companyId, String targetId,Map<String,Object> params){
        pager = resumeService.getCompanyResume(pager, null, null, companyId);
        return pagerToJqGridMap(pager);
    }

    public Map<String,Object> pagerToJqGridMap(Pager pager){
        List<Map> dataRows = new ArrayList<Map>();
        Map<String, Object> data = new HashMap<String, Object>();
        List<Resume> list = (List<Resume>) pager.getList();
        for(Resume mongoEntity:list){
            Map map = resumeService.getDetailMap(mongoEntity);
            if(mongoEntity.getBizerId().equals("anonymous") || StringUtils.isEmpty(mongoEntity.getBizerId())){
                map.put("bizer", "系统");
            }else{
                Users users = usersService.get(mongoEntity.getBizerId());
                if(null != users){
                    map.put("bizer", users.getName());
                    map.put("photoId", users.getHeadImage()==null?"":users.getHeadImage().getGridId());
                }
            }
            dataRows.add(map);
        }
        data.put("dataRows", dataRows);
        data.put("records", pager.getTotalCount());
        data.put("page",pager.getPageNumber());
        data.put("rows",pager.getPageSize());
        data.put("total",pager.getPageCount());
        return data;
    }
     */
    //转化成JSON
    public String toJson(FormEntity formEntity){
        if(formEntity instanceof Client){
            Type type = new TypeToken<Client>(){}.getType();
            return FormEntityGsonBuilder.create().toJson((Client) formEntity,type);
        }else{
            return FormEntityGsonBuilder.create().toJson(formEntity);
        }
    }

    public Object fromJson(String json,Class clazz){
        return FormEntityGsonBuilder.create().fromJson(json,clazz);
    }
}
