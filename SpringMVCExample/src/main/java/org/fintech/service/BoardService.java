package org.fintech.service;

import java.util.List;

import org.fintech.domain.BoardVO;
import org.fintech.domain.Criteria;

public interface BoardService {

	//신규 게시물 등록 
	public void register(BoardVO board);
	
	//특정 게시물 상세보기
	public BoardVO get(Long bno);
		
	//특정 게시물 수정
	public boolean modify(BoardVO board);
	
	//특정 게시물 삭제
	public boolean remove(Long bno);
	
	//게시물 리스트
	//public List<BoardVO> getList();

	public List<BoardVO> getList(Criteria cri);
	
	public int getTotal(Criteria cri);
}
