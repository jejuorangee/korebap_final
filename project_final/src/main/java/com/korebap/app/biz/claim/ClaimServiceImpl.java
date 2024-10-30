package com.korebap.app.biz.claim;

import java.util.List;

//import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// 인터페이스인 BoardService의 구현체(실현체)
@Service("claimService")//어노테이션
//컨트롤러, Actioln, ServiceImpl 류에 사용
// 사용자가 호출(look up) 할 때 사용
public class ClaimServiceImpl implements ClaimService{
	@Autowired
// 의존성 주입을 통해 다른 객체를 자동으로 연결해주는 어노테이션
	private ClaimDAO claimDAO;// 멤버변수

	@Override
	public List<ClaimDTO> selectAll(ClaimDTO claimDTO) {
		return null;
	}

	@Override
	public ClaimDTO selectOne(ClaimDTO claimDTO) {
		return this.claimDAO.selectOne(claimDTO);
	}

	@Override
	public boolean insert(ClaimDTO claimDTO) {
		return this.claimDAO.insert(claimDTO);
	}

	@Override
	public boolean update(ClaimDTO claimDTO) {
		return false;
	}

	@Override
	public boolean delete(ClaimDTO claimDTO) {
		return false;
	}
}
