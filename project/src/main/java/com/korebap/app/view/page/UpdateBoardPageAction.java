package com.korebap.app.view.page;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.korebap.app.biz.board.BoardDTO;
import com.korebap.app.biz.board.BoardService;
import jakarta.servlet.http.HttpSession;

@Controller
public class UpdateBoardPageAction {

	@Autowired
	BoardService boardService;

	@RequestMapping(value="/updateBoardPage.do")
	public String updateBoardPage(HttpSession session, BoardDTO boardDTO, Model model) {
		// [ 게시글 수정]

		System.out.println("=====com.korebap.app.view.page updateBoardPage 시작");

		// 경로를 담을 변수
		String viewName;

		// 상세페이지로 이동시키기 위해 변수 선언
		int board_num = boardDTO.getBoard_num();

		// 세션에서 로그인 정보를 가져와 DTO의 member_id에 담아준다. (M에게도 전달 필요)
		String login_member_id = (String)session.getAttribute("member_id");


		// 데이터 로그
		System.out.println("=====com.korebap.app.view.page updateBoardPage member_id 확인 : ["+login_member_id+"]");
		System.out.println("=====com.korebap.app.view.page updateBoardPage board_num 확인 : ["+board_num+"]");


		if(login_member_id==null) { // 만약 로그인 상태가 아니라면 
			System.out.println("=====com.korebap.app.view.page updateBoardPage 로그인 세션 없음");

			// 로그인 안내 후 login 페이지로 이동시킨다
			model.addAttribute("msg", "로그인이 필요한 서비스입니다.");
			model.addAttribute("path", "loginPage.do");

			// 데이터를 보낼 경로
			viewName = "info";
		}
		else {
			
			System.out.println("=====com.korebap.app.view.page updateBoardPage 로그인 세션 있음");
			
			boardDTO = boardService.selectOne(boardDTO);
			
			model.addAttribute("board", boardDTO);
			
			viewName ="updateBoard";
			
		}

		System.out.println("=====com.korebap.app.view.page updateBoardPage viewName ["+viewName+"]");
		System.out.println("=====com.korebap.app.view.page updateBoardPage 종료");


		return viewName;
	}

}
