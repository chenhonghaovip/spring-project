package com.honghao.cloud.middle;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

public class MqProduct {

    public void test(){
        DefaultMQProducer defaultMQProducer = new DefaultMQProducer();

        try {
            defaultMQProducer.start();
            defaultMQProducer.send(new Message());
        } catch (MQClientException | RemotingException | MQBrokerException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
