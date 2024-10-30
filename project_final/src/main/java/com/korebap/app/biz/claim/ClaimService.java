package com.korebap.app.biz.claim;

import java.util.List;


public interface ClaimService {
	List<ClaimDTO> selectAll(ClaimDTO claimDTO);
	ClaimDTO selectOne(ClaimDTO claimDTO);
	boolean insert(ClaimDTO claimDTO);
	boolean update(ClaimDTO claimDTO);
	boolean delete(ClaimDTO claimDTO);
}
// Service의 메서드가 DAO의 메서드 시그니쳐와 동일해야함!!!!!