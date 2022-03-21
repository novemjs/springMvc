package org.fintech.service;

import java.util.List;

import org.fintech.domain.Criteria;
import org.fintech.domain.ReplyPageDTO;
import org.fintech.domain.ReplyVO;

public interface ReplyService {
	//신규 댓글 등록처리
	public int register(ReplyVO vo);
	
	//특정 댓글 상세보기 처리
	public ReplyVO get(Long rno);
	
	//특정 댓글 수정 처리
	public int modify(ReplyVO vo);
	
	//특정 댓글 삭제 처리
	public int remove(Long rno);
	
	//댓글 리스트 처리
	public List<ReplyVO> getList(Criteria cri,Long bno);
	
	//페이징처리를 포함한 댓글 리스트 처리 03.18
	public ReplyPageDTO getListPage(Criteria cri,Long bno);
}
