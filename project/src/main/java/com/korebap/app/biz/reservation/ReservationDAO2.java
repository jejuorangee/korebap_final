package com.korebap.app.biz.reservation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class ReservationDAO2 {
	// 예약 생성
	private final String RESERVATION_INSERT = "INSERT INTO RESERVATION (RESERVATION_PAYMENT_NUM, RESERVATION_REGISTRATION_DATE) VALUES (?, ?)";
	// 예약 내용 변경 (예약 상태 변경)
	private final String RESERVATION_UPDATE = "UPDATE RESERVATION SET RESERVATION_STATUS = '예약취소' WHERE RESERVATION_NUM = ?";
	// 사장님 예약건 전체보기 - 상품 판매자 ID
	// 10/27 수정 : 사장님 ID로 사장님 전체 예약건 보기
	private final String RESERVATION_OWNER_SELECTALL = "SELECT R.RESERVATION_NUM,R.RESERVATION_REGISTRATION_DATE,R.RESERVATION_STATUS,COALESCE(PR.PRODUCT_NUM, 0) AS PRODUCT_NUM,COALESCE(PR.PRODUCT_NAME, '존재하지 않는 상품입니다.') AS PRODUCT_NAME,\r\n"
			+ "P.PAYMENT_PRICE,M.MEMBER_ID AS BUYER_ID,M.MEMBER_NAME AS BUYER_NAME,M.MEMBER_PHONE AS BUYER_PHONE FROM RESERVATION R JOIN PAYMENT P ON R.RESERVATION_PAYMENT_NUM = P.PAYMENT_NUM\r\n"
			+ "JOIN PRODUCT PR ON P.PAYMENT_PRODUCT_NUM = PR.PRODUCT_NUM JOIN MEMBER M ON P.PAYMENT_MEMBER_ID = M.MEMBER_ID WHERE PR.PRODUCT_SELLER_ID = ? ORDER BY R.RESERVATION_REGISTRATION_DATE DESC";
	// 내 예약 전체보기
	private final String RESERVATION_SELECTALL = "SELECT R.RESERVATION_NUM, R.RESERVATION_REGISTRATION_DATE, R.RESERVATION_STATUS, "
			+ "COALESCE(PR.PRODUCT_NUM, 0) AS PRODUCT_NUM, COALESCE(PR.PRODUCT_NAME, '존재하지 않는 상품입니다.') AS PRODUCT_NAME, P.PAYMENT_PRICE "
			+ "FROM RESERVATION R " + "JOIN PAYMENT P ON R.RESERVATION_PAYMENT_NUM = P.PAYMENT_NUM "
			+ "LEFT JOIN PRODUCT PR ON P.PAYMENT_PRODUCT_NUM = PR.PRODUCT_NUM "
			+ "WHERE P.PAYMENT_MEMBER_ID = ? ORDER BY R.RESERVATION_REGISTRATION_DATE DESC";
	// 내 예약 상세보기
	private final String RESERVATION_SELECTONE = "SELECT P.PAYMENT_NUM, R.RESERVATION_NUM, R.RESERVATION_REGISTRATION_DATE, R.RESERVATION_STATUS, "
			+ "COALESCE(PR.PRODUCT_NUM, 0) AS PRODUCT_NUM, COALESCE(PR.PRODUCT_NAME, '존재하지 않는 상품입니다.') AS PRODUCT_NAME, "
			+ "P.PAYMENT_PRICE, P.PAYMENT_REGISTRATION_DATE, P.PAYMENT_METHOD, P.PAYMENT_STATUS, "
			+ "COALESCE(P.PAYMENT_MEMBER_ID, '탈퇴한 회원입니다.') AS PAYMENT_MEMBER_ID, "
			+ "COALESCE(M.MEMBER_NAME, '탈퇴한 회원입니다.') AS MEMBER_NAME, "
			+ "COALESCE(M.MEMBER_PHONE, '탈퇴한 회원입니다.') AS MEMBER_PHONE, " + "F.FILE_DIR " + "FROM RESERVATION R "
			+ "JOIN PAYMENT P ON R.RESERVATION_PAYMENT_NUM = P.PAYMENT_NUM "
			+ "LEFT JOIN PRODUCT PR ON P.PAYMENT_PRODUCT_NUM = PR.PRODUCT_NUM "
			+ "LEFT JOIN MEMBER M ON P.PAYMENT_MEMBER_ID = M.MEMBER_ID " + "LEFT JOIN ( "
			+ "    SELECT FILE_DIR, PRODUCT_ITEM_NUM, "
			+ "           ROW_NUMBER() OVER (PARTITION BY PRODUCT_ITEM_NUM ORDER BY FILE_NUM) AS ROW_NUM "
			+ "    FROM IMAGEFILE " + ") F ON F.ROW_NUM = 1 AND PR.PRODUCT_NUM = F.PRODUCT_ITEM_NUM "
			+ "WHERE R.RESERVATION_NUM = ?";
	// 사장님 예약 상세보기 - 상품 판매자 ID, 예약 번호 
	// 10/27 수정 : 사장님 ID, 예약번호로 상세예약건 보기
	private final String RESERVATION_OWNER_SELECTONE = "SELECT R.RESERVATION_NUM,R.RESERVATION_REGISTRATION_DATE,R.RESERVATION_STATUS,COALESCE(PR.PRODUCT_NUM, 0) AS PRODUCT_NUM,COALESCE(PR.PRODUCT_NAME, '존재하지 않는 상품입니다.') AS PRODUCT_NAME,\r\n"
			+ "P.PAYMENT_PRICE,P.PAYMENT_REGISTRATION_DATE,P.PAYMENT_METHOD,P.PAYMENT_STATUS,M.MEMBER_ID AS BUYER_ID,M.MEMBER_NAME AS BUYER_NAME,M.MEMBER_PHONE AS BUYER_PHONE\r\n"
			+ "FROM RESERVATION R JOIN PAYMENT P ON R.RESERVATION_PAYMENT_NUM = P.PAYMENT_NUM JOIN PRODUCT PR ON P.PAYMENT_PRODUCT_NUM = PR.PRODUCT_NUM\r\n"
			+ "JOIN member M ON P.PAYMENT_MEMBER_ID = M.MEMBER_ID WHERE PR.PRODUCT_SELLER_ID = ? AND R.RESERVATION_NUM = ?";
	// 가장 마지막에 저장된 PK 번호 보여주기
	private final String SELECTONE_LAST_NUM = "SELECT RESERVATION_NUM FROM RESERVATION ORDER BY RESERVATION_NUM DESC LIMIT 1";

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public boolean insert(ReservationDTO reservationDTO) { // 추가
		System.out.println("====model.reservationDAO2.insert 시작");
		int result = 0;
		// pstmt.setDate(2, reservationDTO.getReservation_registration_date()); // 예약일
		// 예약일 import java.sql.Date; 에서 String 처리가 어려워 java.util.Date; 으로 변경
		// util을 사용하면 객체를 sql.Date 객체로 변환이 필요함
		Date utilDate = reservationDTO.getReservation_registration_date();
		java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime()); // 변환
		Date sql_date = sqlDate;
		result = jdbcTemplate.update(RESERVATION_INSERT, reservationDTO.getReservation_payment_num(), sql_date);

		System.out.println("====model.reservationDAO2.insert result : " + result);
		if (result <= 0) { // 만약 executeUpdate를 통하여 반환받은 정수가 0보다 작거나 같다면
			System.err.println("====model.reservationDAO2.insert 실패");
			return false; // 변경된 행 수가 없는 것이므로 false 반환
		}
		System.out.println("====model.reservationDAO2.insert 성공");
		return true;
	}

	public boolean update(ReservationDTO reservationDTO) { // 변경
		System.out.println("====model.reservationDAO2.update 시작");
		int result = 0;
		result = jdbcTemplate.update(RESERVATION_UPDATE, reservationDTO.getReservation_num());
		System.out.println("====model.reservationDAO2.update result : " + result);
		if (result <= 0) { // 만약 executeUpdate를 통하여 반환받은 정수가 0보다 작거나 같다면
			System.err.println("====model.reservationDAO2.update 실패");
			return false; // 변경된 행 수가 없는 것이므로 false 반환
		}
		System.out.println("====model.reservationDAO2.update 종료");
		return true;
	}

	public List<ReservationDTO> selectAll(ReservationDTO reservationDTO) { // 전체 출력
		System.out.println("====model.reservationDAO2.selectAll 시작");
		List<ReservationDTO> datas = new ArrayList<ReservationDTO>();
		System.out.println("====model.reservationDAO2.selectAll reservationDTO.getReservation_condition() : ["
				+ reservationDTO.getReservation_condition() + "]");
		if (reservationDTO.getReservation_condition().equals("RESERVATION_SELECTALL")) {
			Object[] args = { reservationDTO.getReservation_member_id() };// 회원 ID
			datas = jdbcTemplate.query(RESERVATION_SELECTALL, args,
					new ReservationRowMapper_all());
		} else if (reservationDTO.getReservation_condition().equals("RESERVATION_OWNER_SELECTALL")) {
			Object[] args = { reservationDTO.getReservation_seller_id() }; // 사장님 ID
			datas = (List<ReservationDTO>) jdbcTemplate.query(RESERVATION_OWNER_SELECTALL, args,
					new ReservationRowMapper_all());
		} else {
			System.err.println("====model.reservationDAO2.selectAll 컨디션 에러");
		}

		System.out.println("====model.reservationDAO2.selectAll datas : " + datas);
		return datas;
	}

	public ReservationDTO selectOne(ReservationDTO reservationDTO) { // 한개 출력
		System.out.println("====model.reservationDAO2.selectOne 시작");
		ReservationDTO data = null;

		System.out.println("====model.reservationDAO2.selectOne reservationDTO.getReservation_condition() : ["
				+ reservationDTO.getReservation_condition() + "]");
		if (reservationDTO.getReservation_condition().equals("RESERVATION_SELECTONE")) { // 내 예약 내역 상세보기
			Object[] args = { reservationDTO.getReservation_num() }; // 예약 번호 (PK)
			data = jdbcTemplate.queryForObject(RESERVATION_SELECTONE, args, new ReservationRowMapper_one());
		}
		else if(reservationDTO.getReservation_condition().equals("RESERVATION_OWNER_SELECTONE")) {
			Object[] args = { reservationDTO.getReservation_seller_id(), reservationDTO.getReservation_num() }; //상품 판매자 ID, 예약 번호
			data = jdbcTemplate.queryForObject(RESERVATION_OWNER_SELECTONE, args, new ReservationRowMapper_one_owner());
		}
		else if (reservationDTO.getReservation_condition().equals("RESERVATION_LAST_NUM")) {
			data = jdbcTemplate.queryForObject(SELECTONE_LAST_NUM, new ReservationRowMapper_one_last_num());
		} 
		else {
			System.err.println("====model.reservationDAO2.selectOne 컨디션 실패");
		}
		System.out.println("====model.reservationDAO2.selectOne data : " + data);
		return data;
	}

	// 기능 미사용으로 private
	public boolean delete(ReservationDTO reservationDTO) { // 삭제
		return false;
	}
}

class ReservationRowMapper_all implements RowMapper<ReservationDTO> {
	// 예약 전체 출력
	@Override
	public ReservationDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		System.out.println("====model.ReservationRowMapper_all 시작");

		ReservationDTO data = new ReservationDTO();
		data.setReservation_num(rs.getInt("RESERVATION_NUM")); // 예약 번호
		java.sql.Date sqlDate = rs.getDate("RESERVATION_REGISTRATION_DATE"); // DB에 저장된 예약일
		data.setReservation_registration_date(new Date(sqlDate.getTime())); // 예약일 (상품 이용일)
		data.setReservation_status(rs.getString("RESERVATION_STATUS")); // 예약 상태 (예약 완료, 예약 취소)
		data.setReservation_product_num(rs.getInt("PRODUCT_NUM")); // 예약 상품 번호
		data.setReservation_product_name(rs.getString("PRODUCT_NAME")); // 예약 상품명
		data.setReservation_price(rs.getInt("PAYMENT_PRICE")); // 예약(결제) 금액

		System.out.println("model.ReservationRowMapper_all 종료");
		return data;
	}
}

class ReservationRowMapper_one implements RowMapper<ReservationDTO> {
	// 내 예약 상세 보기
	@Override
	public ReservationDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		System.out.println("model.ReservationRowMapper_one 시작");

		ReservationDTO data = new ReservationDTO();
		data.setReservation_num(rs.getInt("RESERVATION_NUM")); // 예약 번호
		data.setReservation_registration_date(rs.getDate("RESERVATION_REGISTRATION_DATE")); // 예약일 (상품 이용일)
		data.setReservation_status(rs.getString("RESERVATION_STATUS")); // 예약 상태 (예약 완료, 예약 취소)
		data.setReservation_product_num(rs.getInt("PRODUCT_NUM")); // 예약 상품 번호
		data.setReservation_product_file_dir(rs.getString("FILE_DIR")); // 예약 상품 썸네일
		data.setReservation_product_name(rs.getString("PRODUCT_NAME")); // 예약 상품명
		data.setReservation_price(rs.getInt("PAYMENT_PRICE")); // 예약(결제) 금액
		data.setReservation_payment_date(rs.getDate("PAYMENT_REGISTRATION_DATE")); // 결제일
		data.setReservation_payment_num(rs.getInt("PAYMENT_NUM")); // 결제 번호
		data.setReservation_payment_method(rs.getString("PAYMENT_METHOD")); // 결제 방법 (카드, 페이)
		data.setReservation_payment_status(rs.getString("PAYMENT_STATUS")); // 결제 상태 (결제 완료, 결제 취소)
		data.setReservation_member_id(rs.getString("PAYMENT_MEMBER_ID")); // 결제자 ID
		data.setReservation_member_name(rs.getString("MEMBER_NAME")); // 예약자 성명
		data.setReservation_member_phone(rs.getString("MEMBER_PHONE")); // 예약자 핸드폰 번호

		System.out.println("model.ReservationRowMapper_one 종료");
		return data;
	}
}

class ReservationRowMapper_one_last_num implements RowMapper<ReservationDTO> { // 예약 마지막 pk
	
	@Override
	public ReservationDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		System.out.println("model.ReservationRowMapper_one_last_num 시작");

		ReservationDTO data = new ReservationDTO();
		data.setReservation_num(rs.getInt("RESERVATION_NUM")); // 예약 번호

		System.out.println("model.ReservationRowMapper_one_last_num 종료");
		return data;
	}
}
class ReservationRowMapper_one_owner implements RowMapper<ReservationDTO> { // 예약 마지막 pk
	
	@Override
	public ReservationDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		System.out.println("model.ReservationRowMapper_one_owner 시작");
	    
		ReservationDTO data = new ReservationDTO();
		data.setReservation_num(rs.getInt("RESERVATION_NUM")); // 예약 번호
		data.setReservation_registration_date(rs.getDate("RESERVATION_REGISTRATION_DATE")); // 예약일 (상품 이용일)
		data.setReservation_status(rs.getString("RESERVATION_STATUS")); // 예약 상태 (예약 완료, 예약 취소)
		data.setReservation_product_num(rs.getInt("PRODUCT_NUM")); // 예약 상품 번호
		data.setReservation_product_name(rs.getString("PRODUCT_NAME")); // 예약 상품 이름
		data.setReservation_price(rs.getInt("PAYMENT_PRICE")); // 예약(결제) 금액
		data.setReservation_member_id(rs.getString("PAYMENT_MEMBER_ID")); // 결제자 ID
		data.setReservation_member_name(rs.getString("MEMBER_NAME")); // 예약자 성명
		data.setReservation_member_phone(rs.getString("MEMBER_PHONE")); // 예약자 핸드폰 번호
		
		System.out.println("model.ReservationRowMapper_one_owner 종료");
		return data;
	}
}
