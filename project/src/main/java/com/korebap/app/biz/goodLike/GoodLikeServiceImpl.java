package com.korebap.app.biz.goodLike;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("goodLikeService")
public class GoodLikeServiceImpl implements GoodLikeService{

	@Autowired
	private GoodLikeDAO2 goodLikeDAO;
	
	@Override
	public List<GoodLikeDTO> selectAll(GoodLikeDTO goodLikeDTO) {
		System.out.println("goodlike update기능 미구현으로 null 반환");
		return this.goodLikeDAO.selectAll(goodLikeDTO);
	}

	@Override
	public GoodLikeDTO selectOne(GoodLikeDTO goodLikeDTO) {
		return this.goodLikeDAO.selectOne(goodLikeDTO);
	}

	@Override
	public boolean insert(GoodLikeDTO goodLikeDTO) {
		return this.goodLikeDAO.insert(goodLikeDTO);
	}

	@Override
	public boolean update(GoodLikeDTO goodLikeDTO) {
		System.out.println("goodlike update기능 미구현으로 false 반환");
		return this.goodLikeDAO.update(goodLikeDTO);
	}

	@Override
	public boolean delete(GoodLikeDTO goodLikeDTO) {
		return this.goodLikeDAO.delete(goodLikeDTO);
	}

}
