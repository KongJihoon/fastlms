package com.zerobase.fastlms.member.controller;


import com.zerobase.fastlms.common.model.ResponseResult;
import com.zerobase.fastlms.course.dto.TakeCourseDto;
import com.zerobase.fastlms.course.model.ServiceResult;
import com.zerobase.fastlms.course.model.TakeCourseInput;
import com.zerobase.fastlms.course.service.TakeCourseService;
import com.zerobase.fastlms.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ApiMemberController {

    private final MemberService memberService;
    private final TakeCourseService takeCourseService;

    @PostMapping("/api/member/course/cancel.api")
    public ResponseEntity<?> cancelCourse(
            @RequestBody TakeCourseInput parameter,
            Principal principal) {

        // 내 강좌인지 확인.
        String userId = principal.getName();


        TakeCourseDto detail = takeCourseService.detail(parameter.getTakeCourseId());

        if (detail == null) {
            ResponseResult responseResult = new ResponseResult(false, "수강 신청 정보가 존재하지 않습니다.");
            return ResponseEntity.ok().body(responseResult);
        }

        if (userId == null || !userId.equals(detail.getUserId())){
            ResponseResult responseResult = new ResponseResult(false, "본인의 수강신청 정보만 취소할 수 있습니다.");
            return ResponseEntity.ok().body(responseResult);
        }


        ServiceResult cancel = takeCourseService.cancel(parameter.getTakeCourseId());

        if(!cancel.isResult()){
            ResponseResult responseResult = new ResponseResult(false, cancel.getMessage());
            return ResponseEntity.ok().body(responseResult);
        }


        ResponseResult responseResult = new ResponseResult(true);
        return ResponseEntity.ok().body(responseResult);
    }

}
