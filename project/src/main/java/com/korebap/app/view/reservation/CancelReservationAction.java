package com.korebap.app.view.reservation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.korebap.app.biz.payment.PaymentDTO;
import com.korebap.app.biz.payment.PaymentService;
import com.korebap.app.biz.reservation.ReservationDTO;
import com.korebap.app.biz.reservation.ReservationService;
import com.korebap.app.view.util.LoginCheck;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class CancelReservationAction {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private PaymentService paymentService;

    @RequestMapping(value = "/cancelReservation.do", method = RequestMethod.GET)
    public ModelAndView cancelReservation(HttpServletRequest request,
    									HttpServletResponse response,
    									ModelAndView modelAndView,
    									ReservationDTO reservationDTO) {
        // 예약 취소
        System.out.println("CancelReservationAction 시작");

        String login_member_id = LoginCheck.loginCheck(request);

        if (login_member_id.equals("")) {
            System.out.println("로그인 세션 없음");
            modelAndView.addObject("msg", "로그인이 필요한 서비스입니다.");
            modelAndView.addObject("path", "loginPage.do");
            modelAndView.setViewName("info"); // info.jsp
            return modelAndView;
        }

        int reservation_num = Integer.parseInt(request.getParameter("reservation_num"));

        // 데이터 로그
        System.out.println("reservation_num : " + reservation_num);

        reservationDTO.setReservation_num(reservation_num);
        reservationDTO.setReservation_condition("RESERVATION_SELECTONE");

        reservationDTO = reservationService.selectOne(reservationDTO);
        System.out.println(reservationDTO);
        boolean flag = false;

        if (reservationDTO != null) {
            reservationDTO.setReservation_status("예약취소");
            flag = reservationService.update(reservationDTO);
        } else {
            System.out.println("예약 내역 없음");
            modelAndView.addObject("msg", "예약내역을 찾을 수 없습니다. 다시 시도해주세요.");
            modelAndView.addObject("path", "myReservationListPage.do");
            modelAndView.setViewName("info"); // info.jsp
            return modelAndView;
        }

        System.out.println("결제 번호 : " + reservationDTO.getReservation_payment_num());
        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setPayment_num(reservationDTO.getReservation_payment_num());
        paymentDTO.setPayment_condition("SELECT_BY_PAYMENT_NUM");

        paymentDTO = paymentService.selectOne(paymentDTO);
        System.out.println(paymentDTO);

        if (flag) {
            System.out.println("CancelReservationAction 로그 : 예약 취소 성공");
            modelAndView.setViewName("redirect:cancelPayment.do?merchant_uid=" + paymentDTO.getMerchant_uid());
        } else {
            System.out.println("CancelReservationAction 로그 : 예약 취소 실패");
            modelAndView.addObject("msg", "예약 취소에 실패했습니다. 다시 시도해주세요.");
            modelAndView.addObject("path", "main.do");
            modelAndView.setViewName("info"); // info.jsp
        }

        System.out.println("CancelReservationAction 끝");
        return modelAndView;
    }
}

//package com.korebap.app.view.reservation;
//
//import com.korebap.app.biz.payment.paymentService;
//import com.korebap.app.biz.payment.PaymentDTO;
//import com.korebap.app.biz.reservation.reservationService;
//import com.korebap.app.biz.reservation.ReservationDTO;
//import com.korebap.app.view.common.Action;
//import com.korebap.app.view.common.ActionForward;
//import com.korebap.app.view.util.LoginCheck;
//
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
//public class CancelReservationAction implements Action {
//
//	@Override
//	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
//		// 예약 취소
//		System.out.println("CancelReservationAction 시작");
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
//		int reservation_num = Integer.parseInt(request.getParameter("reservation_num"));
//		//String merchant_uid = request.getParameter("merchant_uid");
//		
//		
//		//데이터 로그
//		System.out.println("reservation_num : "+reservation_num);
//		
//		ReservationDTO reservationDTO = new ReservationDTO();
//		reservationDTO.setReservation_num(reservation_num);
//		reservationDTO.setReservation_condition("RESERVATION_SELECTONE");
//		
//		reservationService reservationService = new reservationService();
//		reservationDTO = reservationService.selectOne(reservationDTO);
//		System.out.println(reservationDTO);
//		boolean flag = false;
//		if(reservationDTO != null) {
//			reservationDTO.setReservation_status("예약취소");
//			flag = reservationService.update(reservationDTO);
//		}
//		else {
//			System.out.println("예약 내역 없음");
//			request.setAttribute("msg", "예약내역을 찾을 수 없습니다. 다시 시도해주세요.");
//			request.setAttribute("path", "myReservationListPage.do");
//			
//			forward.setPath("info.jsp");
//			forward.setRedirect(false);
//			return forward;
//		}
//		System.out.println("결제 번호 : "+reservationDTO.getReservation_payment_num());
//		PaymentDTO paymentDTO = new PaymentDTO();
//		paymentDTO.setPayment_num(reservationDTO.getReservation_payment_num());
//		paymentDTO.setPayment_condition("SELECT_BY_PAYMENT_NUM");
//		
//		paymentService paymentService = new paymentService();
//		paymentDTO = paymentService.selectOne(paymentDTO);
//		System.out.println(paymentDTO);
//		
//		if(flag) {
//			System.out.println("CancelReservationAction 로그 : 예약 취소 성공");
//			forward.setPath("cancelPayment.do?merchant_uid="+paymentDTO.getMerchant_uid());
//			forward.setRedirect(true);
//		}
//		else {
//			System.out.println("CancelReservationAction 로그 : 예약 취소 실패");
//			request.setAttribute("msg", "예약 취소에 실패했습니다. 다시 시도해주세요.");
//			request.setAttribute("path", "main.do");
//			forward.setPath("info.jsp");
//			forward.setRedirect(false);
//		}
//		
//		System.out.println("CancelReservationAction 끝");
//		return forward;
//	}
//
//}
//// 예약 취소로 업데이트
//// 결제 취소