package com.zerobase.fastlms.member;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@Builder
public class MemberInput {
    private String userId;
    private String userName;
    private String password;
    private String phone;

}
