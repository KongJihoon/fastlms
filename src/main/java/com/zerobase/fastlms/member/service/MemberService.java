package com.zerobase.fastlms.member.service;

import com.zerobase.fastlms.member.entity.Member;
import com.zerobase.fastlms.member.entity.ResetPasswordInput;
import com.zerobase.fastlms.member.model.MemberInput;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;


public interface MemberService extends UserDetailsService {

    boolean register(MemberInput memberInput);

    /**
     * uuid에 해당하는 계정을 활성화
     */
    boolean emailAuth(String uuid);

    /**
     * 입력한 이메일로 비밀번호 초기화 정보를 전송
     */
    boolean sendResetPassword(ResetPasswordInput input);

    /**
     * uuid에 대해서 password를 초기화
     */
    boolean resetPassword(String uuid, String password);


    /**
     * 입력받은 uuid 값 유효성 검사
     */
    boolean checkResetPassword(String uuid);
}
