package com.example.blogging.blog.exception;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResourceNotFoundException extends RuntimeException {
    private String resourceName;
    private String fieldName;
    private int fieldValue;

    public ResourceNotFoundException(String resourceName, String fieldName, int fieldValue) {
        super(String.format("%s having %s with %s not found", resourceName, fieldName, fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
}
