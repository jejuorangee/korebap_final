<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="mytag"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="description" content="Sona Template">
<meta name="keywords" content="Sona, unica, creative, html">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<title>고래밥</title>

<!-- Google Font -->
<link
	href="https://fonts.googleapis.com/css?family=Lora:400,700&display=swap"
	rel="stylesheet">
<link
	href="https://fonts.googleapis.com/css?family=Cabin:400,500,600,700&display=swap"
	rel="stylesheet">


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

<!-- jQuery 연결 -->
<script src="http://code.jquery.com/jquery-3.5.1.min.js"></script>
<!-- infinite-scroll CDN 
<script src="https://cdn.jsdelivr.net/gh/marshallku/infinite-scroll/dist/infiniteScroll.js"></script>-->

<style>
.search-container {
	text-align: center;
	margin-top: 50px;
}

.search-box {
	display: flex;
	justify-content: center;
	align-items: center;
}

.search-input {
	padding: 10px;
	font-size: 16px;
	border: 2px solid #ccc;
	border-radius: 5px 0 0 5px;
	width: 300px;
	outline: none;
}

.search-button {
	padding: 10px 20px;
	font-size: 16px;
	border: 2px solid #ccc;
	border-left: none;
	border-radius: 0 5px 5px 0;
	background-color: #1F3BB3;
	color: white;
	cursor: pointer;
}

.search-button:hover {
	background-color: #e0f7fa;
}

.file-display {
	position: relative;
	z-index: 1;
	background-color: #f8f8f8;
	border: 1px solid #ccc;
	border-radius: 4px;
	padding: 10px;
	min-height: 30px;
	line-height: 30px;
	overflow: hidden;
}

.nice-select {
	margin-right: 10px;
}

body {
	color: black; /* 전체 글씨 색상 검정으로 변경 */
	background-color: #f8f8f8; /* 배경색을 밝은 회색으로 설정 */
}

.bi-text {
	color: black; /* 글씨 색상 검정으로 변경 */
	background-color: white; /* 배경색을 하얀색으로 설정 */
	padding: 10px; /* 여백 추가 */
}
</style>
</head>




<body>
	<!-- 헤더 연결 -->
	<c:import url="header.jsp"></c:import>

	<!-- 탐색경로 섹션 시작 -->
	<div class="breadcrumb-section">
		<div class="container">
			<div class="row">
				<div class="col-lg-12">
					<div class="breadcrumb-text">
						<h2>자유 게시판</h2>
						<div class="bt-option">
							<span>포인트 소개, 월척자랑</span>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- 탐색경로 섹션 종료 -->

	<!-- 검색창 섹션 시작 -->
	<div class="search-container">
		<div class="search-box">
			<!-- 검색폼 -->
			<form action="boardListPage.do" method="GET">
				<select name="board_search_criteria">
					<option value="basic"
						${search_criteria == 'basic' ? 'selected' : ''}>최신순</option>
					<option value="like" ${search_criteria == 'like' ? 'selected' : ''}>좋아요순</option>
				</select> <input type="text" class="search-input" name="board_searchKeyword"
					placeholder="게시물검색..." value="${param.board_searchKeyword}">
				<button class="search-button" type="submit">검색</button>
				<!--  <div class="select-option" value="${search_keyword}"></div> -->
			</form>
			<!-- 글 작성 버튼 추가 -->
			<a href="writeBoardPage.do" class="search-button"
				style="margin-left: 10px;">글 작성하기</a>
		</div>
	</div>
	<br>
	<!-- 검색창 섹션 시작 -->

	<!-- 게시판 목록 섹션 시작 -->
	<!-- 게시판 목록이 비어있지 않다면 -->
	<section class="blog-section blog-page spad">
		<div class="container">
			<!-- 게시글이 없는 경우 메시지 출력 -->
			<c:if test="${empty boardList}">
				<div class="empty-boardList">
					<p>게시글이 없습니다.</p>
					<a href="main.do" class="btn-primary">메인으로 돌아가기</a>
				</div>
			</c:if>

			<!-- 게시글이 있는 경우 게시글 목록 출력 -->
			<div class="row" id="boardList">
				<!-- AJAX로 데이터를 받아서 이곳에 추가 -->
				<c:if test="${not empty boardList}">
					<c:forEach var="board" items="${boardList}">
						<div class="col-md-4">
							<div class="blog-item scroll_counter">
								<c:if test="${not empty board.board_file_dir}">
									<img src="img/board/${board.board_file_dir}" alt="게시글 이미지"
										class="img-fluid">
								</c:if>
								<c:if test="${empty board.board_file_dir}">
									<img src="img/board/boardBasic.png" alt="기본 이미지"
										class="img-fluid">
								</c:if>
								<div class="bi-text">
									<span class="b-tag">자유게시판</span>
									<div class="b-tag">${board.board_like_cnt}</div>
									<h4>
										<a class="text-dark"
											href="boardDetail.do?member_id=${member_id}&board_num=${board.board_num}">${board.board_title}</a>
									</h4>
									<div class="b-time">
										<div class="text-dark">
											<i class="icon_clock_alt"></i>${board.board_registration_date}
										</div>
									</div>
								</div>
							</div>
						</div>
					</c:forEach>

				</c:if>
			</div>
		</div>
	</section>
	<!-- 게시판 목록 섹션 종료 -->



	<input type="hidden" id="totalPage"
		data-board-total-page="${totalPage}" value="${totalPage}">
	<input type="hidden" id="currentPage"
		data-board-current-Page="${currentPage}">




	<!-- 푸터 연결 -->
	<c:import url="footer.jsp"></c:import>

	<!-- Js Plugins -->
	<!--  <script src="js/jquery-3.3.1.min.js"></script> -->
	<script src="js/bootstrap.min.js"></script>
	<script src="js/jquery.magnific-popup.min.js"></script>
	<script src="js/jquery.nice-select.min.js"></script>
	<script src="js/jquery-ui.min.js"></script>
	<script src="js/jquery.slicknav.js"></script>
	<script src="js/owl.carousel.min.js"></script>
	<script src="js/main.js"></script>
	<script src="js/board/boardListScroll.js"></script>

</body>
</html>