package org.fintech.controller;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@Log4j
//Controller및 웹 환경에 사용되는 객체들을 자동으로 Bean등록 처리
//를 해주는 어노테이션
@WebAppConfiguration
@ContextConfiguration(
		{"file:src/main/webapp/WEB-INF/spring/root-context.xml",
  		 "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"}
		)
public class BoardControllerTests {

	@Setter(onMethod_ = @Autowired)
	private WebApplicationContext ctx;
	
	//Web application을 tomcat server를 이용하여 주소창에서
	//url을 처리하는게 아니라 프로그램에서 url처리를 할 수 있도록
	//하는 객체
	private MockMvc mockMvc;
	
	//MockMvc 사용을 위한 준비 선언
	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
	}
	
	//게시물 리스트
	@Ignore
	public void testList() throws Exception {
		//perform? 결과로 ResultActions 객체를 리턴받는다.
		log.info(
				/* get:전송방식을 표시
 				   get("/board/list") : 실행하려는 URL 표시	
			     */
				mockMvc.perform(MockMvcRequestBuilders.get("/board/list"))
					   .andReturn() //테스트한 결과를 객체형식으로 받는 경우 선언
    				   //요청을 전송하는 view값과 Model 객체에 저장된 값을 리턴
					   .getModelAndView()
					   //리턴되는 값을 Map형태로 받는다.
					   .getModelMap());		
	}
	
	//p216 신규 게시물 등록
	@Ignore
	public void testRegister() throws Exception {
		
		String resultPage =
				 mockMvc.perform(MockMvcRequestBuilders.post("/board/register")
						 		.param("title","MockMvc사용")
						 		.param("content","가상서버이용")
						 		.param("writer","신사임당"))
				        .andReturn()
				        .getModelAndView()
				        .getViewName();
		 
		log.info("resultPage:" + resultPage);
				        
	}
	
	//p218
	@Ignore
	public void testGet() throws Exception {
		log.info(mockMvc.perform(MockMvcRequestBuilders
						.get("/board/get").param("bno","7"))
						.andReturn()
						.getModelAndView()
						.getModelMap());
						
	}
	
	@Ignore
	public void testModify() throws Exception {
		String resultPage = 
				mockMvc.perform(MockMvcRequestBuilders
						.post("/board/modify")
							.param("bno","8")
							.param("title","수정된테스트 제목")
							.param("content","수정된 테스트 내용")
							.param("writer","홍길동"))
						.andReturn()
						.getModelAndView()
						.getViewName();
		
		log.info("resultPage:" + resultPage);
				
	}
	
	@Ignore
	public void testRemove()throws Exception{
		String resultPage=mockMvc.perform(MockMvcRequestBuilders.post("/board/remove").param("bno", "3")).andReturn().getModelAndView().getViewName();
		
		log.info(resultPage);
	}
	
	@Test
	public void testListPaging() throws Exception{
		log.info(mockMvc.perform(
				MockMvcRequestBuilders.get("/board/list")
				.param("pageNum", "2")
				.param("amount", "50"))
				.andReturn().getModelAndView().getModelMap());
	}
	
			
}




