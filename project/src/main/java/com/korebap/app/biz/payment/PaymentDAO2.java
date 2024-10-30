package com.korebap.app.biz.payment;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class PaymentDAO2 {
	// 결제 생성
	private final String INSERT = "INSERT INTO PAYMENT (PAYMENT_MEMBER_ID, PAYMENT_PRODUCT_NUM, PAYMENT_ORDER_NUM, MERCHANT_UID, PAYMENT_PRICE, PAYMENT_STATUS, PAYMENT_METHOD) "
			+ "VALUES (?, ?, ?, ?, ?, ?, ?)";
	// 결제 변경 (완료 > 취소)
	private final String UPDATE = "UPDATE PAYMENT SET PAYMENT_STATUS = '결제취소' WHERE PAYMENT_NUM = ? AND PAYMENT_STATUS = '결제완료'";
	// 결제 내역 반환
	private final String SELECTONE_BY_MERCHANT_UID = "SELECT PAYMENT_NUM, PAYMENT_MEMBER_ID, PAYMENT_PRODUCT_NUM, PAYMENT_ORDER_NUM, MERCHANT_UID, PAYMENT_REGISTRATION_DATE, PAYMENT_PRICE, PAYMENT_STATUS, PAYMENT_METHOD "
			+ "FROM PAYMENT WHERE MERCHANT_UID = ?";
	// 결제 번호로 결제 내역 반환
	private final String SELECTONE_BY_PAYMENT_NUM = "SELECT PAYMENT_NUM, PAYMENT_MEMBER_ID, PAYMENT_PRODUCT_NUM, PAYMENT_ORDER_NUM, MERCHANT_UID, PAYMENT_REGISTRATION_DATE, PAYMENT_PRICE, PAYMENT_STATUS, PAYMENT_METHOD "
			+ "FROM PAYMENT WHERE PAYMENT_NUM = ?";
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public boolean insert(PaymentDTO paymentDTO) { // 입력
		System.out.println("====model.PaymentDAO2.insert 시작");
		String payment_member_id = paymentDTO.getPayment_member_id(); // 결제자 ID (PK)
		int payment_product_num = paymentDTO.getPayment_product_num(); // 결제 상품 번호
		String payment_order_num = paymentDTO.getPayment_order_num(); // 주문 번호 (포트원 생성 번호)
		String merchant_uid = paymentDTO.getMerchant_uid(); // UUID + 시간 조합 번호 (controller 사용)
		int payment_price = paymentDTO.getPayment_price(); // 결제 금액
		String payment_status = paymentDTO.getPayment_status(); // 결제 상태 : 결제완료, 결제취소
		String payment_method = paymentDTO.getPayment_method(); // 결제 방법 : 카드, 페이

		int result = jdbcTemplate.update(INSERT, payment_member_id, payment_product_num, payment_order_num,
				merchant_uid, payment_price, payment_status, payment_method);
		System.out.println("====model.PaymentDAO2.insert result : " + result);
		if (result <= 0) {// 변경이 된 행 수가 0보다 작거나 같다면
			System.err.println("====model.PaymentDAO2.insert 실패");
			return false; // false 반환
		}
		System.out.println("====model.PaymentDAO2.update 종료");
		return true;
	}

	public boolean update(PaymentDTO paymentDTO) { // 변경
		System.out.println("====model.PaymentDAO2.update 시작");
		int result = jdbcTemplate.update(UPDATE, paymentDTO.getPayment_num());

		System.out.println("====model.PaymentDAO2.update result : " + result);
		if (result <= 0) { // 만약 변경된 행이 없다면
			System.err.println("====model.PaymentDAO2.update 실패");
			return false; // false 반환
		}
		System.out.println("====model.PaymentDAO2.update 종료");
		return true;
	}

	public PaymentDTO selectOne(PaymentDTO paymentDTO) {
		System.out.println("====model.PaymentDAO2.selectOne 시작");
		PaymentDTO data = null;

		System.out.println("====model.PaymentDAO2.selectOne paymentDTO.getPayment_condition() : ["
				+ paymentDTO.getPayment_condition() + "]");
		if (paymentDTO.getPayment_condition().equals("SELECT_BY_MERCHANT_UID")) {
			Object[] args = { paymentDTO.getMerchant_uid() };
			data = jdbcTemplate.queryForObject(SELECTONE_BY_MERCHANT_UID, args, new PaymentRowMapper_one());
		} else if (paymentDTO.getPayment_condition().equals("SELECT_BY_PAYMENT_NUM")) {
			Object[] args = { paymentDTO.getPayment_num() }; // 컨트롤러에서 사용하는 번호
			data = jdbcTemplate.queryForObject(SELECTONE_BY_PAYMENT_NUM, args, new PaymentRowMapper_one());
		} else {
			System.out.println("====model.PaymentDAO2.selectOne 컨디션 에러");
		}
		System.out.println("====model.PaymentDAO2.selectOne data : " + data);
		return data;
	}

	// 기능 미사용으로 private
	public boolean delete(PaymentDTO paymentDTO) { // 입력

		return false;
	}

	// 기능 미사용으로 private
	public List<PaymentDTO> selectAll(PaymentDTO paymentDTO) { // 전체 출력
		ArrayList<PaymentDTO> datas = new ArrayList<PaymentDTO>();
		return datas;
	}

}

class PaymentRowMapper_one implements RowMapper<PaymentDTO> {
	// 결제 내역 반환
	@Override
	public PaymentDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		System.out.println("====model.PaymentRowMapper_one 실행");

		PaymentDTO data = new PaymentDTO();

		data.setPayment_num(rs.getInt("PAYMENT_NUM")); // 결제 번호
		data.setPayment_member_id(rs.getString("PAYMENT_MEMBER_ID")); // 결제자 ID
		data.setPayment_product_num(rs.getInt("PAYMENT_PRODUCT_NUM")); // 결제한 상품 번호
		data.setPayment_order_num(rs.getString("PAYMENT_ORDER_NUM")); // 포트원에서 생성된 결제 번호
		data.setMerchant_uid(rs.getString("MERCHANT_UID")); // controller에서 UUID+시간 조합으로 사용하는 번호
		data.setPayment_registration_date(rs.getDate("PAYMENT_REGISTRATION_DATE")); // 결제일
		data.setPayment_price(rs.getInt("PAYMENT_PRICE")); // 결제 금액
		data.setPayment_status(rs.getString("PAYMENT_STATUS")); // 결제 상태 (결제완료, 결제 취소)
		data.setPayment_method(rs.getString("PAYMENT_METHOD")); // 결제 방법 (card, pay)

		System.out.println("====model.PaymentRowMapper_one 반환");
		return data;
	}
}
