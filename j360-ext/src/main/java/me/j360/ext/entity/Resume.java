package me.j360.ext.entity;
import me.j360.ext.bean.EventBean;
import org.springframework.data.annotation.Transient;

/**
 * Created with us-parent -> com.fz.us.modules.entity.
 * User: min_xu
 * Date: 2014/9/15
 * Time: 15:06
 * 说明：
 */
public class Resume extends SourceMongoEntity {
    private static final long serialVersionUID = -2755329278196422648L;

    public Resume(){
        super();
    }
    public Resume(EventBean.EventEnum event,String text,String targetId,String targetClass){
        super();
        setEvent(event);
        setText(text);
        setTargetId(targetId);
        setTargetClass(targetClass);
    }

    public Resume(EventBean.EventEnum event,String text,String targetId,String targetClass,String bizerId,String companyId){
        super();
        setEvent(event);
        setText(text);
        setTargetId(targetId);
        setTargetClass(targetClass);
        setBizerId(bizerId);
        setCompanyId(companyId);
    }
    /**
     * 发生事件的类型
     * */
    private EventBean.EventEnum event;



    private EventBean.EventLevel level;
    /**
     * 发生事件文字描述
     * */
    private String text;
    /**
     * 发生事件可能存在的对象封装(需要通过gson进行二次转化)
     * */
    private String jsonObject;
    /**
     * 发生事件可能存在的对象封装(需要通过gson进行二次转化)
     * */
    private String preJsonObject;
    /**
     * 发生事件可能存在的不可封装的对象转化成文字，比如修改记录比较结果
     * */
    private String textObject;

    /**
     * 如果是销售7步的履历，则需要client的Id
     * */
    private String clientId;
    /**
     *
     * */
    private EventBean.TargetTypeEnum targetType;
    private EventBean.ServiceEnum service;

    /**
     * 产生这个履历发生的Class和Method
     * */
    private String eventClass;
    private String eventMethod;

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    private String eventDate;



    private String bizerName;
    private String bizerHeadImageId;


    public String getEventClass() {
        return eventClass;
    }

    public void setEventClass(String eventClass) {
        this.eventClass = eventClass;
    }

    public String getEventMethod() {
        return eventMethod;
    }

    public void setEventMethod(String eventMethod) {
        this.eventMethod = eventMethod;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public EventBean.EventEnum getEvent() {
        return event;
    }

    public void setEvent(EventBean.EventEnum event) {
        this.event = event;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(String jsonObject) {
        this.jsonObject = jsonObject;
    }

    public String getPreJsonObject() {
        return preJsonObject;
    }

    public void setPreJsonObject(String preJsonObject) {
        this.preJsonObject = preJsonObject;
    }
    public String getTextObject() {
        return textObject;
    }

    public void setTextObject(String textObject) {
        this.textObject = textObject;
    }
    public EventBean.TargetTypeEnum getTargetType() {
        return targetType;
    }

    public void setTargetType(EventBean.TargetTypeEnum targetType) {
        this.targetType = targetType;
    }

    public EventBean.ServiceEnum getService() {
        return service;
    }

    public void setService(EventBean.ServiceEnum service) {
        this.service = service;
    }

    public EventBean.EventLevel getLevel() {
        return level;
    }

    public void setLevel(EventBean.EventLevel level) {
        this.level = level;
    }

    @Transient
    public String getBizerName() {
        return bizerName;
    }
    public void setBizerName(String bizerName) {
        this.bizerName = bizerName;
    }
    @Transient
    public String getBizerHeadImageId() {
        return bizerHeadImageId;
    }
    public void setBizerHeadImageId(String bizerHeadImageId) {
        this.bizerHeadImageId = bizerHeadImageId;
    }
}
