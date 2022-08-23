package com.uni.member.service;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import static com.uni.common.JDBCTemplate.*;

import com.uni.common.exception.MemberException;
import com.uni.member.model.dao.MemberDAO;
import com.uni.member.model.dto.Member;
/*Service
 * 1) Controller 로 부터 인자 전달 받음
 * 2) Connection 객체 생성
 * 3) DAO객체 생성
 * 4) DAO로 생성한 Connection 객체와 인자 전달
 * 5) DAO를 수행한 결과를 가지고 비지니스 로직 및 트랜잭션 관리
 * */
public class MemberService {
	
	private MemberDAO memberDAO = new MemberDAO();

	public ArrayList<Member> selectAll() throws MemberException {
		Connection conn = getConnection();
		ArrayList<Member> list = memberDAO.selectAll(conn);
		return list;
	}

	public Member selectOne(String inputMemberId) throws MemberException {
		Connection conn = getConnection();
		Member m = memberDAO.selectOne(conn, inputMemberId);
		return m;
	}

	public List<Member> selectByName(String inputMemberName) throws MemberException {
		Connection conn = getConnection();
		List<Member> list = memberDAO.selectByName(conn, inputMemberName);
		return list;
	}

	public int insertMember(Member inputMember) throws MemberException {
		Connection conn = getConnection();
		int result = memberDAO.insertMember(conn, inputMember); 
		if(result > 0) {
			commit(conn);
		}else {
			rollback(conn);
		}
		return result;
	}

	public int updateMember(Member updateMember) throws MemberException {
		Connection conn = getConnection();
		int result = memberDAO.updateMember(conn, updateMember);
		if(result > 0) {
			commit(conn);
		}else {
			rollback(conn);
		}
		return result;
	}

	public int deleteMember(String inputMemberId) throws MemberException {
		Connection conn = getConnection();
		int result = memberDAO.deleteMember(conn, inputMemberId);
		if(result > 0) {
			commit(conn);
		}else {
			rollback(conn);
		}
		return result;
	}

	public void exitProgram() {
		close(getConnection());
	}
	
}
