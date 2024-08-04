package com.github.agroscienceteam.imagemanager.listeners;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

import com.github.agroscienceteam.imagemanager.models.KafkaMessage;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import lombok.Getter;
import lombok.SneakyThrows;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;

@Getter
public class TestListener {

  private final BlockingDeque<KafkaMessage> messages = new LinkedBlockingDeque<>(1024);

  private final String topic;
  private final String groupId;

  public TestListener(String topic) {
    this.topic = topic;
    this.groupId = topic;
  }

  @KafkaListener(
          topics = "#{__listener.topic}",
          groupId = "#{__listener.groupId}"
  )
  public void receive(ConsumerRecord<String, String> message) {
    messages.add(new KafkaMessage(message.key(), message.value()));
  }

  @SneakyThrows
  public KafkaMessage getMessage(long timeoutMillis) {
    return messages.poll(timeoutMillis, MILLISECONDS);
  }

  public void clear() {
    messages.clear();
  }

}
