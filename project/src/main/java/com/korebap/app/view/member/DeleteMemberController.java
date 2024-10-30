package com.korebap.app.view.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.korebap.app.biz.member.MemberDTO;
import com.korebap.app.biz.member.MemberService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class DeleteMemberController {

    @Autowired
    private MemberService memberService; // 서비스 의존성 주입

    @RequestMapping(value = "/deleteMember.do",method = RequestMethod.POST)
    public String deleteMember(HttpSession session) {
    	System.out.println("************************************************************com.korebap.app.view.member.DeleteMemberController_deleteMember_POST 시작************************************************************");

        String login_member_id = (String) session.getAttribute("member_id");

        // 로그인 세션 확인
        if (login_member_id == null || login_member_id.isEmpty()) {
            System.out.println("로그인 세션 없음");
            return "main";
        }

        // 데이터 로그
        System.out.println("member_id : " + login_member_id);

        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setMember_id(login_member_id);

        // 회원 탈퇴 처리
        boolean flag = memberService.delete(memberDTO); // MemberService의 delete 메서드 호출

        if (flag) {
            System.out.println("DeleteMemberController 로그 : 회원탈퇴 성공");
            session.removeAttribute("member_id");
            session.removeAttribute("role");
            System.out.println("************************************************************com.korebap.app.view.member.DeleteMemberController_deleteMember_POST 종료************************************************************");
            return "main";
        } else {
            System.out.println("DeleteMemberController 로그 : 회원탈퇴 실패");
            System.out.println("************************************************************com.korebap.app.view.member.DeleteMemberController_deleteMember_POST 종료************************************************************");
            return "main";
        }
    }
}
