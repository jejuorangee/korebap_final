package com.korebap.app.view.async;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import com.google.gson.Gson;
import com.korebap.app.biz.board.BoardDAO;
import com.korebap.app.biz.board.BoardDTO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//import controller.common.ActionForward;

@WebServlet("/boardListScroll")
public class BoardListScroll extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	public BoardListScroll() {
		super();
	}

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("GET 요청 도착");

		
		// [게시글 전체 출력]
		// 글 제목+내용 검색이기 때문에, View에서 받아온 Parameter 값을 변수에 넣어준다.
		// 정렬 종류 (기본(최신)/좋아요 순)
		String search_criteria = request.getParameter("board_search_criteria");
		// 검색어
		String search_Keyword = request.getParameter("searchKeyword");
		// 선택한 페이지 번호
		String board_page = request.getParameter("currentPage");
		System.out.println("받아온 현재 페이지 번호: " + board_page);



//		
//		if (board_page == null || board_page.equals("1")) {
//			System.out.println("BoardListScroll currentPage가 null이거나 1인 경우");
//			// 첫 페이지는 메인 로직에서 처리, 스크롤 요청은 무시
//			response.setStatus(HttpServletResponse.SC_NO_CONTENT);
//			return;
//		}
		
		
		// 데이터 로그
		System.out.println("BoardListScroll 로그 searchContent : [" + search_criteria +"]");
		System.out.println("BoardListScroll 로그 searchKeyword : [" + search_Keyword +"]");
		System.out.println("BoardListScroll 로그 board_page : [" + board_page +"]");

		BoardDTO boardDTO = new BoardDTO();
		BoardDAO boardDAO = new BoardDAO();

		// V에게 받은 파라미터 값을 DTO 객체에 담에 M에게 전달해주고, 반환받는다.
		boardDTO.setBoard_search_criteria(search_criteria); // 게시판 정렬 기준
		boardDTO.setBoard_searchKeyword(search_Keyword); // 게시판 검색어
		boardDTO.setBoard_condition("BOARD_ALL"); // 전체출력 컨디션


		// 사용자가 선택한 페이지번호 처리
		int current_page=0;
	   

		// 만약 받아온 페이지 번호가 null 이 아니라면
		if(board_page != null) {	
			System.out.println("BoardListScroll 받아온 페이지 번호 null이 아닌 경우");
			System.out.println("board_page : ["+board_page+"]");
			// 받아온 페이지 번호가 null이 아니라면
			// int 타입으로 변환
			current_page = Integer.valueOf(board_page).intValue();

			// M에게 전달하기 위해 DTO에 담아준다.
			boardDTO.setBoard_page_num(current_page);
			System.out.println("current_page : ["+current_page+"]");
		}
//		else {
//			System.out.println("BoardListScroll 받아온 페이지 번호 null인 경우");
//			// M에게 전달하기 위해 DTO에 담아준다.
//			current_page =1;
//			boardDTO.setBoard_page_num(current_page);
//			System.out.println("board_page : ["+current_page+"]");
//		}
		
		

		//M에게 데이터를 보내주고, 결과를 ArrayList로 반환받는다. (게시판 전체 글)
		List<BoardDTO> boardList = boardDAO.selectAll(boardDTO);

		// [게시판 페이지 전체 개수]
		//BoardDTO page_count = new BoardDTO();
//		boardDTO.setBoard_page_num(current_page);
		boardDTO.setBoard_condition("BOARD_PAGE_COUNT");
		boardDTO = boardDAO.selectOne(boardDTO);
		// int 타입 변수에 받아온 값을 넣어준다.
		int board_total_page = boardDTO.getBoard_total_page();

		System.out.println("BoardListScroll 로그 board_page_count ["+board_total_page+"]");

//		if (board_total_page < 1) {
//			board_total_page = 1; // 최소 페이지 수를 1로 설정
//		}

		System.out.println("BoardListScroll 로그 board_page_count ["+board_total_page+"]");


        if (current_page > board_total_page) {
            System.out.println("마지막 페이지 요청, 더 이상 데이터 없음");
            response.setStatus(HttpServletResponse.SC_NO_CONTENT); // 더 이상 게시글이 없음을 클라이언트에 알림
            return;
        }

		// JSON으로 변환하여 응답
		response.setContentType("application/json");  
		PrintWriter out=response.getWriter();
		String jsonResponse = new Gson().toJson(boardList);  // Gson을 이용하여 JSON 변환
		System.out.println("BoardListScroll 로그 :["+jsonResponse+"]");
		out.print(jsonResponse);
		out.flush();
        System.out.println("BoardListScroll 종료");
        

		 // JSP로 데이터를 전달하고 HTML을 생성하여 클라이언트에 응답
//        request.setAttribute("boardList", boardList);
//        request.setAttribute("totalPage", board_total_page); // 게시판 페이지 개수
//		request.setAttribute("currentPage", current_page); // 게시판 현재 페이지
//        System.out.println("BoardListScroll Controller 응답 객체에 boardList 담음");
//
//        //request.setAttribute("ajaxRequest", "true");
//        RequestDispatcher dispatcher = request.getRequestDispatcher("/boardList2.jsp");
//        System.out.println("BoardListScroll Controller 경로 설정");
//        System.out.println("BoardListScroll Controller 종료");
//        
//        System.out.println("boardList 값: " + boardList); 
//        dispatcher.forward(request, response);
//        
//		

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("POST 요청 도착");


	}

}
