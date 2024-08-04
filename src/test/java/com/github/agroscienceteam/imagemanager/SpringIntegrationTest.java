package com.github.agroscienceteam.imagemanager;

import static io.zonky.test.db.AutoConfigureEmbeddedDatabase.DatabaseProvider.ZONKY;
import static io.zonky.test.db.AutoConfigureEmbeddedDatabase.DatabaseType.POSTGRES;

import io.cucumber.spring.CucumberContextConfiguration;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

@CucumberContextConfiguration
@SpringBootTest(classes = Application.class)
@TestPropertySource(locations = {"classpath:application.yml", "classpath:config/application.test.yml"})
@AutoConfigureEmbeddedDatabase(type = POSTGRES, provider = ZONKY)
@EmbeddedKafka(partitions = 1, brokerProperties = {"listeners=PLAINTEXT://localhost:9393", "port=9393"}, zookeeperPort = 2183)
@EnableAutoConfiguration(exclude= DataSourceAutoConfiguration.class)
@ContextConfiguration(initializers = Initializer.class)
public class SpringIntegrationTest {

}
