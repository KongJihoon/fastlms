package com.zerobase.fastlms.course.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TakeCourse implements TakeCourseCode{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    Long courseId;

    String userId;

    long payPrice;

    String status; // 상태( 수강 신청, 결제 완료, 수강 취소)

    LocalDateTime regDt; // 신청일


}
