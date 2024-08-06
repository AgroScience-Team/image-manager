package com.github.agroscienceteam.imagemanager.configs;

import java.util.Properties;
import lombok.Data;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
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
  public NewTopic newPhotos(
          final @Value("${app.new-photos.name}") String topic,
          final @Value("${app.partitions}") int partitions,
          final @Value("${app.replicas}") short replicas) {
    return new NewTopic(topic, partitions, replicas);
  }

  @Bean
  public NewTopic workersResults(
          final @Value("${app.workers-results.name}") String topic,
          final @Value("${app.partitions}") int partitions,
          final @Value("${app.replicas}") short replicas) {
    return new NewTopic(topic, partitions, replicas);
  }

}
