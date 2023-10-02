package com.example.ampersand02.payload.commons;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse implements Serializable {
    private Boolean status;
    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<ErrorObj> errors;

    public ApiResponse(Boolean status, String message) {
        this.status = status;
        this.message = message;
    }
}
