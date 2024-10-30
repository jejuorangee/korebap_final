package com.korebap.app.view.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;



@Controller
public class LoginPageAction {

	@RequestMapping(value="/loginPage.do", method=RequestMethod.GET)
	public String loginPage() {
		// [ 로그인 페이지 이동]
		
		System.out.println("=====com.korebap.app.view.page loginPage 시작");
		System.out.println("=====com.korebap.app.view.page loginPage 단순 페이지 이동 기능, 바로 종료");
		return "redirect:login.jsp";
	}
}
