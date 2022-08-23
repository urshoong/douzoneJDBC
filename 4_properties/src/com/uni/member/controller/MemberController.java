package com.uni.member.controller;

import java.util.ArrayList;
import java.util.List;

import com.uni.member.model.dto.Member;
import com.uni.member.service.MemberService;
import com.uni.member.view.MemberMenu;

public class MemberController {
	private MemberService memberService = new MemberService(); 

	public void selectAll() {
		MemberMenu menu = new MemberMenu();
		
		ArrayList<Member> list = memberService.selectAll();
		
		if(!list.isEmpty()) {
			menu.displayMemberMenu(list);
		}else {
			menu.displayError("해당되는 데이터가 없습니다.");
		}
	}

	public void selectOne(String inputMemberId) {
		MemberMenu menu = new MemberMenu();
		Member m = memberService.selectOne(inputMemberId);
		if(m != null) {
			menu.displayMember(m);
		}else {
			menu.displayError(inputMemberId + "해당되는 데이터가 없습니다.");
		}
	}

	public void selectByName(String inputMemberName) {
		MemberMenu menu = new MemberMenu();
		List<Member> list = memberService.selectByName(inputMemberName);
		
		if(!list.isEmpty()) {
			menu.displayMemberMenu(list);
		}else {
			menu.displayError("해당되는 데이터가 없습니다.");
		}
	}

	public void insertMember(Member inputMember) {
		int result = memberService.insertMember(inputMember);
		if(result > 0) {
			new MemberMenu().displaySuccess("회원가입성공");
		}else {
			new MemberMenu().displayError("회원가입실패");
		}
	}

	public void updateMember(Member updateMember) {
		int result = memberService.updateMember(updateMember);
		if(result > 0) {
			new MemberMenu().displaySuccess("회원수정성공");
		}else {
			new MemberMenu().displayError("회원수정실패");
		}
	}

	public void deleteMember(String inputMemberId) {
		int result = memberService.deleteMember(inputMemberId);
		if(result > 0) {
			new MemberMenu().displaySuccess("회원삭제성공");
		}else {
			new MemberMenu().displayError("회원삭제실패");
		}
	}

	public void exitProgram() {
		memberService.exitProgram();
	}

}
