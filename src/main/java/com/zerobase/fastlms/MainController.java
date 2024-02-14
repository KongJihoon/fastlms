package com.zerobase.fastlms;


// MainPage 클래스를 만든 목적
// 논리적인 주소와 물리적인 주소를 매핑하기 위함


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Controller
public class MainController {


    @RequestMapping("/")
    public String index(){
        return "index";
    }

    // request -> WEB -> SERVER
    // response -> SERVER -> WEB

    @RequestMapping("/hello")
    public void hello(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setContentType("text/html;charset=UTF-8");

        PrintWriter printWriter = response.getWriter();


        String msg = "<html>" +
                "<head>" +
                "<meta charset=\"UTF-*\">"+
                "<body>" +
                "<p>hello</p> <p>fastlms website!!!</p>" +
                "<p> 안녕하세요 </p>" +
                "</body>" +
                "</html>";

        printWriter.write(msg);
        printWriter.close();
    }





}
