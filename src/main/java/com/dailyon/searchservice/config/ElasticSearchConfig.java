package com.dailyon.searchservice.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile(value = {"local", "test"})
public class ElasticSearchConfig implements DisposableBean {

  @Value("${elasticsearch.hostname}")
  private String hostname;

  @Value("${elasticsearch.port}")
  private int port;

  @Value("${elasticsearch.scheme}")
  private String scheme;

  @Bean
  @Profile(value = {"local", "test"})
  public RestHighLevelClient client() {
    RestClientBuilder restClientBuilder = RestClient.builder(new HttpHost(hostname, port, scheme));
    return new RestHighLevelClient(restClientBuilder);
  }

  @Override
  public void destroy() throws Exception {
    client().close();
  }
}
