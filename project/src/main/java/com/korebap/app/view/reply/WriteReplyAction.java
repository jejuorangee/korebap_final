package com.korebap.app.view.reply;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.korebap.app.biz.reply.ReplyDTO;
import com.korebap.app.biz.reply.ReplyService;

import jakarta.servlet.http.HttpSession;


@Controller
public class WriteReplyAction {

	@Autowired
	ReplyService replyService;

	// 로그인 확인
	// 댓글 내용
	// 댓글 작성자
	//	insert


	@RequestMapping(value="/writeReply.do")
	public String writeReply(HttpSession session, ReplyDTO replyDTO, Model model, RedirectAttributes redirectAttributes) {
		// [댓글 작성]


		// 세션에서 로그인 정보를 가져온다.
		String login_member_id = (String)session.getAttribute("member_id");

		// 경로를 저장하는 변수
		String viewName;
		// 게시글 상세페이지로 보낼 때 필요한 게시글 번호
		int reply_board_num = replyDTO.getReply_board_num();

		// DTO 데이터 로그
		System.out.println("=====com.korebap.app.view.review writeReply member_id 확인 : ["+login_member_id+"]");
		//System.out.println("=====com.korebap.app.view.review writeReply reply_board_num 확인 : ["+reply_board_num+"]");
		System.out.println("=====com.korebap.app.view.review writeReply DTO 확인 : ["+replyDTO+"]");


		if(login_member_id==null) { // 만약 로그인 상태가 아니라면 
			System.out.println("=====com.korebap.app.view.review writeReply 로그인 세션 없음");

			// 로그인 안내 후 login 페이지로 이동시킨다
			model.addAttribute("msg", "로그인이 필요한 서비스입니다.");
			model.addAttribute("path", "loginPage.do");

			// 데이터를 보낼 경로
			viewName = "info";
		}
		else {
			System.out.println("=====com.korebap.app.view.review writeReply 로그인 세션 있음");

			// Service에게 DTO를 보내 댓글 insert 진행
			boolean flag = replyService.insert(replyDTO);

			// 반환받은 값이 true라면
			if(flag) {
				// 댓글 작성 성공
				// 글 상세 페이지로 보내준다
				System.out.println("=====com.korebap.app.view.review writeReply 댓글 작성 성공");
				// 상품 상세 페이지로 보내줌
				// 리다이렉트시 쿼리 매개변수를 자동으로 URL에 포함
				// 쿼리 매개변수 == URL에서 ? 기호 뒤에 위치하는 key-value 쌍
				redirectAttributes.addAttribute("board_num", reply_board_num);
				viewName = "redirect:boardDetail.do";
			}
			else {
				// 댓글 작성 실패
				// 실패 안내와 함께 글 상세 페이지로 보내준다.
				System.out.println("=====com.korebap.app.view.review writeReply 댓글 작성 실패");

				model.addAttribute("msg", "댓글 작성에 실패했습니다. 다시 시도해주세요.");
				model.addAttribute("path", "boardDetail.do?board_num="+reply_board_num);
				viewName = "info";
			}

		}
		
		
		System.out.println("=====com.korebap.app.view.review writeReply viewName ["+viewName+"]");
		System.out.println("=====com.korebap.app.view.review writeReply 종료");
		
		return viewName;

	}

}
