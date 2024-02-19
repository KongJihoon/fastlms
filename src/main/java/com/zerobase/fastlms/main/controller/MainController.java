package com.zerobase.fastlms.main.controller;


// MainPage 클래스를 만든 목적
// 논리적인 주소와 물리적인 주소를 매핑하기 위함


import com.zerobase.fastlms.component.MailComponent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@RequiredArgsConstructor
@Controller
public class MainController {
    private final MailComponent mailComponent;

    @RequestMapping("/")
    public String index(){


        return "index";
    }







}
