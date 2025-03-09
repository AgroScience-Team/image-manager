package com.github.agroscienceteam.imagemanager.infra.input;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.agroscienceteam.imagemanager.configs.annotations.Audit;
import com.github.agroscienceteam.imagemanager.domain.EventsListener;
import com.github.agroscienceteam.imagemanager.domain.photo.Photo;
import com.github.agroscienceteam.imagemanager.domain.photo.PhotoDistributor;
import com.github.agroscienceteam.imagemanager.domain.photo.PhotoRepository;
import com.github.agroscienceteam.imagemanager.infra.mappers.DbMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Getter
@Component
@RequiredArgsConstructor
public class NewPhotoListener implements EventsListener<String> {

  private final ObjectMapper mapper;
  private final PhotoRepository photoRepository;
  private final PhotoDistributor distributor;
  private final DbMapper dbMapper;

  @KafkaListener(topics = "agro.new.photos")
  @Override
  @Audit
  public void receive(@Payload String message) throws Exception {
    var photo = mapper.readValue(message, Photo.class);

    photoRepository.save(dbMapper.map(photo));

    distributor.distribute(photo);
  }

}