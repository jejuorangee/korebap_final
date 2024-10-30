package com.korebap.app.view.page;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.korebap.app.biz.imagefile.ImageFileDTO;
import com.korebap.app.biz.imagefile.ImageFileService;
import com.korebap.app.biz.member.MemberDTO;
import com.korebap.app.biz.member.MemberService;
import com.korebap.app.biz.product.ProductDTO;
import com.korebap.app.biz.product.ProductService;
import com.korebap.app.view.util.LoginCheck;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class PaymentInfoPageAction {

    @Autowired
    private ProductService productService;

    @Autowired
    private ImageFileService imageFileService;

    @Autowired
    private MemberService memberService;

    @PostMapping("/paymentInfoPage.do")
    public String paymentInfoPage(HttpServletRequest request, Model model,
                                   @RequestParam("reservation_date") String reservationDate,
                                   @RequestParam("product_num") int productNum,
                                   MemberDTO memberDTO,
                                   ProductDTO productDTO,
                                   ImageFileDTO fileDTO) {
    	System.out.println("PaymentInfoPageAction.java paymentInfoPage() 시작");
        String loginMemberId = LoginCheck.loginCheck(request);
        
        if (loginMemberId.isEmpty()) {
            model.addAttribute("msg", "로그인이 필요한 서비스입니다.");
            model.addAttribute("path", "login.do");
            return "info"; // info.jsp
        }
        productDTO.setProduct_num(productDTO.getProduct_num());
		productDTO.setProduct_condition("PRODUCT_BY_INFO");
        productDTO = productService.selectOne(productDTO);
        if (productDTO == null) {
            model.addAttribute("msg", "상품을 찾을 수 없습니다. 다시 시도해주세요.");
            model.addAttribute("path", "main.do");
            return "info"; // info.jsp
        }

        fileDTO.setFile_product_num(productNum);
        fileDTO.setFile_condition("PRODUCT_FILE_SELECTALL");
        List<ImageFileDTO> fileList = imageFileService.selectAll(fileDTO);
        //ArrayList<ImageFileDTO> fileList = (ArrayList<ImageFileDTO>) imageFileService.selectAll(fileDTO);

        memberDTO.setMember_id(loginMemberId);
		memberDTO.setMember_condition("MYPAGE");
        memberDTO = memberService.selectOne(memberDTO);
        System.out.println(memberDTO);
        if (memberDTO == null) {
            model.addAttribute("msg", "사용자 정보를 찾을 수 없습니다. 다시 시도해주세요.");
            model.addAttribute("path", "main.do");
            return "info"; // info.jsp
        }

        model.addAttribute("reservation_date", reservationDate);
        model.addAttribute("product", productDTO);
        model.addAttribute("fileList", fileList);
        model.addAttribute("member", memberDTO);
        System.out.println("리저베이션으로 이동중~~~~~~~~~~~");
        System.out.println("PaymentInfoPageAction.java paymentInfoPage() 끝");
        return "reservation"; // reservation.jsp
    }
}


//package com.korebap.app.view.page;
//
//import java.util.ArrayList;
//
//import com.korebap.app.biz.imagefile.ImageFileService;
//import com.korebap.app.biz.imagefile.ImageFileDTO;
//import com.korebap.app.biz.member.MemberService;
//import com.korebap.app.biz.member.MemberDTO;
//import com.korebap.app.biz.product.ProductService;
//import com.korebap.app.biz.product.ProductDTO;
//import com.korebap.app.view.common.Action;
//import com.korebap.app.view.common.ActionForward;
//import com.korebap.app.view.util.LoginCheck;
//
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
//public class PaymentInfoPageAction implements Action {
//
//	@Override
//	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
//		System.out.println("PaymentInfoPageAction 시작");
//		
//		String login_member_id = LoginCheck.loginCheck(request);
//		ActionForward forward=new ActionForward();
//		if(login_member_id.equals("")) {
//			System.out.println("로그인 세션 없음");
//			request.setAttribute("msg", "로그인이 필요한 서비스입니다.");
//			request.setAttribute("path", "loginPage.do");
//			
//			forward.setPath("info.jsp");
//			forward.setRedirect(false);
//			return forward;
//		}
//		
//		// 상품명 카테고리 사용예정일 예약자명 전화번호 결제 금액
//		String reservation_date = request.getParameter("reservation_date");
//		int product_num = Integer.parseInt(request.getParameter("product_num"));
//		
//		ProductDTO productDTO = new ProductDTO();
//		productDTO.setProduct_num(product_num);
//		productDTO.setProduct_condition("PRODUCT_BY_INFO");
//		
//		ProductService productService = new ProductService();
//		productDTO = productService.selectOne(productDTO);
//		
//		if(productDTO == null) {
//			request.setAttribute("msg", "상품을 찾을 수 없습니다. 다시 시도해주세요.");
//			request.setAttribute("path", "main.do");
//			
//			forward.setPath("info.jsp");
//			forward.setRedirect(false);
//			return forward;
//		}
//		
//		ImageFileDTO fileDTO = new ImageFileDTO();
//		fileDTO.setFile_product_num(product_num);
//		fileDTO.setFile_condition("PRODUCT_FILE_SELECTALL");
//		
//		ImageFileService fileService = new ImageFileService();
//		ArrayList<ImageFileDTO> fileList = fileService.selectAll(fileDTO);
//		
//		MemberDTO memberDTO = new MemberDTO();
//		memberDTO.setMember_id(login_member_id);
//		memberDTO.setMember_condition("CHECK_MEMBER_ID");
//		
//		MemberService memberService = new MemberService();
//		memberDTO = memberService.selectOne(memberDTO);
//		
//		if(memberDTO == null) {
//			request.setAttribute("msg", "사용자 정보를 찾을 수 없습니다. 다시 시도해주세요.");
//			request.setAttribute("path", "main.do");
//			
//			forward.setPath("info.jsp");
//			forward.setRedirect(false);
//			return forward;
//		}
//		
//		request.setAttribute("reservation_date", reservation_date);
//		request.setAttribute("product", productDTO);
//		request.setAttribute("fileList", fileList);
//		request.setAttribute("member", memberDTO);
//		System.out.println("70 product_name: "+productDTO.getProduct_name());
//		System.out.println("71 member_name : "+memberDTO.getMember_name());
//		forward.setPath("reservation.jsp");
//		forward.setRedirect(false);
//		
//		System.out.println("PaymentInfoPageAction 끝");
//		return forward;
//	}
//
//}
