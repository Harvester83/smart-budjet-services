package com.smartbudget.authservice.common.dto;


import lombok.Data;

@Data
public class ApiError {
    private String code;
    private String message;

}
