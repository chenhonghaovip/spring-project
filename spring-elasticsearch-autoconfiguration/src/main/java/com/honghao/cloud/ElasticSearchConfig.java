package com.honghao.cloud;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @author chenhonghao12
 * @version 1.0
 */
@Configuration
@ConditionalOnProperty(name = "spring.elastic.enable", havingValue = "true")
@EnableConfigurationProperties(ElasticSearchProperties.class)
public class ElasticSearchConfig {

    @Resource
    private ElasticSearchProperties elasticSearchProperties;

    @Bean
    @ConditionalOnMissingBean(RestHighLevelClient.class)
    public RestHighLevelClient restHighLevelClient() {
        return new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost(elasticSearchProperties.getHost(), elasticSearchProperties.getPort(), "http")
                ).setHttpClientConfigCallback(httpClientBuilder ->
                        httpClientBuilder.setDefaultCredentialsProvider(init()))
        );
    }

    private CredentialsProvider init() {
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials(elasticSearchProperties.getUser(), elasticSearchProperties.getPassword()));
        return credentialsProvider;
    }

}
