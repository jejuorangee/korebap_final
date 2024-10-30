package com.korebap.app.view.async;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.korebap.app.biz.board.BoardDTO;
import com.korebap.app.biz.board.BoardService;
import com.korebap.app.biz.goodLike.GoodLikeDTO;
import com.korebap.app.biz.goodLike.GoodLikeService;

import jakarta.servlet.http.HttpSession;

// Board 관련 비동기 모아둔 class

@RestController
public class Board {

	@Autowired
	GoodLikeService goodLikeService;

	@Autowired
	BoardService boardService;

	@RequestMapping(value="/boardLike.do", method=RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> boardLike(HttpSession session, @RequestBody GoodLikeDTO goodLikeDTO) {
		// 좋아요 비동기
		System.out.println("=====com.korebap.app.view.async boardLike 비동기 시작");

		// json 타입으로 반환하기 위한 Map 객체 생성
		Map<String, Object> jsonResponse = new HashMap<>();

		int board_num = goodLikeDTO.getGoodLike_board_num(); // 글 번호
		String member_id = (String)session.getAttribute("member_id"); //로그인된 id

		// 데이터 로그
		System.out.println("=====com.korebap.app.view.async boardLike board_num ["+board_num+"]");
		System.out.println("=====com.korebap.app.view.async boardLike member_id ["+member_id+"]");

		if (member_id == null) {
			jsonResponse.put("message", "로그인 후 좋아요를 눌러주세요.");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(jsonResponse);
		}


		// 저장된 아이디와 글 번호를 넣고 service에게 보내서 반환 (좋아요 여부 확인하기 위함)
		goodLikeDTO.setGoodLike_member_id(member_id);
		goodLikeDTO.setGoodLike_board_num(board_num);

		GoodLikeDTO newLikeDTO = goodLikeService.selectOne(goodLikeDTO);

		System.out.println("=====com.korebap.app.view.async boardLike newLikeDTO ["+newLikeDTO+"]");

		//response.setContentType("application/json"); // JSON으로 응답 설정

		boolean flag;
		//		String jsonResponse;
		//		PrintWriter out=response.getWriter();

		//		// 새로운 DTO를 만들어서 id와 글 번호를 set 해준다.
		//		GoodLikeDTO newLikeDTO = new GoodLikeDTO();
		//		newLikeDTO.setGoodLike_member_id(member_id);
		//		newLikeDTO.setGoodLike_board_num(board_num);
		//		

		// 글 번호, id를 넣어서 selectOne으로 내역을 찾아온다. 
		// 있으면 boardnum, memberid 객체 반환해줌
		// 없다면 null (좋아요 한 내역이 없다면)

		// DTO 객체가 null이라면 (좋아요 한 내역이 없다면)
		if(newLikeDTO == null) {
			System.out.println("=====com.korebap.app.view.async boardLike 기존에 좋아요 한 내역 없음-insert");

			// 기존 id와 board_num 넣은 객체를 보내서 좋아요 insert
			flag = goodLikeService.insert(goodLikeDTO);
			//			BoardDAO boardDAO = new BoardDAO();
			//			boardDTO = boardService.selectOne(boardDTO);
			// json형식으로 데이터 set
			//jsonResponse = "{\"flag\": " + "true" + "," + "\"likeCnt\": " + boardDTO.getBoard_like_cnt() + "}";
		}
		else {
			System.out.println("=====com.korebap.app.view.async boardLike 기존에 좋아요 한 내역 있음-delete");

			// 기존에 좋아요 한 데이터 객체를 보내서 delete
			flag = goodLikeService.delete(newLikeDTO);
			//			boardDTO.setBoard_num(board_num);
			//			boardDTO.setBoard_condition("BOARD_SELECT_ONE");

			//			BoardDAO boardDAO = new BoardDAO();
			//			boardDTO = boardDAO.selectOne(boardDTO);
			//			// json형식으로 데이터 set
			//jsonResponse = "{\"flag\": " + "false" + "," + "\"likeCnt\": " + boardDTO.getBoard_like_cnt() + "}";
		}

		BoardDTO boardDTO = new BoardDTO();
		boardDTO.setBoard_num(board_num);
		boardDTO.setBoard_condition("BOARD_SELECT_ONE");

		boardDTO = boardService.selectOne(boardDTO); // 좋아요 개수 가져오기

		jsonResponse.put("flag",flag);
		jsonResponse.put("likeCnt",boardDTO.getBoard_like_cnt());
		// 		 

		// Controller에서 데이터를 json으로 반환하기 위해 @ResponseBody, ResponseEntity 를 사용할 수 있다.
		// ResponseEntity : 사용자의 HttpRequest에 대한 응답 데이터를 포함하는 클래스
		//		반환값에 상태 코드와 응답 메세지를 주고 싶을 떄 사용

		return ResponseEntity.ok(jsonResponse);

		//		out.print(jsonResponse);
		//		out.flush();


	} // 좋아요 메서드 종료





}
