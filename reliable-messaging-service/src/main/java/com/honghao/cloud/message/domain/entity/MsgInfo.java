package com.honghao.cloud.message.domain.entity;

import java.util.Date;

/**
 * @author chenhonghao
 */
public class MsgInfo {
    private Long msgId;

    private String content;

    private Integer status;

    private String appId;

    private String url;

    /**
     * 消费者服务名
     */
    private String consumerAppId;
    /**
     * 消费者回调查询路径
     */
    private String consumerUrl;

    private Integer retryTime;

    private String topic;

    private Integer delay;

    private Date createTime;

    public String getConsumerAppId() {
        return consumerAppId;
    }

    public void setConsumerAppId(String consumerAppId) {
        this.consumerAppId = consumerAppId;
    }

    public String getConsumerUrl() {
        return consumerUrl;
    }

    public void setConsumerUrl(String consumerUrl) {
        this.consumerUrl = consumerUrl;
    }

    public Long getMsgId() {
        return msgId;
    }

    public void setMsgId(Long msgId) {
        this.msgId = msgId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId == null ? null : appId.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public Integer getRetryTime() {
        return retryTime;
    }

    public void setRetryTime(Integer retryTime) {
        this.retryTime = retryTime;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic == null ? null : topic.trim();
    }

    public Integer getDelay() {
        return delay;
    }

    public void setDelay(Integer delay) {
        this.delay = delay;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}