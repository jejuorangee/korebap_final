package com.korebap.app.biz.claim;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class ClaimDAO2 {
	// 게시글 신고
		private final String CLAIM_INSERT_BOARD = "INSERT INTO CLAIM (CLAIM_BOARD_NUM, CLAIM_REPLY_NUM, CLAIM_TARGET_MEMBER_ID, CLAIM_REPORTER_ID) VALUES (?, NULL, ?, ?);";
		// 댓글 신고
		private final String CLAIM_INSERT_REPLY = "INSERT INTO CLAIM (CLAIM_BOARD_NUM, CLAIM_REPLY_NUM, CLAIM_TARGET_MEMBER_ID, CLAIM_REPORTER_ID) VALUES (NULL,?,?,?);";
		// 신고 처리 완료
		private final String CLAIM_UPDATE = "UPDATE CLAIM SET CLAIM_STATUS='Resolved' WHERE CLAIM_NUM=?";
		// 신고여부 확인
		private final String CLAIM_SELECTONE = "SELECT CLAIM_NUM,CLAIM_BOARD_NUM,CLAIM_REPLY_NUM,CLAIM_STATUS,CLAIM_TARGET_MEMBER_ID,CLAIM_REPORTER_ID FROM CLAIM WHERE CLAIM_NUM = ?";
		// 보류 신고 개수
		private final String CLAIM_PENDING_COUNT_SELECTONE = "SELECT COUNT(*) AS RESOLVED_CLAIM_COUNT FROM CLAIM WHERE CLAIM_STATUS = 'Pending'";
		// 처리 완료 신고 개수
		private final String CLAIM_RESOLVED_COUNT_SELECTONE = "SELECT COUNT(*) AS RESOLVED_CLAIM_COUNT FROM CLAIM WHERE CLAIM_STATUS = 'Resolved'";
		// 신고 전체 보기
		private final String CLAIM_ALL_SELECTALL = "SELECT CLAIM_NUM,CLAIM_BOARD_NUM,CLAIM_REPLY_NUM,CLAIM_STATUS,CLAIM_TARGET_MEMBER_ID,CLAIM_REPORTER_ID FROM CLAIM";
		// 보류 신고 전체 보기
		private final String CLAIM_PENDING_SELECTALL = "SELECT CLAIM_NUM,CLAIM_BOARD_NUM,CLAIM_REPLY_NUM,CLAIM_STATUS,CLAIM_TARGET_MEMBER_ID,CLAIM_REPORTER_ID FROM CLAIM WHERE CLAIM_STATUS = 'Pending'";
		// 처리 완료 신고 전체 보기
		private final String CLAIM_RESOLVED_SELECTALL = "SELECT CLAIM_NUM,CLAIM_BOARD_NUM,CLAIM_REPLY_NUM,CLAIM_STATUS,CLAIM_TARGET_MEMBER_ID,CLAIM_REPORTER_ID FROM CLAIM WHERE CLAIM_STATUS = 'Resolved'";

	@Autowired
	private JdbcTemplate jdbcTemplate;

	// Create
	public boolean insert(ClaimDTO claimDTO) {
		int result;
		System.out.println("====model.ClaimDAO2.insert 시작");
		System.out.println("====model.ClaimDAO2.insert claimDTO.getClaim_condition() : ["+claimDTO.getClaim_condition()+"]");
		if (claimDTO.getClaim_condition().equals("INSERT_CLAIM_BOARD")) {
			System.out.println("====model.ClaimDAO2.insert_INSERT_CLAIM_BOARD 시작");
			result = jdbcTemplate.update(CLAIM_INSERT_BOARD, claimDTO.getClaim_board_num(), // 게시글 번호 - FK
					claimDTO.getClaim_target_member_id(), // 신고 당한 유저
					claimDTO.getClaim_reporter_id()); // 신고한 유저
		} else if (claimDTO.getClaim_condition().equals("INSERT_CLAIM_REPLY")) {
			System.out.println("====model.ClaimDAO2.insert_INSERT_CLAIM_REPLY 시작");
			result = jdbcTemplate.update(CLAIM_INSERT_REPLY, claimDTO.getClaim_reply_num(), // 댓글 번호 - FK
					claimDTO.getClaim_target_member_id(), // 신고 당한 유저
					claimDTO.getClaim_reporter_id()); // 신고한 유저
		} else {
			System.out.println("====model.ClaimDAO2.insert 컨디션 실패");
			return false; // 잘못된 조건 처리
		}
		if (result <= 0) { // 변경된 행 수가 0보다 작거나 같다면
			System.out.println("====model.ClaimDAO2.insert 실패");
			return false;
		}
		System.out.println("====model.ClaimDAO2.insert 성공");
		return true; // 성공적으로 삽입 완료
	}

	// Read
	public ClaimDTO selectOne(ClaimDTO claimDTO) {
	    System.out.println("====model.ClaimDAO2.selectOne 시작");
	    Object[] args = { claimDTO.getClaim_num() };
	    System.out.println("====model.ClaimDAO2.selectOne claimDTO.getClaim_num() : ["+claimDTO.getClaim_num()+"]");
	    System.out.println("====model.ClaimDAO2.selectOne claimDTO.getClaim_condition() : ["+claimDTO.getClaim_condition()+"]");
	    if (claimDTO.getClaim_condition().equals("CLAIM_SELECTONE")) {
	        System.out.println("====model.ClaimDAO2.selectOne CLAIM_SELECTONE 시작");
	        return jdbcTemplate.queryForObject(CLAIM_SELECTONE, args, new ClaimRowMapper_one());
	    } else if (claimDTO.getClaim_condition().equals("CLAIM_PENDING_COUNT_SELECTONE")) {
	        System.out.println("====model.ClaimDAO2.selectOne CLAIM_PENDING_COUNT_SELECTONE 시작");
	        // 보류 신고 개수 반환을 위해 쿼리 실행
	        Integer count = jdbcTemplate.queryForObject(CLAIM_PENDING_COUNT_SELECTONE, Integer.class);
	        claimDTO.setClaim_cnt(count); // ClaimDTO에 카운트 설정 (예시)
	        return claimDTO; // 카운트를 포함한 ClaimDTO 반환
	    } else if (claimDTO.getClaim_condition().equals("CLAIM_RESOLVED_COUNT_SELECTONE")) {
	        System.out.println("====model.ClaimDAO2.selectOne CLAIM_RESOLVED_COUNT_SELECTONE 시작");
	        // 처리 완료 신고 개수 반환을 위해 쿼리 실행
	        Integer count = jdbcTemplate.queryForObject(CLAIM_RESOLVED_COUNT_SELECTONE, Integer.class);
	        claimDTO.setClaim_cnt(count); // ClaimDTO에 카운트 설정 (예시)
	        return claimDTO; // 카운트를 포함한 ClaimDTO 반환
	    } else {
	        System.err.println("====model.ClaimDAO2.selectOne 컨디션 에러");
	        return null; // 잘못된 조건 처리
	    }
	}

	
	public List<ClaimDTO> selectAll(ClaimDTO claimDTO) {
		System.out.println("====model.ClaimDAO2.selectAll 시작");
		List<ClaimDTO> datas = new ArrayList<>();
	    System.out.println("====model.ClaimDAO2.selectAll claimDTO.getClaim_condition() : ["+claimDTO.getClaim_condition()+"]");
		if (claimDTO.getClaim_condition().equals("CLAIM_ALL_SELECTALL")) {// 신고 전체 보기
			System.out.println("====model.ClaimDAO2.selectAll CLAIM_ALL_SELECTALL 시작");
			datas = (List<ClaimDTO>) jdbcTemplate.query(CLAIM_ALL_SELECTALL, new ClaimRowMapper_all());
		} else if (claimDTO.getClaim_condition().equals("CLAIM_PENDING_SELECTALL")) { // 보류 신고 전체 보기
			System.out.println("====model.ClaimDAO2.selectAll CLAIM_PENDING_SELECTALL 시작");
			datas = (List<ClaimDTO>) jdbcTemplate.query(CLAIM_PENDING_SELECTALL, new ClaimRowMapper_all());
		} else if (claimDTO.getClaim_condition().equals("CLAIM_RESOLVED_SELECTALL")) { // 처리 완료 신고 전체 보기
			System.out.println("====model.ClaimDAO2.selectAll CLAIM_RESOLVED_SELECTALL 시작");
			datas = (List<ClaimDTO>) jdbcTemplate.query(CLAIM_RESOLVED_SELECTALL, new ClaimRowMapper_all());
		} else {
			System.err.println("====model.ClaimDAO2.selectAll 컨디션 에러");
			return null; // 빈 리스트 반환
		}
		System.out.println("====model.ClaimDAO2.selectAll 성공");
		return datas;
	}

	// Update
	// "UPDATE CLAIM SET CLAIM_STATUS='Resolved' WHERE CLAIM_NUM=?";
	public boolean update(ClaimDTO claimDTO) { // 보류 > 해결 로 수정
		// 신고 내역 댓글, 게시글 (트리거 사용)
		int result;
		System.out.println("====model.ClaimDAO2.update 시작");
		result = jdbcTemplate.update(CLAIM_UPDATE, claimDTO.getClaim_num()); // 신고 번호
		System.out.println("====model.ClaimDAO2.update result : "+result);
		if (result <= 0) { // 변경된 행 수가 0보다 작거나 같다면
			System.err.println("====model.ClaimDAO2.update 행 변경 실패");
			return false;
		}
		System.out.println("====model.ClaimDAO2.update 성공");
		return true; // 성공적으로 삽입 완료
	}

	// Delete
	public boolean delete(ClaimDTO claimDTO) { // 지우기
		// 신고 내역 지우기(관리자)
		return false;
	}
}

class ClaimRowMapper_one implements RowMapper<ClaimDTO> {
	@Override
	public ClaimDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		ClaimDTO data = new ClaimDTO();
		data.setClaim_num(rs.getInt("CLAIM_NUM"));
		return data;
	}
}

class ClaimRowMapper_all implements RowMapper<ClaimDTO> {
	@Override
	public ClaimDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		ClaimDTO data = new ClaimDTO();
		data.setClaim_num(rs.getInt("CLAIM_NUM")); // 신고 번호
		data.setClaim_board_num(rs.getInt("CLAIM_BOARD_NUM")); // 게시판 번호
		data.setClaim_reply_num(rs.getInt("CLAIM_REPLY_NUM")); // 댓글 번호
		data.setClaim_status(rs.getString("CLAIM_STATUS")); // 신고 상태
		data.setClaim_target_member_id(rs.getString("CLAIM_TARGET_MEMBER_ID")); // 신고 당한 사람
		data.setClaim_reporter_id(rs.getString("CLAIM_REPORTER_ID")); // 신고자
		return data;
	}
}
