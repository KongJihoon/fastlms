package com.zerobase.fastlms.course.controller;


import com.zerobase.fastlms.admin.dto.CategoryDto;
import com.zerobase.fastlms.admin.service.CategoryService;
import com.zerobase.fastlms.course.dto.CourseDto;
import com.zerobase.fastlms.course.model.CourseInput;
import com.zerobase.fastlms.course.model.CourseParam;
import com.zerobase.fastlms.course.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CourseController extends BaseController {

    private final CourseService courseService;
    private final CategoryService categoryService;

    @GetMapping("/course")
    public String list(Model model,
                       CourseParam courseParam) {

        List<CourseDto> list = courseService.frontList(courseParam);

        model.addAttribute("list", list);

        List<CategoryDto> categoryList = categoryService.frontList(CategoryDto.builder().build());

        int courseTotalCount = 0;

        if(categoryList != null) {
            for (CategoryDto x :categoryList){
                courseTotalCount += x.getCourseCount();
            }
        }


        model.addAttribute("categoryList", categoryList);
        model.addAttribute("courseTotalCount", courseTotalCount);

        return "course/index";
    }


    @GetMapping("/course/{id}")
    public String courseDetail(Model model,
                       CourseParam courseParam) {


        CourseDto detail = courseService.frontDetail(courseParam.getId());

        model.addAttribute("detail", detail);


        return "course/detail";
    }






}
