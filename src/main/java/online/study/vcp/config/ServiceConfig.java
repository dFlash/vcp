package online.study.vcp.config;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

/**
 * 
 * @author DMaliavin
 * @since 0.0.1
 */
@Configuration
@ComponentScan({ "online.study.vcp.service.impl" })
public class ServiceConfig {

    @Bean
    public static PropertySourcesPlaceholderConfigurer placeholderConfigurer() throws IOException {
        PropertySourcesPlaceholderConfigurer conf = new PropertySourcesPlaceholderConfigurer();
        conf.setLocations(new Resource[] { new ClassPathResource("application.properties") });
        return conf;
    }
}
