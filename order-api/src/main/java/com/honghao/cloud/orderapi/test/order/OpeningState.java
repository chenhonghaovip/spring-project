package com.honghao.cloud.orderapi.test.order;

/**
 * @author chenhonghao
 * @date 2020-03-12 16:50
 */
public class OpeningState extends LiftState{

    @Override
    public void open() {
        System.out.println("执行开门动作");
    }

    @Override
    public void close() {
        lift.setmCurState(Lift.closingState);
        lift.close();
    }

    @Override
    public void run() {
        System.out.println("不执行运行动作");
    }

    @Override
    public void stop() {
        System.out.println("不执行停止动作");
    }
}
