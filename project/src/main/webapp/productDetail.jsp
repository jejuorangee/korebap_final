<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
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
<link
   href="https://fonts.googleapis.com/css2?family=Noto+Sans:wght@400;700&display=swap" rel="stylesheet">
   

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

<script type="text/javascript"src="//dapi.kakao.com/v2/maps/sdk.js?appkey=fecf06794382c055ec106573ef0aa278&libraries=services"></script>
<script type="text/javascript"src="//dapi.kakao.com/v2/maps/sdk.js?appkey=fecf06794382c055ec106573ef0aa278"></script>
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=APIKEY&libraries=services,clusterer,drawing"></script>
<style>
/* 파일출력 관련 스타일 */
.testimonial-section {
   padding: 0; /* 위아래 패딩을 없애기 */
}

.bd-pic {
   margin: 0; /* 이미지 주변 마진을 없애기 */
}

.bd-pic img {
   max-height: 500px; /* 이미지 최대 높이 조정 */
   width: 100%; /* 섹션에 맞게 전체 너비 사용 */
   height: auto; /* 비율 유지 */
   object-fit: cover; /* 비율 유지하면서 잘라내기 */
}

.testimonial-slider {
   height: auto; /* 슬라이더 높이를 자동으로 조정 */
}

.ts-item {
   margin: 0; /* 아이템 간의 여백 줄이기 */
}

/*날씨 스타일*/
.section-title h3 {
   color: white;
   text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.5); /* 그림자 추가 */
}

.weatherInfo {
   color: white;
   text-shadow: 1px 1px 3px rgba(0, 0, 0, 0.5); /* 그림자 추가 */
}

/*별점 플러그인*/
.star-rating {
   display: inline-block;
   font-size: 0;
   position: relative;
   cursor: pointer;
   direction: rtl;
}

.star-rating input {
   display: none;
}

.star-rating label {
   color: #ccc;
   font-size: 2rem;
   padding: 0;
   margin: 0;
   cursor: pointer;
   display: inline-block;
}

.star-rating label:before {
   content: '★';
}

.star-rating input:checked ~ label {
   color: gold;
}

.star-rating input:checked ~ label:hover, .star-rating input:checked ~
   label:hover ~ label {
   color: gold;
}

.star-rating input:hover ~ label {
   color: gold;
}

#result {
   margin-top: 10px;
}

#averageRating {
   font-size: 1.5rem;
   margin-top: 20px;
}
/*찜목록 추가 버튼*/
.btn-add {
    top: 10px; 
    right: 10px;
    background: none;
    border: none;
    font-size: 24px;
    color: red;
    cursor: pointer;
}
.btn-add:hover {
    color: darkred;
}

</style>

</head>

<body>
   <!-- 헤더 연결 -->
   <c:import url="header.jsp"></c:import>

   <!-- 상품 이름 & 카테고리 섹션 시작 -->
   <br>
   <div class="bd-hero-text">
      <!-- 카테고리(바다,민물) -->
      <span>바다, 민물</span>
      <!-- EL식 : product_kategorie -->
      <!-- 상품 제목 -->
      <h2 style="color: #00008B;">${product.product_name}
      <!-- 찜 버튼 -->
      <c:if test="${not empty member_id}">
         <form action="addWishList.do" method="POST" onsubmit="return showAlert()">
             <c:if test="${not empty wishlist.wishlist_product_num}">
                 <button type="submit" name="wishlist_product_num" id="wishlist-add" value="${product.product_num}" class="btn-add" data-num="${product.product_num}"><i class="icon_heart"></i></button>
             </c:if>
         </form>
      </c:if>
      </h2>
   </div>
   <br>
   <!-- 상품 이름 & 카테고리 섹션 종료 -->

   <!-- 지도 & 달력 & 날씨 섹션 시작 -->
   <section class="hero-section">
      <div class="container">
         <div class="row">
            
            <!-- 지도 API -->
            <div class="col-lg-6">
               <div id="product_map" style="width: 100%; height: 600px;"></div>
            </div>

            <!-- 달력(예약날짜) 선택 섹션 -->
            <div class="col-lg-6">
               <!-- 예약 폼 -->
               <c:import url="calendar.jsp"></c:import>

               <br>
               <br>
               <!-- 현재 날씨 -->
               <div class="section-title">
                  <h3>현재 날씨</h3>
                  <div class="container d-flex flex-column align-items-center">
                     <div class="weatherInfo"></div>
                     <img class="weatherIcon">
                  </div>
               </div>
            </div>
         </div>
      </div>
      <!-- 배경 사진 -->
      <div class="hero-slider owl-carousel">
         <div class="hs-item set-bg"></div>
      </div>
   </section>
   <!-- 지도 & 달력 & 날씨 섹션 종료 -->

   <!-- 상품 상세페이지 파일출력섹션 시작 -->
   <section class="testimonial-section spad">
      <div class="pi-text">
      </div>
      <!-- 사장님이 넣은 파일 확인유무(사진) -->
      <c:if test="${not empty fileList}">
         <div class="col-lg-8 offset-lg-2">
            <div class="testimonial-slider owl-carousel">
               <!-- 데이터로부터 사장님이 넣은 사진 파일 반복 -->
               <c:forEach var="file" items="${fileList}">
                  <div class="ts-item">
                     <div class="bd-pic">
                        <!-- 사장님 입력 이미지 -->
                        <img src="${file.file_dir}" alt="상품설명을 위해 사장님이 넣은 사진파일입니다.">
                     </div>
                  </div>
               </c:forEach>
            </div>
         </div>
      </c:if>
   </section>
   <!-- 상품 상세페이지 파일출력섹션 종료 -->

   <!-- 상품 상세페이지 메인섹션 시작 -->
   <section class="blog-details-section">
      <div class="container">
         <div class="row">
            <div class="col-lg-10 offset-lg-1">
               <div class="blog-details-text">
                  <div class="bd-title">
                     <!-- 블로그 내용 -->
                     <p>${product.product_details}</p>
                  </div>
                  <div class="comment-option">
                     <h4>리뷰</h4>
                     <c:if test="${empty review_num}">
                        <p>작성된 리뷰이 없습니다.</p>
                     </c:if>
                     <!-- 리뷰 반복 -->
                     <c:forEach var="review" items="${reviewList}">
                        <div class="single-comment-item">
                           <div class="sc-author">
                              <!-- 리뷰 작성자 프로필 이미지 -->
                              <img src="img/profile/${review.review_member_profile}"
                                 alt="댓글 작성자 프로필 이미지">
                           </div>
                           <div class="sc-text">
                              <!-- 리뷰 작성일자 -->
                              <span>${review.review_registration_date}</span>
                              
                              <!-- 리뷰 작성자 -->
                              <h5>
                                 <!-- 사용자 레벨에 따른 등급 -->
                                 <c:if test="${not empty member.member_id}">
                                    <c:import url="role.jsp"></c:import>
                                 </c:if>
                                 ${review.review_content}
                              </h5>
                              <!-- 리뷰 내용 -->
                              <p>${review.review_writer_id}</p>
                              <!-- 리뷰 삭제 (비동기X)-->
                              <c:if test="${member_id == review.review_writer_id}">
                                 <a href="deleteReview.do?review_num=${review.review_num}&product_num=${product.product_num}"
                                    class="comment-btn">리뷰삭제</a>
                              </c:if>
                           </div>
                        </div>
                     </c:forEach>
                  </div>
                  <div class="leave-comment">
                  
                     <!-- 리뷰 작성 폼 -->
                     <form action="writeReview.do" method="POST" class="comment-form">
                        <h4>리뷰 작성</h4>
                        <div class="star-rating">
                           <input type="radio" id="star5" name="rating" value="5" />
                           <label for="star5" title="5개 별"></label>
                           <input type="radio" id="star4" name="rating" value="4" />
                           <label for="star4" title="4개 별"></label>
                           <input type="radio" id="star3" name="rating" value="3" />
                           <label for="star3" title="3개 별"></label>
                           <input type="radio" id="star2" name="rating" value="2" />
                           <label for="star2" title="2개 별"></label>
                           <input type="radio" id="star1" name="rating" value="1" />
                           <label for="star1" title="1개 별"></label>
                        </div>
                        <div class="row">
                           <div class="col-lg-12 text-center">
                              <!-- 글 내용 입력 필드 -->
                              <textarea id="content" name="review_content" rows="10"
                                 required placeholder="리뷰를 작성해주세요"></textarea>
                              <!-- 글번호 -->
                              <input type="hidden" name="review_product_num"
                                 value="${product.product_num}" />
                              <!-- 제출 버튼 -->
                              <input type="submit" value="리뷰작성" class="site-btn" />
                           </div>
                        </div>
                     </form>
                  </div>
               </div>
            </div>
         </div>
      </div>
   </section>
   <!-- 글 상세페이지 메인섹션 종료 -->

   <!-- 추천 상품섹션 시작 -->
   <section class="recommend-blog-section spad">
      <div class="container">
         <div class="row">
            <div class="col-lg-12">
               <div class="section-title">
                  <h2>추천 상품</h2>
               </div>
            </div>
         </div>
         <div class="row">
            <!-- 추천 상품 아이템 반복 -->
            <c:forEach var="product" items="${productlist}" begin="1" end="3"
               step="1">
               <div class="col-md-4">
                  <!-- 게시판 태그 -->
                  <mytag:smallBoard />
               </div>
            </c:forEach>
         </div>
      </div>
   </section>
   <!-- 추천 상품섹션 종료 -->

   <!-- 푸터 연결 -->
   <c:import url="footer.jsp"></c:import>

   <!-- 템플릿 Js 플러그인 -->
   <script src="js/jquery-3.3.1.min.js"></script>
   <script src="js/bootstrap.min.js"></script>
   <script src="js/jquery.magnific-popup.min.js"></script>
   <script src="js/jquery.nice-select.min.js"></script>
   <script src="js/jquery-ui.min.js"></script>
   <script src="js/jquery.slicknav.js"></script>
   <script src="js/owl.carousel.min.js"></script>
   <script src="js/main.js"></script>
   <script src="js/weather.js"></script>
   

<script>
function showAlert() {
    alert("상품이 찜 목록에 추가되었습니다!");
    return true; // 폼을 제출하도록 허용
}
</script>
<script>
var mapContainer = document.getElementById('product_map'), // 지도를 표시할 div 
    mapOption = {
        center: new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
        level: 3 // 지도의 확대 레벨
    };  

// 지도를 생성합니다    
var map = new kakao.maps.Map(mapContainer, mapOption); 

// 주소-좌표 변환 객체를 생성합니다
var geocoder = new kakao.maps.services.Geocoder();

var product_data={address:'${product.product_address}'};

// 주소로 좌표를 검색합니다
geocoder.addressSearch(product_data.address, function(result, status) {

    // 정상적으로 검색이 완료됐으면 
     if (status === kakao.maps.services.Status.OK) {

        var coords = new kakao.maps.LatLng(result[0].y, result[0].x);

        // 결과값으로 받은 위치를 마커로 표시합니다
        var marker = new kakao.maps.Marker({
            map: map,
            position: coords
        });

        // 인포윈도우로 장소에 대한 설명을 표시합니다
        var infowindow = new kakao.maps.InfoWindow({
            content: '<div style="width:150px;text-align:center;padding:6px 0;">${product.product_name}</div>'
        });
        infowindow.open(map, marker);

        // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
        map.setCenter(coords);
    } 
});    
</script>
</body>

</html>