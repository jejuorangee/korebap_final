<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>상품 수정 페이지</title>
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
					<h2>상품 수정</h2>
				</div>
				<hr>
				<!-- 상품 수정 form -->
				<form id="productForm" action="updateProduct.do" method="POST"
				enctype="multipart/form-data">
				<!-- 기존 상품 데이터 표시 -->
				<input type="hidden" name="product_num" value="${product.product_num}" />

				<!-- 상품 사진 업로드 -->
				<div class="input-group mb-3">
					<input type="file" name="product_files" id="product_files"
						class="form-control" multiple accept="image/*"
						onchange="displayFileNames()">
				</div>
				<div class="file-display" id="fileList">
					<c:choose>
						<c:when test="${not empty product.product_file_dir}">
							<img src="img/profile/${product.product_file_dir}" alt="상품 사진 미리보기">
						</c:when>
						<c:otherwise>
							<img src="img/default.jpg" alt="기본 상품 사진">
						</c:otherwise>
					</c:choose>
				</div>
				
				<!-- 상품 장소 선택 -->
				<div class="input-group mb-3">
					<select id="product_location" name="product_location"
						class="form-control" required>
						<option value="sea" ${product.product_location == 'sea' ? 'selected' : ''}>바다</option>
						<option value="freshwater" ${product.product_location == 'freshwater' ? 'selected' : ''}>민물</option>
					</select>
				</div>

				<!-- 상품 유형 선택 -->
				<div class="input-group mb-3">
					<select id="product_category" name="product_category"
						class="form-control" required>
						<option value="fishing_boat" ${product.product_category == 'fishing_boat' ? 'selected' : ''}>낚시배</option>
						<option value="fishing_spot" ${product.product_category == 'fishing_spot' ? 'selected' : ''}>낚시터</option>
						<option value="fishing_cafe" ${product.product_category == 'fishing_cafe' ? 'selected' : ''}>낚시카페</option>
						<option value="water" ${product.product_category == 'water' ? 'selected' : ''}>수상</option>
					</select>
				</div>

				<!-- 상품 명 입력 -->
				<div class="input-group mb-3">
					<input type="text" name="product_name" id="product_name"
						class="form-control" value="${product.product_name}" required>
				</div>

				<!-- 상품 가격 입력 -->
				<div class="input-group mb-3">
					<input type="number" name="product_price" id="product_price"
						class="form-control" value="${product.product_price}" min="0"
						required>
				</div>

				<!-- 주소 입력 -->
				<!-- 우편 번호 -->
				<div class="input-group mb-3">
					<input type="text" name="postcode" id="postcode"
						class="form-control" value="${product.product_postcode}" readonly required>
					<button class="btn btn-outline-secondary" type="button"
						id="addressBtn" style="width: 140px">주소 찾기</button>
				</div>

				<!-- 기본 주소 -->
				<div class="input-group mb-3">
					<input type="text" name="address" id="address"
						class="form-control" value="${product.product_address}" readonly required>
				</div>

				<!-- 추가 주소 -->
				<div class="input-group mb-3">
					<input type="text" name="extraAddress" id="extraAddress"
						class="form-control" value="${product.product_extraAddress}" readonly>
				</div>

				<!-- 상세 주소 -->
				<div class="input-group mb-3">
					<input type="text" name="detailAddress" id="detailAddress"
						class="form-control" value="${product.product_detailAddress}" required>
				</div>

				<!-- 상품 설명 입력 -->
				<div class="input-group mb-3">
					<textarea id="product_details" name="product_details"
						class="form-control" rows="5" required>${product.product_details}</textarea>
				</div>

				<!-- 제출 -->
				<div class="d-grid gap-2 col-6 mx-auto">
					<input type="submit" class="btn btn-primary" value="상품 수정"><br><br>
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
	
		<script>
		// 주소 API 동작 확인
		$('#addressBtn').on('click', function() {
			new daum.Postcode({
				oncomplete: function(data) {
					$('#postcode').val(data.zonecode);
					$('#address').val(data.address);
					$('#extraAddress').val(data.buildingName);
				}
			}).open();
		});

		// 파일 미리보기
		function displayFileNames() {
			const fileList = document.getElementById('product_files').files;
			const fileDisplay = document.getElementById('fileList');
			fileDisplay.innerHTML = ''; // 기존 파일 목록 초기화
			for (let i = 0; i < fileList.length; i++) {
				let img = document.createElement('img');
				img.src = URL.createObjectURL(fileList[i]);
				img.onload = function() {
					URL.revokeObjectURL(this.src); // 메모리 해제
				}
				fileDisplay.appendChild(img);
			}
		}
	</script>
</body>
</html>
