package com.honghao.cloud.userapi.test;

import lombok.extern.slf4j.Slf4j;

/**
 * 编译时与运行时类型测试
 *
 * @author chenhonghao
 * @date 2019-08-14 17:22
 */
@Slf4j
public class RunTimeTest {
    static abstract class AbstractPeople {

    }
    static class Man extends AbstractPeople {
        public void sayBye(){
            log.info("man say goodbye");
        }
    }
    static class Woman extends AbstractPeople {
        public void sayBye(){
            log.info("woman say goodbye");
        }
    }
    public void sayHello(AbstractPeople people){
        log.info("people say hello");
    }
    public void sayHello(Man man){
        log.info("man say hello");
    }
    public void sayHello(Woman woman){
        log.info("woman say hello");
    }
    public static void main(String[] args) {
        AbstractPeople man = new Man();
        Man man1 = new Man();
        RunTimeTest runTimeTest = new RunTimeTest();
        runTimeTest.sayHello(man);
        runTimeTest.sayHello(man1);

        Woman woman = new Woman();
        woman.sayBye();
    }
}
