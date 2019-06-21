package com.example.auth_demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class GetTokenParam {
    @JsonProperty("password")
    private String password;
}
