package com.korebap.app.biz.goodLike;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class GoodLikeDAO2 {
	// 좋아요 등록
	private final String GOODLIKE_INSERT = "INSERT INTO GOODLIKE(LIKE_BOARD_NUM,LIKE_MEMBER_ID) VALUES(?,?)";
	// 좋아요 삭제
	private final String GOODLIKE_DELETE = "DELETE FROM GOODLIKE WHERE LIKE_BOARD_NUM = ? AND LIKE_MEMBER_ID = ?";
	// 좋아요 개수
	private final String GOODLIKE_SELECTONE = "SELECT LIKE_BOARD_NUM, LIKE_MEMBER_ID FROM GOODLIKE WHERE LIKE_BOARD_NUM = ? AND LIKE_MEMBER_ID = ?";

	@Autowired // jdbc 연결
	private JdbcTemplate jdbcTemplate;

	public boolean insert(GoodLikeDTO goodLikeDTO) { // 입력
		System.out.println("====model.GoodLikeDAO2.insert 시작");
		int result = jdbcTemplate.update(GOODLIKE_INSERT, goodLikeDTO.getGoodLike_board_num(),
				goodLikeDTO.getGoodLike_member_id());
		System.out.println("====model.GoodLikeDAO2.insert result : "+result);
		if (result <= 0) {
			System.err.println("====model.GoodLikeDAO2.insert 실패");
			return false;
		}
		System.out.println("====model.GoodLikeDAO2.insert 성공");
		return true;
	}
	
	// "DELETE FROM GOODLIKE WHERE LIKE_BOARD_NUM = ? AND LIKE_MEMBER_ID = ?"
	public boolean delete(GoodLikeDTO goodLikeDTO) { // 삭제
		System.out.println("====model.GoodLikeDAO2.delete 시작");
		// 좋아요단 게시글에 좋아요 지우기.
		int result = jdbcTemplate.update(GOODLIKE_DELETE, goodLikeDTO.getGoodLike_board_num(),
				goodLikeDTO.getGoodLike_member_id());
		System.out.println("====model.GoodLikeDAO2.delete result : "+result);
		if (result <= 0) {
			System.err.println("====model.GoodLikeDAO2.delete 실패");
			return false;
		}
		System.out.println("====model.GoodLikeDAO2.delete 성공");
		return true;
	}

	public GoodLikeDTO selectOne(GoodLikeDTO goodLikeDTO) { // 한명 출력
		System.out.println("====model.GoodLikeDAO2.selectOne 시작");
		// - 좋아요를 했는지 안했는지 여부 확인
		// - 로그인을 하고 나갔다가 들어왔을 때, 좋아요가 남아있어야 함
		Object[] args = { goodLikeDTO.getGoodLike_board_num(),
				goodLikeDTO.getGoodLike_member_id() };
		try {
			GoodLikeDTO data = jdbcTemplate.queryForObject(GOODLIKE_SELECTONE, args, new GoodLikeRowMapper());
			System.out.println("====model.GoodLikeDAO2.selectOne data : "+data);
			return data;
		} catch (Exception e) {
			System.err.println("====model.GoodLikeDAO2.selectOne SQL 실패");
			// 데이터가 없는 경우 null 반환 또는 다른 처리
			return null; // 또는 적절한 예외 처리
		}
	}

	// 현재 사용되지 않지만, 나중에 구현될 수 있음
	public List<GoodLikeDTO> selectAll(GoodLikeDTO goodLikeDTO) { // 전체 출력
		return null;
	}

	// 현재 사용되지 않지만, 나중에 구현될 수 있음
	public boolean update(GoodLikeDTO goodLikeDTO) { // 업데이트
		return false;
	}

}

class GoodLikeRowMapper implements RowMapper<GoodLikeDTO> {

	@Override
	public GoodLikeDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		GoodLikeDTO data = new GoodLikeDTO();
		data.setGoodLike_num(rs.getInt("LIKE_NUM"));
		data.setGoodLike_board_num(rs.getInt("LIKE_BOARD_NUM"));
		data.setGoodLike_member_id(rs.getString("LIKE_MEMBER_ID"));
		System.out.println("goodLike.GoodLikeDAO.selec 반환");
		return data;
	}

}
