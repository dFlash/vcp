package online.study.vcp.config;

import java.util.List;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Configuration web support
 * 
 * @author DMaliavin
 * @since 0.0.1
 */
@Configuration
@EnableWebMvc
@EnableSpringDataWebSupport
@ComponentScan({ "online.study.vcp.controller" })
public class MVCConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("/static/");
        registry.addResourceHandler("/media/**").addResourceLocations("/media/");
        registry.addResourceHandler("/favicon.ico").addResourceLocations("/favicon.ico");
        registry.addResourceHandler("/index.html").addResourceLocations("/index.html");
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> messageConverters) {
        messageConverters.add(new MappingJackson2HttpMessageConverter());
    }

}
