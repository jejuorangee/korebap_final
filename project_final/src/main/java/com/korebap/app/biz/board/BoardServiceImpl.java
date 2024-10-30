package com.korebap.app.biz.board;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// 인터페이스인 BoardService의 구현체(실현체)
@Service("boardService")//어노테이션
//컨트롤러, Actioln, ServiceImpl 류에 사용
// 사용자가 호출(look up) 할 때 사용
public class BoardServiceImpl implements BoardService{
// 의존성 주입을 통해 다른 객체를 자동으로 연결해주는 어노테이션
	@Autowired
	private BoardDAO2 boardDAO;// 멤버변수
	

	@Override
	public BoardDTO selectOne(BoardDTO boardDTO) {
		return this.boardDAO.selectOne(boardDTO);
	}

	@Override
	public boolean insert(BoardDTO boardDTO) {
		return this.boardDAO.insert(boardDTO);
	}

	@Override
	public boolean update(BoardDTO boardDTO) {
		return this.boardDAO.update(boardDTO);
	}

	@Override
	public boolean delete(BoardDTO boardDTO) {
		return this.boardDAO.delete(boardDTO);
	}

	@Override
	public List<BoardDTO> selectAll(BoardDTO boardDTO) {
		return this.boardDAO.selectAll(boardDTO);

	}

}
