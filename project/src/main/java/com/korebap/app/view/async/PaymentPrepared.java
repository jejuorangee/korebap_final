package com.korebap.app.view.async;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.korebap.app.biz.payment.PaymentInfo;
import com.korebap.app.view.payment.PaymentUtil;

@RestController
public class PaymentPrepared {

    @RequestMapping(value="/payment/prepared.do", method=RequestMethod.POST, produces = "application/json")
    public @ResponseBody int preparePayment(@RequestBody PaymentInfo paymentInfo) {
        
        System.out.println("PaymentPrepared : POST 요청 도착");
        System.out.println("merchant_uid: " + paymentInfo.getMerchant_uid());
        String mid = paymentInfo.getMerchant_uid();
        // 토큰 발행
        paymentInfo = PaymentUtil.portOne_code();
        System.out.println("portone token: " + paymentInfo.getToken());

        paymentInfo.setMerchant_uid(mid);

        paymentInfo = PaymentUtil.prepareResult(paymentInfo);
        
        //int amount = paymentInfo.getAmount() <= 0 ? paymentInfo.getAmount() : 0
        // JSON 응답 생성
        int jsonResponse;
        if (paymentInfo.getAmount() > 0) {
            jsonResponse =  paymentInfo.getAmount();
        } else {
            jsonResponse = 0;
        }

        System.err.println("JSON 응답: " + jsonResponse); // JSON 응답을 로그에 출력

        return jsonResponse;
    }
}


//package com.korebap.app.view.async;
//
//import java.io.IOException;
//import java.io.PrintWriter;
//
//import com.korebap.app.biz.payment.PaymentInfo;
//import com.korebap.app.view.payment.PaymentUtil;
//
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
//@WebServlet("/payment/prepared")
//public class PaymentPrepared extends HttpServlet {
//	private static final long serialVersionUID = 1L;
//       
//    public PaymentPrepared() {
//        super();
//    }
//
//	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		System.out.println("PaymentPrepared : GET 요청 도착");
//	}
//
//	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		System.out.println("PaymentPrepared : POST 요청 도착");
//		String merchant_uid = request.getParameter("merchant_uid");
//		System.out.println(merchant_uid);
//		
//		// 토큰 발행
//		PaymentInfo paymentInfo = PaymentUtil.portOne_code();
//		System.out.println("portone token : "+paymentInfo.getToken());
//		
//		paymentInfo.setMerchant_uid(merchant_uid);
//		
//		paymentInfo = PaymentUtil.prepareReult(paymentInfo);
//		
//		response.setContentType("application/json"); // JSON으로 응답 설정
//	    PrintWriter out = response.getWriter();
//	    
//	    //System.out.println(paymentInfo.getAmount());
//	    String jsonResponse;
//	    
//	    if (paymentInfo.getAmount() > 0) {
//	        jsonResponse = "{\"amountRes\": " + paymentInfo.getAmount() + "}";
//	        System.err.println("JSON 응답: " + jsonResponse); // JSON 응답을 로그에 출력
//	    } else {
//	        jsonResponse = "{\"amountRes\": 0}"; // JSON 형식으로 0 반환
//	        System.err.println("JSON 응답: " + jsonResponse); // JSON 응답을 로그에 출력
//	    }
//	    
//	    out.print(jsonResponse);
//	    out.flush();
//	}
//}
