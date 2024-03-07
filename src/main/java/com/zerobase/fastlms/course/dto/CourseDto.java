package com.zerobase.fastlms.course.dto;

import com.zerobase.fastlms.course.entity.Course;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseDto {


     Long id;

     Long categoryId;

     String imagePath;

     String keyword;

     String subject;


     String summery;

     String content;

     Long price;

     Long salePrice;

     LocalDate saleEndDt;

     LocalDateTime regDt;
     LocalDateTime udtDt;

     long totalCount;
     long seq;

     public static CourseDto of(Course course) {
          return CourseDto.builder()
                  .id(course.getId())
                  .categoryId(course.getCategoryId())
                  .imagePath(course.getImagePath())
                  .keyword(course.getKeyword())
                  .subject(course.getSubject())
                  .summery(course.getSummery())
                  .content(course.getContent())
                  .price(course.getPrice())
                  .salePrice(course.getSalePrice())
                  .saleEndDt(course.getSaleEndDt())
                  .regDt(course.getRegDt())
                  .udtDt(course.getUdtDt())
                  .build();
     }

     public static List<CourseDto> of(List<Course> courses){
          if(courses == null){
               return null;
          }

          List<CourseDto> courseDtoList = new ArrayList<>();

          for(Course x : courses){
               courseDtoList.add(CourseDto.of(x));

          }
          return courseDtoList;
     }
}
