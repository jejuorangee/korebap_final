package com.korebap.app.view.payment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.korebap.app.biz.payment.PaymentDTO;
import com.korebap.app.biz.payment.PaymentService;
import com.korebap.app.view.util.LoginCheck;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class UpdatePaymentAction {

    @Autowired
    private PaymentService paymentService;

    @RequestMapping(value = "/updatePayment.do", method = RequestMethod.GET)
    public ModelAndView updatePayment(HttpServletRequest request,
    								HttpServletResponse response,
    								ModelAndView modelAndView,
    								PaymentDTO paymentDTO) {
        System.out.println("UpdatePaymentAction 시작");

        String login_member_id = LoginCheck.loginCheck(request);

        if (login_member_id.equals("")) {
            System.out.println("로그인 세션 없음");
            modelAndView.addObject("msg", "로그인이 필요한 서비스입니다.");
            modelAndView.addObject("path", "loginPage.do");
            modelAndView.setViewName("info"); // info.jsp
            return modelAndView;
        }

        int payment_num = Integer.parseInt(request.getParameter("payment_num"));

        // 데이터 로그
        System.out.println("payment_num : " + payment_num);

        paymentDTO.setPayment_num(payment_num);
        paymentDTO.setPayment_status("결제취소");

        boolean flag = paymentService.update(paymentDTO);

        if (flag) {
            System.out.println("UpdatePaymentAction 로그 : 결제 상태 업데이트 성공");
            modelAndView.addObject("msg", "결제가 취소가 완료 되었습니다.");
            modelAndView.addObject("path", "myReservationListPage.do");
            modelAndView.setViewName("info"); // info.jsp
        } else {
            System.out.println("UpdatePaymentAction 로그 : 결제 상태 업데이트 실패");
            modelAndView.addObject("msg", "결제 취소에 실패 했습니다. 다시 시도해주세요.");
            modelAndView.addObject("path", "main.do");
            modelAndView.setViewName("info"); // info.jsp
        }

        System.out.println("UpdatePaymentAction 끝");
        return modelAndView;
    }
}

//package com.korebap.app.view.payment;
//
//import com.korebap.app.biz.payment.paymentService;
//import com.korebap.app.biz.payment.PaymentDTO;
//import com.korebap.app.view.common.Action;
//import com.korebap.app.view.common.ActionForward;
//import com.korebap.app.view.util.LoginCheck;
//
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
//public class UpdatePaymentAction implements Action {
//
//	@Override
//	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
//		System.out.println("UpdatePaymentAction 시작");
//		
//		String login_member_id = LoginCheck.loginCheck(request);
//		ActionForward forward=new ActionForward();
//		if(login_member_id.equals("")) {
//			System.out.println("로그인 세션 없음");
//			request.setAttribute("msg", "로그인이 필요한 서비스입니다.");
//			request.setAttribute("path", "loginPage.do");
//			
//			forward.setPath("info.jsp");
//			forward.setRedirect(false);
//			return forward;
//		}
//		
//		int payment_num = Integer.parseInt(request.getParameter("payment_num"));
//		
//		//데이터 로그
//		System.out.println("payment_num : "+payment_num);
//		
//		PaymentDTO paymentDTO = new PaymentDTO();
//		paymentDTO.setPayment_num(payment_num);
//		paymentDTO.setPayment_status("결제취소");
//		
//		paymentService paymentService = new paymentService();
//		boolean flag = paymentService.update(paymentDTO);
//		
//		if(flag) {
//			System.out.println("UpdatePaymentAction 로그 : 결제 상태 업데이트 성공");
//			request.setAttribute("msg", "결제가 취소가 완료 되었습니다.");
//			request.setAttribute("path", "myReservationListPage.do");
//			forward.setPath("info.jsp");
//			forward.setRedirect(false);
//		}
//		else {
//			System.out.println("UpdatePaymentAction 로그 : 결제 상태 업데이트 실패");
//			request.setAttribute("msg", "결제 취소에 실패 했습니다. 다시 시도해주세요.");
//			request.setAttribute("path", "main.do");
//			forward.setPath("info.jsp");
//			forward.setRedirect(false);
//		}
//		
//		System.out.println("UpdatePaymentAction 끝");
//		return forward;
//	}
//
//}
