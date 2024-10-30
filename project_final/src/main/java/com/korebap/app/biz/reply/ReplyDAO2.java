package com.korebap.app.biz.reply;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class ReplyDAO2 {
	
	private final String REPLY_INSERT = "INSERT INTO REPLY (REPLY_CONTENT, REPLY_WRITER_ID, REPLY_PRIVATE, REPLY_BOARD_NUM) "
	        + "VALUES (?, ?, ?, ?)";

	private final String REPLY_UPDATE = "UPDATE REPLY SET REPLY_CONTENT = ? WHERE REPLY_NUM = ?";

	private final String REPLY_DELETE = "DELETE FROM REPLY WHERE REPLY_NUM = ?";

	private final String REPLY_ALL = "SELECT R.REPLY_NUM, R.REPLY_CONTENT, R.REPLY_WRITER_ID, M.MEMBER_NICKNAME, COALESCE(ML.LV_COUNT, 1) AS MEMBER_LEVEL, "
	        + "R.REPLY_REGISTRATION_DATE, M.MEMBER_PROFILE "
	        + "FROM REPLY R "
	        + "JOIN MEMBER M ON R.REPLY_WRITER_ID = M.MEMBER_ID "
	        + "JOIN BOARD B ON R.REPLY_BOARD_NUM = B.BOARD_NUM "
	        + "LEFT JOIN (SELECT B.BOARD_WRITER_ID, COUNT(B.BOARD_WRITER_ID) AS LV_COUNT FROM BOARD B GROUP BY B.BOARD_WRITER_ID) ML ON R.REPLY_WRITER_ID = ML.BOARD_WRITER_ID "
	        + "WHERE B.BOARD_NUM = ? ORDER BY R.REPLY_NUM DESC";
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public boolean insert(ReplyDTO replyDTO){	// 추가
		Object[] args = {replyDTO.getReply_content(), replyDTO.getReply_writer_id(), replyDTO.getReply_board_num()};
		int result = jdbcTemplate.update(REPLY_INSERT,args,new ReplyRowMapper());
		if(result <= 0) {
			System.out.println("model.ReplyDAO.insert 행 변경 실패");
			return false;
		}
		System.out.println("model.ReplyDAO.insert 행 변경 성공");
		return true;
	}

	public boolean update(ReplyDTO replyDTO){	// 업데이트(변경)
		Object[] args = {replyDTO.getReply_content(), replyDTO.getReply_num()};
		int result = jdbcTemplate.update(REPLY_UPDATE,args,new ReplyRowMapper());
		if(result <= 0) {
			System.out.println("model.ReplyDAO.update 행 변경 실패");
			return false;
		}
		System.out.println("model.ReplyDAO.update 행 변경 성공");
		return true;
	}

	public boolean delete(ReplyDTO replyDTO){	// 지우기
		Object[] args = {replyDTO.getReply_num()};
		int result = jdbcTemplate.update(REPLY_DELETE,args,new ReplyRowMapper());
		if(result <= 0) { // 변변경이 된 수가 0보다 작거나 같다면
			System.out.println("model.ReplyDAO.delete 행 변경 실패");
			return false;
		}
		System.out.println("model.ReplyDAO.delete 행 변경 성공");
		return true;
	}

	public List<ReplyDTO> selectAll(ReplyDTO replyDTO){ // 전체 출력
		List<ReplyDTO> replyList = new ArrayList<ReplyDTO>();
		replyList = jdbcTemplate.query(REPLY_ALL,new ReplyRowMapper());
		return replyList;
	}

	// 사용하지 않는 기능으로 private 처리
	private ReplyDTO selectOne(ReplyDTO replyDTO){// 힌개 출력
		ReplyDTO data=null;

		return data;
	}
}

class ReplyRowMapper implements RowMapper<ReplyDTO>{
	@Override
	public ReplyDTO mapRow(ResultSet rs, int rowNum){
		ReplyDTO data = new ReplyDTO();
		try {
			data.setReply_num(rs.getInt("REPLY_NUM"));
			data.setReply_content(rs.getString("REPLY_CONTENT")); // 댓글 내용
			data.setReply_writer_id(rs.getString("REPLY_WRITER_ID")); // 댓글 작성자 ID
			data.setReply_writer_nickname("MEMBER_NICKNAME");// 댓글 작성자(닉네임)
			data.setReply_writer_level(rs.getInt("MEMBER_LEVEL")); // 댓글 작성자의 lv
			// 날짜 타입 LocalDate → Date 타입으로 변경 24.09.03
			data.setReply_registration_date(rs.getDate("REPLY_REGISTRATION_DATE")); // 댓글 작성일
			// 댓글 비공개 여부 boolean → int 타입으로 변경 24.09.03
			data.setReply_member_profile(rs.getString("MEMBER_PROFILE")); // 회원의 프로필 이미지
		} catch (SQLException e) {
			System.out.println("model.ReplyDAO SQL문 실패");
			e.printStackTrace();
		}
		return null;
	}
}
