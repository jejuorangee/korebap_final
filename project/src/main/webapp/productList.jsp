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
						<!-- 상품명 출력 -->
						<h2>${product.product_title}</h2>
						<div class="bt-option"></div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- 탐색경로 섹션 종료 -->

	<input type="hidden" id="product_location"
		data-product_location="${product_location}"
		value="${product_location}">
	<input type="hidden" id="product_category"
		data-product_category="${product_category}"
		value="${product_category}">
	<input type="hidden" id="productList" data-productList="${productList}"
		value="${productList}">
	<input type="hidden" id="product_total_page"
		data-product_page_count="${product_total_page}"
		value="${product_total_page}">
	<input type="hidden" id="currentPage"
		data-product-current-page="${currentPage}" value="${product_page_num}">


	<!-- 상품 검색창 섹션 시작 -->
	<div class="search-container">
		<div class="search-box">
			<!-- 검색폼 -->
			<form action="productListPage.do" method="GET">
				<!-- GET 요청으로 폼 제출 -->

				<select id="search_option" name="searchOption">
					<!-- 검색 옵션 선택 -->
					<option value="newest">최신순</option>
					<!-- 기본순 옵션 -->
					<option value="rating">별점순</option>
					<!-- 별점순 옵션 -->
					<option value="wish">찜 많은 순</option>
					<!-- 찜 많은 순 옵션 -->
					<option value="payment">결제 많은 순</option>
					<!-- 결제 많은 순 옵션 -->
				</select>
				<!-- 검색어 입력 -->
				<input type="text" class="search-input" name="product_searchKeyword"
					placeholder="상품검색..." value="${param.product_searchKeyword}">
				<!-- 검색 버튼 -->
				<button class="search-button" type="submit">검색</button>
			</form>
		</div>
	</div>
	<br>
	<!-- 상품 검색창 섹션 시작 -->

	<!-- 상품 목록 섹션 시작 -->
	<section class="blog-section blog-page spad">
		<div class="container">
			<div class="row">

				<!-- 현재 페이지 설정 -->
				<c:set var="currentPage"
					value="${param.currentPage != null ? param.currentPage : 1}" />

				<!-- 검색한 상품이 없는 경우 -->
				<c:if test="${empty productList}">
					<!-- productList가 비어있는지 검사 -->
					<div class="col-md-4">
						<!-- 3열 그리드 -->
						<p>검색 결과가 없습니다.</p>
						<!-- 검색 결과가 없을 때 메시지 -->
					</div>
				</c:if>
				<!-- c:forEach를 사용하여 상품 항목 반복 시작 -->
				<div class="row" id="productList">
					<c:forEach var="product" items="${productList}">
						<!-- productList를 반복 -->
						<div class="col-md-4">
							<!-- 3열 그리드 -->
							<c:if test="${not empty product.product_file_dir}">
								<img alt="상품사진입니다" class="blog-item set-bg"
									src="${product.product_file_dir}">
							</c:if>
							<c:if test="${empty product.product_file_dir}">
								<img alt="상품사진입니다" class="blog-item set-bg"
									src="img/board/boardBasic.png">
							</c:if>
							<div class="bi-text">
								<!-- 상품 카테고리 -->
								<span class="b-tag">${product.product_location}</span>🌊<span
									class="b-tag">${product.product_category}</span>
								<!-- 상품 별점 평균 -->
								<span>⭐${product.product_avg_rating}⭐</span>
								<!-- 상품명 및 링크 -->
								<!-- 상품의 PK값과 함께 이동 -->
								<h4>
									<a href="productDetail.do?product_num=${product.product_num}">${product.product_name}</a>
								</h4>
								<span class="b-tag">상품가격 : ${product.product_price}￦</span> <br>
								<br>
								<br>
							</div>
						</div>
					</c:forEach>
				</div>
			</div>
		</div>
	</section>
	<!-- 상품 목록 섹션 종료 -->

	<!-- 페이지네이션 섹션 시작 -->
	<div class="col-lg-12">
		<div class="pagination">
			<c:if test="${currentPage > 1}">
				<a href="#" class="page-link" data-page="${currentPage - 1}">이전</a>
			</c:if>
			<c:forEach var="i" begin="1" end="${product_page_count}">
				<c:if test="${i == currentPage}">
					<strong>${i}</strong>
				</c:if>
				<c:if test="${i != currentPage}">
					<a href="#" class="page-link" data-page="${i}">${i}</a>
				</c:if>
			</c:forEach>
			<c:if test="${currentPage < product_page_count}">
				<a href="#" class="page-link" data-page="${currentPage + 1}">다음</a>
			</c:if>
		</div>
	</div>
	<!-- 페이지네이션 섹션 종료 -->
 

	<script type="text/javascript">
$(document).ready(function() {
    console.log("문서 로드 완료");
    
    var baseUrl = 'productList.do';
    var searchKeyword = $('input[name="product_searchKeyword"]').val(); // 검색어 
    var searchOption = $('select[name="searchOption"]').val();  // 정렬 기준
    var productLocation = $('#product_location').val();  // 바다, 민물
    var productCategory = $('#product_category').val();  // 낚시배, 낚시터, 수상, 낚시카페
    var totalPage = $('#totalPage').data('product_page_count');  // 전체 페이지 수

    
	  console.log("currentPage 문서 로드 후 ["+currentPage+"]");
      console.log("searchKeyword 문서 로드 후 ["+searchKeyword+"]");
      console.log("searchOption 문서 로드 후 ["+searchOption+"]");
      console.log("productLocation 문서 로드 후 ["+productLocation+"]");
      console.log("productCategory 문서 로드 후 ["+productCategory+"]");
	
      /*
      // 검색 버튼 클릭 이벤트 처리
      // 특정 조건에 따라 들어온 페이지에서 검색한 경우 form 제출을 막기 위함
      $('.search-button').on('click', function(e) {
          e.preventDefault(); // 기본 폼 제출 방지
         loadProducts(1); // 검색 결과는 항상 첫 페이지에서 시작
      });
     */ 

    // 페이지네이션 클릭 이벤트	
    $('.pagination').on('click', '.page-link', function() {
        console.log("페이지네이션 클릭 이벤트");

        //renderProducts(currentPage); // 페이지 클릭 시 필터와 검색어를 포함해서 요청
        var currentPage = $(this).data('page');
        
        
        console.log("currentPage ["+currentPage+"]");
        loadProducts(currentPage); // 클릭한 페이지 번호로 로드
        
        
    });
      
    
 // 제품 로드 함수
    function loadProducts(currentPage) {
	 
	 
    	 // 페이지가 로드될 때마다 필요한 값 업데이트
        var searchKeyword = $('input[name="product_searchKeyword"]').val(); 
        var searchOption = $('select[name="searchOption"]').val();
        var productLocation = $('#product_location').val();  
        var productCategory = $('#product_category').val();  
	 
    	  console.log("searchKeyword ["+searchKeyword+"]");
          console.log("searchOption ["+searchOption+"]");
          console.log("productLocation ["+productLocation+"]");
          console.log("productCategory ["+productCategory+"]");

         
          // AJAX 요청
          $.ajax({
              url: baseUrl, // productList.do
              type: 'GET',
              data: {
              	"currentPage": currentPage,
                  "product_searchKeyword": searchKeyword,
                  "product_search_criteria": searchOption,
                  "product_location": productLocation,
                  "product_category": productCategory
              },
              dataType: 'json', // 응답 데이터 유형을 JSON으로 설정
              //contentType : 'application/json', // json  타입
              success: function(data) {
              	
          	    console.log("ajax요청 성공 반환");
          	    console.log("ajax요청 성공 currentPage : " +currentPage+ "]");

          	    
          	    
          	 // responseMap에서 productList 추출
                  const productList = data.productList;
                  let productHTML = ''; // 제품 정보를 담을 변수
          	   
                  
                  
          	 // 기존 상품 목록을 지우고 새로운 목록으로 대체
          	  $('.blog-section .row').empty();
                // $('#productList').empty();
          		//productHTML.empty();
                 
          		console.log("productList ["+productList+"]")

              	  if (!productList || productList.length === 0) {
    					console.log("더 이상 불러올 게시글이 없습니다.");
    					productHTML +='<p>데이터 없음</p>'; // 데이터가 없을 경우 메시지 표시
    					 updatePagination(1, 1); // 페이지네이션 초기화
    				}
              	  else{
              		  
                	 	// 반복문을 돌려 json 타입의 데이터를 html 요소에 반영한다.      
        	            productList.forEach((product, i) => {
        			    console.log("forEach 시작 i:" + i);
        			    productHTML += ' <div class="col-md-4">' +
        			    '<img alt="상품사진" class="blog-item set-bg" src="' + (product.product_file_dir ? product.product_file_dir : 'img/board/boardBasic.png') + '">' +
        			    '<div class="bi-text">' +
        			    '<span class="b-tag">' + product.product_location + '</span>🌊' +
        			    '<span class="b-tag">' + product.product_category + '</span>' +
        			    '<span>⭐' + product.product_avg_rating + '⭐</span>' +
        			    '<h4><a href="productDetail.do?product_num=' + product.product_num + '">' + product.product_name + '</a></h4>' +
        			    '<span class="b-tag">상품가격 : ' + product.product_price + '￦</span>' +
        			    '</div></div>';
        			    
        			 
        	            });
  	           // 페이지네이션 업데이트 함수 호출
  	            updatePagination(currentPage, data.product_page_count);
  	            
              		  
              	  }
               
          	 	
  	             $('.blog-section .row').append(productHTML);
          	 	
  	            
              }
          });
          
	 
 }

   // 페이지 버튼 상태 업데이트 함수
   function updatePagination(currentPage,totalPages) {
	   
	   console.log("페이지네이션 버튼 로그 currentPage ["+currentPage+"]")
	   console.log("페이지네이션 버튼 로그 totalPages ["+totalPages+"]")
	   
	   var paginationHtml = '';

	    if (currentPage > 1) {
	        paginationHtml += '<a href="#" class="page-link" data-page="' + (currentPage - 1) + '">이전</a>';
	    }

	    for (var i = 1; i <= totalPages; i++) {
	        if (i === currentPage) {
	            paginationHtml += '<strong class="page-link active">' + i + '</strong>';
	        } else {
	            paginationHtml += '<a href="#" class="page-link" data-page="' + i + '">' + i + '</a>';
	        }
	    }

	    if (currentPage < totalPages) {
	        paginationHtml += '<a href="#" class="page-link" data-page="' + (currentPage + 1) + '">다음</a>';
	    }

	    $('.pagination').html(paginationHtml);
   
   }
   
});
</script>




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
	<!--  <script src="js/main.js"></script>-->
</body>
</html>