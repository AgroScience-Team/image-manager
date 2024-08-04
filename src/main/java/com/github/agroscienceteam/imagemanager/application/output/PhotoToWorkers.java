package com.github.agroscienceteam.imagemanager.application.output;

import com.github.agroscienceteam.imagemanager.configs.annotations.Command;
import com.github.agroscienceteam.imagemanager.domain.EventSender;
import com.github.agroscienceteam.imagemanager.domain.Photo;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;

@Command
@RequiredArgsConstructor
public class PhotoToWorkers implements EventSender<Photo> {

  private final KafkaTemplate<String, String> kafka;

  @Override
  public void send(String destination, Photo photo) {
    kafka.send(destination, photo.photoExtension(), photo.photoId().toString());
  }

}
