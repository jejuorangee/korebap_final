<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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

<!-- Google Font 추가 -->
<link
	href="https://fonts.googleapis.com/css?family=Lora:400,700&display=swap"
	rel="stylesheet">

<!-- Css Styles -->
<link rel="stylesheet" href="css/bootstrap.css" type="text/css">
<link rel="stylesheet" href="css/font-awesome.min.css" type="text/css">
<link rel="stylesheet" href="css/elegant-icons.css" type="text/css">
<link rel="stylesheet" href="css/flaticon.css" type="text/css">
<link rel="stylesheet" href="css/owl.carousel.min.css" type="text/css">
<link rel="stylesheet" href="css/nice-select.css" type="text/css">
<link rel="stylesheet" href="css/jquery-ui.min.css" type="text/css">
<link rel="stylesheet" href="css/magnific-popup.css" type="text/css">
<link rel="stylesheet" href="css/slicknav.min.css" type="text/css">
<link rel="stylesheet" href="css/style.css" type="text/css">
<style>
.search-container { /* 검색 컨테이너 스타일 */
	text-align: center; /* 중앙 정렬 */
	margin-top: 50px; /* 위쪽 여백 */
}

.search-box { /* 검색 박스 스타일 */
	display: flex; /* Flexbox 사용 */
	justify-content: center; /* 중앙 정렬 */
	align-items: center; /* 수직 중앙 정렬 */
}

.search-input { /* 검색 입력창 스타일 */
	padding: 10px; /* 패딩 */
	font-size: 16px; /* 글자 크기 */
	border: 2px solid #ccc; /* 테두리 */
	border-radius: 5px 0 0 5px; /* 테두리 반경 */
	width: 300px; /* 너비 */
	outline: none; /* 아웃라인 제거 */
}

.search-button { /* 검색 버튼 스타일 */
	padding: 10px 20px; /* 패딩 */
	font-size: 16px; /* 글자 크기 */
	border: 2px solid #ccc; /* 테두리 */
	border-left: none; /* 왼쪽 테두리 제거 */
	border-radius: 0 5px 5px 0; /* 테두리 반경 */
	background-color: #1F3BB3; /* 배경색 */
	color: white; /* 글자 색상 */
	cursor: pointer; /* 커서 포인터 변경 */
}

.search-button:hover { /* 검색 버튼에 마우스 오버 시 */
	background-color: #e0f7fa; /* 배경색 변경 */
}

.file-display { /* 파일 표시 영역 스타일 */
	position: relative; /* 상대 위치 */
	z-index: 1; /* z-인덱스 */
	background-color: #f8f8f8; /* 배경색 */
	border: 1px solid #ccc; /* 테두리 */
	border-radius: 4px; /* 테두리 반경 */
	padding: 10px; /* 패딩 */
	min-height: 30px; /* 최소 높이 */
	line-height: 30px; /* 줄 높이 */
	overflow: hidden; /* 오버플로우 숨김 */
}

.nice-select { /* Nice Select 스타일 */
	margin-right: 10px; /* 오른쪽 여백 */
}

.text-center {
	display: flex; /* Flexbox 사용 */
	flex-direction: column; /* 세로 방향 정렬 */
	justify-content: center; /* 세로 방향 중앙 정렬 */
	align-items: center; /* 가로 방향 중앙 정렬 */
	height: 100%; /* 부모 요소의 높이에 맞춤 */
}

.no-products-message {
	font-size: 24px; /* 글자 크기 */
	font-weight: bold; /* 글자 두께 */
	color: #1F3BB3; /* 글자 색상 (파란색) */
	margin-bottom: 20px; /* 아래쪽 여백 */
}
</style>

</head>
<body>
	<!-- 샘플 데이터 설정 -->
	<%
	List<Map<String, Object>> productList = new ArrayList<>();

	for (int i = 1; i <= 7; i++) {
		Map<String, Object> product = new HashMap<>();
		product.put("product_name", "샘플 상품 " + i);
		product.put("product_num", 100 + i);
		productList.add(product);
	}

	request.setAttribute("productList", productList);
	%>
	<!-- 헤더 섹션 -->
	<c:import url="header.jsp"></c:import>

	<!-- 탐색경로 섹션 시작 -->
	<div class="breadcrumb-section">
		<div class="container">
			<div class="row">
				<div class="col-lg-12">
					<div class="breadcrumb-text">
						<!-- 상품명 출력 -->
						<h2 style="display: inline;">내 상품 관리</h2>
						<button class="search-button" style="margin-left: 20px;"
							onclick="location.href='addProduct.jsp'">상품 등록하기</button>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- 탐색경로 섹션 종료 -->

	<!-- 상품 목록 섹션 시작 -->
	<section class="blog-section blog-page spad">
		<div class="container">
			<div class="row">
				<c:if test="${empty productList}">
					<div class="col-md-12 text-center">
						<p class="no-products-message">
							등록한 상품이 없습니다. <br> 상품을 등록해 주세요.
						</p>
					</div>
				</c:if>
				<c:if test="${not empty productList}">
					<c:forEach var="product" items="${productList}">
						<div class="col-md-4">
							<c:if test="${not empty product.product_file_dir}">
								<img alt="상품사진입니다" class="blog-item set-bg"
									src="${product.product_file_dir}">
							</c:if>
							<c:if test="${empty product.product_file_dir}">
								<img alt="상품사진입니다" class="blog-item set-bg"
									src="img/board/boardBasic.png">
							</c:if>
							<div class="bi-text">
								<span class="b-tag">${product.product_category}</span> <span>⭐${product.product_avg_rating}⭐</span>
								<h4>
									<a href="productDetail.do?product_num=${product.product_num}">${product.product_name}</a>
								</h4>
								<span class="b-tag">상품가격 : ${product.product_price}￦</span> <br>

								<p>${product.product_name}</p>
								<button class="search-button" type="submit"
									onclick="location.href='detail.jsp?id=${product.product_num}'">상세정보</button>
								<button class="search-button" type="button"
									style="margin-left: 10px;" data-toggle="modal"
									data-target="#deleteModal"
									data-product-num="${product.product_num}">삭제</button>
							</div>
							<br> <br>
						</div>
					</c:forEach>
				</c:if>
			</div>
		</div>
	</section>
	<!-- 상품 목록 섹션 종료 -->

	<!-- 삭제 확인 모달 -->
	<div class="modal fade" id="deleteModal" tabindex="-1" role="dialog"
		aria-labelledby="deleteModalLabel" aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="deleteModalLabel">삭제 확인</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">정말 삭제하시겠습니까?</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary"
						data-dismiss="modal">취소</button>
					<button type="button" class="btn btn-danger"
						id="confirmDeleteButton">삭제</button>
				</div>
			</div>
		</div>
	</div>


	<!-- 페이지네이션 섹션 시작 -->
	<div class="col-lg-12">
		<div class="pagination">
			<!-- 현재 페이지가 1보다 크면 '이전' 링크 표시 -->
			<c:if test="${currentPage > 1}">
				<a href="productListPage.do?currentPage=${currentPage - 1}">이전</a>
			</c:if>
			<c:if test="${currentPage == 1}">
				<a href="productListPage.do?currentPage=${currentPage - 1}" disabled>이전</a>
			</c:if>

			<!-- 전체 페이지 수에 따른 페이지 번호 링크 생성 -->
			<c:forEach var="i" begin="1" end="${product_page_count}">
				<c:if test="${i == currentPage}">
					<strong>${i}</strong>
					<!-- 현재 페이지 강조 -->
				</c:if>
				<c:if test="${i != currentPage}">
					<a href="productListPage.do?currentPage=${i}">${i}</a>
				</c:if>
			</c:forEach>

			<!-- 현재 페이지가 전체 페이지 수보다 작으면 '다음' 링크 표시 -->
			<c:if test="${currentPage < product_page_count}">
				<a href="productListPage.do?currentPage=${currentPage + 1}">다음</a>
			</c:if>
			<c:if test="${currentPage == product_page_num}">
				<a href="productListPage.do?currentPage=${currentPage + 1}" disabled>다음</a>
			</c:if>
		</div>
	</div>
	<!-- 페이지네이션 섹션 종료 -->
	<!-- JavaScript 코드 -->
	<script>
$(document).ready(function() {
    let productNumToDelete;

    // 삭제 버튼 클릭 시 모달에 제품 번호 저장
    $('button[data-target="#deleteModal"]').on('click', function() {
        productNumToDelete = $(this).data('product-num');
    });

    // 모달의 삭제 버튼 클릭 시 삭제 요청
    $('#confirmDeleteButton').on('click', function() {
        $.post('productDelete.do', { product_num: productNumToDelete }, function(response) {
            // 삭제 후 페이지 새로고침 또는 메시지 표시
            location.reload(); // 삭제 후 페이지 새로고침
        });
    });
});
</script>
	<!-- 푸터 연결 -->
	<jsp:include page="footer.jsp" />

	<!-- Js Plugins -->
	<script src="js/jquery-3.3.1.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="js/jquery.magnific-popup.min.js"></script>
	<script src="js/jquery.nice-select.min.js"></script>
	<script src="js/jquery-ui.min.js"></script>
	<script src="js/jquery.slicknav.js"></script>
	<script src="js/owl.carousel.min.js"></script>
	<script src="js/main.js"></script>
</body>
</html>
