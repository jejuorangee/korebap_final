<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<script src="js/jquery-3.3.1.min.js"></script>

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
<title>고래밥 심심이</title>
<style>
    .message {
        border-top: 1px solid #ccc;
        padding: 10px;
        margin-top: 5px;
        background-color: #e6e6e6;
    }
    #chat-container {
        width: 400px;
        height: 600px;
        display: flex;
        flex-direction: column;
        border: 1px solid #ccc;
    }
    #chat-messages {
        flex: 1;
        overflow-y: auto;
        padding: 10px;
        display: flex;
        flex-direction: column-reverse;
    }
    #user-input {
        display: flex;
        padding: 10px;

    }
    #user-input input {
        flex: 1;
        padding: 10px;
        outline: none;
    }
    #user-input button {
        border: none;
        background-color: #1e88e5;
        color: white;
        padding: 10px 15px;
        cursor: pointer;
    }
</style>
</head>
<body>
<c:import url="header.jsp"></c:import>

<div class="container">
	<div class="row simsimeHeaderRow">
		<div class="col-lg-10 offset-lg-1">
			<div class="bd-hero-text">
				<h1>⚓낚시 천재 고심이⚓</h1>
			</div>
		</div>
	</div>
	<div class="row simsimeRow">
		<div class="col-lg-10 offset-lg-1">
			<div class="bd-hero-text">
				<h3>천재 낚시꾼 고심이(고래밥 심심이)에게 낚시 꿀팁을 물어보세요.</h3>
			</div>
		</div>
	</div>
	<div id="chat-container" class="container simsimeContainer">
	    <div id="chat-messages"></div>
	    <div id="user-input">
	        <input type="text" placeholder="메시지를 입력하세요..." />
	        <button>전송</button>
	    </div>
	</div>
</div>

<c:import url="footer.jsp"></c:import>

<script src="js/chatbot.js"></script>
</body>
</html>





























