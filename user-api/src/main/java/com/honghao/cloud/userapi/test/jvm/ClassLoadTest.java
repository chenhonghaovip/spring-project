package com.honghao.cloud.userapi.test.jvm;

import java.io.InputStream;

/**
 * @author chenhonghao
 * @date 2020-08-13 20:21
 */
public class ClassLoadTest {
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
//        ClassLoader classLoader = ClassLoadTest.class.getClassLoader();
//        System.out.println(classLoader);
//
//        System.out.println(classLoader.getParent());
//        System.out.println(classLoader.getParent().getParent());

        ClassLoader classLoader1 = new ClassLoader() {
            @Override
            public Class<?> loadClass(String name) throws ClassNotFoundException {
                try {
                    String fileName = name.substring(name.lastIndexOf(".")+1)+".class";
                    InputStream in = getClass().getResourceAsStream(fileName);
                    if (in == null){
                        return super.loadClass(name);
                    }
                    byte[] bytes = new byte[in.available()];
                    in.read(bytes);
                    return defineClass(name,bytes,0,bytes.length);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return super.loadClass(name);
            }
        };
        Object o = classLoader1.loadClass("com.honghao.cloud.userapi.test.jvm.ClassLoadTest").newInstance();
        System.out.println(o.getClass());
        System.out.println(ClassLoadTest.class.getClassLoader());
        System.out.println(o instanceof ClassLoadTest);


    }
}
