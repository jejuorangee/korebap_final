package com.korebap.app.view.board;


import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.korebap.app.biz.board.BoardDTO;
import com.korebap.app.biz.board.BoardInsertTransaction;
import com.korebap.app.biz.board.BoardService;
import com.korebap.app.biz.imagefile.ImageFileDTO;
import com.korebap.app.biz.imagefile.ImageFileService;

import jakarta.servlet.http.HttpSession;

@Controller
public class WriteBoardAction {

	@Autowired
	BoardService boardService;
	@Autowired
	ImageFileService fileService;
	@Autowired
	BoardInsertTransaction boardInsertTransaction; // 트랜잭션

	@PostMapping(value="/writeBoard.do")
	public String writeBoard(HttpSession session, BoardDTO boardDTO, ImageFileDTO imageFileDTO, Model model,
			RedirectAttributes redirectAttributes, @RequestPart MultipartFile files) throws IllegalStateException, IOException  {

		// [ 게시글 작성 ]

		System.out.println("=====com.korebap.app.view.board writeBoard 시작");

		// 경로를 담을 변수
		// info로 보내는 경우가 많아서 info로 초기화
		String viewName = "info";

		// 상세페이지로 이동시키기 위해 변수 선언
		int board_num = boardDTO.getBoard_num();

		// 세션에서 로그인 정보를 가져와 DTO의 member_id에 담아준다. (M에게 전달 필요)
		String member_id = (String)session.getAttribute("member_id");


		// 데이터 로그
		System.out.println("=====com.korebap.app.view.board writeBoard member_id 확인 : ["+member_id+"]");
		System.out.println("=====com.korebap.app.view.board writeBoard boardDTO 확인 : ["+boardDTO+"]");


		if(member_id == null) { // 만약 로그인 상태가 아니라면 
			System.out.println("=====com.korebap.app.view.board writeBoard 로그인 세션 없음");

			// 로그인 안내 후 login 페이지로 이동시킨다
			model.addAttribute("msg", "로그인이 필요한 서비스입니다.");
			model.addAttribute("path", "loginPage.do");

		}
		else {
			System.out.println("=====com.korebap.app.view.board writeBoard 로그인 세션 있음");

			try { // 트랜잭션 RuntimeException 발생 후 페이지 이동 시키기 위해 try-catch로 묶는다
				
				// 트랜잭션 요청
				boolean flag = boardInsertTransaction.insertBoardAndImg(session,boardDTO, imageFileDTO, files);

				// 글/이미지 입력 성공시
				if(flag) {
					// 게시글 상세 페이지로 이동
					// 리다이렉트시 쿼리 매개변수를 자동으로 URL에 포함
					// 쿼리 매개변수 == URL에서 ? 기호 뒤에 위치하는 key-value 쌍
					redirectAttributes.addAttribute("board_num", board_num);
					System.out.println("=====com.korebap.app.view.board writeBoard getBoard_num ["+board_num+"]");

					viewName = "redirect:boardDetail.do";
				}
				else { // 글 작성 실패
					System.out.println("=====com.korebap.app.view.board writeBoard 게시글 작성 실패");
					model.addAttribute("msg", "글 작성에 실패했습니다. 다시 시도해주세요.");
					model.addAttribute("path", "writeBoardPage.do");
				}
			} catch (RuntimeException e) {
				// 트랜잭션 중 예외 발생
	            System.out.println("=====com.korebap.app.view.board writeBoard 트랜잭션 예외 발생: " + e.getMessage());
	            model.addAttribute("msg", "오류가 발생했습니다. 다시 시도해주세요.");
	            model.addAttribute("path", "writeBoardPage.do");
			}

		}// else 끝 (로그인상태)

		System.out.println("=====com.korebap.app.view.board writeBoard 종료");

		return viewName;
	}
}