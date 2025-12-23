package me.sreejithnair.ecomx_api.common.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ApiResponse<T>{
    private T data;
    private String message = "";
    private ApiError error;

    @JsonFormat(pattern = "hh:mm:ss dd-MM-yyyy")
    private LocalDateTime timestamp;

    public ApiResponse(){
        this.timestamp = LocalDateTime.now();
    }

    public ApiResponse(T data){
        this();
        this.data = data;
    }

    public ApiResponse(T data, String message){
        this();
        this.message = message;
        this.data = data;
    }

    public ApiResponse(String message) {
        this();
        this.message = message;
    }

    public ApiResponse(ApiError apiError){
        this();
        this.error = apiError;
    }

    public ApiResponse(ApiError apiError, String message){
        this();
        this.error = apiError;
        this.message = message;
    }
}
