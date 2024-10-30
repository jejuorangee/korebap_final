package com.korebap.app.biz.product;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class ProductDAO2 {
	// 상품 등록
	private final String PRODUCT_INSERT = "INSERT INTO PRODUCT (PRODUCT_NAME, PRODUCT_PRICE, PRODUCT_DETAILS, PRODUCT_ADDRESS, PRODUCT_LOCATION, PRODUCT_CATEGORY, PRODUCT_SELLER_ID) \r\n"
			+ "VALUES (?, ?, ?, ?, ?, ?, ?)";
	// 사장님 상품 수정
	private final String PRODUCT_UPDATE = "UPDATE PRODUCT P JOIN MEMBER M ON M.MEMBER_ID = P.PRODUCT_SELLER_ID\r\n"
			+ "SET P.PRODUCT_NAME = ?, P.PRODUCT_PRICE = ?, P.PRODUCT_DETAILS = ?, P.PRODUCT_ADDRESS = ?, P.PRODUCT_LOCATION = ?, P.PRODUCT_CATEGORY = ? WHERE P.PRODUCT_SELLER_ID = ?";
	// 전체출력 통합 >> 정렬기준 + 검색어
	private final String PRODUCT_SELECTALL = "SELECT PRODUCT_NUM, PRODUCT_NAME, PRODUCT_PRICE, PRODUCT_ADDRESS, PRODUCT_LOCATION, PRODUCT_CATEGORY, RATING, PAYMENT_COUNT, WISHLIST_COUNT, FILE_DIR"
			+ " FROM ("
			+ "    SELECT PRODUCT_NUM, PRODUCT_NAME, PRODUCT_PRICE, PRODUCT_ADDRESS, PRODUCT_LOCATION, PRODUCT_CATEGORY, RATING, PAYMENT_COUNT, WISHLIST_COUNT,FILE_DIR,"
			+ "           ROW_NUMBER() OVER (" + "           		ORDER BY" + "           		CASE"
			+ "           		 	WHEN ?  = 'newest' THEN PRODUCT_NUM"
			+ "                    WHEN ? = 'rating' THEN COALESCE(RATING, -1)"
			+ "                    WHEN ? = 'wish' THEN COALESCE(WISHLIST_COUNT, -1)"
			+ "                    WHEN ? = 'payment' THEN COALESCE(PAYMENT_COUNT, -1)"
			+ "                    ELSE PRODUCT_NUM" + "                   END DESC) AS ROW_NUM"
			+ "    FROM PRODUCT_INFO_VIEW" + "    WHERE PRODUCT_NAME LIKE CONCAT('%',COALESCE(?, ''), '%')"
			+ "    AND (PRODUCT_LOCATION = COALESCE(?, PRODUCT_LOCATION)) AND (PRODUCT_CATEGORY = COALESCE(?, PRODUCT_CATEGORY)) "
			+ ") AS subquery " + "WHERE ROW_NUM BETWEEN (COALESCE(?, 1) - 1) * 9 + 1 AND COALESCE(?, 1) * 9";
	// 상품 상세보기
	private final String SELECTONE = "SELECT PRODUCT_NAME, PRODUCT_PRICE, PRODUCT_DETAILS, PRODUCT_ADDRESS, PRODUCT_LOCATION, PRODUCT_CATEGORY, "
			+ "(SELECT COALESCE(ROUND(AVG(R.REVIEW_STAR), 1), 0) FROM REVIEW R WHERE R.REVIEW_PRODUCT_NUM = PRODUCT_NUM) AS RATING, "
			+ "(SELECT COUNT(PA.PAYMENT_PRODUCT_NUM) FROM PAYMENT PA WHERE PA.PAYMENT_PRODUCT_NUM = PRODUCT_NUM) AS PAYMENT_COUNT, "
			+ "(SELECT COUNT(W.WISHLIST_PRODUCT_NUM) FROM WISHLIST W WHERE W.WISHLIST_PRODUCT_NUM = PRODUCT_NUM) AS WISHLIST_COUNT "
			+ "FROM PRODUCT WHERE PRODUCT_NUM = ?";
	// 사용자가 선택한 일자의 재고 보기
	private final String SELECTONE_CURRENT_STOCK = "SELECT P.PRODUCT_NUM, (P.PRODUCT_CNT - COALESCE(RS.RESERVATION_COUNT, 0)) AS CURRENT_STOCK "
			+ "FROM PRODUCT P "
			+ "LEFT JOIN (SELECT PA.PAYMENT_PRODUCT_NUM AS PRODUCT_NUM, COUNT(R.RESERVATION_REGISTRATION_DATE) AS RESERVATION_COUNT "
			+ "FROM RESERVATION R " + "JOIN PAYMENT PA ON R.RESERVATION_PAYMENT_NUM = PA.PAYMENT_NUM "
			+ "WHERE R.RESERVATION_REGISTRATION_DATE = ? "
			+ "GROUP BY PA.PAYMENT_PRODUCT_NUM) RS ON P.PRODUCT_NUM = RS.PRODUCT_NUM " + "WHERE P.PRODUCT_NUM = ?";

	// 상품 전체 개수
	// 1029 수정 : 관리자 페이지 필요 쿼리문 추가
	private final String PRODUCT_TOTAL_CNT = "SELECT COUNT(PRODUCT_NUM) AS PRODUCT_TOTAL_CNT FROM PRODUCT";
	// 전체 데이터 개수를 반환 (전체 페이지 수 - 기본)
	private final String PRODUCT_TOTAL_PAGE = "SELECT CEIL(COALESCE(COUNT(PRODUCT_NUM), 0) / 9.0) AS PRODUCT_TOTAL_PAGE FROM PRODUCT";

	// 전체 데이터 개수를 반환 (검색어 사용 페이지수)
	private final String PRODUCT_SEARCH_PAGE = "WHERE PRODUCT_NAME LIKE CONCAT('%', ?, '%')";

	// 전체 데이터 개수를 반환 (필터링 검색 페이지 수)
	private final String PRODUCT_FILTERING_PAGE = "WHERE (PRODUCT_LOCATION = COALESCE(?, PRODUCT_LOCATION) AND PRODUCT_CATEGORY = COALESCE(?, PRODUCT_CATEGORY))";
	// 샘플데이터(크롤링) insert
	// 바다 - 낚시배
	private final String PRODUCT_CRAWLING_SEA_BOAT_INSERT = "INSERT INTO PRODUCT (PRODUCT_NAME, PRODUCT_PRICE, PRODUCT_DETAILS, PRODUCT_CNT, PRODUCT_ADDRESS, PRODUCT_LOCATION, PRODUCT_CATEGORY,PRODUCT_SELLER_ID) "
			+ "VALUES (?, ?, ?, ?, ?, '바다', '낚시배',?)";
	// 바다 - 낚시터
	private final String PRODUCT_CRAWLING_SEA_FISHING_INSERT = "INSERT INTO PRODUCT (PRODUCT_NAME, PRODUCT_PRICE, PRODUCT_DETAILS, PRODUCT_CNT, PRODUCT_ADDRESS, PRODUCT_LOCATION, PRODUCT_CATEGORY,PRODUCT_SELLER_ID) "
			+ "VALUES (?, ?, '바다 낚시터입니다~!', 99, ?, '바다', '낚시터',?)";
	// 민물 - 낚시터
	private final String PRODUCT_CRAWLING_FRESH_WATER_FISHING_INSERT = "INSERT INTO PRODUCT (PRODUCT_NAME, PRODUCT_PRICE, PRODUCT_DETAILS, PRODUCT_CNT, PRODUCT_ADDRESS, PRODUCT_LOCATION, PRODUCT_CATEGORY,PRODUCT_SELLER_ID) "
			+ "VALUES (?, ?, '민물 낚시터입니다~!', ?, ?, '민물', '수상',?)";
	// 민물 - 낚시카페
	private final String PRODUCT_CRAWLING_FRESH_WATER_FISHING_CAFE_INSERT = "INSERT INTO PRODUCT (PRODUCT_NAME, PRODUCT_PRICE, PRODUCT_DETAILS, PRODUCT_CNT, PRODUCT_ADDRESS, PRODUCT_LOCATION, PRODUCT_CATEGORY, PRODUCT_SELLER_ID) "
			+ "VALUES (?, ?, '민물 낚시카페입니다~!', 50, ?, '민물', '낚시카페',?)";
	// 이미지 파일 저장을 위한 select
	// 상품 pk 출력
	private final String PRODUCT_NUM_SELECT = "SELECT MAX(PRODUCT_NUM) AS MAX_NUM FROM PRODUCT";
	// 상품 번호 출력
	private final String PRODUCT_SELECTALL_CRAWLING = "SELECT PRODUCT_NUM FROM PRODUCT";

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public boolean insert(ProductDTO productDTO) {
		System.out.println("====model.ProductDAO2.insert 시작");

		// 변수 초기화
		String product_name = productDTO.getProduct_name();
		int product_price = productDTO.getProduct_price();
		String product_details = productDTO.getProduct_details();
		int product_cnt = productDTO.getProduct_cnt();
		String product_address = productDTO.getProduct_address();
		String product_location = productDTO.getProduct_location();
		String product_category = productDTO.getProduct_category();
		String product_seller_id = productDTO.getProduct_seller_id();

		// 쿼리 및 파라미터 초기화
		String sql = null;
		Object[] args = null;
		System.out.println("====model.ProductDAO2.insert productDTO.getProduct_condition() : ["
				+ productDTO.getProduct_condition() + "]");
		if (productDTO.getProduct_condition().equals("PRODUCT_INSERT")) { // 상품등록
			sql = PRODUCT_INSERT;
			args = new Object[] { product_name, // 상품명
					product_price, // 가격
					product_details, // 설명
					product_cnt, product_address, // 주소
					product_location, // 위치
					product_category, // 카테고리
					product_seller_id // 판매자
			};
		} else if (productDTO.getProduct_condition().equals("PRODUCT_CRAWLING_SEA_BOAR_INSERT")) { // 바다 - 낚시배
			sql = PRODUCT_CRAWLING_SEA_BOAT_INSERT;
			args = new Object[] { product_name, // 상품명
					product_price, // 가격
					product_details, // 설명
					product_cnt, // 재고
					product_address, // 주소
					product_seller_id };
		} else if (productDTO.getProduct_condition().equals("PRODUCT_CRAWLING_SEA_FISHING_INSERT")) { // 바다 - 낚시터
			sql = PRODUCT_CRAWLING_SEA_FISHING_INSERT;
			args = new Object[] { product_name, // 상품명
					product_price, // 가격
					product_address, // 주소
					product_seller_id };
		} else if (productDTO.getProduct_condition().equals("PRODUCT_CRAWLING_FRESH_WATER_FISHING_INSERT")) { // 민물 - 수상
			sql = PRODUCT_CRAWLING_FRESH_WATER_FISHING_INSERT;
			args = new Object[] { product_name, // 상품명
					product_price, // 가격
					product_cnt, // 수량
					product_address, // 주소
					product_seller_id };
			// 민물 - 낚시카페
		} else if (productDTO.getProduct_condition().equals("PRODUCT_CRAWLING_FRESH_WATER_FISHING_CAFE_INSERT")) {
			sql = PRODUCT_CRAWLING_FRESH_WATER_FISHING_CAFE_INSERT;
			args = new Object[] { product_name, // 상품명
					product_price, // 가격
					product_address, // 주소
					product_seller_id };
		} else {
			System.err.println("====model.ProductDAO2.insert 컨디션 에러");
		}

		int result = jdbcTemplate.update(sql, args);
		System.out.println("====model.ProductDAO2.insert result : " + result);
		if (result <= 0) {
			System.err.println("====model.ProductDAO2.insert 실패");
			return false;
		}
		System.out.println("====model.ProductDAO2.insert 종료");
		return true; // 성공적으로 삽입 완료
	}

	public List<ProductDTO> selectAll(ProductDTO productDTO) { // 전체 출력
		System.out.println("model.ProductDAO2.selectAll 시작");
		List<ProductDTO> datas = new ArrayList<>();
		// ProductRowMapper productRowMapper = new ProductRowMapper(); // 미리 생성
		System.out.println(
				"====model.selectAll productDAO2.getProduct_condition() : [" + productDTO.getProduct_condition() + "]");
		// 상품 전체 출력

		if (productDTO.getProduct_condition() == null || productDTO.getProduct_condition().isEmpty()) {
			System.out.println("====model.ProductDAO2.selectAll 상품 전체 목록 출력 (최신순) 시작");
			Object[] args = { productDTO.getProduct_search_criteria(), // 검색 정렬 기준
					productDTO.getProduct_search_criteria(), // 검색 정렬 기준
					productDTO.getProduct_search_criteria(), // 검색 정렬 기준
					productDTO.getProduct_search_criteria(), // 검색 정렬 기준
					productDTO.getProduct_searchKeyword(), // 검색어
					productDTO.getProduct_location(), // 상품 장소 (바다/민물)
					productDTO.getProduct_category(), // 상품 유형 (낚시배/낚시터/낚시카페/수상)
					productDTO.getProduct_page_num(), // 페이지 번호 (첫 데이터)
					productDTO.getProduct_page_num() // 페이지 번호 (마지막 데이터)
			};
			datas = (List<ProductDTO>) jdbcTemplate.query(PRODUCT_SELECTALL, args, new ProductRowMapper_all());
		} else if (productDTO.getProduct_condition().equals("PRODUCT_SELECTALL_CRAWLING")) { // 크롤링 번호 확인
			System.out.println("====model.ProductDAO2.selectAll_PRODUCT_SELECTALL_CRAWLING 시작");
			datas = (List<ProductDTO>) jdbcTemplate.query(PRODUCT_SELECTALL_CRAWLING,
					new ProductRowMapper_all_crawling());
		}
		System.out.println("====model.ProductDAO2.selectAll datas :[" + datas + "]");
		System.out.println("====model.ProductDAO2.selectAll 성공");
		return datas;
	}

	public ProductDTO selectOne(ProductDTO productDTO) { // 한개 출력
		System.out.println("====model.ProductDAO2.selectOne 시작");
		ProductDTO data = null;
		// ProductRowMapper productRowMapper = new ProductRowMapper(); // 미리 생성
		try {
			System.out.println("====model.ProductDAO2.selectOne productDTO.getProduct_condition() : ["
					+ productDTO.getProduct_condition() + "]");
			if (productDTO.getProduct_condition().equals("PRODUCT_BY_INFO")) {// 상품 상세보기
				Object[] args = { productDTO.getProduct_num() }; // 상품번호
				data = jdbcTemplate.queryForObject(SELECTONE, args, new ProductRowMapper_one_by_info());
			} else if (productDTO.getProduct_condition().equals("PRODUCT_BY_CURRENT_STOCK")) { // 사용자가 선택한 일자의 재고 보기
				Object[] args = { productDTO.getProduct_reservation_date(), productDTO.getProduct_num() };
				data = jdbcTemplate.queryForObject(SELECTONE_CURRENT_STOCK, args,
						new ProductRowMapper_one_current_stock());
			} else if (productDTO.getProduct_condition().equals("PRODUCT_NUM_SELECT")) {// 크롤링 상품번호 조회
				data = jdbcTemplate.queryForObject(PRODUCT_NUM_SELECT, new ProductRowMapper_one_num());
			} //else if (productDTO.getProduct_condition().equals("PRODUCT_TOTAL_CNT")) {// 상품 개수 출력
				//data = jdbcTemplate.queryForObject(PRODUCT_TOTAL_CNT, new ProductRowMapper_one_product_cnt());
			//}
			// // 크롤링 확인 전체 출력
			// else if (productDTO.getProduct_condition().equals("PRODUCT_SELECT")) {
			// System.out.println("model.ProductDAO.selectOne 상품데이터 확인을 위한 읽기 시작");
			// data = jdbcTemplate.queryForObject(PRODUCT_SELECT, new
			// ProductRowMapper_one_select());
			// System.out.println("data :"+data);
			// }
			// 페이지네이션에 사용하기 위해 전체 페이지 수 반환
			else if (productDTO.getProduct_condition().equals("PRODUCT_PAGE_COUNT")) {
				System.out.println("====model.ProductDAO2.selectOne 전체 상품 페이지 수 반환 쿼리 읽기 시작");
				Object[] args;
				if (productDTO.getProduct_searchKeyword() != null && !productDTO.getProduct_searchKeyword().isEmpty()) {
					System.out.println("====model.ProductDAO2.selectOne 키워드 검색 페이지 수 반환 쿼리 읽기 시작");
					args = new Object[] { productDTO.getProduct_searchKeyword() };
					data = jdbcTemplate.queryForObject(PRODUCT_TOTAL_PAGE + " " + PRODUCT_SEARCH_PAGE, args,
							new ProductRowMapper_one_page_count());
				} else if ((productDTO.getProduct_location() != null && !productDTO.getProduct_location().isEmpty())
						|| (productDTO.getProduct_category() != null && !productDTO.getProduct_category().isEmpty())) {
					System.out.println("====model.ProductDAO2.selectOne 필터링 검색 페이지 수 반환 쿼리 읽기 시작");
					args = new Object[] { productDTO.getProduct_location(), productDTO.getProduct_category() };
					data = jdbcTemplate.queryForObject(PRODUCT_TOTAL_PAGE + " " + PRODUCT_FILTERING_PAGE, args,
							new ProductRowMapper_one_page_count());
				} else {
					System.out.println("====model.ProductDAO2.selectOne 전체 페이지 수 반환 쿼리 읽기 시작");
					data = jdbcTemplate.queryForObject(PRODUCT_TOTAL_PAGE, new ProductRowMapper_one_page_count());
				}
			} else {
				System.err.println("====model.ProductDAO2.selectOne 쿼리문 읽어오기 컨디션 실패");
			}
		} catch (Exception e) {
			System.err.println("====model.ProductDAO2.selectOne 여기 실패!!!!!!! 널반환");
			return null;
		}

		System.out.println("====model.ProductDAO2.selectOne data : " + data);
		return data;
	}

	public boolean update(ProductDTO productDTO) { // 사장님 상품 변경
		System.out.println("====model.ProductDAO2.update 시작");

		// 변수 초기화
		String product_name = productDTO.getProduct_name();
		int product_price = productDTO.getProduct_price();
		String product_details = productDTO.getProduct_details();
		String product_address = productDTO.getProduct_address();
		String product_location = productDTO.getProduct_location();
		String product_category = productDTO.getProduct_category();
		String product_seller_id = productDTO.getProduct_seller_id();

		Object[] args = null;
		args = new Object[] { product_name, // 상품명
				product_price, // 가격
				product_details, // 설명
				product_address, // 주소
				product_location, // 위치
				product_category, // 카테고리
				product_seller_id // 판매자
		};
		int result = jdbcTemplate.update(PRODUCT_UPDATE, args);
		System.out.println("====model.ProductDAO2.update result : " + result);
		if (result <= 0) {
			System.err.println("====model.ProductDAO2.update 실패");
			return false;
		}
		System.out.println("====model.ProductDAO2.update 종료");
		return true; // 성공적으로 변경 완료
	}

	public boolean delete(ProductDTO productDTO) { // 입력
		System.out.println("====model.ProductDAO2.delete 시작");

		String product_seller_id = productDTO.getProduct_seller_id();
		int product_price = productDTO.getProduct_price();

		return false;
	}

	class ProductRowMapper_all implements RowMapper<ProductDTO> {

		@Override
		public ProductDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			System.out.println("====model.ProductDAO2.ProductRowMapper_all 실행");

			ProductDTO data = new ProductDTO();
			data.setProduct_num(rs.getInt("PRODUCT_NUM")); // 상품 번호
			data.setProduct_name(rs.getString("PRODUCT_NAME")); // 상품명
			data.setProduct_price(rs.getInt("PRODUCT_PRICE")); // 상품 가격
			data.setProduct_address(rs.getString("PRODUCT_ADDRESS")); // 상품 주소
			data.setProduct_location(rs.getString("PRODUCT_LOCATION")); // 상품 장소 (바다,민물)
			data.setProduct_category(rs.getString("PRODUCT_CATEGORY")); // 상품 유형 (낚시배, 낚시터,낚시카페, 수상)
			data.setProduct_avg_rating(rs.getDouble("RATING")); // 별점 평균
			data.setProduct_payment_cnt(rs.getInt("PAYMENT_COUNT")); // 결제 수
			data.setProduct_wishlist_cnt(rs.getInt("WISHLIST_COUNT")); // 찜 수
			data.setProduct_file_dir(rs.getString("FILE_DIR")); // 파일 경로

			System.out.println("====model.ProductDAO2.ProductRowMapper_all 반환");

			return data;
		}
	}

	class ProductRowMapper_all_crawling implements RowMapper<ProductDTO> {

		@Override
		public ProductDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			System.out.println("====model.ProductDAO2.ProductRowMapper_all_crawling 실행");

			ProductDTO data = new ProductDTO();
			data.setProduct_num(rs.getInt("PRODUCT_NUM")); // 상품 번호

			System.out.println("====model.ProductDAO2.ProductRowMapper_all_crawling 반환");

			return data;
		}
	}

	// --------------------------------------------------one--------------------
	class ProductRowMapper_one_by_info implements RowMapper<ProductDTO> {
		// 상품 상세보기
		@Override
		public ProductDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			System.out.println("model.ProductDAO.ProductRowMapper_one_by_info 실행");

			ProductDTO data = new ProductDTO();
			data.setProduct_num(rs.getInt("PRODUCT_NUM")); // 상품 번호
			data.setProduct_name(rs.getString("PRODUCT_NAME")); // 상품명
			data.setProduct_price(rs.getInt("PRODUCT_PRICE")); // 상품 가격
			data.setProduct_details(rs.getString("PRODUCT_DETAILS")); // 상품 설명
			data.setProduct_address(rs.getString("PRODUCT_ADDRESS")); // 상품 주소
			data.setProduct_location(rs.getString("PRODUCT_LOCATION")); // 상품 장소 (바다,민물)
			data.setProduct_category(rs.getString("PRODUCT_CATEGORY")); // 상품 유형 (낚시배, 낚시터)
			data.setProduct_avg_rating(rs.getDouble("RATING")); // 별점 평균
			data.setProduct_payment_cnt(rs.getInt("PAYMENT_COUNT")); // 결제 수
			data.setProduct_wishlist_cnt(rs.getInt("WISHLIST_COUNT")); // 찜 수

			System.out.println("model.ProductDAO.ProductRowMapper_one_by_info 반환");

			return data;
		}
	}

	class ProductRowMapper_one_current_stock implements RowMapper<ProductDTO> {
		// 사용자가 선택한 일자의 재고 보기
		@Override
		public ProductDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			System.out.println("model.ProductDAO.ProductRowMapper_one_current_stock 실행");

			ProductDTO data = new ProductDTO();
			data.setProduct_num(rs.getInt("PRODUCT_NUM")); // 상품 번호
			data.setProduct_cnt(rs.getInt("CURRENT_STOCK")); // 상품의 재고

			System.out.println("model.ProductDAO.ProductRowMapper_one_current_stock 반환");

			return data;
		}
	}

	class ProductRowMapper_one_num implements RowMapper<ProductDTO> {
		// 상품번호 보여주기 (크롤링)
		@Override
		public ProductDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			System.out.println("model.ProductDAO.ProductRowMapper_one_num 실행");

			ProductDTO data = new ProductDTO();
			data.setProduct_num(rs.getInt("MAX_NUM")); // 상품 번호

			System.out.println("model.ProductDAO.ProductRowMapper_one_num 반환");

			return data;
		}
	}

	class ProductRowMapper_one_ditail_owner implements RowMapper<ProductDTO> {
		// 상품 상세보기
		@Override
		public ProductDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			System.out.println("model.ProductDAO.ProductRowMapper_one_ditail_owner 실행");

			ProductDTO data = new ProductDTO();
			data.setProduct_num(rs.getInt("PRODUCT_NUM")); // 상품 번호
			data.setProduct_name(rs.getString("PRODUCT_NAME")); // 상품명
			data.setProduct_price(rs.getInt("PRODUCT_PRICE")); // 상품 가격
			data.setProduct_details(rs.getString("PRODUCT_DETAILS")); // 상품 설명
			data.setProduct_address(rs.getString("PRODUCT_ADDRESS")); // 상품 주소
			data.setProduct_location(rs.getString("PRODUCT_LOCATION")); // 상품 장소 (바다,민물)
			data.setProduct_category(rs.getString("PRODUCT_CATEGORY")); // 상품 유형 (낚시배, 낚시터)
			data.setProduct_avg_rating(rs.getDouble("RATING")); // 별점 평균
			data.setProduct_payment_cnt(rs.getInt("PAYMENT_COUNT")); // 결제 수
			data.setProduct_wishlist_cnt(rs.getInt("WISHLIST_COUNT")); // 찜 수

			System.out.println("model.ProductDAO.ProductRowMapper_one_ditail_owner 반환");

			return data;
		}
	}

	// class ProductRowMapper_one_select implements RowMapper<ProductDTO> {
	// // 전체 출력
	// @Override
	// public ProductDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
	// System.out.println("model.ProductDAO.ProductRowMapper_one_select 실행");
	//
	// ProductDTO data = new ProductDTO();
	// data.setProduct_num(rs.getInt("PRODUCT_NUM")); // 상품 번호
	//
	// System.out.println("model.ProductDAO.ProductRowMapper_one_select 반환");
	//
	// return data;
	// }
	// }
	class ProductRowMapper_one_page_count implements RowMapper<ProductDTO> {
		// 상품 테이블의 전체 개수 출력
		@Override
		public ProductDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			System.out.println("model.ProductDAO.ProductRowMapper_one_page_count 실행");

			ProductDTO data = new ProductDTO();
			data.setProduct_total_page(rs.getInt("PRODUCT_TOTAL_PAGE")); // 상품 개수

			System.out.println("model.ProductDAO.ProductRowMapper_one_page_count 반환");

			return data;
		}
	}

//	class ProductRowMapper_one_product_cnt implements RowMapper<ProductDTO> {
//			// 상품 총 개수
//			@Override
//			public ProductDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
//				System.out.println("model.ProductDAO.ProductRowMapper_one_product_cnt 실행");
//				
//				ProductDTO data = new ProductDTO();
//				data.setProduct_total_cnt(rs.getInt("PRODUCT_TOTAL_CNT")); // 상품 개수
//				
//				System.out.println("model.ProductDAO.ProductRowMapper_one_product_cnt 반환");
//				
//				return data;
//			}
//	}

}
