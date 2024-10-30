//package com.korebap.app.view.member;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import com.korebap.app.view.common.ActionForward;
//
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//
//@Controller
//public class LogoutAction {
//
//	@RequestMapping("/logout.do")
//	public String logout(HttpSession session) { // 로그아웃 기능을 수행하는 메서드
//		System.out.println("LogoutAction 시작");
//
//		session.removeAttribute("member_id");
//		session.removeAttribute("member_role");
//		System.out.println("LogoutAction 끝");
//
//		return "redirect:main.do";
//	}
//
//}
