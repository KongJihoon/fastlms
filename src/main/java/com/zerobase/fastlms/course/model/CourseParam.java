package com.zerobase.fastlms.course.model;

import com.zerobase.fastlms.admin.model.CommonParam;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourseParam extends CommonParam {
    long id;
    long categoryId;

}
