package org.fintech.service;

import java.util.List;

import org.fintech.domain.BoardVO;
import org.fintech.domain.Criteria;
import org.fintech.mapper.BoardMapper;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

//해당 클래스가 서비스 기능을 수행한다는 어노테이션
@Service
@Log4j
//모든 매개변수를 이용하는 생성자를 생성
@AllArgsConstructor
public class BoardServiceImpl implements BoardService {
	
	private BoardMapper mapper;
	
	@Override
	public void register(BoardVO board) {
		log.info("register 실행" + board);
		
		mapper.insertSelectKey(board);
	}

	//특정 게시물 번호에 대한 상세보기
	@Override
	public BoardVO get(Long bno) {
		log.info("get() 실행");
		
		return mapper.read(bno);
	}

	//특정 게시물 수정
	@Override
	public boolean modify(BoardVO board) {
		log.info("modify() 실행");
		
		return mapper.update(board) == 1;
	}

	//특정 게시물 삭제
	@Override
	public boolean remove(Long bno) {
		log.info("remove() 실행");
		
		return mapper.delete(bno) == 1;
	}

//	@Override
//	public List<BoardVO> getList() {
//		log.info("getList() 실행");
//		
//		return mapper.getList();
//	}

	@Override
	public List<BoardVO> getList(Criteria cri) {
		log.info("get List with criteria: "+cri);
		return mapper.getListWithPaging(cri);
	}

	@Override
	public int getTotal(Criteria cri) {
		log.info("get total count");
		//BoardMapper.xml에 있는 getTotalCount를 실행
		return mapper.getTotalCount(cri);
	}
	
	
}





