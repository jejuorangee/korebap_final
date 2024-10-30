package com.korebap.app.biz.like;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import com.korebap.app.biz.common.JDBCUtil;


@Repository
public class LikeDAO {
	private final String INSERT = "INSERT INTO GOODLIKE(LIKE_BOARD_NUM,LIKE_MEMBER_ID) VALUES(?,?)";
	private final String DELETE = "DELETE FROM GOODLIKE WHERE LIKE_BOARD_NUM = ? AND LIKE_MEMBER_ID = ?";
	private final String SELECTONE= "SELECT LIKE_BOARD_NUM, LIKE_MEMBER_ID FROM GOODLIKE WHERE LIKE_BOARD_NUM = ? AND LIKE_MEMBER_ID = ?";
	
	//"INSERT INTO GOODLIKE(LIKE_BOARD_NUM,LIKE_MEMBER_ID) VALUES(?,?)"
	public boolean insert(LikeDTO goodLikeDTO){   // 입력
		System.out.println("goodLike.GoodLikeDAO.insert 시작");
		// 좋아요 추가
		// 좋아요 받은 글에 좋아요 개수 +1씩 입력
		Connection conn=JDBCUtil.connect(); // JDBC연결
		PreparedStatement pstmt=null;   // SQL 실행

		try {
			pstmt=conn.prepareStatement(INSERT);//INSERT 실행	
			pstmt.setInt(1,goodLikeDTO.getGoodLike_board_num()); // 게시글
			pstmt.setString(2,goodLikeDTO.getGoodLike_member_id());   // 회원

			int result=pstmt.executeUpdate();
			if(result<=0) {
				System.err.println("goodLike.GoodLikeDAO.insert 실패");
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("goodLike.GoodLikeDAO.insert SQL 실패");
			return false;
		}finally {
			JDBCUtil.disconnect(pstmt,conn); // 종료
		}
		System.out.println("goodLike.GoodLikeDAO.insert 성공");
		return true;

	}
	
//	현재 사용되지 않지만, 나중에 구현될 수 있음
	private boolean update(LikeDTO goodLikeDTO){   // 업데이트
		return false;
	}
	
	//"DELETE FROM GOODLIKE WHERE LIKE_BOARD_NUM = ? AND LIKE_MEMBER_ID = ?"
	public boolean delete(LikeDTO goodLikeDTO){   // 삭제
		System.out.println("goodLike.GoodLikeDAO.delete 시작");
		// 좋아요단 게시글에 좋아요 지우기.
		Connection conn=JDBCUtil.connect(); // JDBC연결
		PreparedStatement pstmt=null;   // SQL 실행

		try {
			pstmt=conn.prepareStatement(DELETE);
			pstmt.setInt(1,goodLikeDTO.getGoodLike_board_num());
			pstmt.setString(2,goodLikeDTO.getGoodLike_member_id());

			int result=pstmt.executeUpdate();
			if(result<=0) {
				System.err.println("goodLike.GoodLikeDAO.delete 실패");
				return false;
			}
		} catch (SQLException e) {
			System.err.println("goodLike.GoodLikeDAO.delete SQL 실패");
			return false;
		} finally {
			JDBCUtil.disconnect(pstmt,conn); // 종료
		}
		System.err.println("goodLike.GoodLikeDAO.delete 성공");
		return true;
	}
	
//	현재 사용되지 않지만, 나중에 구현될 수 있음
	private ArrayList<LikeDTO> selectAll(LikeDTO goodLikeDTO){ // 전체 출력
		ArrayList<LikeDTO> datas=new ArrayList<LikeDTO>();
		return datas;
	}
	
	public LikeDTO selectOne(LikeDTO goodLikeDTO){// 한명 출력
		System.out.println("goodLike.GoodLikeDAO.selectOne 시작");
		LikeDTO data=null;
		// - 좋아요를 했는지 안했는지 여부 확인
		// - 로그인을 하고 나갔다가 들어왔을 때, 좋아요가 남아있어야 함
		Connection conn=JDBCUtil.connect(); // JDBC연결
		PreparedStatement pstmt=null;   // SQL 실행
		ResultSet rs=null;
		try {
			pstmt=conn.prepareStatement(SELECTONE);
			pstmt.setInt(1, goodLikeDTO.getGoodLike_board_num());
			pstmt.setString(2, goodLikeDTO.getGoodLike_member_id());

			rs = pstmt.executeQuery();
			if (rs.next()) {
				data = new LikeDTO();
				data.setGoodLike_board_num(rs.getInt("like_board_num"));
				data.setGoodLike_member_id(rs.getString("like_member_id"));

			}
		}
		catch (SQLException e) {
			System.err.println("goodLike.GoodLikeDAO.selectOne SQL 실패");
			return null;
		} finally {
			JDBCUtil.disconnect(pstmt,conn); // 종료
			System.out.println("goodLike.GoodLikeDAO.selectOne 성공");
		}
		return data;
	}
}
