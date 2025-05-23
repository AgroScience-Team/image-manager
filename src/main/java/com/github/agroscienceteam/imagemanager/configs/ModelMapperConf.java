package com.github.agroscienceteam.imagemanager.configs;

import static java.time.OffsetDateTime.parse;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.record.RecordModule;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConf {

  @Bean
  public ModelMapper modelMapper() {
    var mapper = new ModelMapper().registerModule(new RecordModule());

    mapper.addConverter(new StringToLocalDateConverter());
    mapper.addConverter(new StringToOffsetDateTime());

    return mapper;
  }

  private static class StringToLocalDateConverter implements Converter<String, LocalDate> {

    @Override
    public LocalDate convert(MappingContext<String, LocalDate> context) {
      String source = context.getSource();
      if (source == null) {
        return null;
      }
      return LocalDate.parse(source);
    }

  }

  private static class StringToOffsetDateTime implements Converter<String, OffsetDateTime> {

    @Override
    public OffsetDateTime convert(MappingContext<String, OffsetDateTime> context) {
      String source = context.getSource();
      if (source == null) {
        return null;
      }
      return parse(source);
    }

  }

}
