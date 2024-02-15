package com.green.greengram4.user.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UserSigninDto {
    @Schema(title ="유저 아이디 ")
    private String uid;
    @Schema(title ="유저 비밀번호")
    private String upw;
}
