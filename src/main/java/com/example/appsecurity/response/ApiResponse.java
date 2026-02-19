package com.example.appsecurity.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ApiResponse<T> {
    private int status;
    private String message;
    private  long timestamp;
    private T errors;
}
