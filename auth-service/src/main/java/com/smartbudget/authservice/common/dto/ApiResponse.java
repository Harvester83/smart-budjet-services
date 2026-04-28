package com.smartbudget.authservice.common.dto;

public class ApiResponse<T> {
    private T data;
    private ApiError error;

    public static <T> ApiResponse<T> success(T data) {
        ApiResponse<T> res = new ApiResponse<>();
        res.data = data;
        res.error = null;
        return res;
    }

    public static <T> ApiResponse<T> error(ApiError error) {
        ApiResponse<T> res = new ApiResponse<>();
        res.data = null;
        res.error = error;
        return res;
    }
}
