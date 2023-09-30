package com.springBoard.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ApiResponseWithData<T> extends ApiResponse {
    @JsonProperty("list")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<T> list;

    @JsonProperty("data")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    public ApiResponseWithData(Boolean success, String resMsg, List<T> list) {
        super(success, resMsg);
        this.list = list;
    }

    public ApiResponseWithData(Boolean success, String resMsg, T data) {
        super(success, resMsg);
        this.data = data;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
