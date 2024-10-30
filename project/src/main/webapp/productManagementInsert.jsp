<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>상품 등록 페이지</title>
<link rel="stylesheet" href="css/bootstrap.min.css" type="text/css">
<!-- jQuery 사용을 위한 연결 -->
<script src="https://code.jquery.com/jquery-3.7.1.min.js"
	integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo="
	crossorigin="anonymous"></script>
<!-- 주소 API를 사용하기 위한 연결 -->
<script
   src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<!-- 상품 사진 미리보기를 위한 연결 -->
<script src="js/product/productManagementFile.js"></script>

<style>
    .file-display img {
        border: 1px solid #ddd;
        border-radius: 4px;
        padding: 5px;
        margin-right: 10px;
        max-width: 100%;
    }
</style>

</head>
<body>
	<!-- 헤더 -->
	<jsp:include page="header.jsp" />
	<br>
	<!-- 내용 -->
	<div class="container text-center">
		<div class="row">
			<div class="col-2"></div>
			<div class="col-8 card" style="background-color: #ffffff">
				<br>
				<div>
					<h2>상품 등록</h2>
				</div>
				<hr>
				<!-- 상품 등록 form -->
				<form id="productForm" action="registerProduct.do" method="POST"
				enctype="multipart/form-data">
				<!-- 상품 사진 업로드 -->
				<div class="input-group mb-3">
					<input type="file" name="product_files" id="product_files"
						class="form-control" multiple accept="image/*"
						onchange="displayFileNames()" required>
				</div>
				<div class="file-display" id="fileList"></div>
				
				
					
					<!-- 상품 장소 선택 -->
					<div class="input-group mb-3">
						<select id="product_location" name="product_location"
							class="form-control" required>
							<option value="">상품 장소를 선택해주세요</option>
							<option value="sea">바다</option>
							<option value="freshwater">민물</option>
						</select>
					</div>

					<!-- 상품 유형 선택 -->
					<div class="input-group mb-3">
						<select id="product_category" name="product_category"
							class="form-control" required>
							<option value="">상품 유형을 선택해주세요</option>
							<option value="fishing_boat">낚시배</option>
							<option value="fishing_spot">낚시터</option>
							<option value="fishing_cafe">낚시카페</option>
							<option value="water">수상</option>
						</select>
					</div>

					<!-- 상품 명 입력 -->
					<div class="input-group mb-3">
						<input type="text" name="product_name" id="product_name"
							class="form-control" placeholder="상품 명을 입력해주세요." required>
					</div>

					<!-- 상품 가격 입력 -->
					<div class="input-group mb-3">
						<input type="number" name="product_price" id="product_price"
							class="form-control" placeholder="상품 가격을 입력해주세요." min="1"
							required>
					</div>
					<!-- 주소 입력 -->
					<!-- 우편 번호 -->
					<div class="input-group mb-3">
						<input type="text" name="postcode" id="postcode"
							class="form-control" placeholder="우편 번호" readonly required>
						<button class="btn btn-outline-secondary" type="button"
							id="addressBtn" style="width: 140px">주소 찾기</button>
					</div>
					<!-- 기본 주소 -->
					<div class="input-group mb-3">
						<input type="text" name="address" id="address"
							class="form-control" placeholder="주소" readonly required>
					</div>
					<!-- 추가 주소 -->
					<div class="input-group mb-3">
						<input type="text" name="extraAddress" id="extraAddress"
							class="form-control" placeholder="추가 주소" readonly required>
					</div>
					<!-- 상세 주소 -->
					<div class="input-group mb-3">
						<input type="text" name="detailAddress" id="detailAddress"
							class="form-control" placeholder="상세 주소" required>
					</div>

					<!-- 상품 설명 입력 -->
					<div class="input-group mb-3">
						<textarea id="product_details" name="product_details"
							class="form-control" rows="5" placeholder="상품 설명을 입력해주세요."
							required></textarea>
					</div>

					<!-- 제출 -->
					<div class="d-grid gap-2 col-6 mx-auto">
						<input type="submit" class="btn btn-primary" value="상품 등록"><br>
						<br>
					</div>
				</form>
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
