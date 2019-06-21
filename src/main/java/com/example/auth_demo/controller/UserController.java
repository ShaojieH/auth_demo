package com.example.auth_demo.controller;


import com.example.auth_demo.dto.TestParam;
import com.example.auth_demo.entity.ApiResult;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.auth_demo.util.EncryptionUtil.getHmacForMap;
import static com.example.auth_demo.util.MapUtil.getObjectStringFieldMap;

@RestController
@RequestMapping("/user")
public class UserController {
    @PostMapping(value = "/test", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ApiResult test(@RequestBody TestParam testParam) throws Exception{

        return new ApiResult<>(200, "generated hmac", getHmacForMap(getObjectStringFieldMap(testParam)));
    }
}
