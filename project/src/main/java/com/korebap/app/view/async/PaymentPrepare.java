package com.korebap.app.view.async;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.korebap.app.biz.payment.PaymentInfo;
import com.korebap.app.view.payment.PaymentUtil;

@RestController
public class PaymentPrepare {

	@RequestMapping(value="/payment/prepare.do", method=RequestMethod.POST)
    public ResponseEntity<String> preparePayment(
            @RequestParam("merchant_uid") String merchantUid,
            @RequestParam("amount") int amount) {
        
        System.out.println("PaymentPrepare : POST 요청 도착");
        System.out.println("merchant_uid: " + merchantUid);
        System.out.println("amount: " + amount);

        // 토큰 발행
        PaymentInfo paymentInfo = PaymentUtil.portOne_code();
        System.out.println("portone token: " + paymentInfo.getToken());

        paymentInfo.setMerchant_uid(merchantUid);
        paymentInfo.setAmount(amount);

        boolean flag = PaymentUtil.prepare(paymentInfo);
        System.out.println(flag);

        if (flag) {
            return ResponseEntity.ok("true");
        } else {
            return ResponseEntity.ok("false");
        }
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
//@WebServlet("/payment/prepare")
//public class PaymentPrepare extends HttpServlet {
//	private static final long serialVersionUID = 1L;
//       
//    public PaymentPrepare() {
//        super();
//    }
//
//	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		System.out.println("PaymentPrepare : GET 요청 도착");
//	}
//
//	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		System.out.println("PaymentPrepare : POST 요청 도착");
//		String merchant_uid = request.getParameter("merchant_uid");
//		int amount = Integer.parseInt(request.getParameter("amount"));
//		System.out.println(merchant_uid);
//		System.out.println(amount);
//		
//		// 토큰 발행
//		PaymentInfo paymentInfo = PaymentUtil.portOne_code();
//		System.out.println("portone token : "+paymentInfo.getToken());
//		
//		paymentInfo.setMerchant_uid(merchant_uid);
//		paymentInfo.setAmount(amount);
//		
//		boolean flag = PaymentUtil.prepare(paymentInfo);
//		
//		PrintWriter out = response.getWriter();
//		if(flag) {
//			out.print("true");
//		}
//		else {
//			out.print("false");
//		}
//	}
//
//}
