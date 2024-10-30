package com.korebap.app.view.notice;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.korebap.app.biz.board.BoardDTO;
import com.korebap.app.biz.board.BoardService;
import com.korebap.app.biz.imagefile.ImageFileDTO;
import com.korebap.app.biz.imagefile.ImageFileService;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpSession;

@Controller
public class WriteNoticeController {
	
	@Autowired
	private BoardService boardService;
	@Autowired
	private ImageFileService ImagefileService;
	
	@Autowired
	private ServletContext servletContext;
	
	@RequestMapping(value="/writeNotice.do", method=RequestMethod.GET)
	public String writeNotice() {
		
		return "writeNotice";
	}
	
	@RequestMapping(value="/writeNotice.do", method=RequestMethod.POST)
	public String writeNotice(BoardDTO boardDTO, ImageFileDTO imageFileDTO, Model model, HttpSession session) { //ArrayList<ImageFileDTO> fileList
		System.out.println("WriteNoticeController.java writeNotice() 시작");
		
		session.setAttribute("member_id", "12@naver.com");
		session.setAttribute("member_role", "ADMIN");
		
		String member_id = (String)session.getAttribute("member_id");
		String member_role = (String)session.getAttribute("member_role");
		
		if(member_id == null || !member_role.equals("ADMIN")) {
			model.addAttribute("msg", "관리자 계정으로 로그인 후 서비스 이용이 가능합니다.");
			model.addAttribute("path", "login.do");
			return "info";
		}
		
		boardDTO.setBoard_writer_id(member_id);
		System.out.println("WriteNoticeController.java writeNotice() board_writer_id : "+boardDTO.getBoard_writer_id());
		System.out.println("WriteNoticeController.java writeNotice() board_title : "+boardDTO.getBoard_title());
		System.out.println("WriteNoticeController.java writeNotice() board_content : "+boardDTO.getBoard_content());
		boardDTO.setBoard_condition("BOARD_INSERT");
		
		String[] img_paths = boardDTO.getBoard_content().split("src=\"");
		
		boolean flag = boardService.insert(boardDTO);
		
		if(!flag) {
			model.addAttribute("msg", "게시글 작성에 실패했습니다. 다시 시도해주세요.");
			model.addAttribute("path", "noticeList.do");
			return "info";
		}
		
		
		//세션에 저장되어 있는 폴더 개수를 가져옵니다. 삼항연산자로 만약 세션값이 null이 아니라면 정수형으로 변경하여 가져오도록합니다.
		int folder_session = (session.getAttribute("FOLDER_NUM") != null) ? (Integer)session.getAttribute("FOLDER_NUM"):0;
		
		
		boardDTO.setBoard_condition("BOARD_NUM_SELECTONE");
		boardDTO = boardService.selectOne(boardDTO);
		if(folder_session > 0) {
			//폴더를 명시해줍니다.
			String folder = "/board_img/"+member_id+"/"+folder_session;

			//명시된 폴더를 추가해주고 내 서버의 폴더주소를 불러옵니다.
			String uploadPath = servletContext.getRealPath(folder);
			
			System.out.println("BoardInsertAction.java 확인용 로그 : "+uploadPath);
			
			//파일이 있는지 확인하기 위해 해당 폴더의 파일을 가져옵니다.
			// File directory = new File(uploadPath);
			//폴더에 있는 파일리스트를 가져옵니다.
			
			//배열리스트로 전환
			// ArrayList<File> arr_files = new ArrayList<File>(Arrays.asList(directory.listFiles()));
			
			//보내준 파일에서 이미지 경로만 문자열 배열로 불러와 줍니다.
			System.out.println("boardDTO.getCotent = " + boardDTO.getBoard_content());
			
			
			//문자열 배열을 기준으로 for문을 돌립니다.
			for(String img_path : img_paths) {
				System.out.println("받아온 이미지 태그 : "+img_path);
				try {
					//저장된 주소+받아온 이미지 경로 끝에 큰따옴표(")가 붙어있기 때문에 제거해줍니다.
					String editorContent = uploadPath+File.separator+img_path.split("\"")[0].toString().substring(img_path.split("\"")[0].lastIndexOf("/")+1);
					//받은 이미지 로그
					System.out.println("이미지 : "+editorContent);
					
					imageFileDTO.setFile_board_num(boardDTO.getBoard_num());
					imageFileDTO.setFile_dir(editorContent);
					imageFileDTO.setFile_condition("BOARD_FILE_INSERT");
					
					//fileList.add(imageFileDTO);
					
					boolean fileFlag = ImagefileService.insert(imageFileDTO);
					
					if(!fileFlag) {
						model.addAttribute("msg", "파일 처리에 실패했습니다. 다시 시도해주세요.");
						model.addAttribute("path", "noticeList.do");
						return "info";
					}
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		
//		if(boardDTO == null) {
//			model.addAttribute("msg", "공자 사항 작성에 실패했습니다. 다시 시도해주세요.");
//			model.addAttribute("path", "noticeList.do");
//			return "info";
//		}
		System.out.println(boardDTO.getBoard_num());
		model.addAttribute("msg", "공지 사항 작성을 완료했습니다.");
		model.addAttribute("path", "noticeDetail.do?board_num="+boardDTO.getBoard_num());
		
		System.out.println("WriteNoticeController.java writeNotice() 끝");
		return "info";
	}
}
