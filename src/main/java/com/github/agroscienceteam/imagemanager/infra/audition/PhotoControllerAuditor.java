package com.github.agroscienceteam.imagemanager.infra.audition;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.agroscienceteam.imagemanager.configs.TopicsConfig;
import com.github.agroscienceteam.imagemanager.domain.audition.UIException;
import com.github.agroscienceteam.imagemanager.infra.input.PhotoController;
import org.aspectj.lang.JoinPoint;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class PhotoControllerAuditor extends BasicAuditor {
  public PhotoControllerAuditor(KafkaTemplate<String, String> producer,
                                ObjectMapper mapper,
                                TopicsConfig conf) {
    super(conf.getTopic("audit"), producer, mapper);
  }

  @Override
  public String getMyKey() {
    return PhotoController.class.getSimpleName();
  }

  @Override
  public void auditError(JoinPoint jp, Exception e) {
    super.auditError(jp, e);
    throw new UIException(e);
  }
}
