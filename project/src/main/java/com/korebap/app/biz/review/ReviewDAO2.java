package com.korebap.app.biz.review;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class ReviewDAO2 {
	// 리뷰 작성
	private final String REVIEW_INSERT = "INSERT INTO REVIEW (REVIEW_CONTENT, REVIEW_PRODUCT_NUM, REVIEW_WRITER_ID, REVIEW_STAR) VALUES (?, ?, ?, ?)";
	// 리뷰 수정
	private final String REVIEW_UPDATE = "UPDATE REVIEW SET REVIEW_CONTENT = ?, REVIEW_STAR = ? WHERE REVIEW_NUM = ?";
	// 리뷰 삭제
	private final String REVIEW_DELETE = "DELETE FROM REVIEW WHERE REVIEW_NUM = ?";
	// 리뷰 전체 출력 (최신순)
	private final String REVIEW_SELECTALL_NEW_REVIEW = "SELECT R.REVIEW_NUM, R.REVIEW_CONTENT, R.REVIEW_REGISTRATION_DATE, R.REVIEW_STAR, \r\n"
			+ "COALESCE(R.REVIEW_WRITER_ID,'탈퇴한 회원입니다.'), COALESCE(M.MEMBER_NICKNAME,'탈퇴한 회원입니다.'), M.MEMBER_PROFILE FROM REVIEW R\r\n"
			+ "LEFT JOIN MEMBER M ON R.REVIEW_WRITER_ID = M.MEMBER_ID\r\n"
			+ "WHERE R.REVIEW_PRODUCT_NUM = ? ORDER BY R.REVIEW_NUM DESC";

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<ReviewDTO> selectAll(ReviewDTO reviewDTO) {
		System.out.println("====model.ReviewDAO2.selectAll 시작");
		return jdbcTemplate.query(REVIEW_SELECTALL_NEW_REVIEW, new Object[] { reviewDTO.getReview_product_num() }, new ReviewRowMapper());
	}

	public boolean insert(ReviewDTO reviewDTO) {
		System.out.println("====model.ReviewDAO.insert 시작");
		int result = jdbcTemplate.update(REVIEW_INSERT, reviewDTO.getReview_content(),
				reviewDTO.getReview_product_num(), reviewDTO.getReview_writer_id(), reviewDTO.getReview_star());
		System.out.println("	model.ReviewDAO.insert result : " + result);
		if (result <= 0) {
			System.err.println("====model.ReviewDAO.insert 실패");
			return false;
		}
		System.out.println("====model.ReviewDAO.insert  성공");
		return true;
	}

	public boolean update(ReviewDTO reviewDTO) {
		System.out.println("====model.ReviewDAO.update 시작");
		int result = jdbcTemplate.update(REVIEW_UPDATE, reviewDTO.getReview_content(), reviewDTO.getReview_star(),
				reviewDTO.getReview_num());
		System.out.println("	model.ReviewDAO.update result : " + result);
		if (result <= 0) {
			System.out.println("====model.ReviewDAO.update 실패");
			return false;
		}
		System.out.println("====model.ReviewDAO.update 성공");
		return true;
	}

	public boolean delete(ReviewDTO reviewDTO) {
		System.out.println("====model.ReviewDAO.delete 시작");
		int result = jdbcTemplate.update(REVIEW_DELETE, reviewDTO.getReview_num());
		System.out.println("	model.ReviewDAO.delete result : " + result);
		if (result <= 0) {
			System.out.println("====model.ReviewDAO.delete 실패");
			return false;
		}
		System.out.println("====model.ReviewDAO.delete 성공");
		return true;
	}

	// 기능 미구현으로 null 반환
	public ReviewDTO selectOne(ReviewDTO reviewDTO) {
		return null;
	}
}

// ROWMAPPER
class ReviewRowMapper implements RowMapper<ReviewDTO> {
	@Override
	public ReviewDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		ReviewDTO data = new ReviewDTO();
		data.setReview_num(rs.getInt("REVIEW_NUM"));
		data.setReview_content(rs.getString("REVIEW_CONTENT"));
		data.setReview_registration_date(rs.getDate("REVIEW_REGISTRATION_DATE"));
		data.setReview_star(rs.getInt("REVIEW_STAR"));
		data.setReview_writer_id(rs.getString("REVIEW_WRITER_ID"));
		data.setReview_writer_nickname(rs.getString("MEMBER_NICKNAME"));
		data.setReview_member_profile(rs.getString("MEMBER_PROFILE"));
		System.out.println("	model.ReviewDAO.mapRow datas : [" + data + "]");
		return data;
	}
}
