/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package me.j360.data.common.test.mybatis;

import me.j360.data.common.entity.User;
import me.j360.data.common.repositor.UserMybatisDao;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import spring.SpringTransactionalTestCase;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

import static org.assertj.core.api.Assertions.*;

@DirtiesContext
@ContextConfiguration(locations = { "/applicationContext.xml" })
public class UserMybatisDaoTest extends SpringTransactionalTestCase {

	@Autowired
	private UserMybatisDao userDao;

	@Test
	public void getUser() throws Exception {
		User user = userDao.get(1L);
		assertThat(user).as("User not found").isNotNull();
		assertThat(user.getLoginName()).isEqualTo("admin");
	}


}
