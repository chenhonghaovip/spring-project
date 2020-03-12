package com.honghao.cloud.orderapi.test.order;

/**
 * @author chenhonghao
 * @date 2020-03-12 16:52
 */
public class RunningState extends LiftState{
    /**
     * 通过构造函数引入电梯的实例化对象
     *
     * @param lift
     */
    public RunningState(Lift lift) {
        super(lift);
    }

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
        // 1、转化为停止状态
        this.mLift.setMCurState(mLift.getStoppingState());
        // 2、停止动作
        this.mLift.stop();
    }
}
