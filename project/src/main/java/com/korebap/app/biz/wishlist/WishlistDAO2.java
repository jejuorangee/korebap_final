package com.korebap.app.biz.wishlist;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class WishlistDAO2 {
	// 위시리스트 추가
	private final String WISHLIST_INSERT = "INSERT INTO WISHLIST (WISHLIST_MEMBER_ID,WISHLIST_PRODUCT_NUM) VALUES (?,?)";
	// 위시리스트 삭제
	private final String WISHLIST_DELETE = "DELETE FROM WISHLIST WHERE WISHLIST_NUM = ?";
	// 나의 위시리스트 개수 보기
	private final String WISHLIST_SELECTONE = "SELECT COUNT(WISHLIST_NUM) AS WISHLIST_COUNT FROM WISHLIST WHERE WISHLIST_MEMBER_ID=?";
	// 나의 위시리스트 전체 보기
	private final String WISHLIST_SELECTALL = "SELECT W.WISHLIST_NUM,P.PRODUCT_NUM,P.PRODUCT_NAME,P.PRODUCT_PRICE,P.PRODUCT_LOCATION,P.PRODUCT_CATEGORY,I.FILE_DIR\r\n"
			+ "FROM WISHLIST W JOIN PRODUCT P ON W.WISHLIST_PRODUCT_NUM = P.PRODUCT_NUM\r\n"
			+ "LEFT JOIN (SELECT FILE_DIR,PRODUCT_ITEM_NUM,ROW_NUMBER() OVER (PARTITION BY PRODUCT_ITEM_NUM ORDER BY FILE_NUM) AS IMAGE_NUMBER\r\n"
			+ "FROM IMAGEFILE) I ON P.PRODUCT_NUM = I.PRODUCT_ITEM_NUM AND I.IMAGE_NUMBER = 2 WHERE W.WISHLIST_MEMBER_ID = ?";

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<WishlistDTO> selectAll(WishlistDTO wishlistDTO) {
		System.out.println("====model.WishlistDAO2.selectAll 시작");
		Object[] args = { wishlistDTO.getWishlist_member_id() };
		System.out.println("====model.WishlistDAO2.selectAll args : "+args);
		// query : 반환형 List (한 개 이상 결과 반환)
		return jdbcTemplate.query(WISHLIST_SELECTALL, args, new WishlistRowMapper());

	}

	public WishlistDTO selectOne(WishlistDTO wishlistDTO) {
		System.out.println("====model.WishlistDAO2.selectOne 시작");
		Object[] args = { wishlistDTO.getWishlist_member_id() };
		System.out.println("====model.WishlistDAO2.selectOne args : "+args);
		return jdbcTemplate.queryForObject(WISHLIST_SELECTONE, args, new Wishlist_selectOne_RowMapper());
	}

	public boolean insert(WishlistDTO wishlistDTO) {
		// WISHLIST_MEMBER_ID,WISHLIST_PRODUCT_NUM
		int result = jdbcTemplate.update(WISHLIST_INSERT, wishlistDTO.getWishlist_member_id(),
				wishlistDTO.getWishlist_product_num());
		System.out.println("====model.WishlistDAO.insert result : "+result);
		// 반환받은 수가 0보다 작거나 같다면 (== 변경된 행 수가 없음)
		if (result <= 0) {
			System.err.println("====model.WishlistDAO.insert 행 변경 실패");
			return false; // false 반환
		}
		return true;

	}

	public boolean delete(WishlistDTO wishlistDTO) {
		System.err.println("====model.WishlistDAO.delete 시작");
		int result = jdbcTemplate.update(WISHLIST_DELETE, wishlistDTO.getWishlist_num());
		System.out.println("====model.WishlistDAO.delete result : "+result);
		// 반환받은 수가 0보다 작거나 같다면 (== 변경된 행 수가 없음)
		if (result <= 0) {
			System.err.println("====model.WishlistDAO.delete 행 변경 실패");
			return false; // false 반환
		}
		return true;
	}

	// 기능 미구현으로 private
	public boolean update(WishlistDTO wishlistDTO) {
		return false;
	}
}

// ResultSet을 객체로 변환해주는 클래스
class WishlistRowMapper implements RowMapper<WishlistDTO> {

	@Override
	public WishlistDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		WishlistDTO data = new WishlistDTO(); // 데이터를 담을 새로운 DTO 생성
		data.setWishlist_num(rs.getInt("WISHLIST_NUM")); // 위시리스트 번호 (PK)
		data.setWishlist_product_num(rs.getInt("PRODUCT_NUM")); // 상품번호
		data.setWishlist_product_name(rs.getString("PRODUCT_NAME")); // 상품명
		data.setWishlist_product_price(rs.getInt("PRODUCT_PRICE")); // 상품 금액
		data.setWishlist_product_location(rs.getString("PRODUCT_LOCATION")); // 상품 장소
		data.setWishlist_product_category(rs.getString("PRODUCT_CATEGORY")); // 상품 유형(낚시배,낚시터,낚시카페,수상)
		data.setWishlist_file_dir(rs.getString("FILE_DIR")); // 파일 경로;

		return data;
	}

}

class Wishlist_selectOne_RowMapper implements RowMapper<WishlistDTO> {

	@Override
	public WishlistDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		WishlistDTO data = new WishlistDTO(); // 데이터를 담을 새로운 DTO 생성
		data.setWishlist_cnt(rs.getInt("WISHLIST_COUNT")); // 위시리스트 개수
		return data;
	}

}
