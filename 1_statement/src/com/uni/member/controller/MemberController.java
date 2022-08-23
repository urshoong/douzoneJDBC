package com.uni.member.controller;

import java.util.ArrayList;

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
		
	}

	public void insertMember(Member inputMember) {
		
	}

	public void updateMember(Object updateMember) {
		
	}

	public void deleteMember(String inputMemberId) {
		
	}

}
