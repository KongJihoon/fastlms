package com.zerobase.fastlms.member.controller;


import com.zerobase.fastlms.admin.dto.MemberDto;
import com.zerobase.fastlms.course.dto.CourseDto;
import com.zerobase.fastlms.course.dto.TakeCourseDto;
import com.zerobase.fastlms.course.model.ServiceResult;
import com.zerobase.fastlms.course.service.TakeCourseService;
import com.zerobase.fastlms.member.entity.Member;
import com.zerobase.fastlms.member.entity.ResetPasswordInput;
import com.zerobase.fastlms.member.model.MemberInput;
import com.zerobase.fastlms.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final TakeCourseService takeCourseService;

    @RequestMapping("/member/login")
    public String login(){


        return "member/login";
    }


    @GetMapping("/member/find_password")
    public String findPassword(){

        return "member/find_password";
    }

    @PostMapping("/member/find_password")
    public String findPasswordSubmit(
            ResetPasswordInput input,
            Model model){

        boolean result = false;

        try {
             result = memberService.sendResetPassword(input);
        }catch (Exception e){

        }
        model.addAttribute("result", result);

        return "member/find_password_result";
    }

    // member/register
    @GetMapping("/member/register")
    public String register(){


        return "member/register";
    }

    @PostMapping("/member/register")
    public String registerSubmit(Model model, HttpServletRequest request, HttpServletResponse response
    , MemberInput parameter){


        boolean result = memberService.register(parameter);


        model.addAttribute("result", result);

        return "member/register_complete";
    }

    @GetMapping("/member/email_auth")
    public String emailAuth(
            Model model,
            HttpServletRequest request
    ){
        String uuid = request.getParameter("id");

        boolean result = memberService.emailAuth(uuid);
        model.addAttribute("result", result);

        return "member/email_auth";
    }

    @GetMapping("/member/info")
    public String memberInfo(Model model, Principal principal) {

        String userId = principal.getName();

        MemberDto detail = memberService.detail(userId);

        model.addAttribute("detail", detail);


        return "member/info";
    }
    @PostMapping("/member/info")
    public String memberInfoSubmit(Model model
                                   , MemberInput parameter,
                                   Principal principal) {

        String userId = principal.getName();
        parameter.setUserId(userId);

        ServiceResult result = memberService.updateMember(parameter);

        if(!result.isResult()){
            model.addAttribute("message", result.getMessage());
            return "common/error";
        }


        return "redirect:/member/info";
    }
    @GetMapping("/member/password")
    public String memberPassword(Model model, Principal principal) {

        String userId = principal.getName();

        MemberDto detail = memberService.detail(userId);

        model.addAttribute("detail", detail);


        return "member/password";
    }
    @PostMapping("/member/password")
    public String memberPasswordSubmit(Model model
                                       , MemberInput parameter
            , Principal principal) {

        String userId = principal.getName();

        parameter.setUserId(userId);

        ServiceResult result = memberService.updateMemberPassword(parameter);

        if(!result.isResult()){
            model.addAttribute("message", result.getMessage());
            return "common/error";
        }

        return "redirect:/member/info";
    }
    @GetMapping("/member/takecourse")
    public String memberTakeCourse(Model model, Principal principal) {

        String userId = principal.getName();
        MemberDto detail = memberService.detail(userId);

        List<TakeCourseDto> list =  takeCourseService.myCourse(userId);

        model.addAttribute("list", list);


        return "member/takecourse";
    }

    @GetMapping("/member/reset_password")
    public String resetPassword(Model model, HttpServletRequest request){

        String uuid = request.getParameter("id");

        boolean result = memberService.checkResetPassword(uuid);

        model.addAttribute("result", result);

        return "member/reset_password";
    }

    @PostMapping("/member/reset_password")
    public String resetPasswordSubmit(
            ResetPasswordInput input,
            Model model
    ){


        boolean result = false;
        try {
            result = memberService.resetPassword(input.getId(), input.getPassword());
        }catch (Exception e){
//            throw new RuntimeException("")
        }

        model.addAttribute("result", result);


        return "member/reset_password_result";
    }

}
