package com.zerobase.fastlms.admin.controller;

import com.zerobase.fastlms.admin.dto.MemberDto;
import com.zerobase.fastlms.admin.model.MemberParam;
import com.zerobase.fastlms.member.service.MemberService;
import com.zerobase.fastlms.util.PageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminMemberController {

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

        PageUtil pageUtil = new PageUtil(totalCount, memberParam.getPageSize(), memberParam.getPageIndex(), queryString);


        model.addAttribute("list", members);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("pager", pageUtil.pager());

        return "admin/member/list";
    }

}
