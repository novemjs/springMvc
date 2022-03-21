package org.fintech.controller;

import org.fintech.domain.BoardVO;
import org.fintech.domain.Criteria;
import org.fintech.domain.PageDTO;
import org.fintech.service.BoardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller
@Log4j
@RequestMapping("/board/*")
//모든 필드 값을 매개변수로 받는 
//생성자를 생성하는 어노테이션
@AllArgsConstructor
public class BoardController {

	private BoardService service;
	
	@GetMapping("/list")
	public void list(Criteria cri,Model model) {
		log.info("list:"+cri);
		
		//게시물 내역을 리턴받아 list 속성에 대입 
		model.addAttribute("list",service.getList(cri));
		//model.addAttribute("pageMaker",new PageDTO(cri,123));
		
		int total=service.getTotal(cri);
		log.info("total: "+total);
		model.addAttribute("pageMaker",new PageDTO(cri,total));
	}
	
	
	//p239
	@GetMapping("/register")
	public void register() {
		
	}
	
	//p216
	//신규 게시물 등록
	//RedirectAttributes?
	//모든 flashAttributes를 redirect하기전에 session에 복사해두고
	//redirect한 후에 session에 있는 flashAttributes를 Model로
	//이동처리 한다.
	@PostMapping("/register")
	public String register(BoardVO board,
			               RedirectAttributes rttr) {
		log.info("register() 실행");
		
		//신규 게시물을 추가
		service.register(board);
		
		//신규 게시물 처리를 한후에 방금전 추가한 게시물번호를
		//일회성 속성으로 지정하여 View에 전달
		rttr.addFlashAttribute("result",board.getBno());
		
		return "redirect:/board/list";
	}
	
	//특정 게시물 번호에 대한 상세보기 처리
	@GetMapping({"/get","/modify"})
	public void get(@RequestParam("bno") Long bno,@ModelAttribute("cri") Criteria cri,Model model) {
			      
		log.info("/get or modify");
		
		//특정 게시물 번호에 대한 내역을 리턴받아서 board
		//속성에 대입
		model.addAttribute("board",service.get(bno));
	}
	
	//특정 게시물 수정 처리
	@PostMapping("/modify")
	public String modify(BoardVO board,
						 RedirectAttributes rttr,@ModelAttribute("cri") Criteria cri) {
		log.info("modify() 실행");
		
		if(service.modify(board)) {
			
			//수정 처리후에 success라는 문자열을 사용하기 위해
			//일회성 속성인 result를 선언
			rttr.addFlashAttribute("result","success");
		}
		//03.15수정후 리스트로 이동시 현재 페이지로 이동
		//rttr.addAttribute("pageNum",cri.getPageNum());
		//rttr.addAttribute("amount",cri.getAmount());
		
		//검색조건 &검색 단어 유지
		//rttr.addAttribute("type",cri.getType());
		//rttr.addAttribute("keyword",cri.getKeyword());
		
		//게시물 리스트로 이동
		return "redirect:/board/list"+cri.getListLink();
	}
	
	//특정 게시물 삭제 처리
	@PostMapping("/remove")
	public String remove(@RequestParam("bno") Long bno,@ModelAttribute("cri") Criteria cri,RedirectAttributes rttr) {
		log.info("remove "+bno);
		//특정 게시물에 대한 삭제처리
		if(service.remove(bno)) {
			rttr.addFlashAttribute("result","success");
		}
		//03.15 수정삭제페이지에서 리스트 이동 수정
		//rttr.addAttribute("pageNum",cri.getPageNum());
		//rttr.addAttribute("amount",cri.getAmount());
		
		//검색조건 &검색 단어 유지
		//rttr.addAttribute("type",cri.getType());
		//rttr.addAttribute("keyword",cri.getKeyword());
		
		//삭제처리후 게시물 리스트로 이동
		return "redirect:/board/list"+cri.getListLink();
	}
}




