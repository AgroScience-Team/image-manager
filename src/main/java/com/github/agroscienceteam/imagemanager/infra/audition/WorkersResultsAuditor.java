package com.github.agroscienceteam.imagemanager.infra.audition;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.agroscienceteam.imagemanager.configs.TopicsConfig;
import com.github.agroscienceteam.imagemanager.infra.input.WorkersResultsListener;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.aspectj.lang.JoinPoint;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class WorkersResultsAuditor extends ListenerAuditor<ConsumerRecord<String, String>> {

  protected WorkersResultsAuditor(KafkaTemplate<String, String> producer,
                                  ObjectMapper mapper,
                                  TopicsConfig conf) {
    super(conf.getTopic("audit"), producer, mapper, conf.getDLQ("workers-results"));
    log.info("Start NewPhotosAuditor with {}, {}", this.auditTopic, this.dlqTopic);
  }

  @Override
  public void auditError(JoinPoint jp, Exception e) {
    super.auditError(jp, e);
    var rec = getMessage(jp);
    producer.send(dlqTopic, rec.key(), rec.value());
  }

  @Override
  public String getMyKey() {
    return WorkersResultsListener.class.getSimpleName();
  }

}
