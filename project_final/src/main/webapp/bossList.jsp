<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
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
<title>사장님 사용자 목록</title>
</head>
<body>
<c:import url="header.jsp"></c:import>
<div class="container mb-5">
	<div id="page-wrapper" >
		<div class="header"> 
			<h1 class="page-header">사장님 사용자 목록</h1>
	   	</div>
	
		<div id="page-inner">
			<div class="row">
			   <div class="col-md-12">
					<!-- Advanced Tables -->
		    		<div class="card-action mb-5 mt-3">
		    			<div class="d-flex justify-content-center">
		    				<form action="searchMember.do">
				    			<input class="search-input" type="text" name="member_id" placeholder="회원 검색..." required />
				    			<input class="search-button" type="submit" value="검색"/>
			    			</form>
		    			</div>
	    			</div>
					<div class="card manager-main-card">
			    		<div class="card-content">
			        		<div class="table-responsive">
				        		<div id="dataTables-example_wrapper" class="dataTables_wrapper form-inline" role="grid">
				            		<table class="table table-striped table-bordered table-hover" id="dataTables-example">
				                		<thead>
				                    		<tr>
												<th>ID</th>
				                        		<th>이름</th>
				                        		<th>전화번호</th>
				                       			<th>가입날짜</th>
				                        		<th>비고</th>
				                    		</tr>
				                		</thead>
				                		<tbody>
				                		<!-- 실 데이터 반복 -->
					                		<c:if test="${empty bossList}">
					                			<tr><td colspan=5>사장님 사용자가 없습니다.</td></tr>
					                		</c:if>
					                		<c:forEach var="boss" items="${bossList}">
						                		<a href="mypage.do?member_id=${boss.member_id}">
							                		<tr>
							                        	<td>${boss.member_id}</td>
							                        	<td>${boss.member_name}</td>
														<td>${boss.member_phone}</td>
								                        <td>${boss.member_registration_date}</td>
								                        <td>
								                        	<div class="d-flex justify-content-between">
								                        		<a href="MemberBan.do?member_id=${boss.member_id}" class="btn btn-primary disabled" role="button" aria-disabled="true">정지</a>
								                        		<a href="deleteMember.do?member_id=${boss.member_id}" class="btn btn-primary disabled" role="button" aria-disabled="true">탈퇴</a>
							                        		</div>
					                        			</td>
					                        		</tr>
				                        		</a>
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