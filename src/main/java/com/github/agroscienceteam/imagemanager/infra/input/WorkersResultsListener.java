package com.github.agroscienceteam.imagemanager.infra.input;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.agroscienceteam.imagemanager.domain.EventsListener;
import com.github.agroscienceteam.imagemanager.domain.PhotoRepository;
import com.github.agroscienceteam.imagemanager.domain.ProcessedPhoto;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WorkersResultsListener implements EventsListener<ConsumerRecord<String, String>> {

  private final ObjectMapper mapper;
  private final PhotoRepository repo;

  @KafkaListener(
          groupId = "${app.workers-results.group-id}",
          topics = "${app.workers-results.name}",
          concurrency = "${app.replicas}"
  )
  @Override
  @SneakyThrows
  public void receive(ConsumerRecord<String, String> message) {
    ProcessedPhotoDTO dto = mapper.readValue(message.value(), ProcessedPhotoDTO.class);
    repo.save(new ProcessedPhoto(message.key(), dto.photoId(), dto.result()));
  }

}
