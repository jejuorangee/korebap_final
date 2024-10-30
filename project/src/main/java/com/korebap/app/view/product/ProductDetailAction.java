package com.korebap.app.view.product;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.korebap.app.biz.imagefile.ImageFileDTO;
import com.korebap.app.biz.imagefile.ImageFileService;
import com.korebap.app.biz.product.ProductDTO;
import com.korebap.app.biz.product.ProductService;
import com.korebap.app.biz.review.ReviewDTO;
import com.korebap.app.biz.review.ReviewService;
import com.korebap.app.biz.wishlist.WishlistDTO;
import com.korebap.app.biz.wishlist.WishlistService;

import jakarta.servlet.http.HttpSession;


@Controller
public class ProductDetailAction {
	// 상품 정보
	// 상품 사진
	// 리뷰2\
	
	@Autowired
	ProductService productService;
	@Autowired
	ReviewService reviewService;
	@Autowired
	ImageFileService fileService;
	@Autowired
	WishlistService wishlistService;

	@RequestMapping(value="/productDetail.do")
	public String productDetail(HttpSession session, Model model,ProductDTO productDTO, 
			ReviewDTO reviewDTO, @RequestParam int product_num, ImageFileDTO fileDTO, WishlistDTO wishlistDTO) {
		// 예약 상세보기
		
		System.out.println("=====com.korebap.app.view.product productDetail 시작");

		// 경로를 담을 변수 설정
		String viewName;
		// Session에 담긴 아이디 확인
		String member_id = (String)session.getAttribute("member_id");
		
		// 데이터 로그
		System.out.println("=====com.korebap.app.view.product member_id ["+member_id+"]");
		System.out.println("=====com.korebap.app.view.product product_num ["+product_num+"]");

		// Service에 DTO 객체를 보내서 데이터를 반환받는다.
		// 상품
//		productDTO.setProduct_num(product_num);
		productDTO.setProduct_condition("PRODUCT_BY_INFO");
		productDTO = productService.selectOne(productDTO);
		
		// 파일
		fileDTO.setFile_product_num(product_num);
		fileDTO.setFile_condition("PRODUCT_FILE_SELECTALL");
		List<ImageFileDTO> fileList = fileService.selectAll(fileDTO);

		// 리뷰
		reviewDTO.setReview_product_num(product_num);
		reviewDTO.setReview_condition("REVIEW_ALL");
		List<ReviewDTO> reviewList = reviewService.selectAll(reviewDTO);
		
		//WishlistDTO wishlistDTO = null;
		if(member_id != null) {
			System.out.println("=====com.korebap.app.view.product productDetail 로그인 아이디가 있다면 ");

			wishlistDTO.setWishlist_member_id(member_id);
			wishlistDTO.setWishlist_product_num(product_num);
	
			wishlistDTO = wishlistService.selectOne(wishlistDTO);
			System.out.println("=====com.korebap.app.view.product productDetail 로그! wishlistDTO ["+wishlistDTO+"]");

			
			model.addAttribute("wishlist", wishlistDTO);
		}

		// 만약 productDTO 객체가 있다면
		if(productDTO != null) {
			System.out.println("=====com.korebap.app.view.product productDetail 상품 찾기 성공 ");

			model.addAttribute("product", productDTO);
			model.addAttribute("fileList", fileList);
			model.addAttribute("reviewList", reviewList);
			
			viewName = "productDetail";
		}
		else {
			System.out.println("=====com.korebap.app.view.product productDetail 상품 찾기 실패");
			
			model.addAttribute("msg", "상품을 찾을 수 없습니다. 다시 시도해 주세요.");
			model.addAttribute("path", "productList.do");
			
			viewName = "info";
			
		}
		System.out.println("=====com.korebap.app.view.product productDetail viewName ["+ viewName +"]");
		
		System.out.println("=====com.korebap.app.view.product productDetail 종료");

		return viewName;
	}

}









//
//public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
//	// 예약 상세보기
//	System.out.println("ProductDetailAction 시작");
//	HttpSession session = request.getSession();
//	String loginMember = (String)session.getAttribute("member_id");
//	int product_num = Integer.parseInt(request.getParameter("product_num"));
//	
//	//데이터 로그
//	System.out.println("product_num : "+product_num);
//	
//	ProductDTO productDTO = new ProductDTO();
//	productDTO.setProduct_num(product_num);
//	productDTO.setProduct_condition("PRODUCT_BY_INFO");
//	
//	ProductDAO productDAO = new ProductDAO();
//	productDTO = productDAO.selectOne(productDTO);
//	
//	FileDTO fileDTO = new FileDTO();
//	fileDTO.setFile_product_num(product_num);
//	fileDTO.setFile_condition("PRODUCT_FILE_SELECTALL");
//	
//	FileDAO fileDAO = new FileDAO();
//	ArrayList<FileDTO> fileList = fileDAO.selectAll(fileDTO);
//	System.out.println(fileList);
//	
//	ReviewDTO reviewDTO = new ReviewDTO();
//	reviewDTO.setReview_product_num(product_num);
//	reviewDTO.setReview_condition("REVIEW_ALL");
//	
//	ReviewDAO reviewDAO = new ReviewDAO();
//	ArrayList<ReviewDTO> reviewList = reviewDAO.selectAll(reviewDTO);
//	
//	WishlistDTO wishlistDTO = null;
//	if(loginMember != null) {
//		wishlistDTO = new WishlistDTO();
//		wishlistDTO.setWishlist_member_id(loginMember);
//		wishlistDTO.setWishlist_product_num(product_num);
////		wishlistDTO.setWishlist_condition("WISHLIST_COUNT");
//		
//		WishlistDAO wishlistDAO = new WishlistDAO();
//		wishlistDTO = wishlistDAO.selectOne(wishlistDTO);
//		
//		request.setAttribute("wishlist", wishlistDTO);
//	}
//	
//	
//	ActionForward forward = new ActionForward();
//	forward.setRedirect(false);
//	if(productDTO != null) {
//		System.out.println("ProductDetailAction 로그 : 상품 찾기 성공");
//		request.setAttribute("product", productDTO);
//		request.setAttribute("fileList", fileList);
//		request.setAttribute("reviewList", reviewList);
//		forward.setPath("productDetail.jsp");
//	}
//	else {
//		System.out.println("ProductDetailAction 로그 : 상품 찾기 실패");
//		request.setAttribute("msg", "상품을 찾을 수 없습니다. 다시 시도해 주세요.");
//		request.setAttribute("path", "productList.do");
//		forward.setPath("info.jsp");
//	}
//	System.out.println("ProductDetailAction 끝");
//	return forward;
//}
