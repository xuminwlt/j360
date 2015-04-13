/**
 * Copyright (c) 2005-2012 springside.org.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package me.j360.base.test;

import me.j360.base.util.mapper.JsonMapper;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import static org.assertj.core.api.Assertions.*;

/**
 * Spring的支持数据库访问, 事务控制和依赖注入的JUnit4 集成测试基类.
 * 相比Spring原基类名字更短并保存了dataSource变量.
 * <p/>
 * 子类需要定义applicationContext文件的位置, 如:
 * @author calvin
 */

@ActiveProfiles("development")
public abstract class SpringTransactionalTestCase extends AbstractTransactionalJUnit4SpringContextTests {
    protected static JsonMapper binder = JsonMapper.nonEmptyMapper();

    /*protected DataSource dataSource;
    @Override
    @Autowired
    public void setDataSource(DataSource dataSource) {
        super.setDataSource(dataSource);
        this.dataSource = dataSource;
    }*/
}
