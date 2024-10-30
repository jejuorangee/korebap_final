package com.korebap.app.view.page;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.korebap.app.biz.product.ProductDTO;
import com.korebap.app.biz.product.ProductService;

@Controller
public class ProductListPageAction {

	@Autowired
	ProductService productService;

	@RequestMapping(value="/productListPage.do", method=RequestMethod.GET)
	public String productListPage(ProductDTO productDTO, Model model, @RequestParam(value="currentPage", defaultValue="1") int product_page_num) {
		// 인자로 받는 cruuentPage는 defaultValue를 설정하여 값이 없는 경우 1로 설정한다.
		// *초기 페이지이므로 null로 들어올 수 있음

		// [ 상품 목록 페이지 ]
		System.out.println("=====com.korebap.app.view.page productListPage 시작");

		// 데이터 로그
		System.out.println("=====com.korebap.app.view.page productListPage productDTO ["+productDTO+"]");
		System.out.println("=====com.korebap.app.view.page productListPage product_page_num ["+product_page_num+"]");

		//		// V에서 받아온 파라미터
		String product_searchKeyword = productDTO.getProduct_searchKeyword(); // 검색어
		String product_location = productDTO.getProduct_location(); // 상품 장소 (바다,민물)
		String product_category = productDTO.getProduct_category(); // 상품 유형 (낚시배, 낚시터, 바다, 민물)
		String product_search_criteria = productDTO.getProduct_search_criteria(); // 최신, 좋아요, 찜, 예약 많은 순 >> 정렬기준



		// V에서 받아온 파라미터 로그
		System.out.println("=====com.korebap.app.view.page productListPage product_searchKeyword ["+product_searchKeyword+"]");
		System.out.println("=====com.korebap.app.view.page productListPage product_location ["+product_location+"]");
		System.out.println("=====com.korebap.app.view.page productListPage product_category ["+product_category+"]");
		System.out.println("=====com.korebap.app.view.page productListPage product_search_criteria ["+product_search_criteria+"]");


		// M에게 전달하기 위해 DTO에 담아준다.
		//		productDTO.setProduct_page_num(product_page_num);
		//
		//		
		//		productDTO.setProduct_searchKeyword(product_searchKeyword); // 검색어
		//		productDTO.setProduct_search_criteria(product_search_criteria); // 최신, 좋아요, 찜, 예약 많은 순 >> 정렬기준
		//		
		//		productDTO.setProduct_location(product_location); // 상품 장소 (바다,민물)
		//		productDTO.setProduct_category(product_category); // 상품 유형 (낚시배, 낚시터, 바다, 민물)
		//		
		//		ProductDAO productDAO = new ProductDAO();

		// 사용자가 선택한 페이지번호 처리
		productDTO.setProduct_page_num(product_page_num);
		// M에게 데이터를 보내주고 결과를 받음
		List<ProductDTO> productList = productService.selectAll(productDTO);

		System.out.println("=====com.korebap.app.view.page productListPage productList ["+productList+"]");



		// [전체 페이지 개수 받아오기]
		productDTO.setProduct_condition("PRODUCT_PAGE_COUNT");
		productDTO = productService.selectOne(productDTO);

		System.out.println("=====com.korebap.app.view.page productListPage productDTO ["+productDTO+"]");


		// int 타입 변수에 받아온 값을 넣어준다.
		int product_total_page = productDTO.getProduct_total_page();

		if (product_total_page < 1) {
			product_total_page = 1; // 최소 페이지 수를 1로 설정
		}



		System.out.println("=====com.korebap.app.view.page productListPage product_total_page ["+product_total_page+"]");
		System.out.println("=====com.korebap.app.view.page productListPage productList ["+productList+"]");

		//		model.addAttribute("productList", productList);
		//		model.addAttribute("product_page_count", product_total_page); // 게시판 페이지 개수
		//		model.addAttribute("currentPage", product_page_num); // 게시판 현재 페이지


		// 모델에 데이터 추가
		model.addAttribute("productList", productList); // 상품 목록
		model.addAttribute("product_location", product_location); // 위치 필터
		model.addAttribute("product_category", product_category); // 카테고리 필터
		model.addAttribute("searchOption", product_search_criteria); // 검색 기준
		model.addAttribute("product_page_count", product_total_page); // 페이지 수
		model.addAttribute("currentPage", product_page_num); // 현재 페이지
		//model.addAttribute("product_total_page", product_total_page);


		//		request.setAttribute("product_location", product_location);
		//		
		//		request.setAttribute("product_category", product_category);
		//		request.setAttribute("productList", productList);
		//		request.setAttribute("product_page_count", product_total_page); // 게시판 페이지 개수
		//		request.setAttribute("currentPage", product_current_page); // 게시판 현재 페이지


		System.out.println("=====com.korebap.app.view.page productListPage 종료");
		return "productList";
	}

}





//@RequestMapping(value="/productListPage.do")
//public ActionForward productListPage(ProductDTO productDTO, Model model) {
//	// [ 상품 목록 페이지 ]
//	System.out.println("=====com.korebap.app.view.page productListPage 시작");
//
//	// V에서 받아온 파라미터
//	String product_searchKeyword = request.getParameter("searchKeyword"); // 검색어
//	String product_location = request.getParameter("product_location"); // 상품 장소 (바다,민물)
//	String product_category = request.getParameter("product_category"); // 상품 유형 (낚시배, 낚시터, 바다, 민물)
//	String product_search_criteria = request.getParameter("searchOption"); // 최신, 좋아요, 찜, 예약 많은 순 >> 정렬기준
//
//	// 현재 페이지 번호를 V에서 받아와 M에게 데이터 요청
//	String product_page = request.getParameter("currentPage"); 
//
//	//데이터 로그
//	System.out.println("productPageAction product_page : " + product_page);
//	System.out.println("product_searchKeyword : "+product_searchKeyword);
//	System.out.println("product_location : " + product_location);
//	System.out.println("product_category : " + product_category);
//	System.out.println("product_searchContent : " + product_search_criteria);
//
//	ProductDTO productDTO = new ProductDTO();
//
//	// DTO에 객체 반영
//	
//
//
//	// DB에 list 요청하기 위한 페이지 번호 지정
//	// 만약 받아온 페이지 번호가 null 이라면 기본값 1
//	int product_current_page =1;
//
//	if(product_page != null) {	// 받아온 페이지 번호가 null이 아니라면
//		System.out.println("productPageAction 받아온 페이지 번호 null이 아닌 경우");
//		// int 타입으로 변환
//		product_current_page = Integer.parseInt(product_page);
//	}
//
//	System.out.println("로그 product_current_page : ["+product_current_page+"]");
//	// M에게 전달하기 위해 DTO에 담아준다.
//	productDTO.setProduct_page_num(product_current_page);
//
//	
//	productDTO.setProduct_searchKeyword(product_searchKeyword); // 검색어
//	productDTO.setProduct_search_criteria(product_search_criteria); // 최신, 좋아요, 찜, 예약 많은 순 >> 정렬기준
//	
//	productDTO.setProduct_location(product_location); // 상품 장소 (바다,민물)
//	productDTO.setProduct_category(product_category); // 상품 유형 (낚시배, 낚시터, 바다, 민물)
//	
//	ProductDAO productDAO = new ProductDAO();
//
//	ArrayList<ProductDTO> productList = productDAO.selectAll(productDTO);
//
//
//	System.out.println("로그 : productList ["+productList+"]");
//	
//	ProductDTO data = new ProductDTO();
//	
//	// [전체 페이지 개수를 받아오기 위해 객체 생성]
//	//ProductDTO page_count = new ProductDTO();
//	//page_count.setProduct_page_num(product_page_num);
//	data.setProduct_condition("PRODUCT_PAGE_COUNT");
//	data = productDAO.selectOne(data);
//	// int 타입 변수에 받아온 값을 넣어준다.
//	int product_total_page = data.getProduct_total_page();
//
//	if (product_total_page < 1) {
//		product_total_page = 1; // 최소 페이지 수를 1로 설정
//	}
//
//	
//
//	System.out.println("로그 productList : ["+productList+"]");
//	
//	request.setAttribute("product_location", product_location);
//	
//	request.setAttribute("product_category", product_category);
//	request.setAttribute("productList", productList);
//	request.setAttribute("product_page_count", product_total_page); // 게시판 페이지 개수
//	request.setAttribute("currentPage", product_current_page); // 게시판 현재 페이지
//
//	ActionForward forward = new ActionForward();
//	forward.setPath("productList.jsp");
//	forward.setRedirect(false);
//
//	System.out.println("ProductListPageAction 끝");
//	return forward;
//}
//
//}
