package com.korebap.app.biz.review;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// 인터페이스인 BoardService의 구현체(실현체)
@Service("reviewService")//어노테이션
//컨트롤러, Actioln, ServiceImpl 류에 사용
// 사용자가 호출(look up) 할 때 사용
public class ReviewServiceImpl implements ReviewService{
	@Autowired
// 의존성 주입을 통해 다른 객체를 자동으로 연결해주는 어노테이션
	private ReviewDAO reviewDAO;// 멤버변수
	

	@Override
	public ReviewDTO selectOne(ReviewDTO reviewDTO) {
		return null;
	}

	@Override
	public boolean insert(ReviewDTO reviewDTO) {
		return this.reviewDAO.insert(reviewDTO);
	}

	@Override
	public boolean update(ReviewDTO reviewDTO) {
		return this.reviewDAO.update(reviewDTO);
	}

	@Override
	public boolean delete(ReviewDTO reviewDTO) {
		return this.reviewDAO.delete(reviewDTO);
	}

	@Override
	public List<ReviewDTO> selectAll(ReviewDTO reviewDTO) {
		return this.reviewDAO.selectAll(reviewDTO);

	}

}
