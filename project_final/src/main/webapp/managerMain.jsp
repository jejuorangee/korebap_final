<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>관리자 메인</title>
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

<!-- chart.js -->
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
</head>
<body>
<c:import url="header.jsp"></c:import>
<div class="container">
	<div id="page-wrapper">
		<div class="header"> 
			<h1 class="page-header">고래밥 현황</h1>
		</div>
		<div class="dashboard-cards mt-5"> 
			<div class="row">
				<div class="col-xs-12 col-sm-6 col-md-3">
					<a href="memberList.do" class="manager-main-a">
						<div class="manager-card horizontal cardIcon waves-effect waves-dark ">
							<div class="card-image red">
								<span class="material-symbols-outlined manager-main-icon">person</span>
							</div>
							<div class="card-stacked red">
								<div class="card-content">
									<h3 id="userTotal">84,198</h3> 
								</div>
								<div class="card-action">
									<strong>사용자</strong>
								</div>
							</div>
						</div>
					</a>
				</div>
				
				<div class="col-xs-12 col-sm-6 col-md-3">
					<a href="bossList.do" class="manager-main-a">
						<div class="manager-card horizontal cardIcon waves-effect waves-dark">
							<div class="card-image orange">
								<span class="material-symbols-outlined manager-main-icon">sailing</span>
							</div>
							<div class="card-stacked orange">
								<div class="card-content">
									<h3 id="bossTotal">36,540</h3> 
								</div>
								<div class="card-action">
									<strong>사장님 사용자</strong>
								</div>
							</div>
						</div>
					</a> 
				</div>
				
				<div class="col-xs-12 col-sm-6 col-md-3">
					<a href="boardList.do" class="manager-main-a">
						<div class="manager-card horizontal cardIcon waves-effect waves-dark">
							<div class="card-image green">
								<span class="material-symbols-outlined manager-main-icon">library_books</span>
							</div>
							<div class="card-stacked green">
								<div class="card-content">
									<h3 id="boardTotal">88,658</h3> 
								</div>
								<div class="card-action">
									<strong>게시판</strong>
								</div>
							</div>
						</div>
					</a>
				</div>
				
				<div class="col-xs-12 col-sm-6 col-md-3">
					<a href="productList.do" class="manager-main-a">
						<div class="manager-card horizontal cardIcon waves-effect waves-dark">
							<div class="card-image blue">
								<span class="material-symbols-outlined manager-main-icon">set_meal</span>
							</div>
							<div class="card-stacked blue">
								<div class="card-content">
									<h3 id="productTotal">24,225</h3> 
								</div>
								<div class="card-action">
									<strong>상품</strong>
								</div>
							</div>
						</div>
					</a>
				</div>
			</div>
		</div>
		<div class="container mt-5">
			<div class="row">
				<div class="col-xs-12 col-sm-12 col-md-7"> 
					<div class="cirStats">
	  					<div class="row">
							<div class="col-xs-12 col-sm-6 col-md-6"> 
								<div class="card-panel text-center manager-main-chart-div">
									<h4>사용자</h4>
									<div class="easypiechart" id="easypiechart-red" data-percent="82">
										<span class="percent">82%</span>
										<canvas id="userChart" class="chartCanvas" height="110" width="110"></canvas>
									</div> 
								</div>
							</div>
							<div class="col-xs-12 col-sm-6 col-md-6"> 
								<div class="card-panel text-center manager-main-chart-div">
									<h4>사장님 사용자</h4>
									<div class="easypiechart" id="easypiechart-orange" data-percent="46">
										<span class="percent">46%</span>
										<canvas id="bossChart" class="chartCanvas" height="110" width="110"></canvas>
									</div>
								</div>
							</div>
							<div class="col-xs-12 col-sm-6 col-md-6 mt-5"> 
								<div class="card-panel text-center manager-main-chart-div">
									<h4>게시판</h4>
									<div class="easypiechart" id="easypiechart-teal" data-percent="55">
										<span class="percent">55%</span>
										<canvas id="boardChart" class="chartCanvas" height="110" width="110"></canvas>
									</div>
								</div>
							</div> 
							<div class="col-xs-12 col-sm-6 col-md-6 mt-5"> 
								<div class="card-panel text-center manager-main-chart-div">
									<h4>상품</h4>
									<div class="easypiechart" id="easypiechart-blue" data-percent="84">
										<span class="percent">84%</span>
										<canvas id="productChart" class="chartCanvas" height="110" width="110"></canvas>
									</div> 
								</div>
							</div> 
						</div>
					</div>
				</div>							
				<div class="col-xs-12 col-sm-12 col-md-5" style="align-content:center"> 
				     <div class="row">
							<div class="col-xs-12"> 
							<div class="card manager-main-card manager-main-chart-div">
								<div class="card-image donutpad">
								  <h3 style="text-align:center">카테고리별 상품 수</h3>
								  <div id="morris-donut-chart">
								  	<canvas id="donutChart" class="chartCanvas" width="440" height="550"></canvas>
								  </div>
								</div> 
								<!-- <div class="card-action">
								  <b>카테고리별 상품 수</b>
								</div> -->
							</div>	
						</div>
					 </div>
				</div>
			</div>
		</div>
		<div class="container mt-5 mb-5">
            <div class="row">
                <div class="col-md-12">
                    <!-- Advanced Tables -->
                    <div class="card manager-main-card">
                        <div class="card-action"></div>
                        <div class="card-content">
                            <div class="table-responsive">
                                <table class="table table-striped table-bordered table-hover" id="dataTables-example">
                                    <thead>
                                        <tr>
                                            <th>신고한 아이디</th>
                                            <th>신고 내용</th>
                                            <th>신고 컨텐츠</th>
                                            <th>신고 받은 아이디</th>
                                            <th>승인 / 반려</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                   		<c:if test="${empty claimList}">
				                			<tr><td colspan=5>처리되지 않은 신고가 없습니다.</td></tr>
				                		</c:if>
				                		<c:forEach var="claim" items="${claimList}">
	                                        <tr>
					                        	<td>${claim.claim_reporter_id}</td>
					                        	<td>${claim.claim_category}</td>
												<c:if test="${claim.claim_board_num != null}">
													<td><a href="boardDetail.do?board_num=${claim.claim_board_num}">게시글 ${claim.claim_board_num}</a></td>
												</c:if>
												<c:if test="${claim.claim_reply_num != null}">
													<td><a href="boardDetail.do?board_num=${claim.claim_reply_board_num}">댓글 ${claim.claim_reply_num}</a></td>
												</c:if>
						                        <td class="center">${claim.claim_target_member_id}</td>
						                        <td class="center">
						                        	<div class="d-flex justify-content-between">
						                        		<a class="btn btn-primary disabled" role="button" aria-disabled="true">승인</a>
						                        		<a class="btn btn-primary disabled" role="button" aria-disabled="true">반려</a>
					                        		</div>
				                        		</td>
	                                       	</tr>
                                       	</c:forEach>
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
            </div>
		</div>
	</div>
</div>
<c:import url="footer.jsp"></c:import>
</body>

<script type="text/javascript">
// HTML에서 값을 가져와 변수로 설정
var userTotal = parseInt(document.getElementById('userTotal').innerText);
var bossTotal = parseInt(document.getElementById('bossTotal').innerText);
var productTotal = parseInt(document.getElementById('productTotal').innerText);
var boardTotal = parseInt(document.getElementById('boardTotal').innerText);

// 사용자와 사장님의 합 계산
var userBossTotal = userTotal + bossTotal;

// 상품과 게시판의 합 계산
var productBoardTotal = productTotal + boardTotal;

//Chart.js로 도넛 차트 구현
var ctx = document.getElementById('donutChart').getContext('2d');
var totalSeaProducts = 80; // 바다의 총 상품 수
var totalFreshwaterProducts = 60; // 민물의 총 상품 수

var seaFishingBoat = 30; // 바다 - 낚시배의 상품 수
var seaFishingSpot = 50; // 바다 - 낚시터의 상품 수
var freshwaterFishingSpot = 20; // 민물 - 낚시터의 상품 수
var freshwaterFishingCafe = 40; // 민물 - 낚시카페의 상품 수

var donutChart = new Chart(ctx, {
    type: 'doughnut',
    data: {
        labels: [
            '바다 - 총 상품', 
            '바다 - 낚시배', 
            '바다 - 낚시터', 
            '민물 - 총 상품', 
            '민물 - 낚시터', 
            '민물 - 낚시카페'
        ],
        datasets: [{
            label: '카테고리 별 상품 수',
            data: [
                totalSeaProducts,  // 바다 - 총 상품
                seaFishingBoat,  // 바다 - 낚시배
                seaFishingSpot,  // 바다 - 낚시터
                totalFreshwaterProducts,  // 민물 - 총 상품
                freshwaterFishingSpot,  // 민물 - 낚시터
                freshwaterFishingCafe  // 민물 - 낚시카페
            ],
            backgroundColor: [
                '#FF6384',  // 바다 - 총 상품 색상
                '#36A2EB',  // 바다 - 낚시배 색상
                '#FFCE56',  // 바다 - 낚시터 색상
                '#4BC0C0',  // 민물 - 총 상품 색상
                '#9966FF',  // 민물 - 낚시터 색상
                '#FF9F40'   // 민물 - 낚시카페 색상
            ],
            hoverBackgroundColor: [
                '#FF6384', '#36A2EB', '#FFCE56', 
                '#4BC0C0', '#9966FF', '#FF9F40'
            ]
        }]
    },
    options: {
        responsive: true,
        maintainAspectRatio: false,
        plugins: {
            tooltip: {
                callbacks: {
                    label: function(context) {
                        let label = context.label || '';
                        let value = context.raw || 0;
                        return `${label}: ${value}개`;
                    }
                }
            }
        }
    }
});


// 각 데이터에 맞는 차트 생성
var userCtx = document.getElementById('userChart').getContext('2d');
var userChart = new Chart(userCtx, {
    type: 'doughnut',
    data: {
        labels: ['일반 사용자', '전체 사용자 비율'],
        datasets: [{
            label: '사용자',
            data: [userTotal, userBossTotal - userTotal],  // 사용자와 사장님 합에서 사용자 데이터
            backgroundColor: ['#FF6384', '#E0E0E0'],
            hoverBackgroundColor: ['#FF6384', '#E0E0E0']
        }]
    }
});

var bossCtx = document.getElementById('bossChart').getContext('2d');
var bossChart = new Chart(bossCtx, {
    type: 'doughnut',
    data: {
        labels: ['사장님 사용자', '전체 사용자 비율'],
        datasets: [{
            label: '사장님 사용자',
            data: [bossTotal, userBossTotal - bossTotal],  // 사용자와 사장님 합에서 사장님 데이터
            backgroundColor: ['#FFCE56', '#E0E0E0'],
            hoverBackgroundColor: ['#FFCE56', '#E0E0E0']
        }]
    }
});

var productCtx = document.getElementById('productChart').getContext('2d');
var productChart = new Chart(productCtx, {
    type: 'doughnut',
    data: {
        labels: ['상품', '남은 비율'],
        datasets: [{
            label: '상품',
            data: [productTotal, productBoardTotal - productTotal],  // 상품과 게시판 합에서 상품 데이터
            backgroundColor: ['#36A2EB', '#E0E0E0'],
            hoverBackgroundColor: ['#36A2EB', '#E0E0E0']
        }]
    }
});

var boardCtx = document.getElementById('boardChart').getContext('2d');
var boardChart = new Chart(boardCtx, {
    type: 'doughnut',
    data: {
        labels: ['게시판', '남은 비율'],
        datasets: [{
            label: '게시판',
            data: [boardTotal, productBoardTotal - boardTotal],  // 상품과 게시판 합에서 게시판 데이터
            backgroundColor: ['#4BC0C0', '#E0E0E0'],
            hoverBackgroundColor: ['#4BC0C0', '#E0E0E0']
        }]
    }
});
</script>

</html>