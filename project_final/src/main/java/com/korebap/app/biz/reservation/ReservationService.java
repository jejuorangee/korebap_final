package com.korebap.app.biz.reservation;

import java.util.List;

public interface ReservationService {
	List<ReservationDTO> selectAll(ReservationDTO reservationDTO);
	ReservationDTO selectOne(ReservationDTO reservationDTO);
	boolean insert(ReservationDTO reservationDTO);
	boolean update(ReservationDTO reservationDTO);
	boolean delete(ReservationDTO reservationDTO);
}
// Service의 메서드가 DAO의 메서드 시그니쳐와 동일해야함!!!!!