package com.github.agroscienceteam.imagemanager.application.output;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.agroscienceteam.imagemanager.configs.annotations.Command;
import com.github.agroscienceteam.imagemanager.domain.EventSender;
import com.github.agroscienceteam.imagemanager.domain.photo.WorkerRequest;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.kafka.core.KafkaTemplate;

@Command
@RequiredArgsConstructor
public class PhotoToWorkers implements EventSender<WorkerRequest> {

  private final KafkaTemplate<String, String> kafka;
  private final ObjectMapper objectMapper;

  @Override
  public void send(String vegetationIndex, WorkerRequest workerRequest) {
    kafka.send("agro.workers." + vegetationIndex, toJson(workerRequest));
  }

  @SneakyThrows
  private String toJson(WorkerRequest workerRequest) {
    return objectMapper.writeValueAsString(workerRequest);
  }

}