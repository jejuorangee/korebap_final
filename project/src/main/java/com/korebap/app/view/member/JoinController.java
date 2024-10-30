package com.korebap.app.view.member;


import java.util.Date;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.korebap.app.biz.member.MemberDTO;
import com.korebap.app.biz.member.MemberService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class JoinController {
	@Autowired
	private MemberService memberService;
	
	/////////////// 회원가입 메서드 시작
	@RequestMapping(value="/join.do",method=RequestMethod.GET)
	public String join() {
    	System.out.println("************************************************************com.korebap.app.view.member.JoinController_join_GET 시작************************************************************");
		System.out.println("회원가입 페이지로 이동");
		System.out.println("************************************************************com.korebap.app.view.member.JoinController_join_GET 종료************************************************************");
		return "join";
	}
	
	
	@RequestMapping(value="/join.do",method=RequestMethod.POST)
	public String join(@RequestParam("postcode")String member_postcode,
						@RequestParam("address")String member_address,
						@RequestParam("extraAddress")String member_extraAddress,
						@RequestParam("detailAddress")String member_detailAddress,MemberDTO memberDTO, Model model) {
		System.out.println("************************************************************com.korebap.app.view.member.JoinController_join_POST 시작************************************************************");
		memberDTO.setMember_postcode(member_postcode);
		memberDTO.setMember_address(member_address);
		memberDTO.setMember_extraAddress(member_extraAddress);
		memberDTO.setMember_detailAddress(member_detailAddress);

		//데이터 로그
		System.out.println("JoinController.java join() member_id : [ "+memberDTO.getMember_id()+" ]");
		System.out.println("JoinController.java join() member_pw : [ "+memberDTO.getMember_password()+" ]");
		System.out.println("JoinController.java join() member_nickname : [ "+memberDTO.getMember_nickname()+" ]");
		System.out.println("JoinController.java join() member_name : [ "+memberDTO.getMember_name()+" ]");
		System.out.println("JoinController.java join() member_phone : [ "+memberDTO.getMember_phone()+" ]");
		System.out.println("JoinController.java join() member_postcode : [ "+memberDTO.getMember_postcode()+" ]");
		System.out.println("JoinController.java join() member_address : [ "+memberDTO.getMember_address()+" ]");
		System.out.println("JoinController.java join() member_extraAddress : [ "+memberDTO.getMember_extraAddress()+" ]");
		System.out.println("JoinController.java join() member_detailAddress : [ "+memberDTO.getMember_detailAddress()+" ]");
		System.out.println("JoinController.java join() member_role : [ "+memberDTO.getMember_role()+" ]");

		String postCode = memberDTO.getMember_postcode();
		String address = memberDTO.getMember_address();
		String extraAddress = memberDTO.getMember_extraAddress();
		String detailAddress = memberDTO.getMember_detailAddress();
		
		String totalAddress = postCode+"_"+address+"_"+extraAddress+"_"+detailAddress;
		System.out.println("JoinController.java join() totalAddress : [ "+totalAddress+" ]");
		
		memberDTO.setMember_address(totalAddress);
		
		boolean flag = memberService.insert(memberDTO);
		
		if(flag) {
			System.out.println("회원가입 성공 -> 로그인 페이지로 이동");
			System.out.println("************************************************************com.korebap.app.view.member.JoinController_join_POST 종료************************************************************");
			return "login";
		}
		
		model.addAttribute("msg", "회원가입에 실패했습니다. 다시 시도해주세요.");
		model.addAttribute("path", "join.jsp");
		System.out.println("************************************************************com.korebap.app.view.member.JoinController_join_POST 종료************************************************************");
		return "info";
	}
	/////////////// 회원가입 메서드 끝
	
	/////////////// 아이디 중복 검사 메서드 시작
	@RequestMapping("/checkMemberId")
	public String checkMemberId(MemberDTO memberDTO) {
		System.out.println("************************************************************com.korebap.app.view.member.JoinController_checkMemberID_GET 시작************************************************************");

		// 데이터 로그
		System.out.println("JoinController.java checkMemberId() member_id : [ "+memberDTO.getMember_id()+" ]");
		
		memberDTO.setMember_condition("CHECK_MEMBER_ID");
		
		memberDTO = memberService.selectOne(memberDTO);
		
		if(memberDTO != null && memberDTO.getMember_id() != null) {
			System.out.println("JoinController.java checkMemberId() 아이디 중복");
			System.out.println("************************************************************com.korebap.app.view.member.JoinController_checkMemberID_GET 종료************************************************************");
			return "true";
		}
		System.out.println("JoinController.java checkMemberId() 아이디 중복 x");
		System.out.println("************************************************************com.korebap.app.view.member.JoinController_checkMemberID_GET 종료************************************************************");
		return "false";
	}
	/////////////// 아이디 중복 검사 메서드 끝
	
	/////////////// 이메일 인증 메서드 시작
	@RequestMapping("/sendMail")
	public void sendMail(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("************************************************************com.korebap.app.view.member.JoinController_sendMail_GET 시작************************************************************");
		
		String num = request.getParameter("num");
        String receiver = request.getParameter("email");
        
        // 데이터 로그
        System.out.println("JoinController.java sendMail() num : [ "+num+" ]");
        System.out.println("JoinController.java sendMail() receiver : [ "+receiver+" ]");

        // 이메일 제목을 설정합니다.
        String subject ="고래밥 회원가입 인증번호입니다.";
        // 이메일 내용에 인증번호를 포함합니다.
        String content = "인증번호 : " + num;
        
        response.setContentType("text/html; charset=utf-8");
        
        // 이메일 서버의 속성을 설정합니다.
        Properties p = System.getProperties(); // 서버 정보를 저장할 Properties 객체
        Authenticator auth = new GoogleAuthentication();
        // SMTP 서버의 속성을 설정합니다.
        p.put("mail.smtp.starttls.enable", "true"); // STARTTLS를 사용하여 암호화된 연결을 설정합니다.
        p.put("mail.smtp.host", "smtp.gmail.com"); // SMTP 서버 호스트 (Gmail)
        p.put("mail.smtp.auth", "true"); // 인증을 사용합니다.
        p.put("mail.smtp.port", "587"); // SMTP 포트 번호 (587은 TLS 포트)
        
        
        // 메일 세션을 생성합니다.
        Session s = Session.getInstance(p, auth); // 서버 속성과 인증 정보를 사용하여 Session 객체 생성
        
        // 메일 메시지를 생성합니다.
        Message m = new MimeMessage(s); // 생성된 Session을 사용하여 MimeMessage 객체 생성
        
        
        try {
        	// 수신자의 이메일 주소를 설정합니다.
        	Address receiver_address = new InternetAddress(receiver); // 수신자 이메일 주소
        	// 메일의 내용 유형과 문자 인코딩 설정
			m.setHeader("content-type", "text/html;charset=utf-8");
			m.addRecipient(Message.RecipientType.TO, receiver_address); // 수신자 추가
			m.setSubject(subject); // 메일 제목 설정
			m.setContent(content, "text/html;charset=utf-8"); // 메일 내용과 형식 설정
			m.setSentDate(new Date()); // 메일 전송 날짜 설정
			
			// 메일을 전송합니다.
			Transport.send(m); // 메일 전송
		} catch (MessagingException e) {
			System.out.println("JoinController.java sendMail() 메일 전송 실패");
			e.printStackTrace();
		} 
        
        System.out.println("************************************************************com.korebap.app.view.member.JoinController_sendMail_GET 종료************************************************************");
	}
	/////////////// 이메일 인증 메서드 끝
}
