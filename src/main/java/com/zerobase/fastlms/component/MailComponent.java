package com.zerobase.fastlms.component;


import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;

@Component
@RequiredArgsConstructor
public class MailComponent {

    private final JavaMailSender javaMailSender;

    public void sendMailTest(){
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo("rwg1279@naver.com");
        message.setSubject("안녕하세요. 공지훈입니다.");
        message.setText("안녕하세요. 공지훈입니다.");


        javaMailSender.send(message);

    }


    public boolean sendMail(String mail, String subject, String text){

        boolean result = false;

        MimeMessagePreparator msg = new MimeMessagePreparator() {
            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
                mimeMessageHelper.setTo(mail);
                mimeMessageHelper.setSubject(subject);
                mimeMessageHelper.setText(text, true);
            }
        };

        try {
            javaMailSender.send(msg);
            return true;
        }catch (Exception e){
            System.out.println(e.getMessage());
        }


        return result;
    }

}
