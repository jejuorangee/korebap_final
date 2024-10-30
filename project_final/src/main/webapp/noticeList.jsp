<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<!-- Google Font -->
<link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200" />

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
<title>공지 사항 목록</title>
</head>
<body>
<!-- 헤더 연결 -->
<c:import url="header.jsp"></c:import>

<!-- container start -->
	<div class="container">
		<div class="page-inner">
			<div class="row pt-5 pb-2">
				<div class="col-12">
					<h1 class="text-center">커뮤니티</h1>
				</div>
			</div>
			<c:if test="${empty noticeList}">
				<div>공지 사항 없음</div>
			</c:if>
			<c:if test="${not empty noticeList}">
				<c:forEach var="notice" items="${noticeList}">
					<div class="row pt-5">
						<div class="col-12">
							<div class="card card-stats card-round mb-0">
								<div class="card-body p-5 d-flex justify-content-between">
									<h3 class="card-title">
										<a href="BOARDONEPAGEACTION.do?model_board_num=${notice.board_num}"
											class="link-dark"> ${notice.board_title}</a>
									</h3>
									<div class="info-user">
										<div class="username">작성자 : ${notice.board_writer_id}</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</c:forEach>
			</c:if>
			<div class="row pt-5">
				<div class="col-md-12 d-flex justify-content-center">
					<nav aria-label="Page navigation">
						<ul id="pagination" class="pagination justify-content-center align-items-center">
							
						</ul>
					</nav>
				</div>
			</div>
		</div>
	</div>		<!-- container end -->

<!-- 푸터 연결 -->
<c:import url="footer.jsp"></c:import>

<!-- Js Plugins -->
<script src="js/jquery-3.3.1.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/jquery.magnific-popup.min.js"></script>
<script src="js/jquery.nice-select.min.js"></script>
<script src="js/jquery-ui.min.js"></script>
<script src="js/jquery.slicknav.js"></script>
<script src="js/owl.carousel.min.js"></script>
<script src="js/main.js"></script>
<script src="js/myJs/displayFileNames.js"></script>
</body>
</html>