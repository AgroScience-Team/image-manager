package com.github.agroscienceteam.imagemanager.infra.input;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.agroscienceteam.imagemanager.configs.TopicsConfig;
import com.github.agroscienceteam.imagemanager.configs.annotations.Audit;
import com.github.agroscienceteam.imagemanager.domain.EventsListener;
import com.github.agroscienceteam.imagemanager.domain.photo.Photo;
import com.github.agroscienceteam.imagemanager.domain.photo.PhotoDistributor;
import com.github.agroscienceteam.imagemanager.domain.photo.PhotoSaver;
import lombok.Getter;
import lombok.NonNull;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Getter
public class NewPhotoListener implements EventsListener<String> {

  @NonNull
  private final ObjectMapper mapper;
  @NonNull
  private final PhotoSaver saver;
  @NonNull
  private final PhotoDistributor distributor;

  private final String topic;
  private final String groupId;

  public NewPhotoListener(@NonNull ObjectMapper mapper,
                          @NonNull PhotoSaver saver,
                          @NonNull PhotoDistributor distributor,
                          @NonNull TopicsConfig conf) {
    this.mapper = mapper;
    this.saver = saver;
    this.distributor = distributor;
    topic = conf.getTopic("new-photos");
    groupId = conf.getGroupId("new-photos");
  }

  @KafkaListener(
          groupId = "#{__listener.groupId}",
          topics = "#{__listener.topic}"
  )
  @Override
  @Audit
  public void receive(
          @Payload String message
  ) throws Exception {
    var photo = mapper.readValue(message, Photo.class);
    saver.save(photo);
    distributor.distribute(photo);
  }

}
