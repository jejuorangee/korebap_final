package com.korebap.app.view.async;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.korebap.app.biz.payment.PaymentDTO;
import com.korebap.app.biz.payment.PaymentInfo;
import com.korebap.app.biz.payment.PaymentService;
import com.korebap.app.view.payment.PaymentUtil;

import java.util.Map;

@RestController
public class PaymentValidate {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/payment/vaildate.do")
    public ResponseEntity<String> validatePayment(@RequestBody Map<String, Object> payload) {
        String impUid = (String) payload.get("imp_uid");
        int productNum = Integer.parseInt((String) payload.get("product_num")); // product_num을 String으로 받음

        System.out.println("POST 요청 도착");
        System.out.println("imp_uid: " + impUid);

        PaymentInfo paymentInfo = PaymentUtil.portOne_code();
        System.out.println("portone token: " + paymentInfo.getToken());

        paymentInfo.setImp_uid(impUid);
        paymentInfo = PaymentUtil.paymentTest(paymentInfo);
        String responseBody = paymentInfo.getResponse().body();
        System.out.println("응답 객체: " + responseBody);

        JSONParser parser = new JSONParser();
        PaymentDTO paymentDTO = new PaymentDTO();
        boolean flag = false;
        int amountRes = 0;

        try {
            JSONObject jsonObject = (JSONObject) parser.parse(responseBody);
            JSONObject responseObject = (JSONObject) jsonObject.get("response");

            if (responseObject != null) {
                Long amount = (Long) responseObject.get("amount");
                amountRes = amount.intValue(); // 결제 금액
                String buyerEmail = (String) responseObject.get("buyer_email");
                String impUid1 = (String) responseObject.get("imp_uid");
                String merchantUid = (String) responseObject.get("merchant_uid");
                String paymentMethod = (String) responseObject.get("pay_method");

                System.out.println("결제된 가격: " + amountRes);
                System.out.println("주문자 이메일: " + buyerEmail);
                System.out.println("결제 고유번호: " + impUid1);
                System.out.println("상점 고유번호: " + merchantUid);
                System.out.println("결제 방법: " + paymentMethod);

                paymentDTO.setPayment_member_id(buyerEmail);
                paymentDTO.setPayment_product_num(productNum);
                paymentDTO.setPayment_order_num(impUid);
                paymentDTO.setMerchant_uid(merchantUid);
                paymentDTO.setPayment_price(amountRes);
                paymentDTO.setPayment_status("결제완료");
                paymentDTO.setPayment_method(paymentMethod);

                flag = paymentService.insert(paymentDTO);
            } else {
                System.out.println("Response 객체가 null입니다.");
                flag = false;
            }
        } catch (ParseException e) {
            System.err.println("JSON 파싱 오류: " + e.getMessage());
        }

        if (flag) {
            return ResponseEntity.ok(String.valueOf(amountRes));
        } else {
            return ResponseEntity.status(500).body("결제 처리 실패");
        }
    }
}




//package com.korebap.app.view.async;
//
//
//import java.io.IOException;
//import java.io.PrintWriter;
//
//import org.json.simple.JSONObject;
//import org.json.simple.parser.JSONParser;
//import org.json.simple.parser.ParseException;
//
//
//import com.korebap.app.biz.payment.paymentService;
//import com.korebap.app.biz.payment.PaymentDTO;
//import com.korebap.app.biz.payment.PaymentInfo;
//import com.korebap.app.view.payment.PaymentUtil;
//
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
//
//@WebServlet("/payment/vaildate")
//public class PaymentValidate extends HttpServlet {
//	private static final long serialVersionUID = 1L;
//
//	public PaymentValidate() {
//		super();
//	}
//
//	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		System.out.println("GET 요청 도착");
//		doPost(request, response);
//	}
//
//	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		System.out.println("POST 요청 도착");
//		String imp_uid = request.getParameter("imp_uid");
//		int product_num = Integer.parseInt(request.getParameter("product_num"));
//		//String merchant_uid = request.getParameter("merchant_uid");
//		//		ProductDTO productDTO = new ProductDTO();
//		//		productDTO.setProduct_num(product_num);
//		//		System.out.println(product_num);
//		//		ProductDAO productDAO = new ProductDAO();
//		//		productDTO = productDAO.selectOne(productDTO);
//		//		
//		//		PrintWriter out = response.getWriter();
//		//		if(productDTO!=null) {
//		//			out.print(productDTO.getProduct_amount());
//		//		}
//		//		else {
//		//			out.print(false);
//		//		}
//
//		PaymentInfo paymentInfo = PaymentUtil.portOne_code();
//		System.out.println("portone token : "+paymentInfo.getToken());
//		System.out.println(imp_uid);
//		
//		paymentInfo.setImp_uid(imp_uid);
//		
//		paymentInfo = PaymentUtil.paymentTest(paymentInfo);
//		String responseBody = paymentInfo.getResponse().body();
//		System.out.println("응답 객체: " + responseBody);
//		// JSON 파싱
//		JSONParser parser = new JSONParser();
//		PaymentDTO paymentDTO= null;
//		boolean flag = false;
//		int amount_res = 0;
//		try {
//			JSONObject jsonObject = (JSONObject) parser.parse(paymentInfo.getResponse().body());
//
//			// 'response' 객체 가져오기
//			JSONObject responseObject = (JSONObject) jsonObject.get("response");
//
//			if (responseObject != null) {
//				// 필요한 값 추출
//				System.out.println(product_num);
//				Long amount = (Long) responseObject.get("amount");
//				amount_res = amount.intValue(); // 결제 금액
//				String buyer_email = (String) responseObject.get("buyer_email");
//				String imp_uid1 = (String) responseObject.get("imp_uid");
//				String merchant_uid = (String) responseObject.get("merchant_uid");
//				//String receiptUrl = (String) responseObject.get("receipt_url");
//				//String cardName = (String) responseObject.get("card_name");
//				String payment_method = (String) responseObject.get("pay_method");
//
//				// 결과 출력
//				System.out.println("결제된 가격: " + amount_res);
//				System.out.println("주문자 이메일: " + buyer_email);
//				System.out.println("결제 고유번호: " + imp_uid1);
//				System.out.println("상점 고유번호: " + merchant_uid);
//				//System.out.println("영수증 URL: " + receiptUrl);
//				//System.out.println("카드 이름: " + cardName);
//				System.out.println("결제 방법: " + payment_method);
//				
//				paymentDTO = new PaymentDTO();
//				paymentDTO.setPayment_member_id(buyer_email);
//				paymentDTO.setPayment_product_num(product_num);
//				paymentDTO.setPayment_order_num(imp_uid);
//				paymentDTO.setMerchant_uid(merchant_uid);
//				paymentDTO.setPayment_price(amount_res);
//				paymentDTO.setPayment_status("결제완료");
//				paymentDTO.setPayment_method(payment_method);
//				
//				paymentService paymentService = new paymentService();
//				flag = paymentService.insert(paymentDTO);
//			} else {
//				System.out.println("Response 객체가 null입니다.");
//				flag = false;
//			}
//		} catch (ParseException e) {
//			System.err.println("JSON 파싱 오류: " + e.getMessage());
//		}
//		PrintWriter out = response.getWriter();
//		if(flag) {
//			out.print(amount_res);
//		}
//		else {
//			out.print(flag);
//		}
//		
//	}
//}
