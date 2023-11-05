package com.springBoard.payload;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.springBoard.constant.ResponseStatus;

import java.io.Serializable;
import java.util.List;

@JsonPropertyOrder({
        "success",
        "code",
        "resMsg"
})
public class ApiResponse implements Serializable {
    @JsonIgnore
    private static final long serialVersionUID = 1L;

    @JsonProperty("success")
    private Boolean success;

    @JsonProperty("code")
    private int code;

    @JsonProperty("resMsg")
    private String resMsg;

    @JsonProperty("data")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object data;

    public ApiResponse() {
    }

    public ApiResponse(ResponseStatus status){
        this.success = status.isSuccess();
        this.resMsg = status.getMessage();
        this.code = status.getCode();
    }

    public ApiResponse(Object data){
        this.success = ResponseStatus.SUCCESS.isSuccess();
        this.resMsg = ResponseStatus.SUCCESS.getMessage();
        this.code = ResponseStatus.SUCCESS.getCode();
        this.data = data;
    }

//    public ApiResponse(Boolean success, String resMsg) {
//        this.success = success;
//        this.resMsg = resMsg;
//    }
//
//    public ApiResponse(Boolean success, String resMsg, Object data) {
//        this.success = success;
//        this.resMsg = resMsg;
//        this.data = data;
//    }


    public Boolean getSuccess() {
        return success;
    }

    public int getCode() {
        return code;
    }

    public String getResMsg() {
        return resMsg;
    }

    public Object getData() {
        return data;
    }
}
