package org.fintech.controller;

import org.fintech.domain.Criteria;
import org.fintech.domain.ReplyPageDTO;
import org.fintech.domain.ReplyVO;
import org.fintech.service.ReplyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@RequestMapping("/replies")
//Rest방식의 컨트롤러 기능을 수행한다는 어노테이션
@RestController
@Log4j
//모든 필드를 매개변수로 하는 생성자를 자동으로 생성하는 어노테이션
@AllArgsConstructor
public class ReplyController {
	private ReplyService service;
	
	//http://localhost:8080/replies/new
	@PostMapping(value="/new",
			//consumes : 모든 클라이언트의 요청중에 json형태로 요청이 들어오는 부분만 처리해준다
			consumes="application/json",
			//produces : 서버가 클라이언트에게 응답처리시 데이터를 처리하는 선언
			produces= { MediaType.TEXT_PLAIN_VALUE })
	//ResponseEntity?
	//클라이언트에게 데이터 응답처리시 Http 상태 코드도 함께 처리
	//@RequestBody?
	//http 요청 Body 부분을 자바 객체로 전달하는 어노테이션
	public ResponseEntity<String> create(@RequestBody ReplyVO vo){
		log.info("ReplyVO: "+vo);
		int insertCount=service.register(vo);
		
		log.info("댓글 insert count: "+insertCount);
		
		return insertCount==1 
			? new ResponseEntity<>("success",HttpStatus.OK)//200번 
			: new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);//500번
	}
	//p395
	//특정 게시물에 대한 댓글 상세보기 처리
	//http://localhost:8080/replies/pages/bno번호/1
	@GetMapping(value = "/pages/{bno}/{page}", produces = { MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseEntity<ReplyPageDTO> getListPage(@PathVariable("page") int page, @PathVariable("bno") Long bno) {
		log.info("getList()");

		Criteria cri = new Criteria(page, 10);
		log.info(cri);

		return new ResponseEntity<>(
				service.getListPage(cri, bno),HttpStatus.OK);
	}
	
	//p397
	//특정 댓글 상세보기 처리
	@GetMapping(value="/{rno}",
			produces= {
					MediaType.APPLICATION_XML_VALUE,
					MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseEntity<ReplyVO> get(@PathVariable("rno") Long rno){
		log.info("get:"+rno);
		
		return new ResponseEntity<>(service.get(rno),HttpStatus.OK);
	}
	
	//특정 댓글 삭제 처리
	@DeleteMapping(value="/{rno}",
			produces= {
					MediaType.APPLICATION_XML_VALUE,
					MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseEntity<String> remove(@PathVariable("rno") Long rno) {
		log.info("remove: "+rno);
		
		//특정 댓글 삭제 처리후 정상적으로 처리 되었으면 200 실패햇으면 500 처리
		return service.remove(rno)==1
				? new ResponseEntity<>("success",HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	//특정 댓글 수정 처리
	@RequestMapping(method= { RequestMethod.PUT, RequestMethod.PATCH },
			value="/{rno}",
			//클라이언트의 요청중 json형태만 처리해준다
			consumes="application/json",
			//서버가 응답처리시 응답 형태 지정
			produces= { MediaType.TEXT_PLAIN_VALUE })
	public ResponseEntity<String> modify(
			//클라이언트에서 전송되는 json데이터를 객체로 변환시켜주는 annotation
			@RequestBody ReplyVO vo,
			//url에 있는 매개변수 값을 가져오는 어노테이션
			@PathVariable("rno") Long rno) {
		vo.setRno(rno);
		log.info("rno: "+rno);
		log.info("modify: "+vo);
		
		return service.modify(vo)==1
				? new ResponseEntity<>("success",HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
