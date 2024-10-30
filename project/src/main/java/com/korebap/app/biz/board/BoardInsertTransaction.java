package com.korebap.app.biz.board;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.korebap.app.biz.imagefile.ImageFileDAO2;
import com.korebap.app.biz.imagefile.ImageFileDTO;

import jakarta.servlet.http.HttpSession;


@Service("boardInsertTransaction")//어노테이션
public class BoardInsertTransaction {
	// 트랜잭션 구현을 위해 멤버변수로 추가

	@Autowired
	private BoardDAO2 boardDAO;

	@Autowired
	private ImageFileDAO2 imageFileDAO;

	// 사진 저장될 경로
	private final static String PATH="C:\\JANG\\workspace03\\project\\src\\main\\webapp\\img\\board\\";


	//@Transactional // 트랜잭션 적용
	public boolean insertBoardAndImg(HttpSession session,BoardDTO boardDTO, ImageFileDTO imageFileDTO, MultipartFile files) throws IOException {

		System.out.println("=====com.korebap.app.biz.board 트랜잭션 시작");

		String member_id = (String)session.getAttribute("member_id");
		
		// 1. 게시글 등록
		boardDTO.setBoard_condition("BOARD_INSERT");
		boardDTO.setBoard_writer_id(member_id);

		boolean insert_flag = boardDAO.insert(boardDTO);


		// 2. 게시글 번호 가져오기
		// 게시글 등록 성공시
		if(insert_flag) {
			System.out.println("=====com.korebap.app.biz.board 트랜잭션 게시글 insert 성공, 글 번호 받기/이미지 등록 시작");

			// 가장 최근 등록된 글 PK 받아오기
			boardDTO.setBoard_condition("BOARD_NUM_SELECTONE");
			boardDTO= boardDAO.selectOne(boardDTO);

			int board_num = boardDTO.getBoard_num();
			
			System.out.println("=====com.korebap.app.biz.board 트랜잭션 board_num"+board_num+"]");
			
			 // 트랜잭션 테스트를 위해 강제 오류 발생시키기
            if (true) { // 강제 예외 발생 조건
                throw new RuntimeException("강제 예외 발생! 트랜잭션 롤백 테스트");
            }


			// 3. 이미지 등록
			String originalFilename = "";

			// DTO에 들어있는 MultipartFile 객체를 가지고 온다. (업로드 된 정보를 가지고 있음)
			files = imageFileDTO.getFiles();
			System.out.println("=====com.korebap.app.biz.board 트랜잭션 files ["+files+"]");

			if (!files.isEmpty()) { // 파일이 비어있지 않은 경우(데이터가 넘어온 경우)
				// 원본 파일명을 받아온다
				originalFilename = files.getOriginalFilename();
				System.out.println("=====com.korebap.app.biz.board 트랜잭션 originalFilename ["+originalFilename+"]");


				// 파일명이 겹치는 경우를 방지하기 위해 랜덤 값 UUID 생성
				UUID uuid = UUID.randomUUID();
				// UUID + 원본파일명 결합하여 파일명 만들어준다.
				String imagepath = uuid.toString() + originalFilename;
				System.out.println("=====com.korebap.app.biz.board 트랜잭션 imagepath 파일명 ["+imagepath+"]");
				

				// 업로드 된 파일을 서버의 경로에 저장한다
				// File 메서드는 경로와 이름을 저장해준다.
				files.transferTo(new File(PATH + imagepath));

				// dir 경로를 DB에 저장
				imageFileDTO.setFile_dir(imagepath);
				imageFileDTO.setFile_board_num(board_num); // 글번호 
				imageFileDTO.setFile_condition("BOARD_FILE_INSERT"); // 글 업데이트 위한 컨디션 지정
				boolean imageFile_flag = imageFileDAO.insert(imageFileDTO);

				// 이미지 등록 실패시 트랜잭션 롤백
				if (!imageFile_flag) {
					// 단순 false 반환은 롤백 x 그대로 커밋될 수 있다.
					// 따라서 강제로 예외를 발생시켜서 롤백시킨다
					throw new RuntimeException("이미지 등록 실패");
				}
			}
			System.out.println("=====package com.korebap.app.biz.board 트랜잭션 성공 종료");

			return true; // 성공

		}
		System.out.println("=====package com.korebap.app.biz.board 게시글 등록 실패");

		return false;
	} // 트랜잭션 메서드 종료



}
