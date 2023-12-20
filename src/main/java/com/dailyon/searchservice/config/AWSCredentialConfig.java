package com.dailyon.searchservice.config;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile(value = {"local", "dev", "prod"})
public class AWSCredentialConfig {

  @Value("${cloud.aws.credentials.MASTER_USERNAME}")
  private String masterUsername;

  @Value("${cloud.aws.credentials.MASTER_PASSWORD}")
  private String masterPassword;

  @Bean
  @Primary
  @Profile(value = {"local", "dev", "prod"})
  public BasicCredentialsProvider amazonAWSCredentialsProvider() {
    BasicCredentialsProvider basicCredentialsProvider = new BasicCredentialsProvider();
    basicCredentialsProvider.setCredentials(
        AuthScope.ANY, new UsernamePasswordCredentials(masterUsername, masterPassword));
    return basicCredentialsProvider;
  }
}
