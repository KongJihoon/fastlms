package com.zerobase.fastlms.member.entity;


import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class ResetPasswordInput {

    private String userId;
    private String userName;

    private String id;
    private String password;

}
