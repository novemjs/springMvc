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
					<form role="form" action="/board/modify" method="post">
						<!-- 03.15 현재페이지번호& 페이지당 행수 hidden처리 -->
						<input type="hidden" name="pageNum" value='<c:out value="${cri.pageNum }"/>'>
						<input type="hidden" name="amount" value='<c:out value="${cri.amount }"/>'>
						<input type="hidden" name='keyword' value='<c:out value="${cri.keyword}"/>'>
						<input type="hidden" name='type' value='<c:out value="${cri.type}"/>'>
						<!-- 끝 -->
						<div class="form-group">
							<label>bno</label>
							<input class="form-control" name="bno" value='<c:out value="${board.bno}"/>' readonly="readonly">
						</div>
						<div class="form-group">
							<label>제목</label>
							<input class="form-control" name="title" value='<c:out value="${board.title}"/>'>
						</div>
						<div class="form-group">
							<label>내용</label>
							<textarea class="form-control" rows="3" name="content"><c:out value="${board.content }"/></textarea>
						</div>
						<div class="form-group">
							<label>작성자</label>
							<input class="form-control" name="writer" value='<c:out value="${board.writer}"/>' readonly="readonly">
						</div>
						<div class="form-group">
							<input type="hidden" class="form-control" name="regDate" value='<fmt:formatDate pattern="yyyy/MM/dd" value="${board.regdate }"/>' readonly="readonly">
						</div>
						<div class="form-group">
							<input type="hidden" class="form-control" name="updateDate" value='<fmt:formatDate pattern="yyyy/MM/dd" value="${board.updatedate }"/>' readonly="readonly">
						</div>
						<!-- data-oper 속성을 이용하면 원하는 기능을 동작하도록 처리 -->
						<button type="submit" data-oper='modify' class="btn btn-default">
							수정
						</button>
						<button type="submit" data-oper='remove' class="btn btn-danger">
							삭제
						</button>
						<button type="submit" data-oper='list' class="btn btn-info">List</button>
					</form>
				</div>
			</div>
		</div>	
	</div>
	<%@include file="../includes/footer.jsp" %>
	<!-- p262 수정화면 버튼 처리 -->
	<script type="text/javascript">
		$(document).ready(function(){
			var formObj=$("form");
			
			$('button').on("click",function(e){
				//html 본래의 처리흐름대로 하지않게 하기위해 처리를 막는 선언(submit을 막는처리)
				e.preventDefault();
				var operation=$(this).data("oper");
				console.log(operation);
				
				if(operation==='remove'){//삭제버튼 클릭시
					formObj.attr("action","/board/remove");
				}else if(operation==='list'){//리스트버튼 클릭시
					//03.15 수정페이지에서 리스트버튼 클릭
					formObj.attr("action","/board/list").attr("method","get");
					//clone():선택한 요소를 복제
					var pageNumTag=$("input[name='pageNum']").clone();
					var amountTag=$("input[name='amountTag']").clone();
					var keywordTag=$("input[name='keyword']").clone();
					var typeTag=$("input[name='type']").clone();
					
					//formObj의 태그들은 그대로 두고 값은 모두 삭제
					//remove()를 사용하면 태그와 값 모두 삭제
					formObj.empty();
					//append():추가
					//게시물 리스트로 이동시 현재 페이지번호와 행수 속성값만 전달한다
					formObj.append(pageNumTag);
					formObj.append(amountTag);
					
					//검색 조건과 검색단어를 수정처리후 리스트화면으로 이동시 그대로 전달하기 위해 선언 03.15
					formObj.append(keywordTag);
					formObj.append(typeTag);
				}
				//수정버튼 클릭시
				formObj.submit();
			});
		});
	</script>
</body>
</html>