package com.mybank.paymenthub.dto.response;

import java.time.LocalDateTime;

public class ErrorResponse {
    private String code;
    private String message;
    private String field;
    private Object rejectedValue;
    private LocalDateTime timestamp;
}
