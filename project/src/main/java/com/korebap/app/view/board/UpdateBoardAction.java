package com.korebap.app.view.board;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.korebap.app.biz.board.BoardDTO;
import com.korebap.app.biz.board.BoardService;

import jakarta.servlet.http.HttpSession;



@Controller
public class UpdateBoardAction {
	
	@Autowired
	BoardService boardService;

	@RequestMapping(value="/updateBoard.do")
	public String updateBoard(HttpSession session, BoardDTO boardDTO, Model model, RedirectAttributes redirectAttributes) {
		// [ 게시글 수정 ]

		System.out.println("=====com.korebap.app.view.board updateBoard 시작");

		// 경로를 담을 변수
		String viewName;

		// 상세페이지로 이동시키기 위해 변수 선언
		int board_num = boardDTO.getBoard_num();

		// 세션에서 로그인 정보를 가져와 DTO의 member_id에 담아준다. (M에게도 전달 필요)
		String member_id = (String)session.getAttribute("member_id");

		// 데이터 로그
		System.out.println("=====com.korebap.app.view.board updateBoard member_id 확인 : ["+member_id+"]");
		System.out.println("=====com.korebap.app.view.board updateBoard board_num 확인 : ["+board_num+"]");
		System.out.println("=====com.korebap.app.view.board updateBoard boardDTO 확인 : ["+boardDTO+"]");


		if(member_id.equals("")) { // 만약 로그인 상태가 아니라면 
			System.out.println("=====com.korebap.app.view.board updateBoard 로그인 세션 없음");

			// 로그인 안내 후 login 페이지로 이동시킨다
			model.addAttribute("msg", "로그인이 필요한 서비스입니다.");
			model.addAttribute("path", "loginPage.do");

			// 데이터를 보낼 경로
			viewName = "info";
		}
		else { // 로그인 상태라면
			
			System.out.println("=====com.korebap.app.view.board updateBoard 로그인 세션 있음");

			// Service에게 DTO 객체를 보내서 update 시킨다
			boolean flag = boardService.update(boardDTO);
			
			if(flag) { // 변경 성공했다면
				System.out.println("=====com.korebap.app.view.board updateBoard 게시글 변경 성공");

				// 게시글 상세 페이지로 이동
				// 리다이렉트시 쿼리 매개변수를 자동으로 URL에 포함
				// 쿼리 매개변수 == URL에서 ? 기호 뒤에 위치하는 key-value 쌍
				redirectAttributes.addAttribute("board_num", board_num);

				viewName = "redirect:boardDetail.do";
				
			}
			else { // 변경 실패했다면 
				System.out.println("=====com.korebap.app.view.board updateBoard 게시글 변경 실패");
				// 안내 + 게시글 상세 페이지로 이동
				
				model.addAttribute("msg", "게시글 수정에 실패했습니다. 다시 시도해주세요.");
				model.addAttribute("path", "boardDetail.do?board_num="+board_num);
				
				viewName = "info";
			}
			
		}

		System.out.println("=====com.korebap.app.view.board updateBoard viewName ["+viewName+"]");
		System.out.println("=====com.korebap.app.view.board updateBoard 종료");
		return viewName;
	}

}
