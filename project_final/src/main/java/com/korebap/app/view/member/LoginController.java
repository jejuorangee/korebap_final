package com.korebap.app.view.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.korebap.app.biz.member.MemberDTO;
import com.korebap.app.biz.member.MemberService;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {
	@Autowired
	private MemberService memberService;
	
	// 오버로딩으로 페이지 이동 만들기
	@RequestMapping(value="/login.do",method=RequestMethod.GET)
	public String login() {
		System.out.println("LoginController.java login() 시작");
		System.out.println("LoginController.java login() 끝");
		return "redirect:login.jsp";
	}
	
	@RequestMapping(value="/login.do", method=RequestMethod.POST)
	public String login(HttpSession session, MemberDTO memberDTO, Model model) {
		System.out.println("LoginController.java login() 시작");
		
		String login_member = (String)session.getAttribute("member_id");
		
		if(session != null || login_member != null) {
			model.addAttribute("msg", "이미 로그인된 사용자 입니다.");
			model.addAttribute("path", "main.do");
		}
		
		// 데이터 로그
		System.out.println("LoginController.java login() member_id : [ "+memberDTO.getMember_id()+" ]");
		System.out.println("LoginController.java login() member_password : [ "+memberDTO.getMember_password()+" ]");
		
		memberDTO.setMember_condition("LOGIN");
		memberDTO = memberService.selectOne(memberDTO);
		
		if(memberDTO != null) {
			System.out.println("LoginController.java login() 로그인 성공");
			session.setAttribute("member_id", memberDTO.getMember_id());
			session.setAttribute("member_role", memberDTO.getMember_role());
			return "redirect:main.do";
		}
		else {
			System.out.println("LoginController.java login() 로그인 실패");
			model.addAttribute("msg", "아이디 또는 비밀번호를 확인해 주세요.");
			model.addAttribute("path", "login.jsp");
		}
		
		System.out.println("LoginController.java login() 끝");
		return "info";
	}
	
	@RequestMapping("/logout.do")
	public String logout(HttpSession session, Model model) {
		System.out.println("LoginController.java logout() 시작");
		
		if(session == null) {
			model.addAttribute("msg", "이미 로그아웃된 사용자 입니다.");
			model.addAttribute("path", "login.do");
		}
		
		session.removeAttribute("member_id");
		session.removeAttribute("member_role");
		
		model.addAttribute("msg", "로그아웃 완료");
		model.addAttribute("path", "login.do");
		
		System.out.println("LoginController.java logout() 끝");
		return "info";
	}
}
