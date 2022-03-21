/*
    댓글 처리 시 공통 기능 수행 03.17 
 */

console.log("Reply Module");
//즉시 실행 함수(함수 선언과 동시에 바로 실행)
var replyService = (function(){
	
	//신규 댓글 추가 처리하는 함수
	/*reply : 신규처리를 하기 위한 객체,
	callback:ajax(비동기통신)을 이용하여 처리후 성공,혹은 실패
	처리*/
	function add(reply,callback,error){
		
		$.ajax({
			type:"post",//전송방식 선언
			url: "/replies/new",//실행하려는 url선언
			//매개변수로 전달되는 객체를 json형태의 문자열로 변환처리
			data: JSON.stringify(reply),
			contentType:"application/json;charset=utf-8",
			//비동기 통신방식 처리결과가 성공시 처리
			success: function(result,status,xhr){
				if(callback){
					callback(result);
				}
			},
			//비동기 통신방식 처리결과가 실패시 처리
			error:function(xhr,status,er){
				if(error){
					error(er);
				}
			}
		})
	}
	
	//p406
	//댓글 목록을 가져오는 함수
	//param:매개변수를 객체타입으로 전달
	function getList(param,callback,error){
		var bno = param.bno;//게시물 번호
		var page = param.page || 1;
		
		//$.getJSON?
		//Http Get방식의 요청을 통해 서버로 부터 받은 Json 데이터를
		//가져오는 선언
		$.getJSON("/replies/pages/" + bno + "/" + page + ".json",
				  function(data){
				  	if(callback){
				  		callback(data.replyCnt,data.list);
				  	}
				  }).fail(function(xhr,status,err){
				  	if(error){
				  		error();
				  	}
				  });
	}//
	
	//p408
	//특정 댓글 삭제 처리
	function remove(rno,callback,error){
		$.ajax({
			type:"delete",
			url:"/replies/" + rno,
			success:function(deleteResult,status,xhr){
				if(callback){
					callback(deleteResult);
				}
			},
			error:function(xhr,status,er){
				if(error){
					error(er);				
				}
			}		
		});
	}
	
	//p410
	//특정 댓글 수정처리
	function update(reply,callback,error){
		$.ajax({
			type:"patch",
			url:"/replies/" + reply.rno,
			data:JSON.stringify(reply),
			contentType:"application/json;charset=utf-8",
			success:function(result,status,xhr){
				if(callback){
					callback(result);
				}
			},
			error:function(xhr,status,er){
				if(error){
					error(er);
				}
			}
			
		});
	}
	
	//p412
	//특정 댓글 내역 상세보기
	function get(rno,callback,error){
		$.get("/replies/" + rno + ".json",function(result){
			if(callback){
				callback(result);
			}
		}).fail(function(xhr,status,err){
			if(error){
				error();
			}
		});
	}
	
	return {
		add:add,
		getList:getList,
		remove:remove,
		update:update,
		get:get
	};
	
})();








