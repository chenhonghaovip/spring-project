package com.honghao.cloud.orderapi.test.order;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * 场景类
 *
 * @author chenhonghao
 * @date 2020-03-12 16:57
 */
@Slf4j
public class ClientTest {
    @Test
    public void test(){
        Lift lift = new Lift();
        lift.setmCurState(new ClosingState());
        lift.open();
        lift.close();

        lift.run();
        lift.stop();
    }
}
