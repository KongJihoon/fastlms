package com.zerobase.fastlms.course.service;

import com.zerobase.fastlms.course.dto.CourseDto;
import com.zerobase.fastlms.course.dto.TakeCourseDto;
import com.zerobase.fastlms.course.model.CourseParam;
import com.zerobase.fastlms.course.model.ServiceResult;
import com.zerobase.fastlms.course.model.TakeCourseParam;

import java.util.List;

public interface TakeCourseService {

    List<TakeCourseDto> list(TakeCourseParam parameter);

    /**
     * 수강 상세 정보
     */
    TakeCourseDto detail(long id);

    // 수강 신청 상태 변경
    ServiceResult updateStatus(long id, String status);


    // 수강 신청 내역
    List<TakeCourseDto> myCourse(String userId);


    // 수강 신청 취소 처리
    ServiceResult cancel(long id);
}
