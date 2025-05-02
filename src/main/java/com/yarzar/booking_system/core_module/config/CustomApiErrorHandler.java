package com.yarzar.booking_system.core_module.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.zalando.problem.spring.web.advice.ProblemHandling;
import org.zalando.problem.spring.web.advice.security.SecurityAdviceTrait;

@ControllerAdvice
public class CustomApiErrorHandler implements ProblemHandling, SecurityAdviceTrait {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomApiErrorHandler.class);

    @Override
    public String formatFieldName(String fieldName) {
        return this.convertFieldNameToSnakeCase(fieldName);
    }

    private String convertFieldNameToSnakeCase(String fieldName) {
        // Convert camelCase to snake_case
        return fieldName.replaceAll("([a-z])([A-Z])", "$1_$2").toLowerCase();
    }
}
