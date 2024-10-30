package com.korebap.app.view.page;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.korebap.app.biz.board.BoardDTO;
import com.korebap.app.biz.board.BoardService;
import com.korebap.app.biz.product.ProductDTO;
import com.korebap.app.biz.product.ProductService;

@Controller
public class MainPageController {
	@Autowired
	private BoardService boardService;
	@Autowired
	private ProductService productService;

	@RequestMapping("/main.do")
	public String mainPage(BoardDTO boardDTO, ProductDTO productDTO, Model model) {
		System.out.println("MainPageController.java mainPage() 시작");

		boardDTO.setBoard_condition("BOARD_ALL");
		boardDTO.setBoard_page_num(1);
		List<BoardDTO> boardList = boardService.selectAll(boardDTO);

		productDTO.setProduct_condition("PRODUCT_BY_ALL");
		productDTO.setProduct_page_num(1);
		List<ProductDTO> productList = productService.selectAll(productDTO);

		// 지도 API - 상품 주소 데이터 json 파싱
		String json="";
		if(!productList.isEmpty()) {
			for(ProductDTO product: productList) {
				json += "{\"title\":\""+product.getProduct_name()+"\",";
				json += "\"address\":\""+product.getProduct_address()+"\"},";
			}
			json = json.substring(0,json.lastIndexOf(","));
		}
		System.out.println("json"+json);
		
		model.addAttribute("boardList", boardList);
		model.addAttribute("productList", productList);
		model.addAttribute("json", json);
		
		System.out.println("MainPageController.java mainPage() 끝");
		return "main";
	}
}
