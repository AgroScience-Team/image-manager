package com.github.agroscienceteam.imagemanager.configs;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ObjectMapperInitializer implements CommandLineRunner {

  private final ObjectMapper mapper;

  @Override
  public void run(String... args) {
    SimpleModule module = new SimpleModule();
    module.addSerializer(ConsumerRecord.class, new JsonSerializer<>() {
      @Override
      public void serialize(ConsumerRecord rec,
                            JsonGenerator gen,
                            SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeStringField("key", rec.key().toString());
        gen.writeStringField("value", rec.value().toString());
        gen.writeEndObject();
      }
    });
    mapper.registerModule(module);
  }

}
