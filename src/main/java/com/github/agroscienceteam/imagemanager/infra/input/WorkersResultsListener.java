package com.github.agroscienceteam.imagemanager.infra.input;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.agroscienceteam.imagemanager.configs.annotations.Audit;
import com.github.agroscienceteam.imagemanager.domain.EventsListener;
import com.github.agroscienceteam.imagemanager.domain.photo.PhotoRepository;
import com.github.agroscienceteam.imagemanager.domain.photo.WorkerResult;
import com.github.agroscienceteam.imagemanager.infra.mappers.DbMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Getter
@RequiredArgsConstructor
public class WorkersResultsListener implements EventsListener<String> {

  private final ObjectMapper mapper;
  private final PhotoRepository repo;
  private final DbMapper dbMapper;

  @KafkaListener(topics = "agro.workers.results")
  @Override
  @Audit
  public void receive(@Payload String message) throws Exception {
    WorkerResult workerResult = mapper.readValue(message, WorkerResult.class);

    if (repo.find(workerResult.photoId(), workerResult.type()) != null) {
      throw new RuntimeException("Entity workers_results already exists");
    }

    repo.save(workerResult);
  }

}