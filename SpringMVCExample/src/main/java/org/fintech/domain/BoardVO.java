package org.fintech.domain;

import java.util.Date;

import lombok.Data;

//tbl_board(게시물)테이블과 매핑처리를 하기위한 클래스
@Data
public class BoardVO {
	private Long bno;
	private String title;
	private String content;
	private String writer;
	private Date regdate;
	private Date updatedate;

	//댓글수 추가 03.21
	private int replyCnt;
}
