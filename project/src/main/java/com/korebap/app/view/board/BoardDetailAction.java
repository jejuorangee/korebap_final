package com.korebap.app.view.board;



import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.korebap.app.biz.board.BoardDTO;
import com.korebap.app.biz.board.BoardService;
import com.korebap.app.biz.goodLike.GoodLikeDTO;
import com.korebap.app.biz.goodLike.GoodLikeService;
import com.korebap.app.biz.imagefile.ImageFileDTO;
import com.korebap.app.biz.imagefile.ImageFileService;
import com.korebap.app.biz.reply.ReplyDTO;
import com.korebap.app.biz.reply.ReplyService;

import jakarta.servlet.http.HttpSession;

@Controller
public class BoardDetailAction {
	// 게시글
	// 댓글
	// 파일
	// 좋아요 수
	
	@Autowired
	BoardService boardService;
	@Autowired
	ImageFileService fileService;
	@Autowired
	ReplyService replyService;
	@Autowired
	GoodLikeService goodLikeService;
	
	
	@RequestMapping(value="/boardDetail.do")
	public String boardDetail(HttpSession session, BoardDTO boardDTO,ImageFileDTO imagefileDTO, ReplyDTO replyDTO,
			@RequestParam int board_num, GoodLikeDTO goodLikeDTO, Model model) {
		System.out.println("=====com.korebap.app.view.board boardDetail 시작");
		
		
		// Session에 저장된 로그인 아이디 확인
		String member_id = (String)session.getAttribute("member_id");
		
		// 데이터 로그
		System.out.println("=====com.korebap.app.view.board boardDetail member_id 확인 : ["+member_id+"]");
		System.out.println("=====com.korebap.app.view.board boardDetail board_num 확인 : ["+board_num+"]");
		
		
		// 글 번호로 게시글 찾기
		boardDTO.setBoard_condition("BOARD_SELECT_ONE");
		boardDTO = boardService.selectOne(boardDTO);
		
		System.out.println("=====com.korebap.app.view.board boardDetail boardDTO 확인 : ["+boardDTO+"]");

		// 게시글 번호를 DTO에 넣어서 전체 파일을 불러온다.
		imagefileDTO.setFile_board_num(board_num);
		imagefileDTO.setFile_condition("BOARD_FILE_SELECTALL");
		List<ImageFileDTO> fileList = new ArrayList<ImageFileDTO>();
		fileList = fileService.selectAll(imagefileDTO);
		
//		List<ImageFileDTO> dirList = new ArrayList<>();
//
//		for (ImageFileDTO file : fileList) {
//			dirList.add(file.getFile_dir());
//		}
		

		// 게시글 번호를 DTO에 넣어서 전체 댓글을 불러온다.
		replyDTO.setReply_board_num(board_num);
		List<ReplyDTO> replyList=new ArrayList<ReplyDTO>();
		replyList = replyService.selectAll(replyDTO);
		
		// 로그
		System.out.println("=====com.korebap.app.view.board boardDetail boardDTO 확인 : ["+boardDTO+"]");
		System.out.println("=====com.korebap.app.view.board boardDetail fileList 확인 : ["+fileList+"]");
		System.out.println("=====com.korebap.app.view.board boardDetail replyList 확인 : ["+replyList+"]");

		
		
		
//		if (member_id != null) {
//			System.out.println("=====com.korebap.app.view.board boardDetail 로그인 세션 있음");
//			
//			goodLikeDTO.setGoodLike_board_num(board_num);
//			goodLikeDTO.setGoodLike_member_id(member_id);
//            
//			// 회원의 아이디와 글 번호를 보내 데이터가 있는지 확인한다.
//            goodLikeDTO = goodLikeService.selectOne(goodLikeDTO);
//            
//            // 만약 DTO 객체가 있다면 true, 없다면 false
//            model.addAttribute("like_member", goodLikeDTO != null ? "true" : "false");
//            
//        }
		
		
		model.addAttribute("board", boardDTO);
		model.addAttribute("fileList", fileList);
		model.addAttribute("replyList", replyList);
		
		System.out.println("=====com.korebap.app.view.board boardDetail 종료");

		// 글 상세페이지로 이동시킴
		return "boardDetails";
	}

}





//
//@Override
//public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
//	System.out.println("BoardDetailsAction 시작");
//	int board_num = Integer.parseInt(request.getParameter("board_num"));
//	String member_id=(String)request.getSession().getAttribute("member_id");
//	//데이터 로그
//	System.out.println("board_num : "+board_num);
//	System.out.println("member_id : "+member_id);
//	
//	BoardDTO boardDTO=new BoardDTO();
//	boardDTO.setBoard_num(board_num); // 글번호
//	boardDTO.setBoard_condition("BOARD_SELECT_ONE");
//	
//	BoardDAO boardDAO=new BoardDAO();
//	boardDTO = boardDAO.selectOne(boardDTO); // 글번호로 게시글 찾기
//	
//	//데이터 로그
//	System.out.println("board_num : "+boardDTO.getBoard_num());
//	System.out.println("board_title : "+boardDTO.getBoard_title());
//	System.out.println("board_regist_date : "+boardDTO.getBoard_registration_date());
//	
//	FileDTO fileDTO=new FileDTO();
//	fileDTO.setFile_board_num(board_num);
//	fileDTO.setFile_condition("BOARD_FILE_SELECTALL");
//	FileDAO fileDAO=new FileDAO();
//	ArrayList<FileDTO> fileList = new ArrayList<FileDTO>();
//	fileList = fileDAO.selectAll(fileDTO); // 글 번호로 해당 글 파일 찾기
//	
//	ReplyDTO replyDTO=new ReplyDTO();
//	replyDTO.setReply_board_num(board_num);
//	ReplyDAO replyDAO=new ReplyDAO();
//	ArrayList<ReplyDTO> replyList=new ArrayList<ReplyDTO>();
//	replyList = replyDAO.selectAll(replyDTO);
//	System.out.println(replyList);
//	
//	
//	if (member_id != null) {
//        System.out.println("boardDetailAction 로그 : 사용자 세션이 있습니다");
//        GoodLikeDTO likeDTO=new GoodLikeDTO();
//        likeDTO.setGoodLike_board_num(board_num);
//        likeDTO.setGoodLike_member_id(member_id);
//        
//        GoodLikeDAO likeDAO=new GoodLikeDAO();
//        likeDTO = likeDAO.selectOne(likeDTO);
//        
//        request.setAttribute("like_member", likeDTO != null ? "true" : "false");
//        /*if(likeDTO !=null) { // 사용자가 좋아요를 눌렀다면
//        	request.setAttribute("like_member", "true");
//        }
//        else { // 사용자가 좋아요를 안눌렀다면
//        	request.setAttribute("like_member", "false");
//        }*/
//    }
//	
//	
//	request.setAttribute("board", boardDTO);
//	request.setAttribute("fileList", fileList);
//	request.setAttribute("replyList", replyList);
//	
//	ActionForward forward=new ActionForward();
//	forward.setPath("boardDetails.jsp");
//	forward.setRedirect(false);
//	System.out.println("BoardDetailsAction 끝");
//	return forward;
//}