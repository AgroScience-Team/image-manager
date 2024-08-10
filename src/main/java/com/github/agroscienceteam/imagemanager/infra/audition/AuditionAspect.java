package com.github.agroscienceteam.imagemanager.infra.audition;

import static java.util.stream.Collectors.toMap;

import com.github.agroscienceteam.imagemanager.domain.audition.Auditor;
import jakarta.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@Setter
public class AuditionAspect {

  private final Map<String, Auditor> auditors;

  public AuditionAspect(List<Auditor> auditors) {
    this.auditors = auditors.stream().collect(toMap(Auditor::getMyKey, ignored -> ignored));
  }

  @Pointcut(value = "@annotation(com.github.agroscienceteam.imagemanager.configs.annotations.Audit)")
  public void callAtAudition() {

  }

  @Around("callAtAudition()")
  public Object aroundCallAt(ProceedingJoinPoint pjp) throws Throwable {
    var auditor = auditors.get(pjp.getSignature().getDeclaringType().getSimpleName());

    Object result = null;
    try {
      auditor.auditInfoBefore(pjp);
      result = pjp.proceed();
      if (result != null) {
        auditor.auditInfoAfter(pjp, result);
      } else {
        auditor.auditInfoAfter(pjp);
      }
    } catch (Exception e) {
      auditor.auditError(pjp, e);
    }

    return result;
  }

  @PostConstruct
  public void log() {
    log.info("Start audition aspect with auditors size {}", auditors.size());
  }

}
