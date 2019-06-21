package com.example.auth_demo.controller;


import com.example.auth_demo.dto.GetTokenParam;
import com.example.auth_demo.entity.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.auth_demo.util.EncryptionUtil.encryptPassword;

@RestController
@RequestMapping("/token")
@Slf4j
public class AuthController {
    @PostMapping(value = "/new", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ApiResult getToken(@RequestBody GetTokenParam getTokenParam) throws Exception {
        String encrypted = encryptPassword(getTokenParam.getPassword());
        // TODO : persist
        return new ApiResult<>(200, "token generated", encrypted);
    }

}
