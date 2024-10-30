package com.korebap.app.biz.like;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("LikeService")
public class LikeServiceImpl implements LikeService{

	@Autowired
	private LikeDAO likeDAO;
	
	@Override
	public List<LikeDTO> selectAll(LikeDTO goodLikeDTO) {
		return null;
	}

	@Override
	public LikeDTO selectOne(LikeDTO likeDTO) {
		return this.likeDAO.selectOne(likeDTO);
	}

	@Override
	public boolean insert(LikeDTO likeDTO) {
		return this.likeDAO.insert(likeDTO);
	}

	@Override
	public boolean update(LikeDTO likeDTO) {
		return false;
	}

	@Override
	public boolean delete(LikeDTO likeDTO) {
		return this.likeDAO.delete(likeDTO);
	}

}
