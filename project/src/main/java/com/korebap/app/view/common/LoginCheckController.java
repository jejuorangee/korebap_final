package com.korebap.app.view.common;

import org.springframework.stereotype.Controller;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginCheckController {

	
	public static String loginCheck(HttpSession session) { // 로그인 체크 메서드

		// 로그인 체크는 계속 사용하니까 분리해도 되지만
		// 프로젝트 규모가 크지 않기 때문에 클래스 한 곳에 로그인 체크가 필요한 기능들을 모아 메서드도 함께 작성하여 사용!
		// 코드의 응집도를 높일 수 있다!
		
		// 로그인 체크를 위해 session에서 userID를 가지고 온다.
		String login_check = (String)session.getAttribute("member_id");


		// 만약 id가 null이라면
		if(login_check == null) {
			
			return "";
		}
		// 아니라면 로그인.jsp
		return "redirect:login.jsp";

	}
	
	
	
	
	


}
