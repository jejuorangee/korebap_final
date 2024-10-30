<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>신고 내역</title>
<!-- Google Font -->
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Archivo:ital,wght@0,100..900;1,100..900&display=swap" rel="stylesheet">

<!-- Css Styles -->
<link rel="stylesheet" href="css/bootstrap.min.css" type="text/css">
<link rel="stylesheet" href="css/font-awesome.min.css" type="text/css">
<link rel="stylesheet" href="css/elegant-icons.css" type="text/css">
<link rel="stylesheet" href="css/flaticon.css" type="text/css">
<link rel="stylesheet" href="css/owl.carousel.min.css" type="text/css">
<link rel="stylesheet" href="css/nice-select.css" type="text/css">
<link rel="stylesheet" href="css/jquery-ui.min.css" type="text/css">
<link rel="stylesheet" href="css/magnific-popup.css" type="text/css">
<link rel="stylesheet" href="css/slicknav.min.css" type="text/css">
<link rel="stylesheet" href="css/style.css" type="text/css">
<link rel="stylesheet" href="css/starPlugin.css" type="text/css">
</head>
<body>
<c:import url="header.jsp"></c:import>

<div class="container mb-5">
	<div id="page-wrapper" >
		<div class="header"> 
			<h1 class="page-header">지난 신고</h1>
	   	</div>
	
		<div id="page-inner">
			<div class="row">
			   <div class="col-md-12">
					<!-- Advanced Tables -->
					<div class="card">
			    		<div class="card-action"></div>
			    		<div class="card-content">
			        		<div class="table-responsive">
				        		<div id="dataTables-example_wrapper" class="dataTables_wrapper form-inline" role="grid">
				            		<table class="table table-striped table-bordered table-hover" id="dataTables-example">
				                		<thead>
				                    		<tr>
												<th>신고한 아이디</th>
				                        		<th>신고 내용</th>
				                        		<th>신고 컨텐츠</th>
				                       			<th>신고 받은 아이디</th>
				                        		<th>승인 / 반려</th>
				                    		</tr>
				                		</thead>
				                		<tbody>
				                		<!-- 실 데이터 반복 -->
					                		<c:if test="${empty claimList}">
					                			<tr><td colspan=5>처리된 신고가 없습니다.</td></tr>
					                		</c:if>
					                		<c:forEach var="claim" items="${claimList}">
						                		<tr>
						                        	<td>${claim.claim_reporter_id}</td>
						                        	<td>${claim.claim_category}</td>
													<c:if test="${claim.claim_board_num != null}">
														<td><a href="boardDetail.do?board_num=${claim.claim_board_num}">게시글 ${claim.claim_board_num}</a></td>
													</c:if>
													<c:if test="${claim.claim_reply_num != null}">
														<td><a href="boardDetail.do?board_num=${claim.claim_reply_board_num}">댓글 ${claim.claim_reply_num}</a></td>
													</c:if>
							                        <td class="center">${claim.claim_target_member_id}</td>
							                        <td class="center">${claim.claim_status}</td>
				                        		</tr>
					                		</c:forEach>
			                    		<!-- 실 데이터 반복 -->
				                		</tbody>
				            		</table>
				        		</div>
				        		<div class="container">
				            		<div class="row">
				            			<div class="col-sm-12">
				            				<div class="dataTables_paginate paging_simple_numberscenter justify-content-center align-items-center" id="dataTables-example_paginate">
				            					<ul class="pagination justify-content-center align-items-center">
				            						<li class="paginate_button previous disabled" aria-controls="dataTables-example" tabindex="0" id="dataTables-example_previous">
				            							<a href="#">Previous</a>
			            							</li>
			            							<li class="paginate_button active" aria-controls="dataTables-example" tabindex="0">
			            								<a href="#">1</a>
			            							</li>
			            							<li class="paginate_button next disabled" aria-controls="dataTables-example" tabindex="0" id="dataTables-example_next">
			            								<a href="#">Next</a>
			            							</li>
		            							</ul>
		           							</div>
		       							</div>
				            		</div>
			            		</div>
			        		</div>
			    		</div>
					</div>
					<!--End Advanced Tables -->
			    </div>
			</div>
		</div>
	</div>
</div>
<c:import url="footer.jsp"></c:import>
</body>
</html>