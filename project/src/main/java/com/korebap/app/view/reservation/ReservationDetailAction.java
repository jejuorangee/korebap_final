package com.korebap.app.view.reservation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.korebap.app.biz.reservation.ReservationDTO;
import com.korebap.app.biz.reservation.ReservationService;
import com.korebap.app.view.util.LoginCheck;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class ReservationDetailAction {

    @Autowired
    private ReservationService reservationService;

    @RequestMapping(value = "/reservationDetail.do", method = RequestMethod.GET)
    public ModelAndView reservationDetail(HttpServletRequest request,
    									HttpServletResponse response,
    									ModelAndView modelAndView,
    									ReservationDTO reservationDTO) {
        // 예약 상세 보기
        System.out.println("ReservationDetailAction 시작");
        
        String login_member_id = LoginCheck.loginCheck(request);

        if (login_member_id.equals("")) {
            System.out.println("로그인 세션 없음");
            modelAndView.addObject("msg", "로그인이 필요한 서비스입니다.");
            modelAndView.addObject("path", "loginPage.do");
            modelAndView.setViewName("info"); // info.jsp
            return modelAndView;
        }

        int reservation_num = Integer.parseInt(request.getParameter("reservation_num"));

        reservationDTO.setReservation_num(reservation_num);
        reservationDTO.setReservation_condition("RESERVATION_SELECTONE");

        reservationDTO = reservationService.selectOne(reservationDTO);

        if (reservationDTO != null) {
            System.out.println("ReservationDetailAction 로그 : 예약 조회 성공");
            modelAndView.addObject("reservation", reservationDTO);
            modelAndView.setViewName("reservationDetails"); // reservationDetails.jsp
        } else {
            System.out.println("ReservationDetailAction 로그 : 예약 조회 실패");
            modelAndView.addObject("msg", "예약 내역을 찾지 못했습니다. 다시 시도해주세요.");
            modelAndView.addObject("path", "reservationList.do");
            modelAndView.setViewName("info"); // info.jsp
        }

        System.out.println("ReservationDetailAction 끝");
        return modelAndView;
    }
}


//package com.korebap.app.view.reservation;
//
//import com.korebap.app.biz.reservation.ReservationDAO;
//import com.korebap.app.biz.reservation.ReservationDTO;
//import com.korebap.app.view.common.Action;
//import com.korebap.app.view.common.ActionForward;
//import com.korebap.app.view.util.LoginCheck;
//
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
//public class ReservationDetailAction implements Action {
//
//	@Override
//	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
//		// 예약 상세 보기
//		System.out.println("ReservationDetailAction 시작");
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
//		
//		ReservationDTO reservationDTO = new ReservationDTO();
//		reservationDTO.setReservation_num(reservation_num);
//		reservationDTO.setReservation_condition("RESERVATION_SELECTONE");
//		
//		ReservationDAO reservationDAO = new ReservationDAO();
//		reservationDTO = reservationDAO.selectOne(reservationDTO);
//		
//		if(reservationDTO != null) {
//			System.out.println("ReservationDetailAction 로그 : 예약 조회 성공");
//			request.setAttribute("reservation", reservationDTO);
//			forward.setPath("reservationDetails.jsp");
//			forward.setRedirect(false);
//		}
//		else {
//			System.out.println("ReservationDetailAction 로그 : 예약 조회 실패");
//			request.setAttribute("msg", "예약 내역을 찾지 못했습니다. 다시 시도해주세요.");
//			request.setAttribute("path", "reservationList.do");
//			forward.setPath("info.jsp");
//			forward.setRedirect(false);
//		}
//		System.out.println("ReservationDetailAction 끝");
//		return forward;
//	}
//
//}
