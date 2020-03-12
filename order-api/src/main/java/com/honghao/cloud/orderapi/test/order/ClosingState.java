package com.honghao.cloud.orderapi.test.order;

/**
 * @author chenhonghao
 * @date 2020-03-12 16:52
 */
public class ClosingState extends LiftState{
    /**
     * 通过构造函数引入电梯的实例化对象
     *
     * @param lift
     */
    public ClosingState(Lift lift) {
        super(lift);
    }

    @Override
    public void open() {
        // 1、变化为开门状态
        this.mLift.setMCurState(mLift.getOpenningState());
        // 2、开门
        this.mLift.open();
    }

    @Override
    public void close() {
        System.out.println("执行关门动作");
    }

    @Override
    public void run() {
        this.mLift.setMCurState(mLift.getRunningState());
        this.mLift.run();
    }

    @Override
    public void stop() {
        this.mLift.setMCurState(mLift.getStoppingState());
        this.mLift.stop();
    }
}
