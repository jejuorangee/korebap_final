package com.korebap.app.view.async;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.korebap.app.biz.board.BoardDTO;
import com.korebap.app.biz.product.ProductDAO;
import com.korebap.app.biz.product.ProductDTO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//import controller.common.ActionForward;

//@WebServlet("/productList.do")
public class ProductListPage extends HttpServlet {
	private static final long serialVersionUID = 1L;


	public ProductListPage() {
		super();
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("GET 요청 도착");


		// [상품 전체 출력]
		// V에서 받아온 파라미터
		String product_searchKeyword = request.getParameter("searchKeyword"); // 검색어
		String product_location = request.getParameter("product_location"); // 상품 장소 (바다,민물)
		String product_category = request.getParameter("product_category"); // 상품 유형 (낚시배, 낚시터, 바다, 민물)
		String product_search_criteria = request.getParameter("searchOption"); // 최신, 좋아요, 찜, 예약 많은 순 >> 정렬기준

		// 현재 페이지 번호를 V에서 받아와 M에게 데이터 요청
		String product_page = request.getParameter("currentPage"); 


		ProductDTO productDTO = new ProductDTO();
		ProductDAO productDAO = new ProductDAO();

		// V에게 받은 파라미터 값을 DTO 객체에 담에 M에게 전달해주고, 반환받는다.
		productDTO.setProduct_searchKeyword(product_searchKeyword); // 검색어
		productDTO.setProduct_location(product_location); // 상품 장소
		productDTO.setProduct_category(product_category); // 유형
		productDTO.setProduct_search_criteria(product_search_criteria); //정렬 기준
		

		// 사용자가 선택한 페이지번호 처리 (null이면 기본 1)
		int current_page=1;

		// 만약 받아온 페이지 번호가 null 이 아니라면
		if(product_page != null) {	
			System.out.println("ProductListPage 받아온 페이지 번호 null이 아닌 경우");
			System.out.println("product_page : ["+product_page+"]");
			// 받아온 페이지 번호가 null이 아니라면
			// int 타입으로 변환
			current_page = Integer.valueOf(product_page).intValue();

			// M에게 전달하기 위해 DTO에 담아준다.
			productDTO.setProduct_page_num(current_page);
			System.out.println("current_page : ["+current_page+"]");
		}
		//		else {
		//			System.out.println("BoardListScroll 받아온 페이지 번호 null인 경우");
		//			// M에게 전달하기 위해 DTO에 담아준다.
		//			current_page =1;
		//			boardDTO.setBoard_page_num(current_page);
		//			System.out.println("board_page : ["+current_page+"]");
		//		}


		
		
		//M에게 데이터를 보내주고, 결과를 ArrayList로 반환받는다. 
		ArrayList<ProductDTO> productList = productDAO.selectAll(productDTO);

		// [게시판 페이지 전체 개수]
		productDTO.setProduct_condition("PRODUCT_PAGE_COUNT");
		productDTO = productDAO.selectOne(productDTO);
		
		// int 타입 변수에 받아온 값을 넣어준다.
		int product_total_page = productDTO.getProduct_total_page();

		System.out.println("BoardListScroll 로그 board_page_count ["+product_total_page+"]");


		System.out.println("BoardListScroll 로그 board_page_count ["+product_total_page+"]");

		// 현재 페이지 > 전체 페이지
		if (current_page > product_total_page) {
			System.out.println("마지막 페이지 요청, 더 이상 데이터 없음");
			response.setStatus(HttpServletResponse.SC_NO_CONTENT); // 더 이상 게시글이 없음을 클라이언트에 알림
			return;
		}

		// JSON으로 변환하여 응답
		response.setContentType("application/json");  
		PrintWriter out=response.getWriter();
		String jsonResponse = new Gson().toJson(productList);  // Gson을 이용하여 JSON 변환
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
