package com.github.agroscienceteam.imagemanager.infra.input;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.agroscienceteam.imagemanager.configs.TopicsConfig;
import com.github.agroscienceteam.imagemanager.configs.annotations.Audit;
import com.github.agroscienceteam.imagemanager.domain.EventsListener;
import com.github.agroscienceteam.imagemanager.domain.photo.PhotoRepository;
import com.github.agroscienceteam.imagemanager.domain.photo.ProcessedPhoto;
import com.github.agroscienceteam.imagemanager.infra.input.dto.ProcessedPhotoDTO;
import lombok.Getter;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Getter
public class WorkersResultsListener implements EventsListener<ConsumerRecord<String, String>> {

  private final ObjectMapper mapper;
  private final PhotoRepository repo;
  private final String topic;
  private final String groupId;

  public WorkersResultsListener(ObjectMapper mapper, PhotoRepository repo, TopicsConfig conf) {
    this.mapper = mapper;
    this.repo = repo;
    topic = conf.getTopic("workers-results");
    groupId = conf.getGroupId("workers-results");
  }

  @KafkaListener(
          groupId = "#{__listener.groupId}",
          topics = "#{__listener.topic}"
  )
  @Override
  @Audit
  public void receive(ConsumerRecord<String, String> message) throws Exception {
    ProcessedPhotoDTO dto = mapper.readValue(message.value(), ProcessedPhotoDTO.class);
    repo.save(new ProcessedPhoto(message.key(), dto.photoId(), dto.result(), dto.extension()));
  }

}
