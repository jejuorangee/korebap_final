package com.korebap.app.view.page;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.korebap.app.biz.board.BoardDTO;
import com.korebap.app.biz.board.BoardService;

import jakarta.servlet.http.HttpSession;

@Controller
public class MyBoardListPageAction {

	@Autowired
	BoardService boardService;


	@RequestMapping(value="/myBoardListPage.do")
	public String myBoardListPage(HttpSession session, BoardDTO boardDTO, Model model) {
		// [ 내 글 목록 ]


		System.out.println("=====com.korebap.app.view.page myBoardListPage 시작");

		// 경로를 담을 변수
		String viewName;

		// 세션에서 로그인 정보를 가져와 DTO의 member_id에 담아준다. (M에게도 전달 필요)
		String login_member_id = (String)session.getAttribute("member_id");


		// 데이터 로그
		System.out.println("=====com.korebap.app.view.page myBoardListPage member_id 확인 : ["+login_member_id+"]");


		if(login_member_id.equals("")) { // 만약 로그인 상태가 아니라면 
			System.out.println("=====com.korebap.app.view.page myBoardListPage 로그인 세션 없음");

			// 로그인 안내 후 login 페이지로 이동시킨다
			model.addAttribute("msg", "로그인이 필요한 서비스입니다.");
			model.addAttribute("path", "loginPage.do");

			// 데이터를 보낼 경로
			viewName = "info";
		}
		else { // 로그인 상태인 경우
			
			System.out.println("=====com.korebap.app.view.page myBoardListPage 로그인 세션 있음");
			
			// session에 저장된 회원의 id를 DTO 객체에 넣고 데이터를 반환 받는다.
			boardDTO.setBoard_writer_id(login_member_id);
			boardDTO.setBoard_condition("MYBOARD_LIST");
			List<BoardDTO> myBoardList = boardService.selectAll(boardDTO);
			
			System.out.println("=====com.korebap.app.view.page myBoardListPage myBoardList ["+myBoardList+"]");

			// view에게 전달하기 위해 model 객체에 저장
			model.addAttribute("myBoardList", myBoardList);
			
			// 경로 설정
			viewName ="myBoardList";
			
		}


		System.out.println("=====com.korebap.app.view.page myBoardListPage viewName ["+viewName+"]");
		System.out.println("=====com.korebap.app.view.page myBoardListPage 종료");

		return viewName;
	}

}








//@RequestMapping(value="/myBoardListPage.do")
//public String execute(HttpSession session, BoardDTO boardDTO, Model model) {
//	// [ 내 글 목록 ]
//	
//	System.out.println("MyBoardListPageAction 시작");
//
//	String login_member_id = LoginCheck.loginCheck(request);
//
//	//데이터 로그
//	System.out.println("member_id : "+login_member_id);
//
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
//	System.out.println("MyBoardListPageAction 로그 : 내가 쓴글 추출 시작");
//	BoardDTO boardDTO=new BoardDTO();
//	boardDTO.setBoard_writer_id(login_member_id);
//	boardDTO.setBoard_condition("MYBOARD_LIST");
//
//	BoardDAO boardDAO=new BoardDAO();
//	ArrayList<BoardDTO> myBoardList = boardDAO.selectAll(boardDTO);
//
//	request.setAttribute("myBoardList", myBoardList);
//
//	System.out.println("MyBoardListPageAction 로그 : 내가 쓴글 추출 끝");
//
//	forward.setPath("myBoardList.jsp");
//	forward.setRedirect(false);
//	
//	System.out.println("MyBoardListPageAction 끝");
//	return forward;
//}
