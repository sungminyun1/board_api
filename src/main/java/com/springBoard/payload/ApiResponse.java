package com.springBoard.payload;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;

@JsonPropertyOrder({
        "success",
        "resMsg"
})
public class ApiResponse implements Serializable {
    @JsonIgnore
    private static final long serialVersionUID = 1L;

    @JsonProperty("success")
    private Boolean success;

    @JsonProperty("resMsg")
    private String resMsg;

    public ApiResponse() {
    }

    public ApiResponse(Boolean success, String resMsg) {
        this.success = success;
        this.resMsg = resMsg;
    }
}
