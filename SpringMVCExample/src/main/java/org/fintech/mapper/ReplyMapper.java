package org.fintech.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.fintech.domain.Criteria;
import org.fintech.domain.ReplyVO;

public interface ReplyMapper {
	//신규 댓글 추가
	public int insert(ReplyVO vo);
	
	//특정 댓글 상세보기
	public ReplyVO read(long rno);
	
	//특정 댓글 삭제처리
	public int delete(long rno);
	
	//특정 댓글 수정
	public int update(ReplyVO reply);
	
	//댓글 페이징 처리
	public List<ReplyVO> getListWithPaging(
			@Param("cri") Criteria cri,
			@Param("bno") Long bno);			
	
	//댓글 총 건수 03.18
	public int getCountByBno(Long bno);
}
