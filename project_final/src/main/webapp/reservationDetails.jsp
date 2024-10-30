<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="mytag"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>예약 내역 상세 페이지</title>
<link rel="stylesheet" href="css/bootstrap.min.css" type="text/css">
<link rel="stylesheet" href="css/style.css" type="text/css">

<!-- jQuery 사용을 위한 연결 -->
<script src="https://code.jquery.com/jquery-3.7.1.min.js"
   integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo="
   crossorigin="anonymous"></script>
<script>
function showReviewForm() {
    document.getElementById("reviewForm").style.display = "block";
}
</script>

<style type="text/css">
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

input {
   border: none !important;
}

h5 {
   width: 120px;
   align-content: center !important;
}
</style>
</head>
<body>
   <!-- 헤더 -->
   <jsp:include page="header.jsp" />
   <br>
   <!-- 예약 내역 상세보기 -->
   <div class="container text-center">
      <div class="row">
         <div class="card">
            <br>
            <div>
               <h2>예약 정보</h2>
            </div>
            <hr>
            <div class="row g-0">
               <div class="col-md-4">
                  <br>
                  <!-- 상품 썸네일 -->
                  <img src="${reservation.reservation_product_file_dir}"
                     class="rounded mx-auto d-block" alt="상품 사진"
                     style="margin-left: 10px;"> <br>
               </div>
               <div class="col-md-8">
                  <div class="card-body">
                     <!-- 상품명, 예약상태, 결제상태 -->
                     <div class="input-group mb-3"
                        style="display: flex; align-items: flex-end; height: 40px;">
                        <a
                           href="productDetail.do?product_num=${reservation.reservation_product_num}">
                           <h3>${reservation.reservation_product_name}</h3>
                        </a>
                        <div class="bd-hero-text" style="margin-right: 3px;">
                           <span>${reservation.reservation_status}</span>
                        </div>
                        <div class="bd-hero-text">
                           <span>${reservation.reservation_payment_status}</span>
                        </div>
                     </div>
                     <!-- 예약번호 -->
                     <div class="input-group mb-3">
                        <h5>예약번호</h5>
                        <input type="text" class="form-control"
                           value="${reservation.reservation_num}" readonly>
                     </div>
                     <span id="nameMsg"></span>
                     <!-- 예약일자 -->
                     <div class="input-group mb-3">
                        <h5>사용 예정일</h5>
                        <input type="text" class="form-control"
                           value="${reservation.reservation_registration_date}" readonly>
                     </div>
                     <span id="nicknameMsg"></span>
                     <!-- 예약자명 -->
                     <div class="input-group mb-3">
                        <h5>예약자명</h5>
                        <input type="text" class="form-control"
                           value="${reservation.reservation_member_name}" readonly>
                     </div>
                     <!-- 예약자 전화번호 -->
                     <div class="input-group mb-3">
                        <h5>전화번호</h5>
                        <input type="text" class="form-control"
                           value="${reservation.reservation_member_phone}" readonly>
                     </div>
                     <!-- 결제금액, 결제방법 -->
                     <div class="input-group mb-3">
                        <h5>결제금액</h5>
                        <input type="text" class="form-control"
                           value="${reservation.reservation_price}" readonly>
                        <div class="bd-hero-text"
                           style="margin-left: 3px; margin-top: 0.75rem;">
                           <span>${reservation.reservation_payment_method}</span>
                        </div>
                     </div>
                     <!-- 취소 버튼 표시 여부 -->
                     <c:if
                        test="${reservation.reservation_status == '예약완료' && reservation.reservation_registration_date > currentDate}">
                        <!-- 뒤로가기 버튼 -->
                        <button type="button"
                           onclick="location.href='myReservationListPage.do'"
                           class="btn btn-outline-secondary" style="width: 140px">뒤로가기</button>
                        <button type="button"
                           onclick="location.href='cancelReservation.do?reservation_num=${reservation.reservation_num}'"
                           class="btn btn-outline-secondary" style="width: 140px">취소하기</button>
                     </c:if>

                     <c:if
                        test="${reservation.reservation_status != '예약완료' || reservation.reservation_registration_date <= currentDate}">
                        <button type="button"
                           onclick="location.href='myReservationListPage.do'"
                           class="btn btn-outline-secondary" style="width: 140px">뒤로가기</button>
                        <span class="text-danger">취소할 수 없습니다.</span>
                     </c:if>
                     <!-- 리뷰 작성 버튼 (예약 날짜가 지났을 경우) -->
                     <c:if
                        test="${reservation.reservation_registration_date <= currentDate}">
                        <button type="button" onclick="showReviewForm()"
                           class="btn btn-primary" style="width: 140px">리뷰 작성</button>
                     </c:if>

                     <!-- 리뷰 작성 폼 -->
                     <div id="reviewForm" style="display: none; margin-top: 20px;">
                        <form action="writeReview.do" method="POST" class="comment-form">
                           <h4>리뷰 작성</h4>
                           <div class="star-rating">
                              <input type="radio" id="star5" name="rating" value="5" /> <label
                                 for="star5" title="5개 별"></label> <input type="radio"
                                 id="star4" name="rating" value="4" /> <label for="star4"
                                 title="4개 별"></label> <input type="radio" id="star3"
                                 name="rating" value="3" /> <label for="star3" title="3개 별"></label>
                              <input type="radio" id="star2" name="rating" value="2" /> <label
                                 for="star2" title="2개 별"></label> <input type="radio"
                                 id="star1" name="rating" value="1" /> <label for="star1"
                                 title="1개 별"></label>
                           </div>
                           <div class="row">
                              <div class="col-lg-12 text-center">
                                 <textarea id="content" name="review_content" rows="10"
                                    required placeholder="리뷰를 작성해주세요"></textarea>
                                 <input type="hidden" name="review_product_num"
                                    value="${product.product_num}" /> <input type="submit"
                                    value="리뷰작성" class="site-btn" />
                              </div>
                           </div>
                        </form>
                     </div>
                  </div>
               </div>
            </div>
         </div>
      </div>
   </div>
   <br>
   <br>
   <br>
   <!-- 푸터 연결 -->
   <c:import url="footer.jsp"></c:import>

</body>
</html>