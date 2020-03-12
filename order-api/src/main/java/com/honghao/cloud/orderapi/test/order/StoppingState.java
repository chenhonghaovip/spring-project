package com.honghao.cloud.orderapi.test.order;

/**
 * @author chenhonghao
 * @date 2020-03-12 16:53
 */
public class StoppingState extends LiftState {
    /**
     * 通过构造函数引入电梯的实例化对象
     *
     * @param lift
     */
    public StoppingState(Lift lift) {
        super(lift);
    }

    @Override
    public void open() {
        // 1、开门状态
        this.mLift.setMCurState(mLift.getOpenningState());
        // 2、执行开门动作
        this.mLift.open();
    }

    @Override
    public void close() {

    }

    @Override
    public void run() {
        // 运行动作
        // 1、运行状态
        this.mLift.setMCurState(mLift.getRunningState());
        // 2、运行动作
        this.mLift.run();
    }

    @Override
    public void stop() {
        System.out.println("电梯停止运行...");
    }
}
