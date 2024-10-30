package com.korebap.app.view.async;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.korebap.app.biz.board.BoardDTO;
import com.korebap.app.biz.board.BoardService;

@RestController
public class BoardListScrollController {
	
	@Autowired
	BoardService boardService;
	
	// 게시판 무한스크롤 비동기 구현하는 컨트롤러
	@RequestMapping(value="/boardListScroll.do", method=RequestMethod.GET)
	public  Map<String, Object> boardListScroll(BoardDTO boardDTO, @RequestParam(value="currentPage") int current_page) {
		
		System.out.println("=====com.korebap.app.view.async boardListScroll 시작");


		// [게시글 전체 출력]
		// 검색어와 정렬 기준을 변수에 넣어준다.
		String searchKeyword = boardDTO.getBoard_searchKeyword(); // 검색어
		String search_criteria = boardDTO.getBoard_search_criteria(); // 정렬 기준

		// 데이터 로그
		System.out.println("=====com.korebap.app.view.async boardListScroll 로그 board_search_criteria : [" + search_criteria +"]");
		System.out.println("=====com.korebap.app.view.async boardListScroll 로그 searchKeyword : [" + searchKeyword +"]");
		System.out.println("=====com.korebap.app.view.async boardListScroll 로그 board_page : [" + current_page +"]");


		// V에게 받은 파라미터 값을 DTO 객체에 담에 M에게 전달해주고, 반환받는다.
		boardDTO.setBoard_page_num(current_page);
		boardDTO.setBoard_condition("BOARD_ALL"); // 전체출력 컨디션

		//M에게 데이터를 보내주고, 결과를 ArrayList로 반환받는다. (게시판 전체 글)
		List<BoardDTO> boardList = boardService.selectAll(boardDTO);

		System.out.println("=====com.korebap.app.view.async boardListScroll 로그 boardList ["+boardList+"]");



		// [게시판 페이지 전체 개수]
		boardDTO.setBoard_condition("BOARD_PAGE_COUNT");
		boardDTO = boardService.selectOne(boardDTO);
		// int 타입 변수에 받아온 값을 넣어준다.
		int board_total_page = boardDTO.getBoard_total_page();

		System.out.println("=====com.korebap.app.view.async boardListScroll 로그 board_page_count ["+board_total_page+"]");


		// 마지막 페이지 요청 시 처리
		// 현재 페이지 > 전체 페이지 수
		if (current_page > board_total_page) {
			System.out.println("=====com.korebap.app.view.async boardListScroll 마지막 페이지 요청");
			return null;
		}

		// 결과를 Map에 담아 반환
		Map<String, Object> responseMap = new HashMap<>();
		responseMap.put("boardList", boardList);
		responseMap.put("totalPage", board_total_page);

		System.out.println("=====com.korebap.app.view.async boardListScroll 로그 responseMap ["+responseMap+"]");

		System.out.println("=====com.korebap.app.view.async boardListScroll 종료");
		return responseMap;

	}

	

}
