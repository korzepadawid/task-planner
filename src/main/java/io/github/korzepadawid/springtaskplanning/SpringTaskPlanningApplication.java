package io.github.korzepadawid.springtaskplanning;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
@ConfigurationPropertiesScan
public class SpringTaskPlanningApplication extends SpringBootServletInitializer {

  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    return application.sources(SpringTaskPlanningApplication.class);
  }

  public static void main(String[] args) {
    SpringApplication.run(SpringTaskPlanningApplication.class, args);
  }
}
