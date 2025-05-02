package com.yarzar.booking_system.core_module.exception;


import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class ResourceNotFoundException extends AbstractThrowableProblem {

    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldObject) {
        super(
                null,
                Status.NOT_FOUND.getReasonPhrase(),
                Status.NOT_FOUND,
                String.format("%s not found with %s : %s", resourceName, fieldName, fieldObject));
    }
}
