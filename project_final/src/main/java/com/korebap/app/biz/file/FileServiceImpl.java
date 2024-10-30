package com.korebap.app.biz.file;

import java.util.List;

//import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// 인터페이스인 BoardService의 구현체(실현체)
@Service("fileService")//어노테이션
//컨트롤러, Actioln, ServiceImpl 류에 사용
// 사용자가 호출(look up) 할 때 사용
public class FileServiceImpl implements FileService{
	@Autowired
	// 의존성 주입을 통해 다른 객체를 자동으로 연결해주는 어노테이션
	private FileDAO fileDAO;// 멤버변수

	@Override
	public List<FileDTO> selectAll(FileDTO fileDTO) {
		return this.fileDAO.selectAll(fileDTO);
	}

	@Override
	public FileDTO selectOne(FileDTO fileDTO) {
		return null;
	}

	@Override
	public boolean insert(FileDTO fileDTO) {
		return this.fileDAO.insert(fileDTO);
	}

	@Override
	public boolean update(FileDTO fileDTO) {
		return this.fileDAO.update(fileDTO);
	}

	@Override
	public boolean delete(FileDTO fileDTO) {
		return this.fileDAO.delete(fileDTO);
	}

	

}
