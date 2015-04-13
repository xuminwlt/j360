package me.j360.base.bean;

import java.io.Serializable;

/**
 * Created with us2 -> com.fz.us.base.bean.
 * User: min_xu
 * Date: 2014-11-25
 * Time: 15:49
 * 说明：
 */
public class Email implements Serializable {

    private static final long serialVersionUID = -2755329278196422648L;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }



    private String body;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    private String account;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    private String subject;


}
