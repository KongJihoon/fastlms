package com.zerobase.fastlms.member.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Member {

    @Id
    private String userId;

    private String userName;

    private String phone;

    private String password;

    private LocalDateTime regDt;

    private boolean emailAuthYn;

    private LocalDateTime emailAuthDt;

    private String emailAuthKey;

    private String resetPasswordKey;
    private LocalDateTime resetPasswordLimitDt;
}
