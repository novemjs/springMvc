<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@include file="../includes/header.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div class="row">
		<div class="col-lg-12">
			<h1 class="page-header">게시판 상세보기</h1>
		</div>
	</div>
	
	<div class="row">
		<div class="col-lg-12">
			<div class="panel panel-success">
				<div class="panel-heading">
					게시판 상세보기
				</div>
				<div class="panel-body">
						<div class="form-group">
							<label>번호</label>
							<input class="form-control" 
							       name="bno"
							       value='<c:out value="${board.bno}"/>'
							       readonly="readonly">
						</div>					      
					      
						<div class="form-group">
							<label>제목</label>
							<input class="form-control" 
							       name="title"
							       value='<c:out value="${board.title}"/>'
							       readonly="readonly">
						</div>
						<div class="form-group">
							<label>내용</label>
							<textarea class="form-control" 
									  rows="3" 
									  name="content"
									  readonly="readonly"><c:out value="${board.content}"/></textarea>
									  
						</div>
						<div class="form-group">
							<label>작성자</label>
							<input class="form-control" 
								   name="writer"
								   value="<c:out value="${board.writer}"/>">
						</div>
						<button data-oper="modify" 
								class="btn btn-primary">
							게시판 수정
						</button>
						<button data-oper="list" 
								class="btn btn-warning">
							게시판 리스트
						</button>
						
						<!-- p317 -->
						<form id="operForm" 
							  action="/board/modify"
							  method="get">
							<input type="hidden" id="bno" name="bno" value='<c:out value="${board.bno}"/>'>
							<input type="hidden" name="pageNum" value='<c:out value="${cri.pageNum}"/>'>
							<input type="hidden" name="amount" value='<c:out value="${cri.amount}"/>'>
							<!-- 검색 조건 & 검색 단어 화면에 유지하기 위해 hidden으로 전송 -->
							<input type="hidden" name="keyword" value='<c:out value="${cri.keyword}"/>'>
							<input type="hidden" name="type" value='<c:out value="${cri.type}"/>'>
						</form>
					</div>
				</div>
			</div>
		</div>
	
	<!-- p414 댓글 리스트 추가 시작 -->
	<div class="row">
		<div class="col-lg-12">
			<div class="panel panel-default">
				<div class="panel-heading">
					<i class="fa fa-comments fa-fw"></i>댓글
					<button id="addReplyBtn"
							class="btn btn-primary btn-xs pull-right">
						댓글입력	
					</button>
				</div>
				<div class="panel-body">
					<ul class="chat">
						<li class="left clearfix" data-rno='12'>
							<div class="header">
								<strong class="primary-font">
									user00
								</strong>
								<small class="pull-right text-muted">
									2022-03-17 13:59
								</small>
							</div>
							<p>Goog Job!</p>
						</li>
					</ul>
				</div>
			</div>
		</div>
	</div>	
	<!-- p414 댓글 리스트 추가 종료 -->
	<!-- 03.18 p439 -->
		<div class="panel-footer">
		
		</div>
										
	<!-- p420 댓글입력을 모달창을 이용하여 처리 시작 -->
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
						&times;
					</button>
					<h4 class="modal-title" id="myModalLabel">
						댓글 모달창
					</h4>
				</div>
				<div class="modal-body">
					<div class="form-group">
						<label>댓글내용</label>
						<input class="form-control" name="reply" value="New Reply!!!">
					</div>
					<div class="form-group">
						<label>댓글작성자</label>
						<input class="form-control" name="replyer" value="replyer">
					</div>
					<div class="form-group">
						<label>댓글작성일자</label>
						<input class="form-control" name="replyDate" value="">
					</div>
				</div>
				<div class="modal-footer">
					<button id="modalRegisterBtn" type="button" class="btn btn-success">
						등록
					</button>
					<button id="modalModBtn" type="button" class="btn btn-warning">
						수정
					</button>
					<button id="modalRemoveBtn" type="button" class="btn btn-danger">
						삭제
					</button>
					<button id="modalCloseBtn" type="button" class="btn btn-primary" data-dismiss="modal">
						Close
					</button>
				</div>
			</div>
		</div>	
	</div>
	<!-- p420 댓글입력을 모달창을 이용하여 처리 종료 -->
		
	
<%@include file="../includes/footer.jsp" %>    	
</body>
</html>

<!-- 03.17 공통기능 수행 reply.js 추가 -->
<script src="/resources/js/reply.js"></script>

<!-- p265 코딩 -->
<script>
	$(document).ready(function(){
		
		//p404
		//get.jsp화면에서 상세보기 게시물번호를 변수에 대입
		var bnoValue = '<c:out value="${board.bno}"/>';
		
		var replyUL = $(".chat");
		
		showList(1);
		
		//특정 게시물번호에 대한 댓글 리스트를 출력하는 함수선언 03.17
		function showList(page){
			replyService.getList({bno:bnoValue,page:page||1},function(replyCnt,list){
				//p438
				if(page==-1){
					//댓글을 표시하기 위한 페이지수 계산
					pageNum=Math.ceil(replyCnt/10.0);
					showList(pageNum);
					return;
				}
				
				var str = "";
				//댓글이 존재하지 않는 경우 처리
				if(list == null || list.length == 0){
					replyUL.html("");
					
					return;
				}
				
				//특정 게시물번호에 대해 댓글이 존재하면 처리
				for(var i=0;i<list.length;i++){
					str += "<li class='left clearfix' data-rno='"+list[i].rno+"'>";
					str += "<div><div class='header'><strong class='primary-font'>" +
					       list[i].replyer + "</strong>";
					str += "<small class='pull-right text-muted'>" + list[i].replyDate + "</small></div>";
					str += "<p>" + list[i].reply + "</p></div></li>";
				}
				
				//.html()?
				//선택한 요소 안의 내용을 가져오는 함수		
				replyUL.html(str);
				
				showReplyPage(replyCnt);
				
			});
		}
		
		
		
/* 		//신규 댓글 처리
		replyService.add(
				//JSON형태로 전송
				{
					reply:"JS Test",
					replyer:"tester",
					bno:bnoValue
				},
				function(result){
					alert("result:" + result);
				}
		);
		
		//P407
		//댓글 리스트
		replyService.getList(
			{bno:bnoValue,page:1},
			function(list){
				for(var i=0;i<list.length;i++){
					console.log(list[i]);
				}
		});
		
		//특정 댓글 삭제 처리
		replyService.remove(
				14,//삭제하려는 댓글번호
				function(count){
					if(count === "success"){
						alert("removed");
					}
				},
				function(err){
					alert("delete error!!!!");
				}
		);
		
		//p411
		replyService.update(
			{
				rno:13,//수정하려는 댓글번호
				bno:bnoValue,
				reply: "Ajax를 이용한 댓글수정"
			},
			
			function(result){
				alert("수정 완료!!");
			}
		); */
		
		//p413
		replyService.get(13,function(data){
			console.log(data);
		});
		
		
		//operForm 폼의 모든 값을 변수에 대입
		var operForm = $("#operForm");
		
		//수정버튼이 클릭되면 처리
		$("button[data-oper='modify']").on("click",function(e){
			operForm.attr("action","/board/modify").submit();
		});
		
		//리스트 버튼을 클릭하면 처리
		$("button[data-oper='list']").on("click",function(e){
			//게시물 리스트로 이동시 게시물번호값은 완전히 삭제
			operForm.find("#bno").remove();
			operForm.attr("action","/board/list").submit();
		});
		
		//p422 모달창 버튼 처리
		//모달창 모든 요소값을 modal변수에 대입
		var modal = $(".modal");
		
		//모달창의 input태그중에서 댓글내용을 변수에 대입
		var modalInputReply = modal.find("input[name='reply']");
		//모달창의 input태그중에서 댓글작성자를 변수에 대입
		var modalInputReplyer = modal.find("input[name='replyer']");
		//모달창의 input태그중에서 댓글작성일자를 변수에 대입
		var modalInputReplyDate = modal.find("input[name='replyDate']");
		
		//댓글 등록 버튼 요소 값을 변수에 대입
		var modalRegisterBtn = $("#modalRegisterBtn");
		//댓글 수정 버튼 요소 값을 변수에 대입
		var modalModBtn = $("#modalModBtn");
		//댓글 삭제 버튼 요소 값을 변수에 대입
		var modalRemoveBtn = $("#modalRemoveBtn");
		
		//댓글 입력 버튼을 클릭하면 처리
		$("#addReplyBtn").on("click",function(e){
			//댓글 입력 모달창의 input태그를 clear
			modal.find("input").val("");
			//댓글등록일자 div를 hide
			modalInputReplyDate.closest("div").hide();
			
			//Close버튼을 제외한 나머지 버튼을 hide
			modal.find("button[id != 'modalCloseBtn']").hide();
			
			//등록버튼을 show
			modalRegisterBtn.show();
			
			//모달창을 show
			$(".modal").modal("show");
			
		});
		
		//p423
		//댓글 모달창에서 입력한 내용을 reply.js의 add 함수 Call
		modalRegisterBtn.on("click",function(){
			
			var reply = {
					reply:modalInputReply.val(),//댓글내용
					replyer:modalInputReplyer.val(),//댓글작성자
					bno:bnoValue//댓글을 입력하기 위한 게시물번호
			};
			
			replyService.add(reply,function(result){
				alert(result);
				//댓글 등록 처리후 화면 클리어 & 모달창 닫기
				modal.find("input").val("");
				modal.modal("hide");
				
				//03.18 댓글 입력후 화면 갱신(1페이지를 조회)
				showList(1);
			});
		});
		
		//p425 특정 댓글 클릭시 이벤트 처리 03.18
		//댓글 리스트에서 클릭을하면 해당 댓글번호를 콘솔창에 출력
		$(".chat").on("click","li",function(e){
			var rno=$(this).data("rno");
			
			//클릭한 댓글번호에 대한 내역을 가져와서 댓글 modal창에 출력
			replyService.get(rno,function(reply){
				modalInputReply.val(reply.reply);
				modalInputReplyer.val(reply.replyer);
				modalInputReplyDate.val(reply.replyDate).attr("readonly","readonly");
				//댓글번호를 rno속성에 대입
				modal.data("rno",reply.rno);
				
				modal.find("button[id!='modalCloseBtn']").hide();
				modalModBtn.show();
				modalRemoveBtn.show();
				
				$(".modal").modal("show");
			
			});
		});
		
		//p427 특정 댓글 수정버튼 처리 03.18
		modalModBtn.on("click",function(e){
			//rno:수정댓글번호, reply:수정 댓글 내용
			var reply={ rno:modal.data("rno"), reply:modalInputReply.val() };
			
			//reply.js
			replyService.update(reply,function(result){
				alert(result);
				modal.modal("hide");
				showList(pageNum);//화면갱신
			});
		});
			
		modalRemoveBtn.on("click",function(e){
			var rno=modal.data("rno");
			
			replyService.remove(rno,function(result){
				alert(result);
				modal.modal("hide");
				showList(pageNum);
			});
		});
		
		//p440 페이징처리 화면 처리
		var pageNum=1;
		//페이지 처리를 하는 영역의 값을 변수에 대입
		var replyPageFooter=$(".panel-footer");
		
		function showReplyPage(replyCnt){
			//화면 하단의 마지막페이지
			var endNum=Math.ceil(pageNum/10.0)*10;
			//화면 하단의 시작페이지
			var startNum=endNum-9;
			
			var prev=startNum !=1;//이전페이지 존재여부 체크
			var next=false;//다음페이지 초기값 x
			
			if(endNum*10>=replyCnt){
				//총댓글건수 / 페이지당 행수해서 마지막 페이지를 다시 계산
				endNum=Math.ceil(replyCnt/10.0);
			}
			if(endNum*10<replyCnt){
				//다음 페이지가 존재하므로 true
				next=true;
			}
			var str="<ul class='pagination pull-right'>";
			
			//이전 페이지 버튼 처리
			if(prev){
				str+="<li class='page-item'><a class='page-link' href='"+(startNum-1)+"'>이전</a></li>";
			}
			//해당 페이지 하단에 페이지 번호 10개 출력
			for(var i=startNum;i<=endNum;i++){
				var active=pageNum==i ? "active" : "";
				str+="<li class='page-item "+active+"'><a class='page-link' href='"+i+"'>"+i+"</a></li>";
			}
			//다음 페이지 버튼 처리
			if(next){
				str+="<li class='page-item'><a class='page-link' href='"+(endNum+1)+"'>다음</a></li>";
			}
			
			str+="</ul></div>";
			console.log(str);
			replyPageFooter.html(str);
		}
		
		//p441 댓글 페이지번호 클릭시 해당 페이지로 이동 처리
		replyPageFooter.on("click","li a",function(e){
			e.preventDefault();
			
			//클릭한 페이지번호
			var targetPageNum=$(this).attr("href");
			console.log("targetPageNum: "+targetPageNum);
			alert("remove success");
			pageNum=targetPageNum;
			//해당 페이지의 댓글 리스트를 출력
			showList(pageNum);
		});
	});
</script>












