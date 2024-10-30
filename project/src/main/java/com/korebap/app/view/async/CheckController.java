package com.korebap.app.view.async;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.korebap.app.biz.member.MemberDTO;
import com.korebap.app.biz.member.MemberService;

import jakarta.servlet.http.HttpSession;

@RestController
public class CheckController {
    @Autowired
    private MemberService memberService;

    @PostMapping("/checkMemberId.do")
    public @ResponseBody String checkMemberId(@RequestBody MemberDTO memberDTO) {
        System.out.println("---------------비동기 처리(checkMemberId) 시작---------------");

        memberDTO.setMember_condition("CHECK_MEMBER_ID");
        memberDTO = memberService.selectOne(memberDTO);

        String result = "false";
        if (memberDTO != null && memberDTO.getMember_id() != null) {
            result = "true"; // ID가 존재하면 true
        }
        
        System.out.println("---------------비동기 처리(checkMemberId) 종료---------------");
        return result;
    }

    @PostMapping("/checkNickName.do")
    public @ResponseBody String checkNickName(@RequestBody MemberDTO memberDTO) {
        System.out.println("---------------비동기 처리(checkNickName) 시작---------------");

        memberDTO.setMember_condition("CHECK_MEMBER_NICKNAME");
        memberDTO = memberService.selectOne(memberDTO);

        String result = "false";
        if (memberDTO != null && memberDTO.getMember_nickname() != null) {
            result = "true"; // 닉네임이 존재하면 true
        }
        
        System.out.println("---------------비동기 처리(checkNickName) 종료---------------");
        return result;
    }

    @PostMapping("/checkPassword.do")
    public @ResponseBody String checkPassword(@RequestBody MemberDTO memberDTO, HttpSession session) {
        System.out.println("---------------비동기 처리(checkPassword) 시작---------------");

        String memberId = (String) session.getAttribute("member_id");
        memberDTO.setMember_id(memberId);
        memberDTO.setMember_condition("MEMBER_PASSWORD_GET");

        memberDTO = memberService.selectOne(memberDTO);
        String result = "false";

        if (memberDTO != null && memberDTO.getMember_password().equals(memberDTO.getMember_password())) {
            result = "true"; // 비밀번호가 일치하면 true
        }
        
        System.out.println("---------------비동기 처리(checkPassword) 종료---------------");
        return result;
    }
}
