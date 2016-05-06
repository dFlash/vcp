package com.maliavin.vcp.config;

import java.util.EnumSet;

import javax.servlet.Filter;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.SessionTrackingMode;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.filter.RequestContextFilter;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * Servlet application initializer that uses instead of web.xml
 * 
 * @author DMaliavin
 * @since 0.0.1
 */
public class ApplicationInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext container) throws ServletException {
        WebApplicationContext ctx = createWebApplicationContext(container);

        container.setSessionTrackingModes(EnumSet.of(SessionTrackingMode.COOKIE));
        container.addListener(new ContextLoaderListener(ctx));

        registerFilters(container);
        registerDispatcherServlet(container, ctx);
    }

    private WebApplicationContext createWebApplicationContext(ServletContext container) {
        AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
        ctx.scan("com.maliavin.vcp.config");
        ctx.setServletContext(container);
        ctx.refresh();
        return ctx;
    }

    private void registerFilters(ServletContext container) {
        registerFilter(container, new CharacterEncodingFilter("UTF-8", true));
        registerFilter(container, new RequestContextFilter());
        registerFilter(container, new DelegatingFilterProxy("springSecurityFilterChain"), "springSecurityFilterChain");
    }
    
    private void registerFilter(ServletContext container, Filter filter, String... filterNames) {
        String filterName = filterNames.length > 0 ? filterNames[0] : filter.getClass().getSimpleName();
        container.addFilter(filterName, filter).addMappingForUrlPatterns(null, true, "/*");
    }


    private void registerDispatcherServlet(ServletContext container, WebApplicationContext ctx) {
        ServletRegistration.Dynamic servlet = container.addServlet("dispatcher", new DispatcherServlet(ctx));
        servlet.setLoadOnStartup(1);
        servlet.addMapping("/");
    }
}
