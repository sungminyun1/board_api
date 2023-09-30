package com.springBoard.payload;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;
import java.util.List;

@JsonPropertyOrder({
        "success",
        "resMsg"
})
public class ApiResponse<T> implements Serializable {
    @JsonIgnore
    private static final long serialVersionUID = 1L;

    @JsonProperty("success")
    private Boolean success;

    @JsonProperty("resMsg")
    private String resMsg;

    @JsonProperty("list")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<T> list;

    public ApiResponse() {
    }

    public ApiResponse(Boolean success, String resMsg) {
        this.success = success;
        this.resMsg = resMsg;
    }

    public ApiResponse(Boolean success, String resMsg, List<T> list) {
        this.success = success;
        this.resMsg = resMsg;
        this.list = list;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getResMsg() {
        return resMsg;
    }

    public void setResMsg(String resMsg) {
        this.resMsg = resMsg;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
