package com.korebap.app.view.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.korebap.app.biz.member.MemberDAO;
import com.korebap.app.biz.member.MemberDTO;
import com.korebap.app.biz.member.MemberService;

import jakarta.servlet.http.HttpSession;

@Controller
public class MypageController {
	@Autowired
	private MemberService memberService;
	
	/////////////// 마이페이지 페이지 메서드 시작
	@RequestMapping("/mypage.do")
	public String mypage(HttpSession session, MemberDTO memberDTO, Model model) {
		System.out.println("MypageController.java mypage() 시작");
		String loginMember = (String)session.getAttribute("member_id");
		
		if(session == null || loginMember == null) {
			model.addAttribute("msg", "로그인이 필요한 서비스입니다.");
			model.addAttribute("path", "login.jsp");
			return "info";
		}
		memberDTO.setMember_id(loginMember);
		memberDTO.setMember_condition("MYPAGE");

		memberDTO = memberService.selectOne(memberDTO);

		if(memberDTO!=null) {
			String[] totalAddress = memberDTO.getMember_address().split("_");

			memberDTO.setMember_postcode(totalAddress[0]);
			memberDTO.setMember_address(totalAddress[1]);
			memberDTO.setMember_extraAddress(totalAddress[2]);
			memberDTO.setMember_detailAddress(totalAddress[3]);
			
			model.addAttribute("member", memberDTO);
		}
		else {
			model.addAttribute("msg", "서비스중 오류가 발생했습니다. 다시 시도해주세요.");
			model.addAttribute("path", "main.do");
			return "info";
		}
		System.out.println("MypageController.java mypage() 끝");
		return "mypage";
	}

	/////////////// 마이페이지 페이지 메서드 끝

	/////////////// 이름 / 닉네임 수정 시작
	@RequestMapping(value="/updateMypage.do", method=RequestMethod.POST)
	public String updateMypage(HttpSession session, MemberDTO memberDTO, Model model) {
		System.out.println("MypageController.java updateMypage() 시작");
		String loginMember = (String)session.getAttribute("member_id");

		if(session == null || loginMember == null) {
			model.addAttribute("msg", "로그인이 필요한 서비스입니다.");
			model.addAttribute("path", "login.jsp");
			return "info";
		}
		
		memberDTO.setMember_id(loginMember);
		memberDTO.setMember_condition("NAME");
		
		// 데이터 로그
		System.out.println("MypageController.java updateMypage() member_id : [ "+memberDTO.getMember_id()+" ]");
		System.out.println("MypageController.java updateMypage() member_name : [ "+memberDTO.getMember_name()+" ]");
		System.out.println("MypageController.java updateMypage() member_nickname : [ "+memberDTO.getMember_nickname()+" ]");
	
		boolean flag = memberService.update(memberDTO);
		
		model.addAttribute("path", "mypage.do");
		if(!flag) {
			System.out.println("MypageController.java updateMypage() 실패");
			model.addAttribute("msg", "회원정보 수정을 실패했습니다. 다시 시도해주세요.");
		}
		else {
			System.out.println("MypageController.java updateMypage() 성공");
			model.addAttribute("msg", "회원정보 수정을 완료했습니다.");
		}
		
		System.out.println("MypageController.java updateMypage() 끝");
		return "info";
	} 
	/////////////// 이름 / 닉네임 수정 끝
	
	
	/////////////// 비밀번호 수정 시작
	@RequestMapping(value="/updatePassword", method=RequestMethod.POST)
	public String updatePassword(HttpSession session, MemberDAO memberDAO, MemberDTO memberDTO, Model model) {
		System.out.println("MypageController.java updatePassword() 시작");
		String loginMember = (String)session.getAttribute("member_id");

		if(session == null || loginMember == null) {
			model.addAttribute("msg", "로그인이 필요한 서비스입니다.");
			model.addAttribute("path", "login.jsp");
			return "info";
		}
		
		memberDTO.setMember_id(loginMember);
		memberDTO.setMember_condition("PASSWORD");
		
		// 데이터 로그
		System.out.println("MypageController.java updatePassword() member_id : [ "+memberDTO.getMember_id()+" ]");
		System.out.println("MypageController.java updatePassword() member_password : [ "+memberDTO.getMember_password()+" ]");
		
		boolean flag = memberDAO.update(memberDTO);
		
		model.addAttribute("path", "mypage.do");
		if(!flag) {
			System.out.println("MypageController.java updatePassword() 실패");
			model.addAttribute("msg", "회원정보 수정을 실패했습니다. 다시 시도해주세요.");
		}
		else {
			System.out.println("MypageController.java updatePassword() t성고");
			model.addAttribute("msg", "회원정보 수정을 완료했습니다.");
		}
		
		System.out.println("MypageController.java updatePassword() 끝");
		return "info";
	}
	/////////////// 비밀번호 수정 끝
}
