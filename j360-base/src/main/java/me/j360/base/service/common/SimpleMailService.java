/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package me.j360.base.service.common;

import me.j360.base.bean.Email;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.Date;

/**
 * 纯文本邮件服务类.
 * 
 * @author calvin
 */
public class SimpleMailService {
	private static Logger logger = LoggerFactory.getLogger(SimpleMailService.class);

	private JavaMailSender mailSender;
	private String textTemplate;

	
	/**
	 * 发送纯文本的用户修改通知邮件.
	 */
	public void sendNotificationMail(String userName) {
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setFrom("智慧销售云平台<system@smart-sales.cn>");
		msg.setTo("xuminwlt2008@163.com");
		msg.setSubject("用户注册邮箱验证");

		//对用户id进行加密，点击链接时解密
		String url = "http://www.smart-sales.cn/smartsales/com/email.action?keyId=1";
		// 将用户名与当期日期格式化到邮件内容的字符串模板
		String content = String.format(textTemplate, userName, new Date(),url);
		msg.setText(content);

		/*try {
			mailSender.send(msg);
			if (logger.isInfoEnabled()) {
				logger.info("纯文本邮件已发送至{}", StringUtils.join(msg.getTo(), ","));
			}
		} catch (Exception e) {
			logger.error("发送邮件失败", e);
		}*/
	}

	/*public void sendValidRegisterMail(Admin admin){
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setFrom("智慧销售云平台<system@smart-sales.cn>");
		msg.setTo(admin.getEmail());
		msg.setSubject("用户注册邮箱验证");

		String param = CyptoUtils.encode(CyptoUtils.EMAIL_KEYWORDS, admin.getUsername());
		//对用户id进行加密，点击链接时解密
		String url = "http://www.smart-sales.cn/smartsales/com/email.action?keyId=" + param;
		// 将用户名与当期日期格式化到邮件内容的字符串模板
		String content = String.format(textTemplate, admin.getUsername(), new Date(),url);
		msg.setText(content);

		try {
			mailSender.send(msg);
			if (logger.isInfoEnabled()) {
				logger.info("纯文本邮件已发送至{}", StringUtils.join(msg.getTo(), ","));
			}
		} catch (Exception e) {
			logger.error("发送邮件失败", e);
		}
	}*/
	
	public void sendRegisterAuthCodeMail(String username,String authcode){
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setFrom("智慧销售云平台<system@smart-sales.cn>");
		msg.setTo(username);
		msg.setSubject("用户注册邮箱验证码");

		msg.setText("【智慧销售】"+authcode);
		try {
			mailSender.send(msg);
			/*if (logger.isInfoEnabled()) {
				logger.info("纯文本邮件已发送至{}", StringUtils.join(msg.getTo(), ","));
			}*/
		} catch (Exception e) {
			//logger.error("发送邮件失败", e);
		}
	}


	public void send(Email email){
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setFrom("智慧销售云平台<system@smart-sales.cn>");
		msg.setTo(email.getAccount());
		msg.setSubject(email.getSubject());
		msg.setText(email.getBody());
		try {
			mailSender.send(msg);
			/*if (logger.isInfoEnabled()) {
				logger.info("纯文本邮件已发送至{}", StringUtils.join(msg.getTo(), ","));
			}*/
		} catch (Exception e) {
			//logger.error("发送邮件失败", e);
		}
	}


	/**
	 * Spring的MailSender.
	 */
	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	/**
	 * 邮件内容的字符串模板.
	 */
	public void setTextTemplate(String textTemplate) {
		this.textTemplate = textTemplate;
	}
}
