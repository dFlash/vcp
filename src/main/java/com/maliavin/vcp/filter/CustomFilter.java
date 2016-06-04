package com.maliavin.vcp.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("customProductionFilter")
public class CustomFilter implements Filter {

    @Value("${production}")
    private String production;

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        setResponseProductionHeader((HttpServletResponse) response);
        chain.doFilter(request, response);
    }

    private void setResponseProductionHeader(HttpServletResponse response) {
        response.addHeader("production", production);
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
    }

}
