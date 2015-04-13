package me.j360.core.entity.biz;

import me.j360.core.entity.parent.FormEntity;

import javax.persistence.*;
import java.util.Set;

/**
 * Created with us-parent -> com.fz.us.core.entity.biz.
 * User: min_xu
 * Date: 2014/9/10
 * Time: 13:49
 * 说明：
 */
@Entity
@Table(name="biz_client")
public class Client extends FormEntity {
    private static final long serialVersionUID = -370316274269471216L;

    public Client(){
        super();
    }

    public Client(String name){
        super();
        this.setName(name);
    }

    /**
     * 客户备注名
     */
    private String  nickName;
    /**
     * 省
     */
    private String province;
    /**
     * 市
     */
    private String city;
    /**
     * 区
     */
    private String county;
    /**
     * 联系地址
     */
    private String address;
    /**
     * 邮政编码
     */
    private String post;
    /**
     * 公司电话
     */
    private String tel;
    /**
     * 传真
     */
    private String fax;
    /**
     * 网址
     */
    private String web;
    /**
     * 发票抬头
     */
    private String invoiceTitle;
    /**
     * 人数规模
     */
    private String staffScale;
    /**
     * 占地规模
     */
    private String areaScale;
    /**
     * 生产规模
     */
    private String produceScale;
    /**
     * 销售规模
     */
    private String salesScale;
    /**
     * 备注
     */
    private String remark;
    /**
     * 联系人
     */
    private Set<Linkman> linkman;
    /**
     * 图片Ids（临时域）
     */
    private String clientPhotoIds;

    public String getProvince() {
        return province;
    }
    public void setProvince(String province) {
        this.province = province;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getCounty() {
        return county;
    }
    public void setCounty(String county) {
        this.county = county;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getPost() {
        return post;
    }
    public void setPost(String post) {
        this.post = post;
    }
    public String getTel() {
        return tel;
    }
    public void setTel(String tel) {
        this.tel = tel;
    }
    public String getFax() {
        return fax;
    }
    public void setFax(String fax) {
        this.fax = fax;
    }
    public String getWeb() {
        return web;
    }
    public void setWeb(String web) {
        this.web = web;
    }
    public String getStaffScale() {
        return staffScale;
    }
    public void setStaffScale(String staffScale) {
        this.staffScale = staffScale;
    }
    public String getAreaScale() {
        return areaScale;
    }
    public void setAreaScale(String areaScale) {
        this.areaScale = areaScale;
    }
    public String getProduceScale() {
        return produceScale;
    }
    public void setProduceScale(String produceScale) {
        this.produceScale = produceScale;
    }
    public String getSalesScale() {
        return salesScale;
    }
    public void setSalesScale(String salesScale) {
        this.salesScale = salesScale;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
    @OneToMany(mappedBy = "client",fetch = FetchType.LAZY)
    @OrderBy("id asc")
    public Set<Linkman> getLinkman() {
        return linkman;
    }
    public void setLinkman(Set<Linkman> linkman) {
        this.linkman = linkman;
    }
    public String getInvoiceTitle() {
        return invoiceTitle;
    }
    public void setInvoiceTitle(String invoiceTitle) {
        this.invoiceTitle = invoiceTitle;
    }
    public String getNickName() {
        return nickName;
    }
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
    @Transient
    public String getClientPhotoIds() {
        return clientPhotoIds;
    }
    public void setClientPhotoIds(String clientPhotoIds) {
        this.clientPhotoIds = clientPhotoIds;
    }

}

