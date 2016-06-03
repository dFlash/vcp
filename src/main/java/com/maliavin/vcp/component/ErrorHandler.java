package com.maliavin.vcp.component;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

import com.maliavin.vcp.exception.SavingException;

/**
 * Custom error handler
 * 
 * @author DMaliavin
 * @since 0.0.1
 */
@Component
public class ErrorHandler extends DefaultHandlerExceptionResolver {
    private static final Logger LOGGER = LoggerFactory.getLogger(ErrorHandler.class);

    public ErrorHandler() {
        setOrder(0);
    }

    @Override
    protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
            Exception ex) {
        LOGGER.error("Error: " + ex.getMessage(), ex);
        try {
            String addErrorMsg = "{ \"message\":\"" + ex.getMessage() + "\", \"addError\":true}";
            response.reset();
            if (ex instanceof SavingException) {
                response.getWriter().write(addErrorMsg);
            } else {
                response.getWriter().write("{ \"message\":\"" + ex.getMessage() + "\"}");
            }
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        } catch (IOException e) {
            LOGGER.error("Error: " + ex.getMessage(), ex);
        }
        return new ModelAndView();
    }

}
