package com.github.agroscienceteam.imagemanager.configs;

import com.github.agroscienceteam.imagemanager.domain.PhotoRepository;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.ListTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Data
@Slf4j
public class TopicsInitializer implements CommandLineRunner {

  @NonNull
  private final AdminClient adminClient;
  @NonNull
  private final PhotoRepository repo;
  @Value("${app.partitions}")
  private int partitions;
  @Value("${app.replicas}")
  private short replicas;

  @Override
  public void run(String... args) {
    createTopicsIfNotExist();
  }

  @SneakyThrows
  private void createTopicsIfNotExist() {
    log.info("CREATING_TOPICS_WITH: {}, {}", partitions, replicas);
    try {
      ListTopicsResult listTopicsResult = adminClient.listTopics();
      Set<String> existingTopics = listTopicsResult.names().get();
      List<String> topics = repo.findAllIndexes();
      List<NewTopic> newTopics = new LinkedList<>();
      for (String topic : topics) {
        if (!existingTopics.contains(topic)) {
          newTopics.add(new NewTopic(topic, partitions, replicas));
        }
      }

      if (!newTopics.isEmpty()) {
        CreateTopicsResult createTopicsResult = adminClient.createTopics(newTopics);
        createTopicsResult.all().get();
        log.info("Created topics: [{}]", newTopics);
      }
    } catch (Exception e) {
      log.info("Failed to create topics [{}]", e.getMessage());
    }
  }

}