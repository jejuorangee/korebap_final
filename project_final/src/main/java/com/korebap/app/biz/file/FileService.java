package com.korebap.app.biz.file;

import java.util.List;

public interface FileService {
	List<FileDTO> selectAll(FileDTO fileDTO);
	FileDTO selectOne(FileDTO fileDTO);
	boolean insert(FileDTO fileDTO);
	boolean update(FileDTO fileDTO);
	boolean delete(FileDTO fileDTO);
}
// Service의 메서드가 DAO의 메서드 시그니쳐와 동일해야함!!!!!