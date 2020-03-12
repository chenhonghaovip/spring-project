package com.honghao.cloud.orderapi.test.order;

/**
 * @author chenhonghao
 * @date 2020-03-12 16:50
 */
public class OpeningState extends LiftState{
    /**
     * 通过构造函数引入电梯的实例化对象
     *
     * @param lift
     */
    public OpeningState(Lift lift) {
        super(lift);
    }

    @Override
    public void open() {
        System.out.println("执行开门动作");
    }

    @Override
    public void close() {
        // 1、转化为关门状态
        mLift.setMCurState(mLift.getClosingState());
        // 2、关门
        mLift.close();
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
