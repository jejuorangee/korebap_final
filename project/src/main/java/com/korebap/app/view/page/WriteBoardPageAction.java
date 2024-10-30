package com.korebap.app.view.page;



import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


import jakarta.servlet.http.HttpSession;

@Controller
public class WriteBoardPageAction {

	@RequestMapping(value="/writeBoardPage.do")
	public String writeBoardPage(HttpSession session, Model model) {
		// [ 게시글 작성 페이지 이동]
		
		System.out.println("=====com.korebap.app.view.page writeBoardPage 시작");

		// 경로 저장해줄 변수
		String viewName;
		// Session에 저장된 id를 가져온다
		String member_id = (String)session.getAttribute("member_id"); 
		
		System.out.println("=====com.korebap.app.view.page writeBoardPage member_id ["+member_id+"]");
		
		
		if(member_id==null) { // 만약 로그인 상태가 아니라면 
			System.out.println("=====com.korebap.app.view.page writeBoardPage 로그인 세션 없음");

			// 로그인 안내 후 login 페이지로 이동시킨다
			model.addAttribute("msg", "로그인이 필요한 서비스입니다.");
			model.addAttribute("path", "loginPage.do");

			// 데이터를 보낼 경로
			viewName = "info";
		}
		else { //  로그인 상태라면
			System.out.println("=====com.korebap.app.view.page writeBoardPage 로그인 세션 있음");

			viewName = "redirect:writeBoard.jsp";
			
			
		}
				
		System.out.println("=====com.korebap.app.view.page writeBoardPage viewName ["+viewName+"]");
		System.out.println("=====com.korebap.app.view.page writeBoardPage 종료");

		
		return viewName;
	}

}




//public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
//	System.out.println("writeBoardPageAction 시작");
//	
//	String login_member_id = LoginCheck.loginCheck(request);
//	ActionForward forward=new ActionForward();
//	if(login_member_id.equals("")) {
//		System.out.println("로그인 세션 없음");
//		request.setAttribute("msg", "로그인이 필요한 서비스입니다.");
//		request.setAttribute("path", "loginPage.do");
//		
//		forward.setPath("info.jsp");
//		forward.setRedirect(false);
//		return forward;
//	}
//	
//	forward.setPath("writeBoard.jsp");
//	forward.setRedirect(true);
//	System.out.println("writeBoardPageAction 끝");
//	return forward;
//}
