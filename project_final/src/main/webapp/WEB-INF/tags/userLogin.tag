<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

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
                              <!-- 사용자 레벨에 따른 등급 -->
                              <c:if test="${empty member_id}">
                                 <c:import url="role.jsp"></c:import>
                              </c:if>
                              ${member.member_nickname}
                           </li>
                       </ul>
                   </div>
                   <div class="col-lg-6">
                       <div class="tn-right">
                           <div class="top-social">
                               <!-- 소셜 미디어 링크 -->
                               <a href="https://koreaisacademy.com/"><i class="fa fa-facebook"></i></a> 
                               <a href="https://koreaisacademy.com/"><i class="fa fa-twitter"></i></a>
                               <a href="https://koreaisacademy.com/"><i class="fa fa-instagram"></i></a>
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
                                   <li><a href="productListPage.do?product_searchContent=null&product_searchKeyword=null">낚시장소</a>
                                       <ul class="dropdown">
                                           <!-- 드롭다운 메뉴 항목 -->
                                           <li><a href="productListPage.do?product_location=바다">바다</a></li>
                                           <li><a href="productListPage.do?product_location=민물">민물</a></li>
                                       </ul>
                                   </li>
                                   <li><a href="productListPage.do?product_searchContent=null&searchKeyword=null">낚시유형</a>
                                       <ul class="dropdown">
                                           <li><a href="productListPage.do?product_category=낚시터">낚시터</a></li>
                                           <li><a href="productListPage.do?product_category=낚시배">낚시배</a></li>
                                       </ul>
                                   </li>
                                   <li><a>마이 메뉴</a>
                                  <ul class="dropdown">
                                      <li><a href="myReservationListPage.do">내 예약</a></li>
                                      <li><a href="wishListPage.do">위시리스트</a></li>
                                  </ul>
                              </li>
                                   <li><a href="boardListPage.do">게시판</a>
                                       <ul class="dropdown">
                                           <li><a href="boardListPage.do">자유 게시판</a></li>
                                       </ul>
                                   </li>
                                   <li>
                                       <!-- 검색창 포함 -->
                                       <div class="search-container">
                                           <div class="search-box">
                                               <!-- 검색폼 -->
                                               <form action="productListPage.do">
                                                   <input type="text" class="search-input" name="searchKeyword" placeholder="상품검색...">
                                                   <button class="search-button" type="submit">검색</button>
                                               </form>
                                           </div>
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