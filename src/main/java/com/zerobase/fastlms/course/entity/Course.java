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
public class Course {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
         Long id;

         Long categoryId;

         String imagePath;

         String keyword;

         String subject;

        @Column(length = 1000)
         String summery;

        @Lob
         String content;

         Long price;

         Long salePrice;

         LocalDate saleEndDt;

         LocalDateTime regDt;
         LocalDateTime udtDt;


}
