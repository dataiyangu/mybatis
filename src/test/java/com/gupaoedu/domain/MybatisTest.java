package com.gupaoedu.domain;

import jdk.nashorn.internal.ir.annotations.Ignore;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 * @description:
 * @author: Leesin Dong
 * @date: Created in 2020/3/9 15:51
 * @version:
 * @modified By:
 */
public class MybatisTest {

    /**
     * 使用MyBatis API方式
     * @throws IOException
     */
    @Ignore
    public void testStatement() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        SqlSession session = sqlSessionFactory.openSession();
        try {
            Blog blog = (Blog) session.selectOne("com.gupaoedu.mapper.BlogMapper.selectBlogById", 1);
            System.out.println(blog);
        } finally {
            session.close();
        }
    }

    public void testSelect() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        //解析配置文件
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        SqlSession session = sqlSessionFactory.openSession(); // ExecutorType.BATCH
        try {
            //获取Mapper对象
            //BlogMapper为了解决statement id硬编码的问题
            BlogMapper mapper = session.getMapper(BlogMapper.class);
            //sql执行
            //不论调用哪个方法都会走到invoke方法，因为是代理对象
            //这里直接点肯定是点不了的
            //直接进入MapperProxy的invoke方法
            Blog blog = mapper.selectBlogById(1);
            System.out.println(blog);
        } finally {
            session.close();
        }
    }
}
