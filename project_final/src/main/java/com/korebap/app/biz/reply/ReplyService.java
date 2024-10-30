package com.korebap.app.biz.reply;

import java.util.List;

public interface ReplyService {
	List<ReplyDTO> selectAll(ReplyDTO replyDTO);
	ReplyDTO selectOne(ReplyDTO replyDTO);
	boolean insert(ReplyDTO replyDTO);
	boolean update(ReplyDTO replyDTO);
	boolean delete(ReplyDTO replyDTO);
}
// Service의 메서드가 DAO의 메서드 시그니쳐와 동일해야함!!!!!