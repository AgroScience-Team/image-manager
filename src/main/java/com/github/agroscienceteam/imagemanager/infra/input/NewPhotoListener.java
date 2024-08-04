package com.github.agroscienceteam.imagemanager.infra.input;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.agroscienceteam.imagemanager.domain.EventsListener;
import com.github.agroscienceteam.imagemanager.domain.Photo;
import com.github.agroscienceteam.imagemanager.domain.PhotoDistributor;
import com.github.agroscienceteam.imagemanager.domain.PhotoSaver;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NewPhotoListener implements EventsListener {

  @NonNull
  private final ObjectMapper mapper;
  @NonNull
  private final PhotoSaver saver;
  @NonNull
  private final PhotoDistributor distributor;

  @KafkaListener(
          groupId = "${app.new-photos.group-id}",
          topics = "${app.new-photos.name}",
          concurrency = "${app.replicas}"
  )
  @SneakyThrows
  @Override
  public void receive(
          @Payload String message
  ) {
    var photo = mapper.readValue(message, Photo.class);
    saver.save(photo);
    distributor.distribute(photo);
  }

}
