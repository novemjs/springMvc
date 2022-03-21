package org.fintech.mapper;

import java.util.List;

import org.fintech.domain.BoardVO;
import org.fintech.domain.Criteria;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

//Spring 테스트를 하기 위한 선언
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class BoardMapperTests {

	//BoardMapperTests 클래스가 컴파일 시점에 
	//BoardMapper 인터페이스를 Setter 어노테이션에 의해 자동주입
	@Setter(onMethod_ = @Autowired)
	private BoardMapper mapper;
	
	@Ignore
	public void testGetList() {
		mapper.getList().forEach(board -> log.info(board));
	}
	
	@Ignore
	public void testInsert() {
		BoardVO board = new BoardVO();
		
		board.setTitle("주문배송");
		board.setContent("빨리 보내주세요");
		board.setWriter("강감찬");
		
		mapper.insert(board);
	}
	
	//p192
	@Ignore
	public void testInsertSelectKey() {
		
		BoardVO board = new BoardVO();
		
		board.setTitle("반품요청");
		board.setContent("상품 파손");
		board.setWriter("신사임당");
		
		mapper.insertSelectKey(board);
		
		log.info(board);
	}
	
	//p194
	@Ignore
	public void testRead() {
		BoardVO board = mapper.read(5L);
		
		log.info(board);
	}
	
	//p195
	@Ignore
	public void testDelete() {
		log.info("삭제건수:" + mapper.delete(4L));
	}
	
	//p197
	@Ignore
	public void testUpdate() {
		BoardVO board = new BoardVO();
		
		board.setBno(5L);
		board.setTitle("수정[주문배송]");
		board.setContent("수정[빠른배송요망]");
		board.setWriter("수정[신사임당]");
		
		int count = mapper.update(board);
		log.info("수정건수:" + count);
	}
	
	@Ignore
	public void testPaging() {
		Criteria cri=new Criteria();
		
		//조회된 게시물 리스트의 3페이지에 표시될 행을 출력
		cri.setPageNum(3);
		cri.setAmount(10);
		List<BoardVO> list=mapper.getListWithPaging(cri);
		list.forEach(board->log.info(board));
	}
	
	@Test
	public void testSerach() {
		Criteria cri=new Criteria();
		cri.setKeyword("newbie");
		cri.setType("W");
		
		List<BoardVO> list=mapper.getListWithPaging(cri);
		list.forEach(board->log.info(board));
	}
	
	
}





