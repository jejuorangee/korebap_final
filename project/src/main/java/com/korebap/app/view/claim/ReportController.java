//package com.korebap.app.view.claim;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//
//import com.korebap.app.biz.payment.PaymentDTO;
//import com.korebap.app.biz.payment.PaymentService;
//import com.korebap.app.biz.reservation.ReservationDTO;
//import com.korebap.app.biz.reservation.ReservationService;
//
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpSession;
//
//@Controller
//public class ReportController {
//
//    @Autowired
//    private ReservationService reservationService; // 예약 정보를 처리하기 위한 DAO
//
//    @Autowired
//    private PaymentService paymentService; // 결제 정보를 처리하기 위한 DAO
//
//    /**
//     * 예약 취소 요청을 처리하는 메서드
//     * @param request HTTP 요청 객체
//     * @param session HTTP 세션 객체
//     * @param model 뷰에 데이터를 전달하기 위한 모델 객체
//     * @param reservationDTO 예약 데이터 전송 객체
//     * @param paymentDTO 결제 데이터 전송 객체
//     * @return 처리 결과에 따라 이동할 뷰의 이름
//     */
//    @RequestMapping(value = "/cancelReservation.do", method = RequestMethod.POST)
//    public String cancelReservation(HttpServletRequest request, HttpSession session, Model model, ReservationDTO reservationDTO, PaymentDTO paymentDTO) {
//        System.out.println("com.koreait.app.view.cliam.ReportController_cancelReservation 시작");
//
//        // 로그인 여부 확인
//        String loginMemberId = (String) session.getAttribute("loggedInUser");
//        if (loginMemberId == null) {
//            System.out.println("로그인 세션 없음");
//            model.addAttribute("msg", "로그인이 필요한 서비스입니다."); // 로그인 필요 메시지 설정
//            model.addAttribute("path", "loginPage.do"); // 리다이렉트할 경로 설정
//            return "info"; // info.jsp로 이동
//        }
//
//        // 요청에서 예약 번호를 가져옴
//        int reservationNum = Integer.parseInt(request.getParameter("reservation_num"));
//        System.out.println("reservation_num : " + reservationNum);
//
//        // 예약 DTO에 예약 번호 및 조건 설정
//        reservationDTO.setReservation_num(reservationNum);
//        reservationDTO.setReservation_condition("RESERVATION_SELECTONE");
//
//        // 예약 정보 조회
//        reservationDTO = reservationService.selectOne(reservationDTO);
//        System.out.println(reservationDTO);
//
//        // 예약이 존재하는 경우
//        if (reservationDTO != null) {
//            // 예약 상태를 '예약취소'로 업데이트
//            reservationDTO.setReservation_status("예약취소");
//            boolean flag = reservationService.update(reservationDTO); // 예약 정보 업데이트
//
//            // 예약 취소 성공
//            if (flag) {
//                System.out.println("CancelReservationAction 로그 : 예약 취소 성공");
//                // 결제 정보 조회를 위한 설정
//                paymentDTO.setPayment_num(reservationDTO.getReservation_payment_num());
//                paymentDTO.setPayment_condition("SELECT_BY_PAYMENT_NUM");
//
//                // 결제 정보 조회
//                paymentDTO = paymentService.selectOne(paymentDTO);
//                System.out.println(paymentDTO);
//
//                // 결제 취소 페이지로 리다이렉트
//                return "redirect:cancelPayment.do?merchant_uid=" + paymentDTO.getMerchant_uid();
//            } else {
//                // 예약 취소 실패
//                System.out.println("CancelReservationAction 로그 : 예약 취소 실패");
//                model.addAttribute("msg", "예약 취소에 실패했습니다. 다시 시도해주세요."); // 실패 메시지 설정
//                model.addAttribute("path", "main.do"); // 리다이렉트할 경로 설정
//                return "info"; // info.jsp로 이동
//            }
//        } else {
//            // 예약 내역이 없는 경우
//            System.out.println("예약 내역 없음");
//            model.addAttribute("msg", "예약내역을 찾을 수 없습니다. 다시 시도해주세요."); // 내역 없음 메시지 설정
//            model.addAttribute("path", "myReservationListPage.do"); // 리다이렉트할 경로 설정
//            return "info"; // info.jsp로 이동
//        }
//    }
//}
