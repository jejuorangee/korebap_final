package com.korebap.app.biz.payment;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// 인터페이스인 MemberService의 구현체(실현체)
@Service("PaymentService")
public class PaymentServiceImpl implements PaymentService {
	@Autowired
	private PaymentDAO paymentDAO;
	
	@Override
	public List<PaymentDTO> selectAll(PaymentDTO paymentDTO) {
		return null;
	}

	@Override
	public PaymentDTO selectOne(PaymentDTO paymentDTO) {
		return this.paymentDAO.selectOne(paymentDTO);
	}

	@Override
	public boolean insert(PaymentDTO paymentDTO) {
		return this.paymentDAO.insert(paymentDTO);
	}

	@Override
	public boolean update(PaymentDTO paymentDTO) {
		return this.paymentDAO.update(paymentDTO);
	}

	@Override
	public boolean delete(PaymentDTO paymentDTO) {
		return false;
	}
}
