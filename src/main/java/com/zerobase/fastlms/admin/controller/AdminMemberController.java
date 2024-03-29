package com.zerobase.fastlms.admin.controller;

import com.zerobase.fastlms.admin.dto.MemberDto;
import com.zerobase.fastlms.admin.model.MemberParam;
import com.zerobase.fastlms.admin.model.MemberInput;
import com.zerobase.fastlms.course.controller.BaseController;
import com.zerobase.fastlms.member.service.MemberService;
import com.zerobase.fastlms.util.PageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminMemberController extends BaseController {

    private final MemberService memberService;

    @GetMapping("/admin/member/list")
    public String list(Model model, MemberParam memberParam){

        memberParam.init();

        List<MemberDto> members = memberService.list(memberParam);


        long totalCount = 0;
        if(members != null && !members.isEmpty()){
            totalCount = members.get(0).getTotalCount();
        }

        String queryString = memberParam.getQueryString();

        String pagerHtml = getPagerHtml(totalCount, memberParam.getPageSize(), memberParam.getPageIndex(), queryString);


        model.addAttribute("list", members);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("pager", pagerHtml);

        return "admin/member/list";
    }

    @GetMapping("/admin/member/detail")
    public String detail(Model model, MemberParam memberParam){

        memberParam.init();

        MemberDto member = memberService.detail(memberParam.getUserId());

        model.addAttribute("member", member);



        return "admin/member/detail";
    }


    @PostMapping("/admin/member/status")
    public String status(Model model, MemberInput parameter){

       boolean result = memberService.updateStatus(parameter.getUserId(), parameter.getUserStatus());


       return "redirect:/admin/member/detail?userId=" + parameter.getUserId();
    }

    @PostMapping("/admin/member/password")
    public String password(Model model, MemberInput parameter){

        boolean result = memberService.updatePassword(parameter.getUserId(), parameter.getPassword());


        return "redirect:/admin/member/detail?userId=" + parameter.getUserId();
    }


}
