package com.honghao.cloud.userapi.test;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;

/**
 * 类加载器加载测试
 *
 * @author chenhonghao
 * @date 2019-08-14 15:07
 */
@Slf4j
public class ClassLoadTest {
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        ClassLoader classLoader = new ClassLoader() {
            @Override
            public Class<?> loadClass(String name) throws ClassNotFoundException {
                String fileName = name.substring(name.lastIndexOf(".")+1)+".class";
                InputStream inputStream = getClass().getResourceAsStream(fileName);
                if (inputStream == null){
                    return super.loadClass(name);
                }
                byte[] b = new byte[0];
                try {
                    b = new byte[inputStream.available()];
                    inputStream.read(b);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return defineClass(name,b,0,b.length);
            }

            @Override
            protected Class<?> findClass(String name) throws ClassNotFoundException {
                return super.findClass(name);
            }
        };


        Object object = classLoader.loadClass("com.honghao.cloud.userapi.test.ClassLoadTest").newInstance();

        log.info("测试结果：{}",object instanceof com.honghao.cloud.userapi.test.ClassLoadTest);
        log.info("result:{}",object.getClass());
    }

}
