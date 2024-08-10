package com.github.agroscienceteam.imagemanager.configs;

import java.util.Properties;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.util.backoff.FixedBackOff;

@Configuration
@Data
@Slf4j
public class KafkaConf {

  @Value("${spring.kafka.bootstrap-servers}")
  private String bootstrapServers;
  @Value("${spring.kafka.security.protocol}")
  private String securityProtocol;
  @Value("${spring.kafka.properties.sasl.mechanism}")
  private String saslMechanism;
  @Value("${spring.kafka.jaas.options.username}")
  private String username;
  @Value("${spring.kafka.jaas.options.password}")
  private String password;

  @Bean
  public AdminClient adminClient() {
    Properties props = new Properties();
    props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
    props.put(AdminClientConfig.SECURITY_PROTOCOL_CONFIG, securityProtocol);
    props.put("sasl.mechanism", saslMechanism);
    props.put("sasl.jaas.config", "org.apache.kafka.common.security.plain.PlainLoginModule"
            + " required username=\"" + username + "\" password=\"" + password + "\";");

    return AdminClient.create(props);
  }

  @Bean
  public DefaultErrorHandler defaultErrorHandler() {
    return new DefaultErrorHandler(new FixedBackOff(5000L, 1L));
  }

}
