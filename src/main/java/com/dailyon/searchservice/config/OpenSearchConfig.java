package com.dailyon.searchservice.config;

import lombok.RequiredArgsConstructor;
import org.apache.http.HttpHost;
import org.apache.http.client.CredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile(value = {"dev", "prod"})
@RequiredArgsConstructor
public class OpenSearchConfig implements DisposableBean {

  @Value("${cloud.aws.opensearch.hostname}")
  private String hostname;

  @Value("${cloud.aws.opensearch.port}")
  private int port;

  @Value("${cloud.aws.opensearch.scheme}")
  private String scheme;

  private final CredentialsProvider credentialsProvider;

  @Bean
  @Profile(value = {"dev", "prod"})
  public RestHighLevelClient client() {
    RestClientBuilder restClientBuilder =
        RestClient.builder(new HttpHost(hostname, port, scheme))
            .setHttpClientConfigCallback(
                httpClientBuilder ->
                    httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider));

    return new RestHighLevelClient(restClientBuilder);
  }

  @Override
  public void destroy() throws Exception {
    client().close();
  }
}
