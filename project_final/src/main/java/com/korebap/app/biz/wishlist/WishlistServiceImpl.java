package com.korebap.app.biz.wishlist;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("wishlistService")
public class WishlistServiceImpl implements WishlistService{
	
	@Autowired
	WishlistDAO wishlistDAO;

	@Override
	public List<WishlistDTO> selectAll(WishlistDTO wishlistDTO) {
		return this.wishlistDAO.selectAll(wishlistDTO);
	}

	@Override
	public WishlistDTO selectOne(WishlistDTO wishlistDTO) {
		return this.wishlistDAO.selectOne(wishlistDTO);
	}

	@Override
	public boolean insert(WishlistDTO wishlistDTO) {
		return this.wishlistDAO.insert(wishlistDTO);
	}

	@Override
	public boolean update(WishlistDTO wishlistDTO) {
		return false;
	}

	@Override
	public boolean delete(WishlistDTO wishlistDTO) {
		return this.wishlistDAO.delete(wishlistDTO);
	}

}
