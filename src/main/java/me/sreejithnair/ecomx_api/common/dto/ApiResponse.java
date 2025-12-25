package me.sreejithnair.ecomx_api.common.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ApiResponse<T> {
    private T data;
    private String message;
    private HttpStatus status;
    private List<String> errors;

    @JsonFormat(pattern = "hh:mm:ss dd-MM-yyyy")
    private LocalDateTime timestamp;

    public ApiResponse() {
        this.timestamp = LocalDateTime.now();
    }

    public ApiResponse(T data) {
        this();
        this.data = data;
    }

    public ApiResponse(T data, String message) {
        this();
        this.message = message;
        this.data = data;
    }

    public ApiResponse(String message) {
        this();
        this.message = message;
    }

    public ApiResponse(ApiError apiError) {
        this();
        this.status = apiError.getStatus();
        this.message = apiError.getMessage();
        this.errors = apiError.getErrors();
    }

    // Static factory methods
    public static <T> ResponseEntity<ApiResponse<T>> ok(T data) {
        return ResponseEntity.ok(new ApiResponse<>(data));
    }

    public static <T> ResponseEntity<ApiResponse<T>> ok(T data, String message) {
        return ResponseEntity.ok(new ApiResponse<>(data, message));
    }

    public static <T> ResponseEntity<ApiResponse<T>> created(T data) {
        return new ResponseEntity<>(new ApiResponse<>(data), HttpStatus.CREATED);
    }

    public static <T> ResponseEntity<ApiResponse<T>> created(T data, String message) {
        return new ResponseEntity<>(new ApiResponse<>(data, message), HttpStatus.CREATED);
    }

    public static <T> ResponseEntity<ApiResponse<T>> noContent() {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
