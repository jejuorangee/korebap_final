<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
    
    <style type="text/css">
        /* 공통 버튼 스타일 */
        #likeBtn, .likeButton, #editButton, #reportButton {
            background-color: #87CEEB; /* 하늘색 */
            border: none;
            color: white;
            padding: 10px 20px;
            text-align: center;
            text-decoration: none;
            display: inline-block;
            font-size: 16px;
            margin: 5px;
            cursor: pointer;
            border-radius: 5px;
            transition: background-color 0.3s ease, transform 0.2s ease;
        }

        #likeBtn:hover, #editButton:hover, #reportButton:hover {
            background-color: #00BFFF; /* 더 밝은 하늘색 */
            transform: scale(1.05);
        }

        .icon_thumb_up, .icon_warning {
            margin-right: 5px;
        }
    </style>
</head>

<body>
    <!-- 헤더 연결 -->
    <c:import url="header.jsp"></c:import>

    <!-- 글 상세페이지 글정보섹션 시작 -->
    <section class="blog-details-hero set-bg" data-setbg="img/board/boardBasic.png">
        <div class="container">
            <div class="row">
                <div class="col-lg-10 offset-lg-1">
                    <div class="bd-hero-text">
                        <span>자유 게시판</span>
                        <h2>${board.board_title}</h2>
                        <ul>
                            <li class="b-time"><i class="icon_clock_alt"></i>${board.board_registration_date}</li>
                            <li><i class="icon_profile"></i> 
                                <c:if test="${not empty member_id}">
                                    <c:import url="role.jsp"></c:import>
                                </c:if> 
                                ${board.board_writer_id}
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </section>
    <!-- 글 상세페이지 글정보섹션 종료 -->

    <!-- 글 상세페이지 파일출력섹션 시작 -->
    <section class="testimonial-section spad">
        <div class="container">
            <div class="row">
                <c:if test="${empty fileList}">
                    <div>사진 없음</div>
                </c:if>
                <c:if test="${not empty fileList}">
                    <div class="col-lg-8 offset-lg-2">
                        <div class="testimonial-slider owl-carousel">
                            <c:forEach var="file" items="${fileList}">
                                <div class="ts-item">
                                    <div class="bd-pic">
                                        <img src="${file.file_dir}" alt="게시글에서 사용자가 입력한 사진파일입니다.">
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </c:if>
            </div>
        </div>
    </section>
    <!-- 글 상세페이지 파일출력섹션 종료 -->

    <!-- 글 상세페이지 메인섹션 시작 -->
    <section class="blog-details-section">
        <div class="container">
            <div class="row">
                <div class="col-lg-10 offset-lg-1">
                    <div class="blog-details-text">
                        <div class="bd-title">
                            <p>${board.board_content}</p>
                        </div>
                        <div class="tag-share">
                            <div class="tags">
                                <div class="button-container">
                                    <button id="likeBtn" class="likeButton"
                                        data-post-id="${board.board_num}"
                                        style="background-color: ${like_member == 'true' ? 'red' : '#87CEEB'};">
                                        👍 좋아요 (<span id="likeCount">${board.board_like_cnt}</span>)
                                    </button>
                                    <button id="claimBtn" class="likeButton" onclick="location.href='insertClaim.do?board_num=${board.board_num}';">🚨 신고</button>
                                    <input id="like_member" value="${member_id}" style="visibility: hidden;">
                                    <c:if test="${member_id == board.board_writer_id}">
                                        <button class="editButton likeButton" data-board-num="${board.board_num}">❌삭제</button>
                                    </c:if>
                                </div>
                            </div>
                        </div>
                        <div class="comment-option">
                            <h4>댓글</h4>
                            <c:if test="${empty replyList}">
                                <p>작성된 댓글이 없습니다.</p>
                            </c:if>
                            <c:forEach var="reply" items="${replyList}">
                                <div class="single-comment-item">
                                    <div class="sc-author">
                                        <img src="img/profile/${reply.reply_member_profile}" alt="댓글 작성자 프로필 이미지">
                                    </div>
                                    <div class="sc-text">
                                        <span>${reply.reply_registration_date}</span>
                                        <h5 id="reply${reply.reply_num}" tabindex="-1">${reply.reply_content}</h5>
                                        <p>${reply.reply_writer_id}</p>
                                        <c:if test="${member_id == reply.reply_writer_id}">
                                            <a class="comment-btn" data-board-num="${board.board_num}" data-reply-num="${reply.reply_num}">댓글삭제</a>
                                        </c:if>
                                        <button id="claimBtn" class="likeButton" onclick="location.href='insertClaim.do?reply_num=${reply.reply_num}';">🚨 신고</button>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                        <div class="leave-comment">
                            <h4>댓글 작성</h4>
                            <form action="writeReply.do" class="comment-form">
                                <div class="row">
                                    <div class="col-lg-12 text-center">
                                        <input type="hidden" name="board_num" value="${board.board_num}" />
                                        <textarea name="reply_content" placeholder="댓글 내용"></textarea>
                                        <button type="submit" class="site-btn">댓글 작성</button>
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

    <!-- 추천 게시판섹션 시작 -->
    <section class="recommend-blog-section spad">
        <div class="container">
            <div class="row">
                <div class="col-lg-12">
                    <div class="section-title">
                        <h2>추천 게시글</h2>
                    </div>
                </div>
            </div>
            <div class="row">
                <c:forEach var="board" items="${boardlist}" begin="1" end="3" step="1">
                    <div class="col-md-4">
                        <mytag:smallBoard />
                    </div>
                </c:forEach>
            </div>
        </div>
    </section>
    <!-- 추천 게시판섹션 종료 -->

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
    <script src="js/starPlugin.js"></script>
    <script>src="js/board/replyFocus.js"</script>

    <script>
        $(document).ready(function() {
            // 좋아요 버튼 클릭 이벤트
            $('#likeBtn').on('click', function() {
                const postId = $(this).data('post-id');
                const isLiked = $(this).css('background-color') === 'rgb(255, 0, 0)'; // red color

                // AJAX 요청
                $.ajax({
                    type: 'POST',
                    url: 'like.do',
                    data: { postId: postId, isLiked: isLiked },
                    success: function(response) {
                        $('#likeCount').text(response.newLikeCount);
                        $('#likeBtn').css('background-color', isLiked ? '#87CEEB' : 'red');
                    },
                    error: function(xhr, status, error) {
                        console.error('AJAX error: ' + status + ' - ' + error);
                    }
                });
            });

            // 댓글 삭제 이벤트
            $('.comment-btn').on('click', function(e) {
                e.preventDefault();
                const boardNum = $(this).data('board-num');
                const replyNum = $(this).data('reply-num');

                // AJAX 요청
                $.ajax({
                    type: 'POST',
                    url: 'deleteReply.do',
                    data: { boardNum: boardNum, replyNum: replyNum },
                    success: function(response) {
                        // 댓글 삭제 성공 시, 해당 댓글 제거
                        if (response.success) {
                            $(this).closest('.single-comment-item').remove();
                        }
                    }.bind(this),
                    error: function(xhr, status, error) {
                        console.error('AJAX error: ' + status + ' - ' + error);
                    }
                });
            });
        });
    </script>
</body>
</html>
