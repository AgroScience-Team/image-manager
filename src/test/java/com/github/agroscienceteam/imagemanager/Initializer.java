package com.github.agroscienceteam.imagemanager;

import com.github.agroscienceteam.imagemanager.listeners.TestListener;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.stereotype.Component;

@Component
@Data
@NoArgsConstructor
@ConfigurationProperties(prefix = "test")
public class Initializer implements ApplicationContextInitializer<GenericApplicationContext> {

  List<String> topics = List.of("ndvi", "image-manager.audit.error", "agroscienceteam.audit.messages");

  @Override
  public void initialize(@NonNull GenericApplicationContext context) {
    topics.forEach(i -> registerTestListener(context, i));
  }

  private void registerTestListener(GenericApplicationContext context, String topic) {
    context.registerBean(topic, TestListener.class, () -> new TestListener(topic));
  }

}
