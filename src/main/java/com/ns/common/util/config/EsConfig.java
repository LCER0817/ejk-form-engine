package com.ns.common.util.config;

import com.google.gson.GsonBuilder;
import com.ns.common.util.constant.CharConstant;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

/**
 * Created by xuezhucao on 2017/7/20.
 */
@Configuration
public class EsConfig {
    @Value("${spring.data.elasticsearch.service-url}")
    private String serviceUrl;

    @Bean
    public HttpClientConfig.Builder httpClientConfigBuilder() {
        List<String> serverList = Arrays.asList(StringUtils.split(serviceUrl, CharConstant.DOT));
        return new HttpClientConfig.Builder(serverList);
    }

    @Bean
    public JestClient getJestClient() {
        JestClientFactory factory = new JestClientFactory();
        factory.setHttpClientConfig(httpClientConfigBuilder()
                .multiThreaded(true)
                .gson(new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create())
                .build());
        return factory.getObject();
    }
}
