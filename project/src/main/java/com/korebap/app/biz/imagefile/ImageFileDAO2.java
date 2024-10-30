package com.korebap.app.biz.imagefile;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class ImageFileDAO2 { 
	// 게시판 사진 저장
	private final String BOARD_FILE_INSERT = "INSERT INTO IMAGEFILE (FILE_DIR, BOARD_ITEM_NUM) VALUES (?, ?);";
	// 상품 사진 저장
	private final String PRODUCT_FILE_INSERT = "INSERT INTO IMAGEFILE (FILE_DIR, PRODUCT_ITEM_NUM) VALUES (?, ?);";
	// 사진 변경
	private final String FILE_UPDATE = "UPDATE IMAGEFILE SET FILE_DIR=? WHERE FILE_NUM=?";
	// 사진 삭제
	private final String FILE_DELETE = "DELETE FROM IMAGEFILE WHERE FILE_NUM=?";
	// 게시판 사진 전체 출력
	private final String BOARD_FILE_SELECTALL = "SELECT FILE_NUM,FILE_DIR FROM IMAGEFILE WHERE BOARD_ITEM_NUM=? ORDER BY FILE_NUM";
	// 상품 사진 전체 출력
	private final String PRODUCT_FILE_SELECTALL = "SELECT FILE_NUM,FILE_DIR FROM IMAGEFILE WHERE PRODUCT_ITEM_NUM=? ORDER BY FILE_NUM";

	@Autowired
	private JdbcTemplate jdbcTemplate; // jdbc 연결을 위한 주입 

	// selectAll한 값을 list에 담아 반환한다. 
	public List<ImageFileDTO> selectAll(ImageFileDTO imageFileDTO) { // 사진 전체 출력
		System.out.println("====model.ImageFileDAO2.selectAll 시작");
		List<ImageFileDTO> datas = new ArrayList<ImageFileDTO>(); // 반환할 값을 생성해준다.
		System.out.println("====model.ImageFileDAO2.selectAll imageFileDTO.getFile_condition() : ["+imageFileDTO.getFile_condition()+"]");
		if (imageFileDTO.getFile_condition().equals("BOARD_FILE_SELECTALL")) { // 게시판 사진을 반환한다.
			System.out.println("====model.ImageFileDAO2.selectAll BOARD_FILE_SELECTALL 시작");
			Object[] args = {imageFileDTO.getFile_board_num()};// 게시판 글 번호
			// query메서드를 사용하여 값을 datas에 넣는다.
			datas = (List<ImageFileDTO>) jdbcTemplate.query(BOARD_FILE_SELECTALL, args, new ImageFileRowMapper_all()); 
		} 
		else if (imageFileDTO.getFile_condition().equals("PRODUCT_FILE_SELECTALL")) { // 상품 사진을 반환한다.
			System.out.println("====model.ImageFileDAO2.selectAll PRODUCT_FILE_SELECTALL 시작");
			Object[] args = {imageFileDTO.getFile_product_num()};// 상품 글 번호
			// query메서드를 사용하여 값을 datas에 넣는다.
			datas = (List<ImageFileDTO>) jdbcTemplate.query(PRODUCT_FILE_SELECTALL, args, new ImageFileRowMapper_all());
		}
		else {
			System.err.println("====model.ImageFileDAO2.selectAll 컨디션 에러");
		}
		System.out.println("====model.ImageFileDAO2.selectAll datas : "+datas);
		return datas;
	}

	// insert에 성공했는지 실패했는지 boolean 타입을 반환한다.
	public boolean insert(ImageFileDTO imageFileDTO) { // 생성
		System.out.println("====model.ImageFileDAO2.insert 시작");
		int result=0; // 초기값을 세팅한다.
		System.out.println("====model.ImageFileDAO2.insert imageFileDTO.getFile_condition() : [" + imageFileDTO.getFile_condition()+"]");
		if (imageFileDTO.getFile_condition().equals("BOARD_FILE_INSERT")) { // 게시판 사진을 등록을 한다.
			System.out.println("====model.ImageFileDAO2.insert BOARD_FILE_INSERT 시작");
			// update 메서드를 사용하여 결과를 result에 담는다.
			result = jdbcTemplate.update(BOARD_FILE_INSERT, imageFileDTO.getFile_dir(),imageFileDTO.getFile_board_num());
		} 
		else if (imageFileDTO.getFile_condition().equals("PRODUCT_FILE_INSERT")) { // 상품 사진을 등록한다.
			System.out.println("====model.ImageFileDAO2.insert PRODUCT_FILE_INSERT 시작");
			// update 메서드를 사용하여 결과를 result에 담는다.
			result = jdbcTemplate.update(PRODUCT_FILE_INSERT, imageFileDTO.getFile_dir(),imageFileDTO.getFile_product_num());
		}
		else {
			System.err.println("====model.ImageFileDAO2.insert 컨디션 실패");
		}
		System.out.println("====model.ImageFileDAO.insert result : " + result);
		if (result <= 0) { // 만약 변경이 된 행 수가 0보다 작거나 같다면
			System.err.println("====model.ImageFileDAO2.insert 실패");
			return false; // 실패를 반환한다.
		}
		System.out.println("====model.ImageFileDAO2.insert 성공");
		return true; // !(result <= 0)라면 true를 반환한다.
	}

	// "UPDATE IMAGEFILE SET FILE_DIR=? WHERE FILE_NUM=?"
	public boolean update(ImageFileDTO imageFileDTO) { // 업데이트
		System.out.println("====model.ImageFileDAO2.update 시작");
		int result=0;// 초기값을 세팅한다.
		// update 메서드를 사용하여 결과를 result에 담는다.
		result = jdbcTemplate.update(FILE_UPDATE,imageFileDTO.getFile_dir(),imageFileDTO.getFile_num());
		System.out.println("====model.ImageFileDAO2.update result : " + result);
		if (result <= 0) { // 만약 변경된 행 수가 없다면
			System.err.println("====model.ImageFileDAO2.update 실패");
			return false;// 실패를 반환한다.
		}
		System.out.println("====model.ImageFileDAO2.update 성공");
		return true;// !(result <= 0)라면 true를 반환한다.
	}

	// "DELETE FROM IMAGEFILE WHERE FILE_NUM=?"
	public boolean delete(ImageFileDTO imageFileDTO) { // 삭제
		System.out.println("====model.ImageFileDAO2.delete 시작");
		int result=0;// 초기값을 세팅한다.
		// update 메서드를 사용하여 결과를 result에 담는다.
		result = jdbcTemplate.update(FILE_DELETE,imageFileDTO.getFile_num());
		System.out.println("====model.ImageFileDAO2.delete result : " + result);
		if (result <= 0) { // 만약 변경된 행 수가 없다면
			System.err.println("====model.ImageFileDAO2.delete 실패");
			return false;// 실패를 반환한다.
		}
		System.out.println("====model.ImageFileDAO2.delete 성공");
		return true;// !(result <= 0)라면 true를 반환한다.
	}

	// 기늠 미구현으로 null 반환
	public ImageFileDTO selectOne(ImageFileDTO imageFileDTO) {
		ImageFileDTO data = null;
		return data;
	}
}

// 데이터베이스 쿼리 결과를 객체로 매핑하는 기능을 한다.
class ImageFileRowMapper_all implements RowMapper<ImageFileDTO> {
	// 게시판 전체 출력
	@Override
	public ImageFileDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		ImageFileDTO data = new ImageFileDTO();
		data.setFile_num(rs.getInt("FILE_NUM")); // 파일 번호
		data.setFile_dir(rs.getString("FILE_DIR")); // 파일 경로
		return data;
	}
}