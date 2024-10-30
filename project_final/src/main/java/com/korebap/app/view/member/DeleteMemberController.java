package com.korebap.app.view.member;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DeleteMemberController {
	@RequestMapping("/deleteMember")
	public String deleteMember() {
		
		return"";
	}
}
