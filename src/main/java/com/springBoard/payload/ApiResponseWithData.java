package com.springBoard.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ApiResponseWithData<T> extends ApiResponse {
    @JsonProperty("data")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    public ApiResponseWithData() {
    }

    public ApiResponseWithData(Boolean success, String resMsg, T data) {
        super(success, resMsg);
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
