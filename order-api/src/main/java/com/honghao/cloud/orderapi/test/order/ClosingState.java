package com.honghao.cloud.orderapi.test.order;

/**
 * @author chenhonghao
 * @date 2020-03-12 16:52
 */
public class ClosingState extends LiftState{
    @Override
    public void open() {
        super.lift.setmCurState(Lift.openningState);
        super.lift.open();
    }

    @Override
    public void close() {
        System.out.println("执行关门动作");
    }

    @Override
    public void run() {
        lift.setmCurState(Lift.runningState);
        lift.run();
    }

    @Override
    public void stop() {
        lift.setmCurState(Lift.stoppingState);
        lift.stop();
    }
}
