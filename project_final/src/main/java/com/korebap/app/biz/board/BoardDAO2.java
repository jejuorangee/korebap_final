package com.korebap.app.biz.board;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class BoardDAO2 { // 게시판
	// 게시글 작성
	private final String BOARD_INSERT = "INSERT INTO BOARD (BOARD_TITLE,BOARD_CONTENT,BOARD_WRITER_ID) VALUES(?,?,?)";
	// 게시글 수정
	private final String BOARD_UPDATE = "UPDATE BOARD SET BOARD_TITLE=COALESCE(?,BOARD_TITLE), BOARD_CONTENT=COALESCE(?,BOARD_CONTENT) WHERE BOARD_NUM=?";
	// 게시글 삭제
	private final String BOARD_DELETE = "DELETE FROM BOARD WHERE BOARD_NUM=?";
	///////////////////////////////////////////////////////////////////////////////////////////////////////
	// 공지사항 총개수
	private final String NOTICE_TOTAL_COUNT = "SELECT COUNT(*) AS NOTICE_TOTAL_COUNT FROM BOARD b JOIN MEMBER m ON b.BOARD_WRITER_ID = m.MEMBER_ID WHERE m.MEMBER_ROLE = 'ADMIN'";
	
	// 페이지별 데이터
	private final String NOTICE_SELECT_ALL = "SELECT BOARD_NUM, BOARD_TITLE\r\n"
			+ "FROM BOARD\r\n"
			+ "ORDER BY BOARD_NUM DESC\r\n"
			+ "LIMIT ?,10";
///////////////////////////////////////////////////////////////////////////////////////////////////////
	// 게시물 총 개수
	private final String BOARD_SELECTONE_COUNT = "SELECT COUNT(BOARD_NUM) FROM BOARD";
	// 게시판 전체 출력 통합
	private final String BOARD_SELECTALL="SELECT BOARD_NUM, BOARD_TITLE, BOARD_REGISTRATION_DATE, LIKE_COUNT,FILE_DIR\r\n"
			+ "FROM (\r\n"
			+ "    SELECT BOARD_NUM, BOARD_TITLE, BOARD_CONTENT, BOARD_REGISTRATION_DATE, LIKE_COUNT, FILE_DIR,\r\n"
			+ "           ROW_NUMBER() OVER (\r\n"
			+ "               ORDER BY \r\n"
			+ "               CASE WHEN ? IS NOT NULL AND ? = 'like' THEN LIKE_COUNT \r\n"
			+ "                   ELSE BOARD_NUM \r\n"
			+ "               END DESC\r\n"
			+ "           ) AS ROW_NUM\r\n"
			+ "    FROM BOARD_INFO_VIEW\r\n"
			+ "    WHERE (? IS NULL OR BOARD_TITLE LIKE CONCAT('%',?,'%') OR BOARD_CONTENT LIKE CONCAT('%',?,'%'))\r\n"
			+ ") as subqeury\r\n" 
			+ "WHERE ROW_NUM BETWEEN (COALESCE(?,1)-1)*9+1 AND COALESCE(?,1)*9";
	// 내 글 전체보기
	private final String BOARD_SELECTALL_MY_BOARD = "SELECT MB.BOARD_NUM, MB.BOARD_TITLE, MB.BOARD_REGISTRATION_DATE, MB.MEMBER_NICKNAME,MB.LIKE_COUNT, MB.FILE_DIR\r\n"
			+ "FROM (SELECT B.BOARD_NUM, B.BOARD_TITLE, B.BOARD_REGISTRATION_DATE, M.MEMBER_NICKNAME, (SELECT COUNT(G.LIKE_BOARD_NUM) FROM GOODLIKE G WHERE G.LIKE_BOARD_NUM=B.BOARD_NUM) AS LIKE_COUNT, I.FILE_DIR,\r\n"
			+ "ROW_NUMBER() OVER(ORDER BY BOARD_NUM DESC) AS ROW_NUM\r\n" + "FROM BOARD B\r\n"
			+ "JOIN MEMBER M ON B.BOARD_WRITER_ID = M.MEMBER_ID\r\n"
			+ "LEFT JOIN (SELECT FILE_DIR, BOARD_ITEM_NUM FROM (SELECT FILE_DIR, BOARD_ITEM_NUM, ROW_NUMBER() OVER(PARTITION BY BOARD_ITEM_NUM ORDER BY FILE_NUM)AS RN FROM IMAGEFILE)as inner_query WHERE RN=1)I ON B.BOARD_NUM = I.BOARD_ITEM_NUM\r\n"
			+ "WHERE B.BOARD_WRITER_ID = ?)MB\r\n" + "WHERE ROW_NUM BETWEEN (COALESCE(?,1)-1)*9+1 AND COALESCE(?,1)*9";
	// 글 상세페이지 출력(제목, 내용, 작성일, 작성자ID, 작성자 닉네임, 좋아요 개수)
	private final String BOARD_SELECTONE = "SELECT B.BOARD_NUM, B.BOARD_TITLE, B.BOARD_CONTENT, B.BOARD_REGISTRATION_DATE, COALESCE(B.BOARD_WRITER_ID,'탈퇴한 회원입니다') AS MEMBER_ID, COALESCE(M.MEMBER_NICKNAME, '탈퇴한 회원입니다.') AS MEMBER_NICKNAME,(SELECT COUNT(LIKE_BOARD_NUM) FROM GOODLIKE G WHERE G.LIKE_BOARD_NUM=B.BOARD_NUM) AS LIKE_COUNT\r\n"
			+ "FROM BOARD B\r\n" + "LEFT JOIN MEMBER M ON B.BOARD_WRITER_ID = M.MEMBER_ID\r\n" + "WHERE B.BOARD_NUM=?";
	// 전체 페이지 수 출력 (기본)
	private final String BOARD_TOTAL_PAGE = "SELECT CEIL(COALESCE(COUNT(BOARD_NUM),0)/9.0)AS BOARD_TOTAL_PAGE FROM BOARD WHERE 1=1";
	// 전체 페이지 수 출력 (키워드 검색 페이지 수)
	private final String BOARD_SEARCH_PAGE = "AND BOARD_TITLE LIKE CONCAT('%',?,'%') OR BOARD_CONTENT LIKE CONCAT('%',?,'%')";
	// 전체 페이지 수 출력 (내가 쓴 글 페이지 수)
	private final String BOARD_MYBOARD_PAGE = "AND BOARD_WRITER_ID = ?";

	// 크롤링 - 게시판 pk 출력
	private final String BOARD_NUM_SELECTONE = "SELECT MAX(BOARD_NUM) AS MAX_NUM FROM BOARD";
	// 크롤링 - 게시판 전체 출력
	private final String BOARD_SELECT = "SELECT BOARD_NUM, BOARD_TITLE, BOARD_CONTENT, BOARD_WRITER_ID, BOARD_REGISTRATION_DATE FROM BOARD";

	@Autowired
	private JdbcTemplate jdbcTemplate;

	//@Transactional
	public boolean insert(BoardDTO boardDTO){   // 입력(생성)
		System.out.println("model.BoardDAO.insert 시작");

		String board_title = boardDTO.getBoard_title();
		String board_content = boardDTO.getBoard_content();
		String board_writer_id = boardDTO.getBoard_writer_id();

		int result=0;

		if(boardDTO.getBoard_condition().equals("BOARD_INSERT")) {
			System.out.println("model.BoardDAO.insert 시작");
			// 글 작성
			result = jdbcTemplate.update(BOARD_INSERT,board_title,board_content,board_writer_id);
		} 
		if (result <= 0) { // 변경된 행 수가 0보다 작거나 같다면
			System.out.println("model.BoardDAO.insert 실패");
			return false;
		}
		System.out.println("model.BoardDAO.insert 성공");
		return true; // 성공적으로 삽입 완료
	}

	public boolean update(BoardDTO boardDTO){   // 업데이트
		System.out.println("model.BoardDAO.update 시작");
		// 글 수정
		String board_title = boardDTO.getBoard_title();
		String board_content = boardDTO.getBoard_content();
		int board_num = boardDTO.getBoard_num();

		int result = jdbcTemplate.update(BOARD_UPDATE,board_title,board_content,board_num);

		System.out.println("model.BoardDAO.update result : " + result);

		if(result<=0) {
			System.out.println("model.BoardDAO.update 실패");
			// 만약 반환된 수가 0보다 작으면 변경된 행이 없다는 뜻이므로 false 반환
			return false;
		}
		System.out.println("model.BoardDAO.update 성공");
		return true;
	}


	public boolean delete(BoardDTO boardDTO){   // 삭제
		System.out.println("model.BoardDAO.delete 시작");

		int board_num = boardDTO.getBoard_num();

		int result = jdbcTemplate.update(BOARD_DELETE,board_num);	
		System.out.println("model.BoardDAO.delete result : " + result);

		if(result<=0) {
			System.out.println("model.BoardDAO.delete 실패");
			return false;
		}
		System.out.println("model.BoardDAO.delete 성공");
		return true;
	}

	public List<BoardDTO> selectAll(BoardDTO boardDTO) { // 전체 출력
		System.out.println("model.BoardDAO.selectAll 시작");
		ArrayList<BoardDTO> datas=new ArrayList<>();
		List<BoardDTO> datas2 = new ArrayList<BoardDTO>();

		// 글 전체 출력 통합
		if (boardDTO.getBoard_condition().equals("BOARD_ALL")) {
			System.out.println("model.BoardDAO.selectAll_글 전체 출력 시작");
			Object[] args = {
					boardDTO.getBoard_search_criteria(), // 게시판 정렬 기준
					boardDTO.getBoard_search_criteria(), // 게시판 정렬 기준
					boardDTO.getBoard_searchKeyword(),    // 검색어
					boardDTO.getBoard_searchKeyword(),    // title에서 찾기
					boardDTO.getBoard_searchKeyword(),    // content에서 찾기
					boardDTO.getBoard_page_num(),         // 페이지 번호 (첫 데이터)
					boardDTO.getBoard_page_num()          // 페이지 번호 (마지막 데이터)
			};
			datas = (ArrayList<BoardDTO>) jdbcTemplate.query(BOARD_SELECTALL, args, new BoardRowMapper_all());
		} 
		// 내가 쓴 글 전체 목록 출력
		else if (boardDTO.getBoard_condition().equals("MYBOARD_LIST")) {
			System.out.println("model.BoardDAO.selectAll_내 글 전체 출력 시작");
			Object[] args ={
					boardDTO.getBoard_writer_id(), // 작성자 id
					boardDTO.getBoard_page_num(),   // 페이지 번호 (첫 데이터)
					boardDTO.getBoard_page_num()    // 페이지 번호 (마지막 데이터)
			};	        
			datas = (ArrayList<BoardDTO>) jdbcTemplate.query(BOARD_SELECTALL_MY_BOARD, args, new BoardRowMapper_all_myboard_list());
		}
		else if(boardDTO.getBoard_condition().equals("NOTICE_SELECT_ALL")) {
			System.out.println("notice_Select_all 시작");
			Object[] args = {boardDTO.getBoard_page_num()};
			datas2 = jdbcTemplate.query(NOTICE_SELECT_ALL, args, new BoardRowMapper_notice_all());
			return datas2;
		}
		else {
			System.err.println("model.BoardDAO.selectAll 실패");
			return null; // 빈 리스트 반환
		}
		System.out.println("model.BoardDAO.selectAll 성공");
		return datas;
	}

	//@Transactional
	public BoardDTO selectOne(BoardDTO boardDTO) { 
		System.out.println("model.BoardDAO.selectOne 시작");
		//BoardRowMapper boardRowMapper = new BoardRowMapper(); // 미리 생성
		BoardDTO data = null;
		// 글 상세페이지 출력
		if (boardDTO.getBoard_condition().equals("BOARD_SELECT_ONE")) {
			System.out.println("model.BoardDAO.selectOne 글 상세페이지 출력 쿼리 읽어오기 시작");
			Object[] args = { boardDTO.getBoard_num() }; // 글 번호
			data = jdbcTemplate.queryForObject(BOARD_SELECTONE, args, new BoardRowMapper_one());
		}
		// 전체 게시판 개수
		else if (boardDTO.getBoard_condition().equals("BOARD_SELECTONE_COUNT")) {
			System.out.println("model.BoardDAO.selectOne 게시판 쿼리 읽어오기 시작");
			// 쿼리 실행 및 데이터 매핑은 RowMapper에서 처리
			data = jdbcTemplate.queryForObject(BOARD_SELECTONE_COUNT, new Object[]{}, new BoardRowMapper_one_count());
		}
		// 가장 최근 게시글 번호를 받아오기
		else if (boardDTO.getBoard_condition().equals("LAST_BOARD_NUM")) {
			System.out.println("model.BoardDAO.selectOne 최근 글 번호 1개만 출력 쿼리 읽어오기 시작");
			data = jdbcTemplate.queryForObject(BOARD_NUM_SELECTONE, new Object[]{}, new BoardRowMapper_one_num());
		}
		// 크롤링 select
		else if (boardDTO.getBoard_condition().equals("BOARD_SELECT")) {
			System.out.println("model.BoardDAO.selectOne 글 번호 반환하기 쿼리 읽어오기 시작");
			// 쿼리 실행 및 데이터 매핑은 RowMapper에서 처리
			data = jdbcTemplate.queryForObject(BOARD_SELECT, new Object[]{},new BoardRowMapper_one_select() );
		}
		else if(boardDTO.getBoard_condition().equals("NOTICE_TOTAL_COUNT")) {
			System.out.println("NOTICE_TOTAL_COUNT 공지사항 총개수 SELECT");
			data = jdbcTemplate.queryForObject(NOTICE_TOTAL_COUNT, new NoticeRowMapper_total_page());
		}
		// 페이지네이션: 전체 페이지 수 반환
		else if (boardDTO.getBoard_condition().equals("BOARD_PAGE_COUNT")) {
			System.out.println("model.BoardDAO.selectOne 게시판 전체 데이터 수 반환하기 쿼리 읽어오기 시작");
			Object[] args;
			if (boardDTO.getBoard_searchKeyword() != null && !boardDTO.getBoard_searchKeyword().isEmpty()) {
				System.out.println("model.BoardDAO.selectOne 페이지 수 : 키워드 있을 때 쿼리");
				args = new Object[]{boardDTO.getBoard_searchKeyword(), boardDTO.getBoard_searchKeyword()};
				data = jdbcTemplate.queryForObject(BOARD_TOTAL_PAGE + " " + BOARD_SEARCH_PAGE, args, new BoardRowMapper_one_page_count());
			} else if (boardDTO.getBoard_writer_id() != null && !boardDTO.getBoard_writer_id().isEmpty()) {
				System.out.println("model.BoardDAO.selectOne 페이지 수 : 내가 쓴 글 전체보기 쿼리");
				args = new Object[]{boardDTO.getBoard_writer_id()};
				data = jdbcTemplate.queryForObject(BOARD_TOTAL_PAGE + " " + BOARD_MYBOARD_PAGE, args, new BoardRowMapper_one_page_count());
			} else {
				System.out.println("model.BoardDAO.selectOne 페이지 수 : 전체보기 쿼리");
				data = jdbcTemplate.queryForObject(BOARD_TOTAL_PAGE, new Object[]{}, new BoardRowMapper_one_page_count());
			}
		} else {
			System.err.println("model.BoardDAO.selectOne SQL문 읽어오기 Condition 실패");
			return null; // 조건이 맞지 않으면 null 반환
		}

		System.out.println("model.BoardDAO.selectOne data : " + data);
		return data;
	}
	
	class BoardRowMapper_notice_all implements RowMapper<BoardDTO>{

		@Override
		public BoardDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			BoardDTO data = new BoardDTO();
			
			data.setBoard_num(rs.getInt("BOARD_NUM"));
			data.setBoard_title(rs.getString("BOARD_TITLE"));
			return data;
		}
	}


	class BoardRowMapper_all implements RowMapper<BoardDTO> {
		// 게시판 전체 출력
		@Override
		public BoardDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			System.out.println("model.BoardRowMapper_all 실행");

			BoardDTO data = new BoardDTO();

			data.setBoard_num(rs.getInt("BOARD_NUM")); // 게시판 번호
			data.setBoard_title(rs.getString("BOARD_TITLE")); // 게시판 제목
			data.setBoard_writer_nickname(rs.getString("MEMBER_NICKNAME")); // 작성자 (닉네임)
			data.setBoard_registration_date(rs.getDate("BOARD_REGISTRATION_DATE")); // 게시판 작성일
			data.setBoard_like_cnt(rs.getInt("LIKE_COUNT"));
			data.setBoard_file_dir(rs.getString("FILE_DIR")); // 파일 경로

			System.out.println("model.BoardRowMapper_all 반환");
			return data;
		}
	}
	class BoardRowMapper_all_myboard_list implements RowMapper<BoardDTO> {
		// 내 게시판
		@Override
		public BoardDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			System.out.println("model.BoardRowMapper_all_myboard_list 실행");
			
			BoardDTO data = new BoardDTO();
			
			data.setBoard_num(rs.getInt("BOARD_NUM")); // 게시판 번호
			data.setBoard_title(rs.getString("BOARD_TITLE")); // 게시판 제목
			data.setBoard_registration_date(rs.getDate("BOARD_REGISTRATION_DATE")); // 게시판 작성일
			data.setBoard_writer_nickname(rs.getString("MEMBER_NICKNAME")); // 작성자 (닉네임)
			data.setBoard_like_cnt(rs.getInt("LIKE_COUNT"));
			data.setBoard_file_dir(rs.getString("FILE_DIR")); // 파일 경로
			
			System.out.println("model.BoardRowMapper_all_myboard_list 반환");
			return data;
		}
	}
	//-------------------------------------------------------------------------------
	class BoardRowMapper_one implements RowMapper<BoardDTO> {
		// 게시판 전체 출력
		@Override
		public BoardDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			System.out.println("model.BoardRowMapper_one 실행");

			BoardDTO data = new BoardDTO();

			data.setBoard_num(rs.getInt("BOARD_NUM")); // 글 번호
			data.setBoard_title(rs.getString("BOARD_TITLE")); // 글 제목
			data.setBoard_content(rs.getString("BOARD_CONTENT")); // 글 내용
			data.setBoard_registration_date(rs.getDate("BOARD_REGISTRATION_DATE")); // 글 작성일
			data.setBoard_writer_id(rs.getString("MEMBER_ID")); // 작성자 ID
			data.setBoard_writer_nickname(rs.getString("MEMBER_NICKNAME"));// 작성자 (닉네임)
			data.setBoard_like_cnt(rs.getInt("LIKE_COUNT")); // 글 좋아요 수

			System.out.println("model.BoardRowMapper_one 반환");
			return data;
		}
	}
	class BoardRowMapper_one_count implements RowMapper<BoardDTO> {
		// 전체 게시판 개수
		@Override
		public BoardDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			System.out.println("model.BoardRowMapper_one_count 실행");

			BoardDTO data = new BoardDTO();

			data.setBoard_total_cnt(rs.getInt("BOARD_TOTAL_CNT"));

			System.out.println("model.BoardRowMapper_one_count 반환");
			return data;
		}
	}
	class BoardRowMapper_one_select implements RowMapper<BoardDTO> {
		// 크롤링 데이터 확인 전체 출력
		@Override
		public BoardDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			System.out.println("model.BoardRowMapper_one_select 실행");
			
			BoardDTO data = new BoardDTO();
			
			data.setBoard_num(rs.getInt("BOARD_NUM")); // 글 번호
			data.setBoard_title(rs.getString("BOARD_TITLE")); // 글 제목
			data.setBoard_writer_id(rs.getString("MEMBER_ID")); // 작성자 ID
			data.setBoard_content(rs.getString("BOARD_CONTENT")); // 글 내용
			data.setBoard_registration_date(rs.getDate("BOARD_REGISTRATION_DATE")); // 글 작성일
			
			System.out.println("model.BoardRowMapper_one_select 반환");
			return data;
		}
	}
	class BoardRowMapper_one_num implements RowMapper<BoardDTO> {
		// 크롤링 select 위해 게시글 번호
		@Override
		public BoardDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			System.out.println("model.BoardRowMapper_one_num 실행");
			
			BoardDTO data = new BoardDTO();
			
			data.setBoard_num(rs.getInt("MAX_NUM"));
			
			System.out.println("model.BoardRowMapper_one_num 반환");
			return data;
		}
	}
	class BoardRowMapper_one_page_count implements RowMapper<BoardDTO> {
		// 페이지 번호
		@Override
		public BoardDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			System.out.println("model.BoardRowMapper_one_page_count 실행");
			
			BoardDTO data = new BoardDTO();
			
			data.setBoard_total_page(rs.getInt("BOARD_TOTAL_PAGE")); // 전체 페이지 수
			
			System.out.println("model.BoardRowMapper_one_page_count 반환");
			return data;
		}
	}
	class NoticeRowMapper_total_page implements RowMapper<BoardDTO>{

		@Override
		public BoardDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			BoardDTO data = new BoardDTO();
			data.setBoard_total_cnt(rs.getInt("NOTICE_TOTAL_COUNT"));
			return data;
		}
		
	}

		// 컬럼 존재 여부를 확인하는 메서드
		//	    private boolean hasColumn(ResultSet rs, String columnName) {
		//	        try {
		//	            rs.findColumn(columnName);
		//	            return true;
		//	        } catch (SQLException e) {
		//	        	 System.err.println("Column not found: " + columnName);
		//	            return false;
		//	        }
		//	    }
	}
