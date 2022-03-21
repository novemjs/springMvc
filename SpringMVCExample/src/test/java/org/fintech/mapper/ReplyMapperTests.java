package org.fintech.mapper;

import java.util.List;
import java.util.stream.IntStream;

import org.fintech.domain.Criteria;
import org.fintech.domain.ReplyVO;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class ReplyMapperTests {
	
	private Long[] bnoArr= { 188L,176L,184L,100L,44L };
	
	@Setter(onMethod_=@Autowired)
	private ReplyMapper mapper;
	
	@Ignore
	public void testMapper() {
		log.info(mapper);
	}
	
	//p383
	@Ignore
	public void testCreate() {
		//1부터 10까지 10번 반복
		IntStream.rangeClosed(1, 10).forEach(i->{
			ReplyVO vo=new ReplyVO();
			
			//배열에 해당하는 값을 가져와 게시물 번호로 지정
			vo.setBno(bnoArr[i%5]);
			vo.setReply("댓글 테스트"+i);
			vo.setReplyer("replyer"+i);
			
			mapper.insert(vo);
		});
	}
	
	//p385
	@Ignore
	public void testRead() {
		Long targetRno=5L;
		
		ReplyVO vo=mapper.read(targetRno);
		log.info(vo);
	}
	//p385
	@Ignore
	public void testDelete() {
		Long targetRno=1L;
		int deleteCount=mapper.delete(targetRno);
		System.out.println("삭제건수:"+deleteCount);
	}
	
	@Ignore
	public void testUpdate() {
		Long targetRno=10L;
		ReplyVO vo=mapper.read(targetRno);
		vo.setReply("Update Reply");
		int count=mapper.update(vo);
		log.info("업데이트 카운트: "+count);
	}
	
	//댓글 페이징 처리
	@Ignore
	public void testList() {
		Criteria cri=new Criteria();
		
		List<ReplyVO> replies=mapper.getListWithPaging(cri, bnoArr[0]);
		replies.forEach(reply->log.info(reply));
	}
	
	@Test
	public void testList2() {
		//페이징 처리를 하기 위해 페이지번호 & 페이지당 행수 지정
		Criteria cri=new Criteria(1,10);
		
		List<ReplyVO> replies=mapper.getListWithPaging(cri, 203L);
		//댓글내역을 반복문을 통해 콘솔창에 출력
		replies.forEach(reply->log.info(reply));
	}
}
