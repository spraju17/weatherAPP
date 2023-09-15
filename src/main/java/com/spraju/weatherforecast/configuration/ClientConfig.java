package com.spraju.weatherforecast.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;


@Configuration
public class ClientConfig {

    @Autowired
    WeatherApiClientConfig whetherApiClientConfig;

    @Bean("whetherApiClient")
    public RestTemplate getWhetherApiClient(){
        return getClient(whetherApiClientConfig.getReadTimeoutMs(), whetherApiClientConfig.getConnectTimeoutMs());
    }

    private RestTemplate getClient(Long readTimeOuts, Long connectTimeOuts){
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setReadTimeout(readTimeOuts.intValue());
        requestFactory.setConnectTimeout(connectTimeOuts.intValue());

        return new RestTemplate(requestFactory);
    }

}
