package com.example.vn_railway.common;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;

import javax.mail.internet.MimeMessage;

@RequiredArgsConstructor
public class MailSender {
    private final JavaMailSender javaMailSender;
    public void sendEmail(){
        try{
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,true);
//            mimeMessageHelper.setFrom("vietnamrailwaynhhn@gmail.com");
//            mimeMessageHelper.setTo();
//            mimeMessageHelper.setSubject();

            String htmlContent = "aaa";
            mimeMessageHelper.setText(htmlContent,true);
            javaMailSender.send(mimeMessage);
        }catch (MessagingException messagingException){
            messagingException.printStackTrace();
        } catch (javax.mail.MessagingException e) {
            throw new RuntimeException(e);
        }
    }
//    public String generateHTMLForMail(){
//
//    }
}
