package com.honghao.cloud.userapi.test;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;

/**
 * mybatis测试
 *
 * @author chenhonghao
 * @date 2019-12-27 09:59
 */
@Slf4j
public class SqlSessionFaotoryTest {
    public static void main(String[] args) {
        String resource = "";
        Reader reader;
        try {
            reader = Resources.getResourceAsReader(resource);
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            SqlSession sqlSession = sqlSessionFactory.openSession();
            sqlSession.selectList("select * from waybill_bc_list");

        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
