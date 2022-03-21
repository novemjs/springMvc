package org.fintech.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.fintech.domain.BoardVO;
import org.fintech.domain.Criteria;

public interface BoardMapper {
	//게시물 테이블에서 모든 자료를 가져오는 어노테이션 선언
	public List<BoardVO> getList();
	
	//게시물 테이블에 신규 게시물 등록
	public void insert(BoardVO board);
	
	//게시물 테이블에 신규 게시물 등록을 하되 게시물번호를 미리
	//알수 있도록 처리
	public void insertSelectKey(BoardVO board);
	
	//특정 게시물 번호의 상세 정보
	public BoardVO read(Long bno);
	
	//특정 게시물 번호 내역 삭제
	public int delete(Long bno);
	
	//특정 게시물 수정
	public int update(BoardVO board);
	
	//페이징 처리 03.14
	public List<BoardVO> getListWithPaging(Criteria cri);

	//03.15 게시물 총 건수
	public int getTotalCount(Criteria cri);
	
	//03.21 댓글수 처리
	public void updateReplyCnt(@Param("bno") Long bno,@Param("amount") int amount);
	
}




