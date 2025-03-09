package com.github.agroscienceteam.imagemanager.infra.audition;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.agroscienceteam.imagemanager.infra.input.NewPhotoListener;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class NewPhotosAuditor extends ListenerAuditor<String> {

  @Override
  public void auditError(JoinPoint jp, Exception e) {
    super.auditError(jp, e);
    producer.send(dlqTopic, getMessage(jp));
  }

  protected NewPhotosAuditor(KafkaTemplate<String, String> producer, ObjectMapper mapper) {
    super(producer, mapper, "agro.new.photos.dlq");
    log.info("Start NewPhotosAuditor with {}, {}", this.auditTopic, this.dlqTopic);
  }

  @Override
  public String getMyKey() {
    return NewPhotoListener.class.getSimpleName();
  }

}
