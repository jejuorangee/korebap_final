package com.korebap.app.view.async;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.korebap.app.biz.reply.ReplyDTO;
import com.korebap.app.biz.reply.ReplyService;

@RestController
public class UpdateReplyController {

    @Autowired
    private ReplyService replyService; // ReplyService를 의존성 주입

    @RequestMapping(value = "/updateReply.do",method = RequestMethod.POST)
    public @ResponseBody boolean updateReply(@RequestBody ReplyDTO replyDTO) {
        System.out.println("POST 요청 도착");
        
        // 데이터 로그
        System.out.println("reply_num : " + replyDTO.getReply_num());
        System.out.println("reply_content : " + replyDTO.getReply_content());

        // 데이터베이스 업데이트
        boolean flag = replyService.update(replyDTO);

        // 응답 반환
        if (flag) { // 성공
            System.out.println("성공");
        }
        else { // 실패
            System.out.println("실패");
        }
        return flag;
    }
}
