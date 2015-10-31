/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package me.j360.data.common.test.mybatis;

import me.j360.data.common.entity.Team;
import me.j360.data.common.repositor.TeamMybatisDao;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import spring.SpringContextTestCase;
import spring.SpringTransactionalTestCase;

import static org.assertj.core.api.Assertions.*;

@DirtiesContext
@ContextConfiguration(locations = { "/applicationContext.xml" })
public class TeamMybatisDaoTest extends SpringContextTestCase {

	@Autowired
	private TeamMybatisDao teamDao;

	@Test
	public void getTeamWithDetail() throws Exception {
		Team team = teamDao.getWithDetail(1L);
		assertThat(team.getName()).isEqualTo("Dolphin");
		assertThat(team.getMaster().getName()).isEqualTo("管理员");
	}
}
