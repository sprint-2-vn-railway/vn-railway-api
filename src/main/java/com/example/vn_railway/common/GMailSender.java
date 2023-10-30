package com.example.vn_railway.common;

import com.example.vn_railway.dto.train_dto.TicketResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;
import java.util.List;

@Component
@RequiredArgsConstructor
public class GMailSender {
    private final JavaMailSender javaMailSender;

    public void sendEmail(StringBuilder sb, String mail) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom("vietnamrailwaynhhn@gmail.com");
            mimeMessageHelper.setTo(mail);
//            mimeMessageHelper.setEncodeFilenames();

            String htmlContent = "" + sb;
            mimeMessageHelper.setText(htmlContent, true);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException messagingException) {
            messagingException.printStackTrace();
        } catch (javax.mail.MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public String generateHTMLForMail(List<TicketResponse> ticketResponseList) {
        StringBuilder sb = new StringBuilder();

        for (TicketResponse ticketResponse : ticketResponseList) {

            sb.append(
                    "    <div style=\"\n" +
                    "            display: flex;\n" +
                    "            align-items: center;\n" +
                    "            width: 80%;\n" +
                    "            height: 80%;\n" +
                    "            background-color:azure;\n" +
                    "            \">\n" +
                    "        <div style=\"\n" +
                    "            display: flex;\n" +
                    "            justify-content: center;\n" +
                    "            border: 4px solid #8B8B00;\n" +
                    "            border-right: 1px dashed  black;\n" +
                    "            border-top-left-radius: 1rem;\n" +
                    "            border-bottom-left-radius: 1rem;\n" +
                    "            padding: 1rem;\n" +
                    "            \">\n" +
                    "            <img style='object-fit: cover; height: 150px; width: auto' src='https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQdxGJa3bcMupJ9drcamWS3YjeSOsEFjO-kgqTA8NiuG1HWHU-0l_OE91XHvjZFZSQxzoI&usqp=CAU'/>" +
                    "        </div>\n" +
                    "\n" +
                    "        <div style=\"\n" +
                    "                justify-content: center;\n" +
                    "                border: 4px solid #8B8B00;\n" +
                    "                border-left: 1px dashed  black;\n" +
                    "                border-top-right-radius: 1rem;\n" +
                    "                border-bottom-right-radius: 1rem;\n" +
                    "                height: 150px;\n" +
                    "                padding: 1rem;\n" +
                    "            \">\n" +
                    "\n" +
                    "            <div style=\"\n" +
                    "                display: flex;\n" +
                    "                justify-content: center;\n" +
                    "                align-items: center;\n" +
                    "            \">\n" +
                    "                <img src=\"https://vantaiduongsat.net/wp-content/uploads/2021/06/logodsvn.png\" style=\"\n" +
                    "                    width: 5%;\n" +
                    "                    height: 5%;\n" +
                    "                    margin-right: 5px;\n" +
                    "                \" />\n" +
                    "                <h2 style=\"\n" +
                    "                text-align: center;\n" +
                    "            \">\n" +
                    "                    Công Ty Cổ Phần Đường Sắt Việt Nam VN-Railway\n" +
                    "                </h2>\n" +
                    "            </div>\n" +
                    "            <div style=\"\n" +
                    "            margin-top: 0.7rem;\n" +
                    "                display: flex;\n" +
                    "                justify-content: space-around;\n" +
                    "                width: 100%;\n" +
                    "                \">\n" +
                    "                <div style=\"display:flex; flex-direction: column; gap: 0.5rem;\">\n" +
                    "                    <div><strong>Mã tàu</strong>: " + ticketResponse.getTrainCode() + " </div>\n" +
                    "                    <div><strong>Tên tàu</strong>: " + ticketResponse.getTrainName() + "</div>\n" +
                    "                    <div><strong>Mã toa</strong>: " + ticketResponse.getCoachCode() + "</div>\n" +
                    "                </div>\n" +
                    "                <div style=\"display:flex; flex-direction: column; gap: 0.5rem;\">\n" +
                    "                    <div><strong>Mã ghế</strong>: " + ticketResponse.getSeatCode() + "</div>\n" +
                    "                    <div><strong>Loại ghế</strong>: Ghế mềm điều hoà</div>\n" +
                    "                    <div><strong>Giá</strong>: " + ticketResponse.getPrice() + "</div>\n" +
                    "                </div>\n" +
                    "                <div style=\"display:flex; flex-direction: column; gap: 0.5rem;\">\n" +
                    "                    <div><strong>Điểm đi</strong>: " + ticketResponse.getFromStation() + "</div>\n" +
                    "                    <div><strong>Điểm đến</strong>: " + ticketResponse.getToStation() + "</div>\n" +
                    "                </div>\n" +
                    "                <div style=\"display:flex; flex-direction: column; gap: 0.5rem;\">\n" +
                    "                    <div><strong>Ngày đi</strong>: " + ticketResponse.getStartDate() + "</div>\n" +
                    "                    <div><strong>Ngày đến</strong>: " + ticketResponse.getEndDate() + "</div>\n" +
                    "                    <div><strong>Tên</strong>: " + ticketResponse.getCustomerName() + "</div>\n" +
                    "                </div>\n" +
                    "            </div>\n" +
                    "            <div>\n" +
                    "            </div>\n" +
                    "        </div>\n" +
                    "    </div>\n"

            );


        }


        return sb.toString();
    }
}
