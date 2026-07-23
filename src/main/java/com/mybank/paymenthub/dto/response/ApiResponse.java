package com.mybank.paymenthub.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {

    private String code;

    private String message;

    private T data;

    private LocalDateTime timestamp;


    public static <T> ApiResponse<T> success(
            String code,
            String message,
            T data
    ){

        return new ApiResponse<>(
                code,
                message,
                data,
                LocalDateTime.now()
        );
    }


    public static <T> ApiResponse<T> error(
            String code,
            String message,
            T data
    ){

        return new ApiResponse<>(
                code,
                message,
                data,
                LocalDateTime.now()
        );
    }

}