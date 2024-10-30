<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<link rel="stylesheet" href="css/style.css" type="text/css">


<c:if test="${not empty member_id}">
	<c:if test="${member_role eq 'USER'}">
		<a href="mypagePage.do" class="bk-btn">마이페이지</a>
		<a href="logout.do" class="bk-btn">로그아웃</a>
	</c:if>
</c:if>

<c:if test="${empty member_id}">
	<a href="joinPage.do" class="bk-btn">회원가입</a>
	<a href="loginPage.do" class="bk-btn"> 로그인 </a>
	<br>
</c:if>
<c:if test="${not empty member_id}">
	<c:if test="${member_role == 'OWNER'}">
		<a href="productManagement.do">상품 관리</a>
		<a href="reservationManagement.do">예약 관리</a>
		<br>
	</c:if>
	<a href="mypagePage.do" class="bk-btn">마이페이지</a>
	<a href="logout.do" class="bk-btn">로그아웃</a>
	<br>
</c:if>
