package org.fintech.service;

import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;

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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class BoardServiceTests {

	@Setter(onMethod_ = @Autowired)
	private BoardService service;
	
	@Ignore
	public void testExist() {
		log.info(service);
		//해당클래스가 빈등록 되어 있으면 true
		assertNotNull(service);
	}
	
	//p205
	@Ignore
	public void testRegister() {
		
		BoardVO board = new BoardVO();
		
		board.setTitle("스프링교육");
		board.setContent("MyBatis를 이용한 게시판");
		board.setWriter("홍길동");
		
		service.register(board);
		
		log.info("생성된 게시물 번호:" + board.getBno());
		
	}
	
	//p206
	@Test
	public void testGetList() {
		service.getList(new Criteria(2,10)).forEach(board -> log.info(board));
	}
	
	//p207
	@Ignore
	public void testGet() {
		log.info(service.get(7L));
	}
	
	//p209
	@Ignore
	public void testDelete() {
		log.info("삭제결과:" + service.remove(2L));
	}
	
	@Ignore
	public void testUpdate() {
		BoardVO board = service.get(7L);
		
		if(board == null) {
			return;
		}
		
		board.setTitle("제목을 수정합니다.");
		log.info("수정결과:" + service.modify(board));
		
	}
	
}









