package org.fintech.domain;

import lombok.Data;

@Data
public class PageDTO {
	private int startPage;//화면 하단 시작페이지
	private int endPage;//화면 하단 종료페이지
	private boolean prev,next;//prev:이전페이지 체크,next:다음페이지 체크
	
	private int total;//전체 게시물 수
	private Criteria cri;//페이징처리를 위한 클래스
	
	public PageDTO(Criteria cri,int total) {
		this.cri=cri;
		this.total=total;
		
		//소수점이하 올림
		this.endPage=(int)(Math.ceil(cri.getPageNum()/10.0))*10;
		this.startPage=this.endPage-9;
		
		//실제 계산에 의한 페이지수
		int realEnd=(int)(Math.ceil((total*1.0) / cri.getAmount()));
		
		//하단 마지막 페이지처리
		if(realEnd<this.endPage) {
			this.endPage=realEnd;
		}
		
		//2페이지가 존재할때 true
		this.prev=this.startPage>1;
		//마지막 페이지보다 작을때 true
		this.next=this.endPage<realEnd;
	}
}
