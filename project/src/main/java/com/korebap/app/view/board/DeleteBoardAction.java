package com.korebap.app.view.board;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.korebap.app.biz.board.BoardDAO;
import com.korebap.app.biz.board.BoardDTO;
import com.korebap.app.biz.board.BoardService;
import com.korebap.app.view.util.LoginCheck;

import jakarta.servlet.http.HttpSession;


@Controller
public class DeleteBoardAction {
	
	@Autowired
	BoardService boardService;

	@RequestMapping(value="/deleteBoard.do")
	public String deleteBoard(HttpSession session, BoardDTO boardDTO, Model model,RedirectAttributes redirectAttributes) {
		// [ 게시글 삭제 ]
		

		System.out.println("=====com.korebap.app.view.board deleteBoard 시작");

		// 경로를 담을 변수
		String viewName;

		// 상세페이지로 이동시키기 위해 변수 선언
		int board_num = boardDTO.getBoard_num();

		// 세션에서 로그인 정보를 가져와 DTO의 member_id에 담아준다. (M에게도 전달 필요)
		String member_id = (String)session.getAttribute("member_id");


		// 데이터 로그
		System.out.println("=====com.korebap.app.view.board deleteBoard member_id 확인 : ["+member_id+"]");
		System.out.println("=====com.korebap.app.view.board deleteBoard board_num 확인 : ["+board_num+"]");


		if(member_id.equals("")) { // 만약 로그인 상태가 아니라면 
			System.out.println("=====com.korebap.app.view.board deleteBoard 로그인 세션 없음");

			// 로그인 안내 후 login 페이지로 이동시킨다
			model.addAttribute("msg", "로그인이 필요한 서비스입니다.");
			model.addAttribute("path", "loginPage.do");

			// 데이터를 보낼 경로
			viewName = "info";
		}
		else { // 로그인 상태일때
			System.out.println("=====com.korebap.app.view.board deleteBoard 로그인 세션 있음");

			// service를 통하여 게시글 삭제 요청을 한다
			boolean flag = boardService.delete(boardDTO);
			
			if(flag) { // 삭제 성공시
				System.out.println("=====com.korebap.app.view.board deleteBoard 게시글 삭제 성공");
				// 메인페이지로 이동
				
				viewName ="redirect : main.do";
			}
			else { // 삭제 실패시
				System.out.println("=====com.korebap.app.view.board deleteBoard 게시글 삭제 실패");

				model.addAttribute("msg", "게시글 삭제에 실패했습니다. 다시 시도해 주세요.");
				model.addAttribute("path", "boardDetail.do?boardNum="+board_num);
				
				viewName = "info";
			}
			
		}
		
		System.out.println("=====com.korebap.app.view.board deleteBoard viewName ["+viewName+"]");

		System.out.println("=====com.korebap.app.view.board deleteBoard 종료");
		return viewName;
	}

}








//@Override
//@RequestMapping(value="/deleteBoard.do")
//public String deleteBoard(HttpSession session, BoardDTO boardDTO) {
//	
//	
//	
//	
//	System.out.println("DeleteBoardAction 시작");
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
//	int board_num=Integer.parseInt(request.getParameter("board_num"));
//	
//	//데이터 로그
//	System.out.println("board_num : "+board_num);
//	
//	BoardDTO boardDTO=new BoardDTO();
//	boardDTO.setBoard_num(board_num);
//	
//	BoardDAO boardDAO=new BoardDAO();
//	boolean flag=boardDAO.delete(boardDTO);
//	
//	forward.setRedirect(true);
//	if(flag) {
//		System.out.println("DeleteBoardAction 로그 : 게시글 삭제 성공");
//		forward.setPath("main.do");
//	}
//	else {
//		System.out.println("DeleteBoardAction 로그 : 게시글 삭제 실패");
//		request.setAttribute("msg", "게시글 삭제에 실패했습니다. 다시 시도해 주세요.");
//		request.setAttribute("path", "boardDetail.do?boardNum="+board_num);
//		
//		forward.setPath("info.jsp");
//		forward.setRedirect(false);
//	}
//	System.out.println("DeleteBoardAction 끝");
//	return forward;
//}
//
//}
