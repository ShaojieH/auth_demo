package com.example.auth_demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class TestParam {
    @JsonProperty("payload")
    private String payload;
    @JsonProperty("others")
    private String others;
}
