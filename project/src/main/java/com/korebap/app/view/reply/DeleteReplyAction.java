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
public class DeleteReplyAction {

	@Autowired
	ReplyService replyService;

	@RequestMapping(value="/deleteReply.do")
	public String deleteReply(HttpSession session, ReplyDTO replyDTO,Model model, RedirectAttributes redirectAttributes) {
		// [ 댓글 삭제 ]

		System.out.println("=====com.korebap.app.view.reply deleteReply 시작");

		// 경로를 담을 변수
		String viewName;

		// 상세페이지로 이동시키기 위해 변수 선언
		int board_num = replyDTO.getReply_board_num();

		// 세션에서 로그인 정보를 가져와 DTO의 member_id에 담아준다. (M에게도 전달 필요)
		String login_member_id = (String)session.getAttribute("member_id");


		// 데이터 로그
		System.out.println("=====com.korebap.app.view.reply deleteReply member_id 확인 : ["+login_member_id+"]");
		System.out.println("=====com.korebap.app.view.reply deleteReply board_num 확인 : ["+board_num+"]");
		System.out.println("=====com.korebap.app.view.reply deleteReply reply_num 확인 : ["+replyDTO.getReply_num()+"]");


		if(login_member_id.equals("")) { // 만약 로그인 상태가 아니라면 
			System.out.println("=====com.korebap.app.view.reply deleteReply 로그인 세션 없음");

			// 로그인 안내 후 login 페이지로 이동시킨다
			model.addAttribute("msg", "로그인이 필요한 서비스입니다.");
			model.addAttribute("path", "loginPage.do");

			// 데이터를 보낼 경로
			viewName = "info";
		}
		else {

			// 데이터 로그

			// service에 삭제 요청 보낸다
			boolean flag = replyService.delete(replyDTO);

			if(flag) { // 삭제 성공시
				System.out.println("=====com.korebap.app.view.reply deleteReply 댓글 삭제 성공");

				redirectAttributes.addAttribute("board_num", board_num);

				viewName = "redirect:boardDetail.do";

			}
			else { // 삭제 실패시
				System.out.println("=====com.korebap.app.view.reply deleteReply 댓글 삭제 실패");

				model.addAttribute("msg", "댓글 삭제 실패. 다시 시도하세요.");
				model.addAttribute("path", "boardDetail.do?board_num="+board_num);

				viewName = "info";
			}

		}
		
		System.out.println("=====com.korebap.app.view.reply deleteReply viewName ["+viewName+"]");
		return viewName;

	}
	
	
	
}





//@Controller
//public class DeleteReplyAction {
//	
//	@Autowired
//	ReplyService replyService;
//
//	@RequestMapping(value="/deleteReply.do")
//	public String deleteReply(HttpSession session, ReplyDTO replyDTO) {
//		System.out.println("DeleteReplyAction 시작");
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
//		int reply_num = Integer.parseInt(request.getParameter("reply_num"));
//		int board_num = Integer.parseInt(request.getParameter("board_num"));
//		
//		//데이터 로그
//		System.out.println("reply_num : "+reply_num);
//		System.out.println("board_num : "+board_num);
//		
//		ReplyDTO replyDTO=new ReplyDTO();
//		replyDTO.setReply_num(reply_num);
//		
//		ReplyDAO replyDAO=new ReplyDAO();
//		boolean flag = replyDAO.delete(replyDTO);
//		
//		if(flag) {
//			System.out.println("DeleteReplyAction 로그 : 댓글 삭제 성공");
//			forward.setPath("boardDetail.do?board_num="+board_num);
//			forward.setRedirect(true);
//		}
//		else {
//			System.out.println("DeleteReplyAction 로그 : 댓글 삭제 실패");
//			request.setAttribute("msg", "댓글 삭제 실패. 다시 시도하세요.");
//			request.setAttribute("path", "boardDetail.do?board_num="+board_num);
//			
//			forward.setPath("info.jsp");
//			forward.setRedirect(false);
//		}
//		System.out.println("DeleteReplyAction 끝");
//		return forward;
//	}
//}
