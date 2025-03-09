package com.github.agroscienceteam.imagemanager.infra.audition;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.agroscienceteam.imagemanager.infra.input.WorkersResultsListener;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class WorkersResultsAuditor extends ListenerAuditor<String> {

  protected WorkersResultsAuditor(KafkaTemplate<String, String> producer, ObjectMapper mapper) {
    super(producer, mapper, "agro.workers.results.dlq");
    log.info("Start NewPhotosAuditor with {}, {}", this.auditTopic, this.dlqTopic);
  }

  @Override
  public void auditError(JoinPoint jp, Exception e) {
    super.auditError(jp, e);
    producer.send(dlqTopic, getMessage(jp));
  }

  @Override
  public String getMyKey() {
    return WorkersResultsListener.class.getSimpleName();
  }

}