package com.korebap.app.biz.like;

import java.util.List;

public interface LikeService {
	public List<LikeDTO> selectAll(LikeDTO goodLikeDTO);
	public LikeDTO selectOne(LikeDTO goodLikeDTO);
	public boolean insert(LikeDTO goodLikeDTO);
	public boolean update(LikeDTO goodLikeDTO);
	public boolean delete(LikeDTO goodLikeDTO);
}
