package com.honghao.cloud.orderapi.test.order;

/**
 * @author chenhonghao
 * @date 2020-03-12 16:52
 */
public class RunningState extends LiftState{

    @Override
    public void open() {
        System.out.println("不执行开门动作");
    }

    @Override
    public void close() {
        System.out.println("不执行关门动作");
    }

    @Override
    public void run() {
        System.out.println("电梯上下运行中...");
    }

    @Override
    public void stop() {
        lift.setmCurState(Lift.stoppingState);
        lift.stop();
    }
}
