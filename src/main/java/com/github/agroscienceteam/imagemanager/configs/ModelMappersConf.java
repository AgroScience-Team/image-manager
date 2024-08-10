package com.github.agroscienceteam.imagemanager.configs;

import java.time.LocalDate;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.record.RecordModule;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMappersConf {

  @Bean
  public ModelMapper modelMapper() {
    var mapper = new ModelMapper().registerModule(new RecordModule());
    mapper.addConverter(new StringToLocalDateConverter());
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

}
