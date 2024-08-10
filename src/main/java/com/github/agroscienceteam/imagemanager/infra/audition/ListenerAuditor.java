package com.github.agroscienceteam.imagemanager.infra.audition;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.JoinPoint;
import org.springframework.kafka.core.KafkaTemplate;

public abstract class ListenerAuditor<T> extends BasicAuditor {

  protected final String dlqTopic;

  public T getMessage(JoinPoint jp) {
    return (T) jp.getArgs()[0];
  }

  protected ListenerAuditor(String auditTopic, KafkaTemplate<String, String> producer, ObjectMapper mapper,
                            String dlqTopic) {
    super(auditTopic, producer, mapper);
    this.dlqTopic = dlqTopic;
  }

}
