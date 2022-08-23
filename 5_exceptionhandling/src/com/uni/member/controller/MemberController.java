package com.uni.member.controller;

import java.util.ArrayList;
import java.util.List;

import com.uni.common.exception.MemberException;
import com.uni.member.model.dto.Member;
import com.uni.member.service.MemberService;
import com.uni.member.view.MemberMenu;

public class MemberController {
	private MemberService memberService = new MemberService(); 

	public void selectAll() {
		MemberMenu menu = new MemberMenu();
		
		ArrayList<Member> list;
		try {
			list = memberService.selectAll();
			
			if(!list.isEmpty()) {
				menu.displayMemberMenu(list);
			}else {
				menu.displayNoData();
			}
		} catch (MemberException e) {
			menu.displayError("회원 전체 조회 실패, 관리자에게 문의하세요.");
		}
	}

	public void selectOne(String inputMemberId) {
		MemberMenu menu = new MemberMenu();
		Member m;
		try {
			m = memberService.selectOne(inputMemberId);
			
			if(m != null) {
				menu.displayMember(m);
			}else {
				menu.displayNoData(inputMemberId);
			}
		} catch (MemberException e) {
			menu.displayError(inputMemberId + " 회원 조회 실패, 관리자에게 문의하세요.");
		}
		
	}

	public void selectByName(String inputMemberName) {
		MemberMenu menu = new MemberMenu();
		List<Member> list;
		try {
			list = memberService.selectByName(inputMemberName);
			
			if(!list.isEmpty()) {
				menu.displayMemberMenu(list);
			}else {
				menu.displayNoData(inputMemberName);
			}
		} catch (MemberException e) {
			menu.displayError(inputMemberName + " 회원 조회 실패, 관리자에게 문의하세요.");
		}
		
		
	}

	public void insertMember(Member inputMember) {
		MemberMenu menu = new MemberMenu();
		int result;
		try {
			result = memberService.insertMember(inputMember);

			if(result > 0) {
				menu.displaySuccess("회원가입성공");
			}
		} catch (MemberException e) {
			menu.displayError("회원 가입 실패, 관리자에게 문의하세요.");
		}
		
	}

	public void updateMember(Member updateMember) {
		MemberMenu menu = new MemberMenu();
		int result;
		try {
			result = memberService.updateMember(updateMember);
			
			if(result > 0) {
				menu.displaySuccess("회원수정성공");
			}
		} catch (MemberException e) {
			menu.displayError("회원 수정 실패, 관리자에게 문의하세요.");
		}
		
	}

	public void deleteMember(String inputMemberId) {
		MemberMenu menu = new MemberMenu();
		int result;
		try {
			result = memberService.deleteMember(inputMemberId);
			
			if(result > 0) {
				menu.displaySuccess("회원삭제성공");
			}
		} catch (MemberException e) {
			menu.displayError("회원 삭제 실패, 관리자에게 문의하세요.");
		}
		
	}

	public void exitProgram() {
		memberService.exitProgram();
	}

}
