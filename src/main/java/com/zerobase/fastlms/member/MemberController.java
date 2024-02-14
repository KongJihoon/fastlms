package com.zerobase.fastlms.member;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class MemberController {

    // member/register
    @GetMapping("/member/register")
    public String register(){


        return "member/register";
    }

    @PostMapping("/member/register")
    public String registerSubmit(HttpServletRequest request, HttpServletResponse response
    , MemberInput parameter){

        System.out.println(parameter.toString());




        return "member/register";
    }
}
