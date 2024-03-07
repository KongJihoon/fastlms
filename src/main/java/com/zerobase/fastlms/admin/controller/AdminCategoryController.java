package com.zerobase.fastlms.admin.controller;

import com.zerobase.fastlms.admin.dto.CategoryDto;
import com.zerobase.fastlms.admin.model.CategoryInput;
import com.zerobase.fastlms.admin.model.MemberParam;
import com.zerobase.fastlms.admin.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminCategoryController {

    private final CategoryService categoryService;

    @GetMapping("/admin/category/list")
    public String list(Model model, MemberParam memberParam){

        List<CategoryDto> categoryList = categoryService.categoryList();
        model.addAttribute("categoryList", categoryList);


        return "admin/category/categoryList";
    }

    @PostMapping("/admin/category/add")
    public String add(Model model, CategoryInput parameter){

        boolean result = categoryService.add(parameter.getCategoryName());

        return "redirect:/admin/category/list";
    }

    @PostMapping("/admin/category/delete")
    public String delete(Model model, CategoryInput parameter){

        boolean result = categoryService.delete(parameter.getId());

        return "redirect:/admin/category/list";
    }

    @PostMapping("/admin/category/update")
    public String update(Model model, CategoryInput parameter){

        boolean result = categoryService.update(parameter);

        return "redirect:/admin/category/list";
    }

}
