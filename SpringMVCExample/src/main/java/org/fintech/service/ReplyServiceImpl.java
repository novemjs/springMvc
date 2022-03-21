package org.fintech.service;

import java.util.List;

import org.fintech.domain.Criteria;
import org.fintech.domain.ReplyPageDTO;
import org.fintech.domain.ReplyVO;
import org.fintech.mapper.BoardMapper;
import org.fintech.mapper.ReplyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class ReplyServiceImpl implements ReplyService{
	@Setter(onMethod_=@Autowired)
	private ReplyMapper mapper;
	
	//댓글수 처리를 위해 자동주입 03.21
	@Setter(onMethod_=@Autowired)
	private BoardMapper boardMapper;
	
	//특정 댓글 등록
	@Override
	public int register(ReplyVO vo) {
		log.info("등록....."+vo);
		return mapper.insert(vo);
	}
	//특정 댓글 상세보기
	@Override
	public ReplyVO get(Long rno) { 
		log.info("get...."+rno); 
		return mapper.read(rno);
	}
	//특정 댓글 수정처리
	@Override
	public int modify(ReplyVO vo) {
		log.info("modify....."+vo);
		return mapper.update(vo);
	}
	//특정 댓글 삭제 처리
	@Override 
	public int remove(Long rno) {
		log.info("remove..."+rno);
		return mapper.delete(rno);
	}
	//댓글 리스트 처리
	@Override
	public List<ReplyVO> getList(Criteria cri, Long bno) {
		log.info("댓글목록 가져오기"+bno);
		return mapper.getListWithPaging(cri, bno);
	}
	
	//페이징 처리 & 댓글 리스트 처리 03.18
	@Override
	public ReplyPageDTO getListPage(Criteria cri, Long bno) {
		return new ReplyPageDTO(
				mapper.getCountByBno(bno),
				mapper.getListWithPaging(cri, bno));
	}
}
