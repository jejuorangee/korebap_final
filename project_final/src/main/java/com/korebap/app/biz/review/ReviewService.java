package com.korebap.app.biz.review;

import java.util.List;

public interface ReviewService {
	List<ReviewDTO> selectAll(ReviewDTO reviewDTO);
	ReviewDTO selectOne(ReviewDTO reviewDTO);
	boolean insert(ReviewDTO reviewDTO);
	boolean update(ReviewDTO reviewDTO);
	boolean delete(ReviewDTO reviewDTO);
}
// Service의 메서드가 DAO의 메서드 시그니쳐와 동일해야함!!!!!