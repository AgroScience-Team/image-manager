package com.github.agroscienceteam.imagemanager.configs;


import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Configuration
@ConfigurationProperties(prefix = "kafka")
public class TopicsConfig {

  private Map<String, TopicProperties> topics;

  public String getDLQ(String topicName) {
    String res = topics.get(topicName).dlq;
    Objects.requireNonNull(res);
    return res;
  }

  public String getTopic(String topicName) {
    String res = topics.get(topicName).name;
    Objects.requireNonNull(res);
    return res;
  }

  public String getGroupId(String topicName) {
    String res = topics.get(topicName).groupId;
    Objects.requireNonNull(res);
    return res;
  }

  public List<String> getTopics() {
    return topics.values().stream()
            .flatMap(topicProps -> Stream.of(topicProps.getName(), topicProps.getDlq())
                    .filter(Objects::nonNull))
            .toList();
  }

  @Data
  public static class TopicProperties {

    private String name;
    private String groupId;
    private String dlq;

  }

}
