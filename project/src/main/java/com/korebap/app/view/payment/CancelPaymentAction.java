package com.korebap.app.view.payment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.korebap.app.biz.payment.PaymentDTO;
import com.korebap.app.biz.payment.PaymentInfo;
import com.korebap.app.biz.payment.PaymentService;
import com.korebap.app.view.util.LoginCheck;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@Controller
public class CancelPaymentAction {

    @Autowired
    private PaymentService paymentService;
    
    @Autowired
    private HttpSession session;

    @RequestMapping(value = "/cancelPayment.do", method = RequestMethod.GET)
    public ModelAndView cancelPayment(HttpServletRequest request,
    								HttpServletResponse response,
    								ModelAndView modelAndView,
    								PaymentDTO paymentDTO,
    								PaymentInfo paymentInfo) {
        System.out.println("cancelPaymentAction 시작");
        String login_member_id = LoginCheck.loginCheck(request);

        if (login_member_id.equals("")) {
            System.out.println("cancelPaymentAction 로그 : 로그인 세션 없음");
            modelAndView.addObject("msg", "로그인이 필요한 서비스입니다.");
            modelAndView.addObject("path", "loginPage.do");
            modelAndView.setViewName("info"); // info.jsp
            return modelAndView;
        }

        String merchant_uid = request.getParameter("merchant_uid");

        // 데이터 로그
        System.out.println("merchant_uid : " + merchant_uid);

        paymentDTO.setMerchant_uid(merchant_uid);
        paymentDTO.setPayment_condition("SELECT_BY_MERCHANT_UID");

        paymentDTO = paymentService.selectOne(paymentDTO);

        if (paymentDTO != null) {
            System.out.println("cancelPaymentAction 로그 : 결제 내역 있음");
            paymentInfo = PaymentUtil.portOne_code();
            paymentInfo.setMerchant_uid(merchant_uid);
            paymentInfo.setAmount(paymentDTO.getPayment_price());
        } else {
            System.out.println("cancelPaymentAction 로그 : 결제 내역 없음");
            modelAndView.addObject("msg", "결제 내역을 찾을 수 없습니다.");
            modelAndView.addObject("path", "main.do");
            modelAndView.setViewName("info"); // info.jsp
            return modelAndView;
        }

        boolean flag = PaymentUtil.cancelPayment(paymentInfo);

        if (flag) {
            System.out.println("cancelPaymentAction 로그 : 결제 취소 성공");
            modelAndView.setViewName("redirect:updatePayment.do?payment_num=" + paymentDTO.getPayment_num());
        } else {
            System.out.println("cancelPaymentAction 로그 : 결제 취소 실패");
            modelAndView.addObject("msg", "결제 취소에 실패 했습니다. 다시 시도해주세요.");
            modelAndView.addObject("path", "main.do");
            modelAndView.setViewName("info"); // info.jsp
        }

        System.out.println("cancelPaymentAction 끝");
        return modelAndView;
    }
}


//public class CancelPaymentAction implements Action {
//
//	@Override
//	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
//		System.out.println("cancelPaymentAction 시작");
//		
//		String login_member_id = LoginCheck.loginCheck(request);
//		ActionForward forward=new ActionForward();
//		if(login_member_id.equals("")) {
//			System.out.println("cancelPaymentAction 로그 : 로그인 세션 없음");
//			request.setAttribute("msg", "로그인이 필요한 서비스입니다.");
//			request.setAttribute("path", "loginPage.do");
//			
//			forward.setPath("info.jsp");
//			forward.setRedirect(false);
//			return forward;
//		}
//		
//		String merchant_uid = request.getParameter("merchant_uid");
//		
//		//데이터 로그
//		System.out.println("merchant_uid : "+merchant_uid);
//		
//		PaymentDTO paymentDTO = new PaymentDTO();
//		//paymentDTO.setPayment_order_num(merchant_uid);
//		paymentDTO.setMerchant_uid(merchant_uid);
//		paymentDTO.setPayment_condition("SELECT_BY_MERCHANT_UID");
//		
//		paymentService paymentService = new paymentService();
//		paymentDTO = paymentService.selectOne(paymentDTO);
//		
//		PaymentInfo paymentInfo = new PaymentInfo();
//		if(paymentDTO != null) {
//			System.out.println("cancelPaymentAction 로그 : 결제 내역 있음");
//			paymentInfo = PaymentUtil.portOne_code();
//			paymentInfo.setMerchant_uid(merchant_uid);
//			paymentInfo.setAmount(paymentDTO.getPayment_price());
//		}
//		else {
//			System.out.println("cancelPaymentAction 로그 : 결제 내역 없음");
//			request.setAttribute("msg", "결제 내역을 찾을 수 없습니다.");
//			request.setAttribute("path", "main.do");
//			forward.setPath("info.jsp");
//			forward.setRedirect(false);
//			return forward;
//		}
//		
//		
//		boolean flag = PaymentUtil.cancelPayment(paymentInfo);
//		
//		if(flag) {
//			System.out.println("cancelPaymentAction 로그 : 결제 취소 성공");
//			forward.setPath("updatePayment.do?payment_num="+paymentDTO.getPayment_num());
//			forward.setRedirect(true);
//		}
//		else {
//			System.out.println("cancelPaymentAction 로그 : 결제 취소 실패");
//			request.setAttribute("msg", "결제 취소에 실패 했습니다. 다시 시도해주세요.");
//			request.setAttribute("path", "main.do");
//			forward.setPath("info.jsp");
//			forward.setRedirect(false);
//		}
//
//		System.out.println("cancelPaymentAction 끝");
//		return forward;
//	}
//
//}