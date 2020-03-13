package com.honghao.cloud.orderapi.test.order;

/**
 * @author chenhonghao
 * @date 2020-03-12 16:53
 */
public class StoppingState extends LiftState {

    @Override
    public void open() {
        lift.setmCurState(Lift.openningState);
        lift.open();
    }

    @Override
    public void close() {
        System.out.println("电梯关闭中...");
    }

    @Override
    public void run() {
        lift.setmCurState(Lift.runningState);
        lift.run();
    }

    @Override
    public void stop() {
        System.out.println("电梯停止运行...");
    }
}
