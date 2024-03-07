package com.zerobase.fastlms.course.model;

import lombok.Data;

@Data
public class CourseInput {

    String subject;
    long id;
    long categoryId;
    String keyword;
    String summary;
    String content;
    long price;
    long salePrice;
    String saleEndDtText;


    // 삭제를 위한 속성
    String idList;
}
