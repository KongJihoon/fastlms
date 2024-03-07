package com.zerobase.fastlms.member.service;

import com.zerobase.fastlms.admin.dto.MemberDto;
import com.zerobase.fastlms.admin.model.MemberParam;
import com.zerobase.fastlms.course.model.ServiceResult;
import com.zerobase.fastlms.member.entity.Member;
import com.zerobase.fastlms.member.entity.ResetPasswordInput;
import com.zerobase.fastlms.member.model.MemberInput;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;


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


    /**
     * 회원 목록 위한(관리자에서만 사용 가능)
     * @return
     */
    List<MemberDto> list(MemberParam memberParam);


    /**
     * 회원 상세 정보
     */
    MemberDto detail(String userId);

    /**
     * 회원 상태 변경
     */
    boolean updateStatus(String userId, String userStatus);

    /**
     * 회원 비밀번호 초기화
     */
    boolean updatePassword(String userId, String password);


    /**
     * 회원 정보 수정
     */
    ServiceResult updateMember(MemberInput parameter);


    /**
     * 회원 정보 페이지내 비밀번호 변경
     */

    ServiceResult updateMemberPassword(MemberInput parameter);

}
