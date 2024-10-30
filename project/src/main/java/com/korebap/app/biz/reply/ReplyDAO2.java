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
	
	private final String REPLY_INSERT = "INSERT INTO REPLY (REPLY_CONTENT, REPLY_WRITER_ID, REPLY_BOARD_NUM) "
	        + "VALUES (?, ?, ?)";

	private final String REPLY_UPDATE = "UPDATE REPLY SET REPLY_CONTENT = ? WHERE REPLY_NUM = ?";

	private final String REPLY_DELETE = "DELETE FROM REPLY WHERE REPLY_NUM = ?";

	private final String REPLY_ALL = "SELECT R.REPLY_NUM, R.REPLY_CONTENT, R.REPLY_WRITER_ID, M.MEMBER_NICKNAME, R.REPLY_REGISTRATION_DATE, M.MEMBER_PROFILE "
	        + "FROM REPLY R "
	        + "JOIN MEMBER M ON R.REPLY_WRITER_ID = M.MEMBER_ID "
	        + "JOIN BOARD B ON R.REPLY_BOARD_NUM = B.BOARD_NUM "
	        + "WHERE B.BOARD_NUM = ? ORDER BY R.REPLY_NUM DESC";
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public boolean insert(ReplyDTO replyDTO){	// 추가
		System.out.println("====model.ReplyDAO2.insert 시작");
		Object[] args = {replyDTO.getReply_content(), replyDTO.getReply_writer_id(), replyDTO.getReply_board_num()};
		int result = jdbcTemplate.update(REPLY_INSERT,args,new ReplyRowMapper());
		System.out.println("====model.ReplyDAO2.insert result : " + result);
		if(result <= 0) {
			System.err.println("====model.ReplyDAO2.insert 실패");
			return false;
		}
		System.out.println("====model.ReplyDAO2.insert 성공");
		return true;
	}

	public boolean update(ReplyDTO replyDTO){	// 업데이트(변경)
		System.out.println("====model.ReplyDAO2.update 시작");
		Object[] args = {replyDTO.getReply_content(), replyDTO.getReply_num()};
		int result = jdbcTemplate.update(REPLY_UPDATE,args,new ReplyRowMapper());
		System.out.println("====model.ReplyDAO2.update result : "+result);
		if(result <= 0) {
			System.err.println("====model.ReplyDAO2.update 실패");
			return false;
		}
		System.out.println("====model.ReplyDAO2.update 성공");
		return true;
	}

	public boolean delete(ReplyDTO replyDTO){	// 지우기
		System.out.println("====model.ReplyDAO2.delete 시작");
		Object[] args = {replyDTO.getReply_num()};
		int result = jdbcTemplate.update(REPLY_DELETE,args,new ReplyRowMapper());
		System.out.println("====model.ReplyDAO2.delete result : "+result);
		if(result <= 0) { // 변변경이 된 수가 0보다 작거나 같다면
			System.err.println("====model.ReplyDAO2.delete 실패");
			return false;
		}
		System.out.println("====model.ReplyDAO2.delete 성공");
		return true;
	}

	public List<ReplyDTO> selectAll(ReplyDTO replyDTO){ // 전체 출력
		System.out.println("====model.ReplyDAO2.selectAll 시작");
		List<ReplyDTO> datas = new ArrayList<ReplyDTO>();
		datas = jdbcTemplate.query(REPLY_ALL,new Object[] {replyDTO.getReply_board_num()},new ReplyRowMapper());
		return datas;
	}

	// 사용하지 않는 기능으로 private 처리
	public ReplyDTO selectOne(ReplyDTO replyDTO){// 힌개 출력
		ReplyDTO data=null;

		return data;
	}
}

class ReplyRowMapper implements RowMapper<ReplyDTO>{
	
	@Override
	public ReplyDTO mapRow(ResultSet rs, int rowNum){
		System.err.println("====model.ReplyDAO2.ReplyRowMapper 시작");
		ReplyDTO data = new ReplyDTO();
		try {
			data.setReply_num(rs.getInt("REPLY_NUM"));
			data.setReply_content(rs.getString("REPLY_CONTENT")); // 댓글 내용
			data.setReply_writer_id(rs.getString("REPLY_WRITER_ID")); // 댓글 작성자 ID
			data.setReply_writer_nickname(rs.getString("MEMBER_NICKNAME"));// 댓글 작성자(닉네임)
			data.setReply_registration_date(rs.getDate("REPLY_REGISTRATION_DATE")); // 댓글 작성일
			data.setReply_member_profile(rs.getString("MEMBER_PROFILE")); // 회원의 프로필 이미지
		} 
		catch (SQLException e) {
			System.err.println("====model.ReplyDAO2.ReplyRowMapper 실패");
			e.printStackTrace();
		}
		return null;
	}
}
