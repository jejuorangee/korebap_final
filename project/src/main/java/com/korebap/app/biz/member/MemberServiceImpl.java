package com.korebap.app.biz.member;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// 인터페이스인 MemberService의 구현체(실현체)
@Service("memberService")
public class MemberServiceImpl implements MemberService {
	@Autowired
	private MemberDAO2 memberDAO;
	
	@Override
	public List<MemberDTO> selectAll(MemberDTO memberDTO) {
		return this.memberDAO.selectAll(memberDTO);
	}

	@Override
	public MemberDTO selectOne(MemberDTO memberDTO) {
		return this.memberDAO.selectOne(memberDTO);
	}

	@Override
	public boolean insert(MemberDTO memberDTO) {
		return this.memberDAO.insert(memberDTO);
	}

	@Override
	public boolean update(MemberDTO memberDTO) {
		return this.memberDAO.update(memberDTO);
	}

	@Override
	public boolean delete(MemberDTO memberDTO) {
		return this.memberDAO.delete(memberDTO);
	}
}
