package com.example.vn_railway.controller.payment;

import com.example.vn_railway.common.GMailSender;
import com.example.vn_railway.common.QRGenerate;
import com.example.vn_railway.config.vn_pay.VNPayConfig;
import com.example.vn_railway.dto.payment.VNPayResponse;
import com.example.vn_railway.dto.train_dto.ISeatResponse;
import com.example.vn_railway.dto.train_dto.SeatPayload;
import com.example.vn_railway.dto.train_dto.TicketResponse;
import com.example.vn_railway.service.trip.ISeatService;
import com.example.vn_railway.service.trip.ITicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ScheduledFuture;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
@CrossOrigin("*")
public class PaymentController {
    private final ISeatService seatService;
    private final ITicketService ticketService;
    private final Map<SeatPayload, ScheduledFuture<?>> futureMap;
    private final GMailSender mailSender;
    @GetMapping("/vn-pay/create/{userName}")
    public ResponseEntity<?> createPayment(@PathVariable(value = "userName", required = false) String userName) throws UnsupportedEncodingException {

//        String orderType = "other";
//        long amount = Integer.parseInt(req.getParameter("amount"))*100;
//        String bankCode = req.getParameter("bankCode");
        List<ISeatResponse> seatResponseList = seatService.getAllSeatByUserName(userName);
        if (seatResponseList == null || seatResponseList.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        long price = (long) seatResponseList.stream()
                .mapToDouble(seat -> seat.getPrice() * seat.getTotalDistance())
                .sum();

        String vnp_TxnRef = VNPayConfig.getRandomNumber(8);

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", VNPayConfig.vnp_Version);
        vnp_Params.put("vnp_Command", VNPayConfig.vnp_Command);
        vnp_Params.put("vnp_TmnCode", VNPayConfig.vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(price * 100));
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_BankCode", "NCB");
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", userName);
        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_OrderType", "190001");
        vnp_Params.put("vnp_IpAddr", "0:0:0:0:0:0:0:1");
        vnp_Params.put("vnp_ReturnUrl", VNPayConfig.vnp_ReturnUrl);


        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = VNPayConfig.hmacSHA512(VNPayConfig.secretKey, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = VNPayConfig.vnp_PayUrl + "?" + queryUrl;


        VNPayResponse vnPayResponse = new VNPayResponse("OK", "Successfully", paymentUrl);

        return ResponseEntity.ok(vnPayResponse);
    }

    @GetMapping("/pay-success/{userName}")
    public ResponseEntity<?> handlePaymentSuccess(@PathVariable(value = "userName", required = false) String userName,
                                                  @RequestParam(value = "responseCode", required = false) String responseCode) {
        // Check param
        if (responseCode == null || !responseCode.equals("00")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Thanh toán không thành công");
        }

        // get data in temporary
        List<ISeatResponse> seatResponseList = seatService.getAllSeatByUserName(userName);
        if (seatResponseList == null || seatResponseList.size() == 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("NOT FOUND");
        }

        List<TicketResponse> ticketResponseList = new ArrayList<>();
        // create qr code and add to ticket response list
        for (ISeatResponse seatResponse : seatResponseList) {
            TicketResponse ticketResponse = ticketService.createTicket(seatResponse);
            ticketResponse.setBase64StringQRCode(
                    QRGenerate.generateQRbyInformation(ticketResponse, 250, 250));
            ticketResponseList.add(ticketResponse);
        }


        // Gửi mail
        StringBuilder sb = new StringBuilder("Cảm ơn bạn đã sử dụng dịch vụ của Công ty cổ phần Đường Sắt Việt Nam. Đây là vé của bạn, xin cảm ơn");
        String result = mailSender.generateHTMLForMail(ticketResponseList);
        mailSender.sendEmail(sb.append(result),ticketResponseList.get(0).getMail());


        return ResponseEntity.ok(ticketResponseList);
    }
}
