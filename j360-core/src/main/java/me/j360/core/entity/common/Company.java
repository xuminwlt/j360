package me.j360.core.entity.common;

import me.j360.core.entity.parent.NameEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;


/**
 * 实体类 - 公司信息
 * ============================================================================
 * 版权所有 2014 。
 * 
 * @author min_xu
 * 
 * @version 0.1 2014-01-20
 * ============================================================================
 */

@Entity
@Table(name="com_company")
public class Company extends NameEntity {
	private static final long serialVersionUID = 8252192070953843585L;

    public Company(){
        super();
    }

    public Company(String name,String adminId){
        super();

        this.setName(name);
        this.setAdminId(adminId);
    }

	public long getCode() {
		return code;
	}

	public void setCode(long code) {
		this.code = code;
	}

	/**
	 * 9位企业码
	 * */
	private long code;
    /**
	 * 账号信息
	 */
	private String adminId;
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
	 * 地址
	 */
	private String address;
	/**
	 * 描述
	 */
	private String description;
	/**
	 * 企业人数
	 */
	private int number;

    public String getSubname() {
        return subname;
    }

    public void setSubname(String subname) {
        this.subname = subname;
    }

    /**
     * 企业简称
     */
    private String subname;
	/**
	 * 区域
	 */
	private String area;
	/**
	 * 是否订阅 1 0不订阅
	 */
	private int subscription;
	/**
	 * 小组内部是否共享1 0（不共享）
	 * **/
	private int departmentShare;
	/**
	 * 是否取消帮助1 0（0不取消）
	 * **/
	private int help;
	/**
	 * 畅销体验公司1
	 * **/
	private int isBestselling;
	/**
	 * 是否开启业务审批功能(0关闭,1开启)
	 * */
	private int ifApprove;
    /**
     * 照片头像
     */
    private FileManage headImage;

	public String getIndustryId() {
		return industryId;
	}

	public void setIndustryId(String industryId) {
		this.industryId = industryId;
	}

	/**
	 * 行业ID
	 * */
	private String industryId;


    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public String getApiToken() {
        return apiToken;
    }

    public void setApiToken(String apiToken) {
        this.apiToken = apiToken;
    }

    public String getApiSecret() {
        return apiSecret;
    }

    public void setApiSecret(String apiSecret) {
        this.apiSecret = apiSecret;
    }

    public int getApiEnable() {
        return apiEnable;
    }

    public void setApiEnable(int apiEnable) {
        this.apiEnable = apiEnable;
    }

	public int getApiWsEnable() {
		return apiWsEnable;
	}

	public void setApiWsEnable(int apiWsEnable) {
		this.apiWsEnable = apiWsEnable;
	}

	/**
     * 企业接口数据
     * */

	private int apiWsEnable;
	private String apiUrl;

    private String apiToken;

    private String apiSecret;

    private int apiEnable;

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

	@Column(length = 5000)
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	@Transient
	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getSubscription() {
		return subscription;
	}

	public void setSubscription(int subscription) {
		this.subscription = subscription;
	}


	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public int getDepartmentShare() {
		return departmentShare;
	}

	public void setDepartmentShare(int departmentShare) {
		this.departmentShare = departmentShare;
	}

	public int getHelp() {
		return help;
	}

	public void setHelp(int help) {
		this.help = help;
	}

	public int getIsBestselling() {
		return isBestselling;
	}

	public void setIsBestselling(int isBestselling) {
		this.isBestselling = isBestselling;
	}

	public int getIfApprove() {
		return ifApprove;
	}

	public void setIfApprove(int ifApprove) {
		this.ifApprove = ifApprove;
	}

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public FileManage getHeadImage() {
        return headImage;
    }

    public void setHeadImage(FileManage headImage) {
        this.headImage = headImage;
    }
}
