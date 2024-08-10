package com.github.agroscienceteam.imagemanager.steps;


import com.github.agroscienceteam.imagemanager.listeners.TestListener;
import io.cucumber.java.en.Then;
import java.util.List;
import java.util.Objects;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

@Setter
public class AuditErrorSteps {

  @Value("${topics.audit-error.name}")
  private String topic;
  private TestListener auditListener;

  @Then("Audit error topic receive message")
  public void receiveMessage(String message) {

  }

  @Autowired
  public void setAuditListener(List<TestListener> listeners) {
    auditListener = listeners.stream().distinct().filter(l -> Objects.equals(l.getTopic(), topic)).findFirst().get();
  }

}
