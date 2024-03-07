package com.zerobase.fastlms.member.service.impl;

import com.zerobase.fastlms.admin.dto.MemberDto;
import com.zerobase.fastlms.admin.mapper.MemberMapper;
import com.zerobase.fastlms.admin.model.MemberParam;
import com.zerobase.fastlms.component.MailComponent;
import com.zerobase.fastlms.config.MemberStopUserException;
import com.zerobase.fastlms.course.model.ServiceResult;
import com.zerobase.fastlms.member.entity.Member;
import com.zerobase.fastlms.member.entity.MemberCode;
import com.zerobase.fastlms.member.entity.ResetPasswordInput;
import com.zerobase.fastlms.member.exception.MemberNotEmailAuthException;
import com.zerobase.fastlms.member.model.MemberInput;
import com.zerobase.fastlms.member.repository.MemberRepository;
import com.zerobase.fastlms.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final MailComponent mailComponent;
    private final MemberMapper memberMapper;


    /**
     * 회원 가입
     */
    @Override
    @Transactional
    public boolean register(MemberInput memberInput) {

        Optional<Member> optionalMember = memberRepository.findById(memberInput.getUserId());

        if(optionalMember.isPresent()){
            return false;
        }
        String encPassword = BCrypt.hashpw(memberInput.getPassword(), BCrypt.gensalt());

        String uuid = UUID.randomUUID().toString();

        Member member = Member.builder().
                userId(memberInput.getUserId()).
                userName(memberInput.getUserName()).
                password(encPassword).
                phone(memberInput.getPhone()).
                regDt(LocalDateTime.now()).
                emailAuthYn(false).
                emailAuthKey(uuid).
                userStatus(MemberCode.MEMBER_STATUS_REQ)
                .build();
        memberRepository.save(member);

        String email = memberInput.getUserId();
        String subject = "fastlms 사이트 가입을 축하드립니다.";
        String text = "<p>fastlms 사이트 가입을 축하드립니다.</p>" +
                "<p>아래 링크를 클릭하셔서 가입을 완료하세요.</p>" +
                "<div><a target ='_blank' href='http://localhost:8080/member/email_auth?id=" + uuid + "'>가입 완료</a></div>";

        mailComponent.sendMail(email, subject, text);

        return true;
    }

    @Override
    @Transactional
    public boolean emailAuth(String uuid) {

        Optional<Member> emailAuthKey = memberRepository.findByEmailAuthKey(uuid);

        if(!emailAuthKey.isPresent()){
            return false;
        }

        Member member = emailAuthKey.get();

        if(member.isEmailAuthYn()){
            return false;
        }

        member.setEmailAuthYn(true);
        member.setEmailAuthDt(LocalDateTime.now());
        member.setUserStatus(MemberCode.MEMBER_STATUS_ING);

        memberRepository.save(member);


        return true;
    }

    @Override
    public boolean sendResetPassword(ResetPasswordInput input) {

        Optional<Member> optionalMember = memberRepository.findByUserIdAndUserName(input.getUserId(), input.getUserName());
        if(!optionalMember.isPresent()){
            throw new UsernameNotFoundException("회원 정보가 존재하지 않습니다.");
        }

        String uuid = UUID.randomUUID().toString();

        Member member = optionalMember.get();
        member.setResetPasswordKey(uuid);
        member.setResetPasswordLimitDt(LocalDateTime.now().plusDays(1));

        memberRepository.save(member);


        String email = input.getUserId();
        String subject = "[fastlms] 비밀번호 초기화 메일 입니다";
        String text = "<p>fastlms 비밀번호 초기화 메일 입니다.</p>" +
                "<p>아래 링크를 비밀번호를 초기화 해주세요.</p>" +
                "<div><a target ='_blank' href='http://localhost:8080/member/reset_password?id=" + uuid + "'>비밀번호 초기화 링크</a></div>";

        mailComponent.sendMail(email, subject, text);


        return true;
    }

    @Override
    public boolean resetPassword(String uuid, String password) {

        Optional<Member> optionalMember = memberRepository.findByResetPasswordKey(uuid);
        if(!optionalMember.isPresent()){
            throw new UsernameNotFoundException("회원 정보가 존재하지 않습니다.");
        }

        Member member = optionalMember.get();

        // 초기화 날짜 유효성 검사
        if (member.getResetPasswordLimitDt() == null){
            throw new RuntimeException("유효한 날짜가 존재하지 않습니다.");
        }

        if(member.getResetPasswordLimitDt().isBefore(LocalDateTime.now())){
            throw new RuntimeException("유효한 날짜가 아닙니다.");
        }

        String encPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        member.setPassword(encPassword);
        member.setResetPasswordKey("");
        member.setResetPasswordLimitDt(null);
        memberRepository.save(member);

        return true;
    }

    @Override
    public boolean checkResetPassword(String uuid) {
        Optional<Member> optionalMember = memberRepository.findByResetPasswordKey(uuid);
        if(!optionalMember.isPresent()){
            return false;
        }

        Member member = optionalMember.get();

        // 초기화 날짜 유효성 검사
        if (member.getResetPasswordLimitDt() == null){
            throw new RuntimeException("유효한 날짜가 존재하지 않습니다.");
        }

        if(member.getResetPasswordLimitDt().isBefore(LocalDateTime.now())){
            throw new RuntimeException("유효한 날짜가 아닙니다.");
        }


        return true;
    }

    @Override
    public List<MemberDto> list(MemberParam memberParam) {

        long totalCount = memberMapper.selectListCount(memberParam);

        List<MemberDto> list = memberMapper.selectList(memberParam);

        if(!CollectionUtils.isEmpty(list)){
            int i = 0;
            for (MemberDto x : list){
                x.setTotalCount(totalCount);

                x.setSeq(totalCount - memberParam.getPageStart() - i);
                i++;
            }
        }

        return list;
    }

    @Override
    public MemberDto detail(String userId) {

        Optional<Member> optionalMember = memberRepository.findById(userId);

        if(!optionalMember.isPresent()){
            return null;
        }

        Member member = optionalMember.get();


        return MemberDto.of(member);
    }

    @Override
    public boolean updateStatus(String userId, String userStatus) {
        Optional<Member> optionalMember = memberRepository.findById(userId);

        if(!optionalMember.isPresent()){
            throw new UsernameNotFoundException("회원 정보가 존재하지 않습니다.");
        }

        Member member = optionalMember.get();

        member.setUserStatus(userStatus);
        memberRepository.save(member);


        return true;
    }

    @Override
    public boolean updatePassword(String userId, String password) {
        Optional<Member> optionalMember = memberRepository.findById(userId);

        if(!optionalMember.isPresent()){
            throw new UsernameNotFoundException("회원 정보가 존재하지 않습니다.");
        }

        Member member = optionalMember.get();

        String encPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        member.setPassword(encPassword);
        memberRepository.save(member);


        return true;
    }

    @Override
    public ServiceResult updateMember(MemberInput parameter) {

        String userId = parameter.getUserId();

        Optional<Member> optionalMember = memberRepository.findById(userId);

        if(!optionalMember.isPresent()){
            throw new UsernameNotFoundException("회원 정보가 존재하지 않습니다.");
        }

        Member member = optionalMember.get();

        member.setPhone(parameter.getPhone());
        member.setZipCode(parameter.getZipCode());
        member.setAddr(parameter.getAddr());
        member.setAddrDetail(parameter.getAddrDetail());
        member.setUptDt(LocalDateTime.now());

        memberRepository.save(member);


        return new ServiceResult(true);
    }

    @Override
    public ServiceResult updateMemberPassword(MemberInput parameter) {

        String userId = parameter.getUserId();

        Optional<Member> optionalMember = memberRepository.findById(userId);

        if(!optionalMember.isPresent()){
            return new ServiceResult(false, "회원 정보가 존재하지 않습니다.");
        }

        Member member = optionalMember.get();

        if (!BCrypt.checkpw(parameter.getPassword(), member.getPassword())) {
            return new ServiceResult(false, "비밀번호가 일치하지 않습니다.");
        }

        String encPassword = BCrypt.hashpw(parameter.getNewPassword(), BCrypt.gensalt());

        member.setPassword(encPassword);

        memberRepository.save(member);
        return new ServiceResult(true);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<Member> optionalMember = memberRepository.findById(username);

        if(!optionalMember.isPresent()){
            throw new UsernameNotFoundException("회원 정보가 존재하지 않습니다.");
        }

        Member member = optionalMember.get();

        if(Member.MEMBER_STATUS_REQ.equals(member.getUserStatus())){
            throw new MemberNotEmailAuthException("이메일 활성화 이후에 로그인을 해주세요.");
        }

        if(Member.MEMBER_STATUS_STOP.equals(member.getUserStatus())){
            throw new MemberStopUserException("정지된 회원 입니다.");
        }

        if(!member.isEmailAuthYn()){
            throw new MemberNotEmailAuthException("이메일을 활성화 해주세요");
        }



        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        if(member.isAdminYn()){
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }

        return new User(member.getUserId(), member.getPassword(), grantedAuthorities);
    }
}
