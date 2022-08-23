package com.uni.member.controller;

import java.util.ArrayList;
import java.util.List;

import com.uni.member.MemberMenu;
import com.uni.member.model.dao.MemberDAO;
import com.uni.member.model.dto.Member;

public class MemberController {
	private MemberDAO memberDAO = new MemberDAO();

	public void selectAll() {
		MemberMenu menu = new MemberMenu();
		
		ArrayList<Member> list = memberDAO.selectAll();
		
		if(!list.isEmpty()) {
			menu.displayMemberMenu(list);
		}else {
			menu.displayError("해당되는 데이터가 없습니다.");
		}
	}

	public void selectOne(String inputMemberId) {
		MemberMenu menu = new MemberMenu();
		Member m = new MemberDAO().selectOne(inputMemberId);
		if(m != null) {
			menu.displayMember(m);
		}else {
			menu.displayError(inputMemberId + "해당되는 데이터가 없습니다.");
		}
	}

	public void selectByName(String inputMemberName) {
		MemberMenu menu = new MemberMenu();
		List<Member> list = memberDAO.selectByName(inputMemberName);
		
		if(!list.isEmpty()) {
			menu.displayMemberMenu(list);
		}else {
			menu.displayError("해당되는 데이터가 없습니다.");
		}
	}

	public void insertMember(Member inputMember) {
		int result = memberDAO.insertMember(inputMember);
		if(result > 0) {
			new MemberMenu().displaySuccess("회원가입성공");
		}else {
			new MemberMenu().displayError("회원가입실패");
		}
	}

	public void updateMember(Member updateMember) {
		int result = memberDAO.updateMember(updateMember);
		if(result > 0) {
			new MemberMenu().displaySuccess("회원수정성공");
		}else {
			new MemberMenu().displayError("회원수정실패");
		}
	}

	public void deleteMember(String inputMemberId) {
		int result = memberDAO.deleteMember(inputMemberId);
		if(result > 0) {
			new MemberMenu().displaySuccess("회원삭제성공");
		}else {
			new MemberMenu().displayError("회원삭제실패");
		}
	}

}
