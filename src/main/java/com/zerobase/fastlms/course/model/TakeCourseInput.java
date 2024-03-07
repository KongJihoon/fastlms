package com.zerobase.fastlms.course.model;

import com.zerobase.fastlms.admin.model.CommonParam;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TakeCourseInput extends CommonParam {
    long courseId;
    String userId;

    long takeCourseId;
}

