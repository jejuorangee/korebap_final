<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<link rel="stylesheet" href="css/style.css" type="text/css">

<header class="header-section">
	<div class="top-nav">
		<div class="container">
			<div class="row">
				<div class="col-lg-6">
					<ul class="tn-left">
						<!-- 헤더의 왼쪽 부분: 연락처 정보 -->
						<li><i class="fa fa-phone"></i>1588-5890</li>
						<li>
							<!-- 사용자 레벨에 따른 등급 --> <c:if test="${empty member_id}">
								<c:import url="role.jsp"></c:import>
							</c:if> ${member.member_nickname}
						</li>
					</ul>
				</div>
				<div class="col-lg-6">
					<div class="tn-right">
						<div class="top-social">
							<!-- 소셜 미디어 링크 -->
							<a href="https://koreaisacademy.com/"><i
								class="fa fa-facebook"></i></a> <a
								href="https://koreaisacademy.com/"><i class="fa fa-twitter"></i></a>
							<a href="https://koreaisacademy.com/"><i
								class="fa fa-instagram"></i></a>
						</div>
						<!-- 사용자 로그인 태그 -->
						<mytag:login />
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="menu-item">
		<div class="container">
			<div class="row">
				<div class="col-lg-2">
					<div class="logo">
						<!-- 사이트 로고 -->
						<a href="main.do"> <img src="img/logo.png" alt="로고입니다."></a>
					</div>
				</div>
				<div class="col-lg-10">
					<div class="nav-menu">
						<nav class="mainmenu">
							<ul>
								<!-- 내비게이션 메뉴 시작 -->
								<li><a
									href="productListPage.do?product_searchContent=null&product_searchKeyword=null">낚시장소</a>
									<ul class="dropdown">
										<!-- 드롭다운 메뉴 항목 -->
										<li><a href="productListPage.do?product_location=바다">바다</a></li>
										<li><a href="productListPage.do?product_location=민물">민물</a></li>
									</ul></li>
								<li><a
									href="productListPage.do?product_searchContent=null&searchKeyword=null">낚시유형</a>
									<ul class="dropdown">
										<li><a href="productListPage.do?product_category=낚시터">낚시터</a></li>
										<li><a href="productListPage.do?product_category=낚시배">낚시배</a></li>
									</ul></li>
								<li><a>마이 메뉴</a>
									<ul class="dropdown">
										<li><a href="myReservationListPage.do">내 예약</a></li>
										<li><a href="wishListPage.do">위시리스트</a></li>
									</ul></li>
								<li><a href="boardListPage.do">게시판</a>
									<ul class="dropdown">
										<li><a href="boardListPage.do">자유 게시판</a></li>
									</ul></li>
								<li><a href="productManagement.do">상품 관리</a>
									<ul class="dropdown">
										<li><a href="productManagement.do">내 상품 관리</a></li>
									</ul></li>
								<li><a href="reservationManagement.do">예약 관리</a>
									<ul class="dropdown">
										<li><a href="reservationManagement.do">내 상품 예약 내역</a></li>
									</ul></li>
								<li>
									<!-- 검색창 포함 -->
									<div class="search-container">
										<!-- 검색 버튼 -->
										<button type="button" class="btn btn-primary"
											data-bs-toggle="modal" data-bs-target="#filterModal">
											상품 검색</button>
									</div>
								</li>
							</ul>
						</nav>
					</div>
				</div>
			</div>
		</div>
	</div>
</header>
<body>

<!-- 모달 -->
<div class="modal fade" id="filterModal" tabindex="-1"
	aria-labelledby="filterModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="filterModalLabel">상품 검색</h5>
				<button type="button" class="btn-close" data-bs-dismiss="modal"
					aria-label="Close"></button>
			</div>
			<div class="modal-body">
				<form id="filterForm">
					<div class="mb-3">
						<label>유형</label><br> <input type="checkbox" name="type"
							value="sea"> 바다 <input type="checkbox" name="type"
							value="freshwater"> 민물
					</div>
					<div class="mb-3">
						<label>카테고리</label><br> <input type="checkbox"
							name="category" value="fishing_spot"> 낚시터 <input
							type="checkbox" name="category" value="fishing_boat"> 낚시배
						<input type="checkbox" name="category" value="fishing_cafe">
						낚시카페
					</div>
					<div class="mb-3">
						<label>이름 또는 내용</label><br> <input type="text"
							name="searchQuery" placeholder="검색어 입력">
					</div>
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-secondary"
					data-bs-dismiss="modal">취소</button>
				<button type="button" class="btn btn-primary"
					onclick="submitSearch()">검색</button>
			</div>
		</div>
	</div>
</div>
</body>
<!-- 헤더 섹션 종료 -->
<!-- Bootstrap JS (필수) -->
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script>
    function submitSearch() {
        var form = document.getElementById('filterForm');
        var formData = new FormData(form);
        
        // formData 출력 (테스트용)
        formData.forEach(function(value, key){
            console.log(key + ": " + value);
        });

        // 모달 닫기
        var myModalEl = document.getElementById('filterModal');
        var modal = bootstrap.Modal.getInstance(myModalEl);
        modal.hide();
    }
</script>

