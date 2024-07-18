package com.honghao.cloud;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author chenhonghao12
 * @version 1.0
 */
@ConfigurationProperties(prefix = "spring.eslastic")
public class ElasticSearchProperties {
    private String host;
    private String user;
    private String password;

    private int port;

    private int timeOut = 0;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(int timeOut) {
        this.timeOut = timeOut;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
