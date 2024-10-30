package com.korebap.app.view.page;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.korebap.app.biz.reservation.ReservationDTO;
import com.korebap.app.biz.reservation.ReservationService;
import com.korebap.app.view.util.LoginCheck;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class MyReservationListPageAction {

    @Autowired
    private ReservationService reservationService;

    @GetMapping("/myReservationListPage.do")
    public String execute(HttpServletRequest request, Model model, ReservationDTO reservationDTO) {
        System.out.println("ReservationListPageAction 시작");

        String login_member_id = LoginCheck.loginCheck(request);
        if (login_member_id == null || login_member_id.isEmpty()) {
            System.out.println("로그인 세션 없음");
            model.addAttribute("msg", "로그인이 필요한 서비스입니다.");
            model.addAttribute("path", "loginPage.do");
            return "info"; // info.jsp로 이동 (Thymeleaf를 사용하는 경우 info.html)
        }
        reservationDTO.setReservation_condition("RESERVATION_SELECTALL");
        reservationDTO.setReservation_member_id(login_member_id);

        // 예약 목록을 가져옵니다.
        List<ReservationDTO> myReservationList = reservationService.selectAll(reservationDTO);
        model.addAttribute("myReservationList", myReservationList);

        System.out.println("ReservationListPageAction 끝");
        return "myReservationList"; // myReservationList.jsp로 이동 (Thymeleaf를 사용하는 경우 myReservationList.html)
    }
}


//package com.korebap.app.view.page;
//
//import java.util.ArrayList;
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
//public class MyReservationListPageActionz {
//
//	@Override
//	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
//		System.out.println("ReservationListPageAction 시작");
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
//		ReservationDTO reservationDTO = new ReservationDTO();
//		reservationDTO.setReservation_member_id(login_member_id);
//		
//		ReservationDAO reservationDAO = new ReservationDAO();
//		ArrayList<ReservationDTO> myReservationList = reservationDAO.selectAll(reservationDTO);
//		
//		request.setAttribute("myReservationList", myReservationList);
//		
//		forward.setPath("myReservationList.jsp");
//		forward.setRedirect(false);
//		
//		System.out.println("ReservationListPageAction 끝");
//		return forward;
//	}
//
//}
