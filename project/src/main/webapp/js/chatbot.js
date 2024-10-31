$(document).ready(function() {
    const chatMessages = $('#chat-messages');
    const userInput = $('#user-input input');
    const sendButton = $('#user-input button');
    
    let isFetching = false; // 요청 진행 상태 관리

    function addMessage(sender, message) {
        const messageElement = $('<div class="message"></div>').text(`${sender}: ${message}`);
        chatMessages.prepend(messageElement);
    }

    function fetchAIResponse(prompt) {
		console.log(prompt);
        return $.ajax({
            url: 'goOpenAI.do', // 서버 컨트롤러의 URL로 변경
            method: 'POST',
            contentType: 'application/json; charset=UTF-8',
            data: JSON.stringify({message: prompt}), // 사용자 메시지를 JSON 형식으로 전송
			dataType: 'json'
		});
    }

    sendButton.on('click', function() {
        const message = userInput.val().trim();
        if (message.length === 0 || isFetching) return;

        addMessage('나', message);
        userInput.val(''); // 입력 필드 비우기

        isFetching = true; // 요청 중
        sendButton.prop('disabled', true); // 버튼 비활성화

        fetchAIResponse(message)
            .done(function(data) {
				console.log('서버응답 : '+data);
				console.log(typeof data);
                const aiResponse = data || '응답이 없습니다.'; // 서버에서 반환된 응답을 사용
                addMessage('고심이', aiResponse);
            })
            .fail(function(jqXHR) {
                console.error('API 오류:', jqXHR.responseJSON);
                addMessage('고심이', 'API 호출 중 오류가 발생했습니다.');
            })
            .always(function() {
                isFetching = false; // 요청 완료
                sendButton.prop('disabled', false); // 버튼 재활성화
            });
    });

    userInput.on('keydown', function(event) {
        if (event.key === 'Enter') {
            sendButton.click();
        }
    });
});