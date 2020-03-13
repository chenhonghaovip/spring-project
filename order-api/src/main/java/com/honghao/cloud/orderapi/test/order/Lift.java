package com.honghao.cloud.orderapi.test.order;

/**
 * 电梯类
 *
 * @author chenhonghao
 * @date 2020-03-12 16:49
 */
public class Lift {
    /**
     * 定义出电梯的所有状态
     */
    public final static LiftState openningState = new OpeningState();
    public final static LiftState closingState = new ClosingState();
    public final static LiftState runningState = new RunningState();
    public final static LiftState stoppingState = new StoppingState();

    /**
     * 定义当前电梯状态
     */
    private LiftState mCurState;

    public void setmCurState(LiftState mCurState) {
        this.mCurState = mCurState;
        this.mCurState.setLift(this);
    }

    /**
     * 执行开门动作
     */
    public void open() {
        mCurState.open();
    }

    /**
     * 执行关门动作
     */
    public void close() {
        mCurState.close();
    }

    /**
     * 执行运行动作
     */
    public void run() {
        mCurState.run();
    }

    /**
     * 执行停止动作
     */
    public void stop() {
        mCurState.stop();
    }
}
