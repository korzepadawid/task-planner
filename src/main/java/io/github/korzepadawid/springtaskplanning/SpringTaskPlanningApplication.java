package io.github.korzepadawid.springtaskplanning;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class SpringTaskPlanningApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringTaskPlanningApplication.class, args);
  }
}
