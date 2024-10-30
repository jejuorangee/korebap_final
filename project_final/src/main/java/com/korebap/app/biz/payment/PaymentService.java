package com.korebap.app.biz.payment;

import java.util.List;

public interface PaymentService {
	List<PaymentDTO> selectAll(PaymentDTO paymentDTO);
	PaymentDTO selectOne(PaymentDTO paymentDTO);
	boolean insert(PaymentDTO paymentDTO);
	boolean update(PaymentDTO paymentDTO);
	boolean delete(PaymentDTO paymentDTO);
}
// Service의 메서드가 DAO의 메서드 시그니쳐와 동일해야함!!!!!