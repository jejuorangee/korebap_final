//package com.korebap.app.biz.board;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.korebap.app.biz.file.FileDTO;
//import com.korebap.app.biz.file.FileService;
//
//@Service("noticeService")
//public class NoticeService {
//	@Autowired
//	private BoardService boardService;
//	@Autowired
//	private FileService fileService;
//	
//	//@Transactional
//    public BoardDTO noticeAndFileInsert(BoardDTO boardDTO, List<FileDTO> fileList) {
//
//        // 2. 게시글 삽입
//		boardDTO.setBoard_condition("BOARD_INSERT");
//        boolean boardFlag = boardService.insert(boardDTO);
//        
//        
//        
//        if (!boardFlag) {
//            throw new RuntimeException("게시글 삽입 실패");
//        }
//
//        // 3. 삽입한 게시글 정보 가져오기
//        boardDTO.setBoard_condition("LAST_BOARD_NUM");
//        boardDTO = boardService.selectOne(boardDTO);
//        if (boardDTO == null) {
//            throw new RuntimeException("게시글 조회 실패");
//        }
//
//        // 4. 파일 삽입
//        for (FileDTO file : fileList) {
//        	file.setFile_board_num(boardDTO.getBoard_num());
//        	System.out.println("{{{{{{{{{{{{ "+boardDTO.getBoard_num()+" }}}}}}}}}}}");
//            boolean fileFlag = fileService.insert(file);
//            if (!fileFlag) {
//            	throw new RuntimeException("파일 삽입 실패: " + file.getFile_dir());
//            }
//        }
//
//        // 5. 성공적으로 처리된 게시글 반환
//        return boardDTO;
//    }
//}
