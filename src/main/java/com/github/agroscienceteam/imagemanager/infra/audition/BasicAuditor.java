package com.github.agroscienceteam.imagemanager.infra.audition;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.agroscienceteam.imagemanager.domain.audition.AuditEntity;
import com.github.agroscienceteam.imagemanager.domain.audition.AuditEntityWithResult;
import com.github.agroscienceteam.imagemanager.domain.audition.Auditor;
import com.github.agroscienceteam.imagemanager.domain.audition.ErrorAudit;
import com.github.agroscienceteam.imagemanager.domain.audition.FatalAudit;
import com.github.agroscienceteam.imagemanager.domain.audition.FatalException;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.springframework.kafka.core.KafkaTemplate;

@RequiredArgsConstructor
@Slf4j
public abstract class BasicAuditor implements Auditor {

  protected final String auditTopic;
  protected final KafkaTemplate<String, String> producer;
  protected final ObjectMapper mapper;

  @Override
  public void auditInfoBefore(JoinPoint jp) {
    log.info("Start method {} in class {} with params {}",
            jp.getSignature().getName(),
            jp.getSignature().getDeclaringType().getSimpleName(),
            Arrays.toString(jp.getArgs()));
  }

  @Override
  public void auditInfoAfter(JoinPoint jp) {
    log.info("End method {} in class {} with params {}",
            jp.getSignature().getName(),
            jp.getSignature().getDeclaringType().getSimpleName(),
            Arrays.toString(jp.getArgs()));
    var audit = new AuditEntity(SYSTEM_NAME,
            jp.getSignature().getDeclaringType(),
            jp.getSignature().getName(),
            jp.getArgs());
    producer.send(auditTopic, SUCCESS_KEY, map(audit));
  }

  @Override
  public void auditInfoAfter(JoinPoint jp, Object result) {
    log.info("End method {} in class {} with params {} and result {}",
            jp.getSignature().getName(),
            jp.getSignature().getDeclaringType().getName(),
            Arrays.toString(jp.getArgs()),
            result.toString());
    var audit = new AuditEntityWithResult(SYSTEM_NAME,
            jp.getSignature().getDeclaringType(),
            jp.getSignature().getName(),
            jp.getArgs(),
            map(result));
    producer.send(auditTopic, SUCCESS_KEY, map(audit));
  }

  @Override
  public void auditError(JoinPoint jp, Exception e) {
    commonAuditError(jp, e);
  }

  protected void commonAuditError(JoinPoint jp, Exception e) {
    log.error("End method {} in class {} with params {} with error {}, {}",
            jp.getSignature().getName(),
            jp.getSignature().getDeclaringType(),
            Arrays.toString(jp.getArgs()),
            e.getClass().getSimpleName(),
            e.getMessage()
    );
    var audit = new ErrorAudit(SYSTEM_NAME,
            jp.getSignature().getDeclaringType(),
            jp.getSignature().getName(),
            jp.getArgs(),
            e.getClass(),
            e.getMessage());
    producer.send(auditTopic, ERROR_KEY, map(audit));
  }

  protected String map(Object obj) {
    try {
      return mapper.writeValueAsString(obj);
    } catch (Exception e) {
      producer.send(auditTopic, ERROR_KEY,
              new FatalAudit(SYSTEM_NAME, e.getClass(), e.getMessage()).toString());
      throw new FatalException(e);
    }
  }

}
