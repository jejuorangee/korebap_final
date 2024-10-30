package com.korebap.app.biz.board;

import java.util.List;

public interface BoardService {
	List<BoardDTO> selectAll(BoardDTO boardDTO);
	BoardDTO selectOne(BoardDTO boardDTO);
	boolean insert(BoardDTO boardDTO);
	boolean update(BoardDTO boardDTO);
	boolean delete(BoardDTO boardDTO);
}
// Service의 메서드가 DAO의 메서드 시그니쳐와 동일해야함!!!!!